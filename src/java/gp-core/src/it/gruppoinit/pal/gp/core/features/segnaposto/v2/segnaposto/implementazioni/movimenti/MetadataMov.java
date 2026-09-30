package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.movimenti;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.movimenti.metadati.IMovimentiMetadatiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;

public class MetadataMov extends SegnapostoTestualeBaseConValoreSingolo {

    private static final String MD_MOV = "MD_MOV";
    private IMovimentiMetadatiService movimentiMetadatiService;

    @Override
    public String getNome() {

	return MD_MOV;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.movimentiMetadatiService = kernel.getBeanOfType(IMovimentiMetadatiService.class);
    }

    @Override
    public boolean haArgomenti() {

	return true;
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	Integer codiceMovimento = valida(argomenti, data);
	if (codiceMovimento == null) {
	    return "";
	}
	String argomento = argomenti[0];
	if (StringUtils.isNotBlank(argomento)) {
	    MovimentiMetadati md = movimentiMetadatiService.findById(new MovimentiMetadatiId(codiceMovimento, argomento.trim()));
	    if (md != null) {
		return md.getValore();
	    }
	}
	return "";
    }

    private Integer valida(String[] argomenti, IUsefulDataForPlaceholderReplacement data) {

	if (argomenti == null || argomenti.length == 0) {
	    return null;
	}
	if (data.getMovimento() == null || data.getMovimento().getId() == null || data.getMovimento().getId().getCodice() == null) {
	    return null;
	}
	return data.getMovimento().getId().getCodice();
    }
}
