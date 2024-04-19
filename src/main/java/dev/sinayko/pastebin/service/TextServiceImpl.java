package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.S3Mapping;
import dev.sinayko.pastebin.domain.TextCreateResponse;
import dev.sinayko.pastebin.domain.TextGetResponse;
import dev.sinayko.pastebin.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.*;

@Slf4j
@Service
public class TextServiceImpl implements TextService {

    private S3Service s3Service;
    private UniqIDGeneratorService hgService;
    private String url;
    private S3MappingService s3MappingService;

    public TextServiceImpl(S3Service s3Service,
                           UniqIDGeneratorService hgService,
                           @Value("${app.url}") String url,
                           S3MappingService s3MappingService) {
        this.s3Service = s3Service;
        this.hgService = hgService;
        this.url = url;
        this.s3MappingService = s3MappingService;
    }

    @Override
    public TextCreateResponse save(String text, Integer days) {
        var uniq = hgService.getId();
        s3Service.storeObject(uniq, text, days);
        saveS3Mapping(uniq, days, Instant.now());
        var expDate = convertDate(s3Service.getObject(uniq).response().expires());
        var response = new TextCreateResponse(url + "text/" + uniq, expDate);
        return response;
    }

    @Override
    public String change(String id, String text, Integer days) {
        return null;
    }

    @Override
    public TextGetResponse get(String id) {
        var s3resp = s3Service.getObject(id);
        String value;
        try {
            value = StreamUtils.copyToString(s3resp, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return new TextGetResponse(value, convertDate(s3resp.response().expires()));
    }

    @Override
    public void delete(String id) {

    }

    private LocalDateTime convertDate(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());
    }

    private S3Mapping saveS3Mapping(String uniq, int days, Instant createDate) {
        var s3m = new S3Mapping();
        var currentDateTime = LocalDateTime.now();
        s3m.setId(uniq);
        s3m.setDateCreated(currentDateTime);
        s3m.setDateExpiration(currentDateTime.plusDays(days));
        s3m.setUser(User.defaultUser());
        return s3MappingService.save(s3m);

    }
}
