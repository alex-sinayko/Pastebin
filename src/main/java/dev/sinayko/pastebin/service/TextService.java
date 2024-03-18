package dev.sinayko.pastebin.service;

import dev.sinayko.pastebin.domain.TextCreateResponse;
import dev.sinayko.pastebin.domain.TextGetResponse;

public interface TextService {
    TextCreateResponse save(String text, Integer days);
    String change(String id, String text, Integer days);
    TextGetResponse get(String id);
    void delete(String id);
}
