package eu.marcellofabbri;

import eu.marcellofabbri.job.DatapointRetrievalJobProcess;
import io.micronaut.runtime.Micronaut;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);

        DatapointRetrievalJobProcess.start();
    }
}
