package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class CodiceClassifica implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_CODICECLASSIFICA";
    private Integer idAutorizzazione;
    private String codiceClassifica;

    public CodiceClassifica(Integer idAutorizzazione, String codiceClassifica) {

	this.idAutorizzazione = idAutorizzazione;
	this.codiceClassifica = codiceClassifica;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.codiceClassifica);
	return metadato;
    }
}
