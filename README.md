 # TASK MANAGER PROJECT 
 

A small user can plan their daily, weekly, monthly workis an application.
 
 ### Node.js MongoDB Rest CRUD API Overview

The data type sent to be the API should be as follows. The Type is can only take 0,1 and 2.

                
0. Day=0
1. Week=1
2. Month=2
                

####JSON　

```json
{
	"title":"Example Title",
	"description":"Example Description",
	"type":"2"
}
```


The following table shows overview of the Rest APIs that will be exported:

| METHODS    | URL                | ACTIONS              |
|:---        |:---                |:---                  |
| GET        | api/task           | get all task         |
| GET        | api/task/:id       | get task by id       |
| GET        | api/task/type/:type| get task by type     |
| POST       | api/task           | add new task         |
| PUT        | api/task/:id       | update task by id    |
| DELETE     | api/task/:id       | remove task by id    |
| DELETE     | api/task/          | remove all task      |


You must run the server.js file to test | TASK-MANAGER-API-> server.js 
----------------------------------------------------------------------------------------------------------

### JAVA Android Development Overview


The task sent as a Project is explained below.

* The app is designed as min Sdk version 23 Target Sdk 29.
* Internet control is done before logging into the application.The application is closed even if the internet is not connected.
* Retrofit Library was used to process JSON data.
* * You can assign a task by clicking the Tab Menu + which I Separate Daily Weekly Monthly.
* You can update or delete the task by clicking on the Task listed.
* You can delete All Tasks from the menu on the action bar and exit the application.


If you want to run it on your own server, after running Node API You should update the address in its folder.

```sh

Folder : RetrofitAPI -> ApiClient.class
public static final String BASE_URL = "http://192.168.43.42:8080/";

```
<img width="300" height="600" alt="java 8 and prio java 8  array review example" src="https://github.com/ridvancakirtr/taskmanager/blob/master/app.gif">
