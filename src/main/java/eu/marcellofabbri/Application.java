package eu.marcellofabbri;

import io.micronaut.runtime.Micronaut;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

@Slf4j
public class Application {

    public static void main(String[] args) throws SchedulerException {
        Micronaut.run(Application.class, args);
    }
}
