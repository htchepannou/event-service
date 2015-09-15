package com.tchepannou.event.service.service;

public interface Command<I, O> {
    O execute(I request, CommandContext context);
}
