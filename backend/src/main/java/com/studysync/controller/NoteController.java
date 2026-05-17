package com.studysync.controller;

import com.studysync.model.Note;
import com.studysync.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    // Get all notes for a user
    @GetMapping("/{userId}")
    public List<Note> getNotes(@PathVariable Long userId) {
        return noteRepository.findByUserId(userId);
    }

    // Add a new note
    @PostMapping
    public Note addNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    // Update a note
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Optional<Note> noteOpt = noteRepository.findById(id);
        if (noteOpt.isEmpty()) return ResponseEntity.notFound().build();

        Note note = noteOpt.get();
        if (updatedNote.getTitle() != null) note.setTitle(updatedNote.getTitle());
        if (updatedNote.getContent() != null) note.setContent(updatedNote.getContent());
        note.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.ok(noteRepository.save(note));
    }

    // Delete a note
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
