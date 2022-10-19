package com.backend.sketchbrain.sketchbrainbackend.layer.controller;

import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import com.backend.sketchbrain.sketchbrainbackend.layer.service.LayerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/server/layer")
public class LayerController {
    private final LayerService layerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @ResponseBody
    @GetMapping
    public Map<String, Object> allLayerList(){
        log.info("[GET] /api/server/layer : get all layer list");
        List<String> layerList = layerService.getLayerList();
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("layer_list",layerList);
        return result;
    }

    @ResponseBody
    @GetMapping("/name/{layer_name}")
    public Map<String, Object> layerParameter(
            @PathVariable String layer_name
    ) throws JsonProcessingException {
        log.info("[GET] /api/server/layer/name/{} : get {} layer parameter",layer_name,layer_name);
        String layerParameter = layerService.getLayerParameter(layer_name);
        if(layerParameter != null)
            return objectMapper.readValue(layerParameter, new TypeReference<>() {});
        else{
            Map<String, Object> result = new ConcurrentHashMap<>();
            result.put("success", false);
            return result;
        }
    }

}
