package com.solarray.taskapi.taskmanager.service;

import com.solarray.taskapi.taskmanager.Entity.TaskEntity;
import com.solarray.taskapi.taskmanager.repository.TaskRepository;
import exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskEntity> fetchAllTask() {
       return taskRepository.findAll();
    }

    public TaskEntity fetchById(int id){
        Optional<TaskEntity> byId = taskRepository.findById(id);
        if (byId.isPresent()){
            return byId.get();
        }else {
            throw new TaskNotFoundException("Record not found for the ID "+id);
        }
    }

    public String addTask(TaskEntity taskEntity) {
        Integer id = taskRepository.save(taskEntity).getId();
        return "Task Saved with ID: "+id;
    }

    public String updateTask(TaskEntity taskEntity){
        Optional<TaskEntity> byId = taskRepository.findById(taskEntity.getId());
        if(byId.isPresent()){
            TaskEntity task = byId.get();
            task.setDescription(taskEntity.getDescription());
            task.setTitle(taskEntity.getTitle());
            task.setIsActive(taskEntity.getIsActive());
            taskRepository.save(task);
            return "Task with the ID "+task.getId() + " is updated";
        }
        else {
            throw new TaskNotFoundException("Task with the ID "+ taskEntity.getId() + " is not available  for update");
        }
    }

    public String deleteTaskById(int id){
        Optional<TaskEntity> byId = taskRepository.findById(id);
        if(byId.isPresent()){
            TaskEntity task = byId.get();
            taskRepository.delete(task);
            return "Task with the ID "+task.getId() + " is deleted";
        }
        else {
            throw new TaskNotFoundException("Task with the ID "+ id + " is not available  for deletion");
        }
    }



}
