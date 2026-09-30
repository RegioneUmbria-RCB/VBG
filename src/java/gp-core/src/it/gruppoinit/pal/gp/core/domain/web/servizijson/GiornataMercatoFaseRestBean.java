package it.gruppoinit.pal.gp.core.domain.web.servizijson;

public class GiornataMercatoFaseRestBean {

    private Integer id;
    private String descrizione;
    private int ordine;
    private boolean attiva;
    private boolean permetteFiltroCategoria;
    private boolean contaPresenze;

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

    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    public boolean getAttiva() {

	return attiva;
    }

    public void setAttiva(boolean attiva) {

	this.attiva = attiva;
    }

    public boolean getPermetteFiltroCategoria() {

	return permetteFiltroCategoria;
    }

    public void setPermetteFiltroCategoria(boolean permetteFiltroCategoria) {

	this.permetteFiltroCategoria = permetteFiltroCategoria;
    }

    public boolean getContaPresenze() {

	return contaPresenze;
    }

    public void setContaPresenze(boolean contaPresenze) {

	this.contaPresenze = contaPresenze;
    }
}
