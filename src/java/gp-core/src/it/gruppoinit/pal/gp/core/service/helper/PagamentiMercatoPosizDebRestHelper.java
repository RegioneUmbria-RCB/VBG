package it.gruppoinit.pal.gp.core.service.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;

public class PagamentiMercatoPosizDebRestHelper {

    private Integer id_presenza;
    private Integer id_pagamento;
    private Date data_registrazione;
    private Date data_presenza;
    private String posteggio;
    private Double superficie;
    private Double importo;
    private String stato_pagamento;
    private boolean effettuato;
    private String codice_iuv;
    private AutorizzazioniRestHelper autorizzazione;
    private String idBollettino;
    private String idRicevuta;

    public Integer getId_presenza() {

	return id_presenza;
    }

    public void setId_presenza(Integer id_presenza) {

	this.id_presenza = id_presenza;
    }

    public Integer getId_pagamento() {

	return id_pagamento;
    }

    public void setId_pagamento(Integer id_pagamento) {

	this.id_pagamento = id_pagamento;
    }

    public Date getData_registrazione() {

	return data_registrazione;
    }

    public void setData_registrazione(Date data_registrazione) {

	this.data_registrazione = data_registrazione;
    }

    public String getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(String posteggio) {

	this.posteggio = posteggio;
    }

    public Double getSuperficie() {

	return superficie;
    }

    public void setSuperficie(Double superficie) {

	this.superficie = superficie;
    }

    public Double getImporto() {

	return importo;
    }

    public void setImporto(Double importo) {

	this.importo = importo;
    }

    public String getStato_pagamento() {

	return stato_pagamento;
    }

    public void setStato_pagamento(String stato_pagamento) {

	this.stato_pagamento = stato_pagamento;
    }

    public boolean getEffettuato() {

	return effettuato;
    }

    public void setEffettuato(boolean effettuato) {

	this.effettuato = effettuato;
    }

    public String getCodice_iuv() {

	return codice_iuv;
    }

    public void setCodice_iuv(String codice_iuv) {

	this.codice_iuv = codice_iuv;
    }

    public AutorizzazioniRestHelper getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(AutorizzazioniRestHelper autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public Date getData_presenza() {

	return data_presenza;
    }

    public void setData_presenza(Date data_presenza) {

	this.data_presenza = data_presenza;
    }

    public String getIdBollettino() {

	return idBollettino;
    }

    public void setIdBollettino(String idBollettino) {

	this.idBollettino = idBollettino;
    }

    public String getIdRicevuta() {

	return idRicevuta;
    }

    public void setIdRicevuta(String idRicevuta) {

	this.idRicevuta = idRicevuta;
    }
}
