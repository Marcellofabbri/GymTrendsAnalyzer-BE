package eu.marcellofabbri.service;

import eu.marcellofabbri.dto.DatapointDto;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class FileWriterService {

    private final String filePath;

    public FileWriterService(@Value("${file.path}") String filePath) {
        this.filePath = filePath;
    }

    public void writeData(DatapointDto datapointDto) {
        try (FileWriter fileWriter = new FileWriter(filePath, true)) {

            LocalDateTime attendanceTime = datapointDto.getAttendanceTime();

            String dayOfTheWeek = attendanceTime.getDayOfWeek().toString();
            String date = attendanceTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String time = attendanceTime.format(DateTimeFormatter.ofPattern("HH:mm"));
            int peopleInTotal = datapointDto.getTotalPeopleInGym();

            fileWriter.write(String.format("%s,%s,%s,%s\n", dayOfTheWeek, date, time, peopleInTotal));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
