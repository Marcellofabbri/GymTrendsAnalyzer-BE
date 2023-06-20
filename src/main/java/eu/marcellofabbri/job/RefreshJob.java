package eu.marcellofabbri.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.marcellofabbri.dto.DatapointDto;
import eu.marcellofabbri.service.DataRetrieveService;
import eu.marcellofabbri.service.FileWriterService;
import eu.marcellofabbri.service.LoginService;
import io.micronaut.scheduling.annotation.Scheduled;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Singleton
public class RefreshJob {

    private final LoginService loginService;
    private final DataRetrieveService dataRetrieveService;
    private final ObjectMapper mapper;
    private final FileWriterService fileWriterService;

    public RefreshJob(LoginService loginService,
                      DataRetrieveService dataRetrieveService, FileWriterService fileWriterService) {
        this.loginService = loginService;
        this.dataRetrieveService = dataRetrieveService;
        this.fileWriterService = fileWriterService;
        this.mapper = new ObjectMapper();
    }

    @Scheduled(fixedDelay = "10s", cron = "00/1 * * * *")
    public void refresh() {
        fileWriterService.writeData(new DatapointDto(0, 0, LocalDateTime.now()));

        try {
            getDatapoint().subscribe(fileWriterService::writeData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private Flowable<DatapointDto> getDatapoint() throws JsonProcessingException {
        String tokenResponse = loginService.login().blockingFirst().body();
        Map response = mapper.readValue(tokenResponse, Map.class);
        String token = "Bearer " + response.get("access_token");
        return dataRetrieveService.fetchDatapoint(token);
    }
}
