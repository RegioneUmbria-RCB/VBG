package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;

public interface IWorkFlowStep<T extends IConfigurazioneComunicazione> {

    /**
     * Gli step devono committare in caso di successo e salvare il nuovo stato nella riga di dettaglio
     * 
     * @param dettaglio
     * @param configurazione
     */
    public void elabora(int idDettaglioComunicazione, T configurazione);
}
