package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class BlackListOggetti {

    private List<BlackListIdentificativi> autorizzazioni;

    public List<BlackListIdentificativi> getAutorizzazioni() {

	if (this.autorizzazioni == null) {
	    this.autorizzazioni = new ArrayList<BlackListIdentificativi>();
	}
	return autorizzazioni;
    }

    public void setAutorizzazioni(List<BlackListIdentificativi> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }
}
