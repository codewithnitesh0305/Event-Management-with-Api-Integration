package com.springboot.Controller;

import com.springboot.DTO.EventsDto;
import com.springboot.Model.Events;
import com.springboot.Response.ApiResponse;
import com.springboot.Service.EventService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.image.ImageProducer;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventsController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private EventService eventService;

    @PostMapping("/importCSV")
    public void importCSVIntoDB() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameter = new JobParametersBuilder()
                .addLong("startTime", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(job, jobParameter);
    }

    @GetMapping("/allEvents")
    public ResponseEntity<?> getAllEvents(@RequestParam Double startLatitude, @RequestParam Double endLongitude,
                                          @RequestParam(value = "pageNumber", defaultValue = "1",required = false) Integer pageNumber,
                                          @RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize,
                                          @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Map<String, Object> allEvents = eventService.getAllEvents(startLatitude, endLongitude, pageNumber, pageSize, date);
        List<EventsDto> allEvent = (List<EventsDto>) allEvents.get("allEvents");
        Long totalEvents = (Long) allEvents.get("totalEvents");
        Integer totalPage = (Integer) allEvents.get("totalPage");
        ApiResponse<List<EventsDto>> apiResponse = new ApiResponse<>("Success",HttpStatus.OK.value(),allEvent,pageNumber,pageSize,totalEvents,totalPage);
        return new ResponseEntity<ApiResponse<List<EventsDto>>>(apiResponse, HttpStatus.OK);
    }
}
