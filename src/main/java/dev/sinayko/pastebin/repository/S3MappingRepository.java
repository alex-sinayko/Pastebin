package dev.sinayko.pastebin.repository;

import dev.sinayko.pastebin.domain.S3Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface S3MappingRepository extends JpaRepository<S3Mapping, String> {
    List<S3Mapping> findByDateExpirationBefore(LocalDateTime dateTime);
}
