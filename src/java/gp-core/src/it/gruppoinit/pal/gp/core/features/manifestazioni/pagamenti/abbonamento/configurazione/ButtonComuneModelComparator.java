package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.Comparator;

public class ButtonComuneModelComparator implements Comparator<ButtonComuneModel> {

    @Override
    public int compare(ButtonComuneModel chiave1, ButtonComuneModel chiave2) {

	return chiave1.getComune().compareToIgnoreCase(chiave2.getComune());
    }
}
