package com.l2o.planner;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.l2o.planner.dto.TaskType;
import com.l2o.planner.dto.TaskTypeResponse;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.UUID;

@QuarkusTest
class TaskTypeResourceTest {
    @Test
    void testCrud() {
	TaskType taskType = new TaskType();
	taskType.name = "TheTask";
	TaskTypeResponse created = given().body(taskType).contentType(ContentType.JSON).when().post("/v1/task-type").then()
		.statusCode(200).extract().as(TaskTypeResponse.class);
	Assertions.assertEquals("TheTask", created.name);
	UUID id = created.id;
	Assertions.assertNotNull(id);
	List<TaskTypeResponse> retrievedList = given().when().get("/v1/task-type").then().statusCode(200).extract()
		.as(new TypeRef<List<TaskTypeResponse>>() {
		});
	TaskTypeResponse matchingResult = retrievedList.stream().filter(item -> item.id.equals(id)).findAny()
		.get();
	Assertions.assertEquals("TheTask", matchingResult.name);
	matchingResult.name = "ThachangedTask";
	given().body(matchingResult).contentType(ContentType.JSON).when().put("/v1/task-type/" + id).then().statusCode(200);
	TaskTypeResponse retrievedSingle = given().when().get("/v1/task-type/" + id).then().statusCode(200).extract()
		.as(TaskTypeResponse.class);
	Assertions.assertEquals("ThachangedTask", retrievedSingle.name);
	given().when().delete("/v1/task-type/" + id).then().statusCode(204);
	List<TaskTypeResponse> finalRetrievedList = given().when().get("/v1/task-type").then().statusCode(200).extract()
		.as(new TypeRef<List<TaskTypeResponse>>() {
		});
	Assertions.assertTrue(finalRetrievedList.stream().filter(item -> item.id.equals(id)).findAny().isEmpty());
    }

}