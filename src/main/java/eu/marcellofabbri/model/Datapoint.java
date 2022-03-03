package eu.marcellofabbri.model;

import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Introspected
public class Datapoint {

  int totalPeopleInGym;
  int totalPeopleInClasses;
  LocalDateTime attendanceTime;
}
