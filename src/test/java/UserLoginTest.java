import Praktikum.models.ResponseUser;
import Praktikum.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;

public class UserLoginTest extends BaseTest{
    private User user;
    String accessToken;

    @Before
    public void setupUser(){
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(6));
        user.setName(RandomStringUtils.randomAlphabetic(6));

        ResponseUser responseUser = steps
                .createUser(user)
                .extract().as (ResponseUser.class);
        accessToken = responseUser.getAccessToken();
    }
    // пользователь может авторизоваться
    @Test
    @DisplayName("successful authorization of the user - POST на /api/auth/login")
    @Description("a successful request returns true")
    public void successfulAuthorization(){
        steps
                .authUser(user)
                .assertThat().statusCode(200)
                .body("success", is(true));
    }

    // если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
    @Test
    @DisplayName("you cannot log in if you enter an incorrect username or password - POST на /api/auth/login")
    @Description("verification of authorization under a non-existent user - error code 401")
    public void unsuccessfulAuthorization(){
        User userBad = new User();
        userBad.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        userBad.setPassword(RandomStringUtils.randomAlphabetic(6));
        steps
                .authUser(userBad)
                .statusCode(401);
    }
    @After
    public void deleteUser(){
        steps
                .deleteUser(accessToken)
                .statusCode(202);
    }
}
