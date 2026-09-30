package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.utils.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseController {

    @Autowired
    private ApplicationContext context;

    protected String getMessageFromBundle(String chiave) {

	return this.getMessageFromBundle(chiave, null);
    }

    protected String getMessageFromBundle(String chiave, Object[] args) {

	return Utilities.getMessageFromBundle(context, chiave, args);
    }
}
