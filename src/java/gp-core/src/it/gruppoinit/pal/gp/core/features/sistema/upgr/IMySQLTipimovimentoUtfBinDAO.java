package it.gruppoinit.pal.gp.core.features.sistema.upgr;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

public interface IMySQLTipimovimentoUtfBinDAO extends BaseDAO {

    List<String> upgrCollateUtf8();

    List<String> upgrTipiMovimentiDoppi();
}
