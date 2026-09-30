package it.gruppoinit.pal.gp.core.features.segnaposto.legacy.rtf;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.DatiOggettoLettera;

public interface ISostituzioniSegnapostoRTFLegacyService {

    byte[] effettuaSostituzioniBaseRtf(DocumentMergeHelper documentMergeHelper, DatiOggettoLettera oggettoLettera,
	    IUsefulDataForPlaceholderReplacement data);
}
