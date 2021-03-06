package com.tchepannou.event.service.dao;

import com.tchepannou.event.service.domain.Place;

import java.util.Collection;
import java.util.List;

public interface PlaceDao {
    Place findById (long id);

    List<Place> findByIds (Collection<Long> ids);
}
