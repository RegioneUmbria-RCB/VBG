package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;

public interface ICreazioneMassiveDettaglioService {

    public void collegaRigheBollettazioneAComunicazioni(int idTestata, ConfigurazioneComunicazioniBollettazione configurazioneComunicazione);

    /**
     * 
     * @param idTestata
     * @param configurazioneComunicazione
     */
    public void collegaRigheCommissioniAComunicazioni(int idTestata, ConfigurazioneComunicazioniCommissioni configurazioneComunicazione);
}
