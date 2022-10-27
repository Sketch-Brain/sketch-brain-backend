package com.backend.sketchbrain.sketchbrainbackend.result.service;

import com.backend.sketchbrain.sketchbrainbackend.result.dao.ResultDao;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
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
                new Result("user1","data1.csv","model1.csv","21"),
                new Result("user2","data2.csv","model2.csv","22"),
                new Result("user3","data3.csv","model3.csv","23")
        });
        given(resultDao.allList()).willReturn(testAllResultList);
        assertEquals(resultService.getAllResultList().size(),3);
    }

    @Test
    @DisplayName("User 이름을 통한 Result List 를 반환하는 Service 로직이 정상 작동한다.")
    void getResultListByUser() {
        List<Result> testResultListByUser =  List.of(new Result[]{
                new Result("user1","data1.csv","model1.csv","21"),
                new Result("user1","data2.csv","model2.csv","22")
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
                new Result(1,"user1","data1.csv","model1.csv","21",null)
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
        Result testInsertResult = new Result("freddie","data1.csv","model1.py","23");
        given(resultDao.insertResult(testInsertResult)).willReturn(1);
        assertEquals(resultService.insertResult(testInsertResult),1);
    }

    @Test
    @DisplayName("정상적이지 않은 Result 에 대한 검사 및 에러 반환이 정상 작동한다.")
    void checkIncorrectArgInResult() {
        Result testInsertIncorrectResult = new Result();

        testInsertIncorrectResult.setId(1);
        testInsertIncorrectResult.setUser("freddie");
        testInsertIncorrectResult.setResult("23");

        assertEquals(resultService.checkIncorrectArgInResult(testInsertIncorrectResult).size(),3);
    }
}