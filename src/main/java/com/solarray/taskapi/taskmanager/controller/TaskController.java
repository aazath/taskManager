package com.solarray.taskapi.taskmanager.controller;

import com.solarray.taskapi.taskmanager.Entity.TaskEntity;
import com.solarray.taskapi.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    private TaskService taskService;

    //create task
    @PostMapping("/tasks")
    public ResponseEntity<String> addTask(@RequestBody TaskEntity taskEntity) {
        try {
            String msg = taskService.addTask(taskEntity);
            return new ResponseEntity<String>(msg, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<String>("Failed to add the task", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //read task
    @GetMapping("/tasks")
    public ResponseEntity<?> findAllTasks(){
        try {
            List<TaskEntity> taskEntities = taskService.fetchAllTask();
            return new ResponseEntity<List<TaskEntity>>(taskEntities,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Unable to fetch Tasks", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> findTaskById(@PathVariable Integer id){
        try {
            TaskEntity task = taskService.fetchById(id);
            return new ResponseEntity<TaskEntity>(task,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Unable to find the Task with ID " + id,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update - full update
    @PutMapping("/tasks")
    public ResponseEntity<String> modifyTask(@RequestBody TaskEntity taskEntity){
        try {
            String message = taskService.updateTask(taskEntity);
            return new ResponseEntity<>(message,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable int id){
        try {
            String message = taskService.deleteTaskById(id);
            return new ResponseEntity<>(message,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }



}
