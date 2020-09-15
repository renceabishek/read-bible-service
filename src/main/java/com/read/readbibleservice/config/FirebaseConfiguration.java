package com.read.readbibleservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.read.readbibleservice.config.properties.FirebaseDbProperties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class FirebaseConfiguration {

  private final FirebaseDbProperties firebaseDbProperties;

  @Value(value = "classpath:google-services.json")
  private Resource gservicesConfig;

  public FirebaseConfiguration(FirebaseDbProperties firebaseDbProperties) {
    this.firebaseDbProperties = firebaseDbProperties;
  }

  @Bean
  public FirebaseApp initializeFirebaseApp() throws IOException {

    InputStream inputStream;

    if (firebaseDbProperties.getConfigcredentials() == null) {
      inputStream = gservicesConfig.getInputStream();
    } else {
      JSONObject jsonObject = new JSONObject(firebaseDbProperties.getConfigcredentials());
      inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
    }

    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream((inputStream)))
        .setDatabaseUrl(firebaseDbProperties.getUrl())
        .build();

    return FirebaseApp.initializeApp(options);
  }


}
