package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class VariantePrg extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String VARIANTEAPRG = "VARIANTEAPRG";

    @Override
    public String getNome() {

	return VARIANTEAPRG;
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	// Non più supportato
	return "";
    }
    //	//cds.flagvia
    //	if (placeholder.equalsIgnoreCase("VARIANTEAPRG")) {
    //	    String val = "";
    //	    if (data.getCds() != null) {
    //		val = data.getCds().getFlagvia() ? "Si" : "No";
    //	    }
    //	    return val;
    //	}
}
