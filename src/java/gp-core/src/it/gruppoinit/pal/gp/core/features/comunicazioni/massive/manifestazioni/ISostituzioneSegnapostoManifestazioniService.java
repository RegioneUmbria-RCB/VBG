package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;

public interface ISostituzioneSegnapostoManifestazioniService {

    OggettoComunicazioneManifestazioni effettuaSostituzioniByMassiveDettaglio(Integer idMailTipo, MassiveDettaglio massiveDettaglio);
}
