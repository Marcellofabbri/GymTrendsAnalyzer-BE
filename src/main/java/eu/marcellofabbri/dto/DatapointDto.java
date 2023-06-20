package eu.marcellofabbri.dto;

import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Value;

@Value
@Introspected
public class DatapointDto {

  int totalPeopleInGym;
  int totalPeopleInClasses;
  LocalDateTime attendanceTime;
}
