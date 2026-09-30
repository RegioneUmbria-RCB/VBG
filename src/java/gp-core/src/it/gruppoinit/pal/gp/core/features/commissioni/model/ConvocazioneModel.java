package it.gruppoinit.pal.gp.core.features.commissioni.model;

import it.gruppoinit.pal.gp.core.domain.CommedilizieConvocazioni;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ConvocazioneModel {

    private Integer id;
    private String descrizione;

    public static ConvocazioneModel fromCommedilizieConvocazioni(CommedilizieConvocazioni convocazione) {

	if (convocazione == null || convocazione.getId() == null || convocazione.getId().getCodice() == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo fromCommedilizieConvocazioni senza passare la convocazione");
	}
	ConvocazioneModel model = new ConvocazioneModel();
	model.id = convocazione.getId().getCodice();
	model.descrizione = Utilities.formatDate(convocazione.getDataconvocazione(), false) + " " + convocazione.getOraconvocazione();
	return model;
    }

    public Integer getId() {

	return id;
    }

    public String getDescrizione() {

	return descrizione;
    }
}
