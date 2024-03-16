package dev.sinayko.pastebin.service;

import com.adobe.testing.s3mock.testcontainers.S3MockContainer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.utils.AttributeMap;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.temporal.ChronoUnit;

import static software.amazon.awssdk.http.SdkHttpConfigurationOption.TRUST_ALL_CERTIFICATES;

@Testcontainers
public class S3ServiceImplTest {

    @Container
    private final S3MockContainer s3Mock = new S3MockContainer("latest");
    private S3Client s3Client;

    @BeforeEach
    void setUp() {
        var endpoint = s3Mock.getHttpsEndpoint();
        var serviceConfig = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .build();
        var httpClient = UrlConnectionHttpClient.builder()
                .buildWithDefaults(AttributeMap.builder()
                        .put(TRUST_ALL_CERTIFICATES, Boolean.TRUE)
                        .build());
        s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .serviceConfiguration(serviceConfig)
                .httpClient(httpClient)
                .build();
        System.setProperty("cloud.aws.bucket", "il-central-1");
    }

    @Test
    public void testStore() {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket("test")
                .build();

        s3Client.createBucket(bucketRequest);
        var s3Service = new S3ServiceImpl(s3Client, "test");
        var resp = s3Service.storeObject("key1", "content1", 30);
        Assertions.assertEquals(200, resp.sdkHttpResponse().statusCode());


    }

    @Test
    public void testGet() throws IOException {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket("test")
                .build();

        s3Client.createBucket(bucketRequest);
        var s3Service = new S3ServiceImpl(s3Client, "test");
        s3Service.storeObject("key1", "content1", 30);
        var response = s3Service.getObject("key1");

        Assertions.assertEquals("content1", StreamUtils.copyToString(response, StandardCharsets.UTF_8));

    }

    @Test
    public void testExpireDate() {
        CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                .bucket("test")
                .build();

        s3Client.createBucket(bucketRequest);
        var s3Service = new S3ServiceImpl(s3Client, "test");
        s3Service.storeObject("key1", "content1", 30);
        var response = s3Service.getObject("key1");

        Assertions.assertEquals(response.response().lastModified()
                        .plus(30, ChronoUnit.DAYS), response.response().expires());

    }

}
