# TaskMaster Application
This is an application that allows user to create, update, or view tasks. Routes available are:
1. /tasks - to view all tasks
2. /tasks with title, description, assignee parameters - create a new task
3. /tasks/{id}/status - to update status of the task. Status is not updated if there is no assignee.
4. /users/{name}/tasks - display all tasks associated to the assignee
5. /tasks/{id}/assign/{assignee} - re-assign task. If assigning to the same person, previous status won't be changed.
6. /tasks/{id} - display details about the task id with img if exists
7. /tasks/{id}/images - upload an image to the task.

## Link to site
http://taskmasterapp.us-east-2.elasticbeanstalk.com


## Deployment issues
* Initial deployment's status is degraded - had to wait a couple minutes and refresh

## Lambda Warmer
Lambda warmer is called taskmasterWarmer which invokes taskmasterLambda.
* [Lambda Warmer Screenshots](./warmer)

## Event Driven Architecture
To wire up our Queues with live running cloud based code, creating a distributed, event driven architecture.

### Feature Tasks

Using Queues and Notifications for the Taskmaster

1. Send an email to an administrator when a task is completed
2. Send a text to the person to whom a task is assigned (when it gets assigned)
3 . When a task is deleted from Dynamo, trigger a message that will fire a lambda to remove any images associated to it from S3.
4. Instead of having S3 run the resizer automatically on upload, evaluate the size of the image in your Java code and then send a message to a Q, that will in turn trigger the lambda resizer -- only when the image > 350k

### Issues
1. No clear instructions for the SNS.
2. Instance not reading DynamoDB.
3. Queues security issues.
