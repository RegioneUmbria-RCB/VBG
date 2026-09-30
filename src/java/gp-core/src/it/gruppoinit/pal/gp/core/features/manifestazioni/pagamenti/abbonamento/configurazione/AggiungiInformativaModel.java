package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
public class AggiungiInformativaModel {

    @XmlElement(name = "comuni")
    private List<ComuneModel> comuni;
    @XmlElement(name = "scadenza")
    private String scadenza;
    @XmlElement(name = "informativa")
    private String informativa;

    public List<ComuneModel> getComuni() {

	return comuni;
    }

    public void setComuni(List<ComuneModel> comuni) {

	this.comuni = comuni;
    }

    public String getScadenza() {

	return this.scadenza;
    }

    public void setScadenza(String scadenza) {

	this.scadenza = scadenza;
    }

    @XmlTransient
    public Date getDataFineValidita() {

	if (StringUtils.isBlank(this.scadenza)) {
	    return null;
	}
	return Utilities.getDate(this.scadenza).getTime();
    }

    public String getInformativa() {

	return informativa;
    }

    public void setInformativa(String informativa) {

	this.informativa = informativa;
    }

    public List<BorsellinoInformative> toBorsellinoInformative() {

	if (this.comuni == null || this.comuni.isEmpty()) {
	    return new ArrayList<BorsellinoInformative>();
	}
	List<BorsellinoInformative> informative = new ArrayList<BorsellinoInformative>();
	for (ComuneModel comune : this.comuni) {
	    BorsellinoInformative borsInfo = new BorsellinoInformative();
	    borsInfo.setComune(new Comuni(comune.getComune()));
	    borsInfo.setDataFineValidita(this.getDataFineValidita());
	    borsInfo.setInformativa(this.getInformativa());
	    informative.add(borsInfo);
	}
	return informative;
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
