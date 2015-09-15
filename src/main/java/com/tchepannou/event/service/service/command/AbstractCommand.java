package com.tchepannou.event.service.service.command;

import com.tchepannou.event.service.service.Command;
import com.tchepannou.event.service.service.CommandContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommand<I, O> implements Command<I, O> {
    //-- Attributes
    private Logger logger;  // NOSONAR

    //-- Constructor
    public AbstractCommand(){
        this.logger = LoggerFactory.getLogger(getClass());
    }

    //-- Abstract
    protected abstract O doExecute (I request, CommandContext context);


    //-- Command Override
    @Override
    public O execute(I request, CommandContext context) {
        /* execute */
        O response = doExecute(request, context);

        /* post */
        return response;
    }

    //-- Protected
    protected Logger getLogger () {
        return logger;
    }
}
