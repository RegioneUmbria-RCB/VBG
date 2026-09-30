package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class MercatiUsoDTO {

    private PkId id;
    private String descrizione;

    
    
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
