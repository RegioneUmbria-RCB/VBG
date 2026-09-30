package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class FaqBean {

    private Integer id;
    private String titolo;
    private String descrizione;
    private Integer ordine;
    private String descrizioneCategoria;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    public String getDescrizioneCategoria() {

	return descrizioneCategoria;
    }

    public void setDescrizioneCategoria(String descrizioneCategoria) {

	this.descrizioneCategoria = descrizioneCategoria;
    }
}
