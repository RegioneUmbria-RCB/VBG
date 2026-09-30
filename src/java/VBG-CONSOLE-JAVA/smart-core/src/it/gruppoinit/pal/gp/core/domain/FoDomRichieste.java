package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "FO_DOM_RICHIESTE")
public class FoDomRichieste implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6573839215171403383L;
    private PkId id;
    private String messaggio;
    private Date dataRichiesta;
    private Integer ggAttesa;
    private String enteRfc53;
    private String idpratica;
    private FoDomande foDomande;
    private String tipoRichiesta;
    private String idRichiestaSistema;
    private Boolean proposta;
    private Boolean flagLetto;
    private String versoInOut;
    private FoDomRichieste richiestaPadre;
    private Boolean flagCompletata;
    private Boolean confermaRicezioneSuap;
    private Date dataRicezioneSuap;
    private String erroreRicezioneSuap;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "FO_DOM_RICHIESTE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "MESSAGGIO")
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_RICHIESTA", length = 7)
    public Date getDataRichiesta() {

	return dataRichiesta;
    }

    public void setDataRichiesta(Date dataRichiesta) {

	this.dataRichiesta = dataRichiesta;
    }

    @Column(name = "GG_ATTESA", precision = 6, scale = 0)
    public Integer getGgAttesa() {

	return ggAttesa;
    }

    public void setGgAttesa(Integer ggAttesa) {

	this.ggAttesa = ggAttesa;
    }

    @Column(name = "ENTE_RFC53", length = 30)
    public String getEnteRfc53() {

	return enteRfc53;
    }

    public void setEnteRfc53(String enteRfc53) {

	this.enteRfc53 = enteRfc53;
    }

    @Column(name = "IDPRATICA", length = 200)
    public String getIdpratica() {

	return idpratica;
    }

    public void setIdpratica(String idpratica) {

	this.idpratica = idpratica;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKIDDOMANDA", referencedColumnName = "ID", insertable = false, updatable = false) })
    public FoDomande getFoDomande() {

	return this.foDomande;
    }

    public void setFoDomande(FoDomande foDomande) {

	this.foDomande = foDomande;
    }

    private Integer foDomandeId;

    @Column(name = "FKIDDOMANDA")
    private Integer getFoDomandeId() {

	if (null != this.getFoDomande()) {
	    if (null != this.getFoDomande().getId()) {
		this.foDomandeId = getFoDomande().getId().getCodice();
		return this.foDomandeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setFoDomandeId(Integer foDomandeId) {

	if (null != this.getFoDomande()) {
	    if (null != this.getFoDomande().getId()) {
		this.foDomandeId = getFoDomande().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @Column(name = "TIPO_RICHIESTA", length = 1)
    public String getTipoRichiesta() {

	return tipoRichiesta;
    }

    public void setTipoRichiesta(String tipoRichiesta) {

	this.tipoRichiesta = tipoRichiesta;
    }

    @Column(name = "ID_RICHIESTA_SISTEMA", length = 200)
    public String getIdRichiestaSistema() {

	return idRichiestaSistema;
    }

    public void setIdRichiestaSistema(String idRichiestaSistema) {

	this.idRichiestaSistema = idRichiestaSistema;
    }

    @Column(name = "PROPOSTA", precision = 1, scale = 0)
    public Boolean getProposta() {

	return proposta;
    }

    public void setProposta(Boolean proposta) {

	this.proposta = proposta;
    }

    @Column(name = "FLAG_LETTO", precision = 1, scale = 0)
    public Boolean getFlagLetto() {

	return flagLetto;
    }

    public void setFlagLetto(Boolean flagLetto) {

	this.flagLetto = flagLetto;
    }

    @Column(name = "VERSO_IN_OUT", length = 1)
    public String getVersoInOut() {

	return versoInOut;
    }

    public void setVersoInOut(String versoInOut) {

	this.versoInOut = versoInOut;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_RICHIESTA", referencedColumnName = "ID", insertable = false, updatable = false) })
    public FoDomRichieste getRichiestaPadre() {

	return richiestaPadre;
    }

    public void setRichiestaPadre(FoDomRichieste richiestaPadre) {

	this.richiestaPadre = richiestaPadre;
    }

    private Integer richiestaPadreId;

    @Column(name = "FK_ID_RICHIESTA")
    private Integer getRichiestaPadreId() {

	if (null != this.getRichiestaPadre()) {
	    if (null != this.getRichiestaPadre().getId()) {
		this.richiestaPadreId = getRichiestaPadre().getId().getCodice();
		return this.richiestaPadreId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setRichiestaPadreId(Integer richiestaPadreId) {

	if (null != this.getRichiestaPadre()) {
	    if (null != this.getRichiestaPadre().getId()) {
		this.richiestaPadreId = getRichiestaPadre().getId().getCodice();
	    }
	}
    }

    @Column(name = "FLAG_COMPLETATA", precision = 1, scale = 0)
    public Boolean getFlagCompletata() {

	return flagCompletata;
    }

    public void setFlagCompletata(Boolean flagCompletata) {

	this.flagCompletata = flagCompletata;
    }

    @Column(name = "CONFERMA_RICEZIONE_SUAP", nullable = true, precision = 1, scale = 0)
    public Boolean getConfermaRicezioneSuap() {

	return confermaRicezioneSuap;
    }

    public void setConfermaRicezioneSuap(Boolean confermaRicezioneSuap) {

	this.confermaRicezioneSuap = confermaRicezioneSuap;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_RICEZIONE_SUAP", length = 7)
    public Date getDataRicezioneSuap() {

	return dataRicezioneSuap;
    }

    public void setDataRicezioneSuap(Date dataRicezioneSuap) {

	this.dataRicezioneSuap = dataRicezioneSuap;
    }

    @Column(name = "ERRORE_RICEZIONE_SUAP", length = 500)
    public String getErroreRicezioneSuap() {

	return erroreRicezioneSuap;
    }

    public void setErroreRicezioneSuap(String erroreRicezioneSuap) {

	this.erroreRicezioneSuap = erroreRicezioneSuap;
    }
}
