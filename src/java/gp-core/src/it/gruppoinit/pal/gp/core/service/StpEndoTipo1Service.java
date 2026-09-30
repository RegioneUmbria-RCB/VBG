package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.StpEndoTipo1DAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;

import java.util.List;

public interface StpEndoTipo1Service extends BaseService<StpEndoTipo1, PkId> {

    public StpEndoTipo1 findbyStpCodice(Integer stpCodice);

    public StpEndoTipo1 findByInventarioProcedimenti(Inventarioprocedimenti inventarioprocedimenti);

    /**
     * @see StpEndoTipo1DAO#findbySoftware(String)
     */
    public List<StpEndoTipo1> findBySoftware(String software);

    /**
     * @see StpEndoTipo1DAO#verificaSchedeEndo1()
     * @return
     */
    public List<StpEndoTipo1> verificaSchedeEndo1();

    public StpEndoTipo1 findByCodiceEndoRegionale(String codReg);

    /**
     * <pre>
     * Verifica se il procedimento è del cart, se eiste ritorna l'oggetto; altrimenti null. La logica per la verifca è: 
     * a.Esiste un record in StpEndoTipo1.
     * b.StpEndoTipo1 ha un associazione con la tabella Oggetti.
     * 
     * @param codiceIntervento
     * </pre>
     */
    public StpEndoTipo1 isProcedimentoCART(Integer codiceProcedimento);

    public List<StpEndoTipo1> findListByInventarioProcedimenti(Integer codiceendo);

    public List<StpEndoTipo1> findByCodiceRegionale(String codiceEndoRegionale);

    public int count();
    
    public List<StpEndoTipo1> findAllByStpCodice(Integer stpCodice);
}
