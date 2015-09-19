package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.domain.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventCollectionResponseMapper {
    private List<Event> events = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private List<Place> places = new ArrayList<>();

    public EventCollectionResponse map (){
        final EventResponseMapper mapper = new EventResponseMapper();
        final EventCollectionResponse response = new EventCollectionResponse();

        final Map<Long, Address> addressMap = addresses.stream()
                .collect(Collectors.toMap(a -> a.getId(), a -> a)
        );

        final Map<Long, Place> locationMap = places.stream()
                .collect(Collectors.toMap(loc -> loc.getId(), loc -> loc)
        );

        events.stream().forEach(event ->
                response.addEvent(
                        mapper
                            .withEvent(event)
                            .withAddress(event.getAddressId() != null ? addressMap.get(event.getAddressId()) : null)
                            .withLocation(event.getLocationId() != null ? locationMap.get(event.getLocationId()) : null)
                            .map()
                )
        );

        return response;
    }

    public EventCollectionResponseMapper withEvents(List<Event> events) {
        this.events = events;
        return this;
    }

    public EventCollectionResponseMapper withAddresses(List<Address> addresses) {
        this.addresses = addresses;
        return this;
    }

    public EventCollectionResponseMapper withLocations(List<Place> places) {
        this.places = places;
        return this;
    }
}
