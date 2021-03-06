package com.tchepannou.event.service.dao;

import com.tchepannou.event.service.domain.Address;

import java.util.Collection;
import java.util.List;

public interface AddressDao {
    Address findById (long id);

    List<Address> findByIds (Collection<Long> ids);
}
