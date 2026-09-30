package it.gruppoinit.pal.gp.core.features.segnaposto.v2.registro;

import it.gruppoinit.pal.gp.core.features.infrastructure.packages.IPackageScannerService;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISegnaposto;

public interface IRegistroSegnaposto {

    ISegnaposto getSegnaposto(String nome, boolean haArgomenti);

    void inizializza(IPackageScannerService packageScanner);

    int getNumeroSegnapostoRegistrati();

    boolean isInizializzato();
}
