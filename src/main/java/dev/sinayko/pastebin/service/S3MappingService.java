package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.S3Mapping;

import java.time.LocalDateTime;
import java.util.List;

public interface S3MappingService {
    S3Mapping getById(String id);
    S3Mapping save(S3Mapping s3m);
    void delete(String id);

    List<S3Mapping> findByExpDateBefore(LocalDateTime dateTime);
    void deleteBulk(List<String> list);
}
