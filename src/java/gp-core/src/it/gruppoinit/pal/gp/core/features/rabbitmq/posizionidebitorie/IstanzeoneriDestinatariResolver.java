package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;

public class IstanzeoneriDestinatariResolver extends DestinatariResolver implements IDestinatariDettPosizioneDebitoriaResolver {

    private DettPosizioneDebitoria dettPosizioneDebitoria;
    private IstanzeoneriService istanzeoneriService;

    public IstanzeoneriDestinatariResolver(IstanzeoneriService istanzeoneriService, IstanzeService istanzeService,
	    IstanzerichiedentiService istanzerichiedentiService, AnagrafeService anagrafeService, DettPosizioneDebitoria dettPosizioneDebitoria) {

	super(istanzeService, istanzerichiedentiService, anagrafeService);
	this.istanzeoneriService = istanzeoneriService;
	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    @Override
    public CodiciFiscaliDestinatariBean getDestinatariPersoneFisiche() {

	Integer codiceIstanza = istanzeoneriService.findCodiceIstanzaByDettPosDebitoria(dettPosizioneDebitoria.getId().getCodice());
	if (codiceIstanza == null) {
	    return new CodiciFiscaliDestinatariBean();
	}
	return calcolaDestinatari(codiceIstanza);
    }
}
