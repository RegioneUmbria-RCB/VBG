package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class ComunicazioniDConcessioniDTO extends ComunicazioniDDTO {

    private PkId idComunicazioniDConcessioni;
    private AutorizzazioniConcessioniDTO autorizzazioniConcessioni;
    private IstanzeDTO istanza;

    public PkId getIdComunicazioniDConcessioni() {

	return idComunicazioniDConcessioni;
    }

    public void setIdComunicazioniDConcessioni(PkId idComunicazioniDConcessioni) {

	this.idComunicazioniDConcessioni = idComunicazioniDConcessioni;
    }

    public AutorizzazioniConcessioniDTO getAutorizzazioniConcessioni() {

	return autorizzazioniConcessioni;
    }

    public void setAutorizzazioniConcessioni(AutorizzazioniConcessioniDTO autorizzazioniConcessioni) {

	this.autorizzazioniConcessioni = autorizzazioniConcessioni;
    }

    public IstanzeDTO getIstanza() {

	return istanza;
    }

    public void setIstanza(IstanzeDTO istanza) {

	this.istanza = istanza;
    }
}
