package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class FasciaMercatoBean {

    private Integer id;
    private String descrizione;
    private boolean attiva;

    public FasciaMercatoBean(Integer id, String descrizione, String descrizioneEstesa, boolean attiva) {

	this.id = id;
	this.descrizione = descrizione;
	this.attiva = attiva;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    // per serializzazione servizi json va lasciato il 
    // metodo get al posto di is
    public boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(boolean attiva) {

	this.attiva = attiva;
    }
}
