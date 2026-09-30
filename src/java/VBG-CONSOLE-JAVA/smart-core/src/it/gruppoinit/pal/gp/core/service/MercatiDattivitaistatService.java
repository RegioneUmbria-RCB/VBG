package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;

import java.util.List;

public interface MercatiDattivitaistatService extends BaseService<MercatiDattivitaistat, MercatiDattivitaistatId> {

    /**
     * Metodo che permette di inserire a più posteggi una merceologia
     * 
     * @param mercatiDattivitaistat
     */
    public void insertMerceologieAPosteggi(MercatiDattivitaistat mercatiDattivitaistat);

    /**
     * @see MercatiDattivitaistatDAO#findAttivitaPosteggi(Integer codicemercato, List<Integer> listcodici)
     */
    public List<MercatiDattivitaistat> findAttivitaPosteggi(Integer codicemercato, List<Integer> listcodici);
}
