package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.OperazioniConsentiteResponseType;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "posizione_debitoria")
public class DettPosizioneDebitoriaResponseType {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "codiceStato")
    private Integer codiceStato;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "idPosizioneDebitoria")
    private Integer idPosizioneDebitoria;
    @XmlElement(name = "dataUltimoStato")
    private String dataUltimoStato;
    @XmlElement(name = "codiceStatoNodo")
    private String codiceStatoNodo;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codiceAvviso")
    private String codiceAvviso;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "dataScadenza")
    private String dataScadenza;
    @XmlElement(name = "importoIvato")
    private BigDecimal importoIvato;
    @XmlElement(name = "importi")
    private ImportoResponseType importi = new ImportoResponseType();
    @XmlElement(name = "operazioniConsentite")
    private OperazioniConsentiteResponseType operazioniConsentite = new OperazioniConsentiteResponseType();
    @XmlElement(name = "descCodiceStatoNodo")
    private String descCodiceStatoNodo;
    @XmlElement(name = "nominativo")
    private String nominativo;

    public DettPosizioneDebitoriaResponseType() {

	super();
    }

    public DettPosizioneDebitoriaResponseType(DettPosizioneDebitoria posizione) {

	super();
	if (posizione == null) {
	    return;
	}
	if (posizione.getId() != null) {
	    this.id = posizione.getId().getCodice();
	}
	StatiPosizioniDebitorieConverter converter = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean convertStato = converter.convertStato(posizione.getStato());
	this.codiceStato = convertStato.getId();
	this.stato = convertStato.getDescrizione();
	this.idPosizioneDebitoria = posizione.getIdPosizioneDebitoria();
	this.setDataUltimoStato(posizione.getDataUltimoStato());
	this.codiceStatoNodo = posizione.getStato();
	this.iuv = posizione.getIuv();
	this.codiceAvviso = posizione.getCodiceAvviso();
	this.descrizione = posizione.getDescrizioneCausale();
	this.importoIvato = posizione.getImportoIvato();
	if (posizione.getDataScadenza() != null) {
	    this.dataScadenza = Utilities.formatDate(posizione.getDataScadenza(), false);
	}
	this.descCodiceStatoNodo = posizione.getDescStato();
	if (posizione.getAnagrafe() != null) {
	    this.nominativo = posizione.getAnagrafe().getDescrizioneRichiedente();
	}
    }

    private void setDataUltimoStato(Date dataUltimoStato) {

	if (dataUltimoStato == null) {
	    this.dataUltimoStato = "";
	    return;
	}
	SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy H:m:s");
	this.dataUltimoStato = sdfr.format(dataUltimoStato);
    }

    public void setImporti(ImportoResponseType importi) {

	this.importi = importi;
    }

    public void setOperazioniConsentite(OperazioniConsentiteResponseType operazioniConsentite) {

	this.operazioniConsentite = operazioniConsentite;
    }

    @XmlTransient
    public Integer getId() {

	return id;
    }

    @XmlTransient
    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    @XmlTransient
    public String getDescrizione() {

	return descrizione;
    }

    @XmlTransient
    public ImportoResponseType getImporti() {

	return importi;
    }

    @XmlTransient
    public String getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getDescCodiceStatoNodo() {

	return descCodiceStatoNodo;
    }

    public String getNominativo() {

	return nominativo;
    }

    public BigDecimal getImportoIvato() {

	return importoIvato;
    }
}
