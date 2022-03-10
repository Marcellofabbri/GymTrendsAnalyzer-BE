package eu.marcellofabbri.job;

import io.micronaut.context.annotation.Context;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import javax.annotation.PostConstruct;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

@Singleton
public class DatapointRetrievalJobProcess {

  @Inject
  private static DatapointCronTrigger datapointCronTrigger;

  public static void start() {
    try {
      JobDetail job = JobBuilder.newJob(DatapointRetrievalJob.class)
          .withIdentity(DatapointRetrievalJob.JOB_NAME)
          .build();

      CronTrigger cronTrigger = datapointCronTrigger.createTrigger();

      SchedulerFactory schFactory = new StdSchedulerFactory();
      Scheduler sch = schFactory.getScheduler();
      sch.start();
      sch.scheduleJob(job, cronTrigger);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
