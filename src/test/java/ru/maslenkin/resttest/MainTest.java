package ru.maslenkin.resttest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;
import static io.restassured.RestAssured.*;

public class MainTest {
    String token = "AgAAAAA3WaDzAADLW2nqnQYz30mdr3d6KpXyhfU";
    String fileName = "test.txt";
    String folderName = "test_folder";

    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://cloud-api.yandex.net")
            .addHeader("Authorization", token)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.ANY)
            .log(LogDetail.ALL)
            .build();

    ResponseSpecification responseSpecification201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectContentType(ContentType.JSON)
            .expectContentType("")
            .build();

    ResponseSpecification responseSpecification400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .expectContentType(ContentType.JSON)
            .build();

    public String getLink() {
        JsonPath body = given()
                .spec(requestSpecification)
                .when().pathParams("path", fileName)
                .get(EndPoint.pathLink)
                .then().log().all()
                .extract().body().jsonPath();
        return body.get("href");
    }

    @Test(description = "positive", priority = 1)
    public void getTestUploadFile() {
        given().spec(requestSpecification)
                .when()
                .put(getLink())
                .then()
                .log().all()
                .spec(responseSpecification201);
    }

    @Test(description = "positive", priority = 2, enabled = true)
    public void getTestCreateFolder() {
        given()
                .spec(requestSpecification)
                .when().pathParams("folderName", folderName)
                .put(EndPoint.pathUploadFolder)
                .then()
                .log().all()
                .spec(responseSpecification201);
    }

    @Test(description = "positive", priority = 3)
    public void getTestCopyFile() {
        given().spec(requestSpecification)
                .when().pathParams("fileName", fileName)
                .post(EndPoint.copyPathFile)
                .then()
                .log().all()
                .spec(responseSpecification201);
    }

    @Test(description = "positive", priority = 4, enabled = true)
    public void getTestCopyFolder() {
        given().spec(requestSpecification)
                .when().pathParams("folderName", folderName)
                .post(EndPoint.copyPathFolder)
                .then()
                .log().all()
                .spec(responseSpecification201);
    }

    @Test(description = "negative", priority = 5)
    public void getTestCopyBadRequest() {
        given().spec(requestSpecification)
                .post(EndPoint.copyPathBadRequest)
                .then()
                .log().all()
                .spec(responseSpecification400);
    }

    @Test(priority = 6)
    public void delete() {
        given()
                .spec(requestSpecification)
                .when().pathParams("path", folderName)
                .delete(EndPoint.path)
                .then()
                .log().all()
                .statusCode(204);
    }
}





