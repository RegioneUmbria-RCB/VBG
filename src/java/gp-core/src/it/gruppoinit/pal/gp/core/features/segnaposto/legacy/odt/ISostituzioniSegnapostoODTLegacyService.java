package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.odt;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.DatiOggettoLettera;

public interface ISostituzioniSegnapostoODTLegacyService {

    byte[] effettuaSostituzioniBaseOdt(DocumentMergeHelper userData, DatiOggettoLettera oggettoLettera, IUsefulDataForPlaceholderReplacement data);
}
