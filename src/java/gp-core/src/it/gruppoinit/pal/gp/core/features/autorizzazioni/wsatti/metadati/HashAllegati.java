package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class HashAllegati implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_HASHALLEGATI";
    private Integer idAutorizzazione;
    private String hashAllegati;

    public HashAllegati(Integer idAutorizzazione, String hashAllegati) {

	this.idAutorizzazione = idAutorizzazione;
	this.hashAllegati = hashAllegati;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.hashAllegati);
	return metadato;
    }
}
