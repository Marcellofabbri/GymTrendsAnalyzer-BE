package eu.marcellofabbri.service;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.AUTHORIZATION;
import static io.micronaut.http.HttpHeaders.USER_AGENT;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import eu.marcellofabbri.config.GymConfiguration;
import eu.marcellofabbri.dto.DatapointDto;
import io.micronaut.http.HttpRequest;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


@MicronautTest
public class DataRetrieveServiceTest {

  private DataRetrieveService service;

  private RxHttpClient httpClientMock;

  private DatapointDto datapointDto;

  @BeforeEach
  void setup() {
    httpClientMock = mock(RxHttpClient.class);
    service = new DataRetrieveService(httpClientMock);
    datapointDto = new DatapointDto();
  }

  @Test
  void shouldFetchDatapoint() {
    String token = "Bearer tOkEn";

    HttpRequest<?> mockRequest =
        HttpRequest.GET("/")
            .header(USER_AGENT, GymConfiguration.USER_AGENT)
            .header(ACCEPT, GymConfiguration.ACCEPT)
            .header(AUTHORIZATION, token);

    Flowable<DatapointDto> expected = Flowable.just(datapointDto);

    Mockito.when(httpClientMock.retrieve(any(HttpRequest.class), any(Class.class))).thenReturn(expected);

    Assertions.assertEquals(expected, service.fetchDatapoint(token));
    ArgumentCaptor<HttpRequest> requestCaptor = ArgumentCaptor.forClass(HttpRequest.class);
    ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
    Mockito.verify(httpClientMock, Mockito.times(1)).retrieve(requestCaptor.capture(), classCaptor.capture());
    Assertions.assertEquals(token, requestCaptor.getValue().getHeaders().get(AUTHORIZATION));
    Assertions.assertEquals(DatapointDto.class, classCaptor.getValue());
  }

}
