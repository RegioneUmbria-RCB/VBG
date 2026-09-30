package it.gruppoinit.stc.domain;

// §§§BEGIN§§§
import it.gruppoinit.upgr.domain.IVersione;
// §§§END§§§
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "VERSIONE")
public class Versione implements Serializable /* §§§BEGIN§§§ */, IVersione /* §§§END§§§ */{

    private static final long serialVersionUID = -1666804696524185336L;
    private static final Logger log = LoggerFactory.getLogger(Versione.class);
    private String versione;
    private transient int majorRelease;
    private transient int minorRelease;
    private transient String bugFixRelease = "";

    public Versione() {

    }

    public Versione(String versione) {

	this.versione = versione;
    }

    @Id
    @Column(name = "VERSIONE", unique = true, nullable = false, length = 15)
    public String getVersione() {

	return this.versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
	this.majorRelease = 0;
	this.minorRelease = 0;
	this.bugFixRelease = "";
	if (this.versione != null) {
	    String[] versionParts = versione.split("\\.");
	    if (versionParts != null && versionParts.length > 0) {
		try {
		    NumberFormat nf = NumberFormat.getIntegerInstance();
		    Number n = nf.parse(versionParts[0]);
		    this.majorRelease = n.intValue();
		    if (versionParts.length > 1) {
			n = nf.parse(versionParts[1]);
			this.minorRelease = n.intValue();
			if (versionParts.length > 2) {
			    this.bugFixRelease = versionParts[2];
			}
		    }
		} catch (ParseException e) {
		    log.warn("setVersione() - errore nel parsing della versione di STC: " + this.versione);
		}
	    } else {
		this.bugFixRelease = versione;
	    }
	}
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((versione == null) ? 0 : versione.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Versione other = (Versione) obj;
	if (versione == null) {
	    if (other.versione != null) {
		return false;
	    }
	} else if (!versione.equals(other.versione)) {
	    return false;
	}
	return true;
    }

    @Transient
    public int getMajorRelease() {

	return majorRelease;
    }

    @Transient
    public int getMinorRelease() {

	return minorRelease;
    }

    /**
     * @return the bugFixRelease
     */
    @Transient
    public String getBugFixRelease() {

	return bugFixRelease;
    }
}
