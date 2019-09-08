package io.jooby.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter implements ValueConverter {
  @Override public boolean supports(Class type) {
    return type == LocalDate.class;
  }

  @Override public Object convert(Class type, String value) {
    try {
      // must be millis
      Instant instant = Instant.ofEpochMilli(Long.parseLong(value));
      return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    } catch (NumberFormatException x) {
      // must be YYYY-MM-dd
      return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
    }
  }
}
