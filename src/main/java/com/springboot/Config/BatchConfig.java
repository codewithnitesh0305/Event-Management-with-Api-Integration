package com.springboot.Config;

import com.springboot.Model.Events;
import com.springboot.Repository.EventsRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private EventsRepository eventsRepository;

    //Reader
    /*This method is responsible for read the csv file data and pass to lineMapper to convert into entity class*/
    @Bean
    public FlatFileItemReader<Events> reader() {
        FlatFileItemReader<Events> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("EventRecords.csv")); //File name that is present in resource folder
        reader.setName("csv-reader");
        reader.setLinesToSkip(1); //Skip the first row of csv file
        reader.setLineMapper(lineMapper());
        return reader;
    }

//    private LineMapper<Events> lineMapper(){
//        DefaultLineMapper<Events> lineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
//        delimitedLineTokenizer.setDelimiter(",");
//        delimitedLineTokenizer.setStrict(false);
//        delimitedLineTokenizer.setNames("event_name","city_name","date","time","latitude","longitude");
//
//        BeanWrapperFieldSetMapper<Events> eventsBeanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        eventsBeanWrapperFieldSetMapper.setTargetType(Events.class);
//        eventsBeanWrapperFieldSetMapper.setDistanceLimit(0);
//        lineMapper.setLineTokenizer(delimitedLineTokenizer);
//        lineMapper.setFieldSetMapper(eventsBeanWrapperFieldSetMapper);
//        return lineMapper;
//    }

    /*This method is responsible for mapping the csv file data to the entity class*/
    private LineMapper<Events> lineMapper() {
        DefaultLineMapper<Events> lineMapper = new DefaultLineMapper<>();
        //The DelimitedLineTokenizer is used to brake each line into an individual value separated by comma
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames("event_name", "city_name", "date", "time", "latitude", "longitude");

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new CustomFieldSetMapper()); // Ensure this is used

        return lineMapper;
    }

    //Processor
    /*The processor transforms the Events object after it has been read from the file. */
    @Bean
    public EventItemProcessor processor() {
        return new EventItemProcessor();
    }

    //Writer
    /*This part writes each Events object to the database using the EventsRepository */
    @Bean
    public RepositoryItemWriter<Events> writer() {
        RepositoryItemWriter<Events> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(eventsRepository); // This line save the data from the database
        repositoryItemWriter.setMethodName("save"); // Use the save method of repository to save the data in database
        return repositoryItemWriter;
    }

    /*A step defines the processing logic in Spring Batch. Here, we define a step to read the CSV file, process the data, and then write it to the database in chunks*/
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("step1", jobRepository).<Events, Events>chunk(10, platformTransactionManager) //// Process 10 events at a time
                .reader(reader()) // Use the reader to read the data
                .processor(processor()) //// Use the processor to process the data
                .writer(writer()) //// Use the writer to save the data
                .build();
    }

    /*A job is a collection of steps that Spring Batch will execute. Here, we configure a simple job that consists of just one step*/
    @Bean
    public Job job(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("importCSV", jobRepository)
                .flow(step1(jobRepository, platformTransactionManager))
                .end().build();
    }
}
