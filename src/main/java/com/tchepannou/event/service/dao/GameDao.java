package com.tchepannou.event.service.dao;

import com.tchepannou.event.service.domain.Game;

import java.util.Collection;
import java.util.List;

public interface GameDao {
    List<Game> findByIds (Collection<Long> ids);
}
