package dev.sinayko.pastebin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
public class S3ServiceImpl implements S3Service {

    private String s3Bucket;
    private final S3Client s3Client;

    S3ServiceImpl(S3Client s3Client, @Value("${cloud.aws.bucket}") String s3Bucket) {
        this.s3Client = s3Client;
        this.s3Bucket = s3Bucket;
    }
    @Override
    public ResponseInputStream<GetObjectResponse> getObject(String keyName) {
        return  s3Client.getObject(req-> req.bucket(s3Bucket).key(keyName));
    }

    @Override
    public PutObjectResponse storeObject(String keyName, String content, int days) {
        return s3Client.putObject(PutObjectRequest.builder()
                .bucket(s3Bucket)
                .key(keyName)
                .expires(Instant.now().plus(days, ChronoUnit.DAYS))
                .build(),
                RequestBody.fromBytes(content.getBytes(StandardCharsets.UTF_8)));
    }
}
