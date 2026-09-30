package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRataBean;

@XmlRootElement(name = "anagraficaBollettazione")
public class AnagraficaBollettazione implements Comparable<AnagraficaBollettazione> {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nominativo")
    private String nominativo;
    @XmlElement(name = "cfpivaPresente")
    private Boolean cfpivaPresente;
    @XmlElement(name = "righeBollettazioneList")
    private List<RigaBollettazione> righeBollettazioneList = new ArrayList<RigaBollettazione>();
    @XmlElement(name = "checkPosizioneDebitoriaRaggruppata")
    private Boolean checkPosizioneDebitoriaRaggruppata;
    @XmlElement(name = "rateizzazione")
    private Set<BollettazioneRataBean> rateizzazione = new TreeSet<BollettazioneRataBean>();
    @XmlElement(name = "daRateizzare")
    private boolean daRateizzare;
    @XmlElement(name = "dettaglioPosizioneDebitoriaPresente")
    private boolean dettaglioPosizioneDebitoriaPresente;
    @XmlElement(name = "posizioniDebitorie")
    private List<Integer> posizioniDebitorie;

    public AnagraficaBollettazione() {

	super();
    }

    public AnagraficaBollettazione(Integer id, String nominativo, Boolean cfpivaPresente, Boolean checkPosizioneDebitoriaRaggruppata,
	    Set<BollettazioneRataBean> rateizzazione) {

	this.id = id;
	this.nominativo = nominativo;
	this.cfpivaPresente = cfpivaPresente;
	this.checkPosizioneDebitoriaRaggruppata = checkPosizioneDebitoriaRaggruppata;
	if (rateizzazione != null) {
	    this.rateizzazione = rateizzazione;
	}
    }

    public Integer getId() {

	return id;
    }

    public String getNominativo() {

	return nominativo;
    }

    public List<RigaBollettazione> getRigheBollettazioneList() {

	return righeBollettazioneList;
    }

    public void aggiungiRiga(RigaBollettazione rigaBollettazione) {

	this.righeBollettazioneList.add(rigaBollettazione);
    }

    public Boolean getCfpivaPresente() {

	return cfpivaPresente;
    }

    public boolean getCheckPosizioneDebitoriaRaggruppata() {

	return checkPosizioneDebitoriaRaggruppata;
    }

    public Set<Integer> getPosizioniDebitorie() {

	Set<Integer> posizioniBebitorie = new HashSet<Integer>();
	for (RigaBollettazione rigaBollettazione : righeBollettazioneList) {
	    if (!rigaBollettazione.getIdDettPosizioniDebitorie().isEmpty()) {
		posizioniBebitorie.addAll(rigaBollettazione.getIdDettPosizioniDebitorie());
	    }
	}
	return posizioniBebitorie;
    }

    public boolean isDettaglioPosizioneDebitoriaPresente() {

	for (RigaBollettazione rigaBollettazione : righeBollettazioneList) {
	    if (!rigaBollettazione.getIdDettPosizioniDebitorie().isEmpty()) {
		return true;
	    }
	}
	return false;
    }

    public boolean isDaRateizzare() {

	this.daRateizzare = false;
	if (Boolean.FALSE.equals(this.checkPosizioneDebitoriaRaggruppata)) {
	    return this.daRateizzare;
	}
	if (this.righeBollettazioneList == null || this.righeBollettazioneList.isEmpty()) {
	    return this.daRateizzare;
	}
	if (this.rateizzazione == null || this.rateizzazione.isEmpty()) {
	    return this.daRateizzare;
	}
	BigDecimal importoTotale = BigDecimal.ZERO;
	for (RigaBollettazione riga : this.righeBollettazioneList) {
	    if (riga.getIdDettPosizioniDebitorie().size() > 1) {
		this.daRateizzare = true;
		return this.daRateizzare;
	    }
	    if (Boolean.TRUE.equals(riga.getValidato())) {
		importoTotale = importoTotale.add(riga.getImportoTotale());
	    }
	}
	for (BollettazioneRataBean rata : rateizzazione) {
	    if (importoTotale.compareTo(new BigDecimal(rata.getImportoMinimo())) >= 0
		    && (rata.getImportoMassimo() == null || importoTotale.compareTo(new BigDecimal(rata.getImportoMassimo())) <= 0)) {
		this.daRateizzare = true;
		return this.daRateizzare;
	    }
	}
	return this.daRateizzare;
    }

    public void setDettaglioPosizioneDebitoriaPresente(boolean dettaglioPosizioneDebitoriaPresente) {

	this.dettaglioPosizioneDebitoriaPresente = dettaglioPosizioneDebitoriaPresente;
    }

    public void setPosizioniDebitorie(List<Integer> posizioniDebitorie) {

	this.posizioniDebitorie = posizioniDebitorie;
    }

    public void setRigheBollettazioneList(List<RigaBollettazione> righeBollettazioneList) {

	this.righeBollettazioneList = righeBollettazioneList;
    }

    public void setDaRateizzare(boolean daRateizzare) {

	this.daRateizzare = daRateizzare;
    }

    @Override
    public int compareTo(AnagraficaBollettazione other) {

	if (other == null) {
	    return -1;
	}
	return StringUtils.defaultString(this.getNominativo()).toUpperCase()
		.compareTo(StringUtils.defaultString(other.getNominativo()).toUpperCase());
    }
}
