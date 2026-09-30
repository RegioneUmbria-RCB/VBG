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

public class Data1ConvEstesa extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String TAG = "DATA1CONVESTESA";
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
	    DateFormat extendedFormat = new SimpleDateFormat("'anno' yyyy, 'il giorno' dd 'del mese di' MMM", Locale.ITALY);
	    if (cds.getData() != null) {
		return FormatUtils.dateFormat(cds.getData(), extendedFormat);
	    }
	}
	return "";
    }
    //	//cds.dataconvocazione estesa
    //	if (placeholder.equalsIgnoreCase("DATA1CONVESTESA")) {
    //	    String val = "";
    //	    DateFormat extendedFormat = new SimpleDateFormat("'anno' yyyy, 'il giorno' dd 'del mese di' MMM", Locale.ITALY);
    //	    //TODO non sarebbe meglio una cosa tipo "lunedì 24 settembre 2001"? DateFormat extendedFormat = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.ITALY);
    //	    if (data.getCds() != null) {
    //		val = FormatUtils.dateFormat(data.getCds().getDataconvocazione(), extendedFormat);
    //	    }
    //	    return val;
    //	}
}
