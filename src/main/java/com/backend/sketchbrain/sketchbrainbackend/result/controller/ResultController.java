package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultExceptions;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@RestController
@RequestMapping(path="/api/result")
public class ResultController {
    private final ResultService resultService;


    @ResponseBody
    @GetMapping
    public Map<String, Object> resultAllLis(
    ){
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
        List<Result> list = resultService.getResultListById(id);
        if(list.isEmpty()){
            throw new ResultExceptions(ResultErrorCodeImpl.UKNOWN_ID_REFERED);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

}
