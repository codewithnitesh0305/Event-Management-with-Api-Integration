package com.springboot.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("main")
    private Main main;

    @JsonProperty("weather")
    private List<Weather> weather;

    // Getter for main
    public Main getMain() {
        return main;
    }

    // Setter for main
    public void setMain(Main main) {
        this.main = main;
    }

    // Getter for weather (specifically for description)
    public List<Weather> getWeather() {
        return weather;
    }

    // Setter for weather
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public static class Main {
        @JsonProperty("temp")
        private double temp;

        // Getter and Setter for temp
        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    public static class Weather {
        @JsonProperty("description")
        private String description;

        // Getter and Setter for description
        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
