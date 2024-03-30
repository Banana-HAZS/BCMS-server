package com.banana.tool;

import java.time.LocalDateTime;

public class TimeCalculator {
    public static LocalDateTime endDate(LocalDateTime startDate, int time){
        switch (time){
            case 1:
                return startDate.plusYears(1L);
            case 2:
                return startDate.plusMonths(6L);
            case 3:
                return startDate.plusMonths(3L);
            case 4:
                return startDate.plusMonths(1L);
            case 5:
                return startDate.plusMonths(4L);
        }
        return null;
    }
}
