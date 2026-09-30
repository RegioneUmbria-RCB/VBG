package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BORSELLINO_INFORMATIVE")
public class BorsellinoInformative implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6561914468275861902L;
    private PkId id;
    private Comuni comune;
    private String informativa;
    private Date dataFineValidita;

    public BorsellinoInformative() {

	this.id = new PkId();
	this.comune = new Comuni();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_INFORMATIVE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE")
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    @NotEmpty
    @Length(max = 500)
    @Column(name = "INFORMATIVA", length = 500)
    public String getInformativa() {

	return informativa;
    }

    public void setInformativa(String informativa) {

	this.informativa = informativa;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_FINE_VALIDITA", length = 7)
    public Date getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }
}
