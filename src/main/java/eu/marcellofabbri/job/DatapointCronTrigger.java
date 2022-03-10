package eu.marcellofabbri.job;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

@Bean
public class DatapointCronTrigger {

  private static final String GROUP = "datapointRetrieval";
  private static final String TRIGGER_IDENTITY = "datapointRetrievalTrigger";

  @Value("${cron.hourly}")
//  @Property(name = "cron.hourly")
  protected static String schedule;

  public static CronTrigger createTrigger() {
    return TriggerBuilder.newTrigger()
        .withIdentity(TRIGGER_IDENTITY)
        .withSchedule(CronScheduleBuilder.cronSchedule("0/30 0/1 * 1/1 * ? *"))
        .forJob(DatapointRetrievalJob.JOB_NAME)
        .build();
  }
}
