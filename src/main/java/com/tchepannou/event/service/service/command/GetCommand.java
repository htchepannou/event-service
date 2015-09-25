package com.tchepannou.event.service.service.command;

import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.service.dao.AddressDao;
import com.tchepannou.event.service.dao.EventDao;
import com.tchepannou.event.service.dao.GameDao;
import com.tchepannou.event.service.dao.PlaceDao;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.domain.Game;
import com.tchepannou.event.service.domain.Place;
import com.tchepannou.event.service.mapper.EventResponseMapper;
import com.tchepannou.event.service.service.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.context.WebApplicationContext;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class GetCommand extends AbstractCommand<Void, EventResponse>{
    @Autowired
    EventDao eventDao;

    @Autowired
    AddressDao addressDao;

    @Autowired
    PlaceDao placeDao;

    @Autowired
    GameDao gameDao;

    @Override
    protected EventResponse doExecute(Void request, CommandContext context) {
        final Event event = eventDao.findById(context.getId());

        final Address address = event.getAddressId() != null
                ? addressDao.findById(event.getAddressId())
                : null;

        final Place place = event.getPlaceId() != null
                ? placeDao.findById(event.getPlaceId())
                : null;


        Game game = null;
        if (Event.Type.game.equals(event.getType())){
            try{
                game = gameDao.findById(event.getId());
            } catch (EmptyResultDataAccessException e) {    // NOSONAR

            }
        }

        return new EventResponseMapper()
                .withEvent(event)
                .withAddress(address)
                .withLocation(place)
                .withGame(game)
                .map();
    }
}
