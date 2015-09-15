package com.tchepannou.event.service.domain;

import java.sql.Time;
import java.util.Date;

public class Event extends Model {
    public enum Type {
        game(1),
        practice(2),
        tournament(3),
        other(4);

        final int code;

        Type (final int code){
            this.code = code;
        }

        public int code (){
            return code;
        }

        public static Type fromCode (int code){
            for (final Type type : Type.values()){
                if (type.code == code){
                    return type;
                }
            }
            return null;
        }
    };

    //-- Attributes
    private long id;
    private long calendarId;
    private Long locationId;
    private Type type;
    private String name;
    private String description;
    private Date startDate;
    private Time startTime;
    private Time endTime;
    private String recurrenceId;
    private Boolean requireRsvp;
    private Date created;
    private Date updated;

    //-- Getter/Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getRecurrenceId() {
        return recurrenceId;
    }

    public void setRecurrenceId(String recurrenceId) {
        this.recurrenceId = recurrenceId;
    }

    public void setRequireRsvp(Boolean requireRsvp) {
        this.requireRsvp = requireRsvp;
    }

    public void setCalendarId(long calendarId) {
        this.calendarId = calendarId;
    }

    public Boolean getRequireRsvp() {
        return requireRsvp;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
