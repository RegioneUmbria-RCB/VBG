package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IOdtSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.IRtfSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.ISegnaposto;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.OdtMultipleTextSubstitution;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.RtfMultipleTextSubstitution;

public abstract class SegnapostoTestualeBaseConValoreMultiplo implements ISegnaposto {

    private TipoFileEnum tipoFile;

    public TipoFileEnum getTipoFile() {

	return tipoFile;
    }

    @Override
    public IOdtSubstitution sostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	this.tipoFile = TipoFileEnum.ODT;
	return new OdtMultipleTextSubstitution(onGetValori(argomenti, data, userData), currentDocument);
    }

    @Override
    public IRtfSubstitution sostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	this.tipoFile = TipoFileEnum.RTF;
	return new RtfMultipleTextSubstitution(onGetValori(argomenti, data, userData));
    }

    protected abstract String[] onGetValori(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData);
}
