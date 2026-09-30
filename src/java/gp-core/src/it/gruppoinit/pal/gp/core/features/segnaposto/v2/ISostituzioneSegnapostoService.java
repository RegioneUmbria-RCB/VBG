package it.gruppoinit.pal.gp.core.features.segnaposto.v2;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public interface ISostituzioneSegnapostoService {

    IOdtSubstitution sostituisciOdt(TextDocument document, StrutturaSegnaposto struttura, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData);

    IRtfSubstitution sostituisciRtf(StrutturaSegnaposto strutturaSegnaposto, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper documentMergeHelper);
}
