package com.example.arena.controller;


import com.example.arena.model.request.CodeSubmission;
import com.example.arena.service.fight.impl.FightServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FightController {

    private final FightServiceImpl fightService;

    @MessageMapping("/submit") // фронт отправляет на /app/submit
    public void submitCode(CodeSubmission submission) {
        fightService.processSubmission(submission);
    }
}
