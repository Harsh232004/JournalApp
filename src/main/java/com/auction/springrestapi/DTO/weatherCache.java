package com.auction.springrestapi.DTO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "weather_cache")
public class weatherCache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "cache_key",unique = true ,nullable = false)
    private String Key;

    @Column(name = "cache_value", nullable = false)
    private String Value;
}
