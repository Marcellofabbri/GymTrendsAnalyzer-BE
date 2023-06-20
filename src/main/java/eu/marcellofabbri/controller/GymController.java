package eu.marcellofabbri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.marcellofabbri.dto.DatapointDto;
import eu.marcellofabbri.service.DataRetrieveService;
import eu.marcellofabbri.service.LoginService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static io.micronaut.http.MediaType.TEXT_CSV;

@Controller("/")
public class GymController {

  private final LoginService loginService;
  private final DataRetrieveService dataRetrieveService;
  private final ObjectMapper mapper;

  public GymController(LoginService loginService, DataRetrieveService dataRetrieveService) {
    this.loginService = loginService;
    this.dataRetrieveService = dataRetrieveService;
    this.mapper = new ObjectMapper();
  }

  @Get("/datapoint")
  public Flowable<DatapointDto> getDatapoint() throws JsonProcessingException {
    String tokenResponse = loginService.login().blockingFirst().body();
    Map response = mapper.readValue(tokenResponse, Map.class);
    String token = "Bearer " + response.get("access_token");
    return dataRetrieveService.fetchDatapoint(token);
  }

  @Get(value = "/download", produces = TEXT_CSV)
  public Flowable<String> download() throws IOException {
    return Flowable.just(readString());
  }

  private String readString() {
    try {
      return Files.readString(Path.of("./file.csv"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
