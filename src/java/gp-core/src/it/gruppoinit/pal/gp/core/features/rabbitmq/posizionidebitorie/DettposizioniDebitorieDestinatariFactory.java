package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import java.util.HashMap;
import java.util.Map;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.BollGestTestataService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;

public class DettposizioniDebitorieDestinatariFactory {

    private IstanzeoneriService istanzeoneriService;
    private IstanzeService istanzeService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private AnagrafeService anagrafeService;
    private BollGestTestataService bollGestTestataService;

    public DettposizioniDebitorieDestinatariFactory(IstanzeoneriService istanzeoneriService, IstanzeService istanzeService,
	    IstanzerichiedentiService istanzerichiedentiService, AnagrafeService anagrafeService, BollGestTestataService bollGestTestataService) {

	this.istanzeoneriService = istanzeoneriService;
	this.istanzeService = istanzeService;
	this.istanzerichiedentiService = istanzerichiedentiService;
	this.anagrafeService = anagrafeService;
	this.bollGestTestataService = bollGestTestataService;
    }

    public Map<String, IDestinatariDettPosizioneDebitoriaResolver> getResolvers(DettPosizioneDebitoriaProvenienzaEnum provenienzaEnum,
	    DettPosizioneDebitoria dettPosizioneDebitoria) {

	Map<String, IDestinatariDettPosizioneDebitoriaResolver> resolvers = new HashMap<String, IDestinatariDettPosizioneDebitoriaResolver>();
	switch (provenienzaEnum) {
	case ISTANZEONERI:
	    resolvers.put(DettPosizioneDebitoriaProvenienzaEnum.ISTANZEONERI.name(), new IstanzeoneriDestinatariResolver(istanzeoneriService,
		    istanzeService, istanzerichiedentiService, anagrafeService, dettPosizioneDebitoria));
	    break;
	case ABBONAMENTO:
	    resolvers.put(DettPosizioneDebitoriaProvenienzaEnum.ABBONAMENTO.name(), new AbbonamentiDestinatariResolver());
	    break;
	case BOLLETTAZIONE:
	    resolvers.put(DettPosizioneDebitoriaProvenienzaEnum.BOLLETTAZIONE.name(), new BollettazioniDestinatariResolver(istanzeoneriService,
		    istanzeService, istanzerichiedentiService, anagrafeService, bollGestTestataService, dettPosizioneDebitoria));
	    break;
	case MERCATIPRESENZE_D:
	    resolvers.put(DettPosizioneDebitoriaProvenienzaEnum.MERCATIPRESENZE_D.name(), new MercatipresenzeDDestinatariResolver());
	    break;
	default:
	    break;
	}
	return resolvers;
    }
}
