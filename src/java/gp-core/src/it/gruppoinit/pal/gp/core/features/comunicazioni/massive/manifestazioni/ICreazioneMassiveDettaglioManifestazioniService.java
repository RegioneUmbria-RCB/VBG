package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ICreazioneMassiveDettaglioService;

public interface ICreazioneMassiveDettaglioManifestazioniService
	extends ICreazioneMassiveDettaglioService<ConfigurazioneComunicazioniManifestazioni> {

    void collegaRigaAComunicazione(ConfigurazioneComunicazioniManifestazioni configurazione, int idPresenza, int idTestata);
}
