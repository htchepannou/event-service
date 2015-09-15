package com.tchepannou.event.service.controller;

import com.tchepannou.event.service.service.CommandContext;

public class CommandContextImpl implements CommandContext {
    //-- Attributes
    private long id;
    private long calendarId;
    private long userId;
    private String transactionId;
    private int limit;
    private int offset;

    //-- Public
    public CommandContextImpl withCalendarId(long blogId){
        this.calendarId = blogId;
        return this;
    }

    public CommandContextImpl withUserId(long userId){
        this.userId = userId;
        return this;
    }

    public CommandContextImpl withLimit (int limit){
        this.limit = limit;
        return this;
    }

    public CommandContextImpl withOffset (int offset){
        this.offset = offset;
        return this;
    }

    public CommandContextImpl withId (long id){
        this.id = id;
        return this;
    }

    public CommandContextImpl withTransactionId (String transactionId){
        this.transactionId = transactionId;
        return this;
    }

    //-- CommandContext overrides
    @Override
    public long getId() {
        return id;
    }

    @Override
    public long getCalendarId() {
        return calendarId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }
}
