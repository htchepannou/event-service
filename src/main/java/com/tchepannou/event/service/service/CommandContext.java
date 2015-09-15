package com.tchepannou.event.service.service;

public interface CommandContext {
    long getId();
    long getCalendarId();
    long getUserId();
    String getTransactionId();
    int getLimit();
    int getOffset();
}
