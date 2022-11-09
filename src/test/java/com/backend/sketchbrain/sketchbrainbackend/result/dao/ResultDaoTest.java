package com.backend.sketchbrain.sketchbrainbackend.result.dao;

import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.UpdateResultVo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ResultDaoTest {

    private ResultDao resultDao;

    @Autowired
    public ResultDaoTest(ResultDao resultDao){
        this.resultDao = resultDao;
    }

    @Test
    @DisplayName("DB 에서 전체 Result 리스트에 대한 조회가 정상 작동한다.")
    void allList() {
        assertFalse(resultDao.allList().isEmpty());
    }

    @Test
    @DisplayName("DB 에서 User 이름을 통한 Result 에 대한 조회가 정상 작동한다.")
    void listByUser() {
        List<Result> resultList = resultDao.allList();
        if(resultList.isEmpty())
            fail();
        String userName = resultList.get(0).getUser();
        assertFalse(resultDao.listByUser(userName).isEmpty());
    }

    @Test
    @DisplayName("User 이름이 DB에 없으면, 빈 값이 반환된다.")
    void listByUserIncorrectName(){
        List<Result> resultList = resultDao.allList();
        if(resultList.isEmpty())
            fail();
        String userName = "@incorrect! user^ &name*";
        assertTrue(resultDao.listByUser(userName).isEmpty());
    }

    @Test
    @DisplayName("DB 에서 Id 통한 Result 에 대한 조회가 정상 작동한다.")
    void listById() {
        List<Result> resultList = resultDao.allList();
        if(resultList.isEmpty())
            fail();
        String id = resultList.get(0).getId().toString();
        assertFalse(resultDao.listById(id).isEmpty());
    }

    @Test
    @DisplayName("User 이름이 DB에 없으면, 빈 값이 반환된다.")
    void listByIdIncorrectId(){
        List<Result> resultList = resultDao.allList();
        if(resultList.isEmpty())
            fail();
        String id = "@incorrect! id^";
        assertTrue(resultDao.listById(id).isEmpty());
    }


    @Test
    @Transactional
    @DisplayName("DB 에서 Result 에 대한 추가가 정상 작동한다.")
    void insertResult() {
        Result testData = new Result("ED306A15-93BE-458D-AC59-9CDAB636A4EC","testUser","data.csv","model.py","12.0");
        assertEquals(resultDao.insertResult(testData),1);
    }

    @Test
    @Transactional
    @DisplayName("정상적이지 않은 Result 는 추가되지 않는다.")
    void insertReulstIncorrectData(){
        Result testIncorrectData = new Result();
        testIncorrectData.setUser("freddie");
        testIncorrectData.setData_name("tmp.csv");
        assertThrows(
                DataIntegrityViolationException.class, () ->{
                    resultDao.insertResult(testIncorrectData);
                }
        );
    }

    @Test
    @Transactional
    @DisplayName("DB 에서 Uuid 를 통한 Result 에 대한 갱신이 정상 작동한다.")
    void updateResult(){
        Result testData = new Result("c72843e9-98dc-4539-9b05-ab0005c736f3","testUser","data.csv","model.py","12.0");
        UpdateResultVo updateResultVo = new UpdateResultVo("c72843e9-98dc-4539-9b05-ab0005c736f3","98.2");
        resultDao.insertResult(testData);
        assertEquals(resultDao.updateResult(updateResultVo),1);
    }

    @Test
    @Transactional
    @DisplayName("정상적이지 않은 Uuid 를 통한 Result 에 대한 갱신이 이뤄지지 않는다.")
    void updateInvalidUuidResult(){
        UpdateResultVo updateResultVo = new UpdateResultVo("inva!lid@ u%uid^","91.2");
        assertEquals(resultDao.updateResult(updateResultVo),0);
    }
}