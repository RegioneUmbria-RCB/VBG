/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocArendo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ProcedimentoSimpleBean;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.helper.FlagPubblicaEnum;

import java.util.List;
import java.util.Set;

/**
 * @author francescop
 * @author gianpaolot
 */
public interface InventarioprocedimentiService extends BaseService<Inventarioprocedimenti, PkId> {

    /**
     * @see InventarioprocedimentiDAO#findAll(Integer, Integer)
     */
    public List<Inventarioprocedimenti> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param tipiendo
     * @return ritorna una lista di inventarioprocedimenti a cui è associata la categoria di endo procedimenti scelta
     */
    public List<Inventarioprocedimenti> findByTipoendo(Tipiendo tipiendo);

    /**
     * Metodo che ricerca per descrizione e famiglia endo.Utilizzato per Ajax.Autocopleter<br/>
     * Metodo per filtrare gli InventariProcedimeniti per il "like" della descrizione, il codice del tipofamigliaendo e
     * per il codice di tipoendo
     * 
     * @param textToSearch
     * @param codiceFamiglia
     *            non è obbligatorio
     * @param codiceTipologia
     *            non è obbligatorio
     * @param escludiDisabilitati
     *            se true la ricerca non verrà effettuata tra i record disattivati
     * @return
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologia(String textToSearch, Integer codiceFamiglia, String idComuneFamiglia,
	    Integer codiceTipologia, String idComuneTipologia, Boolean escludiDisabilitati);

    /**
     * Metodo che inserisce una copia esatta dell'endoprocedimento scelto con tutti i record ad esso collegati. Il
     * metodo andra a modificare solo la descrizione dell'endoprocedimento aggiungento la dicitura "Copia di"
     * 
     * @param entity
     */
    public Inventarioprocedimenti insertCopia(Inventarioprocedimenti entity);

    /**
     * 
     * @return
     */
    public List<Inventarioprocedimenti> findAllNonStp();

    /**
     * @see InventarioprocedimentiDAO#findByDescrizioneFamigliaendoAndSoftware(String descrizione, String
     *      codicesoftware)
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoAndSoftware(String descrizione, String codicesoftware);

    /**
     * Verifica che gli endoprocedimenti individuati dai codici passati come argomento non siano incompatibili
     * 
     * @param endoprocedimentiArray
     */
    public void checkEndoIncompatibili(Set<Integer> endoprocedimentis);

    /**
     * @see InventarioprocedimentiDAO#countRecordByFilter(Inventarioprocedimenti inventarioprocedimenti)
     */
    public int countRecordByFilter(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * @see InventarioprocedimentiDAO#findByInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti, int
     *      startRowPage, int endRowPage)
     */
    public List<Inventarioprocedimenti> findByInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti, Integer startRowPage,
	    Integer endRowPage);

    public List<Inventarioprocedimenti> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult);

    public List<Inventarioprocedimentisoftware> findInventarioprocedimentisoftwareByFilterTable(FilterTable filterTable, Integer firstResult,
	    Integer maxResult);

    public int countRecord(FilterTable filterTable);

    /**
     * Ritorna un oggetto filter Table a partire dalla entity
     */
    public FilterTable createFilterTableByEntity(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * Ritorna un oggetto filter Table a partire da Inventarioprocedimentisoftware
     */
    public FilterTable createFilterTableByEntity(Inventarioprocedimentisoftware inventarioprocedimentisoftware);

    /**
     * Ritorna la lista degli endo procedimenti filtarti per famiglia , categoria e software e con una like sul campo
     * procedimento
     * 
     * @param textToSearch
     * @param codiceFamiglia
     * @param codiceTipologia
     * @param codicesoftware
     * @return
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaAndSoftware(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, String[] codicesoftware, String idcomunebase, String tipoEndo);

    /**
     * Torna la lista delle Inventarioprocedimenti di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Inventarioprocedimenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);

    /**
     * Torna il conteggio dei tipiendo per filtrati per tipo famiglia endo
     * 
     * @param tipiendo
     * @return
     */
    public int countByTipiendo(Tipiendo tipiendo);

    /**
     * Torna la lista delle Inventarioprocedimenti legati ad un tipomovimento. i record sono ordinati per
     * software.ordine, software.descrizione, inventarioprocedimenti.ordine, inventarioprocedimenti.procedimento
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Inventarioprocedimenti> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);

    /**
     * Ritorna la lista degli endo procedimenti filtarti per famiglia, categoria, software,codice nature endo con una
     * like sul campo procedimento
     * 
     * @param textToSearch
     * @param codiceFamiglia
     * @param codiceTipologia
     * @param codicesoftware
     * @param listCodicinature
     * @param listCodiciEndoAttivati
     *            se diverso da null verranno esclusi i record con il codici passati
     * @return
     */
    public List<Inventarioprocedimenti> findByDescrizioneFamigliaendoETipologiaAndSoftwareAndNatureEndo(String textToSearch, Integer codiceFamiglia,
	    Integer codiceTipologia, String codicesoftware, List<Integer> listCodicinature, List<Integer> listCodiciEndoAttivati);

    public List<Integer> findCodiciEndoPerSoftware(String software);

    public List<Inventarioprocedimenti> findByAlberoprocArendo(AlberoprocArendo alberoprocArendo);

    public List<ProcedimentoSimpleBean> findListaSottonodiDi(String codiceElemento, FlagPubblicaEnum pubblicaEnum);

    public List<String> findGerarchiaNodiPadre(String codiceElemento);

    public List<Tipifamiglieendo> findTutteFamiglieEndoByCurrSoftwareAndTT(FlagPubblicaEnum flagPubblicaEnum);

    public List<Tipiendo> findTutteTipologieEndoEndoByCurrSoftwareAndTT(Integer codiceFamiglia, FlagPubblicaEnum flagPubblicaEnum);

    public ProcedimentoBean findProcedimentoBean(Integer codiceProcedimento, boolean isRegionale, String codiceComune);

    /**
     * <pre>
     *      	dove tipoRicerca può valere:
     * 		    - tutteParole: Cerca le voci che contengono tutte le parole
     * 		    - interaFrase: Cerca le voci che contengono l'intera frase
     * 		    - almenoUnaParola: Cerca le voci che contengono almeno una parola
     * 	
     * 		    e campiRicerca può valere:
     * 		    - titoli: cerca nei titoli
     * 		    - titoliDescrizioni: cerca nei titoli e nelle descrizioni
     * </pre>
     * 
     * @param testoDaCercare
     * @param tipoRicerca
     * @param campiRicerca
     * @return
     */
    public List<ProcedimentoSimpleBean> findProcedimentiByDescrizione(String testoDaCercare, String tipoRicerca, String campiRicerca,
	    Integer firstResult, Integer maxResults);

    public String getEndoprocedimentoKey(Inventarioprocedimenti inventarioprocedimenti);

    public PkId getIdFromEndoprocedimentoKey(String endoprocedimentoKey);

    public void insertEndo2(Inventarioprocedimenti entity, Integer codicealberoproc);

    public void insertEndo1(Inventarioprocedimenti entity, StpEndoTipo1 stpEndoTipo1);

    public void updateEndo1(Inventarioprocedimenti entity, StpEndoTipo1 stpEndoTipo1);

    public String getChiaveEndoLocale(Inventarioprocedimenti ip);
}
