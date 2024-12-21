package com.springboot.Config;

import com.springboot.Model.Events;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomFieldSetMapper implements FieldSetMapper<Events> {

    /*This Class is converting the CSV data (are in string) into the Class Events*/

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Events mapFieldSet(FieldSet fieldSet) throws BindException {
        Events event = new Events();
        event.setEvent_name(fieldSet.readString("event_name"));
        event.setCity_name(fieldSet.readString("city_name"));

        // Manually converting String to LocalDate
        String dateString = fieldSet.readString("date");
        event.setDate(LocalDate.parse(dateString, DATE_FORMAT)); // Correct conversion

        event.setTime(fieldSet.readString("time"));
        event.setLatitude(fieldSet.readDouble("latitude"));
        event.setLongitude(fieldSet.readDouble("longitude"));

        return event;
    }
}
