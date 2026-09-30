package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni;

import it.gruppoinit.pal.gp.core.domain.helper.CommissioniTHelper;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;

public class CommediNumero extends SegnapostoTestualeBaseConValoreSingolo {

    private CommissioniedilizieTService commissioniedilizieTService;

    @Override
    public String getNome() {

	return "COMMEDI_NUMERO";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.commissioniedilizieTService = kernel.getBeanOfType(CommissioniedilizieTService.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	CommissioniTHelper comT = commissioniedilizieTService.findByCodiceIstanza(data.getIstanza().getId().getCodice());
	if (comT == null) {
	    return "";
	}
	return comT.getNumero();
    }
}
