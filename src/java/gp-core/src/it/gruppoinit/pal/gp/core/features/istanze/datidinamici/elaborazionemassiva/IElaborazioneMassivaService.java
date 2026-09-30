package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataDettagliataModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model.TestataModel;
import it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.ws.model.EsitoElaborazioneMassivaSchede;

public interface IElaborazioneMassivaService {

    /**
     * Ricerca le elaborazioni per il software corrente {@link ORMHelper#getSoftware()}
     * 
     * @return
     */
    List<TestataModel> findAll();

    TestataDettagliataModel findById(int idTestata);

    int creaElaborazione(CreaTestataRequest request);

    EsitoElaborazioneMassivaSchede elabora(int idTestata);

    /**
     * @see IElaborazioneMassivaDAO#findRighePerElaborazione(CreaTestataRequest)
     */
    public List<Integer> findRighePerElaborazione(CreaTestataRequest request);

    /**
     * 
     * @param idElaborazione
     *            setta il FLAG ELIMINA A 1
     */
    public void eliminaElaborazioneMassiveRiga(Integer idElaborazione);

    /**
     * Il metodo cerca le schede dinamiche che sono presenti nelle istanze recuperate dai filtri
     * 
     * @param filtri
     * @return
     */
    List<IdentificativoDescrizioneBean> findSchedeDinamiche(CreaTestataRequest filtri);

    List<TestataModel> findAllByRuoliResponsabile(Integer codiceResponsabile);
}
