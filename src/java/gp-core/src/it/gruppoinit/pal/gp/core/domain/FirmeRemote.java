package it.gruppoinit.pal.gp.core.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "FIRMEREMOTE")
public class FirmeRemote implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6601474160767774618L;
    private PkId id;
    private String provider;
    private String descrizione;
    private String endpoint;
    private Boolean flagAttiva;
    private Set<FirmeRemoteParametri> parametri = new HashSet<FirmeRemoteParametri>(0);

    public FirmeRemote() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "FIRMEREMOTE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 20)
    @Column(name = "PROVIDER", length = 20)
    public String getProvider() {

	return provider;
    }

    public void setProvider(String provider) {

	this.provider = provider;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "DESCRIZIONE", length = 50)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @NotEmpty
    @Length(max = 100)
    @Column(name = "ENDPOINT", length = 100)
    public String getEndpoint() {

	return endpoint;
    }

    public void setEndpoint(String endpoint) {

	this.endpoint = endpoint;
    }

    @NotNull
    @Column(name = "FLAG_ATTIVA", precision = 1, scale = 0)
    public Boolean getFlagAttiva() {

	return flagAttiva;
    }

    public void setFlagAttiva(Boolean flagAttiva) {

	this.flagAttiva = flagAttiva;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "firmaRemota")
    public Set<FirmeRemoteParametri> getParametri() {

	return parametri;
    }

    public void setParametri(Set<FirmeRemoteParametri> parametri) {

	this.parametri = parametri;
    }
}
