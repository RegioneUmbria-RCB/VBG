package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione;

import java.util.Date;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.Atto;

public class EstremiAutorizzazione {

    private String idRiferimento;
    private String numero;
    private Date data;

    public String getIdRiferimento() {

	return idRiferimento;
    }

    public String getNumero() {

	return numero;
    }

    public Date getData() {

	return data;
    }

    public EstremiAutorizzazione(String idRiferimento, String numero, Date data) {

	this.idRiferimento = idRiferimento;
	this.numero = numero;
	this.data = data;
    }

    public static EstremiAutorizzazione fromAtto(Atto atto) {

	return new EstremiAutorizzazione(atto.getId() != null ? atto.getId().toString() : null, atto.getNumero(), atto.getData());
    }
}
