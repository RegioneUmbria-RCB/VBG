package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

public class AutorizzazioniPresenzeStoricoRestHelper {

    private Integer id_autorizzazione;
    private String aut_numero;
    private String aut_comune;
    private String aut_originaria;
    private String aut_precedente;
    private Integer numero_presenze_autorizzazione;
    private List<AutorizzazioniMercatiPresenzeStoricoRestHelper> mercati;

    public Integer getId_autorizzazione() {

	return id_autorizzazione;
    }

    public void setId_autorizzazione(Integer id_autorizzazione) {

	this.id_autorizzazione = id_autorizzazione;
    }

    public String getAut_numero() {

	return aut_numero;
    }

    public void setAut_numero(String aut_numero) {

	this.aut_numero = aut_numero;
    }

    public String getAut_comune() {

	return aut_comune;
    }

    public void setAut_comune(String aut_comune) {

	this.aut_comune = aut_comune;
    }

    public String getAut_originaria() {

	return aut_originaria;
    }

    public void setAut_originaria(String aut_originaria) {

	this.aut_originaria = aut_originaria;
    }

    public String getAut_precedente() {

	return aut_precedente;
    }

    public void setAut_precedente(String aut_precedente) {

	this.aut_precedente = aut_precedente;
    }

    public List<AutorizzazioniMercatiPresenzeStoricoRestHelper> getMercati() {

	if (this.mercati == null) {
	    this.mercati = new ArrayList<AutorizzazioniMercatiPresenzeStoricoRestHelper>();
	}
	return mercati;
    }

    public void setMercati(List<AutorizzazioniMercatiPresenzeStoricoRestHelper> mercati) {

	this.mercati = mercati;
    }

    public Integer getNumero_presenze_autorizzazione() {

	return numero_presenze_autorizzazione;
    }

    public void setNumero_presenze_autorizzazione(Integer numero_presenze_autorizzazione) {

	this.numero_presenze_autorizzazione = numero_presenze_autorizzazione;
    }
}
