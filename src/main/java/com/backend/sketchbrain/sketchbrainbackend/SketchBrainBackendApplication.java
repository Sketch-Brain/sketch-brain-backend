package com.backend.sketchbrain.sketchbrainbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SketchBrainBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SketchBrainBackendApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000");
                registry.addMapping("/**").allowedOrigins("http://localhost:8080");
                registry.addMapping("/**").allowedOrigins("http://203.252.166.213:30801");
            }
        };
    }
}
