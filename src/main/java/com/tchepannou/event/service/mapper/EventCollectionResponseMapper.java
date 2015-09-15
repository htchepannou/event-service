package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.service.domain.Event;

import java.util.ArrayList;
import java.util.List;

public class EventCollectionResponseMapper {
    private List<Event> events = new ArrayList<>();

    public EventCollectionResponse map (){
        final EventResponseMapper mapper = new EventResponseMapper();
        final EventCollectionResponse response = new EventCollectionResponse();

        events.stream().forEach(event -> response.addEvent(mapper.withEvent(event).map()));

        return response;
    }

    public EventCollectionResponseMapper withEvents(List<Event> events) {
        this.events = events;
        return this;
    }
}
