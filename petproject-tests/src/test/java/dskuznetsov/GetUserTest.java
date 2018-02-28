package dskuznetsov;

import com.google.gson.JsonObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.swagger.client.ApiClient;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.User;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.mapper.ObjectMapperType.GSON;
import static io.swagger.client.GsonObjectMapper.gson;
import static org.assertj.core.api.Assertions.assertThat;

public class GetUserTest {
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
        api.createUser().body(new User().username(NAME).firstName(FIRSTNAME).lastName(LASTNAME));
    }


    @Test
    public void shouldGetUserFirstName() {
        JsonObject userObj = api.getUserByName().usernamePath(NAME).execute(r -> r.as(JsonObject.class, GSON));
        assertThat(userObj.get("firstName").getAsString()).isEqualTo(FIRSTNAME);
    }
}
