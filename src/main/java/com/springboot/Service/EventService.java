package com.springboot.Service;

import com.springboot.DTO.EventsDto;
import com.springboot.Model.Events;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface EventService {

    public Map<String, Object> getAllEvents(Double startLatitude, Double endLongitude, Integer pageNumber, Integer pageSize, LocalDate startDate);
}
