package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class PosteggiConcessioniHelper implements Comparable<PosteggiConcessioniHelper> {

    private Integer idautsub;
    private String idcomune;
    private Integer codicemercato;
    private Integer iduso;
    private Integer idposteggio;
    private String codiceposteggio;
    private String software;
    private Integer codiceconcessione;
    private Integer codiceistanza;
    private Integer codicetitolare;
    private Integer codiceoccupante;
    private Date datacessazione;
    private Date datafineaffitto;
    private boolean flagcausaliaffitto;
    private boolean posteggiodisabilitato;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodicemercato() {

	return codicemercato;
    }

    public void setCodicemercato(Integer codicemercato) {

	this.codicemercato = codicemercato;
    }

    public Integer getIduso() {

	return iduso;
    }

    public void setIduso(Integer iduso) {

	this.iduso = iduso;
    }

    public Integer getIdposteggio() {

	return idposteggio;
    }

    public void setIdposteggio(Integer idposteggio) {

	this.idposteggio = idposteggio;
    }

    public String getCodiceposteggio() {

	return codiceposteggio;
    }

    public void setCodiceposteggio(String codiceposteggio) {

	this.codiceposteggio = codiceposteggio;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Integer getCodiceconcessione() {

	return codiceconcessione;
    }

    public void setCodiceconcessione(Integer codiceconcessione) {

	this.codiceconcessione = codiceconcessione;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public Integer getCodicetitolare() {

	return codicetitolare;
    }

    public void setCodicetitolare(Integer codicetitolare) {

	this.codicetitolare = codicetitolare;
    }

    public Integer getCodiceoccupante() {

	return codiceoccupante;
    }

    public void setCodiceoccupante(Integer codiceoccupante) {

	this.codiceoccupante = codiceoccupante;
    }

    public boolean isConcessionePresente() {

	return this.codiceconcessione != null;
    }

    public Date getDatacessazione() {

	return datacessazione;
    }

    public void setDatacessazione(Date datacessazione) {

	this.datacessazione = datacessazione;
    }

    public Date getDatafineaffitto() {

	return datafineaffitto;
    }

    public void setDatafineaffitto(Date datafineaffitto) {

	this.datafineaffitto = datafineaffitto;
    }

    public boolean isFlagcausaliaffitto() {

	return flagcausaliaffitto;
    }

    public void setFlagcausaliaffitto(boolean flagcausaliaffitto) {

	this.flagcausaliaffitto = flagcausaliaffitto;
    }

    public Integer getIdautsub() {

	return idautsub;
    }

    public void setIdautsub(Integer idautsub) {

	this.idautsub = idautsub;
    }

    public boolean isPosteggiodisabilitato() {

	return posteggiodisabilitato;
    }

    public void setPosteggiodisabilitato(boolean posteggiodisabilitato) {

	this.posteggiodisabilitato = posteggiodisabilitato;
    }

    @Override
    public int compareTo(PosteggiConcessioniHelper o) {

	if (o == null) {
	    return -1;
	}
	Date lclDataCessazione = (this.getDatacessazione() == null) ? Utilities.getDate("31/12/2099", WebConstants.DATE_FORMAT_PATTERN).getTime()
		: this.getDatacessazione();
	Date datacessazione2 = (o.getDatacessazione() == null) ? Utilities.getDate("31/12/2099", WebConstants.DATE_FORMAT_PATTERN).getTime()
		: o.getDatacessazione();
	int ret = Utilities.dateWithoutTime(lclDataCessazione).compareTo(Utilities.dateWithoutTime(datacessazione2));
	if (ret != 0) {
	    return ret;
	}
	Integer idAutSub = this.getIdautsub() == null ? 0 : this.getIdautsub();
	Integer idAutSub2 = o.getIdautsub() == null ? 0 : o.getIdautsub();
	return idAutSub.compareTo(idAutSub2);
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
