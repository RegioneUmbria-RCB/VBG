package it.alveo.firmaremota.aruba;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.alveo.firmaremota.aruba.configurazione.ArubaParams;
import it.alveo.firmaremota.aruba.exception.InvalidSessionIdException;

public class BaseDelegate {

    @Autowired
    public ArubaParams arubaParams;

    protected void validateSession(String sessionid) {

	if (StringUtils.isBlank(sessionid)) {
	    throw InvalidSessionIdException.fromEmpty();
	}
    }
}
