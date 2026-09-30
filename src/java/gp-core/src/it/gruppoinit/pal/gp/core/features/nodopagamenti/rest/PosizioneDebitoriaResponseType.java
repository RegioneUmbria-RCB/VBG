package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "posizione_debitoria")
@XmlSeeAlso({ ImportiResponseType.class, StatoResponseType.class, PagamentiResponseType.class, SessioniPagamentoResponseType.class })
public class PosizioneDebitoriaResponseType {

    @XmlElement(name = "id_posizione_debitoria")
    private Integer idPosizioneDebitoria;
    @XmlElement(name = "uuid")
    private String uuid;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "codice_avviso")
    private String codiceAvviso;
    @XmlElement(name = "qr_code")
    private String qrCode;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "data_registrazione", type = Date.class)
    private Date dataRegistrazione;
    @XmlElement(name = "importi")
    private Set<ImportiResponseType> importi = new HashSet<ImportiResponseType>();
    @XmlElement(name = "stato_attuale")
    private StatoResponseType statoAttuale;
    @XmlElement(name = "pagamenti")
    private Set<PagamentiResponseType> pagamenti = new HashSet<PagamentiResponseType>();
    @XmlElement(name = "stati")
    private Set<StatoResponseType> stati = new HashSet<StatoResponseType>();
    @XmlElement(name = "otf")
    private boolean otf;
    @XmlElement(name = "nominativo_soggetto_debitore")
    private String nominativoSoggettoDebitore;
    @XmlElement(name = "cf_soggetto_debitore")
    private String cfSoggettoDebitore;
    @XmlElement(name = "data_scadenza", type = Date.class)
    private Date dataScadenza;
    @XmlElement(name = "sessioniPagamento")
    private Set<SessioniPagamentoResponseType> sessioniPagamento = new HashSet<SessioniPagamentoResponseType>();

    public PosizioneDebitoriaResponseType() {

	super();
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getDataRegistrazione() {

	return dataRegistrazione;
    }

    public void setDataRegistrazione(Date dataRegistrazione) {

	this.dataRegistrazione = dataRegistrazione;
    }

    public String getUuid() {

	return uuid;
    }

    public String getIuv() {

	return iuv;
    }

    public String getQrCode() {

	return qrCode;
    }

    public Set<ImportiResponseType> getImporti() {

	return importi;
    }

    public StatoResponseType getStatoAttuale() {

	return statoAttuale;
    }

    public Set<PagamentiResponseType> getPagamenti() {

	return pagamenti;
    }

    public Set<StatoResponseType> getStati() {

	return stati;
    }

    public void setIdPosizioneDebitoria(Integer idPosizioneDebitoria) {

	this.idPosizioneDebitoria = idPosizioneDebitoria;
    }

    public Integer getIdPosizioneDebitoria() {

	return idPosizioneDebitoria;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public void setQrCode(String qrCode) {

	this.qrCode = qrCode;
    }

    public void setImporti(Set<ImportiResponseType> importi) {

	if (importi == null) {
	    importi = new HashSet<ImportiResponseType>();
	}
	this.importi = importi;
    }

    public void setStatoAttuale(StatoResponseType statoAttuale) {

	this.statoAttuale = statoAttuale;
    }

    public void setPagamenti(Set<PagamentiResponseType> pagamenti) {

	this.pagamenti = pagamenti;
    }

    public void setStati(Set<StatoResponseType> stati) {

	if (stati == null) {
	    stati = new HashSet<StatoResponseType>();
	}
	this.stati = stati;
    }

    public Set<SessioniPagamentoResponseType> getSessioniPagamento() {

	if (this.sessioniPagamento == null) {
	    this.sessioniPagamento = new HashSet<SessioniPagamentoResponseType>();
	}
	return sessioniPagamento;
    }

    public void setSessioniPagamento(Set<SessioniPagamentoResponseType> sessioniPagamento) {

	this.sessioniPagamento = sessioniPagamento;
    }

    public boolean getOtf() {

	return otf;
    }

    public void setOtf(boolean otf) {

	this.otf = otf;
    }

    public String getNominativoSoggettoDebitore() {

	return nominativoSoggettoDebitore;
    }

    public void setNominativoSoggettoDebitore(String nominativoSoggettoDebitore) {

	this.nominativoSoggettoDebitore = nominativoSoggettoDebitore;
    }

    public String getCfSoggettoDebitore() {

	return cfSoggettoDebitore;
    }

    public void setCfSoggettoDebitore(String cfSoggettoDebitore) {

	this.cfSoggettoDebitore = cfSoggettoDebitore;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }
}
