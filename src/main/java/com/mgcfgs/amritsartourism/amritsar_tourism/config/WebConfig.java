// package com.mgcfgs.amritsartourism.amritsar_tourism.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.format.Formatter;
// import org.springframework.format.FormatterRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.Locale;

// @Configuration
// public class WebConfig implements WebMvcConfigurer {

//     @Override
//     public void addFormatters(FormatterRegistry registry) {
//         registry.addFormatterForFieldType(LocalDate.class, new LocalDateFormatter());
//     }

//     private static class LocalDateFormatter implements Formatter<LocalDate> {

//         @Override
//         public LocalDate parse(String text, Locale locale) {
//             return LocalDate.parse(text, DateTimeFormatter.ISO_LOCAL_DATE);
//         }

//         @Override
//         public String print(LocalDate date, Locale locale) {
//             return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
//         }
//     }
// }