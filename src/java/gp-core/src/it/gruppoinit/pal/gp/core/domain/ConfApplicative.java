package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.Length;

@Entity
@Table(name = "CONF_APPLICATIVE")
public class ConfApplicative implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 247425712947832099L;
    private ConfApplicativeId id;
    private String valore;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, length = 100)) })
    public ConfApplicativeId getId() {

	return id;
    }

    public void setId(ConfApplicativeId id) {

	this.id = id;
    }

    @Length(max = 200)
    @Column(name = "VALORE", length = 200)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
