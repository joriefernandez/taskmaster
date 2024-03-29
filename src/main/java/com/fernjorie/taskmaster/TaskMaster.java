package com.fernjorie.taskmaster;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.UUID;

@DynamoDBTable(tableName = "TaskInfo")
public class TaskMaster {

    private String id;

    private String title;

    private String description;

    private String status;

    private String assignee;

    private String imgUrl;

    private String imgResized;

    public TaskMaster(){}



    //Constructor
    public TaskMaster(String title, String description, String assignee) {
        this.title = title;
        this.description = description;
        this.status = setInitialStatus(assignee);
        this.assignee = assignee;
    }

    //Constructor
    public TaskMaster(String title, String description, String assignee, String imgUrl) {
        this.title = title;
        this.description = description;
        this.status = setInitialStatus(assignee);
        this.assignee = assignee;
        this.imgUrl = imgUrl;
    }


    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey()
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.toLowerCase();
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @DynamoDBAttribute
    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @DynamoDBAttribute
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @DynamoDBAttribute
    public String getImgResized() {
        return imgResized;
    }

    public void setImgResized(String imgResized) {
        this.imgResized = imgResized;
    }

    //Helper method to update status based on assignee
    private String setInitialStatus(String assignee){
        if(assignee != null && !assignee.equals("")){
            return "assigned";
        }

        return "available";
    }

}
