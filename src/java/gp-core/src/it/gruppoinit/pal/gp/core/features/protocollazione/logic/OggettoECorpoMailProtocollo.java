package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Mailtipo;

public class OggettoECorpoMailProtocollo {

    private String oggetto;
    private String corpo;

    public OggettoECorpoMailProtocollo(Mailtipo mailTipo, String oggettoDefault) {

	this.oggetto = mailTipo != null && StringUtils.isNotBlank(mailTipo.getOggetto()) //
		? mailTipo.getOggetto() //
		: oggettoDefault;
	this.corpo = mailTipo != null && StringUtils.isNotBlank(mailTipo.getCorpo()) //
		? mailTipo.getCorpo() //
		: null;
    }

    public String getOggetto() {

	return oggetto;
    }

    public String getCorpo() {

	return corpo;
    }
}
