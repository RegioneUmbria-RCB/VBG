package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.web.command.NuovaIstanzaCommand;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeStepsEseguiti;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.FoArjSteps;

import java.util.List;

public interface NuovaIstanzaService {

    public void clear(NuovaIstanzaCommand cmd, List<FoArjDomandeStepsEseguiti> stepsToClear);

    /**
     * il metodo crea un nuovo oggetto NuovaIstanzaCommand e gli associa il flusso recuperato dalla configurazione
     * 
     * @return
     * @throws Exception
     */
    public NuovaIstanzaCommand nuovaDomanda() throws Exception;

    /**
     * il metodo crea un nuovo oggetto NuovaIstanzaCommand e gli associa il flusso collegato all'intervento o in
     * alternativa quello dalla configurazione
     * 
     * @param codiceIntervento
     * @param servizio
     *            (eventuale servizio registrato)
     * @return
     * @throws Exception
     */
    public NuovaIstanzaCommand nuovaDomandaDaIntervento(Integer codiceIntervento, FoArjServizi servizio) throws Exception;

    /**
     * il metodo recupera la domanda da FO_ARJ_DOMANDE e gli associa come flusso il merge di quello della configurazione
     * (fino allo step INTERVENTO) più quello dell'eventuale intervento salvato nella domanda
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public NuovaIstanzaCommand riprendiDomandaInCompilazione(Integer id) throws Exception;

    /**
     * il metodo salva(insert o update) la domanda in compilazione
     * 
     * @param command
     * @param stepEseguito
     *            parametro opzionale da passare solo se si deve salvare lo step e passare ad un altro step
     * @throws Exception
     */
    public void saveDomanda(NuovaIstanzaCommand command, FoArjSteps stepEseguito) throws Exception;

    /**
     * il metodo aggiorna il flusso associato eseguendo il merge del flusso della configurazione con quello
     * dell'intervento scelto
     * 
     * @param cmd
     * @return
     * @throws Exception
     */
    public FoArjSteps updateDomandaDaCambioIntervento(NuovaIstanzaCommand cmd) throws Exception;

    /**
     * Il metodo verifica se c'è stato un cambio intervento confrontando il valore del campo interventoProcedimenti con quello del campo intervento 
     * 
     * @param cmd
     * @return true se cmd.getInterventoProcedimenti()==null || cmd.getIntervento()!=cmd.getInterventoProcedimenti()
     */
    public boolean isCambioIntervento(NuovaIstanzaCommand cmd);
}
