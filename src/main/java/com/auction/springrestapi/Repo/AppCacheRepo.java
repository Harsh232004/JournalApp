package com.auction.springrestapi.Repo;

import com.auction.springrestapi.DTO.weatherCache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCacheRepo extends JpaRepository<weatherCache, Integer> {

}
