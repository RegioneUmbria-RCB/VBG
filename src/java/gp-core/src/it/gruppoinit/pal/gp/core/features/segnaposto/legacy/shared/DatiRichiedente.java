package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;

public class DatiRichiedente {

    private Anagrafe anagrafeRichiedente;
    private String tipoSoggetto;

    public DatiRichiedente(Anagrafe anagrafeRichiedente, String tipoSoggetto) {

	this.anagrafeRichiedente = anagrafeRichiedente;
	this.tipoSoggetto = tipoSoggetto;
    }

    public DatiRichiedente(Istanzerichiedenti istanzaRichiedente) {

	this.anagrafeRichiedente = istanzaRichiedente.getRichiedente();
	this.tipoSoggetto = istanzaRichiedente.getTiposoggetto().getTiposoggetto();
    }

    public Anagrafe getAnagrafeRichiedente() {

	return anagrafeRichiedente;
    }

    public String getTipoSoggetto() {

	return tipoSoggetto;
    }

    public StringBuilder buildCointestatarioEstesoString(boolean scriviTitolo, boolean scriviTipoSoggetto, boolean scriviIndirizzo,
	    String etichettaIndirizzo, boolean scriviDatiNascita, boolean scriviCFPI, String crlf) {

	StringBuilder sb = this.buildCointestatarioString(scriviTitolo);
	Anagrafe ana = this.getAnagrafeRichiedente();
	if (scriviTipoSoggetto && StringUtils.isNotBlank(this.getTipoSoggetto())) {
	    sb.insert(0, FormatUtils.stringFormat(this.getTipoSoggetto()) + " ");
	}
	sb.append(crlf);
	if (scriviIndirizzo) {
	    if (StringUtils.isNotBlank(etichettaIndirizzo)) {
		//Residenza: 
		sb.append(FormatUtils.stringFormat(etichettaIndirizzo)).append(" ");
	    }
	    IndirizzoDestinatario id = new IndirizzoDestinatario();
	    id.setIndirizzo(ana.getIndirizzo());
	    id.setCap(ana.getCap());
	    Comuni comRes = ana.getComuneResidenza();
	    if (comRes != null) {
		id.setCitta(WordUtils.capitalizeFully(comRes.getComune()));
	    }
	    id.setProvincia(ana.getProvincia());
	    sb.append(id.buildIndirizzo(" "));
	    sb.append(crlf);
	}
	if (scriviDatiNascita && ana.getTipoanagrafe().equals("F")) {
	    sb.append("Dati Nascita: ");
	    if (ana.getDatanascita() != null) {
		sb.append(FormatUtils.dateFormat(ana.getDatanascita())).append(" ");
	    }
	    Comuni comNas = ana.getComuneNascita();
	    if (comNas != null) {
		sb.append(FormatUtils.stringFormat(comNas.getComune(), true)).append(" ");
		if (StringUtils.isNotBlank(comNas.getSiglaprovincia())) {
		    sb.append("(").append(FormatUtils.stringFormat(comNas.getSiglaprovincia())).append(") ");
		}
	    }
	    sb.deleteCharAt(sb.length() - 1).append(crlf);
	}
	if (scriviCFPI) {
	    if (StringUtils.isNotBlank(ana.getCodicefiscale())) {
		sb.append("CF: ").append(FormatUtils.stringFormat(ana.getCodicefiscale().toUpperCase()));
		sb.append(crlf);
	    }
	    if (StringUtils.isNotBlank(ana.getPartitaiva())) {
		sb.append("PI: ").append(FormatUtils.stringFormat(ana.getPartitaiva()));
		sb.append(crlf);
	    }
	}
	return sb;
    }

    public StringBuilder buildCointestatarioString(boolean scriviTitolo) {

	StringBuilder sb = new StringBuilder();
	Anagrafe ana = this.getAnagrafeRichiedente();
	if (scriviTitolo) {
	    String titolo = ana.getTitolo() != null ? FormatUtils.stringFormat(ana.getTitolo().getTitolo()) : "";
	    if (titolo.length() > 0) {
		sb.append(titolo).append(" ");
	    }
	}
	String nome = FormatUtils.stringFormat(ana.getNome());
	if (nome.length() > 0) {
	    sb.append(nome);
	    sb.append(" ");
	}
	sb.append(FormatUtils.stringFormat(ana.getNominativo()));
	return sb;
    }
}
