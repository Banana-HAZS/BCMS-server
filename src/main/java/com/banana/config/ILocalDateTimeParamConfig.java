// package com.banana.config;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.convert.converter.Converter;
//
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.time.LocalTime;
// import java.time.format.DateTimeFormatter;
//
// @Configuration
// public class ILocalDateTimeParamConfig {
//     private static final String localDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
//     private static final String localDateFormat = "yyyy-MM-dd";
//     private static final String localTimeFormat = "HH:mm:ss";
//
//     @Bean
//     public Converter<String, LocalDateTime> localDateTimeConverter() {
//         return new LocalDateTimeConverter();
//     }
//
//     @Bean
//     public Converter<String, LocalDate> localDateConverter() {
//         return new LocalDateConverter();
//     }
//
//     @Bean
//     public Converter<String, LocalTime> localTimeConverter() {
//         return new LocalTimeConverter();
//     }
//
//     public static class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
//         @Override
//         public LocalDateTime convert(String text) {
//             return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(localDateTimeFormat));
//         }
//     }
//
//     public static class LocalDateConverter implements Converter<String, LocalDate> {
//         @Override
//         public LocalDate convert(String text) {
//             return LocalDate.parse(text, DateTimeFormatter.ofPattern(localDateFormat));
//         }
//     }
//
//     public static class LocalTimeConverter implements Converter<String, LocalTime> {
//         @Override
//         public LocalTime convert(String text) {
//             return LocalTime.parse(text, DateTimeFormatter.ofPattern(localTimeFormat));
//         }
//     }
// }
