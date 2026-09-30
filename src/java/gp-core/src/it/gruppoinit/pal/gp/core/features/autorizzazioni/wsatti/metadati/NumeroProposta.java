package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class NumeroProposta implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_NUMEROPROPOSTA";
    private Integer idAutorizzazione;
    private String numeroProposta;

    public NumeroProposta(Integer idAutorizzazione, String numeroProposta) {

	this.idAutorizzazione = idAutorizzazione;
	this.numeroProposta = numeroProposta;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.numeroProposta);
	return metadato;
    }
}
