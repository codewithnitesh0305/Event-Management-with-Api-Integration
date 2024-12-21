package com.springboot.Service;

import com.springboot.DTO.EventsDto;
import com.springboot.Model.Events;
import com.springboot.Repository.EventsRepository;
import com.springboot.Response.DistanceResponse;
import com.springboot.Response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    private EventsRepository eventsRepository;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private DistanceService distanceService;

    @Override
    public List<EventsDto> getAllEvents(Double startLatitude, Double endLongitude, Integer pageNumber, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC,"Date");
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<EventsDto> eventsDtoList = new ArrayList<>();
        Page<Events> eventsPage = eventsRepository.findAll(pageable);
        List<Events> allEvents = eventsPage.getContent();
        LocalDate startDate = LocalDate.of(2024, 4, 16); // Start from April 16, 2024
        LocalDate endDate = startDate.plusDays(5);
        allEvents = allEvents.stream().filter(data -> !data.getDate().isBefore(startDate) && !data.getDate().isAfter(endDate)).sorted(Comparator.comparing(Events::getDate)).collect(Collectors.toList());
        for (Events events : allEvents) {
            EventsDto dto = new EventsDto();
            dto.setEvent_name(events.getEvent_name());
            dto.setCity_name(events.getCity_name());
            dto.setDate(events.getDate());

            // Fetch weather data
            WeatherResponse weatherResponse = weatherService.getWeather(events.getLatitude(), events.getLongitude());
            if (weatherResponse != null && weatherResponse.getMain() != null) {
                String temperature = String.format("%.2f", weatherResponse.getMain().getTemp());
                String descriptions = weatherResponse.getWeather().stream()
                        .map(WeatherResponse.Weather::getDescription)
                        .collect(Collectors.joining(", "));
                dto.setWeather(temperature + "°C  " + descriptions);
            } else {
                dto.setWeather(null);
            }
            DistanceResponse distanceResponse = distanceService.calculateDistance(startLatitude,endLongitude,events.getLatitude(),events.getLongitude());
            String distance_km = distanceResponse.getDistance();
            dto.setDistance_km(Double.parseDouble(distance_km));
            eventsDtoList.add(dto);
        }
        return eventsDtoList; // Return the complete list of DTOs
    }




    public EventsDto convertIntoDto(EventsDto events) {
        EventsDto eventsDto = new EventsDto();
        eventsDto.setEvent_name(events.getEvent_name());
        eventsDto.setCity_name(events.getCity_name());
        eventsDto.setDate(events.getDate());
        eventsDto.setWeather(events.getWeather());
        eventsDto.setDistance_km(1234.098766);
        return eventsDto;
    }
}
