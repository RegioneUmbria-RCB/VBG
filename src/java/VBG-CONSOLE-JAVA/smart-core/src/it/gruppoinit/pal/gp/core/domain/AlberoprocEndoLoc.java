package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "ALBEROPROC_ENDO_LOC")
public class AlberoprocEndoLoc implements java.io.Serializable {

    private static final long serialVersionUID = 8180480021596186088L;
    private PkId id;
    private Alberoproc alberoproc;
    private Inventarioprocedimenti inventarioprocedimenti;
    private String descrizione;
    private Boolean flagPubblica;
    private Boolean flagNecessario;
    private Boolean flagIntervento;
    private Comuni comune;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ALBEROPROC_ENDO_LOC.ID") })
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
    @JoinColumns({ @JoinColumn(name = "FK_AP_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = true, updatable = true),
	    @JoinColumn(name = "FK_AP_IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = true, updatable = true) })
    public Alberoproc getAlberoproc() {

	return alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "FK_CI_CODICE", referencedColumnName = "CODICEINVENTARIO", nullable = false, insertable = true, updatable = true),
	    @JoinColumn(name = "FK_CI_IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = true, updatable = true) })
    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    @Length(max = 300)
    @Column(name = "DESCRIZIONE", nullable = true, length = 300)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "FLAG_PUBBLICA", precision = 1, scale = 0)
    public Boolean getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Boolean flagPubblica) {

	this.flagPubblica = flagPubblica;
    }

    @Column(name = "FLAG_NECESSARIO", precision = 1, scale = 0)
    public Boolean getFlagNecessario() {

	return flagNecessario;
    }

    public void setFlagNecessario(Boolean flagNecessario) {

	this.flagNecessario = flagNecessario;
    }

    @Column(name = "FLAG_INTERVENTO", precision = 1, scale = 0)
    public Boolean getFlagIntervento() {

	return flagIntervento;
    }

    public void setFlagIntervento(Boolean flagIntervento) {

	this.flagIntervento = flagIntervento;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE")
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }
}
