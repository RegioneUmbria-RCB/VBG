package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class RiferimentoPEG implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_RIFERIMENTOPEG";
    private Integer idAutorizzazione;
    private String riferimentoPEG;

    public RiferimentoPEG(Integer idAutorizzazione, String riferimentoPEG) {

	this.idAutorizzazione = idAutorizzazione;
	this.riferimentoPEG = riferimentoPEG;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.riferimentoPEG);
	return metadato;
    }
}
