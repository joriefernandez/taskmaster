package com.fernjorie.taskmaster;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface TaskInfoRepository extends CrudRepository<TaskMaster, String> {
    List<TaskMaster> findTaskByStatus(String status);
    List<TaskMaster> findTaskByAssignee(String assignee);

}
