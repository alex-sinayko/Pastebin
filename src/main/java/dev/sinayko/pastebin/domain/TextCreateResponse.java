package dev.sinayko.pastebin.domain;

import java.time.LocalDateTime;

public record TextCreateResponse (String url, LocalDateTime dateExp) {
}
