package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.IAutorizzazioneMetadato;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaResponse;

public class MetadatiNumerazioneFactory implements IAutorizzazioneMetadatoFactory {

    private List<IAutorizzazioneMetadato> lista;

    public static MetadatiNumerazioneFactory fromIdELeggiDeterminaResponse(Integer idAutorizzazione, LeggiDeterminaResponse response) {

	if (idAutorizzazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo statico MetadatiNumerazioneFactory.fromIdELeggiDeterminaResponse senza passare l'id dell'autorizzazione");
	}
	if (response == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo statico MetadatiNumerazioneFactory.fromIdELeggiDeterminaResponse senza passare la response della lettura valorizzata");
	}
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	List<IAutorizzazioneMetadato> lista = new ArrayList<IAutorizzazioneMetadato>();
	lista.add(new NumeroAtto(idAutorizzazione, response.getNumeroAtto()));
	lista.add(new DataAtto(idAutorizzazione, response.getDataAtto(), format));
	lista.add(new AnnoAtto(idAutorizzazione, response.getAnnoAtto()));
	return new MetadatiNumerazioneFactory(lista);
    }

    private MetadatiNumerazioneFactory(List<IAutorizzazioneMetadato> lista) {

	this.lista = lista;
    }

    @Override
    public List<IAutorizzazioneMetadato> get() {

	return this.lista;
    }
}
