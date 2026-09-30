package it.gruppoinit.pal.gp.core.features.manifestazioni.comunicazioni.massive;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeDMassive;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IMercatipresenzeDMassiveDAO extends BaseDAO<MercatipresenzeDMassive, PkId> {

    Integer findIdPresenzaByDettaglio(Integer idMercatipresenzeDMassive);

    MercatipresenzeDMassive findByDettaglio(Integer idMassivaDettaglio);
}
