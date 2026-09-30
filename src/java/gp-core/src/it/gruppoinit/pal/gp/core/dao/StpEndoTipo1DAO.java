package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;

public interface StpEndoTipo1DAO extends BaseDAO<StpEndoTipo1, PkId> {

    public StpEndoTipo1 findbyStpCodice(Integer stpCodice);

    public StpEndoTipo1 findByInventarioProcedimenti(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * Trova tutti i record legati al modulo software tramite endoprocedimento
     * 
     * @param software
     * @return
     */
    public List<StpEndoTipo1> findbySoftware(String software);

    /**
     * Verifica le schede degli endo di tipo 1 e torna una lista di quelle che non hanno una scheda di spiegazione
     * associata ordinata per famiglia endo, tipi endo endo
     * 
     * @return
     */
    public List<StpEndoTipo1> verificaSchedeEndo1();
    
    public List<StpEndoTipo1> findAllByStpCodice(Integer stpCodice);
}
