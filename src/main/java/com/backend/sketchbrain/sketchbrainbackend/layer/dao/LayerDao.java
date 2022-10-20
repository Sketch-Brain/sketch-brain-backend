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

    public List<Layer> layerList(){
        String query = "SELECT layer_name FROM layer";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Layer.class));
    }

    public List<Layer> layerParameter(String layer_name){
        String query = "SELECT parameter FROM layer WHERE layer_name='" + layer_name +"'";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Layer.class));
    }
}
