package com.springboot.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DistanceResponse {
    private String distance;
    private String unit;

    // Getters and Setters
    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    // Static method to parse JSON
    public static DistanceResponse fromJson(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, DistanceResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing DistanceResponse JSON: " + e.getMessage(), e);
        }
    }
}
