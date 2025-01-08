package ec.edu.epn.monederovirtual.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Fecha o patrón no pueden ser nulos o vacíos.");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }
}