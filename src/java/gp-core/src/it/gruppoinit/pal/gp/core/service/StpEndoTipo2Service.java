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

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo);

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice);

    public StpEndoTipo2 findbyAlberoproc(Integer codiceAlberoproc);

    /**
     * Se inserito torna l'endo associato all'inventarioprocedimento
     * 
     * @param codiceInventario
     * @return
     */
    public StpEndoTipo2 findByInventarioproc(Integer codiceInventario);

    /**
     * @param tipo
     * @deprecated può causare OOM
     * @see StpEndoTipo2DAO#findBySoftwareAndTipo(String, String)
     */
    @Deprecated
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo);

    public List<Integer> findCodiciBySoftwareAndTipo(String software, String tipo);

    /**
     * @see StpEndoTipo2DAO#verificaSchedeEndo2()
     */
    public List<StpEndoTipo2> verificaSchedeEndo2();

    /**
     * <pre>
     * Ricerca la lista di StpEndoTipo2 filtrando per :
     *   <ol>
     *   	<li>codiceTipologiaEndo</li>
     *   	<li>tipo</li>
     *   </ol>
     * 
     * &#64;param codiceTipologiaEndo
     * &#64;param tipo
     * &#64;return List<StpEndoTipo2>
     * </pre>
     */
    public List<StpEndoTipo2> findbyTipoAndCodiceTipologiaEndo(Integer codiceTipologiaEndo, String tipo);

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
    public List<StpEndoTipo2> findbyTipoAndCodiceEndoRegionale(String codiceEndoRegionale, String tipo);

    public StpEndoTipo2 findbyTipoAndCodiceTipologiaEndoAndCodiceRegionale(Integer codiceTipologiaEndo, String tipo, String codiceEndoRegionale);

    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate();

    /**
     * <pre>
     * Verifica se l'intervento è del cart, se eiste ritorna l'oggetto; altrimenti null. La logica per la verifca è: 
     * a.Esiste un record in StpEndoTipo2.
     * b.StpEndoTipo2 ha un associazione con la tabella Oggetti.
     * 
     * &#64;param codiceIntervento
     * </pre>
     */
    public StpEndoTipo2 isIntervetoCART(Integer codiceIntervento);

    public List<Integer> findListCategorieAndAttivita();

    public List<StpEndoTipo2> findListByInventarioprocedimenti(Integer codiceendo);

    public List<StpEndoTipo2> findByCodiceRegionale(String codiceEndoRegionale);

    public List<StpEndoTipo2> findAllByStpCodice(Integer stpCodice, String tipo);

    public List<StpEndoTipo2> findByTipoSortByStpCodice(String tipo, boolean sortAsc);

    public StpEndoTipo2 findbyStpCodiceAndSoftwareAlberoProc(Integer stpCodice, String software);
}
