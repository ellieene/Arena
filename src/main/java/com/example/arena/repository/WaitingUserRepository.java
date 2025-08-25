package com.example.arena.repository;

import com.example.arena.model.entity.WaitingUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WaitingUserRepository extends JpaRepository<WaitingUser, UUID> {

    Optional<WaitingUser> findByUsername(String username);

    @Query("SELECT w FROM WaitingUser w WHERE w.lvl BETWEEN :min AND :max ORDER BY w.joinedAt ASC")
    List<WaitingUser> findCandidates(@Param("min") int min, @Param("max") int max);
}
