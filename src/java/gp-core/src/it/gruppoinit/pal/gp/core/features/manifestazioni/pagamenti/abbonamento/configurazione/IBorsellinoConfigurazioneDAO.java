package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.TreeSet;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface IBorsellinoConfigurazioneDAO extends BaseDAO<BorsellinoConfigurazione, PkId> {

    BorsellinoConfigurazione findConfigurazione();

    TreeSet<ButtonComuneModel> findComuni();
}
