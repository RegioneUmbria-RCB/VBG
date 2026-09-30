package it.gruppoinit.pal.gp.core.features.manifestazioni.utils;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PresenzeNonPagateBean {

    private Integer idgiornata;
    private Date dataregistrazione;
    private String descrizionegiornata;
    private Integer idpresenza;
    private Integer idposteggio;
    private Integer numeropresenze;
    private Integer codiceanagrafe;
    private boolean spuntista;
    private boolean flagassenzagiustiticata;
    private Integer codiceconcessionario;
    private Integer fkautorizzazioniid;
    private Integer autconcessionario;
    private String fkcodiceistat;

    public Integer getIdgiornata() {

	return idgiornata;
    }

    public void setIdgiornata(Integer idgiornata) {

	this.idgiornata = idgiornata;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }

    public String getDescrizionegiornata() {

	return descrizionegiornata;
    }

    public void setDescrizionegiornata(String descrizionegiornata) {

	this.descrizionegiornata = descrizionegiornata;
    }

    public Integer getIdpresenza() {

	return idpresenza;
    }

    public void setIdpresenza(Integer idpresenza) {

	this.idpresenza = idpresenza;
    }

    public Integer getIdposteggio() {

	return idposteggio;
    }

    public void setIdposteggio(Integer idposteggio) {

	this.idposteggio = idposteggio;
    }

    public Integer getNumeropresenze() {

	return numeropresenze;
    }

    public void setNumeropresenze(Integer numeropresenze) {

	this.numeropresenze = numeropresenze;
    }

    public Integer getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(Integer codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public boolean isSpuntista() {

	return spuntista;
    }

    public void setSpuntista(boolean spuntista) {

	this.spuntista = spuntista;
    }

    public boolean isFlagassenzagiustiticata() {

	return flagassenzagiustiticata;
    }

    public void setFlagassenzagiustiticata(boolean flagassenzagiustiticata) {

	this.flagassenzagiustiticata = flagassenzagiustiticata;
    }

    public Integer getCodiceconcessionario() {

	return codiceconcessionario;
    }

    public void setCodiceconcessionario(Integer codiceconcessionario) {

	this.codiceconcessionario = codiceconcessionario;
    }

    public Integer getFkautorizzazioniid() {

	return fkautorizzazioniid;
    }

    public void setFkautorizzazioniid(Integer fkautorizzazioniid) {

	this.fkautorizzazioniid = fkautorizzazioniid;
    }

    public Integer getAutconcessionario() {

	return autconcessionario;
    }

    public void setAutconcessionario(Integer autconcessionario) {

	this.autconcessionario = autconcessionario;
    }

    public String getFkcodiceistat() {

	return fkcodiceistat;
    }

    public void setFkcodiceistat(String fkcodiceistat) {

	this.fkcodiceistat = fkcodiceistat;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
