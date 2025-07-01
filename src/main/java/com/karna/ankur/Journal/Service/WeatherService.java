package com.karna.ankur.Journal.Service;

import com.karna.ankur.Journal.api.response.WeatherResponse;
import com.karna.ankur.Journal.cache.AppCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;
//    private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    AppCache appCache;
    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        String cacheKey = "weather_of_" + city;
        log.info("Using cache key: '{}'", cacheKey);
        WeatherResponse weatherResponse = redisService.get(cacheKey, WeatherResponse.class);

        // WeatherResponse cachedResponse = redisService.get(cacheKey, WeatherResponse.class);
        if (weatherResponse != null) {
            log.info("Cache HIT for key: {}", cacheKey);
            return weatherResponse;
        }
        log.info("Cache MISS for key: {}", cacheKey);

        if (weatherResponse != null) {
            log.info("Returning cached weather for {}", city);
            return weatherResponse;
        } else {
            log.info("Cache miss for {}, fetching from API", city);
            String finalAPI = appCache.appCache.get("weather_api")
                    .replace("<city>", city)
                    .replace("<api_key>", apiKey);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                    finalAPI, HttpMethod.GET, null, WeatherResponse.class);

            WeatherResponse body = response.getBody();
            if (body != null) {
                log.info("Caching weather data for {}", city);
                redisService.set(cacheKey, body, 604800L);
            }
            return body;
        }
    }

//    public WeatherResponse getWeather(String city) {
//        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
//        if (weatherResponse != null) {
//            return weatherResponse;
//        } else {
//            String finalAPI = appCache.appCache.get("weather_api").replace("<city>", city).replace("<api_key>", apiKey);
//            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WeatherResponse.class);
//            WeatherResponse body = response.getBody();
//            if (body != null) {
//                redisService.set("weather_of_" + city, body, 300L);
//            }
//            return body;
//
//        }
//    }
}
