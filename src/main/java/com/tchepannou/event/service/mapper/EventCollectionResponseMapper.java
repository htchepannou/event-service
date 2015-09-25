package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.domain.Game;
import com.tchepannou.event.service.domain.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventCollectionResponseMapper {
    private List<Event> events = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private List<Place> places = new ArrayList<>();
    private List<Game> games = new ArrayList<>();

    public EventCollectionResponse map (){
        final EventResponseMapper mapper = new EventResponseMapper();
        final EventCollectionResponse response = new EventCollectionResponse();

        final Map<Long, Address> addressMap = addresses.stream()
                .collect(Collectors.toMap(a -> a.getId(), a -> a)
        );

        final Map<Long, Place> locationMap = places.stream()
                .collect(Collectors.toMap(loc -> loc.getId(), loc -> loc)
        );

        final Map<Long, Game> gameMap = games.stream()
                .collect(Collectors.toMap(game -> game.getId(), game -> game)
        );

        events.stream().forEach(event ->
                response.addEvent(
                        mapper
                            .withEvent(event)
                            .withAddress(event.getAddressId() != null ? addressMap.get(event.getAddressId()) : null)
                            .withLocation(event.getPlaceId() != null ? locationMap.get(event.getPlaceId()) : null)
                            .withGame(gameMap.get(event.getId()))
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

    public EventCollectionResponseMapper withGames(List<Game> games) {
        this.games = games;
        return this;
    }
}
