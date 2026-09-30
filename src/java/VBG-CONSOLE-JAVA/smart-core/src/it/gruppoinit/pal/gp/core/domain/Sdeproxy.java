package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.NotEmpty;

/**
 * @author riccardob
 *
 */
@Entity
@Table(name = "SDEPROXY")
public class Sdeproxy implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2231805403571931205L;
    private String idente;
    private String aliasEnte;
    private String descrizione;
    private String codicecatastalecomune;
    private String wsStradario;
    private String wsAnagrafeResidenti;
    private String wsStatopratica;
    private String usernameWs;
    private String passwordWs;
    private String idcomunebase;
    private String wsServizi;

    @Id
    @Column(name = "IDENTE", unique = true, nullable = false, length = 15)
    public String getIdente() {

	return idente;
    }

    public void setIdente(String idente) {

	this.idente = idente;
    }

    @NotEmpty
    @Column(name = "ALIAS_ENTE", length = 20)
    public String getAliasEnte() {

	return aliasEnte;
    }

    public void setAliasEnte(String aliasEnte) {

	this.aliasEnte = aliasEnte;
    }

    @Column(name = "DESCRIZIONE", length = 255)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "CODICECATASTALECOMUNE", length = 5)
    public String getCodicecatastalecomune() {

	return codicecatastalecomune;
    }

    public void setCodicecatastalecomune(String codicecatastalecomune) {

	this.codicecatastalecomune = codicecatastalecomune;
    }

    @Column(name = "WS_STRADARIO", length = 255)
    public String getWsStradario() {

	return wsStradario;
    }

    public void setWsStradario(String wsStradario) {

	this.wsStradario = wsStradario;
    }

    @Column(name = "WS_ANAGRAFE_RESIDENTI", length = 255)
    public String getWsAnagrafeResidenti() {

	return wsAnagrafeResidenti;
    }

    public void setWsAnagrafeResidenti(String wsAnagrafeResidenti) {

	this.wsAnagrafeResidenti = wsAnagrafeResidenti;
    }

    @Column(name = "WS_STATOPRATICA", length = 255)
    public String getWsStatopratica() {

	return wsStatopratica;
    }

    public void setWsStatopratica(String wsStatopratica) {

	this.wsStatopratica = wsStatopratica;
    }

    @Column(name = "USERNAME_WS", length = 15)
    public String getUsernameWs() {

	return usernameWs;
    }

    public void setUsernameWs(String usernameWs) {

	this.usernameWs = usernameWs;
    }

    @Column(name = "PASSWORD_WS", length = 15)
    public String getPasswordWs() {

	return passwordWs;
    }

    public void setPasswordWs(String passwordWs) {

	this.passwordWs = passwordWs;
    }

    @Column(name = "IDCOMUNEBASE", length = 6)
    public String getIdcomunebase() {

	return idcomunebase;
    }

    public void setIdcomunebase(String idcomunebase) {

	this.idcomunebase = idcomunebase;
    }

    @Column(name = "WS_SERVIZI", length = 255)
    public String getWsServizi() {

	return wsServizi;
    }

    public void setWsServizi(String wsServizi) {

	this.wsServizi = wsServizi;
    }
}
