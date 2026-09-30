package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class CdsOra2Conv extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "CDSORA2CONV";
    // private CommissioniToCdsDAO commissioniToCdsDAO;

    @Override
    public String getNome() {

	return TAG;
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	// this.commissioniToCdsDAO = kernel.getBeanOfType(CommissioniToCdsDAO.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	return "";
    }
    //	if (placeholder.equalsIgnoreCase("CDSORA2CONV")) {
    //    String val = "";
    //    if (data.getCds() != null) {
    //	val = FormatUtils.stringFormat(data.getCds().getOraconvocazione2());
    //    }
    //    return val;
    //}
}
