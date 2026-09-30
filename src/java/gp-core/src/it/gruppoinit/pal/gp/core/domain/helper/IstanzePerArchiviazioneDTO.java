package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class IstanzePerArchiviazioneDTO {

    private PkId id;
    private String numeroIstanza;
    private Integer codiceistanza;
    private Integer codiceArchiviazioneIstanze;
    private String codiceSoftware;

    public IstanzePerArchiviazioneDTO() {

	this.id = new PkId();
    }

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getCodiceSoftware() {

	return codiceSoftware;
    }

    public void setCodiceSoftware(String codiceSoftware) {

	this.codiceSoftware = codiceSoftware;
    }

    public Integer getCodiceArchiviazioneIstanze() {

	return codiceArchiviazioneIstanze;
    }

    public void setCodiceArchiviazioneIstanze(Integer codiceArchiviazioneIstanze) {

	this.codiceArchiviazioneIstanze = codiceArchiviazioneIstanze;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((codiceArchiviazioneIstanze == null) ? 0 : codiceArchiviazioneIstanze.hashCode());
	result = prime * result + ((codiceSoftware == null) ? 0 : codiceSoftware.hashCode());
	result = prime * result + ((codiceistanza == null) ? 0 : codiceistanza.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((numeroIstanza == null) ? 0 : numeroIstanza.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	IstanzePerArchiviazioneDTO other = (IstanzePerArchiviazioneDTO) obj;
	if (codiceArchiviazioneIstanze == null) {
	    if (other.codiceArchiviazioneIstanze != null)
		return false;
	} else if (!codiceArchiviazioneIstanze.equals(other.codiceArchiviazioneIstanze))
	    return false;
	if (codiceSoftware == null) {
	    if (other.codiceSoftware != null)
		return false;
	} else if (!codiceSoftware.equals(other.codiceSoftware))
	    return false;
	if (codiceistanza == null) {
	    if (other.codiceistanza != null)
		return false;
	} else if (!codiceistanza.equals(other.codiceistanza))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (numeroIstanza == null) {
	    if (other.numeroIstanza != null)
		return false;
	} else if (!numeroIstanza.equals(other.numeroIstanza))
	    return false;
	return true;
    }
}
