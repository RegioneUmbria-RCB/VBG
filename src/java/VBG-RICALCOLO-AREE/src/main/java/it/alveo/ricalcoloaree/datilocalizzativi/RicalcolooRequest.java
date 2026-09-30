package it.alveo.ricalcoloaree.datilocalizzativi;

import it.alveo.ricalcoloaree.entities.Istanze;
import it.alveo.ricalcoloaree.entities.IstanzeStradario;

public class RicalcolooRequest {

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

    public static RicalcolooRequest fromIstanzestradario(IstanzeStradario istanzestradario) {

	if (istanzestradario == null) {
	    throw new IllegalArgumentException("Non è possibile calcolare i parametri per il ricalcolo da un record Istanzestradario null");
	}
	RicalcolooRequest request = new RicalcolooRequest();
	//request.idIstanzeStradario = istanzestradario.getId().getCodice();
	request.idIstanzeStradario = Integer.parseInt(istanzestradario.getId());
	request.km = istanzestradario.getKm();
	//request.codiceStradario = istanzestradario.getStradario().getId().getCodice();
	request.codiceStradario = Integer.parseInt(istanzestradario.getStradario().getCodicestradario());
	//request.codiceIstanza = istanzestradario.getIstanza().getId().getCodice();
	request.codiceIstanza = istanzestradario.getIstanza().getPkId().getCodice();
	request.istanza = istanzestradario.getIstanza();
	request.civico = istanzestradario.getCivico();
	return request;
    }
}
