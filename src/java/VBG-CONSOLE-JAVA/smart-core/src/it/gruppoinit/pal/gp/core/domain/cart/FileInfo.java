package it.gruppoinit.pal.gp.core.domain.cart;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FileInfo", propOrder = { "nomeFile", "idOggetto", "idSemantico", "dimensione" })
public class FileInfo {

    @XmlAttribute(name = "nomeFile", required = true)
    private String nomeFile;
    @XmlAttribute(name = "idOggetto", required = true)
    private Integer idOggetto;
    @XmlAttribute(name = "idSemantico", required = false)
    private String idSemantico;
    @XmlAttribute(name = "dimensione", required = false)
    private long dimensione;
    @XmlAttribute(name = "firmaValidata", required = false)
    private boolean firmaValidata;

    public FileInfo() {

    }

    /**
     * @return the nomeFile
     */
    public String getNomeFile() {

	return Utilities.correggiNomeFile(nomeFile);
    }

    /**
     * @param nomeFile
     *            the nomeFile to set
     */
    public void setNomeFile(String nomeFile) {

	this.nomeFile = Utilities.correggiNomeFile(nomeFile);
    }

    /**
     * @return the idOggetto
     */
    public Integer getIdOggetto() {

	return idOggetto;
    }

    /**
     * @param idOggetto
     *            the idOggetto to set
     */
    public void setIdOggetto(Integer idOggetto) {

	this.idOggetto = idOggetto;
    }

    public String getIdSemantico() {

	return idSemantico;
    }

    public void setIdSemantico(String idSemantico) {

	this.idSemantico = idSemantico;
    }

    public long getDimensione() {

	return dimensione;
    }

    public void setDimensione(long numbytes) {

	this.dimensione = numbytes;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	sb.append("{").append("idOggetto: ").append(idOggetto);
	sb.append(", nomeFile:").append(nomeFile).append("}");
	return sb.toString();
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((idOggetto == null) ? 0 : idOggetto.hashCode());
	result = prime * result + ((nomeFile == null) ? 0 : nomeFile.hashCode());
	return result;
    }

    /**
     * Due oggetti FileInfo sono considerati uguali quando hanno lo stesso codice oggetto
     */
    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FileInfo other = (FileInfo) obj;
	if (idOggetto == null) {
	    if (other.idOggetto != null)
		return false;
	} else if (!idOggetto.equals(other.idOggetto))
	    return false;
	return true;
    }

    public boolean isFirmaValidata() {

	return firmaValidata;
    }

    public void setFirmaValidata(boolean firmaValidata) {

	this.firmaValidata = firmaValidata;
    }
}
