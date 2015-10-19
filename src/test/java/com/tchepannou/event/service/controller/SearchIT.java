package com.tchepannou.event.service.controller;

import com.tchepannou.core.http.Http;
import com.tchepannou.event.service.Starter;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({"/db/clean.sql", "/db/search.sql"})
public class SearchIT {
    private String transactionId = UUID.randomUUID().toString();

    //-- Test
    @Test
    public void should_returns_events (){
        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
        .when()
            .get("/v1/calendar/1000+1001/search/from/2015-11-10/to/2015-11-17")
        .then()
            .log()
                .all()
            .statusCode(HttpStatus.SC_OK)
            .body("events", hasSize(3))

            .body("events[0].id", is(100))
            .body("events[0].calendarId", is(1000))
            .body("events[0].type", is("game"))
            .body("events[0].name", is("vs Arsenal"))
            .body("events[0].description", is("This is a game"))
            .body("events[0].startDate", startsWith("2015-11-10"))
            .body("events[0].startTime", is("10:30"))
            .body("events[0].endTime", is("11:30"))
            .body("events[0].recurrenceId", nullValue())
            .body("events[0].requireRsvp", is(false))
            .body("events[0].address.street", is("3030 Linton"))
            .body("events[0].address.zipCode", is("H0H 0H0"))
            .body("events[0].address.city", is("Montreal"))
            .body("events[0].address.state", is("QC"))
            .body("events[0].address.country", is("CA"))
            .body("events[0].address.countryName", is("Canada"))
            .body("events[0].address.location", is("QC, Canada"))
            .body("events[0].place.id", is(190))
            .body("events[0].place.name", is("Location 1"))
            .body("events[0].place.website", is("http://location1.com"))
            .body("events[0].game.opponent", is("Arsenal"))
            .body("events[0].game.score1", is(1))
            .body("events[0].game.score2", is(0))
            .body("events[0].game.jerseyColor", is("red"))
            .body("events[0].game.home", is(true))
            .body("events[0].game.overtime", is(false))
            .body("events[0].game.outcome", is("win"))
            .body("events[0].game.duration", is(90))

            .body("events[1].id", is(101))
            .body("events[1].calendarId", is(1000))
            .body("events[1].type", is("practice"))
            .body("events[1].name", is("Practice101"))
            .body("events[1].description", is("This is a practice101"))
            .body("events[1].startDate", startsWith("2015-11-12"))
            .body("events[1].startTime", is("11:30"))
            .body("events[1].endTime", is("18:30"))
            .body("events[1].recurrenceId", is("43094039"))
            .body("events[1].requireRsvp", is(true))
            .body("events[1].address.street", is("340 Pascal"))
            .body("events[1].address.zipCode", is("H1K 1C6"))
            .body("events[1].address.city", is("Laval"))
            .body("events[1].address.state", is("QC"))
            .body("events[1].address.country", is("CA"))
            .body("events[1].address.countryName", is("Canada"))
            .body("events[1].address.location", is("QC, Canada"))
            .body("events[1].place.id", is(191))
            .body("events[1].place.name", is("Location 2"))
            .body("events[1].place.website", is("http://location2.com"))
            .body("events[2].game", nullValue())

            .body("events[2].id", is(102))
            .body("events[2].calendarId", is(1001))
            .body("events[2].type", is("practice"))
            .body("events[2].name", is("Practice102"))
            .body("events[2].description", is("This is a practice102"))
            .body("events[2].startDate", startsWith("2015-11-12"))
            .body("events[2].startTime", is("09:30"))
            .body("events[2].endTime", is("11:30"))
            .body("events[2].recurrenceId", nullValue())
            .body("events[2].requireRsvp", is(true))
            .body("events[2].address", nullValue())
            .body("events[2].place", nullValue())
            .body("events[2].game", nullValue())
        ;
        // @formatter:on
    }


    @Test
    public void should_returns_400_for_malformed_start_date (){
        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
        .when()
            .get("/v1/calendar/1000+1001/search/from/20151110/to/2015-11-17")
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("bad_date_format"))
        ;
        // @formatter:on
    }

    @Test
    public void should_returns_400_for_malformed_end_date (){
        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
        .when()
            .get("/v1/calendar/1000+1001/search/from/2015-11-10/to/20151117")
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("bad_date_format"))
        ;
        // @formatter:on
    }
}
