package com.studysync.repository;

import com.studysync.model.StudySession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
    List<StudySession> findByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM StudySession s WHERE s.userId = :userId")
    Integer getTotalMinutesByUserId(@Param("userId") Long userId);

    @Query("SELECT s FROM StudySession s WHERE s.userId = :userId ORDER BY s.studyDate DESC")
    List<StudySession> findRecentByUserId(@Param("userId") Long userId);
}
