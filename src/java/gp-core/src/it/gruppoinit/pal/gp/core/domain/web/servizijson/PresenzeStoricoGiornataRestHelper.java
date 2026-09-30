package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.math.BigDecimal;
import java.util.Date;

public class PresenzeStoricoGiornataRestHelper {

    private Date data;
    private String posteggio;
    private BigDecimal superficie;
    private Integer numero_presenze;
    private Integer id_presenza;

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(String posteggio) {

	this.posteggio = posteggio;
    }

    public BigDecimal getSuperficie() {

	return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {

	this.superficie = superficie;
    }

    public Integer getNumero_presenze() {

	return numero_presenze;
    }

    public void setNumero_presenze(Integer numero_presenze) {

	this.numero_presenze = numero_presenze;
    }

    public Integer getId_presenza() {

	return id_presenza;
    }

    public void setId_presenza(Integer id_presenza) {

	this.id_presenza = id_presenza;
    }
}
