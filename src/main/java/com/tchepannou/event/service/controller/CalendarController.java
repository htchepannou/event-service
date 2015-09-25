package com.tchepannou.event.service.controller;

import com.tchepannou.core.client.v1.ErrorResponse;
import com.tchepannou.core.http.Http;
import com.tchepannou.event.client.v1.EventCollectionResponse;
import com.tchepannou.event.client.v1.EventResponse;
import com.tchepannou.event.client.v1.SearchRequest;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    @RequestMapping(method = RequestMethod.POST, value="/search")
    @ApiOperation("Search for events")
    public EventCollectionResponse search(
            @RequestHeader(Http.HEADER_TRANSACTION_ID) String transactionId,
            @Valid @RequestBody SearchRequest request
    ) {
        return searchCommand.execute(request,
                new CommandContextImpl()
                        .withTransactionId(transactionId)
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse validationError(MethodArgumentNotValidException ex, final HttpServletRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return createErrorResponse(HttpStatus.BAD_REQUEST.value(), fieldErrors.get(0).getDefaultMessage(), request);
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
    }}
