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
@Table(name = "TIPIMOV_APPIOSERVIZI_INT")
public class TipimovAppioServiziInt {

    private TipimovAppioServiziIntId id;
    private AppIoServizi appIoServizi;
    private Tipimovimento tipimovimento;
    private Alberoproc alberoproc;
    private String templateOggetto;
    private String templateMessaggio;

    public TipimovAppioServiziInt() {

	// NIENTE DA INIZIALIZZARE
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)),
	    @AttributeOverride(name = "tipomovimento", column = @Column(name = "TIPOMOVIMENTO", nullable = false, length = 8)),
	    @AttributeOverride(name = "fkAlberoprocScid", column = @Column(name = "FK_ALBEROPROC_SCID", nullable = false, length = 10)) })
    public TipimovAppioServiziIntId getId() {

	return this.id;
    }

    public void setId(TipimovAppioServiziIntId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ALBEROPROC_SCID", referencedColumnName = "SC_ID", insertable = false, updatable = false) })
    public Alberoproc getAlberoproc() {

	return this.alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
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
