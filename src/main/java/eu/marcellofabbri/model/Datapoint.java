package eu.marcellofabbri.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Datapoint {

  int peopleAtTheGym;
  int peopleInClasses;
  LocalDateTime when;
  String where;
}
