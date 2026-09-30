package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.util.List;
import java.util.Map;

public interface StpEndoTipo2DAO extends BaseDAO<StpEndoTipo2, PkId> {

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo, String idcomune);

    public StpEndoTipo2 findbyAlberoproc(String idcomune, Integer codiceAlberoproc);

    /**
     * Trova tutti i record legati al modulo software tramite endoprocedimento e al tipo ricercato. Se tipo non è
     * settato o e nullo allora ricerca indipendentemente da questo parametro
     * 
     * @param software
     * @param tipo
     *            il tipo di record da estrarre
     * @param idcomune
     * @return
     */
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo, String idcomune);

    /**
     * Trova tutti i record legati al modulo software tramite endoprocedimento e al tipo ricercato e al flag_regionale.
     * Se tipo non è settato o e nullo allora ricerca indipendentemente da questo parametro
     * 
     * @param software
     * @param tipo
     * @param flagRegionale
     * @return
     */
    public List<StpEndoTipo2> findBySoftwareAndTipoAndFlagRegionale(String software, String tipo, Boolean flagRegionale, String idcomune);

    /**
     * Verifica le schede degli endo di tipo 2 e torna una lista di quelle che non hanno una scheda di spiegazione
     * associata ordinata per vw_alberoproc.sc_descrizione
     * 
     * @return
     */
    public List<StpEndoTipo2> verificaSchedeEndo2(String idcomune);

    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate(String idcomune);
}
