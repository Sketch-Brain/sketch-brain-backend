package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.DeleteResultVo;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.InsertResultVo;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.UpdateResultVo;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                new Result(1,"ED306A15-93BE-458D-AC59-9CDAB636A4EC","user1","data.csv","model.py","11",null),
                new Result(2,"181CB408-A561-4AC2-ACFA-B937119CA3E2","user2","data.csv1","model.py","21",null),
                new Result(3,"13F84F0D-9C7B-4507-B352-2F2A724444C6","user3","data.csv2","model.py","21",null)
        });

        given(resultService.getAllResultList()).willReturn(allResultList);

        mvc.perform(get("/api/server/result"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty())
                .andExpect(jsonPath("result[0].result").value("11"))
                .andExpect(jsonPath("result[1].id").value(2))
                .andExpect(jsonPath("result[2].data_name").value("data.csv2"))
                .andReturn();
    }

    @Test
    @DisplayName("Result 를 추가하는 Controller 로직이 정상 작동한다.")
    void insertResult() throws Exception {
        InsertResultVo insertResult = new InsertResultVo("13F84F0D-9C7B-4507-B352-2F2A724444C6","user","data.csv","model.py","23");
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
        InsertResultVo incorrectResult = new InsertResultVo("13F84F0D-9C7B-4507-B352-2F2A724444C6","user","data.csv","model.py",null);

        List<ArgumentError> argumentErrorList = List.of(new ArgumentError[]{
                new ArgumentError("result",null,"NEED TO INSERT RESULT")
        });

        given(resultService.checkIncorrectArgInResult(incorrectResult)).willReturn(argumentErrorList);
        String content = objectMapper.writeValueAsString(incorrectResult);
        mvc.perform(put("/api/server/result")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.INCORRECT_PARAMETER_EXIST.getMessage()))
                .andExpect(jsonPath("errors").isNotEmpty())
                .andExpect(jsonPath("errors[0].fieldName").value("result"))
                .andExpect(jsonPath("errors[0].reason").value("NEED TO INSERT RESULT"));
    }

    @Test
    @DisplayName("User 이름을 통한 Result List 를 반환하는 Controller 로직이 정상 작동한다.")
    void resultListByUser() throws Exception {
        List<Result> testResultListByUser =  List.of(new Result[]{
                new Result("13F84F0D-9C7B-4507-B352-2F2A724444C6","user1","data1.csv","model1.csv","21"),
                new Result("181CB408-A561-4AC2-ACFA-B937119CA3E2","user1","data2.csv","model2.csv","22")
        });
        given(resultService.getResultListByUser("user1")).willReturn(testResultListByUser);

        mvc.perform(get("/api/server/result/user/{user}","user1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty())
                .andExpect(jsonPath("result[0].data_name").value("data1.csv"))
                .andExpect(jsonPath("result[1].model_name").value("model2.csv"))
                .andExpect(jsonPath("result[1].result").value("22"));
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
                new Result(1,"181CB408-A561-4AC2-ACFA-B937119CA3E2","user1","data1.csv","model1.csv","21",null)
        });
        given(resultService.getResultListById("1")).willReturn(testResultListById);

        mvc.perform(get("/api/server/result/id/{id}","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").isNotEmpty())
                .andExpect(jsonPath("result[0].user").value("user1"))
                .andExpect(jsonPath("result[0].data_name").value("data1.csv"))
                .andExpect(jsonPath("result[0].result").value("21"));
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

    @Test
    @DisplayName("Uuid 를 통한 Result 갱신에 대한 Controller 로직이 정상 작동한다.")
    void updateResult() throws Exception{
        UpdateResultVo updateResultVo = new UpdateResultVo("6b2089c5-9f6a-4f51-904e-de10e3c455a1","93.1");
        given(resultService.updateResult(updateResultVo)).willReturn(1);
        given(resultService.checkIncorrectUpdateResultVo(updateResultVo)).willReturn(new ArrayList<>());
        String content = objectMapper.writeValueAsString(updateResultVo);

        mvc.perform(patch("/api/server/result")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("success").value(true));
    }

    @Test
    @DisplayName("정상적이지 않은 Result 는 갱신되지 않고, 에러를 반환한다")
    void updateInvalidResult() throws Exception{
        UpdateResultVo updateResultVo = new UpdateResultVo();
        updateResultVo.setUuid("6b2089c5-9f6a-4f51-904e-de10e3c455a1");
        List<ArgumentError> argumentErrorList = List.of(new ArgumentError[]{
                new ArgumentError("uuid",null,"NEED TO INSERT UUID")
        });

        given(resultService.checkIncorrectUpdateResultVo(updateResultVo)).willReturn(argumentErrorList);
        String content = objectMapper.writeValueAsString(updateResultVo);

        mvc.perform(patch("/api/server/result")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.INCORRECT_PARAMETER_EXIST.getMessage()))
                .andExpect(jsonPath("errors").isNotEmpty())
                .andExpect(jsonPath("errors[0].fieldName").value("uuid"))
                .andExpect(jsonPath("errors[0].reason").value("NEED TO INSERT UUID"));
    }

    @Test
    @DisplayName("Uuid 를 통한 Result 삭제 Controller 로직이 정상 작동한다.")
    void deleteResult() throws Exception {

        DeleteResultVo deleteResultVo = new DeleteResultVo("6b2089c5-9f6a-4f51-904e-de10e3c455a1");
        given(resultService.deleteResult(deleteResultVo.getUuid())).willReturn(1);
        String content = objectMapper.writeValueAsString(deleteResultVo);
        mvc.perform(delete("/api/server/result")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success").value(true));
    }

    @Test
    @DisplayName("정상적이지 않은 Result 는 삭제되지 않고, 에러를 반환한다")
    void deleteInvalidUuidResult() throws Exception{

        DeleteResultVo deleteResultVo = new DeleteResultVo("inva#lid% ^uuid!");

        given(resultService.deleteResult(deleteResultVo.getUuid())).willReturn(0);
        String content = objectMapper.writeValueAsString(deleteResultVo);
        mvc.perform(delete("/api/server/result")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value(ResultErrorCodeImpl.UKNOWN_UUID_REFERED.getMessage()));
    }
}