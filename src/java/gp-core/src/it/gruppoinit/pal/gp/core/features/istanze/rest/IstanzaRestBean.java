package it.gruppoinit.pal.gp.core.features.istanze.rest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class IstanzaRestBean {

    @XmlElement(name = "uuid")
    private String uuid;
    @XmlElement(name = "codice_istanza")
    private Integer codice_istanza;
    @XmlElement(name = "numero_istanza")
    private String numero_istanza;
    @XmlElement(name = "data")
    private String data;
    @XmlElement(name = "numero_protocollo")
    private String numero_protocollo;
    @XmlElement(name = "data_protocollo")
    private String data_protocollo;
    @XmlElement(name = "richiedente")
    private String richiedente;
    @XmlElement(name = "oggetto")
    private String oggetto;
    @XmlElement(name = "intervento")
    private String intervento;

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public Integer getCodice_istanza() {

	return codice_istanza;
    }

    public void setCodice_istanza(Integer codice_istanza) {

	this.codice_istanza = codice_istanza;
    }

    public String getNumero_istanza() {

	return numero_istanza;
    }

    public void setNumero_istanza(String numero_istanza) {

	this.numero_istanza = numero_istanza;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getData_protocollo() {

	return data_protocollo;
    }

    public void setData_protocollo(String data_protocollo) {

	this.data_protocollo = data_protocollo;
    }

    public String getNumero_protocollo() {

	return numero_protocollo;
    }

    public void setNumero_protocollo(String numero_protocollo) {

	this.numero_protocollo = numero_protocollo;
    }

    public String getRichiedente() {

	return richiedente;
    }

    public void setRichiedente(String richiedente) {

	this.richiedente = richiedente;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public String getIntervento() {

	return intervento;
    }

    public void setIntervento(String intervento) {

	this.intervento = intervento;
    }

    @XmlTransient
    public static List<IstanzaRestBean> fromIstanzeList(List<Istanze> istanzes) {

	List<IstanzaRestBean> result = new ArrayList<IstanzaRestBean>();
	for (Istanze istanze : istanzes) {
	    IstanzaRestBean ret = new IstanzaRestBean();
	    ret.setCodice_istanza(istanze.getId().getCodice());
	    ret.setRichiedente(istanze.getTransientRichiedenteQualitaAzienda());
	    ret.setNumero_istanza(istanze.getNumeroistanza());
	    ret.setNumero_protocollo(istanze.getNumeroprotocollo());
	    ret.setIntervento(istanze.getAlberoproc().getDescrizioneCompleta());
	    ret.setOggetto(istanze.getLavori());
	    ret.setUuid(istanze.getUuid());
	    if (istanze.getData() != null) {
		ret.setData(Utilities.formatDate(istanze.getData(), false));
	    }
	    if (istanze.getDataprotocollo() != null) {
		ret.setData_protocollo(Utilities.formatDate(istanze.getDataprotocollo(), false));
	    }
	    result.add(ret);
	}
	return result;
    }
}
