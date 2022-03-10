package eu.marcellofabbri.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.micronaut.http.MutableHttpRequest;
import io.micronaut.rxjava2.http.client.RxHttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

@MicronautTest
public class LoginServiceTest {

  private LoginService service;

  private RxHttpClient httpClient;

  private static final String USERNAME = "username";

  private static final String PIN = "pin";

  @BeforeEach
  void setup() {
    httpClient = Mockito.mock(RxHttpClient.class);
    service = new LoginService(httpClient, USERNAME, PIN);
  }

  @Test
  void shouldSendHttpCallToLogin() {
    ArgumentCaptor<MutableHttpRequest> httpRequestCaptor = ArgumentCaptor.forClass(MutableHttpRequest.class);
    ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);

    service.login();

    verify(httpClient, times(1)).exchange(httpRequestCaptor.capture(), eq(String.class));
    Map<String, String> body = (Map) httpRequestCaptor.getValue().getBody().get();
    assertEquals(USERNAME, body.get("username"));
    assertEquals(PIN, body.get("password"));
  }
}
