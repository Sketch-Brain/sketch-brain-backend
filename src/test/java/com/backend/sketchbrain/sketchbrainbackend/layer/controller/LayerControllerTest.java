package com.backend.sketchbrain.sketchbrainbackend.layer.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.LayerErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.layer.service.LayerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LayerControllerTest {

    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private LayerService layerService;


    @Test
    @DisplayName("전체 Layer Name List 를 반환하는 Controller 로직이 정상 작동한다.")
    void allLayerList() throws Exception {
        List<String> getLayerListReturn = List.of(new String[]{"activation","conv2d","flatten"});
        Map<String, Object> result = new ConcurrentHashMap<>();

        result.put("layer_list",getLayerListReturn);
        given(layerService.getLayerList()).willReturn(getLayerListReturn);

        mvc.perform(get("/api/server/layer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("layer_list").isNotEmpty())
                .andExpect(jsonPath("layer_list[0]").value("activation"))
                .andExpect(jsonPath("layer_list[1]").value("conv2d"))
                .andExpect(jsonPath("layer_list[2]").value("flatten"))
                .andReturn();
    }

    @Test
    @DisplayName("Layer 이름을 통한 Layer Parameter 를 반환하는 Controller 로직이 정상 작동한다.")
    void layerParameter() throws Exception {
        String layerName = "activation";
        String getLayerParameterReturn = "{\"activation\": {\"type\": \"string\", \"visible\": true, \"default_value\": \"relu\"}}";
        given(layerService.getLayerParameter(layerName)).willReturn(getLayerParameterReturn);

        mvc.perform(get("/api/server/layer/name/" + layerName ))
                .andExpect(status().isOk())
                .andExpect(jsonPath(layerName).isNotEmpty())
                .andExpect(jsonPath("activation.type").value("string"))
                .andExpect(jsonPath("activation.visible").value("true"))
                .andExpect(jsonPath("activation.default_value").value("relu"));
    }

    @Test
    @DisplayName("Layer 이름이 DB에 없으면, Not Found 를 반환한다.")
    void layerParameterIncorrectLayerName() throws Exception{
        String layerName = "@incorrect! layer^ &name*";
        given(layerService.getLayerParameter(layerName)).willReturn(null);

        mvc.perform(get("/api/server/layer/name/{layer_name}",layerName))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(LayerErrorCodeImpl.UNKNOWN_LAYER_NAME_REFERED.getMessage()));
    }
}