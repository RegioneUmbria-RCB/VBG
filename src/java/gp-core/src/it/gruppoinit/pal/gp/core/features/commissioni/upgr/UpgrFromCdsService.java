package it.gruppoinit.pal.gp.core.features.commissioni.upgr;

import java.util.List;

public interface UpgrFromCdsService {

    List<CdsReport> eseguiUpgr();

    void migraMenuSuSoftwareTT();

    void migraCommediTipopareriMov();
}
