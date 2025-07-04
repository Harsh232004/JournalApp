package com.auction.springrestapi.enums;

import lombok.Getter;

@Getter
public enum Keys{
    WEATHER_BASE_URL("weather.base.url"),
    WEATHER_ENDPOINT_TEMPLATE("weather.endpoint.template");

    private final String key;

    Keys(String key) {
        this.key = key;
    }

}
