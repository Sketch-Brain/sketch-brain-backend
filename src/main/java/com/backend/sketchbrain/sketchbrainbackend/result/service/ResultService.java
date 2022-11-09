package com.backend.sketchbrain.sketchbrainbackend.result.service;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import com.backend.sketchbrain.sketchbrainbackend.result.dao.ResultDao;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.InsertResultVo;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.UpdateResultVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResultService {
    private final ResultDao resultDao;

    public List<Result> getAllResultList(){
        return resultDao.allList();
    }

    public List<Result> getResultListByUser(String user){
        return resultDao.listByUser(user);
    }

    public List<Result> getResultListById(String id){
        return resultDao.listById(id);
    }

    public List<Result> getResultListByUuid(String uuid){
        return resultDao.listByUuid(uuid);
    }

    public int insertResult(InsertResultVo insertResultVo){
        return resultDao.insertResult(insertResultVo);
    }

    public int updateResult(UpdateResultVo updateResultVo){return resultDao.updateResult(updateResultVo);}

    public List<ArgumentError> checkIncorrectArgInResult(InsertResultVo insertResultVo){
        List<ArgumentError> argumentErrorList = new ArrayList<>();

        if(insertResultVo.getUuid() == null){
            argumentErrorList.add(new ArgumentError("uuid",insertResultVo.getUuid(),"NEED TO INSERT UUID"));
        }
        if (insertResultVo.getUser() == null){
            argumentErrorList.add(new ArgumentError("user",insertResultVo.getUser(),"NEED TO INSERT USER"));
        }
        if (insertResultVo.getData_name() == null){
            argumentErrorList.add(new ArgumentError("data_name",insertResultVo.getData_name(),"NEED TO INSERT MODEL_NAME"));
        }
        if (insertResultVo.getModel_name() == null){
            argumentErrorList.add(new ArgumentError("model_name",insertResultVo.getModel_name(),"NEED TO INSERT MODEL_NAME"));
        }
        if (insertResultVo.getResult() == null){
            argumentErrorList.add(new ArgumentError("result",insertResultVo.getResult(),"NEED TO INSERT RESULT"));
        }

        return argumentErrorList;
    }
    public List<ArgumentError> checkIncorrectUpdateResultVo(UpdateResultVo updateResultVo){
        List<ArgumentError> argumentErrorList = new ArrayList<>();

        if(updateResultVo.getUuid() == null){
            argumentErrorList.add(new ArgumentError("uuid",updateResultVo.getUuid(),"NEED TO INSERT UUID"));
        }
        if (updateResultVo.getResult() == null){
            argumentErrorList.add(new ArgumentError("result",updateResultVo.getResult(),"NEED TO INSERT RESULT"));
        }
        return argumentErrorList;
    }
}
