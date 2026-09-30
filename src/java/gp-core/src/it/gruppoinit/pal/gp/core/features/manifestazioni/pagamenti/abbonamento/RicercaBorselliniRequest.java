package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

public class RicercaBorselliniRequest {

    private String anagrafe;
    private Integer idAutorizzazione;
    private String stato;

    public String getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(String anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public static RicercaBorselliniRequest fromParametri(String anagrafe, String stato, Integer idAutorizzazione) {

	RicercaBorselliniRequest ret = new RicercaBorselliniRequest();
	ret.setIdAutorizzazione(idAutorizzazione);
	ret.setStato(stato);
	ret.setAnagrafe(anagrafe);
	return ret;
    }
}
