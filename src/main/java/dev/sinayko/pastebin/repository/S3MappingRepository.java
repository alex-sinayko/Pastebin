package dev.sinayko.pastebin.repository;

import dev.sinayko.pastebin.domain.S3Mapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3MappingRepository extends JpaRepository<S3Mapping, String> {

}
