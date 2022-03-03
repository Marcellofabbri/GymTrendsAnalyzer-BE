package eu.marcellofabbri.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("gym.url")
public interface GymConfiguration {

  String USER_AGENT = "Micronaut HTTP Client";
  String ACCEPT = "application/vnd.github.v3+json, application/json";

  String getMember();

  String getApi();

  String getLogin();
}
