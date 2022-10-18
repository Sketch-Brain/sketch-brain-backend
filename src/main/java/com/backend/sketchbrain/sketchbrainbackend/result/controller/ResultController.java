package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultExceptions;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/result")
public class ResultController {
    private final ResultService resultService;


    @ResponseBody
    @GetMapping
    public Map<String, Object> resultAllLis(
    ){
        log.info("[GET] /api/result : get result list");
        List<Result> list = resultService.getAllResultList();
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

    @ResponseBody
    @PutMapping
    public Map<String,Object> insertResult(
            @RequestBody Result result
    ){
        log.info("[PUT] /api/result : insert result");
        resultService.insertResult(result);
        Map<String,Object> response = new ConcurrentHashMap<>();
        response.put("success", true);
        return response;
    }

    @ResponseBody
    @GetMapping("/user/{user}")
    public Map<String,Object> resultListByUser(
            @PathVariable String user
    ){
        log.info("[GET] /api/result/user/{} : get result list filtered by {}",user,user);
        List<Result> list = resultService.getResultListByUser(user);
        if(list.isEmpty()){
            throw new ResultExceptions(ResultErrorCodeImpl.UKNOWN_USER_REFERED);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

    @ResponseBody
    @GetMapping("/id/{id}")
    public Map<String,Object> resultListById(
            @PathVariable String id
    ){
        log.info("[GET] /api/result/id/{} : get result list filtered by {}",id,id);
        List<Result> list = resultService.getResultListById(id);
        if(list.isEmpty()){
            throw new ResultExceptions(ResultErrorCodeImpl.UKNOWN_ID_REFERED);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

}
