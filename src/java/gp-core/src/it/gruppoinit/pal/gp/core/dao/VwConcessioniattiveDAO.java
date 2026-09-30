package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwConcessioniattive;

import java.util.List;

public interface VwConcessioniattiveDAO extends BaseDAO<VwConcessioniattive, PkId> {

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
     * Torna una lista di VwConcessioniattive di tutti gli usi del mercato filtrati per mercato e psosteggio
     * 
     * @param codiceMercato
     *            il codice del mercato
     * 
     * @param codicePosteggio
     *            il codice posteggio
     * @return nullo o un oggetto VwConcessioniattive valorizzato
     */
    public List<VwConcessioniattive> findByMercatoAndPosteggio(Integer codiceMercato, Integer codicePosteggio);

    /**
     * Torna una lista di VwConcessioniattive filtrati per mercato, uso e psosteggio
     * 
     * @param codiceMercato
     * @param codiceUso
     * @param codicePosteggio
     * @return
     */
    public List<VwConcessioniattive> findByMercatoMercatoUsoAndPosteggio(Integer codiceMercato, Integer codiceUso, Integer codicePosteggio);
}
