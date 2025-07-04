package com.auction.springrestapi.Api;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WeatherAPI {

        private Current current;

        @Getter
        @Setter
        public static class Current {

            @JsonProperty("temp_c")
            private int temperature;

            @JsonProperty("is_day")
            private float IsDay;
        }

        /*public class Location {
            private String name;
            private String region;
            private String country;

        }*/
    }

