package com.karna.ankur.Journal.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WeatherResponse {

    private Location location;
    private Current current;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Current {
        private String observationTime;
        private int temperature;
        private long weatherCode;
        private List<String> weatherIcons;
        private List<String> weatherDescriptions;
        private AirQuality airQuality;
        private long windSpeed;
        private long windDegree;
        private String windDir;
        private long pressure;
        private long precip;
        private long humidity;
        private long cloudcover;
        private int feelslike;
        private long uvIndex;
        private long visibility;
        private String isDay;
    }

    public class AirQuality {
        private String co;
        private String no2;
        private String o3;
        private String so2;
        private String pm25;
        private String pm10;
        private String usEpaIndex;
        private String gbDefraIndex;

    }



    public class Location {
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        private String timezoneID;
        private String localtime;
        private long localtimeEpoch;
        private String utcOffset;
    }



}
