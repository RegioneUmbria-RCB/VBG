package it.gruppoinit.pal.gp.core.service.helper;

public class PagamentiMercatoPosizDebRestHelperV2 extends PagamentiMercatoPosizDebRestHelper {

    private String ruolo;
    private String descrizioneMercato;

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public String getDescrizioneMercato() {

	return descrizioneMercato;
    }

    public void setDescrizioneMercato(String descrizioneMercato) {

	this.descrizioneMercato = descrizioneMercato;
    }

    public static PagamentiMercatoPosizDebRestHelperV2 fromPagamentiMercatoPosizDebRestHelper(PagamentiMercatoPosizDebRestHelper p) {

	PagamentiMercatoPosizDebRestHelperV2 ret = new PagamentiMercatoPosizDebRestHelperV2();
	ret.setAutorizzazione(p.getAutorizzazione());
	ret.setCodice_iuv(p.getCodice_iuv());
	ret.setData_presenza(p.getData_presenza());
	ret.setData_registrazione(p.getData_registrazione());
	ret.setEffettuato(p.getEffettuato());
	ret.setId_pagamento(p.getId_pagamento());
	ret.setId_presenza(p.getId_presenza());
	ret.setIdBollettino(p.getIdBollettino());
	ret.setIdRicevuta(p.getIdRicevuta());
	ret.setImporto(p.getImporto());
	ret.setPosteggio(p.getPosteggio());
	ret.setStato_pagamento(p.getStato_pagamento());
	ret.setSuperficie(p.getSuperficie());
	return ret;
    }
}
