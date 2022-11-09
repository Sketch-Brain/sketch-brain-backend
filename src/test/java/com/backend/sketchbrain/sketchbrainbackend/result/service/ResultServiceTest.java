package com.backend.sketchbrain.sketchbrainbackend.result.service;

import com.backend.sketchbrain.sketchbrainbackend.result.dao.ResultDao;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.InsertResultVo;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.UpdateResultVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ResultServiceTest {

    private ResultService resultService;


    @Mock
    private ResultDao resultDao;

    @BeforeEach
    void init(){
        this.resultService = new ResultService(this.resultDao);

    }
    @Test
    @DisplayName("전체 Result List 를 반환하는 Service 로직이 정상 작동한다.")
    void getAllResultList() {
        List<Result> testAllResultList =  List.of(new Result[]{
                new Result("ED306A15-93BE-458D-AC59-9CDAB636A4EC","user1","data1.csv","model1.csv","21"),
                new Result("181CB408-A561-4AC2-ACFA-B937119CA3E2","user2","data2.csv","model2.csv","22"),
                new Result("13F84F0D-9C7B-4507-B352-2F2A724444C6","user3","data3.csv","model3.csv","23")
        });
        given(resultDao.allList()).willReturn(testAllResultList);
        assertEquals(resultService.getAllResultList().size(),3);
    }

    @Test
    @DisplayName("User 이름을 통한 Result List 를 반환하는 Service 로직이 정상 작동한다.")
    void getResultListByUser() {
        List<Result> testResultListByUser =  List.of(new Result[]{
                new Result("13F84F0D-9C7B-4507-B352-2F2A724444C6","user1","data1.csv","model1.csv","21"),
                new Result("181CB408-A561-4AC2-ACFA-B937119CA3E2","user1","data2.csv","model2.csv","22")
        });
        given(resultDao.listByUser("user1")).willReturn(testResultListByUser);
        assertEquals(resultService.getResultListByUser("user1").size(),2);
    }

    @Test
    @DisplayName("User 이름이 DB에 없으면, 빈 리스트가 반환된다.")
    void getResultListByIncorrectUser() {
        List<Result> testResultListByUser = new ArrayList<>();
        String userName = "@incorrect! user^ &name*";
        given(resultDao.listByUser(userName)).willReturn(testResultListByUser);
        assertTrue(resultService.getResultListByUser(userName).isEmpty());
    }

    @Test
    @DisplayName("Id를 통한 Result List 를 반환하는 Service 로직이 정상 작동한다.")
    void getResultListById() {
        List<Result> testResultListById =  List.of(new Result[]{
                new Result(1,"181CB408-A561-4AC2-ACFA-B937119CA3E2","user1","data1.csv","model1.csv","21",null)
        });
        given(resultDao.listById("1")).willReturn(testResultListById);
        assertEquals(resultService.getResultListById("1").size(),1);
    }

    @Test
    @DisplayName("Id 가 DB에 없으면, 빈 리스트가 반환된다.")
    void getResultListByIncorrectId() {
        List<Result> testResultListById = new ArrayList<>();
        String id = "@incorrect! id^";
        given(resultDao.listById(id)).willReturn(testResultListById);
        assertTrue(resultService.getResultListByUser(id).isEmpty());
    }
    @Test
    @DisplayName("Result 를 추가하는 Service 로직이 정상 작동한다.")
    void insertResult() {
        InsertResultVo testInsertResult = new InsertResultVo("181CB408-A561-4AC2-ACFA-B937119CA3E2","freddie","data1.csv","model1.py","23");
        given(resultDao.insertResult(testInsertResult)).willReturn(1);
        assertEquals(resultService.insertResult(testInsertResult),1);
    }

    @Test
    @DisplayName("정상적이지 않은 Result 에 대한 검사 및 에러 반환이 정상 작동한다.")
    void checkIncorrectArgInResult() {
        InsertResultVo testInsertIncorrectResult = new InsertResultVo();


        testInsertIncorrectResult.setUser("freddie");
        testInsertIncorrectResult.setResult("23");

        assertEquals(resultService.checkIncorrectArgInResult(testInsertIncorrectResult).size(),3);
    }

    @Test
    @DisplayName("Result 를 갱신하는 Service 로직이 정상 작동한다.")
    void updateResult(){
        UpdateResultVo updateResultVo = new UpdateResultVo("181CB408-A561-4AC2-ACFA-B937119CA3E2","91.2");
        given(resultDao.updateResult(updateResultVo)).willReturn(1);
        assertEquals(resultService.updateResult(updateResultVo),1);
    }

    @Test
    @DisplayName("정상적이지 않은 Update Result 에 대한 검사 및 에러 반환이 정상 작동한다.")
    void checkIncorrectUpdateResultVo(){
        UpdateResultVo updateResultVo = new UpdateResultVo();
        updateResultVo.setResult("72.1");
        assertEquals(resultService.checkIncorrectUpdateResultVo(updateResultVo).size(),1);
    }
}