package com.backend.sketchbrain.sketchbrainbackend.layer.dao;

import com.backend.sketchbrain.sketchbrainbackend.layer.dto.Layer;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class LayerDao {
    private final JdbcTemplate jdbcTemplate;

    public List<String> layerList(){
        String query = "SELECT layer_name FROM layer";
        List<Layer> queryResult = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Layer.class));
        List<String> result = new ArrayList<>();
        queryResult.stream().forEach(layer -> result.add(layer.getLayer_name()));
        return result;
    }

    public String layerParameter(String layer_name){
        String query = "SELECT parameter FROM layer WHERE layer_name='" + layer_name +"'";
        List<Layer> queryResult = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Layer.class));
        if(queryResult.isEmpty())
            return null;
        else
            return queryResult.get(0).getParameter();
    }
}
