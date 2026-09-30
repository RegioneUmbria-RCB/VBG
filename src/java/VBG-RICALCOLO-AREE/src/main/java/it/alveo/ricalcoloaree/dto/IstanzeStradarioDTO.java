package it.alveo.ricalcoloaree.dto;

public class IstanzeStradarioDTO {

    private String id;
    private String idcomune;
    private Integer codiceistanza;
    private String civico;
    private String km;
    private String uuid;
    private String codicestradario;

    public IstanzeStradarioDTO(String id, String idcomune, Integer codiceistanza, String civico, String km, String uuid, String codicestradario) {

	super();
	this.id = id;
	this.idcomune = idcomune;
	this.codiceistanza = codiceistanza;
	this.civico = civico;
	this.km = km;
	this.uuid = uuid;
	this.codicestradario = codicestradario;
    }

    public String getId() {

	return id;
    }

    public void setId(String id) {

	this.id = id;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodiceistanza() {

	return codiceistanza;
    }

    public void setCodiceistanza(Integer codiceistanza) {

	this.codiceistanza = codiceistanza;
    }

    public String getCivico() {

	return civico;
    }

    public void setCivico(String civico) {

	this.civico = civico;
    }

    public String getKm() {

	return km;
    }

    public void setKm(String km) {

	this.km = km;
    }

    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    public String getCodicestradario() {

	return codicestradario;
    }

    public void setCodicestradario(String codicestradario) {

	this.codicestradario = codicestradario;
    }
}
