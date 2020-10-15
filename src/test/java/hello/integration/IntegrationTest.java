package hello.integration;

import hello.Application;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class IntegrationTest {
    //获取临时端口
    @Inject
    Environment environment;

    @Test
    public void notLoggedInByDefault() throws IOException {
        String port = environment.getProperty("local.server.port");
        System.out.println(environment.getProperty("local.server.port"));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url("http://localhost:" + port + "/auth")
                .header("Accept", "application/json")
                .build();

        try(Response response = client.newCall(request).execute()){
            String body =response.body().string();
            System.out.println(body);
            Assertions.assertTrue(body.contains("false"));
            Assertions.assertEquals(200, response.code());
        }
    }
}
