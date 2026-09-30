package it.gruppoinit.pal.gp.core.service.helper;

import java.util.ArrayList;
import java.util.List;

public class GiorniMercatoPresenzeRestHelper {

    private String descrizioneGiorno;
    private Integer codiceGiorno;
    private List<AutorizzazioniConcessioniPresenzeRestHelper> presenze = new ArrayList<AutorizzazioniConcessioniPresenzeRestHelper>();

    public String getDescrizioneGiorno() {

	return descrizioneGiorno;
    }

    public void setDescrizioneGiorno(String descrizioneGiorno) {

	this.descrizioneGiorno = descrizioneGiorno;
    }

    public Integer getCodiceGiorno() {

	return codiceGiorno;
    }

    public void setCodiceGiorno(Integer codiceGiorno) {

	this.codiceGiorno = codiceGiorno;
    }

    public List<AutorizzazioniConcessioniPresenzeRestHelper> getPresenze() {

	return presenze;
    }

    public void setPresenze(List<AutorizzazioniConcessioniPresenzeRestHelper> presenze) {

	this.presenze = presenze;
    }
}
