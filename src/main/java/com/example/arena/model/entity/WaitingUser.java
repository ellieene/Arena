package com.example.arena.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "waiting_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private int lvl;

    @Column(nullable = false)
    private long joinedAt; // timestamp, для таймаутов
}

