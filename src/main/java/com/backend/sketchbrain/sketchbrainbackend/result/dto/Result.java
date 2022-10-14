package com.backend.sketchbrain.sketchbrainbackend.result.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Result {
    private int id;
    private String user;
    private String data_name;
    private String model_name;
    private String result;
    private Timestamp created_at;
}
