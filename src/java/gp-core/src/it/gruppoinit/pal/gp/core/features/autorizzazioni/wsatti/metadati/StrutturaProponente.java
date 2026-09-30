package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class StrutturaProponente implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_STRUTTURAPROPONENTE";
    private Integer idAutorizzazione;
    private String strutturaProponente;

    public StrutturaProponente(Integer idAutorizzazione, String strutturaProponente) {

	this.idAutorizzazione = idAutorizzazione;
	this.strutturaProponente = strutturaProponente;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.strutturaProponente);
	return metadato;
    }
}
