package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoInformativeDAO extends BaseDAO<BorsellinoInformative, PkId> {

    List<BorsellinoInformative> findByCodiceComune(String codiceComune);

    List<BorsellinoInformative> findByCodiceComuneAttive(String codiceComune);

    void deleteById(Integer id);
}
