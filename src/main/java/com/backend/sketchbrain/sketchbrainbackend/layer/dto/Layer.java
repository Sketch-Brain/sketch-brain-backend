package com.backend.sketchbrain.sketchbrainbackend.layer.dto;


import lombok.*;
import nonapi.io.github.classgraph.json.Id;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Layer {
    @NonNull
    private Integer id;
    @NonNull
    private String layer_name;
    @NonNull
    private String parameter;
}