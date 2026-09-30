package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;

public class Note extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "NOTE";
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

	CommissioniedilizieT cds = commissioniToCdsDAO.findUltimaCommissionePerIstanza(data);
	if (cds != null) {
	    return StringUtils.defaultString(cds.getNote());
	}
	return "";
    }
    //  //cds.oraconvocazione2
    //  	if (placeholder.equalsIgnoreCase("TAG")) {
    //  	    String val = "";
    //  	    if (data.getCds() != null) {
    //  		val = FormatUtils.stringFormat(data.getCds().getNote());
    //  	    }
    //  	    return val;
    //  	}
}
