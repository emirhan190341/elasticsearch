package com.emirhanarici.blogapp.controller;

import com.emirhanarici.blogapp.dto.SearchDtoRequest;
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
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        log.info("Request to create blog: {}", blog);

        return ResponseEntity.ok(blogService.save(blog));
    }

    @GetMapping("/getAllDataFromIndex/{indexName}")
    public ResponseEntity<List<Blog>> getAllDataFromIndex(@PathVariable String indexName) {
        log.info("Request to get all data from index: {}", indexName);

        return ResponseEntity.ok(blogService.getAllDataFromIndex(indexName));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Blog>> search(@RequestBody SearchDtoRequest dto) {
        log.info("Request to search blog by field and value: {}", dto);

        return ResponseEntity.ok(blogService.searchBlogByFieldAndValue(dto));
    }

    @GetMapping("/search/{title}/{category}")
    public ResponseEntity<List<Blog>> searchByTitleAndCategory(@PathVariable String title,
                                                               @PathVariable String category) {
        log.info("Request to search blog by title and category: {} {}", title, category);

        return ResponseEntity.ok(blogService.searchBlogByTitleAndCategory(title, category));
    }

    @GetMapping("/boolQuery")
    public ResponseEntity<List<Blog>> boolQuery(@RequestBody SearchDtoRequest dto) {
        log.info("Request to search blog by field and value: {}", dto);

        return ResponseEntity.ok(blogService.boolQuery(dto));
    }


}



