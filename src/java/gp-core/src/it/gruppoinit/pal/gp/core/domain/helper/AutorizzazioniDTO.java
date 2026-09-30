package it.gruppoinit.pal.gp.core.domain.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.PkId;

public class AutorizzazioniDTO {

    private PkId id;
    private String autorizcomune;
    private String autoriznumero;
    private Date autorizdata;
    private String tipologiaregistro;
    private String codiceposteggio;
    private Integer idposteggio;
    private String transientEstremiAut;
    private Boolean flagAttiva;
    private Date dataCessazione;
    private String autoriznumeroSubentro;
    private String mercatiUsoDescrizione;
    private String note;
    private String notesistema;
    // campi aut originaria
    private String autorignumero;
    private Date autorigdata;
    private String autorigcomune;
    private Integer codicetitolareaut;
    private String nometitolareaut;
    private String cognometitolareaut;
    private String cftitolareaut;
    private String pivatitolareaut;
    private Integer codiceoccupaut;
    private String nomeoccupaut;
    private String cognomeoccupaut;
    private String cfoccupaut;
    private String pivaoccupaut;

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public Integer getIdposteggio() {

	return idposteggio;
    }

    public void setIdposteggio(Integer idposteggio) {

	this.idposteggio = idposteggio;
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

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public String getNotesistema() {

	return notesistema;
    }

    public void setNotesistema(String notesistema) {

	this.notesistema = notesistema;
    }

    public String getTransientEstremiAut() {

	boolean returnEmpty = true;
	String autorizNumero = "-";
	if (this.getAutoriznumero() != null) {
	    autorizNumero = autoriznumero;
	    returnEmpty = false;
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);
	String autorizData = "-";
	if (this.getAutorizdata() != null) {
	    autorizData = dateFormat.format(autorizdata);
	    returnEmpty = false;
	}
	String autorizComune = "-";
	if (StringUtils.isNotBlank(autorizcomune)) {
	    autorizComune = autorizcomune;
	    returnEmpty = false;
	}
	String registro = "-";
	if (StringUtils.isNotBlank(tipologiaregistro)) {
	    registro = tipologiaregistro;
	    returnEmpty = false;
	}
	if (returnEmpty) {
	    return "";
	}
	transientEstremiAut = autorizNumero + "," + autorizData + "," + autorizComune + "," + registro;
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

    public String getAutorignumero() {

	return autorignumero;
    }

    public void setAutorignumero(String autorignumero) {

	this.autorignumero = autorignumero;
    }

    public Date getAutorigdata() {

	return autorigdata;
    }

    public void setAutorigdata(Date autorigdata) {

	this.autorigdata = autorigdata;
    }

    public String getAutorigcomune() {

	return autorigcomune;
    }

    public void setAutorigcomune(String autorigcomune) {

	this.autorigcomune = autorigcomune;
    }

    public Integer getCodicetitolareaut() {

	return codicetitolareaut;
    }

    public void setCodicetitolareaut(Integer codicetitolareaut) {

	this.codicetitolareaut = codicetitolareaut;
    }

    public String getNometitolareaut() {

	return nometitolareaut;
    }

    public void setNometitolareaut(String nometitolareaut) {

	this.nometitolareaut = nometitolareaut;
    }

    public String getCognometitolareaut() {

	return cognometitolareaut;
    }

    public void setCognometitolareaut(String cognometitolareaut) {

	this.cognometitolareaut = cognometitolareaut;
    }

    public String getCftitolareaut() {

	return cftitolareaut;
    }

    public void setCftitolareaut(String cftitolareaut) {

	this.cftitolareaut = cftitolareaut;
    }

    public String getPivatitolareaut() {

	return pivatitolareaut;
    }

    public void setPivatitolareaut(String pivatitolareaut) {

	this.pivatitolareaut = pivatitolareaut;
    }

    public Integer getCodiceoccupaut() {

	return codiceoccupaut;
    }

    public void setCodiceoccupaut(Integer codiceoccupaut) {

	this.codiceoccupaut = codiceoccupaut;
    }

    public String getNomeoccupaut() {

	return nomeoccupaut;
    }

    public void setNomeoccupaut(String nomeoccupaut) {

	this.nomeoccupaut = nomeoccupaut;
    }

    public String getCognomeoccupaut() {

	return cognomeoccupaut;
    }

    public void setCognomeoccupaut(String cognomeoccupaut) {

	this.cognomeoccupaut = cognomeoccupaut;
    }

    public String getCfoccupaut() {

	return cfoccupaut;
    }

    public void setCfoccupaut(String cfoccupaut) {

	this.cfoccupaut = cfoccupaut;
    }

    public String getPivaoccupaut() {

	return pivaoccupaut;
    }

    public void setPivaoccupaut(String pivaoccupaut) {

	this.pivaoccupaut = pivaoccupaut;
    }

    public String getDescrizioneTitolare() {

	String answer = getCognometitolareaut();
	if (StringUtils.isNotBlank(getNometitolareaut())) {
	    answer += " " + getNometitolareaut();
	}
	if (StringUtils.isNotBlank(getPivatitolareaut())) {
	    answer += " P.Iva: " + getPivatitolareaut();
	}
	if (StringUtils.isNotBlank(getCftitolareaut())) {
	    answer += " CF: " + getCftitolareaut();
	}
	return answer;
    }

    public String getDescrizioneOccupante() {

	String answer = getCognomeoccupaut();
	if (StringUtils.isNotBlank(getNomeoccupaut())) {
	    answer += " " + getNomeoccupaut();
	}
	if (StringUtils.isNotBlank(getPivaoccupaut())) {
	    answer += " P.Iva: " + getPivaoccupaut();
	}
	if (StringUtils.isNotBlank(getCfoccupaut())) {
	    answer += " CF: " + getCfoccupaut();
	}
	return answer;
    }
}
