package it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AppAmbulantiAutorizzazioniStampa {

    @XmlElement
    private List<AutorizzazioniMercatoSrv> autorizzazioni;
    @XmlElement
    private StampaPDFRiferimentiDocumento rifDocumento;

    public List<AutorizzazioniMercatoSrv> getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(List<AutorizzazioniMercatoSrv> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public StampaPDFRiferimentiDocumento getRifDocumento() {

	return rifDocumento;
    }

    public void setRifDocumento(StampaPDFRiferimentiDocumento rifDocumento) {

	this.rifDocumento = rifDocumento;
    }
}
