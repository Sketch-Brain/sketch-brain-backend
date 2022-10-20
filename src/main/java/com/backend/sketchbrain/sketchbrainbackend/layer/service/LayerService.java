package com.backend.sketchbrain.sketchbrainbackend.layer.service;

import com.backend.sketchbrain.sketchbrainbackend.layer.dao.LayerDao;
import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import io.swagger.v3.core.util.Json;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LayerService {
    private final LayerDao layerDao;

    public List<String> getLayerList(){
        return layerDao.layerList();
    }

    public String getLayerParameter(String layer_name){
        return layerDao.layerParameter(layer_name);
    }
}
