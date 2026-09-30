package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;

import java.util.List;
import java.util.Set;

public interface MercatiDattivitaistatDAO extends BaseDAO<MercatiDattivitaistat, MercatiDattivitaistatId> {

    public Set<MercatiDattivitaistat> findAttivitaPosteggio(Integer codicemercato, Integer idposteggio);

    /**
     * Metodo che ritorna tutte le attivita del mercato per i posteggi passati, se la lista viene passata nulla verranno
     * ritornano tutte le attività presenti nel mercato (le attivita non sono duplicate)
     * 
     * @param codicemercato
     * @param list
     *            può essere null
     * @return
     */
    public List<MercatiDattivitaistat> findAttivitaPosteggi(Integer codicemercato, List<Integer> listcodici);
}
