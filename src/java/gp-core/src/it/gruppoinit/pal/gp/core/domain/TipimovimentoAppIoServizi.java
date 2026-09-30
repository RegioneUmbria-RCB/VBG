package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;

@Entity
@Table(name = "TIPIMOVIMENTO_APP_IO_SERVIZI")
public class TipimovimentoAppIoServizi implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1699145089449826031L;
    private TipimovimentoAppIoServiziId id;
    private Tipimovimento tipimovimento;
    private AppIoServizi appIoServizi;
    private String templateOggetto;
    private String templateMessaggio;

    public TipimovimentoAppIoServizi() {

	this.id = new TipimovimentoAppIoServiziId();
	this.tipimovimento = new Tipimovimento();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)),
	    @AttributeOverride(name = "tipomovimento", column = @Column(name = "TIPOMOVIMENTO", nullable = false, length = 8)) })
    public TipimovimentoAppIoServiziId getId() {

	return this.id;
    }

    public void setId(TipimovimentoAppIoServiziId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "TIPOMOVIMENTO", referencedColumnName = "TIPOMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Tipimovimento getTipimovimento() {

	return tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }
    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    /*private String tipimovimentoId;
    
    private String getTipimovimentoId() {
    
    if (null != this.getTipimovimento() && null != this.getTipimovimento().getId()) {
        this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
        return this.tipimovimentoId;
    }
    return null;
    }
    
    @SuppressWarnings("unused")
    private void setTipimovimentoId(String tipimovimentoId) {
    
    if (null != this.getTipimovimento() && null != this.getTipimovimento().getId()) {
        this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
    }
    }*/
    //END FIX/////////////////////////////////////////////////////

    @Length(min = 10, max = 120)
    @Column(name = "TEMPLATE_OGGETTO")
    public String getTemplateOggetto() {

	return templateOggetto;
    }

    public void setTemplateOggetto(String templateOggetto) {

	this.templateOggetto = templateOggetto;
    }

    @Length(min = 80, max = 10000)
    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "TEMPLATE_MESSAGGIO")
    public String getTemplateMessaggio() {

	return templateMessaggio;
    }

    public void setTemplateMessaggio(String templateMessaggio) {

	this.templateMessaggio = templateMessaggio;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDENTIFICATIVO_SERVIZIO", referencedColumnName = "IDENTIFICATIVO_SERVIZIO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AppIoServizi getAppIoServizi() {

	return appIoServizi;
    }

    public void setAppIoServizi(AppIoServizi appIoServizi) {

	this.appIoServizi = appIoServizi;
    }
}
