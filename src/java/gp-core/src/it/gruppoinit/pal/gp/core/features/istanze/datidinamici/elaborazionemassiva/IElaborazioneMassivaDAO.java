package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massive;
import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltri;
import it.gruppoinit.pal.gp.core.domain.Dyn2Massiveschede;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.RigaElaborazioneModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataModel;

public interface IElaborazioneMassivaDAO {

    /**
     * Ricerca le elaborazioni per il software corrente {@link ORMHelper#getSoftware()}
     * 
     * @return
     */
    List<TestataModel> findAll();

    public Dyn2Massive findMassivaById(Integer id);

    /**
     * la lista delle schede scelte per l'elaborazione ordinate per ordine asc
     * 
     * @param idElaborazione
     * @return
     */
    public List<Dyn2Massiveschede> getSchedeByElaborazione(int idElaborazione);

    /**
     * la lista delle schede scelte per l'elaborazione ordinate per ordine asc
     * 
     * @param idElaborazione
     * @return
     */
    public List<Dyn2MassiveFiltri> getFiltriByElaborazione(int idElaborazione);

    /**
     * 
     * @param idElaborazione
     * @param firstResult
     * @param maxResults
     * @return
     */
    public Set<RigaElaborazioneModel> getRigheForElaborazione(int idElaborazione, Integer firstResult, Integer maxResults);

    int creaElaborazione(CreaTestataRequest request);

    /**
     * Trova le istanze per l'inserimento delle righe di elaborazione. Usato anche per verificare in fase di inserimento
     * se la elaborazione produrrà risultati
     * 
     * @param request
     * @return
     */
    public List<Integer> findRighePerElaborazione(CreaTestataRequest request);

    /**
     * Setta il flag elimina elaborazione a 1
     * 
     * @param idElaborazione
     */
    public void eliminaElaborazioniMassiveRiga(Integer idElaborazione);

    List<IdentificativoDescrizioneBean> findSchedeDinamiche(CreaTestataRequest filtri);

    List<Dyn2MassiveFiltri> getFiltriByElaborazione(int idTestata, ElaborazioniMassiveFiltriEnum tipoFiltro);
}
