package com.fernjorie.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class TaskMasterController {

    private static final String AVAILABLE="available";
    private static final String ASSIGNED="assigned";
    private static final String ACCEPTED="accepted";
    private static final String FINISHED="finished";


    @Autowired
    TaskInfoRepository repository;

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskMaster>> getTasks(){
        Iterable<TaskMaster> results = repository.findAll();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskMaster> addNewTask(@RequestParam String title,
                                                           @RequestParam  String description,
                                                           @RequestParam String assignee){

        TaskMaster newTask = new TaskMaster(title, description, assignee);
        repository.save(newTask);
        return ResponseEntity.ok(newTask);
    }

    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<TaskMaster> updateTask(@PathVariable String id){
        TaskMaster currentTask = repository.findById(id).get();

        switch(currentTask.getStatus()){
            case AVAILABLE:
                currentTask.setStatus(ASSIGNED);
                break;
            case ASSIGNED:
                currentTask.setStatus(ACCEPTED);
                break;
            case ACCEPTED:
                currentTask.setStatus(FINISHED);
                break;
        }

        repository.save(currentTask);
        return ResponseEntity.ok(currentTask);
    }

    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<Iterable<TaskMaster>> getUserTasks(@PathVariable String assignee){
        Iterable<TaskMaster> results = repository.findTaskByAssignee(assignee);
        return ResponseEntity.ok(results);
    }
}
