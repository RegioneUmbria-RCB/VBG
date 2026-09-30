package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.Length;

@Entity
@Table(name = "OGGETTI_METADATI")
public class OggettiMetadati implements Serializable {

    private static final long serialVersionUID = -8141653614486361037L;
    private OggettiMetadatiId id;
    private String valore;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codiceoggetto", column = @Column(name = "CODICEOGGETTO", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, length = 100)) })
    public OggettiMetadatiId getId() {

	return id;
    }

    public void setId(OggettiMetadatiId id) {

	this.id = id;
    }

    @Length(max = 4000)
    @Column(name = "VALORE", length = 4000)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
