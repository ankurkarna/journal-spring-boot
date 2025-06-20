package com.karna.ankur.Journal.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherResponse {
    private Location location;
    private Current current;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Current {
        @JsonProperty("observation_time")
        private String observationTime;
        private int temperature;
        @JsonProperty("weather_code")
        private long weatherCode;
        @JsonProperty("weather_icons")
        private List<String> weatherIcons;
        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;
        @JsonProperty("air_quality")
        private AirQuality airQuality;
        @JsonProperty("wind_speed")
        private long windSpeed;
        @JsonProperty("wind_degree")
        private long windDegree;
        @JsonProperty("wind_dir")
        private String windDir;
        private long pressure;
        private long precip;
        private long humidity;
        private long cloudcover;
        private int feelslike;
        @JsonProperty("uv_index")
        private long uvIndex;
        private long visibility;
        @JsonProperty("is_day")
        private String isDay;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AirQuality {
        private String co;
        private String no2;
        private String o3;
        private String so2;
        @JsonProperty("pm2_5")
        private String pm25;
        @JsonProperty("pm10")
        private String pm10;
        @JsonProperty("us-epa-index")
        private String usEpaIndex;
        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Location {
        private String name;
        private String country;
        private String region;
        private String lat;
        private String lon;
        @JsonProperty("timezone_id")
        private String timezoneID;
        private String localtime;
        @JsonProperty("localtime_epoch")
        private long localtimeEpoch;
        @JsonProperty("utc_offset")
        private String utcOffset;
    }
}
//
//import lombok.*;
//import org.springframework.context.annotation.Bean;
//
//import java.util.List;
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Data
//public class WeatherResponse {
//
//    private Location location;
//    private Current current;
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Data
//    public class Current {
//        private String observationTime;
//        private int temperature;
//        private long weatherCode;
//        private List<String> weatherIcons;
//        private List<String> weatherDescriptions;
//        private AirQuality airQuality;
//        private long windSpeed;
//        private long windDegree;
//        private String windDir;
//        private long pressure;
//        private long precip;
//        private long humidity;
//        private long cloudcover;
//        private int feelslike;
//        private long uvIndex;
//        private long visibility;
//        private String isDay;
//    }
//
//    public class AirQuality {
//        private String co;
//        private String no2;
//        private String o3;
//        private String so2;
//        private String pm25;
//        private String pm10;
//        private String usEpaIndex;
//        private String gbDefraIndex;
//
//    }
//
//
//
//    public class Location {
//        private String name;
//        private String country;
//        private String region;
//        private String lat;
//        private String lon;
//        private String timezoneID;
//        private String localtime;
//        private long localtimeEpoch;
//        private String utcOffset;
//    }
//
//
//
//}
