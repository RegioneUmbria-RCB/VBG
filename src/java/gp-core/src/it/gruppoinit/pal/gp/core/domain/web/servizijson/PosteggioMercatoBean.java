package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class PosteggioMercatoBean {

    private Integer id;
    private String numero;
    private String coordinate;
    private Boolean occupato;

    public PosteggioMercatoBean() {

    }

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public String getCoordinate() {

	return coordinate;
    }

    public void setCoordinate(String coordinate) {

	this.coordinate = coordinate;
    }

    public Boolean getOccupato() {

	return occupato;
    }

    public void setOccupato(Boolean occupato) {

	this.occupato = occupato;
    }
}
