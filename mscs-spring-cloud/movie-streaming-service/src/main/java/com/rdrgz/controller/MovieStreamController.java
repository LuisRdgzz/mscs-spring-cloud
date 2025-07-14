package com.rdrgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
@RestController
public class MovieStreamController {

    private static final Logger log = Logger.getLogger(MovieStreamController.class.getName());
    private static final String VIDEO_DIRECTORY = "D:\\Stream\\";

    @Autowired
    private MovieCatalogService movieCatalogService;

    @GetMapping("/stream/{videoPath}")
    /*
     * {videoPath} is the path segment containing the exact filename of the video to stream.
     * The @PathVariable String videoPath parameter captures this path from the request.
     * This endpoint reads the video file from disk and returns it as a stream.
     */
    public ResponseEntity<InputStreamResource> streamVideo(@PathVariable String videoPath) throws FileNotFoundException {
        File file = new File(VIDEO_DIRECTORY + videoPath);
        if (file.exists()) {
            InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("video/mp4"))
                    .body(inputStreamResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stream/with-id/{videoInfoId}")
    /*
     * This endpoint allows clients to stream a video by its ID.
     * It resolves the video path by calling the movie-catalog-service,
     * then streams the corresponding video file.
     */
    public ResponseEntity<InputStreamResource> streamVideoById(@PathVariable Long videoInfoId) throws FileNotFoundException {
        String moviePath = movieCatalogService.getMoviePath(videoInfoId);
        log.log(Level.INFO, "Resolved movie path = {0}", moviePath);
        return streamVideo(moviePath);
    }
}
