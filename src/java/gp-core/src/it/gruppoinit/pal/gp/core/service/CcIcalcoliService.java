package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.CcIcalcoliDAO;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoli;
import it.gruppoinit.pal.gp.core.domain.CcIcalcoloTcontributo;
import it.gruppoinit.pal.gp.core.domain.CcIcalcolotcontributoRiduz;
import it.gruppoinit.pal.gp.core.domain.CcItabella1;
import it.gruppoinit.pal.gp.core.domain.CcItabella2;
import it.gruppoinit.pal.gp.core.domain.CcItabella3;
import it.gruppoinit.pal.gp.core.domain.CcItabella4;
import it.gruppoinit.pal.gp.core.domain.CcTabellaClassiedificio;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;
import java.util.Set;

/**
 * 
 * @author
 */
public interface CcIcalcoliService extends BaseService<CcIcalcoli, PkId> {

    /**
     * @see CcIcalcoliDAO#findAll(Integer, Integer)
     */
    public List<CcIcalcoli> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna il numero di oggetti CcIcalcoli filtrati per CcValiditacoefficienti
     * 
     * @param entity
     * @return
     */
    public int countByCcTabellaClassiedificio(CcTabellaClassiedificio entity);

    /**
     * Restituisce le righe della tabella 1 per una data istanza di CcIcalcoli passata come argomento. Le righe sono
     * ordinate per ccClassisuperfici.da, ccClassisuperfici.a ossia nell'ordine corretto per la visualizzazione in
     * maschera.
     * 
     * @param ccicalcoli
     * @return
     */
    public List<CcItabella1> findRigheTabella1(CcIcalcoli ccicalcoli);

    /**
     * Restituisce le righe della tabella 2 per una data istanza di CcIcalcoli passata come argomento. Le righe sono
     * ordinate per ccDettaglisuperficie.descrizione ossia nell'ordine corretto per la visualizzazione in maschera.
     * 
     * @param ccicalcoli
     * @return
     */
    public List<CcItabella2> findRigheTabella2(CcIcalcoli ccicalcoli);

    /**
     * Restituisce le righe della tabella 3 per una data istanza di CcIcalcoli passata come argomento. Le righe sono
     * ordinate per ccTabella3.rapportoSuSnrDa e ccTabella3.rapportoSuSnrA ossia nell'ordine corretto per la
     * visualizzazione in maschera.
     * 
     * @param ccicalcoli
     * @return
     */
    public List<CcItabella3> findRigheTabella3(CcIcalcoli ccicalcoli);

    /**
     * Restituisce le righe della tabella 4 per una data istanza di CcIcalcoli passata come argomento. Le righe sono
     * ordinate per ccTabellaCaratterist.descrizione e ccTabella3.rapportoSuSnrA ossia nell'ordine corretto per la
     * visualizzazione in maschera.
     * 
     * @param ccicalcoli
     * @return
     */
    public List<CcItabella4> findRigheTabella4(CcIcalcoli ccicalcoli);

    /**
     * Inserisce nel database il calcolo del costo di costruzione passato come argomento e tutte le entità in relazione.
     * Se il calcolo esiste già (ha l'ID valorizzato) il record e le entità in relazione vengono aggiornate.
     * 
     * @param calcolo
     */
    public void insertOrUpdate(CcIcalcoli calcolo);

    /**
     * Salva il coefficiente per il calcolo della quota di contributo relativa ai costi di costruzione. coefficiente
     * totale salvato in CC_ICALCOLO_TCONTRIBUTO.COEFFICIENTE destinazione salvata in CC_ICALCOLO_TCONTRIBUTO.FK_CCDE_ID
     * tipo intervento inserito o aggiornato in CC_ICALCOLO_DCONTRIBUTO.FK_CCTI_ID coefficiente (senza riduzioni)
     * salvato in CC_ICALCOLO_DCONTRIBUTO.COEFFICIENTE Le righe di CC_ICALCOLOTCONTRIBUTO_RIDUZ già esistenti vengono
     * cancellate e quelle passate come argomento (riduzContributo) vengono inserite e collegate a CcIcalcoloTcontributo
     * corrente.
     * 
     * @param tContributo
     * @param riduzContributo
     */
    public void salvaCoefficienteContributo(CcIcalcoloTcontributo tContributo, Set<CcIcalcolotcontributoRiduz> riduzContributo);

    /**
     * Ritorna un oggetto CcIcalcoli filtrato per CcIcalcoloTcontributo, se non eiste ritorna null
     * 
     * @param entity
     * @return
     */
    public CcIcalcoli findByCcIcalcoloTcontributo(CcIcalcoloTcontributo entity);
}
