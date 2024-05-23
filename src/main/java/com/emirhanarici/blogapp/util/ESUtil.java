package com.emirhanarici.blogapp.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.util.ObjectBuilder;
import com.emirhanarici.blogapp.dto.SearchDtoRequest;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class ESUtil {


    public static Query createMatchAllQuery() {
        return Query.of(q -> q.matchAll(new MatchAllQuery.Builder().build()));
    }


    public static Supplier<Query> buildQueryForFieldAndValue(String fieldName, String searchValue) {
        return () -> Query.of(q -> q.match(buildMatchQueryForFieldAndValue(fieldName, searchValue)));
    }

    private static MatchQuery buildMatchQueryForFieldAndValue(String fieldName, String searchValue) {
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchValue)
                .build();
    }


    public static Supplier<Query> createBoolQuery(SearchDtoRequest dto) {
        return () -> Query.of(q -> q.bool(boolQuery(dto.getFieldName().get(0), dto.getSearchValue().get(0),
                dto.getFieldName().get(1), dto.getSearchValue().get(1))));
    }

    private static BoolQuery boolQuery(String key1, String value1, String key2, String value2) {
        return new BoolQuery.Builder()
                .filter(termQuery(key1, value1))
                .must(matchQuery(key2, value2))
                .build();
    }

    //Birebir eşleşme
    private static Query termQuery(String key, String value) {
        return Query.of(q -> q.term(new TermQuery.Builder().field(key).value(value).build()));
    }

    //Arama yapma
    private static Query matchQuery(String key, String value) {
        return Query.of(q -> q.match(new MatchQuery.Builder().field(key).query(value).build()));
    }


}
