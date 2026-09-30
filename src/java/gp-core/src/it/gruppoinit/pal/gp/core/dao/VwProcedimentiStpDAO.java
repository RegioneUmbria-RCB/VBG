package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStp;
import it.gruppoinit.pal.gp.core.domain.VwProcedimentiStpId;

import java.util.List;

public interface VwProcedimentiStpDAO extends BaseDAO<VwProcedimentiStp, VwProcedimentiStpId> {

    public List<VwProcedimentiStp> findByCodiceStp(String codiceStp);

    public List<VwProcedimentiStp> findByCodiceInventario(Integer codiceInventario);
}
