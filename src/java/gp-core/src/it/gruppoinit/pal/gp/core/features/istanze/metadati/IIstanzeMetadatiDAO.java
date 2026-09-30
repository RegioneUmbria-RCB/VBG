package it.gruppoinit.pal.gp.core.features.istanze.metadati;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadatiId;

public interface IIstanzeMetadatiDAO extends BaseDAO<IstanzeMetadati, IstanzeMetadatiId> {

    void deleteByIstanza(Integer codiceIstanza);
}
