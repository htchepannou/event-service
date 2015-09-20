package com.tchepannou.event.service.service.command;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.SearchRequest;
import com.tchepannou.event.service.dao.AddressDao;
import com.tchepannou.event.service.dao.EventDao;
import com.tchepannou.event.service.dao.GameDao;
import com.tchepannou.event.service.dao.PlaceDao;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.domain.Game;
import com.tchepannou.event.service.domain.Place;
import com.tchepannou.event.service.mapper.EventCollectionResponseMapper;
import com.tchepannou.event.service.service.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class SearchCommand extends AbstractCommand<SearchRequest, EventCollectionResponse>{
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Autowired
    EventDao eventDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    PlaceDao locationDao;

    @Autowired
    GameDao gameDao;

    @Override
    protected EventCollectionResponse doExecute(SearchRequest request, CommandContext context) {
        try {

            DateFormat df = new SimpleDateFormat(DATE_FORMAT);

            final List<Event> events = eventDao.search(
                    request.getCalendarIds(),
                    df.parse(request.getStartDate()),
                    df.parse(request.getEndDate()),
                    request.getLimit(),
                    request.getOffset()
            );

            Set<Long> addressIds = events.stream()
                    .map(Event::getAddressId)
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());
            List<Address> addresses = addressDao.findByIds(addressIds);

            Set<Long> locationIds = events.stream()
                    .map(Event::getLocationId)
                    .filter(id -> id != null)
                    .collect(Collectors.toSet());
            final List<Place> places = locationDao.findByIds(locationIds);

            Set<Long> gameIds = events.stream()
                    .filter(event -> Event.Type.game.equals(event.getType()))
                    .map(Event::getId)
                    .collect(Collectors.toSet());
            final List<Game> games = gameDao.findByIds(gameIds);

            return new EventCollectionResponseMapper()
                    .withEvents(events)
                    .withAddresses(addresses)
                    .withLocations(places)
                    .withGames(games)
                    .map();

        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
