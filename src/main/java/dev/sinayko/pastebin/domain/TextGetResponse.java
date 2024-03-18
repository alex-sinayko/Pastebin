package dev.sinayko.pastebin.domain;

import java.time.LocalDateTime;

public record TextGetResponse (String text, LocalDateTime dateTime) {
}
