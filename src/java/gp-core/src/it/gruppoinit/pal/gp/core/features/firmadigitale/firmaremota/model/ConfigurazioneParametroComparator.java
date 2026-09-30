package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.model;

import java.util.Comparator;

public class ConfigurazioneParametroComparator implements Comparator<ConfigurazioneParametro> {

    @Override
    public int compare(ConfigurazioneParametro parametro1, ConfigurazioneParametro parametro2) {

	return parametro1.getOrdine().compareTo(parametro2.getOrdine());
    }
}
