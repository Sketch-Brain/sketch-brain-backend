package com.backend.sketchbrain.sketchbrainbackend.result.dto;

import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Result {
    private Integer id;
    @NonNull
    private String user;
    @NonNull
    private String data_name;
    @NonNull
    private String model_name;
    @NonNull
    private String result;
    private Timestamp created_at;
}
