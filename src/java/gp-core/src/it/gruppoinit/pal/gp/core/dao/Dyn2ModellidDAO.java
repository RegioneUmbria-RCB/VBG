package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface Dyn2ModellidDAO extends BaseDAO<Dyn2Modellid, PkId> {

    /**
     * Ritorna la massima riga salvata sul DB filtrado per modelli T
     * 
     * @param dyn2Modellit
     * @return
     */
    public Integer findMaxRigaByModelloT(Dyn2Modellit dyn2Modellit);

    /**
     * Ritorna la massima colonna salvata sul DB filtrado per righe e modelli T
     * 
     * @param dyn2Modellit
     * @return
     */
    public Integer findMaXColonnaByRigaModelloDAndModelloT(Dyn2Modellit dyn2Modellit, Dyn2Modellid dyn2Modellid);
}
