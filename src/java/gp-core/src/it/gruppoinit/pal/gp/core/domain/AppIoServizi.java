package it.gruppoinit.pal.gp.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;

@Entity
@Table(name = "APP_IO_SERVIZI")
public class AppIoServizi implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4940510177010025501L;
    private AppIoServiziId id;
    private String descrizione;
    private Set<AppIoServiziConfig> appIoServiziConfigs = new HashSet<AppIoServiziConfig>(0);
    private Set<AppIoParam> appIoParam = new HashSet<AppIoParam>(0);
    private Set<AppIoServiziConfigParam> appIoServiziConfigParam = new HashSet<AppIoServiziConfigParam>(0);
    private Set<TipimovimentoAppIoServizi> tipimovimentoAppIoServizi = new HashSet<TipimovimentoAppIoServizi>(0);
    private Set<AppIoCoda> appIoCoda = new HashSet<AppIoCoda>(0);

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)) })
    public AppIoServiziId getId() {

	return id;
    }

    public void setId(AppIoServiziId id) {

	this.id = id;
    }

    @Length(max = 1000)
    @Column(name = "DESCRIZIONE")
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appIoServizi", targetEntity = AppIoServiziConfig.class)
    public Set<AppIoServiziConfig> getAppIoServiziConfigs() {

	return appIoServiziConfigs;
    }

    public void setAppIoServiziConfigs(Set<AppIoServiziConfig> appIoServiziConfigs) {

	this.appIoServiziConfigs = appIoServiziConfigs;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "servizio", targetEntity = AppIoParam.class)
    public Set<AppIoParam> getAppIoParam() {

	return appIoParam;
    }

    public void setAppIoParam(Set<AppIoParam> appIoParam) {

	this.appIoParam = appIoParam;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appIoServizi", targetEntity = AppIoServiziConfigParam.class)
    public Set<AppIoServiziConfigParam> getAppIoServiziConfigParam() {

	return appIoServiziConfigParam;
    }

    public void setAppIoServiziConfigParam(Set<AppIoServiziConfigParam> appIoServiziConfigParam) {

	this.appIoServiziConfigParam = appIoServiziConfigParam;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appIoServizi", targetEntity = TipimovimentoAppIoServizi.class)
    public Set<TipimovimentoAppIoServizi> getTipimovimentoAppIoServizi() {

	return tipimovimentoAppIoServizi;
    }

    public void setTipimovimentoAppIoServizi(Set<TipimovimentoAppIoServizi> tipimovimentoAppIoServizi) {

	this.tipimovimentoAppIoServizi = tipimovimentoAppIoServizi;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appIoServizi", targetEntity = AppIoCoda.class)
    public Set<AppIoCoda> getAppIoCoda() {

	return appIoCoda;
    }

    public void setAppIoCoda(Set<AppIoCoda> appIoCoda) {

	this.appIoCoda = appIoCoda;
    }
}
