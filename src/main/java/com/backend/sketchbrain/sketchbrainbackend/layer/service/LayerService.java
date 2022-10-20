package com.backend.sketchbrain.sketchbrainbackend.layer.service;

import com.backend.sketchbrain.sketchbrainbackend.layer.dao.LayerDao;
import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LayerService {
    private final LayerDao layerDao;

    public List<String> getLayerList(){
        List<Layer> queryResult = layerDao.layerList();
        List<String> result = new ArrayList<>();
        queryResult.stream().forEach(layer -> result.add(layer.getLayer_name()));
        return result;
    }

    public String getLayerParameter(String layer_name){
        List<Layer> queryResult = layerDao.layerParameter(layer_name);
        if(queryResult.isEmpty())
            return null;
        else
            return queryResult.get(0).getParameter();
    }
}
