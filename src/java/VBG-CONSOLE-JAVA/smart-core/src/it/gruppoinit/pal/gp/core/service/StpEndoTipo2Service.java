package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo2DAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.List;
import java.util.Map;

public interface StpEndoTipo2Service extends BaseService<StpEndoTipo2, PkId> {

    public static final String TIPO_CATEGORIA = "CATEGORIA";
    public static final String TIPO_ATTIVITA = "ATTIVITA";
    public static final String TIPO_ENDO = "ENDO";

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo, String idcomune);

    public StpEndoTipo2 findbyAlberoproc(String idcomune, Integer codiceAlberoproc);

    /**
     * Se inserito torna l'endo associato all'inventarioprocedimento e al nodo alberoprc
     * @param scid 
     * @param codiceInventario
     * 
     * @return
     */
    public StpEndoTipo2 findByAlberoprocInventarioproc(String idcomune, Integer scid, Integer codiceInventario);

    /**
     * Se inserito torna l'endo associato all'inventarioprocedimento e al nodo alberoprc
     * @param scid 
     * @param codiceInventario
     * 
     * @return
     */
    public List<StpEndoTipo2> findByInventarioproc(String idcomune, Integer codiceInventario);
    /**
     * @param tipo
     * 
     * @see StpEndoTipo2DAO#findBySoftwareAndTipo(String, String)
     */
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo, String idcomune);

    /**
     * @param tipo
     * 
     * @see StpEndoTipo2DAO#findBySoftwareAndTipo(String, String)
     */
    public List<StpEndoTipo2> findBySoftwareAndTipoAndFlagRegionale(String software, String tipo, Boolean flagRegionale, String idcomune);

    /**
     * @see StpEndoTipo2DAO#verificaSchedeEndo2()
     */
    public List<StpEndoTipo2> verificaSchedeEndo2(String idcomune);

    /**
     * <pre>
     * Ricerca la lista di StpEndoTipo2 filtrando per :
     *   <ol>
     *   	<li>codiceTipologiaEndo</li>
     *   	<li>tipo</li>
     *   </ol>
     * 
     * @param codiceTipologiaEndo
     * @param tipo
     * @return List<StpEndoTipo2>
     * </pre>
     */
    public List<StpEndoTipo2> findbyTipoAndCodiceTipologiaEndo(Integer codiceTipologiaEndo, String tipo, String idcomune);

    /**
     * Ricerca la lista di StpEndoTipo2 filtrando per:
     * <ol>
     * <li>codiceEndoRegionale</li>
     * <li>tipo</li>
     * </ol>
     * 
     * @param codiceEndoRegionale
     * @param tipo
     * @return
     */
    public List<StpEndoTipo2> findbyTipoAndCodiceEndoRegionale(String codiceEndoRegionale, String tipo, String idcomune);

    public StpEndoTipo2 findbyTipoAndCodiceTipologiaEndoAndCodiceRegionale(Integer codiceTipologiaEndo, String tipo, String codiceEndoRegionale,
	    String idcomune);

    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate(String idcomune);

    /**
     * <pre>
     * Verifica se l'intervento è del cart, se eiste ritorna l'oggetto; altrimenti null. La logica per la verifca è: 
     * a.Esiste un record in StpEndoTipo2.
     * b.StpEndoTipo2 ha un associazione con la tabella Oggetti.
     * 
     * @param codiceIntervento
     * </pre>
     */
    public StpEndoTipo2 isIntervetoCART(Integer codiceIntervento, String idcomune);

    public List<StpEndoTipo2> findListCategorieAndAttivita(String idcomune);
}
