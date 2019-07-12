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
[Lambda Warmer](./warmer)