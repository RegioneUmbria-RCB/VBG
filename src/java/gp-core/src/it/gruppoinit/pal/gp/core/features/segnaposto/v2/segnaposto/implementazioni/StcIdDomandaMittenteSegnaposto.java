package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class StcIdDomandaMittenteSegnaposto extends SegnapostoTestualeBaseConValoreSingolo {

    @Override
    public String getNome() {

	return "IDDOMANDAMITTENTE";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	if (data.getIstanza() != null && data.getIstanza().getDomandestcs() != null && data.getIstanza().getDomandestcs().size() > 0) {
	    return data.getIstanza().getDomandestcs().iterator().next().getIdDomandamitt();
	}
	return null;
    }
}
