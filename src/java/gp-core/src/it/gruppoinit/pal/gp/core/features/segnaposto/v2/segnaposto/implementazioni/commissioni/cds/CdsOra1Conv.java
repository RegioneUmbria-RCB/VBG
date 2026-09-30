package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.DataOraBean;

public class CdsOra1Conv extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "CDSORA1CONV";
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

	DataOraBean cds = commissioniToCdsDAO.findConvocazioneUltimaCommissionePerIstanza(data);
	if (cds != null) {
	    return StringUtils.defaultString(cds.getOra());
	}
	return "";
    }
    //	//cds.oraconvocazione
    //	if (placeholder.equalsIgnoreCase("CDSORA1CONV")) {
    //	    String val = "";
    //	    if (data.getCds() != null) {
    //		val = FormatUtils.stringFormat(data.getCds().getOraconvocazione());
    //	    }
    //	    return val;
    //	}
}
