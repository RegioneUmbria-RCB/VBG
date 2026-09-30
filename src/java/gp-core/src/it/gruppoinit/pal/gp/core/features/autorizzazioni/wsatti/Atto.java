package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaResponse;

public class Atto {

    private Integer id;
    private String numero;
    private Date data;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumero() {

	return numero;
    }

    public void setNumero(String numero) {

	this.numero = numero;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public static Atto fromNumeraDeterminaResponse(NumeraDeterminaResponse response, Date dataAtto) {

	Atto atto = new Atto();
	atto.setId(response.getIdDocumento());
	atto.setNumero(response.getNumero().toString() + '/' + response.getAnno().toString());
	atto.setData(dataAtto);
	return atto;
    }
}
