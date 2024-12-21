package com.springboot.Service;

import com.springboot.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    private final RestTemplate restTemplate;

    @Value("${api.weather.url}")
    private String weatherApiUrl;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(double latitude, double longitude) {
        String url = String.format("%s?lat=%f&lon=%f&appid=%s&units=metric",
                weatherApiUrl, latitude, longitude, apiKey);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
