package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface MercatipresenzeTPrenotService extends BaseService<MercatipresenzeTPrenot, PkId> {

    public List<MercatipresenzeTPrenot> findByMercatipresenzeT(Integer mercatipresenzeTId);

    public List<MercatipresenzeTPrenot> findByMercatipresenzeTAndPosteggioAndAnagrafe(Integer mercatipresenzeTId, Integer mercatiDId,
	    Integer anagrafeId);

    /**
     * Ordinati per data inserimento
     * 
     * @param mercatipresenzeTId
     * @param mercatiDId
     * @return
     */
    public List<MercatipresenzeTPrenot> findByMercatipresenzeTAndPosteggio(Integer mercatipresenzeTId, Integer mercatiDId);

    /**
     * ordinati per data inserimento
     * 
     * @param mercatiDId
     * @return
     */
    public List<MercatipresenzeTPrenot> findByMercatiD(Integer mercatiDId);

    /**
     * ordinati per data inserimento
     * 
     * @param anagrafeId
     * @return
     */
    public List<MercatipresenzeTPrenot> findByAnagrafe(Integer anagrafeId);
}
