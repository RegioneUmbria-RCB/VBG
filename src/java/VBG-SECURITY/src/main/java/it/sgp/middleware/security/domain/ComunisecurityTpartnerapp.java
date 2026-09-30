package it.sgp.middleware.security.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "COMUNISECURITY_TPARTNERAPP")
public class ComunisecurityTpartnerapp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1213440427754412225L;
    private Integer id;
    private String token;
    private String codicecomune;
    private String software;
    private String tokenpartnerapp;

    @Id
    @TableGenerator(name = "tablegen", table = "ID_TABLE", pkColumnName = "ID", pkColumnValue = "COMUNISECURITY_TPARTNERAPP_ID", valueColumnName = "NEXT_ID")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tablegen")
    @Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    @NotNull
    @Column(name = "TOKEN", length = 50)
    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    @Column(name = "CODICECOMUNE", length = 10)
    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    @Column(name = "SOFTWARE", length = 10)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    @Lob
    @Column(name = "TOKENPARTNERAPP")
    public String getTokenpartnerapp() {

	return tokenpartnerapp;
    }

    public void setTokenpartnerapp(String tokenpartnerapp) {

	this.tokenpartnerapp = tokenpartnerapp;
    }
}
