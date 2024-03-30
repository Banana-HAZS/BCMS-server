package com.banana.tool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTool {
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime toLocalDate(String dateTimeStr) {
        LocalDate ld = LocalDate.parse(dateTimeStr, df);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());

        return ldt;
    }
}
