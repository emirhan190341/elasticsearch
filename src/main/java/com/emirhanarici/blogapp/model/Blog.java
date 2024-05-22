package com.emirhanarici.blogapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="blogs_index")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Blog {

    @Id
    private Long id;
    private String title;
    private String content;
    private String category;
    private String authorName;
    private String authorAvatar;
    private String cover;

}
