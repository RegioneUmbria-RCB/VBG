package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;

public interface UpgrFromCdsDAO extends BaseDAO {

    UpgrDaMigrareBean findCdsDaMigrare();

    CdsReport migraCDS(CdsDaMigrareBean cdsDaMigrareBean, List<CdsTipologieInserite> codicetipologia, List<CdsCaricheInserite> cariche);

    void migraMenuSuSoftwareTT();

    void migraCommediTipopareriMov();
}
