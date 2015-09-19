package com.tchepannou.event.service.mapper;

import com.tchepannou.event.client.v1.PlaceResponse;
import com.tchepannou.event.service.domain.Address;
import com.tchepannou.event.service.domain.Place;

public class PlaceResponseMapper {
    private Place place;
    private Address address;
    private AddressResponseMapper addressResponseMapper = new AddressResponseMapper();


    public PlaceResponse map (){
        PlaceResponse response = new PlaceResponse();
        response.setId(place.getId());
        response.setName(place.getName());
        response.setWebsite(place.getWebsite());

        if (address != null) {
            response.setAddress(
                    addressResponseMapper
                            .withAddress(address)
                            .map()
            );
        }
        return response;
    }

    public PlaceResponseMapper withPlace(Place place) {
        this.place = place;
        return this;
    }

    public PlaceResponseMapper withAddress(Address address) {
        this.address = address;
        return this;
    }
}
