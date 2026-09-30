package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared;

import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public interface IOdtUtilsService {

    String odtBonificaValueForODT(String valuePalceHolder);

    String odtIsImmagineForODT(String valuePalceHolder, String placeholder, IUsefulDataForPlaceholderReplacement data);
}
