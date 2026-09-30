package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class Data2ConvEstesa extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "DATA2CONVESTESA";
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
    //	//cds.dataconvocazione2 estesa
    //	if (placeholder.equalsIgnoreCase("DATA2CONVESTESA")) {
    //	    String val = "";
    //	    DateFormat extendedFormat = new SimpleDateFormat("'anno' yyyy, 'il giorno' dd 'del mese di' MMM", Locale.ITALY);
    //	    //TODO non sarebbe meglio una cosa tipo "lunedì 24 settembre 2001"? DateFormat extendedFormat = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.ITALY);
    //	    if (data.getCds() != null) {
    //		val = FormatUtils.dateFormat(data.getCds().getDataconvocazione2(), extendedFormat);
    //	    }
    //	    return val;
    //	}
}
