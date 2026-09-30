package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigComune;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoConfigComuneDAO extends BaseDAO<BorsellinoConfigComune, PkId> {

    BorsellinoConfigComune findByCodiceComune(String comune);

    DettaglioComuneModel findAbbonamentoConfigComune(String codiceComune);
}
