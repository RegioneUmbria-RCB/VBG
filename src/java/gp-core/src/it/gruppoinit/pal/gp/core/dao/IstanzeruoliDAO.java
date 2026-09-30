/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.IstanzeruoliId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeruoliDTO;

/**
 * @author francescop
 * 
 */
public interface IstanzeruoliDAO extends BaseDAO<Istanzeruoli, IstanzeruoliId> {

    public List<Istanzeruoli> findByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile);

    /**
     * @deprecated ATTENZIONE!!! Può provocare OOM in quanto la costruzione dell'oggetto istanzeruoli è onerosa. usare
     *             {@link #findDTOByAlberoproc(Integer, Integer, Integer)}
     * @param codiceAlberoproc
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzeruoli> findByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult);

    public List<IstanzeruoliDTO> findDTOByAlberoproc(Integer codiceAlberoproc, Integer firstResult, Integer maxResult);

    public int countByAlberoproc(Integer codiceAlberoproc);

    /**
     * @deprecated ATTENZIONE!!! Può provocare OOM in quanto la costruzione dell'oggetto istanzeruoli è onerosa usare
     *             {@link #findDTOByAlberoprocAndIdRuolo(Integer, Integer, Integer, Integer)}
     * 
     * @param codiceAlberoproc
     * @param idRuolo
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Istanzeruoli> findByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo, Integer firstResult, Integer maxResult);

    public List<IstanzeruoliDTO> findDTOByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo, Integer firstResult, Integer maxResult);

    public int countByAlberoprocAndIdRuolo(Integer codiceAlberoproc, Integer idRuolo);

    public IstanzeruoliDTO findDTOById(String idcomune, Integer codiceIstanza, Integer idRuolo);

    public void insertDTO(String idcomune, Integer codiceIstanza, Integer idRuolo);

    public void deleteDTO(String idcomune, Integer codiceIstanza, Integer idRuolo);

    public void deleteAllByAlberoproc(Integer codiceAlberoproc);

    public void delete(String idComune, Integer codiceIstanza, Set<Integer> idRuoliDaCancellare);

    public void insert(String idComune, Integer codiceIstanza, Set<Integer> idRuoliDaAggiungere);
}
