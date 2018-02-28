package dskuznetsov;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.swagger.client.ApiClient;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.User;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static io.swagger.client.GsonObjectMapper.gson;
import static org.assertj.core.api.Assertions.assertThat;

public class CreateUserTest {
    private UserApi api;
    private User user;
    private String NAME = "name";
    private String FIRSTNAME = "firstname";
    private String LASTNAME = "lastname";

    @Before
    public void createApi() {
        api = ApiClient.api(ApiClient.Config.apiConfig().reqSpecSupplier(
                () -> new RequestSpecBuilder().setConfig(config().objectMapperConfig(objectMapperConfig().defaultObjectMapper(gson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri("http://petstore.swagger.io/v2"))).user();
        user = new User().username(NAME).firstName(FIRSTNAME).lastName(LASTNAME);
    }

    @Test
    public void shouldSee0AfterCreateUser() {
        int statusCode = api.createUser()
                .body(user).execute(r -> r.getStatusCode());

        assertThat(statusCode).isEqualTo(200);
    }
}
