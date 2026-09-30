package it.gruppoinit.pal.gp.core.domain;

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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "CONFIGURAZIONE_CALCOLI")
public class ConfigurazioneCalcoli implements java.io.Serializable {

    private static final long serialVersionUID = 5415094206011736729L;
    private PkId id;
    private String descrizione;
    private Oggetti jsonMatrice;
    private Oggetti jsonConfigurazione;
    private Integer versione;

    public ConfigurazioneCalcoli() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "CONFIGURAZIONE_CALCOLI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 8, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 200)
    @Column(name = "DESCRIZIONE", nullable = false, length = 200)
    public String getDescrizione() {

	return this.descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "JSON_MATRICE", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getJsonMatrice() {

	return jsonMatrice;
    }

    public void setJsonMatrice(Oggetti jsonMatrice) {

	this.jsonMatrice = jsonMatrice;
    }

    // FIX WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer jsonMatriceId;

    @Column(name = "JSON_MATRICE")
    public Integer getJsonMatriceId() {

	if (this.getJsonMatrice() != null && this.getJsonMatrice().getId() != null) {
	    this.jsonMatriceId = this.getJsonMatrice().getId().getCodice();
	    return this.jsonMatriceId;
	}
	return null;
    }

    public void setJsonMatriceId(Integer jsonMatriceId) {

	if (this.getJsonMatrice() != null && this.getJsonMatrice().getId() != null) {
	    this.jsonMatriceId = this.getJsonMatrice().getId().getCodice();
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "JSON_CONFIGURAZIONE", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getJsonConfigurazione() {

	return jsonConfigurazione;
    }

    public void setJsonConfigurazione(Oggetti jsonConfigurazione) {

	this.jsonConfigurazione = jsonConfigurazione;
    }

    // FIX WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer jsonConfigurazioneId;

    @Column(name = "JSON_CONFIGURAZIONE")
    public Integer getJsonConfigurazioneId() {

	if (this.getJsonConfigurazione() != null && this.getJsonConfigurazione().getId() != null) {
	    this.jsonConfigurazioneId = this.getJsonConfigurazione().getId().getCodice();
	    return this.jsonConfigurazioneId;
	}
	return null;
    }

    public void setJsonConfigurazioneId(Integer jsonConfigurazioneId) {

	if (this.getJsonConfigurazione() != null && this.getJsonConfigurazione().getId() != null) {
	    this.jsonConfigurazioneId = this.getJsonConfigurazione().getId().getCodice();
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotNull
    @Column(name = "VERSIONE", nullable = false)
    public Integer getVersione() {

	return versione;
    }

    public void setVersione(Integer versione) {

	this.versione = versione;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("id", this.getId());
	toStringBuilder.append("descrizione", this.getDescrizione());
	toStringBuilder.append("json_matrice", this.getJsonMatriceId());
	toStringBuilder.append("json_configurazione", this.getJsonConfigurazioneId());
	toStringBuilder.append("versione", this.getVersione());
	return toStringBuilder.toString();
    }
}
