package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;

public class PosteggioInfoRestBean {

    private String codicePosteggio;
    private Double importo;
    private PosteggioImportoHelper importoHelper;

    public String getCodicePosteggio() {

	return codicePosteggio;
    }

    public void setCodicePosteggio(String codicePosteggio) {

	this.codicePosteggio = codicePosteggio;
    }

    public Double getImporto() {

	return importo;
    }

    public void setImporto(Double importo) {

	this.importo = importo;
    }

    public PosteggioImportoHelper getImportoHelper() {

	return importoHelper;
    }

    public void setImportoHelper(PosteggioImportoHelper importoHelper) {

	this.importoHelper = importoHelper;
    }
}
