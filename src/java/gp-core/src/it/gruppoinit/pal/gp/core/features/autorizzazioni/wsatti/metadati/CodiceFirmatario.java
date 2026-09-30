package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class CodiceFirmatario implements IAutorizzazioneMetadato {

    public static String CHIAVE = "CODICE_FIRMATARIO";
    private Integer idAutorizzazione;
    private String codiceFirmatario;

    public CodiceFirmatario(Integer idAutorizzazione, String codiceFirmatario) {

	this.idAutorizzazione = idAutorizzazione;
	this.codiceFirmatario = codiceFirmatario;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.codiceFirmatario);
	return metadato;
    }
}
