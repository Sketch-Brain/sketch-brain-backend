package com.backend.sketchbrain.sketchbrainbackend.layer.dto;

public class LayerReturnExample {

    public static final String returnAllLayerListExample =
            "{\n" +
            "  \"layer_list\": [\n" +
            "    \"layer_name1\",\n" +
            "    \"layer_name2\",\n" +
            "    \"layer_name3\",\n" +
            "  ]\n" +
            "}";
    public static final String returnLayerParameterExample =
            "{\n" +
            "  \"parameter_name1\": {\n" +
            "    \"type\": \"string\",\n" +
            "    \"visible\": false,\n" +
            "    \"default_value\": null\n" +
            "  },\n" +
            "  \"parameter_name2\": {\n" +
            "    \"type\": \"float\",\n" +
            "    \"visible\": true,\n" +
            "    \"default_value\": null\n" +
            "  }\n" +
            "}";
}
