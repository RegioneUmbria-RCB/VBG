package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.IComunicazioniBollettazioneService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.IComunicazioniCommissioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.IComunicazioniGenService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;

@Service
public class ComunicazioniMassiveServiceFactoryImpl implements IComunicazioniMassiveServiceFactory {

    @SuppressWarnings("rawtypes")
    private List<IComunicazioniMassiveService> services = new ArrayList<IComunicazioniMassiveService>(0);

    @Autowired
    public ComunicazioniMassiveServiceFactoryImpl(IComunicazioniBollettazioneService comunicazioniBollettazioneService,
	    IComunicazioniCommissioniService comunicazioniCommissioniService,
	    IComunicazioniManifestazioniService comunicazioniManifestazioniService,
	    @Qualifier("comunicazioniMercServiceImpl") IComunicazioniGenService comunicazioniMassiveMercService,
	    @Qualifier("comunicazioniIstServiceImpl") IComunicazioniGenService comunicazioniIstServiceImpl) {

	this.services.add(comunicazioniBollettazioneService);
	this.services.add(comunicazioniCommissioniService);
	this.services.add(comunicazioniManifestazioniService);
	
	this.services.add(comunicazioniMassiveMercService);
	this.services.add(comunicazioniIstServiceImpl);
    }

    @SuppressWarnings("rawtypes")
    public IComunicazioniMassiveService getService(Integer idComunicazione) {

	if (idComunicazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile ricavare il service corretto per la gestione delle comunicazioni senza passare l'id della comunicazione");
	}
	for (IComunicazioniMassiveService service : this.services) {
	    if (service.exists(idComunicazione)) {
		return service;
	    }
	}
	throw new IllegalArgumentException("Non è stato trovato nessun service che gestisce la comunicazione con id " + idComunicazione);
    }
}
