import Praktikum.models.Ingredient;
import Praktikum.models.ResponseUser;
import Praktikum.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.*;

public class CreatedOrderTest extends BaseTest{
    public String accessToken;
    List<String> ingredients;
    Ingredient ingredient;

    @Before
    public void setupUser(){
       User user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(6));
        user.setName(RandomStringUtils.randomAlphabetic(6));

        ResponseUser responseUser = steps
                .createUser(user)
                .extract().as (ResponseUser.class);
        accessToken = responseUser.getAccessToken();
        ingredients = Arrays.asList(steps.getIngredients().extract().body().path("data[0]._id"),
                steps.getIngredients().extract().body().path("data[1]._id"));
        ingredient = new Ingredient(ingredients);
    }
    //создать заказ с авторизацией и ингредиентами
    @Test
    @DisplayName("creating an order - POST на /api/orders")
    @Description("checking the creation of the order, the response code and the response body")
    public void orderCreated() {
        steps
                .createdOrder(accessToken, ingredient)
                .assertThat().statusCode(200)
                .body("success", is(true))
                .body("name", notNullValue())
                .body("order.number",notNullValue());
    }
    //создать заказ без авторизации c ингредиентами
    //баг
    @Test
    @DisplayName("creating an order without authorization - POST на /api/orders")
    @Description("only authorized users can place orders - response code - 400")
    public void orderCreatedWithoutAuthorization() {
        steps
                .createdOrder(ingredient)
                .assertThat().statusCode(400);
    }

    //создать заказ с авторизацией и с невалидным хешем ингредиентов
    @Test
    @DisplayName("creating an order - POST на /api/orders")
    @Description("If an invalid hash of an ingredient is passed in the request, the response code 500")
    public void orderCreatedWithAnInvalidUHash() {
        List<String> ingredients2 = Arrays.asList(RandomStringUtils.randomAlphabetic(24), RandomStringUtils.randomAlphabetic(24));
        ingredient = new Ingredient(ingredients2);
        steps
                .createdOrder(accessToken, ingredient)
                .assertThat().statusCode(500);

    }
    //создать заказ без ингредиентов
    @Test
    @DisplayName("creating an order - POST на /api/orders")
    @Description("checking the creation of the order, the response code and the response body")
    public void orderCreatedWithoutIngredients() {
        steps
                .createdOrder(accessToken)
                .assertThat().statusCode(400)
                .body("success", is(false))
               .body("message", equalTo("Ingredient ids must be provided"));

    }
    @After
    public void deleteUser(){
        steps
                .deleteUser(accessToken)
                .statusCode(202);
    }

}
