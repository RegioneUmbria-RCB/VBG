package it.gruppoinit.pal.gp.core.domain.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PosteggioImportoHelper implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8112594976012905227L;
    private BigDecimal importo;
    private List<RigaImporto> listaImporti;

    public PosteggioImportoHelper() {

	this.importo = new BigDecimal(0);
	this.listaImporti = new ArrayList<RigaImporto>();
    }

    public BigDecimal getImporto() {

	this.importo = new BigDecimal(0);
	for (RigaImporto importi : listaImporti) {
	    this.importo = this.importo.add(importi.getImporto());
	}
	return this.importo;
    }

    public List<RigaImporto> getListaImporti() {

	return listaImporti;
    }

    public void setListaImporti(List<RigaImporto> listaImporti) {

	this.listaImporti = listaImporti;
    }
}
