package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;

public abstract class SegnapostoTestualeBaseConValoreSingolo extends SegnapostoTestualeBase {

    @Override
    protected String onSostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	return this.onGetValore(argomenti, data, userData);
    }

    @Override
    protected String onSostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	return this.onGetValore(argomenti, data, userData);
    }

    protected abstract String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData);
}
