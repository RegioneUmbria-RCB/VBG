package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class AnnoProposta implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_ANNOPROPOSTA";
    private Integer idAutorizzazione;
    private Integer annoProposta;

    public AnnoProposta(Integer idAutorizzazione, Integer annoProposta) {

	this.idAutorizzazione = idAutorizzazione;
	this.annoProposta = annoProposta;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.annoProposta.toString());
	return metadato;
    }
}
