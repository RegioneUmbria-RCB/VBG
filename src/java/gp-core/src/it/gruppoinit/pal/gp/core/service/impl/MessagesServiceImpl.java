package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.service.MessagesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessagesServiceImpl implements MessagesService {

    private static final Logger log = LoggerFactory.getLogger(MessagesServiceImpl.class);
    @Autowired
    private ApplicationContext context;

    @Override
    public String getMessage(String key) {

	return this.getMessage(key, null);
    }

    @Override
    public String getMessage(String key, Object[] placeHolders) {

	String message = "";
	try {
	    message = context.getMessage(key, placeHolders, LocaleContextHolder.getLocale());
	} catch (Exception e) {
	    log.error("getMessage({})", key, e);
	    message = "???" + key + "???";
	}
	return message;
    }
}
