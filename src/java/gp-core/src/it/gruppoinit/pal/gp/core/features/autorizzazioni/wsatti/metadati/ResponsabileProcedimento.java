package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;

public class ResponsabileProcedimento implements IAutorizzazioneMetadato {

    private static String CHIAVE = "WSATTI_RESPONSABILEPROCEDIMENTO";
    private Integer idAutorizzazione;
    private String responsabileProcedimento;

    public ResponsabileProcedimento(Integer idAutorizzazione, String responsabileProcedimento) {

	this.idAutorizzazione = idAutorizzazione;
	this.responsabileProcedimento = responsabileProcedimento;
    }

    @Override
    public AutorizzazioniMetadati toAutorizzazioniMetadati() {

	AutorizzazioniMetadatiId id = new AutorizzazioniMetadatiId(ORMHelper.getIdcomune(), this.idAutorizzazione, CHIAVE);
	AutorizzazioniMetadati metadato = new AutorizzazioniMetadati();
	metadato.setId(id);
	metadato.setValore(this.responsabileProcedimento);
	return metadato;
    }
}
