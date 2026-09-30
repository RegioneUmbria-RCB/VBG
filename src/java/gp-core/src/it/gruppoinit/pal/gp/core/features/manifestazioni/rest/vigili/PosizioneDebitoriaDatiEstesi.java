package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ImportiResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PagamentiResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.StatoResponseType;

public class PosizioneDebitoriaDatiEstesi {

    @XmlElement(name = "importi")
    private Set<ImportiResponseType> importi = new HashSet<ImportiResponseType>();
    @XmlElement(name = "stato_attuale")
    private StatoResponseType statoAttuale;
    @XmlElement(name = "pagamenti")
    private Set<PagamentiResponseType> pagamenti = new HashSet<PagamentiResponseType>();
    @XmlElement(name = "stati")
    private Set<StatoResponseType> stati = new HashSet<StatoResponseType>();

    public Set<ImportiResponseType> getImporti() {

	return importi;
    }

    public void setImporti(Set<ImportiResponseType> importi) {

	this.importi = importi;
    }

    public StatoResponseType getStatoAttuale() {

	return statoAttuale;
    }

    public void setStatoAttuale(StatoResponseType statoAttuale) {

	this.statoAttuale = statoAttuale;
    }

    public Set<PagamentiResponseType> getPagamenti() {

	return pagamenti;
    }

    public void setPagamenti(Set<PagamentiResponseType> pagamenti) {

	this.pagamenti = pagamenti;
    }

    public Set<StatoResponseType> getStati() {

	return stati;
    }

    public void setStati(Set<StatoResponseType> stati) {

	this.stati = stati;
    }

    public static PosizioneDebitoriaDatiEstesi fromDatiNodoPagamento(PosizioneDebitoriaResponseType datiNodo) {

	if (datiNodo == null) {
	    return null;
	}
	PosizioneDebitoriaDatiEstesi ret = new PosizioneDebitoriaDatiEstesi();
	ret.setImporti(datiNodo.getImporti());
	ret.setPagamenti(datiNodo.getPagamenti());
	ret.setStati(datiNodo.getStati());
	ret.setStatoAttuale(datiNodo.getStatoAttuale());
	return ret;
    }
}
