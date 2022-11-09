package com.backend.sketchbrain.sketchbrainbackend.result.dto;

public class ResultReturnExample {
    public static final String returnAllResultListExample = "{\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"uuid\": ED306A15-93BE-458D-AC59-9CDAB636A4EC,\n" +
            "      \"user\": \"user1\",\n" +
            "      \"data_name\": \"data1.csv\",\n" +
            "      \"model_name\": \"model1.py\",\n" +
            "      \"result\": \"32\",\n" +
            "      \"created_at\": \"2022-10-20T04:00:32.000+00:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"uuid\": 181CB408-A561-4AC2-ACFA-B937119CA3E2,\n" +
            "      \"user\": \"user2\",\n" +
            "      \"data_name\": \"data2.csv\",\n" +
            "      \"model_name\": \"model2.py\",\n" +
            "      \"result\": \"23\",\n" +
            "      \"created_at\": \"2022-10-20T01:44:00.000+00:00\"\n" +
            "    },\n" +
            "  ]\n" +
            "}";

    public static final String returnResultListByUserExample = "{\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"uuid\": ED306A15-93BE-458D-AC59-9CDAB636A4EC,\n" +
            "      \"user\": \"user\",\n" +
            "      \"data_name\": \"data1.csv\",\n" +
            "      \"model_name\": \"model1.py\",\n" +
            "      \"result\": \"32\",\n" +
            "      \"created_at\": \"2022-10-20T04:00:32.000+00:00\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"uuid\": 181CB408-A561-4AC2-ACFA-B937119CA3E2,\n" +
            "      \"user\": \"user\",\n" +
            "      \"data_name\": \"data2.csv\",\n" +
            "      \"model_name\": \"model2.py\",\n" +
            "      \"result\": \"23\",\n" +
            "      \"created_at\": \"2022-10-20T01:44:00.000+00:00\"\n" +
            "    },\n" +
            "  ]\n" +
            "}";

    public static final String returnResultListByIdExample = "{\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"uuid\": ED306A15-93BE-458D-AC59-9CDAB636A4EC,\n" +
            "      \"user\": \"user\",\n" +
            "      \"data_name\": \"data1.csv\",\n" +
            "      \"model_name\": \"model1.py\",\n" +
            "      \"result\": \"32\",\n" +
            "      \"created_at\": \"2022-10-20T04:00:32.000+00:00\"\n" +
            "    }\n" +
            "}";

    public static final String returnResultListByUuidExample = "{\n" +
            "  \"result\": [\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"uuid\": ED306A15-93BE-458D-AC59-9CDAB636A4EC,\n" +
            "      \"user\": \"user\",\n" +
            "      \"data_name\": \"data1.csv\",\n" +
            "      \"model_name\": \"model1.py\",\n" +
            "      \"result\": \"32\",\n" +
            "      \"created_at\": \"2022-10-20T04:00:32.000+00:00\"\n" +
            "    }\n" +
            "}";
    public static final String returnInsertResult = "{\n" +
            "   \"success\": true\n" +
            "}" ;
}
