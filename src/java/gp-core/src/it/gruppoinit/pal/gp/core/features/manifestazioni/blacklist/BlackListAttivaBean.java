package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

public class BlackListAttivaBean {

    private Integer autorizzazioniId;
    private Integer mercatiUsoId;
    private String contesto;
    private Integer codicemercato;

    public BlackListAttivaBean() {

    }

    public Integer getAutorizzazioniId() {

	return autorizzazioniId;
    }

    public void setAutorizzazioniId(Integer autorizzazioniId) {

	this.autorizzazioniId = autorizzazioniId;
    }

    public Integer getMercatiUsoId() {

	return mercatiUsoId;
    }

    public void setMercatiUsoId(Integer mercatiUsoId) {

	this.mercatiUsoId = mercatiUsoId;
    }

    public String getContesto() {
    
        return contesto;
    }

    public void setContesto(String contesto) {
    
        this.contesto = contesto;
    }

    public Integer getCodicemercato() {
    
        return codicemercato;
    }

    
    public void setCodicemercato(Integer codicemercato) {
    
        this.codicemercato = codicemercato;
    }
    
}
