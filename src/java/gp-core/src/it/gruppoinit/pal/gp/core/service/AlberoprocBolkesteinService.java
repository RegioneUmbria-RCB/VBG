package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.AlberoprocBolkestein;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.AlberoprocBolkesteinCommand;

import java.util.List;

public interface AlberoprocBolkesteinService extends BaseService<AlberoprocBolkestein, PkId> {

    /**
     * Metodo che restituisce la lista di AlberoprocBolkestein filtrati per il codice Alberoproc
     * 
     * @param codice
     * @return
     */
    public List<AlberoprocBolkestein> findByAlberoProc(Integer codice);

    /**
     * Restituisce l'AlberoprocBolkestein relativo all'intervento, mercato, uso, e posteggio
     * 
     * @param codiceAlberoproc
     * @param codiceMercato
     * @param codiceUso
     * @param idPosteggio
     * @return
     */
    public AlberoprocBolkestein findByAlberoProcAndMercatoAndUsoAndPosteggio(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso,
	    Integer idPosteggio);

    public void populateCommand(AlberoprocBolkesteinCommand alberoprocbolkestein);

    /**
     * Cancella tutti gli AlberoprocBolkestein relativi a quell'intervento, mercato e uso
     * 
     * @param codiceAlberoproc
     * @param codiceMercato
     * @param codiceUso
     */
    public void deleteByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso);

    /**
     * Restituisce la lista di AlberoprocBolkestein relativi all'intervento, mercato e uso
     * 
     * @param codiceAlberoproc
     * @param codiceMercato
     * @param codiceUso
     * @return
     */
    public List<AlberoprocBolkestein> findByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso);

    /**
     * Inserisce tutti gli AlberoprocBolkestein per quell'intervento, mercato e uso
     * 
     * @param codiceAlberoproc
     * @param codiceMercato
     * @param codiceUso
     */
    public void insertByAlberoProcAndMercatoAndUso(Integer codiceAlberoproc, Integer codiceMercato, Integer codiceUso);
}
