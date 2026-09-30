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

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "TIPIMOVIMENTO_RABBIT")
public class TipimovimentoRabbit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5511066790884859994L;
    private TipimovimentoRabbitId id;
    private Tipimovimento tipimovimento;
    private Mailtipo mailtipo;
    private String categoria;

    public TipimovimentoRabbit() {

	this.id = new TipimovimentoRabbitId();
	this.tipimovimento = new Tipimovimento();
	this.mailtipo = new Mailtipo();
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "tipimovimento", column = @Column(name = "fkTipimovimento", nullable = false, precision = 8, scale = 0)),
	    @AttributeOverride(name = "topic", column = @Column(name = "topic", nullable = false, length = 50)) })
    public TipimovimentoRabbitId getId() {

	return id;
    }

    public void setId(TipimovimentoRabbitId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_TIPIMOVIMENTO", referencedColumnName = "TIPOMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Tipimovimento getTipimovimento() {

	return tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MAILTIPO", referencedColumnName = "CODICEMAIL", nullable = false, insertable = false, updatable = false) })
    public Mailtipo getMailtipo() {

	return mailtipo;
    }

    public void setMailtipo(Mailtipo mailtipo) {

	this.mailtipo = mailtipo;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mailtipoId;

    @Column(name = "fk_mailtipo")
    @SuppressWarnings("unused")
    private Integer getMailtipoId() {

	if (null != this.getMailtipo()) {
	    if (null != this.getMailtipo().getId()) {
		this.mailtipoId = getMailtipo().getId().getCodice();
		return this.mailtipoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMailtipoId(Integer mailtipoId) {

	if (null != this.getMailtipo()) {
	    if (null != this.getMailtipo().getId()) {
		this.mailtipoId = getMailtipo().getId().getCodice();
	    }
	}
    }
    //END /////////////////////////////////////////////////////

    @Column(name = "CATEGORIA", nullable = true, length = 30)
    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }
}
