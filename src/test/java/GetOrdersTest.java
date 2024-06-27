import Praktikum.models.ResponseUser;
import Praktikum.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class GetOrdersTest extends BaseTest{
    public String accessToken;
    @Before
    public void setupUser() {
        User user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(6));
        user.setName(RandomStringUtils.randomAlphabetic(6));

        ResponseUser responseUser = steps
                .createUser(user)
                .extract().as(ResponseUser.class);
        accessToken = responseUser.getAccessToken();
    }
    //получить заказы авторизованного пользователя
    @Test
    @DisplayName("creating an order - GET на /api/orders")
    @Description("when receiving orders from an authorized user, the status code is 200")
    public void getOrders() {
        steps
                .getOrder(accessToken)
                .assertThat().statusCode(200)
                .body("success", is(true));

    }
    //получить заказы неавторизованного пользователя
    @Test
    @DisplayName("creating an order - GET на /api/orders")
    @Description("it is impossible to receive orders from an unauthorized user - error code 401")
    public void getOrdersWithoutAuthorization() {
        steps
                .getOrder()
                .assertThat().statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));

    }
    @After
    public void deleteUser(){
        steps
                .deleteUser(accessToken)
                .statusCode(202);
    }
}
