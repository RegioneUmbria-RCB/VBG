package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaResponse;

public class MetadatiInserimentoFactory implements IAutorizzazioneMetadatoFactory {

    private List<IAutorizzazioneMetadato> lista;

    public static MetadatiInserimentoFactory fromIdELeggiDeterminaResponse(Integer idAutorizzazione, LeggiDeterminaResponse response) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo statico MetadatiInserimentoFactory.fromIdELeggiDeterminaResponse senza passare l'id dell'autorizzazione");
	}
	if (response == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo statico MetadatiInserimentoFactory.fromIdELeggiDeterminaResponse senza passare la response della lettura valorizzata");
	}
	List<IAutorizzazioneMetadato> lista = new ArrayList<IAutorizzazioneMetadato>();
	lista.add(new Classifica(idAutorizzazione, response.getClassifica()));
	lista.add(new CodiceClassifica(idAutorizzazione, response.getCodiceClassifica()));
	lista.add(new NumeroProposta(idAutorizzazione, response.getNumeroProposta()));
	lista.add(new AnnoProposta(idAutorizzazione, response.getAnnoProposta()));
	lista.add(new UfficioProponente(idAutorizzazione, response.getUfficioProponente()));
	lista.add(new StrutturaProponente(idAutorizzazione, response.getStrutturaProponente()));
	lista.add(new Dirigente(idAutorizzazione, response.getDirigente()));
	lista.add(new Oggetto(idAutorizzazione, response.getOggetto()));
	return new MetadatiInserimentoFactory(lista);
    }

    private MetadatiInserimentoFactory(List<IAutorizzazioneMetadato> lista) {

	this.lista = lista;
    }

    @Override
    public List<IAutorizzazioneMetadato> get() {

	return this.lista;
    }
}
