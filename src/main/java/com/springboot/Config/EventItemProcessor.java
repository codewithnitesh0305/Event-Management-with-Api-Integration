package com.springboot.Config;

import com.springboot.Model.Events;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


public class EventItemProcessor implements ItemProcessor<Events,Events> {

    @Override
    public Events process(Events item) throws Exception {
        return item;
    }
}
