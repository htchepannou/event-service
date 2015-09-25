package com.tchepannou.event.service.controller;

import com.jayway.restassured.http.ContentType;
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
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({"/db/clean.sql", "/db/get.sql"})
public class GetIT {
    private String transactionId = UUID.randomUUID().toString();

    //-- Test
    @Test
    public void should_returns_events (){
        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
        .when()
            .get("/v1/calendar/event/100")
        .then()
            .log()
                .all()
            .statusCode(HttpStatus.SC_OK)

            .body("id", is(100))
            .body("calendarId", is(1000))
            .body("type", is("game"))
            .body("name", is("vs Arsenal"))
            .body("description", is("This is a game"))
            .body("startDate", startsWith("2015-11-10"))
            .body("startTime", is("10:30"))
            .body("endTime", is("11:30"))
            .body("recurrenceId", nullValue())
            .body("requireRsvp", is(false))
            .body("address.street", is("3030 Linton"))
            .body("address.zipCode", is("H0H 0H0"))
            .body("address.city", is("Montreal"))
            .body("address.state", is("QC"))
            .body("address.country", is("CA"))
            .body("address.countryName", is("Canada"))
            .body("address.location", is("QC, Canada"))
            .body("place.id", is(190))
            .body("place.name", is("Location 1"))
            .body("place.website", is("http://location1.com"))
            .body("game.opponent", is("Arsenal"))
            .body("game.score1", is(1))
            .body("game.score2", is(0))
            .body("game.jerseyColor", is("red"))
            .body("game.home", is(true))
            .body("game.overtime", is(false))
            .body("game.outcome", is("win"))
            .body("game.duration", is(90))
        ;
        // @formatter:on
    }

    @Test
    public void should_return_404_when_not_found (){
        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
        .when()
            .get("/v1/calendar/event/99999")
        .then()
            .log()
                .all()
            .statusCode(404)
        ;
        // @formatter:on
    }

}
