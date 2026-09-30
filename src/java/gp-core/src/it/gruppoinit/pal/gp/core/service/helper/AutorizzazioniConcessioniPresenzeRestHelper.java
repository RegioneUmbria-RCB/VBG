package it.gruppoinit.pal.gp.core.service.helper;

public class AutorizzazioniConcessioniPresenzeRestHelper {

    private AutorizzazioniConcessioniRestHelper autorizzazione;
    private int numeroPresenze;
    private int numeroPresenzeProprietario;

    public AutorizzazioniConcessioniRestHelper getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(AutorizzazioniConcessioniRestHelper autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public int getNumeroPresenze() {

	return numeroPresenze;
    }

    public void setNumeroPresenze(int numeroPresenze) {

	this.numeroPresenze = numeroPresenze;
    }

    public int getNumeroPresenzeProprietario() {

	return numeroPresenzeProprietario;
    }

    public void setNumeroPresenzeProprietario(int numeroPresenzeProprietario) {

	this.numeroPresenzeProprietario = numeroPresenzeProprietario;
    }
}
