package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.math.BigDecimal;
import java.util.Date;

import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;

public class GeneraTracciatoLottoRequest {

    private TipiOperazione tipoOperazione;
    private TipiCodiceEnte tipoCodiceEnte;
    private String codiceEnte;
    private String codiceVersamento;
    private String idLotto;
    private Date dataCreazione;
    private int totaleRigheDebito;
    private int numRate;
    private BigDecimal totLotto;
    private String flagTipoDocDebito;
    private PayProfiliEntiCreditori ente;
    private boolean accorpamento;
    private IdentificativoVettore vettore;
    private TipoPostalizzazione tipoPost;
    private boolean fronteRetro;
    private boolean colori;

    public boolean isAccorpamento() {

	return accorpamento;
    }

    public void setAccorpamento(boolean accorpamento) {

	this.accorpamento = accorpamento;
    }

    public IdentificativoVettore getVettore() {

	return vettore;
    }

    public void setVettore(IdentificativoVettore vettore) {

	this.vettore = vettore;
    }

    public TipoPostalizzazione getTipoPost() {

	return tipoPost;
    }

    public void setTipoPost(TipoPostalizzazione tipoPost) {

	this.tipoPost = tipoPost;
    }

    public boolean isFronteRetro() {

	return fronteRetro;
    }

    public void setFronteRetro(boolean fronteRetro) {

	this.fronteRetro = fronteRetro;
    }

    public boolean isColori() {

	return colori;
    }

    public void setColori(boolean colori) {

	this.colori = colori;
    }

    public TipiOperazione getTipoOperazione() {

	return tipoOperazione;
    }

    public void setTipoOperazione(TipiOperazione tipoOperazione) {

	this.tipoOperazione = tipoOperazione;
    }

    public TipiCodiceEnte getTipoCodiceEnte() {

	return tipoCodiceEnte;
    }

    public void setTipoCodiceEnte(TipiCodiceEnte tipoCodiceEnte) {

	this.tipoCodiceEnte = tipoCodiceEnte;
    }

    public String getCodiceEnte() {

	return codiceEnte;
    }

    public void setCodiceEnte(String codiceEnte) {

	this.codiceEnte = codiceEnte;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    public String getIdLotto() {

	return idLotto;
    }

    public void setIdLotto(String idLotto) {

	this.idLotto = idLotto;
    }

    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    public int getTotaleRigheDebito() {

	return totaleRigheDebito;
    }

    public void setTotaleRigheDebito(int totaleRigheDebito) {

	this.totaleRigheDebito = totaleRigheDebito;
    }

    public int getNumRate() {

	return numRate;
    }

    public void setNumRate(int numRate) {

	this.numRate = numRate;
    }

    public BigDecimal getTotLotto() {

	return totLotto;
    }

    public void setTotLotto(BigDecimal totLotto) {

	this.totLotto = totLotto;
    }

    public String getFlagTipoDocDebito() {

	return flagTipoDocDebito;
    }

    public void setFlagTipoDocDebito(String flagTipoDocDebito) {

	this.flagTipoDocDebito = flagTipoDocDebito;
    }

    public PayProfiliEntiCreditori getEnte() {

	return ente;
    }

    public void setEnte(PayProfiliEntiCreditori ente) {

	this.ente = ente;
    }
}
