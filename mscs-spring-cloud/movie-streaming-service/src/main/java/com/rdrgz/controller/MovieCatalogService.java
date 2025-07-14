package com.rdrgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MovieCatalogService {
    private static final String CATALOG_SERVICE = "http://movie-catalog-service";

    @Autowired
    private RestTemplate restTemplate;

    public String getMoviePath(Long movieInfoId){
        var response = restTemplate.
                /* This line calls the endpoint defined in MovieInfoController */
                        getForEntity(
                        CATALOG_SERVICE + "/movie-info/find-path-by-id/{movieInfoId}",
                        // This URL is used to connect to the movie-catalog-service
                        String.class, movieInfoId
                );
        return response.getBody();
    }

}
