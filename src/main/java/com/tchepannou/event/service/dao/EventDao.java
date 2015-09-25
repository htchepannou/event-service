package com.tchepannou.event.service.dao;

import com.tchepannou.event.service.domain.Event;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventDao {
    Event findById (long id);

    List<Event> search (Collection<Long> calendarIds, Date startDate, Date endDate, int limit, int offset);
}
