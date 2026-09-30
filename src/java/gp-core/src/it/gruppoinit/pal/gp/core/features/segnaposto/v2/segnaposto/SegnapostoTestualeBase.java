package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IOdtSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IRtfSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.OdtTextSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.RtfTextSubstitution;

public abstract class SegnapostoTestualeBase implements ISegnaposto {

    private TipoFileEnum tipoFile;

    protected TipoFileEnum getTipoFile() {

	return tipoFile;
    }

    @Override
    public final IOdtSubstitution sostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	this.tipoFile = TipoFileEnum.ODT;
	String valore = onSostituisciOdt(currentDocument, argomenti, data, userData);
	return new OdtTextSubstitution(valore);
    }

    @Override
    public IRtfSubstitution sostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	this.tipoFile = TipoFileEnum.RTF;
	String valore = FormatUtils.stringFormat(onSostituisciRtf(argomenti, data, userData));
	return new RtfTextSubstitution(valore);
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

    }

    protected abstract String onSostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData);

    protected abstract String onSostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData);
}
