package PatientApiV2.PatientApiV2.shared.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapper {

    public String formatLocalDate(LocalDate date , String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String formatLocalTime(LocalTime time , String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }
}

