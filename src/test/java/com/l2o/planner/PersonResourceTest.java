package com.l2o.planner;

import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.l2o.planner.dto.Person;
import com.l2o.planner.dto.PersonResponse;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

@QuarkusTest
class PersonResourceTest {
    @Test
    void testCrud() {
	Person person = new Person();
	person.name = "Bob Smith";
	PersonResponse created = given().body(person).contentType(ContentType.JSON).when().post("/v1/person").then()
		.statusCode(200).extract().as(PersonResponse.class);
	Assertions.assertEquals("Bob Smith", created.name);
	UUID id = created.id;
	Assertions.assertNotNull(id);
	List<PersonResponse> retrievedList = given().when().get("/v1/person").then().statusCode(200).extract()
		.as(new TypeRef<List<PersonResponse>>() {
		});
	PersonResponse matchingResult = retrievedList.stream().filter(item -> item.id.equals(id)).findAny()
		.get();
	Assertions.assertEquals("Bob Smith", matchingResult.name);
	matchingResult.name = "Billy-Bob Smith-Pearson";
	given().body(matchingResult).contentType(ContentType.JSON).when().put("/v1/person/" + id).then().statusCode(200);
	PersonResponse retrievedSingle = given().when().get("/v1/person/" + id).then().statusCode(200).extract()
		.as(PersonResponse.class);
	Assertions.assertEquals("Billy-Bob Smith-Pearson", retrievedSingle.name);
	given().when().delete("/v1/person/" + id).then().statusCode(204);
	List<PersonResponse> finalRetrievedList = given().when().get("/v1/person").then().statusCode(200).extract()
		.as(new TypeRef<List<PersonResponse>>() {
		});
	Assertions.assertTrue(finalRetrievedList.stream().filter(item -> item.id.equals(id)).findAny().isEmpty());
    }

}