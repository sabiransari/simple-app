package com.simple.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/cart")
public class ShoppingCartController {
    @PostMapping("/{cartId}/add")
    public ResponseEntity.BodyBuilder addToCart(@PathVariable Long cartId) {
      log.info("item added to cart {}", cartId);
      return ResponseEntity.ok();
    }

    @PutMapping("/{cartId}/remove/{itemId}")
    public ResponseEntity.BodyBuilder removeFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        log.info("item {} removed from cart {}", itemId, cartId);
        return ResponseEntity.ok();
    }
}
