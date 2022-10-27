package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class ResultControllerTest {

    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private ResultService resultService;

    @Test
    @DisplayName("전체 Result List 를 반환하는 Controller 로직이 정상 작동한다.")
    void resultAllLis() throws Exception {
        List<Result> allResultList = List.of(new Result[]{
                new Result(1,"user1","data.csv","model.py","21",null),
                new Result(2,"user2","data.csv","model.py","21",null),
                new Result(3,"user3","data.csv","model.py","21",null)
        });
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result",allResultList);
        given(resultService.getAllResultList()).willReturn(allResultList);

        mvc.perform(get("/api/server/result"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty())
                .andExpect(jsonPath("result[2].id").value(3))
                .andReturn();
    }

    @Test
    void insertResult() {
    }

    @Test
    void resultListByUser() {
    }

    @Test
    void resultListById() {
    }
}