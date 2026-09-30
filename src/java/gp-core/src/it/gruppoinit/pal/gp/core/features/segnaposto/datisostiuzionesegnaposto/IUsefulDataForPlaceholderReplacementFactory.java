package it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;

public interface IUsefulDataForPlaceholderReplacementFactory {

    IUsefulDataForPlaceholderReplacement create(Istanze istanza, Movimenti movimento);
}
