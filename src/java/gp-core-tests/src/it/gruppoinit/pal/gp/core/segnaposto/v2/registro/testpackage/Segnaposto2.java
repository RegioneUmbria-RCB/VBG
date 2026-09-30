package it.gruppoinit.pal.gp.core.segnaposto.v2.registro.testpackage;

import org.odftoolkit.simple.TextDocument;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBase;

public class Segnaposto2 extends SegnapostoTestualeBase {

    @Override
    public String getNome() {

	return "SEGNAPOSTO2";
    }

    @Override
    public boolean haArgomenti() {

	return true;
    }

    @Override
    public String onSostituisciOdt(TextDocument currentDocument, String[] argomenti, IUsefulDataForPlaceholderReplacement data,
	    DocumentMergeHelper userData) {

	// TODO Auto-generated method stub
	return "segnaposto 2";
    }

    @Override
    protected String onSostituisciRtf(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	// TODO Auto-generated method stub
	return null;
    }
}
