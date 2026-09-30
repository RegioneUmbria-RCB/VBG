package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo;

import it.gruppoinit.pal.gp.pay.connector.mip.genova.BaseFolderCaricamento;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.MipFileHelper;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.TipiCodiceEnte;
import it.gruppoinit.pal.gp.pay.connector.mip.genova.TipiOperazione;

public class ParametriBean {

    private BaseFolderCaricamento baseFolderCaricamento;
    private TipiOperazione tipoOperazione;
    private TipiCodiceEnte tipoCodiceEnte;
    private MipFileHelper fileHelper;
    private String securityToken;
    private String codiceEnte;
    private String codiceVersamento;
    private String descrizioneCausale;
    private String idLotto;
    private String errore;
    private Boolean erroreTemporaneo;

    public BaseFolderCaricamento getBaseFolderCaricamento() {

	return baseFolderCaricamento;
    }

    public void setBaseFolderCaricamento(BaseFolderCaricamento baseFolderCaricamento) {

	this.baseFolderCaricamento = baseFolderCaricamento;
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

    public MipFileHelper getFileHelper() {

	return fileHelper;
    }

    public void setFileHelper(MipFileHelper fileHelper) {

	this.fileHelper = fileHelper;
    }

    public String getSecurityToken() {

	return securityToken;
    }

    public void setSecurityToken(String securityToken) {

	this.securityToken = securityToken;
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

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public String getIdLotto() {

	return idLotto;
    }

    public void setIdLotto(String idLotto) {

	this.idLotto = idLotto;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public Boolean getErroreTemporaneo() {

	return erroreTemporaneo;
    }

    public void setErroreTemporaneo(Boolean erroreTemporaneo) {

	this.erroreTemporaneo = erroreTemporaneo;
    }
}
