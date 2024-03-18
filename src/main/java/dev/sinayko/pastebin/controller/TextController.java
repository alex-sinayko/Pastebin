package dev.sinayko.pastebin.controller;

import dev.sinayko.pastebin.domain.TextCreateRequest;
import dev.sinayko.pastebin.domain.TextCreateResponse;
import dev.sinayko.pastebin.domain.TextGetResponse;
import dev.sinayko.pastebin.service.TextService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

@RestController
@AllArgsConstructor
@RequestMapping("/text")
public class TextController {

    private TextService textService;

    @PostMapping
    public ResponseEntity<TextCreateResponse> create(@RequestBody TextCreateRequest req) {
        return ResponseEntity.ok(textService.save(req.text(), req.days()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TextGetResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(textService.get(id));
    }

    @ExceptionHandler({ NoSuchKeyException.class})
    public ResponseEntity handleNotFoundException() {
        return ResponseEntity.notFound().build();
    }
}
