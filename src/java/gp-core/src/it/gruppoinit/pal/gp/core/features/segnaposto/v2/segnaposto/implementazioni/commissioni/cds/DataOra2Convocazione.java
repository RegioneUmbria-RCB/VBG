package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class DataOra2Convocazione extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "DATAORA2CONVOCAZ";
    private CommissioniToCdsDAO commissioniToCdsDAO;

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

	this.commissioniToCdsDAO = kernel.getBeanOfType(CommissioniToCdsDAO.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	return "";
    }
    //	//cds.dataconvocazione2 + cds.oraconvocazione2
    //	if (placeholder.equalsIgnoreCase("DATAORA2CONVOCAZ")) {
    //	    StringBuilder val = new StringBuilder();
    //	    if (data.getCds() != null) {
    //		val.append(FormatUtils.dateFormat(data.getCds().getDataconvocazione2()));
    //		if (StringUtils.isNotBlank(data.getCds().getOraconvocazione2())) {
    //		    val.append(" alle ore ").append(data.getCds().getOraconvocazione2().trim());
    //		}
    //	    }
    //	    return val.toString();
    //	}
}
