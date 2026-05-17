package com.studysync.controller;

import com.studysync.model.StudySession;
import com.studysync.model.User;
import com.studysync.repository.StudySessionRepository;
import com.studysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sessions")
public class StudySessionController {

    @Autowired
    private StudySessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    // Log a new study session (called when Pomodoro completes)
    @PostMapping
    public ResponseEntity<Map<String, Object>> logSession(@RequestBody StudySession session) {
        sessionRepository.save(session);

        // Update streak for the user
        Optional<User> userOpt = userRepository.findById(session.getUserId());
        Map<String, Object> response = new HashMap<>();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            LocalDate today = LocalDate.now();
            LocalDate lastDate = user.getLastStudyDate();

            if (lastDate == null || lastDate.isBefore(today.minusDays(1))) {
                // Streak broken or first time — reset to 1
                user.setStreakCount(1);
            } else if (lastDate.isEqual(today.minusDays(1))) {
                // Studied yesterday — increment streak
                user.setStreakCount(user.getStreakCount() + 1);
            }
            // If already studied today, don't change streak

            user.setLastStudyDate(today);
            userRepository.save(user);

            response.put("streakCount", user.getStreakCount());
        }

        response.put("success", true);
        response.put("message", "Session logged");
        return ResponseEntity.ok(response);
    }

    // Get all sessions for a user
    @GetMapping("/{userId}")
    public List<StudySession> getSessions(@PathVariable Long userId) {
        return sessionRepository.findByUserId(userId);
    }

    // Get total study minutes for a user
    @GetMapping("/{userId}/total")
    public ResponseEntity<Map<String, Object>> getTotalMinutes(@PathVariable Long userId) {
        Integer total = sessionRepository.getTotalMinutesByUserId(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("totalMinutes", total != null ? total : 0);
        response.put("totalHours", total != null ? Math.round(total / 60.0 * 10.0) / 10.0 : 0);
        return ResponseEntity.ok(response);
    }
}
