package com.ume.studentsystem.spec;

import org.springframework.data.jpa.domain.Specification;

public class SpecificationBuilder<T> {

    private Specification<T> spec = Specification.unrestricted();

    public SpecificationBuilder<T> equal(String field, Object value) {
        if (value != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get(field), value));
        }
        return this;
    }


    public SpecificationBuilder<T> like(String field, String value) {
        if (value != null && !value.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
        }
        return this;
    }

    public SpecificationBuilder<T> greaterThanOrEqual(String field, Comparable value) {
        if (value != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get(field), value));
        }
        return this;
    }

    public SpecificationBuilder<T> lessThanOrEqual(String field, Comparable value) {
        if (value != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get(field), value));
        }
        return this;
    }

    public SpecificationBuilder<T> between(String field, Comparable start, Comparable end) {
        if (start != null && end != null) {
            spec = spec.and((root, query, cb) ->
                    cb.between(root.get(field), start, end));
        } else if (start != null) {
            greaterThanOrEqual(field, start);
        } else if (end != null) {
            lessThanOrEqual(field, end);
        }
        return this;
    }

    public Specification<T> build() {
        return spec;
    }
}