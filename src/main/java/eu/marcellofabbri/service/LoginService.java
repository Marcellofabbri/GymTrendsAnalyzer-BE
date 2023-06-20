package eu.marcellofabbri.service;

import static io.micronaut.http.HttpHeaders.USER_AGENT;
import static io.micronaut.http.HttpRequest.POST;

import eu.marcellofabbri.config.GymConfiguration;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.reactivex.Flowable;

import javax.inject.Singleton;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class LoginService {

  protected final String username;
  protected final String pin;
  private final RxHttpClient httpClient;
  private final String loginUrl;
  private GymConfiguration configuration;

  public LoginService(
      @Client("${gym.url.login}") RxHttpClient httpClient,
      @Value("${gym.username}") String username,
      @Value("${gym.pin}") String pin,
      @Value("${gym.url.login}") String url) {
    this.httpClient = httpClient;
    this.username = username;
    this.pin = pin;
    this.loginUrl = url;
  }

  public Flowable<HttpResponse<String>> login() {
    Map<String, String> data = new LinkedHashMap<>();
    data.put("grant_type", "password");
    data.put("username", this.username);
    data.put("password", this.pin);
    data.put("scope", "pgcapi");
    data.put("client_id", "ro.client");
    return httpClient.exchange(
        POST(loginUrl, data)
                .header(USER_AGENT, "PureGym/1523 CFNetwork/1312 Darwin/21.0.0")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED), String.class);
  }
}
