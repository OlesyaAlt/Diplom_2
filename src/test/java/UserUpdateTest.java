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

public class UserUpdateTest extends BaseTest{
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
    //после авторизации пользователь может изменить данные в поле Емейл
    @Test
    @DisplayName("after authorization, the user can change the Email - PATCH на /api/auth/user")
    @Description("a successful update returns true")
    public void changeFieldEmail(){
        String newEmail = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + "yandex.ru";
        user.setEmail(newEmail);
        steps
                .updateUser(accessToken,user)
                .assertThat().statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(newEmail));
    }
    //после авторизации пользователь может изменить данные в поле Имя
    @Test
    @DisplayName("after authorization, the user can change the Name - PATCH на /api/auth/user")
    @Description("a successful update returns true")
    public void changeFieldName() {
        String newName = RandomStringUtils.randomAlphabetic(6);
        user.setName(newName);

        steps
                .updateUser(accessToken,user)
                .assertThat().statusCode(200)
                .body("success", is(true))
                .body("user.name", equalTo(newName));
    }

    //после авторизации пользователь может изменить пароль
    @Test
    @DisplayName("after authorization, the user can change the Password - PATCH на /api/auth/user")
    @Description("a successful update returns true")
    public void changeFieldPassword() {
        String newPassword = RandomStringUtils.randomAlphabetic(6);
        user.setPassword(newPassword);

        steps
                .updateUser(accessToken,user)
                .assertThat().statusCode(200)
                .body("success", is(true));

        //зашли под новым паролем
        steps
                .authUser(user)
                .statusCode(200);

    }
    //без авторизации пользователь не может изменить поле Емэйл
    @Test
    @DisplayName("without authorization, the user cannot change the Email - PATCH на /api/auth/user")
    @Description("error message - You should be authorised")
    public void changeFieldEmailWithoutAuthorization() {
        String newEmail = RandomStringUtils.randomAlphabetic(10) + "@" + "yandex.ru";
        user.setEmail(newEmail);

        steps
                .updateUser(user)
                .statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));

    }
    //без авторизации пользователь не может изменить Пароль
    @Test
    @DisplayName("without authorization, the user cannot change the Password - PATCH на /api/auth/user")
    @Description("error message - You should be authorised")
    public void changeFieldPasswordWithoutAuthorization() {
        String newPassword = RandomStringUtils.randomAlphabetic(6);
        user.setPassword(newPassword);

        steps
                .updateUser(user)
                .assertThat().statusCode(401)
                .body("success", is(false))
                .body("message", equalTo("You should be authorised"));
    }
    //без авторизации пользователь не может изменить поле Имя
    @Test
    @DisplayName("without authorization, the user cannot change the Name - PATCH на /api/auth/user")
    @Description("error message - You should be authorised")
    public void changeFieldNameWithoutAuthorization() {
        String newName = RandomStringUtils.randomAlphabetic(6);
        user.setName(newName);

        steps
                .updateUser(user)
                .statusCode(401)
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
