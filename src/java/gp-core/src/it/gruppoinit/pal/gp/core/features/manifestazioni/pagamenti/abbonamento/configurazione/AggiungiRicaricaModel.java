package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AggiungiRicaricaModel {

    @XmlElement(name = "comuni")
    private List<ComuneModel> comuni;
    @XmlElement(name = "tipo")
    private String tipo;
    @XmlElement(name = "importo")
    private Integer importo;

    public List<ComuneModel> getComuni() {

	return comuni;
    }

    public void setComuni(List<ComuneModel> comuni) {

	this.comuni = comuni;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public Integer getImporto() {

	return importo;
    }

    public void setImporto(Integer importo) {

	this.importo = importo;
    }

    public List<BorsellinoRicariche> toBorsellinoRicariche() {

	if (this.comuni == null || this.comuni.isEmpty()) {
	    return new ArrayList<BorsellinoRicariche>();
	}
	List<BorsellinoRicariche> ricariche = new ArrayList<BorsellinoRicariche>();
	for (ComuneModel comune : this.comuni) {
	    BorsellinoRicariche borsRic = new BorsellinoRicariche();
	    borsRic.setComune(new Comuni(comune.getComune()));
	    borsRic.setImporto(this.getImporto());
	    borsRic.setTipo(this.getTipo());
	    ricariche.add(borsRic);
	}
	return ricariche;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
