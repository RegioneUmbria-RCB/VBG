package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.NatureProcedure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;

public interface NatureProcedureService extends BaseService<NatureProcedure, PkId> {

    public static enum NATURA_BASE_ENUM {
	comunicazione, ordinario, scia
    };

    /**
     * torna tutti i record del software corrente
     */
    @Override
    public List<NatureProcedure> findAll(Integer firstResult, Integer maxResult);

    public List<NatureProcedure> findByTipiprocedure(Integer codiceprocedura, Integer firstResult, Integer maxResult);

    /**
     * Cerca la procedura per il software corrente e la naturabase passata
     * 
     * @param codicenaturabase
     * @return
     */
    public Tipiprocedure findByNatura(String codicenaturabase);
}
