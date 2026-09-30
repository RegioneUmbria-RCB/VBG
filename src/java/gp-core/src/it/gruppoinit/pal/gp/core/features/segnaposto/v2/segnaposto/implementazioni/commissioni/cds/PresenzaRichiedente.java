package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class PresenzaRichiedente extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String PRESENZARICHIEDENTE = "PRESENZARICHIEDENTE";

    @Override
    public String getNome() {

	return PRESENZARICHIEDENTE;
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	// non più supportato
	return "";
    }
    //	//cds.invitorichiedente
    //	if (placeholder.equalsIgnoreCase("PRESENZARICHIEDENTE")) {
    //	    String val = "";
    //	    if (data.getCds() != null) {
    //		val = data.getCds().getInvitorichiedente() ? "Si" : "No";
    //	    }
    //	    return val;
    //	}
}
