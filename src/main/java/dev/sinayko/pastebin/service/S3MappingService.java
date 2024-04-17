package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.S3Mapping;

public interface S3MappingService {
    S3Mapping getById(String id);
    S3Mapping save(S3Mapping s3m);
    void delete(String id);
}
