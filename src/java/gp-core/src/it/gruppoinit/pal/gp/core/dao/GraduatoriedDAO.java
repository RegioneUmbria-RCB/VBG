/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.web.GraduatoriedFilter;
import it.gruppoinit.pal.gp.core.domain.web.SchedaDinamicaFilter;

import java.util.List;
import java.util.Set;

/**
 * @author lucap
 * 
 */
public interface GraduatoriedDAO extends BaseDAO<Graduatoried, PkId> {

    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet);

    public List<Istanzedyn2dati> findBandoOutput(Graduatoried graduatoried);

    /**
     * <pre>
     * Recupera tutti i record Graduatoried filtrando per i filtri impostati sulla testata GraduatorietCom:
     *    a. Riservata a: Tutti i soggetti delle graduatoria (non considera se hanno o no una concessione),Solamente i titolari di concessione,
     *       solamente ai non titolari di concessione
     *    b. dalla posizione alla posizione (valore compreso graduatoried.posizione)
     *    c. le schede dell'istanza devono rispettare le condizioni del campo  GraduatorietCom.dyn2filtri
     * @param codicegraduatoriet_com
     * @return
     * </pre>
     */
    public Set<GraduatoriedDTO> findGraduatoriedPerComunuicazione(Integer codicegraduatoriet, Integer posizioneDa, Integer posizioneA,
	    String destinatari, SchedaDinamicaFilter dinamicaFilter);

    /**
     * <pre>
     * Recupera tutti i record Graduatoried filtrando per i filtri impostati sulla testata GraduatorietCom:
     *    a. Riservata a: Tutti i soggetti delle graduatoria (non considera se hanno o no una concessione),Solamente i titolari di concessione,
     *       solamente ai non titolari di concessione
     *    b. dalla posizione alla posizione (valore compreso graduatoried.posizione)
     *    c. le schede dell'istanza devono rispettare le condizioni del campo  GraduatorietCom.dyn2filtri
     * @param codicegraduatoriet_com
     * @return
     * </pre>
     */
    public List<GraduatoriedDTO> findByGraduatoriet(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum, Integer firstResult,
	    Integer maxResult);

    /**
     * 
     * @return
     */
    public int findExsistGraduatoridFilterByDynDatiIstanza(GraduatoriedFilter filter);
}
