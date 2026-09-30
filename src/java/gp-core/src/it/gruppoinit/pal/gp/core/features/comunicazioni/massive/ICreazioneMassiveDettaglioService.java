package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;

public interface ICreazioneMassiveDettaglioService<T extends IConfigurazioneComunicazione> {

    public void collegaRigheAComunicazioni(int idTestata, T configurazione);
}
