package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.upgr;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IUpgrComunicazioniDAO extends BaseDAO {

    List<PkId> cercaLeComunicazioniSenzaTipoCom();
}
