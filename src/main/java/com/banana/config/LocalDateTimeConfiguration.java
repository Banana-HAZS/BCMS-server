// package com.banana.config;
//
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
// import org.springframework.context.annotation.Bean;
//
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
//
// public class LocalDateTimeConfiguration {
//
//     @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
//     private String pattern;
//
//     @Bean
//     public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
//         return builder -> {
//             DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//
//             //返回时间数据序列化
//             builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
//             //接收时间数据反序列化
//             builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
//         };
//     }
// }
