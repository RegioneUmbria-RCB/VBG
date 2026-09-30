package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.TempLinkallegati;
import it.gruppoinit.pal.gp.core.features.infrastructure.ioc.interfaces.IOCKernel;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoDTO;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.SegnapostoTestualeBaseConValoreSingolo;
import it.gruppoinit.pal.gp.core.service.TempLinkallegatiService;

public class ConteggioAllegatiHash extends SegnapostoTestualeBaseConValoreSingolo {

    private MovimentiZipLogicoService zipLogicoService;
    private TempLinkallegatiService tempLinkallegatiService;
    public static final String TAG = "CONTEGGIO_ALLEGATI_HASH";

    @Override
    public String getNome() {

	return "CONTEGGIO_ALLEGATI_HASH";
    }

    @Override
    public boolean haArgomenti() {

	return false;
    }

    @Override
    public void inizializzaServizi(IOCKernel kernel) throws ClassNotFoundException {

	this.zipLogicoService = kernel.getBeanOfType(MovimentiZipLogicoService.class);
	this.tempLinkallegatiService = kernel.getBeanOfType(TempLinkallegatiService.class);
    }

    @Override
    protected String onGetValore(String[] argomenti, IUsefulDataForPlaceholderReplacement data, DocumentMergeHelper userData) {

	int cnt = 0;
	int codiceMovimento = data.getMovimento().getId().getCodice();
	List<MovimentiZipLogicoDTO> zipLogico = this.zipLogicoService.findMovimentiZipLogicoDTOByMovimento(codiceMovimento);
	String uuid = userData.getUuidLinkTemp();
	List<TempLinkallegati> list = tempLinkallegatiService.findByUuid(uuid);
	if (!zipLogico.isEmpty()) {
	    for (MovimentiZipLogicoDTO zipallegati : zipLogico) {
		if (zipallegati.getCodiceOggetto() != null) {
		    cnt++;
		}
	    }
	}
	if (!list.isEmpty()) {
	    for (TempLinkallegati tempLinkallegati : list) {
		if (tempLinkallegati.getCodiceoggetto() != null) {
		    cnt++;
		}
	    }
	}
	return String.valueOf(cnt);
    }
}
