package com.example.arena.service.matchmaking.impl;

import com.example.arena.model.entity.WaitingUser;
import com.example.arena.repository.WaitingUserRepository;
import com.example.arena.service.matchmaking.MatchmakingService;
import com.example.arena.service.matchmaking.QueueChecke;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueueCheckeImpl implements QueueChecke {

    private final WaitingUserRepository waitingUserRepository;
    private final MatchmakingService matchmakingService;

    @Scheduled(fixedRate = 5000)
    @Override
    public void checkQueue() {
        List<WaitingUser> all = waitingUserRepository.findAll();
        all.forEach(matchmakingService::tryMatch);
    }
}
