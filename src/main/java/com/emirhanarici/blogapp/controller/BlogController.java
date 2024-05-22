package com.emirhanarici.blogapp.controller;

import com.emirhanarici.blogapp.model.Blog;
import com.emirhanarici.blogapp.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
@Slf4j
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody  Blog blog){
        log.info("Request to create blog: {}", blog);

        return ResponseEntity.ok(blogService.save(blog));
    }

    @GetMapping("/getAllDataFromIndex/{indexName}")
    public ResponseEntity<List<Blog>> getAllDataFromIndex(@PathVariable String indexName){
        log.info("Request to get all data from index: {}", indexName);

        return ResponseEntity.ok(blogService.getAllDataFromIndex(indexName));
    }

}



