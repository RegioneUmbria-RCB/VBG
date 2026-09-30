package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

public class BollGestDettaglioDTO {

    private int codiceanagrafe;
    private String nominativo;
    private String nome;
    private String codicefiscale;
    private String partitaiva;
    private String tipoanagrafe;
    private Integer tipologia;
    private String formagiuridica;
    private Integer id;
    private Integer fkbollgestid;
    private Boolean flagvalidata;
    private Integer fkrettificaid;
    private String descrizione;
    private Boolean flaginsauto;
    private Integer fkcodiceanagrafe;
    private Integer fkcontoid;
    private Integer fkposdebdettaglioid;
    private Boolean flageliminata;
    private String noteutente;
    private String notesistema;
    private Boolean flagrettificata;
    private BigDecimal importosenzaiva;
    private Integer iva;
    private BigDecimal importototale;
    private Boolean flagconguaglio;
    private Date datascadenza;
    private String conto;
    private Integer idposizionerateizzata;

    public int getCodiceanagrafe() {

	return codiceanagrafe;
    }

    public void setCodiceanagrafe(int codiceanagrafe) {

	this.codiceanagrafe = codiceanagrafe;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public String getNome() {

	return nome;
    }

    public void setNome(String nome) {

	this.nome = nome;
    }

    public String getCodicefiscale() {

	return codicefiscale;
    }

    public void setCodicefiscale(String codicefiscale) {

	this.codicefiscale = codicefiscale;
    }

    public String getPartitaiva() {

	return partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Integer getFkbollgestid() {

	return fkbollgestid;
    }

    public void setFkbollgestid(Integer fkbollgestid) {

	this.fkbollgestid = fkbollgestid;
    }

    public Boolean getFlagvalidata() {

	return flagvalidata;
    }

    public void setFlagvalidata(Boolean flagvalidata) {

	this.flagvalidata = flagvalidata;
    }

    public Integer getFkrettificaid() {

	return fkrettificaid;
    }

    public void setFkrettificaid(Integer fkrettificaid) {

	this.fkrettificaid = fkrettificaid;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Boolean getFlaginsauto() {

	return flaginsauto;
    }

    public void setFlaginsauto(Boolean flaginsauto) {

	this.flaginsauto = flaginsauto;
    }

    public Integer getFkcodiceanagrafe() {

	return fkcodiceanagrafe;
    }

    public void setFkcodiceanagrafe(Integer fkcodiceanagrafe) {

	this.fkcodiceanagrafe = fkcodiceanagrafe;
    }

    public Integer getFkcontoid() {

	return fkcontoid;
    }

    public void setFkcontoid(Integer fkcontoid) {

	this.fkcontoid = fkcontoid;
    }

    public Integer getFkposdebdettaglioid() {

	return fkposdebdettaglioid;
    }

    public void setFkposdebdettaglioid(Integer fkposdebdettaglioid) {

	this.fkposdebdettaglioid = fkposdebdettaglioid;
    }

    public Boolean getFlageliminata() {

	return flageliminata;
    }

    public void setFlageliminata(Boolean flageliminata) {

	this.flageliminata = flageliminata;
    }

    public String getNoteutente() {

	return noteutente;
    }

    public void setNoteutente(String noteutente) {

	this.noteutente = noteutente;
    }

    public String getNotesistema() {

	return notesistema;
    }

    public void setNotesistema(String notesistema) {

	this.notesistema = notesistema;
    }

    public Boolean getFlagrettificata() {

	return flagrettificata;
    }

    public void setFlagrettificata(Boolean flagrettificata) {

	this.flagrettificata = flagrettificata;
    }

    public BigDecimal getImportosenzaiva() {

	return importosenzaiva;
    }

    public void setImportosenzaiva(BigDecimal importosenzaiva) {

	this.importosenzaiva = importosenzaiva;
    }

    public Integer getIva() {

	return iva;
    }

    public void setIva(Integer iva) {

	this.iva = iva;
    }

    public BigDecimal getImportototale() {

	return importototale;
    }

    public void setImportototale(BigDecimal importototale) {

	this.importototale = importototale;
    }

    public Boolean getFlagconguaglio() {

	return flagconguaglio;
    }

    public void setFlagconguaglio(Boolean flagconguaglio) {

	this.flagconguaglio = flagconguaglio;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public String getConto() {

	return conto;
    }

    public void setConto(String conto) {

	this.conto = conto;
    }

    public String getTipoanagrafe() {

	return tipoanagrafe;
    }

    public void setTipoanagrafe(String tipoanagrafe) {

	this.tipoanagrafe = tipoanagrafe;
    }

    public Integer getTipologia() {

	return tipologia;
    }

    public void setTipologia(Integer tipologia) {

	this.tipologia = tipologia;
    }

    public String getFormagiuridica() {

	return formagiuridica;
    }

    public void setFormagiuridica(String formagiuridica) {

	this.formagiuridica = formagiuridica;
    }

    public Integer getIdposizionerateizzata() {

	return idposizionerateizzata;
    }

    public void setIdposizionerateizzata(Integer idposizionerateizzata) {

	this.idposizionerateizzata = idposizionerateizzata;
    }

    public String getDescrizioneRichiedente() {

	String answer = "";
	String tNominativo = getNominativo() == null ? "" : getNominativo();
	if (getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)) {
	    answer = tNominativo + " " + (getNome() == null ? "" : getNome());
	    if (StringUtils.isNotBlank(getCodicefiscale())) {
		answer += " CF: " + getCodicefiscale();
	    }
	} else {
	    answer = tNominativo;
	    answer += " " + StringUtils.defaultString(getFormagiuridica());
	    if (StringUtils.isNotBlank(getPartitaiva())) {
		answer += " P.Iva: " + getPartitaiva();
	    }
	    if (StringUtils.isNotBlank(getCodicefiscale())) {
		answer += " CF: " + getCodicefiscale();
	    }
	}
	if (getTipologia() != null) {
	    if (getTipologia().intValue() == -1) {
		answer += " T";
	    }
	}
	if (StringUtils.isNotBlank(getTipoanagrafe())) {
	    answer += " [P." + getTipoanagrafe() + ".]";
	}
	return answer;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
