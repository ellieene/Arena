package com.example.arena.repository;

import com.example.arena.model.entity.Fight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FightRepository extends JpaRepository<Fight, UUID> {
}
