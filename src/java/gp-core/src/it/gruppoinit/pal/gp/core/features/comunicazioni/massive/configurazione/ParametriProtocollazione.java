package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione;

import java.util.ArrayList;
import java.util.List;

public class ParametriProtocollazione {

    private Integer codiceMailtipo;
    private List<ParametriProtocolloPerEnte> parametriPerEnte;

    public ParametriProtocollazione(Integer codiceMailtipo, List<ParametriProtocolloPerEnte> parametriPerEnte) {

	this.codiceMailtipo = codiceMailtipo;
	this.parametriPerEnte = parametriPerEnte;
    }

    public Integer getCodiceMailtipo() {

	return codiceMailtipo;
    }

    public List<ParametriProtocolloPerEnte> getParametriPerEnte() {

	if (this.parametriPerEnte == null) {
	    this.parametriPerEnte = new ArrayList<ParametriProtocolloPerEnte>();
	}
	return parametriPerEnte;
    }
}
