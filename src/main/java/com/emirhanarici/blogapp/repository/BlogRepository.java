package com.emirhanarici.blogapp.repository;

import com.emirhanarici.blogapp.model.Blog;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BlogRepository extends ElasticsearchRepository<Blog, Long> {

    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"category\": \"?1\"}}]}}")
    List<Blog> searchByTitleAndCategory(String title, String category);


}
