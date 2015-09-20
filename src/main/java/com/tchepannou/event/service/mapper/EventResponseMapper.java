package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.domain.Game;
import com.tchepannou.event.service.domain.Place;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EventResponseMapper {
    private static final String TIME_FORMAT = "HH:mm";
    private Event event;
    private Address address;
    private Place place;
    private Game game;
    private AddressResponseMapper addressResponseMapper = new AddressResponseMapper();
    private PlaceResponseMapper locationResponseMapper = new PlaceResponseMapper();
    private GameResponseMapper gameResponseMapper = new GameResponseMapper();

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
        response.setStartDate(new Timestamp(event.getStartDate().getTime()));
        response.setStartTime(toString(event.getStartTime(), fmt));
        response.setType(toString(event.getType()));
        response.setUpdated(event.getUpdated());

        if (address != null) {
            response.setAddress(
                    addressResponseMapper.withAddress(address).map()
            );
        }
        if (place != null) {
            response.setPlace(
                    locationResponseMapper.withPlace(place).map()
            );
        }
        if (event.getType() == Event.Type.game && game != null){
            response.setGame(
                    gameResponseMapper.withGame(game).map()
            );
        }

        return response;
    }

    public EventResponseMapper withAddress(Address address) {
        this.address = address;
        return this;
    }

    public EventResponseMapper withEvent(Event event) {
        this.event = event;
        return this;
    }

    public EventResponseMapper withLocation(Place place) {
        this.place = place;
        return this;
    }

    public EventResponseMapper withGame(Game game) {
        this.game = game;
        return this;
    }

    private String toString (Time time, DateFormat fmt){
        return time != null ? fmt.format(time) : null;
    }

    private String toString (Enum enu){
        return enu != null ? enu.toString() : null;
    }
}
