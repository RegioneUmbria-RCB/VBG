package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BorsellinoAppAutorizzazioni {

    @XmlElement
    private Integer id;
    @XmlElement
    private String data;
    @XmlElement
    private String numero;
    @XmlElement
    private String ente;
    @XmlElement(name = "cf_titolare")
    private String cfTitolare;
    @XmlElement
    private String titolare;

    public BorsellinoAppAutorizzazioni() {

	super();
    }

    public Integer getId() {

	return id;
    }

    public String getData() {

	return data;
    }

    public String getNumero() {

	return numero;
    }

    public String getEnte() {

	return ente;
    }

    public static BorsellinoAppAutorizzazioni fromAutorizzazione(Autorizzazioni autorizzazione) {

	BorsellinoAppAutorizzazioni ret = new BorsellinoAppAutorizzazioni();
	ret.numero = autorizzazione.getAutoriznumero();
	ret.data = Utilities.formatDateISO8601(autorizzazione.getAutorizdata());
	ret.ente = autorizzazione.getAutorizcomune().getComune();
	ret.id = autorizzazione.getId().getCodice();
	ret.cfTitolare = getCfTitolare(autorizzazione.getAnagrafe());
	ret.titolare = getTitolare(autorizzazione.getAnagrafe());
	return ret;
    }

    @XmlTransient
    private static String getCfTitolare(Anagrafe anagrafe) {

	if (anagrafe == null) {
	    return null;
	}
	return anagrafe.getCfPiva();
    }

    @XmlTransient
    private static String getTitolare(Anagrafe anagrafe) {

	if (anagrafe == null) {
	    return null;
	}
	String tNominativo = anagrafe.getNominativo() == null ? "" : anagrafe.getNominativo();
	tNominativo += " " + (anagrafe.getNome() == null ? "" : anagrafe.getNome());
	return tNominativo.trim();
    }
}
