package com.read.readbibleservice.config;

import com.read.readbibleservice.config.properties.FirebaseDbProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

  private final FirebaseDbProperties firebaseDbProperties;


  public WebClientConfiguration(FirebaseDbProperties firebaseDbProperties) {
    this.firebaseDbProperties = firebaseDbProperties;
  }

  @Bean(name = "webClientFirebase")
  public WebClient webClientConfig(WebClient.Builder webClient) {
    return webClient.baseUrl(firebaseDbProperties.getUrl())
        .build();
  }
}
