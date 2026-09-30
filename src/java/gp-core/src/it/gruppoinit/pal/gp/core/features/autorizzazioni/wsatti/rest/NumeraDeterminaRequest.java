package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest;

import javax.xml.bind.annotation.XmlElement;

public class NumeraDeterminaRequest {

    @XmlElement(name = "iddocumento")
    private Integer idDocumento;

    public Integer getIdDocumento() {

	return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {

	this.idDocumento = idDocumento;
    }

    public static NumeraDeterminaRequest fromIdDocumento(Integer idDocumento) {

	if (idDocumento == null) {
	    throw new IllegalArgumentException("Impossibile richiamare NumeraDeterminaRequest.fromIdDocumento senza passare l'id di riferimento");
	}
	NumeraDeterminaRequest request = new NumeraDeterminaRequest();
	request.setIdDocumento(idDocumento);
	return request;
    }
}
