package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.Set;

import org.springframework.util.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutorizzazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class BorsellinoAutorizzazioniModel {

    private Integer id;
    private String numero;
    private String data;
    private String comune;
    private String numeroAttoCollegato;
    private String dataAttoCollegato;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getData() {

	return data;
    }

    public void setData(String data) {

	this.data = data;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getDataAttoCollegato() {

	return dataAttoCollegato;
    }

    public void setDataAttoCollegato(String dataAttoCollegato) {

	this.dataAttoCollegato = dataAttoCollegato;
    }

    public String getNumeroAttoCollegato() {

	return numeroAttoCollegato;
    }

    public void setNumeroAttoCollegato(String numeroAttoCollegato) {

	this.numeroAttoCollegato = numeroAttoCollegato;
    }

    public String getDescrizioneAutorizzazione() {

	String attoCollegato = "";
	if (StringUtils.hasLength(getNumeroAttoCollegato())) {
	    attoCollegato = " (Atto collegato numero " + getNumeroAttoCollegato() + " del " + getDataAttoCollegato() + ")";
	}
	return "numero: " + getNumero() + ", data: " + getData() + ", comune: " + getComune() + attoCollegato;
    }

    public static BorsellinoAutorizzazioniModel fromBorsellinoAutorizzazione(BorsellinoAutorizzazioni ba) {

	Autorizzazioni autorizzazione = ba.getAutorizzazione();
	BorsellinoAutorizzazioniModel ret = new BorsellinoAutorizzazioniModel();
	Set<AutorizzazioniConcessioni> autorizzazioniConcessionisForFkAutconcAutatt = autorizzazione
		.getAutorizzazioniConcessionisForFkAutconcAutatt();
	if (!autorizzazioniConcessionisForFkAutconcAutatt.isEmpty()) {
	    AutorizzazioniConcessioni conc = autorizzazioniConcessionisForFkAutconcAutatt.iterator().next();
	    if (conc.getAutorizzazioniByFkAutconcAutcoll() != null) {
		ret.setNumeroAttoCollegato(conc.getAutorizzazioniByFkAutconcAutcoll().getAutoriznumero());
		ret.setDataAttoCollegato(Utilities.formatDate(conc.getAutorizzazioniByFkAutconcAutcoll().getAutorizdata(), false));
	    }
	}
	if (autorizzazione.getAutorizcomune() != null) {
	    ret.setComune(autorizzazione.getAutorizcomune().getComune());
	}
	ret.setNumero(autorizzazione.getAutoriznumero());
	ret.setId(autorizzazione.getId().getCodice());
	ret.setData(Utilities.formatDate(autorizzazione.getAutorizdata(), false));
	return ret;
    }
}
