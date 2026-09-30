package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author riccardob
 *
 */
public class MercatiPresenzeStoricoRestHelper {

    private String descrizioneMercato;
    private Integer codiceMercato;
    private List<GiorniMercatoPresenzeRestHelper> giorniMercato = new ArrayList<GiorniMercatoPresenzeRestHelper>();

    public String getDescrizioneMercato() {

	return descrizioneMercato;
    }

    public void setDescrizioneMercato(String descrizioneMercato) {

	this.descrizioneMercato = descrizioneMercato;
    }

    public Integer getCodiceMercato() {

	return codiceMercato;
    }

    public void setCodiceMercato(Integer codiceMercato) {

	this.codiceMercato = codiceMercato;
    }

    public List<GiorniMercatoPresenzeRestHelper> getGiorniMercato() {

	return giorniMercato;
    }

    public void setGiorniMercato(List<GiorniMercatoPresenzeRestHelper> giorniMercato) {

	this.giorniMercato = giorniMercato;
    }
}
