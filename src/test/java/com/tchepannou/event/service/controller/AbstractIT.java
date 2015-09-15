package com.tchepannou.event.service.controller;

import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractIT {
    @Value("${server.port}")
    private int port;

    @Before
    public final void setUp() {
        RestAssured.port = port;
    }


}
