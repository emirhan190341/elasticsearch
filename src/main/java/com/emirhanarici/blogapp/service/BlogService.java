package com.emirhanarici.blogapp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.emirhanarici.blogapp.model.Blog;
import com.emirhanarici.blogapp.repository.BlogRepository;
import com.emirhanarici.blogapp.util.ESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final BlogRepository blogRepository;
    private final ElasticsearchClient elasticsearchClient;


    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllDataFromIndex(String indexName) {

        try {
            var supplier = ESUtil.createMatchAllQuery();
            log.info("Elasticsearch query {}", supplier.toString());

            SearchResponse<Blog> response = elasticsearchClient.search(
                    q -> q.index(indexName).query(supplier), Blog.class);

            log.info("Elasticsearch response {}", response.toString());

            return extractItemsFromResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Blog> extractItemsFromResponse(SearchResponse<Blog> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }


}
