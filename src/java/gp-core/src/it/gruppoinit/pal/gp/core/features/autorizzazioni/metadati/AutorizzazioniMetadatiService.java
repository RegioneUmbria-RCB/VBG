package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;

public interface AutorizzazioniMetadatiService {

    void insert(AutorizzazioniMetadati metadato);

    void insertMetadati(List<IAutorizzazioneMetadato> metadati);

    void deleteByIdAutorizzazione(Integer codice);
}
