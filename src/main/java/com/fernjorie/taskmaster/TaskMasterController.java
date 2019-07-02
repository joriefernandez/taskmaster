package com.fernjorie.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class TaskMasterController {

    private static final String AVAILABLE="Available";
    private static final String ASSIGNED="Assigned";
    private static final String ACCEPTED="Accepted";
    private static final String FINISHED="Finished";


    @Autowired
    TaskInfoRepository repository;

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskMaster>> getTasks(){
        Iterable<TaskMaster> results = repository.findAll();
        return ResponseEntity.ok(results);
    }

    @PostMapping("/tasks")
    public ResponseEntity<Iterable<TaskMaster>> addNewTask(@RequestParam String title, @RequestParam  String desc){

        TaskMaster newTask = new TaskMaster(title, desc);
        repository.save(newTask);
        return ResponseEntity.ok(repository.findAll());
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
}
