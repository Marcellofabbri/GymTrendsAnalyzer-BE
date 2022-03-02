package eu.marcellofabbri.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DatapointDto {

  int peopleAtTheGym;
  int peopleInClasses;
  LocalDateTime when;
  String where;
}
