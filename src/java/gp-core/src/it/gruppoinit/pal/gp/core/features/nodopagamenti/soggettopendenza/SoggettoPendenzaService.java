package it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;

public interface SoggettoPendenzaService {

    Anagrafe getSoggettoPendenza(SoggettiPendenzaEnum soggettoPendenza, Istanzeoneri istoneri);

    boolean isAzienda(SoggettiPendenzaEnum isAzienda);
}
