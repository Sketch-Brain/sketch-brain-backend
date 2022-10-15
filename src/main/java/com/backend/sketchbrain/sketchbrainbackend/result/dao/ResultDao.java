package com.backend.sketchbrain.sketchbrainbackend.result.dao;

import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ResultDao {

    private final JdbcTemplate jdbcTemplate;

    public List<Result> allList(){
        String query = "SELECT * FROM result ORDER BY id DESC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class));
    }

    public List<Result> listByUser(String user){
        String query = "SELECT * FROM result WHERE user='" + user + "' ORDER BY id DESC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class));
    }

    public List<Result> listById(String id){
        String query = "SELECT * FROM result WHERE id='" + id + "'";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class));
    }

    public int insertResult(Result result){
        String query = "INSERT INTO result(user,data_name,model_name,result) VALUES(?,?,?,?)";
        return jdbcTemplate.update(query,
                new Object[]{result.getUser(), result.getData_name(), result.getModel_name(), result.getResult()});

    }
}
