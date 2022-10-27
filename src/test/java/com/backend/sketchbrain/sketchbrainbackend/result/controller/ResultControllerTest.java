package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        given(resultService.getAllResultList()).willReturn(allResultList);

        mvc.perform(get("/api/server/result"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty())
                .andExpect(jsonPath("result[2].id").value(3))
                .andReturn();
    }

    @Test
    @DisplayName("Result 를 추가하는 Controller 로직이 정상 작동한다.")
    void insertResult() throws Exception {
        Result insertResult = new Result("user","data.csv","model.py","23");
        given(resultService.insertResult(insertResult)).willReturn(1);
        given(resultService.checkIncorrectArgInResult(insertResult)).willReturn(new ArrayList<>());
        String content = objectMapper.writeValueAsString(insertResult);

        mvc.perform(put("/api/server/result")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    @DisplayName("정상적이지 않은 Result 는 추가되지 않고, 에러를 반환한다")
    void insertIncorrectResult() throws Exception{
        Result incorrectResult = new Result(1,"user","data.csv","model.py","23",null);

        List<ArgumentError> argumentErrorList = List.of(new ArgumentError[]{
                new ArgumentError("result","1","DON'T NEED TO INSERT ID")
        });

        given(resultService.checkIncorrectArgInResult(incorrectResult)).willReturn(argumentErrorList);
        String content = objectMapper.writeValueAsString(incorrectResult);
        mvc.perform(put("/api/server/result")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.INCORRECT_PARAMETER_EXIST.getMessage()));
    }

    @Test
    @DisplayName("User 이름을 통한 Result List 를 반환하는 Controller 로직이 정상 작동한다.")
    void resultListByUser() throws Exception {
        List<Result> testResultListByUser =  List.of(new Result[]{
                new Result("user1","data1.csv","model1.csv","21"),
                new Result("user1","data2.csv","model2.csv","22")
        });
        given(resultService.getResultListByUser("user1")).willReturn(testResultListByUser);

        mvc.perform(get("/api/server/result/user/{user}","user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty());
    }

    @Test
    @DisplayName("User 이름이 DB에 없으면, Not Found 를 반환한다.")
    void resultListByIncorrectUser() throws  Exception{
        String userName = "@incorrect! user^ &name*";
        List<Result> testResultListByIncorrectUser = new ArrayList<>();
        given(resultService.getResultListByUser(userName)).willReturn(testResultListByIncorrectUser);

        mvc.perform(get("/api/server/result/user/{user}",userName))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.UKNOWN_USER_REFERED.getMessage()));
    }
    @Test
    @DisplayName("id 를 통한 Result List 를 반환하는 Controller 로직이 정상 작동한다.")
    void resultListById() throws Exception {
        List<Result> testResultListById =  List.of(new Result[]{
                new Result(1,"user1","data1.csv","model1.csv","21",null)
        });
        given(resultService.getResultListById("1")).willReturn(testResultListById);

        mvc.perform(get("/api/server/result/id/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty());
    }

    @Test
    @DisplayName("id 가 DB에 없으면, Not Found 를 반환한다.")
    void resultLlistByIncorrectId() throws Exception{
        List<Result> testResultListByIncorrectId = new ArrayList<>();
        String id = "@incorrect! id^";
        given(resultService.getResultListById(id)).willReturn(testResultListByIncorrectId);

        mvc.perform(get("/api/server/result/id/{id}",id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.UKNOWN_ID_REFERED.getMessage()));
    }
}