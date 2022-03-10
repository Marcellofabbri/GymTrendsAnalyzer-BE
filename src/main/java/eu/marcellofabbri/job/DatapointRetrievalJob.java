package eu.marcellofabbri.job;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import eu.marcellofabbri.config.GymConfiguration;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
@Singleton
public class DatapointRetrievalJob implements Job {

  public static final String JOB_NAME = "datapointRetrievalJob";

  private final RxHttpClient httpClient;

  public DatapointRetrievalJob(@Client("${gym.url.api}") RxHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Override
  public void execute(JobExecutionContext context) {
    log.info("Executing call");

    HttpRequest<?> req =
        HttpRequest.GET(UriBuilder.of("/datapoint").build())
            .header(USER_AGENT, GymConfiguration.USER_AGENT)
            .header(ACCEPT, GymConfiguration.ACCEPT);

    httpClient.retrieve(req);
  }
}
