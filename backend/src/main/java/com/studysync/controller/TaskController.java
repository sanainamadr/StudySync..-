package com.studysync.controller;

import com.studysync.model.Task;
import com.studysync.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Get all tasks for a user
    @GetMapping("/{userId}")
    public List<Task> getTasks(@PathVariable Long userId) {
        return taskRepository.findByUserId(userId);
    }

    // Add a new task
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    // Update a task (edit title/description or mark complete)
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) return ResponseEntity.notFound().build();

        Task task = taskOpt.get();
        if (updatedTask.getTitle() != null) task.setTitle(updatedTask.getTitle());
        if (updatedTask.getDescription() != null) task.setDescription(updatedTask.getDescription());
        if (updatedTask.getPriority() != null) task.setPriority(updatedTask.getPriority());
        task.setCompleted(updatedTask.isCompleted());

        return ResponseEntity.ok(taskRepository.save(task));
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Get only completed tasks
    @GetMapping("/{userId}/completed")
    public List<Task> getCompletedTasks(@PathVariable Long userId) {
        return taskRepository.findByUserIdAndCompleted(userId, true);
    }
}
