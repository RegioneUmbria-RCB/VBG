package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class AutorizzazioniMercatiPresenzeStoricoRestHelper {

    private String mercato;
    private String giorno;
    private Integer numero_presenze_mercato;
    private List<PresenzeStoricoGiornataRestHelper> presenze;

    public String getMercato() {

	return mercato;
    }

    public void setMercato(String mercato) {

	this.mercato = mercato;
    }

    public String getGiorno() {

	return giorno;
    }

    public void setGiorno(String giorno) {

	this.giorno = giorno;
    }

    public List<PresenzeStoricoGiornataRestHelper> getPresenze() {

	if (this.presenze == null) {
	    this.presenze = new ArrayList<PresenzeStoricoGiornataRestHelper>();
	}
	return presenze;
    }

    public void setPresenze(List<PresenzeStoricoGiornataRestHelper> presenze) {

	this.presenze = presenze;
    }

    public Integer getNumero_presenze_mercato() {

	return numero_presenze_mercato;
    }

    public void setNumero_presenze_mercato(Integer numero_presenze_mercato) {

	this.numero_presenze_mercato = numero_presenze_mercato;
    }
}
