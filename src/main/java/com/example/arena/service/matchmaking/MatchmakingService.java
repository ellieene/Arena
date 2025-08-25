package com.example.arena.service.matchmaking;

import com.example.arena.model.entity.WaitingUser;

public interface MatchmakingService {
    void addToQueue(String username, String lvl);
    void tryMatch(WaitingUser user);
}
