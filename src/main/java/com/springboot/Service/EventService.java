package com.springboot.Service;

import com.springboot.DTO.EventsDto;
import com.springboot.Model.Events;

import java.util.List;

public interface EventService {

    public List<EventsDto> getAllEvents(Double startLatitude, Double endLongitude, Integer pageNumber, Integer pageSize);
}
