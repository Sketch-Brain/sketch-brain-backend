package com.backend.sketchbrain.sketchbrainbackend.result.service;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import com.backend.sketchbrain.sketchbrainbackend.result.dao.ResultDao;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
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

    public int insertResult(Result result){
        return resultDao.insertResult(result);
    }

    public int updateResult(UpdateResultVo updateResultVo){return resultDao.updateResult(updateResultVo);}

    public List<ArgumentError> checkIncorrectArgInResult(Result result){
        List<ArgumentError> argumentErrorList = new ArrayList<>();
        if(result.getId() != null){
            argumentErrorList.add(new ArgumentError("result",result.getId().toString(),"DON'T NEED TO INSERT ID"));
        }
        if(result.getUuid() == null){
            argumentErrorList.add(new ArgumentError("uuid",result.getUuid(),"NEED TO INSERT UUID"));
        }
        if (result.getUser() == null){
            argumentErrorList.add(new ArgumentError("user",result.getUser(),"NEED TO INSERT USER"));
        }
        if (result.getData_name() == null){
            argumentErrorList.add(new ArgumentError("data_name",result.getData_name(),"NEED TO INSERT MODEL_NAME"));
        }
        if (result.getModel_name() == null){
            argumentErrorList.add(new ArgumentError("model_name",result.getModel_name(),"NEED TO INSERT MODEL_NAME"));
        }
        if (result.getResult() == null){
            argumentErrorList.add(new ArgumentError("result",result.getResult(),"NEED TO INSERT RESULT"));
        }
        if(result.getCreated_at() != null){
            argumentErrorList.add(new ArgumentError("created_at",result.getCreated_at().toString(),"DON'T NEED TO INSERT CREATED_AT"));
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
