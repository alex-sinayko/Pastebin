package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.TextCreateResponse;
import dev.sinayko.pastebin.domain.TextGetResponse;
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

    public TextServiceImpl(S3Service s3Service,
                           UniqIDGeneratorService hgService,
                           @Value("${app.url}") String url) {
        this.s3Service = s3Service;
        this.hgService = hgService;
        this.url = url;
    }

    @Override
    public TextCreateResponse save(String text, Integer days) {
        var uniq = hgService.getId();
        var s3resp = s3Service.storeObject(hgService.getId(), text, days);
        var response = new TextCreateResponse(url + "text/" + uniq, LocalDateTime.now());
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
}
