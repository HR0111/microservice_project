package org.hemant.microservice.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import static org.hamcrest.Matchers.*;

//@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDbContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

    }

    static {
        mongoDbContainer.start();
    }

    @Test
    void shouldCreateProduct() {

        String requestBody = """
                    {
                      
                       "name": "Iphone 19Pro Max",
                       "description": "This is a sample product description.",
                       "price": 1000
                     }
                
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/product")
                .then()
                .statusCode(201)
                .body("id", Matchers.notNullValue())
                .body("name", Matchers.equalTo("Iphone 19Pro Max"))
                .body("description", Matchers.equalTo("This is a sample product description."))
                .body("price", Matchers.equalTo(1000));



    }

}
