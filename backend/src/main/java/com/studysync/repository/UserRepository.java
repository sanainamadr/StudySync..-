// ============================================================
// FILE: repository/UserRepository.java
// ============================================================
package com.studysync.repository;

import com.studysync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}


// ============================================================
// FILE: repository/TaskRepository.java
// ============================================================
// (Create this as a separate file in the same package)

// package com.studysync.repository;
// import com.studysync.model.Task;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;
// public interface TaskRepository extends JpaRepository<Task, Long> {
//     List<Task> findByUserId(Long userId);
//     List<Task> findByUserIdAndCompleted(Long userId, boolean completed);
// }


// ============================================================
// FILE: repository/NoteRepository.java
// ============================================================
// package com.studysync.repository;
// import com.studysync.model.Note;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;
// public interface NoteRepository extends JpaRepository<Note, Long> {
//     List<Note> findByUserId(Long userId);
// }


// ============================================================
// FILE: repository/StudySessionRepository.java
// ============================================================
// package com.studysync.repository;
// import com.studysync.model.StudySession;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
// import java.util.List;
// public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
//     List<StudySession> findByUserId(Long userId);
//     @Query("SELECT SUM(s.durationMinutes) FROM StudySession s WHERE s.userId = :userId")
//     Integer getTotalMinutesByUserId(Long userId);
// }
