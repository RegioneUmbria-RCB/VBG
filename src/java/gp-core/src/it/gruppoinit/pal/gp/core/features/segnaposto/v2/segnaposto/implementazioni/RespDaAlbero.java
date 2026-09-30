package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class RespDaAlbero extends SegnapostoTestualeBaseConValoreSingolo {

    private AlberoprocService alberoprocService;

    @Override
    public String getNome() {

	return "RESPDAALBERO";
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
	if (data.getIstanza().getAlberoproc() == null || data.getIstanza().getAlberoproc().getId() == null
		|| data.getIstanza().getAlberoproc().getId().getCodice() == null) {
	    return "";
	}
	AlberoprocHelper alberoprocHelper = alberoprocService.findAlberoprocHelper(data.getIstanza().getAlberoproc().getId().getCodice());
	if (alberoprocHelper.getResponsabile() != null) {
	    return StringUtils.defaultString(alberoprocHelper.getResponsabile().getResponsabile());
	}
	return "";
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.alberoprocService = kernel.getBeanOfType(AlberoprocService.class);
    }
}
