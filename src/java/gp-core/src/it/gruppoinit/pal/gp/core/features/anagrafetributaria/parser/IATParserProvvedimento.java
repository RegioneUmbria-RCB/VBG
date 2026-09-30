package it.gruppoinit.pal.gp.core.features.anagrafetributaria.parser;

import java.util.Date;

public interface IATParserProvvedimento {

    String getNumeroProvvedimento();

    Date getDataProvvedimento();

    String getCfPivaSoggetto();

    boolean isDatiRicercabili();

    String getHashChiaveProvvedimento();
}