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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "ANAGRAFE_INDIRIZZI")
public class AnagrafeIndirizzi implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2676091411467906639L;
    private PkId id;
    private Anagrafe anagrafe;
    private String identificativoSede;
    private String partitaiva;
    private String codiceFiscale;
    private String citta;
    private String indirizzo;

    public AnagrafeIndirizzi() {

	this.id = new PkId();
	this.anagrafe = new Anagrafe();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ANAGRAFE_INDIRIZZI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "CODICEANAGRAFE", referencedColumnName = "CODICEANAGRAFE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return this.anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    private Integer anagrafeId;

    @Column(name = "CODICEANAGRAFE")
    private Integer getAnagrafeId() {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
		return this.anagrafeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAnagrafeId(Integer anagrafeId) {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
	    }
	}
    }

    @Length(max = 20)
    @Column(name = "IDENTIFICATIVO_SEDE", length = 20)
    public String getIdentificativoSede() {

	return identificativoSede;
    }

    public void setIdentificativoSede(String identificativoSede) {

	this.identificativoSede = identificativoSede;
    }

    @Length(max = 11)
    @Column(name = "PARTITAIVA", length = 11)
    public String getPartitaiva() {

	return this.partitaiva;
    }

    public void setPartitaiva(String partitaiva) {

	this.partitaiva = partitaiva;
    }

    @Length(max = 16)
    @Column(name = "CODICEFISCALE", length = 16)
    public String getCodiceFiscale() {

	return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {

	this.codiceFiscale = codiceFiscale;
    }

    @Length(max = 50)
    @Column(name = "CITTA", length = 50)
    public String getCitta() {

	return this.citta;
    }

    public void setCitta(String citta) {

	this.citta = citta;
    }

    @Length(max = 100)
    @Column(name = "INDIRIZZO", length = 100)
    public String getIndirizzo() {

	return this.indirizzo;
    }

    public void setIndirizzo(String indirizzo) {

	this.indirizzo = indirizzo;
    }
}
