package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.service.domain.Event;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventResponseMapper {
    private static final String TIME_FORMAT = "HH:mm";
    private Event event;

    public EventResponse map (){
        DateFormat fmt = new SimpleDateFormat(TIME_FORMAT);

        EventResponse response = new EventResponse();
        response.setCalendarId(event.getCalendarId());
        response.setCreated(event.getCreated());
        response.setDescription(event.getDescription());
        response.setEndTime(toString(event.getEndTime(), fmt));
        response.setId(event.getId());
        response.setName(event.getName());
        response.setRecurrenceId(event.getRecurrenceId());
        response.setRequireRsvp(event.getRequireRsvp());
        response.setStartDate(event.getStartDate());
        response.setStartTime(toString(event.getStartTime(), fmt));
        response.setType(toString(event.getType()));
        response.setUpdated(event.getUpdated());

        return response;
    }

    public EventResponseMapper withEvent(Event event) {
        this.event = event;
        return this;
    }

    private String toString (Time time, DateFormat fmt){
        return time != null ? fmt.format(time) : null;
    }

    private String toString (Enum enu){
        return enu != null ? enu.toString() : null;
    }
}
