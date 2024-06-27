import Praktikum.models.ResponseUser;
import Praktikum.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UserCreatedTest extends BaseTest{
    private User user;

    @Before
    public void setupUser(){
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(6));
        user.setName(RandomStringUtils.randomAlphabetic(6));
    }

    //создать уникального пользователя
    @Test
    @DisplayName("creating a user - POST на /api/auth/register")
    @Description("checking the creation of the user, the response code and the response body")
    public void userCreatedTest() {
        steps
                .createUser(user)
                .assertThat().statusCode(200)
                .body("success", is(true));
    }
    // нельзя создать пользователя, который уже зарегистрирован
    @Test
    @DisplayName("you cannot create two identical users - POST на /api/auth/register")
    @Description("check that it is impossible to create two identical users and error code 403")
    public void dontCreateTwoIdenticalUsers() {
        steps
                .createUser(user);

        steps
                .createUser(user)
                .statusCode(403)
                .body("success", is(false))
                .body("message", equalTo("User already exists"));

    }
    @After
    public void deleteUser(){
        ResponseUser responseUser =
                steps
                        .authUser(user)
                        .extract().as (ResponseUser.class);
        String accessToken = responseUser.getAccessToken();
        steps
                .deleteUser(accessToken)
                .statusCode(202);
    }
}