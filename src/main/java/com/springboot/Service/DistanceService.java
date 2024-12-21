package com.springboot.Service;

import com.springboot.Response.DistanceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class DistanceService {

    @Value("${api.distance.url}")
    private String distanceApiUrl; // API URL for the Distance Calculator

    @Value("${api.rapid.key}")
    private String apiKey; // API Key for RapidAPI

    @Value("${api.rapid.host}")
    private String apiHost; // API Host for RapidAPI

    public DistanceResponse calculateDistance(double startLatitude, double startLongitude,
                                              double endLatitude, double endLongitude) {
        try {
            // Construct the API URL with query parameters
            String url = String.format("%s/v1/getdistance?start_lat=%f&start_lng=%f&end_lat=%f&end_lng=%f&unit=kilometers",
                    distanceApiUrl, startLatitude, startLongitude, endLatitude, endLongitude);

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("x-rapidapi-key", apiKey)
                    .header("x-rapidapi-host", apiHost)
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Check the response status and parse the body
            if (response.statusCode() == 200) {
                // Assuming DistanceResponse has a static method to parse JSON
                return DistanceResponse.fromJson(response.body());
            } else {
                throw new RuntimeException("Failed to calculate distance. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            // Log the exception (could use a logging framework like SLF4J)
            System.err.println("Error while calling the distance API: " + e.getMessage());
            throw new RuntimeException("Failed to calculate distance", e);
        }
    }
}
