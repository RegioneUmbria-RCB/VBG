package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.IConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ListaComunicazioniResoconti;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.RigaComunicazioneDettagliata;

public interface IComunicazioniMassiveService<T extends IConfigurazioneComunicazione> {

    public int creaNuovaComunicazione(T configurazione);

    public void elabora(int idTestata);

    public void elaboraRiga(int idRiga);

    /**
     * 
     * @param idRiferimento
     *            rappresenta l'identificativo del riferimento relativo al contesto della comunicazione es.
     *            idBollettazione oppure idCommissione
     * @return
     */
    public List<ListaComunicazioniResoconti> creaListaTestata(Integer idRiferimento);

    public ListaComunicazioniResoconti getResoconto(Integer idTestata);

    public RigaComunicazioneDettagliata getRigaDettagliata(int idRiga);

    public void eliminaMassiva(int idTestata, Responsabili operatore);

    public boolean exists(Integer idTestata);

    public IWorkflowComunicazioniService getWorkFlowService();
}
