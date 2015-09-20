package com.tchepannou.event.service.controller;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.tchepannou.core.http.Http;
import com.tchepannou.event.client.v1.SearchRequest;
import com.tchepannou.event.service.Starter;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.UUID;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
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
        SearchRequest request = new SearchRequest();
        request.setStartDate("2015/11/10");
        request.setEndDate("2015/11/17");
        request.setCalendarIds(Arrays.asList(1000L, 1001L));

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search")
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
    public void should_returns_empty_for_request_with_no_calendar_id (){
        SearchRequest request = new SearchRequest();
        request.setStartDate("2014/01/01");
        request.setEndDate("2015/01/01");

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search" )
        .then()
            .log()
                .all()
            .statusCode(HttpStatus.SC_OK)
            .body("events", hasSize(0))
        ;
        // @formatter:on
    }

    @Test
    public void should_returns_400_for_no_start_date (){
        SearchRequest request = new SearchRequest();
        request.setStartDate(null);
        request.setEndDate("2015/01/01");

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search" )
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("start_date_missing"))
        ;
        // @formatter:on
    }

    @Test
    public void should_returns_400_for_no_end_date (){
        SearchRequest request = new SearchRequest();
        request.setStartDate("2015/01/01");
        request.setEndDate(null);

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search" )
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("end_date_missing"))
        ;
        // @formatter:on
    }

    @Test
    public void should_returns_400_for_malformed_start_date (){
        SearchRequest request = new SearchRequest();
        request.setStartDate("fdlkfdlk");
        request.setEndDate("2015/01/01");

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search" )
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("start_date_format"))
        ;
        // @formatter:on
    }

    @Test
    public void should_returns_400_for_malformed_end_date (){
        SearchRequest request = new SearchRequest();
        request.setStartDate("2015/01/01");
        request.setEndDate("fdlkfdlk");

        // @formatter:off
        given()
                .header(Http.HEADER_TRANSACTION_ID, transactionId)
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/v1/calendar/search" )
        .then()
            .log()
                .all()
            .statusCode(400)
            .body("code", is(400))
            .body("text", is("end_date_format"))
        ;
        // @formatter:on
    }
}
