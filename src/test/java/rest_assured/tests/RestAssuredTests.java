package rest_assured.tests;

import io.restassured.RestAssured;
import rest_assured.models.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static rest_assured.specs.UserSpecs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestAssuredTests {

    @BeforeAll
    public static void setUp() {

        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Check User Not Found")
    void userNotFoundTest() {
        step("Send request Get User", () ->
                given(userNotFoundSpec)
                        .get("/users/23")
                        .then()
                        .spec(codeResponse(404)));
    }

    @Test
    @DisplayName("Check Successful Login")
    void successfulLoginWithSpecsTest() {
        LoginModel authData = new LoginModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponse response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(codeResponse(200))
                        .extract().as(LoginResponse.class));

        step("Check response", () ->
                assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    @DisplayName("Check Unsuccessful Login")
    void unsuccessfulLoginTest() {

        LoginModel authData = new LoginModel();
        authData.setEmail("eve.holt@reqres.in");

        ErrorModel response = step("Make request", () ->
                given(loginRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(codeResponse(400))
                        .extract().as(ErrorModel.class));

        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }


    @Test
    @DisplayName("Check Successful User Update via Put Method")
    void updateUserDataPutMethodTest() {
        UpdateUserModel newData = new UpdateUserModel();
        newData.setName("eve.holt@reqres.in");
        newData.setJob("zion resident");

        UpdateUserResponse response = step("Make PUT request", () ->
                given(updateUserSpec)
                        .body(newData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(codeResponse(200))
                        .extract().as(UpdateUserResponse.class));
        step("Response", () ->
                assertEquals("zion resident", response.getJob()));

    }

    @Test
    @DisplayName("Check Successful User Update via Patch Method")
    void updateUserDataPatchMethodTest() {
        UpdateUserModel newData = new UpdateUserModel();
        newData.setName("eve.holt@reqres.in");
        newData.setJob("zion resident");

        UpdateUserResponse response = step("Make PATCH request", () ->
                given(updateUserSpec)
                        .body(newData)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(codeResponse(200))
                        .extract().as(UpdateUserResponse.class));
        step("Response", () ->
                assertEquals("zion resident", response.getJob()));

    }

    @Test
    @DisplayName("Check Successful User Delete")
    void deleteUserTest() {
        step("Send request Delete User", () ->
                given(deleteRequestSpec)
                        .delete("/users/2")
                        .then()
                        .spec(codeResponse(204)));
    }
}


