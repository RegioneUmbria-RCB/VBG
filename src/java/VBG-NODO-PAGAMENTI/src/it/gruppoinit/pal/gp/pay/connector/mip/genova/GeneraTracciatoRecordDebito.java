package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.util.Date;

public class GeneraTracciatoRecordDebito {

    private String nomeDocumento;
    private String nomeDocumentoAllegato;
    private Integer numPagine;
    private Integer annoDebito;
    private String idDebito;
    private Date dataEmissione;
    private String tipoCodiceIntestatario;
    private String codiceIntestatario;
    private String intestatario;
    private String tipoCodiceDebitore;
    private String codiceDebitore;
    private String codiceFiscaleDebitore;
    private String indirizzoDebitore;
    private String capDebitore;
    private String emailDebitore;
    private String comuneProvincia;
    private String modalitaSpedizione;
    private FlagPagamento flagPagamento;
    private FlagRateizzazione flagRateizzazione;
    private Integer numeroRate;
    private FlagRipartizione flagRipartizione;
    private FlagTipoAccorpamento flagTipoAccorpamento;
    private String descrizione;
    private FlagPresenzaIndirizzo flagPresenzaIndirizzo;
    private String identificativoLotto;
    private String causaleVersamento;
    private String versione;

    public String getCausaleVersamento() {

	return causaleVersamento;
    }

    public void setCausaleVersamento(String causaleVersamento) {

	this.causaleVersamento = causaleVersamento;
    }

    public String getVersione() {

	return versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
    }

    public String getEmailDebitore() {

	return emailDebitore;
    }

    public void setEmailDebitore(String emailDebitore) {

	this.emailDebitore = emailDebitore;
    }

    public String getIdentificativoLotto() {

	return identificativoLotto;
    }

    public void setIdentificativoLotto(String identificativoLotto) {

	this.identificativoLotto = identificativoLotto;
    }

    public String getNomeDocumentoAllegato() {

	return nomeDocumentoAllegato;
    }

    public void setNomeDocumentoAllegato(String nomeDocumentoAllegato) {

	this.nomeDocumentoAllegato = nomeDocumentoAllegato;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public FlagPresenzaIndirizzo getFlagPresenzaIndirizzo() {

	return flagPresenzaIndirizzo;
    }

    public void setFlagPresenzaIndirizzo(FlagPresenzaIndirizzo flagPresenzaIndirizzo) {

	this.flagPresenzaIndirizzo = flagPresenzaIndirizzo;
    }

    public FlagTipoAccorpamento getFlagTipoAccorpamento() {

	return flagTipoAccorpamento;
    }

    public void setFlagTipoAccorpamento(FlagTipoAccorpamento flagTipoAccorpamento) {

	this.flagTipoAccorpamento = flagTipoAccorpamento;
    }

    public Integer getNumeroRate() {

	return numeroRate;
    }

    public void setNumeroRate(Integer numeroRate) {

	this.numeroRate = numeroRate;
    }

    public FlagRipartizione getFlagRipartizione() {

	return flagRipartizione;
    }

    public void setFlagRipartizione(FlagRipartizione flagRipartizione) {

	this.flagRipartizione = flagRipartizione;
    }

    public FlagRateizzazione getFlagRateizzazione() {

	return flagRateizzazione;
    }

    public void setFlagRateizzazione(FlagRateizzazione flagRateizzazione) {

	this.flagRateizzazione = flagRateizzazione;
    }

    public FlagPagamento getFlagPagamento() {

	return flagPagamento;
    }

    public void setFlagPagamento(FlagPagamento flagPagamento) {

	this.flagPagamento = flagPagamento;
    }

    public String getComuneProvincia() {

	return comuneProvincia;
    }

    public void setComuneProvincia(String comuneProvincia) {

	this.comuneProvincia = comuneProvincia;
    }

    public String getModalitaSpedizione() {

	return modalitaSpedizione;
    }

    public void setModalitaSpedizione(String modalitaSpedizione) {

	this.modalitaSpedizione = modalitaSpedizione;
    }

    public String getIndirizzoDebitore() {

	return indirizzoDebitore;
    }

    public void setIndirizzoDebitore(String indirizzoDebitore) {

	this.indirizzoDebitore = indirizzoDebitore;
    }

    public String getCapDebitore() {

	return capDebitore;
    }

    public void setCapDebitore(String capDebitore) {

	this.capDebitore = capDebitore;
    }

    public String getTipoCodiceDebitore() {

	return tipoCodiceDebitore;
    }

    public void setTipoCodiceDebitore(String tipoCodiceDebitore) {

	this.tipoCodiceDebitore = tipoCodiceDebitore;
    }

    public String getCodiceDebitore() {

	return codiceDebitore;
    }

    public void setCodiceDebitore(String codiceDebitore) {

	this.codiceDebitore = codiceDebitore;
    }

    public String getCodiceFiscaleDebitore() {

	return codiceFiscaleDebitore;
    }

    public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {

	this.codiceFiscaleDebitore = codiceFiscaleDebitore;
    }

    public String getIntestatario() {

	return intestatario;
    }

    public void setIntestatario(String intestatario) {

	this.intestatario = intestatario;
    }

    public String getTipoCodiceIntestatario() {

	return tipoCodiceIntestatario;
    }

    public void setTipoCodiceIntestatario(String tipoCodiceIntestatario) {

	this.tipoCodiceIntestatario = tipoCodiceIntestatario;
    }

    public String getCodiceIntestatario() {

	return codiceIntestatario;
    }

    public void setCodiceIntestatario(String codiceIntestatario) {

	this.codiceIntestatario = codiceIntestatario;
    }

    public String getNomeDocumento() {

	return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {

	this.nomeDocumento = nomeDocumento;
    }

    public Integer getNumPagine() {

	return numPagine;
    }

    public void setNumPagine(Integer numPagine) {

	this.numPagine = numPagine;
    }

    public Integer getAnnoDebito() {

	return annoDebito;
    }

    public void setAnnoDebito(Integer annoDebito) {

	this.annoDebito = annoDebito;
    }

    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public Date getDataEmissione() {

	return dataEmissione;
    }

    public void setDataEmissione(Date dataEmissione) {

	this.dataEmissione = dataEmissione;
    }
}
