package it.gruppoinit.pal.gp.core.service;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.dao.GruppiEndoprocedimentiDDAO;
import it.gruppoinit.pal.gp.core.domain.GruppiEndoprocedimentiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface GruppiEndoprocedimentiDService extends BaseService<GruppiEndoprocedimentiD, PkId> {

    public List<GruppiEndoprocedimentiD> findByGruppiT(Integer codiceGruppoT);

    public List<GruppiEndoprocedimentiD> findByCodiceInventario(Integer codiceEndo);

    public Set<Integer> findByEndoprocedimenti(Set<Integer> codiciEndoprocedimenti, String software);

    /**
     * Torna una lista di endoprocedimenti non configurati nella tabella GruppiEndoProcedimentiD
     * 
     * @param codiciEndoprocedimenti
     * @return
     */
    public Set<Integer> findEndoProcedimentiNonPresentiInGruppi(Set<Integer> codiciEndoprocedimenti);

    /**
     * {@link GruppiEndoprocedimentiDDAO#findByEndoprocedimentiConWarning(Set, String)}
     * 
     * @param codiciEndoprocedimenti
     * @param software
     * @return
     */
    public List<ChiaveValoreBean<Integer, String>> findByEndoprocedimentiConWarning(Set<Integer> codiciEndoprocedimenti, String software);
}
