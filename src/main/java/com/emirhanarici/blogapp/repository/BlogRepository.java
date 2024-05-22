package com.emirhanarici.blogapp.repository;

import com.emirhanarici.blogapp.model.Blog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BlogRepository extends ElasticsearchRepository<Blog, Long>{
}
