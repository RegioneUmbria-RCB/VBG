package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class AutorizzazioniDTO {

    private PkId id;
    private String autorizcomune;
    private String autoriznumero;
    private Date autorizdata;
    private String tipologiaregistro;
    private String codiceposteggio;
    private String transientEstremiAut;
    private Boolean flagAttiva;
    private Date dataCessazione;
    private String autoriznumeroSubentro;
    private String mercatiUsoDescrizione;

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public Boolean getFlagAttiva() {

	return flagAttiva;
    }

    public void setFlagAttiva(Boolean flagAttiva) {

	this.flagAttiva = flagAttiva;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public AutorizzazioniDTO() {

	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public String getAutorizcomune() {

	return autorizcomune;
    }

    public void setAutorizcomune(String autorizcomune) {

	this.autorizcomune = autorizcomune;
    }

    public String getAutoriznumero() {

	return autoriznumero;
    }

    public void setAutoriznumero(String autoriznumero) {

	this.autoriznumero = autoriznumero;
    }

    public Date getAutorizdata() {

	return autorizdata;
    }

    public void setAutorizdata(Date autorizdata) {

	this.autorizdata = autorizdata;
    }

    public String getTipologiaregistro() {

	return tipologiaregistro;
    }

    public void setTipologiaregistro(String tipologiaregistro) {

	this.tipologiaregistro = tipologiaregistro;
    }

    public String getTransientEstremiAut() {

	boolean returnEmpty = true;
	String _autoriznumero = "-";
	if (this.getAutoriznumero() != null) {
	    _autoriznumero = autoriznumero;
	    returnEmpty = false;
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String _autorizdata = "-";
	if (this.getAutorizdata() != null) {
	    _autorizdata = dateFormat.format(autorizdata);
	    returnEmpty = false;
	}
	String _autorizcomune = "-";
	if (StringUtils.isNotBlank(autorizcomune)) {
	    _autorizcomune = autorizcomune;
	    returnEmpty = false;
	}
	String _registro = "-";
	if (StringUtils.isNotBlank(tipologiaregistro)) {
	    _registro = tipologiaregistro;
	    returnEmpty = false;
	}
	if (returnEmpty) {
	    return "";
	}
	transientEstremiAut = _autoriznumero + "," + _autorizdata + "," + _autorizcomune + "," + _registro;
	return transientEstremiAut;
    }

    public String getAutoriznumeroSubentro() {

	return autoriznumeroSubentro;
    }

    public void setAutoriznumeroSubentro(String autoriznumeroSubentro) {

	this.autoriznumeroSubentro = autoriznumeroSubentro;
    }

    public String getMercatiUsoDescrizione() {

	return mercatiUsoDescrizione;
    }

    public void setMercatiUsoDescrizione(String mercatiUsoDescrizione) {

	this.mercatiUsoDescrizione = mercatiUsoDescrizione;
    }
}
