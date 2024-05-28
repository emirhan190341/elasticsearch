package com.emirhanarici.blogapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchDtoRequest {
    private List<String> fieldName;
    private List<String> searchValue;
}
