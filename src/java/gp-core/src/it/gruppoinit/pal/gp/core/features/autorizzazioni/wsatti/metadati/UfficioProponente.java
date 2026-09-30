package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class UfficioProponente implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_UFFICIOPROPONENTE";
    private Integer idAutorizzazione;
    private String ufficioProponente;

    public UfficioProponente(Integer idAutorizzazione, String ufficioProponente) {

	this.idAutorizzazione = idAutorizzazione;
	this.ufficioProponente = ufficioProponente;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.ufficioProponente);
	return metadato;
    }
}
