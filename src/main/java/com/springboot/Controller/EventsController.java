package com.springboot.Controller;

import com.springboot.DTO.EventsDto;
import com.springboot.Model.Events;
import com.springboot.Service.EventService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> getAllEvents(@RequestParam Double startLatitude, @RequestParam Double endLongitude, @RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,@RequestParam(value = "pageSize", defaultValue = "5",required = false) Integer pageSize) {
        //     Double startLatitude = 28.667856;
//            Double startLongitude = 77.449791;
        List<EventsDto> allEvents = eventService.getAllEvents(startLatitude, endLongitude, pageNumber, pageSize);
        return new ResponseEntity<List<EventsDto>>(allEvents, HttpStatus.OK);
    }
}
