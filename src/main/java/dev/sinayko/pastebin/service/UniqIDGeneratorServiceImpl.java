package dev.sinayko.pastebin.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UniqIDGeneratorServiceImpl implements UniqIDGeneratorService {
    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }
}
