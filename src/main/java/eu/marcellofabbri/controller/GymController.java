package eu.marcellofabbri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.marcellofabbri.dto.DatapointDto;
import eu.marcellofabbri.service.DataRetrieveService;
import eu.marcellofabbri.service.LoginService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;
import java.util.Map;

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
  Flowable<DatapointDto> getDatapoint() throws JsonProcessingException {
    String tokenResponse = loginService.login().blockingFirst().body();
    Map response = mapper.readValue(tokenResponse, Map.class);
    String token = "Bearer " + response.get("access_token");
    return dataRetrieveService.fetchDatapoint(token);
  }
}
