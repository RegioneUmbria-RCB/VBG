package it.gruppoinit.pal.gp.core.domain;

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
@Table(name = "TIPIMOV_APPIOSERVIZI_ENDO")
public class TipimovAppioServiziEndo {

    private TipimovAppioServiziEndoId id;
    private AppIoServizi appIoServizi;
    private Tipimovimento tipimovimento;
    private Inventarioprocedimenti inventarioprocedimenti;
    private String templateOggetto;
    private String templateMessaggio;

    public TipimovAppioServiziEndo() {

	// Niente da inizializzare
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)),
	    @AttributeOverride(name = "tipomovimento", column = @Column(name = "TIPOMOVIMENTO", nullable = false, length = 8)),
	    @AttributeOverride(name = "codiceinventario", column = @Column(name = "CODICEINVENTARIO", nullable = false, length = 10)) })
    public TipimovAppioServiziEndoId getId() {

	return id;
    }

    public void setId(TipimovAppioServiziEndoId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEINVENTARIO", referencedColumnName = "CODICEINVENTARIO", insertable = false, updatable = false) })
    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
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
}
