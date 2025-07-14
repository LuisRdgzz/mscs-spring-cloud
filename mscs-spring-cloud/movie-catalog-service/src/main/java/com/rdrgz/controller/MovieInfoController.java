package com.rdrgz.controller;

import com.rdrgz.model.MovieInfo;
import com.rdrgz.repository.MovieInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieInfoController {
    @Autowired
    private MovieInfoRepository repository;

    @PostMapping("/movie-info/save")
    public List<MovieInfo> saveAll(@RequestBody List<MovieInfo> movieInfoList){
        return repository.saveAll(movieInfoList);
    }

    @GetMapping("/movie-info/list")
    public List<MovieInfo> getAll(){
        return repository.findAll();
    }

    /*
     * To enable communication between microservices, we'll create this additional endpoint.
     * The movie-streaming-service will call this endpoint to retrieve data.
     * This represents service-to-service communication.
     * A load balancer can be used to call the other service dynamically.
     * The movie-streaming-service will use this endpoint to perform that communication.
     */
    @GetMapping("/movie-info/find-path-by-id/{movieInfoId}")
    public String findPathById(@PathVariable Long movieInfoId){
        var videoInfoOptional = repository.findById(movieInfoId);
        return videoInfoOptional.map(MovieInfo::getPath).orElse(null);
    }
}
