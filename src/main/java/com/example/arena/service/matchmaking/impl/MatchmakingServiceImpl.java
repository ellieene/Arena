package com.example.arena.service.matchmaking.impl;

import com.example.arena.model.entity.Fight;
import com.example.arena.model.entity.WaitingUser;
import com.example.arena.repository.FightRepository;
import com.example.arena.repository.WaitingUserRepository;
import com.example.arena.service.matchmaking.MatchmakingService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MatchmakingServiceImpl implements MatchmakingService {

    private final WaitingUserRepository waitingUserRepository;
    private final FightRepository fightRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private static final long TIMEOUT = 60_000; // 60 сек
    private static final long EXPAND_RANGE_AFTER = 30_000; // через 30 сек

    @Transactional
    @Override
    public void addToQueue(String username, String lvl) {
        long now = System.currentTimeMillis();

        waitingUserRepository.findByUsername(username)
                .ifPresent(waitingUserRepository::delete);

        WaitingUser waitingUser = new WaitingUser();
        waitingUser.setUsername(username);
        waitingUser.setLvl(Integer.parseInt(lvl));
        waitingUser.setJoinedAt(now);

        waitingUserRepository.save(waitingUser);

        tryMatch(waitingUser);
    }

    @Transactional
    @Override
    public void tryMatch(WaitingUser user) {
        long now = System.currentTimeMillis();
        long waitTime = now - user.getJoinedAt();

        int range = (waitTime > EXPAND_RANGE_AFTER) ? 1 : 0;

        if (waitTime > TIMEOUT) {
            waitingUserRepository.delete(user);
            messagingTemplate.convertAndSendToUser(
                    user.getUsername(),
                    "/queue/fight",
                    "❌ Противник не найден, попробуйте позже."
            );
            return;
        }

        int minLvl = user.getLvl() - range;
        int maxLvl = user.getLvl() + range;

        List<WaitingUser> candidates = waitingUserRepository.findCandidates(minLvl, maxLvl);
        candidates.removeIf(c -> c.getUsername().equals(user.getUsername()));

        if (!candidates.isEmpty()) {
            WaitingUser opponent = candidates.get(0);

            waitingUserRepository.delete(user);
            waitingUserRepository.delete(opponent);

            Fight fight = new Fight();
            fight.setPlayersOne(user.getUsername());
            fight.setPlayersTwo(opponent.getUsername());
            fight.setExercise(generateExercise());

            fightRepository.save(fight);

            messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/fight", fight);
            messagingTemplate.convertAndSendToUser(opponent.getUsername(), "/queue/fight", fight);
        }
    }
    private String generateExercise() {
        List<String> exercises = List.of("Push-ups", "Squats", "Sit-ups");
        int index = new Random().nextInt(exercises.size());
        return exercises.get(index);
    }
}
