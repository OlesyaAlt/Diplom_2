import Praktikum.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserCreatedNegativeTest extends BaseTest{
    private User user;

    //в теле запроса отсутствуте поле Емэйл
    @Test
    @DisplayName("the Email field is missing in the request body - POST на /api/auth/register")
    @Description("you cannot create a courier if the required Email field is missing")
    public void userCreatedWithoutEmailField() {
        user = new User();
        user.setPassword(RandomStringUtils.randomAlphabetic(6));
        user.setName(RandomStringUtils.randomAlphabetic(6));

        steps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    //в теле запроса отсутствуте поле Пароль
    @Test
    @DisplayName("the Password field is missing in the request body - POST на /api/auth/register")
    @Description("you cannot create a user if the required Password field is missing")
    public void userCreatedWithoutPasswordField() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setName(RandomStringUtils.randomAlphabetic(6));

        steps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
    //в теле запроса отсутствуте поле Имя
    @Test
    @DisplayName("the Name field is missing in the request body - POST на /api/auth/register")
    @Description("you cannot create a user if the required Name field is missing")
    public void userCreatedWithoutNameField() {
        user = new User();
        user.setEmail(RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru");
        user.setPassword(RandomStringUtils.randomAlphabetic(6));

        steps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }
}