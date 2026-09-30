package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;

public class RicalcoloRequest {

    private Integer idIstanzeStradario;
    private Integer codiceIstanza;
    private Integer codiceStradario;
    private String km;
    private String civico;
    private Istanze istanza;

    public Integer getIdIstanzeStradario() {

	return idIstanzeStradario;
    }

    public String getKm() {

	return km;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public Integer getCodiceStradario() {

	return codiceStradario;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public String getCivico() {

	return civico;
    }

    public static RicalcoloRequest fromIstanzestradario(Istanzestradario istanzestradario) {

	if (istanzestradario == null) {
	    throw new IllegalArgumentException("Non è possibile calcolare i parametri per il ricalcolo da un record Istanzestradario null");
	}
	RicalcoloRequest request = new RicalcoloRequest();
	request.idIstanzeStradario = istanzestradario.getId().getCodice();
	request.km = istanzestradario.getKm();
	request.codiceStradario = istanzestradario.getStradario().getId().getCodice();
	request.codiceIstanza = istanzestradario.getIstanza().getId().getCodice();
	request.istanza = istanzestradario.getIstanza();
	request.civico = istanzestradario.getCivico();
	return request;
    }
}
