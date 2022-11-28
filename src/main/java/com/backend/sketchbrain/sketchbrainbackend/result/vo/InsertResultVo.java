package com.backend.sketchbrain.sketchbrainbackend.result.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InsertResultVo {
    private String uuid;
    private String user;
    private String data_name;
    private String model_name;
    private String result;
}
