package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.VwConcessioniattiveDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;

import java.util.List;

public interface VwConcessioniattiveService extends BaseService<VwConcessioniattive, PkId> {

    /**
     * Torna un oggetto VwConcessioniattive utilizzando secondo i parametri immessi
     * 
     * @param codiceMercato
     *            il codice del mercato
     * @param codiceUso
     *            il codice uso
     * @param codicePosteggio
     *            il codice posteggio
     * @return nullo o un oggetto VwConcessioniattive valorizzato
     */
    public VwConcessioniattive findByMercatoUsoPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio);

    /**
     * @see VwConcessioniattiveDAO#findByMercatoAndPosteggio(Integer codiceMercato, Integer codicePosteggio)
     */
    public List<VwConcessioniattive> findByMercatoAndPosteggio(Integer codiceMercato, Integer codicePosteggio);

    /**
     * @see VwConcessioniattiveDAO#findByMercatoMercatoUsoAndPosteggio(Integer codiceMercato, Integer codiceUso, Integer
     *      codicePosteggio)
     */
    public List<VwConcessioniattive> findByMercatoMercatoUsoAndPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio);

    public List<Integer> findMercatoUsoConConcessioniAttiveByPosteggi(Integer codicemercato, String[] idPosteggi);
}
