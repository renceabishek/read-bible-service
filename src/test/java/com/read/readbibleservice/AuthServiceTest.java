package com.read.readbibleservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.config.properties.FirebaseDbProperties;
import com.read.readbibleservice.model.User;
import com.read.readbibleservice.service.AuthService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unittest")
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    FirebaseDbProperties firebaseDbProperties;
    @Autowired
    ObjectMapper objectMapper;

    MockWebServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = new MockWebServer();
        URL url = new URL(firebaseDbProperties.getUrl());
        server.start(InetAddress.getByName(url.getHost()), url.getPort());
    }

    @AfterEach
    void tearDown() throws IOException {
        server.shutdown();
    }

    private User getUser() {
        return User.builder()
                .email("test@gmail.com")
                .confirmationDateTime(LocalDateTime.now().minusHours(20))
                .isEnabled(false)
                .confirmationToken("12345")
                .name("Mr Abishek")
                .username("test")
                .build();
    }

    @Test
    public void checkConfirmationToken_if_Localdate_Expires_24hrs_Positive() throws InterruptedException, IOException {

        /*String token = "12345";

        HashMap<String, User> cl = new HashMap<>();
        cl.put("123", getUser());


        String body = objectMapper.writeValueAsString(cl);

        server.url("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(body)
                .addHeader("Content-Type", "application/json"));

        server.url("/login/123.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("")
                .addHeader("Content-Type", "application/json"));

        Assertions.assertThat(authService.confirmUser(token))
                .satisfies(ModelAndView::getView)


        final RecordedRequest recordedRequest = this.server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22", recordedRequest.getPath());

        final RecordedRequest recordedRequest2 = this.server.takeRequest();
        assertEquals("PATCH", recordedRequest2.getMethod());
        assertEquals("/login/123.json", recordedRequest2.getPath());*/

    }

    @Test
    public void checkConfirmationToken_if_Localdate_Expires_24hrs_Negative() throws JsonProcessingException, InterruptedException {
        /*String token = "12345";

        HashMap<String, User> cl = new HashMap<>();
        //cl.put("values", new ConfirmationLogin("123","rence", token, LocalDateTime.now().minusHours(25)));

        String body = objectMapper.writeValueAsString(cl);

        server.url("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(body)
                .addHeader("Content-Type", "application/json"));


        Assertions.assertThat(authService.confirmUser(token))
                .hasFieldOrPropertyWithValue("status", 50003);

        final RecordedRequest recordedRequest = this.server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22", recordedRequest.getPath());*/

    }

    @Test
    public void checkConfirmationToken_if_Localdate_Expires_24hrs_Positive_isEnabled_true()
            throws JsonProcessingException, InterruptedException {

        /*String token = "12345";

        HashMap<String, User> cl = new HashMap<>();
        //cl.put("values", new ConfirmationLogin("123","rence", token, LocalDateTime.now().minusHours(22)));

        String body = objectMapper.writeValueAsString(cl);

        server.url("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(body)
                .addHeader("Content-Type", "application/json"));

        User user = null;//new User("rence","123","ADMIN","rence@gmail.com",true);
        String bodyUser = objectMapper.writeValueAsString(user);

        server.url("/login/123.json");
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(bodyUser)
                .addHeader("Content-Type", "application/json"));


        Assertions.assertThat(authService.confirmUser(token))
                .hasFieldOrPropertyWithValue("status", 50004);

        final RecordedRequest recordedRequest = this.server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals("/loginconfirmation.json?orderBy=%22token%22&equalTo=%2212345%22", recordedRequest.getPath());

        final RecordedRequest recordedRequest1 = this.server.takeRequest();
        assertEquals("GET", recordedRequest1.getMethod());
        assertEquals("/login/123.json", recordedRequest1.getPath());*/

    }
}
