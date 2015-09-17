package com.tchepannou.event.service.mapper;

import com.google.common.base.Strings;
import com.tchepannou.event.client.v1.AddressInfo;
import com.tchepannou.event.service.domain.Address;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

public class AddressInfoMapper {
    private Address address;
    private static final Map<String, Locale> LOCALE3;

    static{
        LOCALE3 = new HashMap<>();
        for (Locale locale : Locale.getAvailableLocales()){
            try {
                String code = locale.getISO3Country();
                if (!Strings.isNullOrEmpty(code) && !LOCALE3.containsKey(code)) {
                    LOCALE3.put(code, new Locale("", locale.getCountry()));
                }
            } catch (MissingResourceException e) {  // NOSONAR

            }
        }
    }

    public AddressInfo map (){
        if (address.isEmpty ()){
            return null;
        }

        AddressInfo info = new AddressInfo();
        info.setCity(address.getCity());
        info.setState(address.getState());
        info.setStreet(address.getStreet());
        info.setZipCode(address.getZipCode());

        String country = address.getCountry();
        final Locale locale = toLocale(country);
        if (locale != null) {
            info.setCountry(locale.getCountry());
            info.setCountryName(locale.getDisplayName());
        }
        return info;
    }

    public AddressInfoMapper withAddress(Address address) {
        this.address = address;
        return this;
    }

    private Locale toLocale(final String country){
        if (country == null) {
            return null;
        }
        else if (country.length() == 2){
            return new Locale("", country);
        } else {
            return LOCALE3.get(country);
        }
    }

}
