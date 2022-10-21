package com.backend.sketchbrain.sketchbrainbackend.layer.controller;

import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.LayerErrorCodeImpl;
import com.backend.sketchbrain.sketchbrainbackend.global.error.exceptions.LayerExceptions;
import com.backend.sketchbrain.sketchbrainbackend.layer.dto.LayerReturnExample;
import com.backend.sketchbrain.sketchbrainbackend.layer.service.LayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/server/layer")
public class LayerController {
    private final LayerService layerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "Get All Layer List", description = "전체 Layer 의 이름 목록을 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Layer 조회 성공",
                    content = @Content(schema = @Schema(defaultValue = LayerReturnExample.returnAllLayerListExample))
            )
    })
    @ResponseBody
    @GetMapping
    public Map<String, Object> allLayerList(){
        log.info("[GET] /api/server/layer : get all layer list");
        List<String> layerList = layerService.getLayerList();
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("layer_list",layerList);
        return result;
    }

    @Operation(summary = "Get Layer Parameter", description = "Layer 의 Parameter 를 반환한다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",description = "Layer Parameter 조회 성공",
                    content = @Content(schema = @Schema(defaultValue = LayerReturnExample.returnLayerParameterExample))
            ),
            @ApiResponse(
                    responseCode = "400", description = "Layer Parameter 조회 실패"
            )
    })
    @ResponseBody
    @GetMapping("/name/{layer_name}")
    public Map<String, Object> layerParameter(
            @PathVariable String layer_name
    ) throws JsonProcessingException {
        log.info("[GET] /api/server/layer/name/{} : get {} layer parameter",layer_name,layer_name);
        String layerParameter = layerService.getLayerParameter(layer_name);

        if(layerParameter == null)
            throw new LayerExceptions(LayerErrorCodeImpl.UNKNOWN_LAYER_NAME_REFERED);
        return objectMapper.readValue(layerParameter, new TypeReference<>() {});
    }
}
