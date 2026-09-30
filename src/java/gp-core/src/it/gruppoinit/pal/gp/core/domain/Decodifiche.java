package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "DECODIFICHE")
public class Decodifiche implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5941162422188311792L;
    private PkId id;
    private String valore;
    private String raggruppamento;
    private Integer ordine;
    private String tabella;
    private String chiave;
    private boolean flgDisabilitato;

    public Decodifiche() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "DECODIFICHE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @Column(name = "VALORE", length = 2000, nullable = false)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @Column(name = "RAGGRUPPAMENTO", length = 200)
    public String getRaggruppamento() {

	return raggruppamento;
    }

    public void setRaggruppamento(String raggruppamento) {

	this.raggruppamento = raggruppamento;
    }

    @Column(name = "ORDINE", length = 4)
    public Integer getOrdine() {

	return this.ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @NotNull
    @Column(name = "TABELLA", length = 64, nullable = false)
    public String getTabella() {

	return this.tabella;
    }

    public void setTabella(String tabella) {

	this.tabella = tabella;
    }

    @NotNull
    @Column(name = "CHIAVE", length = 20, nullable = false)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @NotNull
    @Column(name = "FLG_DISABILITATO", nullable = false, precision = 1, scale = 0)
    public boolean isFlgDisabilitato() {

	return this.flgDisabilitato;
    }

    public void setFlgDisabilitato(boolean flgDisabilitato) {

	this.flgDisabilitato = flgDisabilitato;
    }
}
