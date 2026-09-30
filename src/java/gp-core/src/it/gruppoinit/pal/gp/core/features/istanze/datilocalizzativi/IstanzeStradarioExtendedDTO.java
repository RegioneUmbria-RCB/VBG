package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class IstanzeStradarioExtendedDTO {

    private String idComune;
    private String software;
    private String codiceComune;
    private String comune;
    private String codiceIstat;
    private Integer codiceIstanza;
    private String numeroIstanza;
    private String uuidIstanzeStradario;
    private String codiceViario;
    private String civico;
    private String prefisso;
    private String descrizione;
    private String km;
    private Integer primario;
    private String latitudine;
    private String longitudine;

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getCodiceComune() {

	return codiceComune;
    }

    public void setCodiceComune(String codiceComune) {

	this.codiceComune = codiceComune;
    }

    public String getComune() {

	return comune;
    }

    public void setComune(String comune) {

	this.comune = comune;
    }

    public String getCodiceIstat() {

	return codiceIstat;
    }

    public void setCodiceIstat(String codiceIstat) {

	this.codiceIstat = codiceIstat;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getNumeroIstanza() {

	return numeroIstanza;
    }

    public void setNumeroIstanza(String numeroIstanza) {

	this.numeroIstanza = numeroIstanza;
    }

    public String getUuidIstanzeStradario() {

	return uuidIstanzeStradario;
    }

    public void setUuidIstanzeStradario(String uuidIstanzeStradario) {

	this.uuidIstanzeStradario = uuidIstanzeStradario;
    }

    public String getCodiceViario() {

	return codiceViario;
    }

    public void setCodiceViario(String codiceViario) {

	this.codiceViario = codiceViario;
    }

    public String getPrefisso() {

	return prefisso;
    }

    public void setPrefisso(String prefisso) {

	this.prefisso = prefisso;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getKm() {

	return km;
    }

    public void setKm(String km) {

	this.km = km;
    }

    public Integer getPrimario() {

	return primario;
    }

    public void setPrimario(Integer primario) {

	this.primario = primario;
    }

    public String getLatitudine() {

	return latitudine;
    }

    public void setLatitudine(String latitudine) {

	this.latitudine = latitudine;
    }

    public String getLongitudine() {

	return longitudine;
    }

    public void setLongitudine(String longitudine) {

	this.longitudine = longitudine;
    }

    public static IstanzeStradarioExtendedDTO fromIstanzestradario(Istanzestradario localizzazione) {

	if (localizzazione == null) {
	    return null;
	}
	IstanzeStradarioExtendedDTO response = new IstanzeStradarioExtendedDTO();
	response.setCivico(localizzazione.getCivico());
	response.setCodiceComune(localizzazione.getIstanza().getComune().getCodicecomune());
	response.setCodiceIstanza(localizzazione.getIstanza().getId().getCodice());
	response.setCodiceIstat(localizzazione.getIstanza().getComune().getCodiceistat());
	response.setCodiceViario(localizzazione.getStradario().getCodviario());
	response.setComune(localizzazione.getIstanza().getComune().getComune());
	response.setDescrizione(localizzazione.getStradario().getDescrizione());
	response.setIdComune(localizzazione.getId().getIdcomune());
	response.setKm(localizzazione.getKm());
	response.setLatitudine(localizzazione.getLatitudine());
	response.setLongitudine(localizzazione.getLongitudine());
	response.setNumeroIstanza(localizzazione.getIstanza().getNumeroistanza());
	response.setPrefisso(localizzazione.getStradario().getPrefisso());
	response.setPrimario(Boolean.TRUE.equals(localizzazione.getPrimario()) ? 1 : 0);
	response.setSoftware(localizzazione.getIstanza().getSoftware().getCodice());
	response.setUuidIstanzeStradario(localizzazione.getUuid());
	return response;
    }
}
