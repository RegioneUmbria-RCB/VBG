package it.gruppoinit.pal.gp.core.features.sistema.upgr;

import java.util.List;

public interface IMySQLTipimovimentoUtfBinService {

    List<String> upgrCollateUtf8();

    List<String> upgrTipiMovimentiDoppi();
}
