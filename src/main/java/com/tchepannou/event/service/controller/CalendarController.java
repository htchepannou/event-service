package com.tchepannou.event.service.controller;

import com.tchepannou.core.client.v1.ErrorResponse;
import com.tchepannou.core.http.Http;
import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.service.client.v1.SearchRequest;
import com.tchepannou.event.service.service.command.GetCommand;
import com.tchepannou.event.service.service.command.SearchCommand;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api(basePath = "/v1/calendar", value = "Event", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(value="/v1/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalendarController {
    //-- Attributes
    private static final Logger LOG = LoggerFactory.getLogger(CalendarController.class);

    @Autowired
    private SearchCommand searchCommand;

    @Autowired
    private GetCommand getCommand;

    //-- REST methods
    @RequestMapping(method = RequestMethod.GET, value="/{cid}/search/from/{startDate}/to/{endDate}")
    @ApiOperation("Search for events")
    public EventCollectionResponse search(
            @RequestHeader(Http.HEADER_TRANSACTION_ID) String transactionId,
            @PathVariable String cid,
            @PathVariable String startDate,
            @PathVariable String endDate,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset
    ) throws ParseException {
        final DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        final SearchRequest request = new SearchRequest();
        request.setCalendarIds(calendarIds(cid));
        request.setStartDate(fmt.parse(startDate));
        request.setEndDate(fmt.parse(endDate));

        return searchCommand.execute(request,
                new CommandContextImpl()
                        .withTransactionId(transactionId)
                        .withLimit(limit)
                        .withOffset(offset)
        );
    }

    @RequestMapping(method = RequestMethod.GET, value="/event/{id}")
    @ApiOperation("Return an event")
    @ApiResponses({
            @ApiResponse(code=404, message="not_found")
    })
    public EventResponse get(
            @RequestHeader(Http.HEADER_TRANSACTION_ID) String transactionId,
            @PathVariable long id
    ) {
        return getCommand.execute(null,
                new CommandContextImpl()
                        .withTransactionId(transactionId)
                        .withId(id)
        );
    }

    //-- Exception Handler
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorResponse notFoundError(final HttpServletRequest request) {
        return createErrorResponse(HttpStatus.NOT_FOUND.value(), "not_found", request);
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponse validationError(ParseException ex, final HttpServletRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST.value(), "bad_date_format", request);
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse internalError(final Exception exception, final HttpServletRequest request) {
        LOG.error("Unexpected error", exception);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(), request);
    }

    private ErrorResponse createErrorResponse(int code, String text, HttpServletRequest request){
        return new ErrorResponse()
                .withCode(code)
                .withText(text)
                .withTransactionId(request.getHeader(Http.HEADER_TRANSACTION_ID));
    }

    //-- Private
    private Set<Long> calendarIds(String ids){
        return Arrays.asList(ids.split("\\+")).stream()
                .map(i -> toLong(i))
                .filter(i -> i > 0)
                .collect(Collectors.toSet());
    }

    private long toLong(String id){
        try {
            return Long.parseLong(id);
        } catch (Exception e) { // NOSONAR
            return 0;
        }
    }
}
