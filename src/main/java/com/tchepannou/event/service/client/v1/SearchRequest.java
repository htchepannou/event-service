package com.tchepannou.event.service.client.v1;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

public class SearchRequest {
    //-- Attributes
    private Collection<Long> calendarIds = new HashSet<>();
    private Date startDate;
    private Date endDate;

    //-- Getter/Setter
    public Collection<Long> getCalendarIds() {
        return calendarIds;
    }

    public void setCalendarIds(Collection<Long> calendarIds) {
        this.calendarIds = calendarIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
