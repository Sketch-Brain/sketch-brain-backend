package com.backend.sketchbrain.sketchbrainbackend.layer.controller;

import com.backend.sketchbrain.sketchbrainbackend.layer.service.LayerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class LayerControllerTest {

    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private LayerService layerService;

    @BeforeEach
    void init(){
        this.mvc = MockMvcBuilders.standaloneSetup(new LayerController(layerService)).build();
    }

    @Test
    void allLayerList() throws Exception {
        List<String> getLayerListReturn = List.of(new String[]{"activation","conv2d","flatten"});
        Map<String, Object> result = new ConcurrentHashMap<>();

        result.put("layer_list",getLayerListReturn);
        given(layerService.getLayerList()).willReturn(getLayerListReturn);

        MvcResult response = mvc.perform(get("/api/server/layer"))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(
            objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {}),
            result
                );
    }

    @Test
    void layerParameter() throws Exception {
        String layerName = "activation";
        String getLayerParameterReturn = "{\"activation\": {\"type\": \"string\", \"visible\": true, \"default_value\": \"relu\"}}";
        given(layerService.getLayerParameter(layerName)).willReturn(getLayerParameterReturn);

        MvcResult response = mvc.perform(get("/api/server/layer/name/" + layerName ))
                .andExpect(status().isOk())
                .andReturn();
        Object responseJson = objectMapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(
                responseJson,
                objectMapper.readValue(getLayerParameterReturn, new TypeReference<>() {})
        );
    }
}