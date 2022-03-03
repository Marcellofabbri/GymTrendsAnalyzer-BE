package eu.marcellofabbri.service;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.AUTHORIZATION;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import eu.marcellofabbri.config.GymConfiguration;
import eu.marcellofabbri.dto.DatapointDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.reactivex.Flowable;
import java.net.URI;
import javax.inject.Singleton;

@Singleton
public class DataRetrieveService {

  private final RxHttpClient httpClient;
  private final URI uri;

  public DataRetrieveService(@Client("${gym.url.api}") RxHttpClient httpClient) {
    this.httpClient = httpClient;
    this.uri = UriBuilder.of("/").build();
  }

  public Flowable<DatapointDto> fetchDatapoint(String token) {
    HttpRequest<?> req =
        HttpRequest.GET(uri)
            .header(USER_AGENT, GymConfiguration.USER_AGENT)
            .header(ACCEPT, GymConfiguration.ACCEPT)
            .header(AUTHORIZATION, token);
    return httpClient.retrieve(req, DatapointDto.class);
  }
}
