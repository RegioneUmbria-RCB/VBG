package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao;

import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public class FilterFromUsefulData {

    private Integer codiceIstanza;
    private Integer codiceMovimento;

    private FilterFromUsefulData() {

	super();
    }

    public FilterFromUsefulData(IUsefulDataForPlaceholderReplacement data) {

	this();
	if (data.getMovimento() != null && data.getMovimento().getId() != null) {
	    this.codiceMovimento = data.getMovimento().getId().getCodice();
	}
	if (data.getIstanza() != null && data.getIstanza().getId() != null) {
	    this.codiceIstanza = data.getIstanza().getId().getCodice();
	}
    }

    public boolean hasParameters() {

	return this.codiceIstanza != null && this.codiceMovimento != null;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }
}
