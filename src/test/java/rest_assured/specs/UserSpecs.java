package rest_assured.specs;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static rest_assured.helpers.CustomAllureListener.withCustomTemplates;

public class UserSpecs {
    public static final RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres-free-v1")
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static final RequestSpecification userNotFoundSpec = with()
            .header("x-api-key", "reqres-free-v1")
            .log().uri();

    public static final RequestSpecification updateUserSpec = with()
            .header("x-api-key", "reqres-free-v1")
            .filter(withCustomTemplates())
            .log().uri()
            .contentType(JSON);

    public static final RequestSpecification deleteRequestSpec = with()
            .header("x-api-key", "reqres-free-v1")
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .log().headers()
            .contentType(JSON);

    public static ResponseSpecification codeResponse(Integer code) {
        return new ResponseSpecBuilder()
                .expectStatusCode(code)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
