package main.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
    public static Date localDateToDate(LocalDate d) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        return Date.from(d.atStartOfDay(defaultZoneId).toInstant());
    }

    public static LocalDate dateToLocalDate(Date d) {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Instant instant = d.toInstant();
        return instant.atZone(defaultZoneId).toLocalDate();
    }
}
