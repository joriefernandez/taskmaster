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

    //Display all tasks
    @GetMapping("/tasks")
    public ResponseEntity<Iterable<TaskMaster>> getTasks(){
        Iterable<TaskMaster> results = repository.findAll();
        return ResponseEntity.ok(results);
    }

    /****** Create new task *********/
    @PostMapping("/tasks")
    public ResponseEntity<TaskMaster> addNewTask(@RequestParam String title,
                                                           @RequestParam  String description,
                                                           @RequestParam String assignee){

        TaskMaster newTask = new TaskMaster(title, description, assignee);
        repository.save(newTask);
        return ResponseEntity.ok(newTask);
    }

    /****** Update task *********/
    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<TaskMaster> updateTask(@PathVariable String id){
        TaskMaster currentTask = repository.findById(id).get();

        //Only time to advance is if there is an assignee on the task
        if(currentTask.getAssignee() != null) {

            switch (currentTask.getStatus()) {
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
        }
        return ResponseEntity.ok(currentTask);
    }

    /****** Display tasks based on the assignee *********/
    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<Iterable<TaskMaster>> getUserTasks(@PathVariable String name){
        Iterable<TaskMaster> results = repository.findTaskByAssignee(name);
        return ResponseEntity.ok(results);
    }

    /****** Re-assign the task *********/
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public ResponseEntity<TaskMaster> updateAssignee(@PathVariable String id, @PathVariable String assignee){
        TaskMaster current = repository.findById(id).get();

        // Check if the assignee is different from the current, if so reset status
        if(!assignee.equals(current.getAssignee())){
            current.setStatus(ASSIGNED);
            current.setAssignee(assignee);
            repository.save(current);
        }

        return ResponseEntity.ok(current);
    }
}
