package com.example.arena.controller;

import com.example.arena.service.matchmaking.MatchmakingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/arena")
public class ArenaController {

    private final MatchmakingService matchmakingService;


    @PostMapping("/fight")
    public ResponseEntity<String> enterArena(
            @RequestHeader(value = "lvl", required = false) String lvl,
            @RequestHeader(value = "username", required = false) String username) {

        if (lvl == null || username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body("Missing lvl or username header");
        }

        matchmakingService.addToQueue(username, lvl);

        return ResponseEntity.ok("✅ Вы добавлены в очередь, ожидайте соперника...");
    }
}
