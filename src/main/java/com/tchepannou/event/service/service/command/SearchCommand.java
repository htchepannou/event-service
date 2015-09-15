package com.tchepannou.event.service.service.command;

import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.SearchRequest;
import com.tchepannou.event.service.dao.EventDao;
import com.tchepannou.event.service.domain.Event;
import com.tchepannou.event.service.mapper.EventCollectionResponseMapper;
import com.tchepannou.event.service.service.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class SearchCommand extends AbstractCommand<SearchRequest, EventCollectionResponse>{
    private static final String DATE_FORMAT = "yyyy/MM/dd";

    @Autowired
    EventDao dao;

    @Override
    protected EventCollectionResponse doExecute(SearchRequest request, CommandContext context) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            List<Event> events = dao.search(
                    request.getCalendarIds(),
                    df.parse(request.getStartDate()),
                    df.parse(request.getEndDate()),
                    30, 0
            );

            return new EventCollectionResponseMapper().withEvents(events).map();
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
