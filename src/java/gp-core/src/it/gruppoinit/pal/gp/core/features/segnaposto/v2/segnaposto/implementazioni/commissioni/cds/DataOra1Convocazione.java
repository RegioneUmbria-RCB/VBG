package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.CommissioniToCdsDAO;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao.DataOraBean;

public class DataOra1Convocazione extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "DATAORA1CONVOCAZ";
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
	    if (cds.getData() != null) {
		StringBuilder val = new StringBuilder();
		val.append(FormatUtils.dateFormat(cds.getData()));
		if (StringUtils.isNotBlank(cds.getOra())) {
		    val.append(" alle ore ").append(cds.getOra());
		}
		return val.toString();
	    }
	}
	return "";
    }
    //    if (placeholder.equalsIgnoreCase("DATAORA1CONVOCAZ")) {
    //	    StringBuilder val = new StringBuilder();
    //	    if (data.getCds() != null) {
    //		val.append(FormatUtils.dateFormat(data.getCds().getDataconvocazione()));
    //		if (StringUtils.isNotBlank(data.getCds().getOraconvocazione())) {
    //		    val.append(" alle ore ").append(data.getCds().getOraconvocazione().trim());
    //		}
    //	    }
    //	    return val.toString();
    //	}
}
