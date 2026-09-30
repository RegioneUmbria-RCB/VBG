package it.gruppoinit.pal.gp.core.features.oneri;

public class DecodificaCausaleOnereRequest {

    private String idCausale;
    private String descrizioneCausale;
    private String codiceSoftware;
    private boolean nodoInterno;
    private boolean nodoSieder;
    private boolean nodoPeople;
    private Integer idCausaleDefault;

    public DecodificaCausaleOnereRequest(String idCausale, String descrizioneCausale, String codiceSoftware) {

	super();
	this.idCausale = idCausale;
	this.descrizioneCausale = descrizioneCausale;
	this.codiceSoftware = codiceSoftware;
	this.nodoInterno = true;
	this.nodoPeople = false;
	this.nodoInterno = false;
	this.idCausaleDefault = null;
    }

    public DecodificaCausaleOnereRequest(String idCausale, String descrizioneCausale, String codiceSoftware, boolean nodoInterno, boolean nodoSieder,
	    boolean nodoPeople, Integer idCausaleDefault) {

	super();
	this.idCausale = idCausale;
	this.descrizioneCausale = descrizioneCausale;
	this.codiceSoftware = codiceSoftware;
	this.nodoSieder = nodoSieder;
	this.nodoPeople = nodoPeople;
	this.nodoInterno = nodoInterno;
	this.idCausaleDefault = idCausaleDefault;
    }

    public String getIdCausale() {

	return this.idCausale;
    }

    public String getDescrizioneCausale() {

	return this.descrizioneCausale;
    }

    public String getCodiceSoftware() {

	return codiceSoftware;
    }

    public boolean isNodoSieder() {

	return this.nodoSieder;
    }

    public boolean isNodoPeople() {

	return this.nodoPeople;
    }

    public boolean isNodoInterno() {

	return this.nodoInterno;
    }

    public Integer getIdCausaleDefault() {

	return idCausaleDefault;
    }
}
