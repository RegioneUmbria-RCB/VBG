package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive.metadati;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati.MetadatoBollettazione;

public interface IBollMetadatiService {

    List<MetadatoBollettazione> elencoMetadati(BollGestTestata testata);
}
