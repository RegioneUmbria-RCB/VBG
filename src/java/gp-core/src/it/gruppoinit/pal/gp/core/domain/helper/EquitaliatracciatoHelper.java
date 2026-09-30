package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.ArrayList;
import java.util.List;

public class EquitaliatracciatoHelper {

    private String rigoTracciato;
    private List<ChiaveValoreBean<String, String>> LISTA_CAMPI_CSV_EXCEL = new ArrayList<ChiaveValoreBean<String, String>>();

    public String getRigoTracciato() {

	return rigoTracciato;
    }

    public void setRigoTracciato(String rigoTracciato) {

	this.rigoTracciato = rigoTracciato;
    }

    public List<ChiaveValoreBean<String, String>> getLISTA_CAMPI_CSV_EXCEL() {

	return LISTA_CAMPI_CSV_EXCEL;
    }

    public void setLISTA_CAMPI_CSV_EXCEL(List<ChiaveValoreBean<String, String>> lISTA_CAMPI_CSV_EXCEL) {

	LISTA_CAMPI_CSV_EXCEL = lISTA_CAMPI_CSV_EXCEL;
    }
}
