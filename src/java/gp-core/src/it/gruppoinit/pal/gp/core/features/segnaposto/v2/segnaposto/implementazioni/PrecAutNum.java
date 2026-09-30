package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.mercati.ReplaceVariabiliMercatoDAO;

public class PrecAutNum extends SegnapostoTestualeBaseConValoreSingolo {

    private ReplaceVariabiliMercatoDAO replaceVariabiliMercatoDAO;

    @Override
    public String getNome() {

	return "PREC_AUT_NUM";
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.replaceVariabiliMercatoDAO = kernel.getBeanOfType(ReplaceVariabiliMercatoDAO.class);
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	if (data.getIstanza() == null || data.getIstanza().getId() == null || data.getIstanza().getId().getCodice() == null) {
	    return "";
	}
	return StringUtils.defaultString(
		replaceVariabiliMercatoDAO.sostituisciSegnaposto(data.getIstanza().getId().getCodice(), getTipoFile()).getPrecAutNum());
    }
}
