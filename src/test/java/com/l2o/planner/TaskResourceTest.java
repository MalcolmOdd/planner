package com.l2o.planner;

import static io.restassured.RestAssured.given;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.l2o.planner.dto.Task;
import com.l2o.planner.dto.TaskResponse;
import com.l2o.planner.dto.TaskType;
import com.l2o.planner.dto.TaskTypeResponse;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class TaskResourceTest {
    @Test
    void testCrud() {
	TaskType taskType = new TaskType();
	taskType.name = "TheTask";
	TaskTypeResponse createdTaskType = given().body(taskType).contentType(ContentType.JSON).when()
		.post("/v1/task-type").then().statusCode(200).extract().as(TaskTypeResponse.class);
	Task task = new Task();
	task.start = Instant.parse("2012-12-12T02:00:00Z");
	task.end = Instant.parse("2012-12-12T09:00:00Z");
	task.tasktypeId = createdTaskType.id;
	TaskResponse createdTask = given().body(task).contentType(ContentType.JSON).when().post("/v1/task").then()
		.statusCode(200).extract().as(TaskResponse.class);
	UUID id = createdTask.id;
	Assertions.assertNotNull(id);
	given().when().delete("/v1/task/" + id).then().statusCode(204);
    }

}