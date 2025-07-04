package com.auction.springrestapi.Service;

import com.auction.springrestapi.Api.WeatherAPI;
import com.auction.springrestapi.cache.AppCache;
import com.auction.springrestapi.enums.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String API_KEY;

    private final RestTemplate restTemplate;
    private final AppCache appCache;
    private final RedisService redisService;

    @Autowired
    public WeatherService(RestTemplate restTemplate, AppCache appCache, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.appCache = appCache;
        this.redisService = redisService;
    }

    public WeatherAPI getWeather(String city) {
        String baseUrl = appCache.getValue(Keys.WEATHER_BASE_URL);
        String endpointTemplate = appCache.getValue(Keys.WEATHER_ENDPOINT_TEMPLATE);

        if (baseUrl == null || endpointTemplate == null) {
            throw new IllegalStateException("Weather configuration not found in database");
        }

        // Try to get from Redis cache first
        WeatherAPI weatherAPI = redisService.get("weather_of_" + city, WeatherAPI.class);
        if (weatherAPI != null) {
            return weatherAPI;
        }

        // Construct the full URL
        String finalAPI = baseUrl + String.format(endpointTemplate, API_KEY, city);
        System.out.println("Constructed URL: " + finalAPI);

        // Make the API call
        ResponseEntity<WeatherAPI> response = restTemplate.exchange(
                finalAPI,
                HttpMethod.GET,
                null,
                WeatherAPI.class
        );

        // Save response body to Redis
        if (response.getBody() != null) {
            redisService.set("weather_of_" + city, response.getBody(), 300L); // cache for 5 mins
        }

        return response.getBody();
    }
}
