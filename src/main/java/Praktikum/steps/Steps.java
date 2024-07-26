package Praktikum.steps;

import Praktikum.models.Ingredient;
import Praktikum.models.User;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class Steps {
    public static final String REGISTER = "/api/auth/register";
    public static final String LOGIN = "/api/auth/login";
    public static final String USER = "/api/auth/user";
    public static final String INGREDIENTS = "/api/ingredients";
    public static final String ORDERS = "/api/orders";

    public ValidatableResponse createUser(User user){

        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(REGISTER)
                .then();
    }
    public ValidatableResponse authUser(User user){

        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .post(LOGIN)
                .then();
    }

    public ValidatableResponse updateUser(User user){
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .when()
                .patch(USER)
                .then();
    }
    public ValidatableResponse updateUser(String accessToken,User user){
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .body(user)
                .when()
                .patch(USER)
                .then();
    }
    public ValidatableResponse deleteUser(String accessToken){
        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .when()
                .delete(USER)
                .then();
    }
    public ValidatableResponse getIngredients(){

        return given()
                .header("Content-type", "application/json")
                .when()
                .get(INGREDIENTS)
                .then();
    }
    public ValidatableResponse createdOrder(String accessToken, Ingredient ingredient){

        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .body(ingredient)
                .when()
                .post(ORDERS)
                .then();
    }
    public ValidatableResponse createdOrder(Ingredient ingredient){

        return given()
                .header("Content-type", "application/json")
                .body(ingredient)
                .when()
                .post(ORDERS)
                .then();
    }
    public ValidatableResponse createdOrder(String accessToken){

        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .when()
                .post(ORDERS)
                .then();
    }
    public ValidatableResponse getOrder(String accessToken){

        return given()
                .header("Content-type", "application/json")
                .and()
                .header("authorization", accessToken)
                .when()
                .get(ORDERS)
                .then();
    }
    public ValidatableResponse getOrder(){

        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS)
                .then();
    }
}
