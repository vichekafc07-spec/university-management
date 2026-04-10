package com.ume.studentsystem.util;

import org.springframework.data.domain.Sort;

import java.util.List;

public class SortResponse {
    public static Sort sortResponse(String sortBy, String sortAs, List<String> allowSort) {
        Sort sort = Sort.by(Sort.Order.desc("id"));
        if (sortBy != null && sortAs != null && allowSort.contains(sortBy)) {
            sort = sortAs.equalsIgnoreCase("desc") ?
                    Sort.by(Sort.Order.desc(sortBy)) :
                    Sort.by(Sort.Order.asc(sortBy));
        }
        return sort;
    }
}
