package com.altran.mamartin.webcontroller;

import static io.restassured.RestAssured.given;

import com.altran.mamartin.beans.dto.Entity;
import com.altran.mamartin.beans.utils.Constants;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class EntityResourceAssured {

  private static final String URL = "http://localhost:8080/rest/api/v1/entity";

  @Test
  public void findAll() {
    List<Entity> result = given()
        .contentType(ContentType.JSON)
        .get(URL + "/all")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .response()
        .body()
        .as(List.class);
    Assert.assertNotNull(result);
    Assert.assertTrue(result.size() > 0);
  }

  @Test
  public void findAllPaginated() {
    ResponseBody body = given()
        .contentType(ContentType.JSON)
        .get(URL + "/?page=2&size=3")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .response()
        .body();
    JsonPath jsonPath = body.jsonPath();
    List<Entity> result = jsonPath.getList("content");
    Assert.assertNotNull(result);
    Assert.assertTrue(3 == result.size());
    Map<String, Object> pageable = jsonPath.get("pageable");
    Assert.assertNotNull(pageable);
    Assert.assertTrue(3 == (Integer) pageable.get("pageSize"));
    Assert.assertTrue(2 == (Integer) pageable.get("pageNumber"));
  }

  @Test
  public void findById() {
    final String id = "050453ef-14a3-4841-b0b4-368de0aeda0e";
    String uri = URL + Constants.SLASH + id;
    Entity result = given()
        .contentType(ContentType.JSON)
        .get(uri)
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .response()
        .body()
        .as(Entity.class);
    Assert.assertNotNull(result);
    Assert.assertTrue(id.equals(result.getId()));
    Assert.assertEquals(uri, result.getUri());
  }

}
