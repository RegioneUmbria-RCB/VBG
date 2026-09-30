package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model.BollGestDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.bollettazione.rateizzazioni.BollettazioneRataBean;

@XmlRootElement(name = "rigaBollettazione")
public class RigaBollettazione {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "importoSenzaIVA")
    private BigDecimal importoSenzaIVA;
    @XmlElement(name = "iva")
    private Integer iva;
    @XmlElement(name = "importoTotale")
    private BigDecimal importoTotale;
    @XmlElement(name = "conguaglio")
    private Boolean conguaglio;
    @XmlElement(name = "validato")
    private Boolean validato;
    @XmlElement(name = "inserimentoAutomatico")
    private Boolean inserimentoAutomatico;
    @XmlElement(name = "rettificatoId")
    private Integer rettificatoId;
    @XmlElement(name = "isRettificato")
    private Boolean isRettificato;
    @XmlElement(name = "descrizioneConto")
    private String descrizioneConto;
    @XmlElement(name = "bollettazioneChiusa")
    private Boolean bollettazioneChiusa;
    @XmlElement(name = "anagrafeCFPIpresente")
    private Boolean anagrafeCFPIpresente;
    @XmlElement(name = "idDettPosizioniDebitorie")
    private Set<Integer> idDettPosizioniDebitorie = new HashSet<Integer>();
    @XmlElement(name = "dataScadenza")
    private Date dataScadenza;
    @XmlElement(name = "posizioniDebitorieRaggruppate")
    private boolean posizioniDebitorieRaggruppate;
    @XmlElement(name = "daRateizzare")
    private boolean daRateizzare;
    @XmlElement(name = "rateizzazione")
    private Set<BollettazioneRataBean> rateizzazione = new TreeSet<BollettazioneRataBean>();
    @XmlElement(name = "supportaValidazione")
    private boolean supportaValidazione;
    @XmlElement(name = "supportaRettifica")
    private boolean supportaRettifica;
    @XmlElement(name = "supportaCancellazione")
    private boolean supportaCancellazione;

    public RigaBollettazione() {

	super();
    }

    public RigaBollettazione(BollGestDettaglioDTO bollGestDettaglio, Set<BollettazioneRataBean> rateizzazione, boolean raggruppaPerUtenza) {

	//TODO Controllare gli stati dei pagamenti che consentono la rettifica
	this.id = bollGestDettaglio.getId();
	this.descrizione = bollGestDettaglio.getDescrizione();
	this.importoSenzaIVA = bollGestDettaglio.getImportosenzaiva();
	this.iva = bollGestDettaglio.getIva();
	this.importoTotale = bollGestDettaglio.getImportototale();
	this.inserimentoAutomatico = bollGestDettaglio.getFlaginsauto();
	this.validato = bollGestDettaglio.getFlagvalidata();
	this.conguaglio = bollGestDettaglio.getFlagconguaglio();
	this.rettificatoId = bollGestDettaglio.getFkrettificaid();
	//TODO: Se il conto è vuoto va capito se inviare un'eccezione
	if (bollGestDettaglio.getFkcontoid() != null) {
	    this.descrizioneConto = bollGestDettaglio.getConto();	   
	}
	this.isRettificato = bollGestDettaglio.getFlagrettificata();
	this.anagrafeCFPIpresente = (StringUtils.isNotEmpty(bollGestDettaglio.getCodicefiscale())
		|| StringUtils.isNotEmpty(bollGestDettaglio.getPartitaiva()));
	if (!this.anagrafeCFPIpresente) {
	    this.validato = Boolean.FALSE;
	}
	if (bollGestDettaglio.getFkposdebdettaglioid() != null) {
	    this.idDettPosizioniDebitorie.add(bollGestDettaglio.getFkposdebdettaglioid());
	}
	if (bollGestDettaglio.getIdposizionerateizzata() != null) {
	    this.idDettPosizioniDebitorie.add(bollGestDettaglio.getIdposizionerateizzata());
	}
	this.posizioniDebitorieRaggruppate = raggruppaPerUtenza;
	this.bollettazioneChiusa = !this.idDettPosizioniDebitorie.isEmpty();// (bollGestDettaglio.getBollGestTestata().getStato().equals(StatoBollettazioneEnum.CHIUSA.getValore()));
	this.dataScadenza = bollGestDettaglio.getDatascadenza();
	if (rateizzazione != null) {
	    this.rateizzazione = rateizzazione;
	}
    }

    public Set<Integer> getIdDettPosizioniDebitorie() {

	return idDettPosizioniDebitorie;
    }

    public Integer getId() {

	return id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public BigDecimal getImportoSenzaIVA() {

	return importoSenzaIVA;
    }

    public Integer getIva() {

	return iva;
    }

    public BigDecimal getImportoTotale() {

	return importoTotale;
    }

    public Boolean getValidato() {

	return validato;
    }

    public Boolean getInserimentoAutomatico() {

	return inserimentoAutomatico;
    }

    public Integer getRettificatoId() {

	return rettificatoId;
    }

    public Boolean getIsRettificato() {

	return isRettificato;
    }

    public void setIsRettificato(Boolean isRettificato) {

	this.isRettificato = isRettificato;
    }

    public Boolean getSupportaRettifica() {

	if (isRettificato) {
	    return false;
	}
	if (!inserimentoAutomatico) {
	    return false;
	}
	if (this.idDettPosizioniDebitorie.size() > 1) {
	    return false;
	}
	return true;
    }

    public Boolean getSupportaValidazione() {

	if (isRettificato) {
	    return false;
	}
	if (this.getImportoTotale().compareTo(BigDecimal.ZERO) <= 0) {
	    return false;
	}
	if (this.idDettPosizioniDebitorie.size() > 1) {
	    return false;
	}
	return true;
    }

    public Boolean getSupportaCancellazione() {

	if (this.isRettificato) {
	    return false;
	}
	if (this.conguaglio) {
	    return true;
	}
	if (this.inserimentoAutomatico) {
	    return false;
	}
	if (this.idDettPosizioniDebitorie.size() > 1) {
	    return false;
	}
	return true;
    }

    public String getDescrizioneConto() {

	return descrizioneConto;
    }
    
    public Boolean getBollettazioneChiusa() {

	return bollettazioneChiusa;
    }

    public Boolean getAnagrafeCFPIpresente() {

	return anagrafeCFPIpresente;
    }

    public boolean isPosizioniDebitorieRaggruppate() {

	return posizioniDebitorieRaggruppate;
    }

    public Boolean getConguaglio() {

	return conguaglio;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public boolean isDaRateizzare() {

	this.daRateizzare = false;
	if (Boolean.TRUE.equals(this.posizioniDebitorieRaggruppate)) {
	    return this.daRateizzare;
	}
	if (this.rateizzazione == null || this.rateizzazione.isEmpty()) {
	    return this.daRateizzare;
	}
	if (this.importoTotale == null) {
	    return this.daRateizzare;
	}
	if (this.idDettPosizioniDebitorie.size() > 1) {
	    this.daRateizzare = true;
	    return this.daRateizzare;
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

    public void aggiungiPosizioneDebitoria(BollGestDettaglioDTO bollGestDettaglio) {

	if (bollGestDettaglio.getFkposdebdettaglioid() != null) {
	    this.idDettPosizioniDebitorie.add(bollGestDettaglio.getFkposdebdettaglioid());
	}
	if (bollGestDettaglio.getIdposizionerateizzata() != null) {
	    this.idDettPosizioniDebitorie.add(bollGestDettaglio.getIdposizionerateizzata());
	}
    }

    public void setValidato(Boolean validato) {

	this.validato = validato;
    }

    public void setPosizioniDebitorieRaggruppate(boolean posizioniDebitorieRaggruppate) {

	this.posizioniDebitorieRaggruppate = posizioniDebitorieRaggruppate;
    }

    public void setSupportaValidazione(boolean supportaValidazione) {

	this.supportaValidazione = supportaValidazione;
    }

    public void setSupportaRettifica(boolean supportaRettifica) {

	this.supportaRettifica = supportaRettifica;
    }

    public void setSupportaCancellazione(boolean supportaCancellazione) {

	this.supportaCancellazione = supportaCancellazione;
    }

    public void setDaRateizzare(boolean daRateizzare) {

	this.daRateizzare = daRateizzare;
    }
}
