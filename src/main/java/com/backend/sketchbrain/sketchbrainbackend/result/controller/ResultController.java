package com.backend.sketchbrain.sketchbrainbackend.result.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.ArgumentError;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.ResultExceptions;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.dto.ResultReturnExample;
import com.backend.sketchbrain.sketchbrainbackend.result.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/server/result")
public class ResultController {
    private final ResultService resultService;

    @Operation(summary = "Get All Result List", description = "전체 Result 의 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Result 조회 성공",
                    content = @Content(schema = @Schema(defaultValue = ResultReturnExample.returnAllResultListExample))
            )
    })
    @ResponseBody
    @GetMapping
    public Map<String, Object> resultAllLis(
    ){
        log.info("[GET] /api/server/result : get all result list");
        List<Result> list = resultService.getAllResultList();
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

    @Operation(summary = "Insert Result", description = "결과를 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Result 저장 성공",
                    content = @Content(schema = @Schema(defaultValue = ResultReturnExample.returnInsertResult))
            )
    })
    @ResponseBody
    @PutMapping
    public Map<String,Object> insertResult(
            @RequestBody Result result
    ){
        log.info("[PUT] /api/server/result : insert result");
        List<ArgumentError> emptyArgList = resultService.checkEmptyArgInResult(result);
        if(!emptyArgList.isEmpty())
            throw new ResultExceptions(ResultErrorCodeImpl.EMPTY_PARAMETER_EXIST,emptyArgList);
        resultService.insertResult(result);
        Map<String,Object> response = new ConcurrentHashMap<>();
        response.put("success", true);
        return response;
    }


    @Operation(summary = "Get Result List By User", description = "해당 유저의 Result 의 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Result 조회 성공",
                    content = @Content(schema = @Schema(defaultValue = ResultReturnExample.returnResultListByUserExample))
            )
    })
    @ResponseBody
    @GetMapping("/user/{user}")
    public Map<String,Object> resultListByUser(
            @PathVariable String user
    ){
        log.info("[GET] /api/server/result/user/{} : get result list filtered by user, {}",user,user);
        List<Result> list = resultService.getResultListByUser(user);
        if(list.isEmpty()){
            throw new ResultExceptions(ResultErrorCodeImpl.UKNOWN_USER_REFERED);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

    @Operation(summary = "Get Result List By Id", description = "해당 Id의 Result 의 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Result 조회 성공",
                    content = @Content(schema = @Schema(defaultValue = ResultReturnExample.returnResultListByIdExample))
            )
    })
    @ResponseBody
    @GetMapping("/id/{id}")
    public Map<String,Object> resultListById(
            @PathVariable String id
    ){
        log.info("[GET] /api/server/id/{} : get result list filtered by id, {}",id,id);
        List<Result> list = resultService.getResultListById(id);
        if(list.isEmpty()){
            throw new ResultExceptions(ResultErrorCodeImpl.UKNOWN_ID_REFERED);
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("result", list);
        return result;
    }

}
