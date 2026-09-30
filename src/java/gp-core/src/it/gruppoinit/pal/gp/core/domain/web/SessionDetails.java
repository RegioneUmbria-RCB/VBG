package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class SessionDetails {

    private String token;
    private Responsabili responsabile;

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public void setResponsabile(Responsabili responsabile) {

	this.responsabile = responsabile;
    }
}
