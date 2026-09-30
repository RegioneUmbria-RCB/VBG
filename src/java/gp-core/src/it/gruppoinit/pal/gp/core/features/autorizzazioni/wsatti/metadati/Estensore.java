package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class Estensore implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_ESTENSORE";
    private Integer idAutorizzazione;
    private String estensore;

    public Estensore(Integer idAutorizzazione, String estensore) {

	this.idAutorizzazione = idAutorizzazione;
	this.estensore = estensore;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.estensore);
	return metadato;
    }
}
