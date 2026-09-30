package it.gruppoinit.pal.gp.core.dao;

import java.util.List;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

public interface StpEndoTipo2DAO extends BaseDAO<StpEndoTipo2, PkId> {

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice, String tipo);

    public StpEndoTipo2 findbyStpCodice(Integer stpCodice);

    public StpEndoTipo2 findbyAlberoproc(Integer codiceAlberoproc);

    /**
     * Trova tutti i record legati al modulo software tramite endoprocedimento e al tipo ricercato. Se tipo non è
     * settato o e nullo allora ricerca indipendentemente da questo parametro
     * 
     * @param software
     * @param tipo
     *            il tipo di record da estrarre
     * @return
     */
    @Deprecated
    public List<StpEndoTipo2> findBySoftwareAndTipo(String software, String tipo);

    public List<Integer> findCodiciBySoftwareAndTipo(String software, String tipo);

    /**
     * Verifica le schede degli endo di tipo 2 e torna una lista di quelle che non hanno una scheda di spiegazione
     * associata ordinata per vw_alberoproc.sc_descrizione
     * 
     * @return
     */
    public List<StpEndoTipo2> verificaSchedeEndo2();

    public Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> findListaAttivitaCartOrdinate();

    public List<StpEndoTipo2> findAllByStpCodice(Integer stpCodice, String tipo);

    public List<StpEndoTipo2> findByTipoSortByStpCodice(String tipo, boolean sortAsc);

    public List<Integer> findListCategorieAndAttivita();
}
