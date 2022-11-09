package com.backend.sketchbrain.sketchbrainbackend.result.dao;

import com.backend.sketchbrain.sketchbrainbackend.result.dto.Result;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.InsertResultVo;
import com.backend.sketchbrain.sketchbrainbackend.result.vo.UpdateResultVo;
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
        String query = "SELECT * FROM result WHERE user= ? ORDER BY id DESC";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class), user);
    }

    public List<Result> listById(String id){
        String query = "SELECT * FROM result WHERE id= ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class),id);
    }

    public List<Result> listByUuid(String uuid){
        String query = "SELECT * FROM result WHERE uuid= ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Result.class),uuid);
    }
    public int insertResult(InsertResultVo insertResultVo){
        String query = "INSERT INTO result(uuid,user,data_name,model_name,result) VALUES(?,?,?,?,?)";
        return jdbcTemplate.update(query,
                new Object[]{insertResultVo.getUuid(),insertResultVo.getUser(), insertResultVo.getData_name(), insertResultVo.getModel_name(), insertResultVo.getResult()});
    }

    public int updateResult(UpdateResultVo updateResultVo){
        String query = "UPDATE result SET result=? WHERE uuid=?";
        return jdbcTemplate.update(query,updateResultVo.getResult(),updateResultVo.getUuid());
    }
}
