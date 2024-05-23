package com.emirhanarici.blogapp.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.emirhanarici.blogapp.dto.SearchDtoRequest;
import com.emirhanarici.blogapp.model.Blog;
import com.emirhanarici.blogapp.repository.BlogRepository;
import com.emirhanarici.blogapp.util.ESUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
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


    public List<Blog> searchBlogByFieldAndValue(SearchDtoRequest dto) {
        SearchResponse<Blog> response = null;
        try {
            //Create a query for the field and value
            Supplier<Query> querySupplier = ESUtil.buildQueryForFieldAndValue(dto.getFieldName().get(0),
                    dto.getSearchValue().get(0));

            log.info("Elasticsearch query {}", querySupplier);

            //Search the query and get the response
            //Use the querySupplier to get the query and pass it to the search method
            response = elasticsearchClient.search(q -> q.index("blogs_index")
                    .query(querySupplier.get()), Blog.class);//sorguyu calistir ve cevabi alir

            log.info("Elasticsearch response: {}", response.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return extractItemsFromResponse(response);

    }


    public List<Blog> searchBlogByTitleAndCategory(String title, String category) {
        return blogRepository.searchByTitleAndCategory(title, category);
    }

    public List<Blog> boolQuery(SearchDtoRequest dto) {
        try {
            var supplier = ESUtil.createBoolQuery(dto);

            log.info("Elasticsearch query: " + supplier.get().toString());

            SearchResponse<Blog> response = elasticsearchClient.search(q ->
                    q.index("blogs_index").query(supplier.get()), Blog.class);

            log.info("Elasticsearch response: {}", response.toString());

            return extractItemsFromResponse(response);
        } catch (IOException e) {
            return Collections.emptyList();
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
