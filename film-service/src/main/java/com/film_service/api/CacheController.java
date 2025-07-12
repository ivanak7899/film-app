package com.film_service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @DeleteMapping("/film/{filmId}")
    public ResponseEntity<Void> evictRatingCache(@PathVariable Long filmId) {
        cacheManager.getCache("filmRatings").evict(filmId);
        return ResponseEntity.noContent().build();
    }
}