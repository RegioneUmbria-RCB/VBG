package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneAppAmbulanti;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.ConfigurazioneRicaricheApp;

public interface IConfigurazioneAppAmbulantiService {

    ConfigurazioneAppAmbulanti getConfigurazione() throws InvalidConfigurationException;

    ConfigurazioneRicaricheApp getConfigurazioneRicariche(Integer codiceAnagrafe);
}
