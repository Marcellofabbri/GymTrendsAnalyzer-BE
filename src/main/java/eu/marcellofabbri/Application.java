package eu.marcellofabbri;

import io.micronaut.runtime.Micronaut;
import org.quartz.SchedulerException;

public class Application {

    public static void main(String[] args) throws SchedulerException {
        Micronaut.run(Application.class, args);
    }
}
