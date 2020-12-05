package com.read.readbibleservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.config.properties.FirebaseDbProperties;
import com.read.readbibleservice.integration.ProfileIntegration;
import com.read.readbibleservice.model.Profile;
import com.read.readbibleservice.service.ProfileService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("unittest")
class ProfileServiceTest {

	@Autowired
	ProfileIntegration profileService;
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

	private Profile getProfile(Profile profile) {
		profile.setName("RenceAbishek");
		profile.setRole("ADMIN");
		return profile;
	}

	@Test
	public void getProfiles() throws JsonProcessingException {

		try {


			Profile pro = new Profile();
			pro = getProfile(pro);
			HashMap<String, Profile> value= new HashMap<>();
			value.put("values",pro);
			String body = objectMapper.writeValueAsString(value);
			System.out.println("body -- " + body);

			server.url("login.json?orderBy=%22username%22&equalTo=%22abishek%22");
			server.enqueue(new MockResponse()
					.setResponseCode(200)
					.setBody(body)
					.addHeader("Content-Type", "application/json"));

			Assertions.assertThat(profileService.getProfileDetail("abishek").block().values())
					.satisfies(it->{
						it.forEach(f-> {
							assertThat(f)
									.hasFieldOrPropertyWithValue("name","RenceAbishek");
						});
					});

			final RecordedRequest recordedRequest = this.server.takeRequest();
			assertEquals("GET", recordedRequest.getMethod());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
