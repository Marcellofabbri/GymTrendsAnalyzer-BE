package eu.marcellofabbri.job;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

import eu.marcellofabbri.config.GymConfiguration;
import eu.marcellofabbri.dto.DatapointDto;
import io.micronaut.context.annotation.Bean;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Flowable;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@Slf4j
@Singleton
public class DatapointRetrievalJob {

  public static final String JOB_NAME = "datapointRetrievalJob";

  private final RxHttpClient httpClient;

  public DatapointRetrievalJob(@Client("${app.service.url}") RxHttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Scheduled(cron = "0/1 * * * *")
  public void execute() {
    log.info("Executing call");

    HttpRequest<?> req =
        HttpRequest.GET(UriBuilder.of("/datapoint").build())
            .header(USER_AGENT, GymConfiguration.USER_AGENT)
            .header(ACCEPT, GymConfiguration.ACCEPT);

    Flowable<DatapointDto> datapoint = httpClient.retrieve(req, DatapointDto.class);
    log.info(String.valueOf(datapoint.blockingSingle().getTotalPeopleInGym()));
  }
}
