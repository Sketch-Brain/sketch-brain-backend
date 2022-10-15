package com.backend.sketchbrain.sketchbrainbackend.result.service;

import com.backend.sketchbrain.sketchbrainbackend.result.dao.ResultDao;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public int insertResult(Result result){
        return resultDao.insertResult(result);
    }
}
