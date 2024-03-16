package dev.sinayko.pastebin.service;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

public interface S3Service {
    ResponseInputStream<GetObjectResponse> getObject(String keyName);
    PutObjectResponse storeObject(String keyName, String content, int days);
}
