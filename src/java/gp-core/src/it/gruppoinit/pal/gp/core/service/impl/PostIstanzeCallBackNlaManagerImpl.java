package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.service.IPostIstanzeInsertCallBack;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.NlaManager;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;

public class PostIstanzeCallBackNlaManagerImpl implements IPostIstanzeInsertCallBack {

    private NlaManager nlaManager;
    private InserimentoPraticaNLARequest request;
    private IstanzeeventiService istanzeeventiService;

    public PostIstanzeCallBackNlaManagerImpl(NlaManager nlaManager, InserimentoPraticaNLARequest request, IstanzeeventiService istanzeeventiService) {

	this.nlaManager = nlaManager;
	this.request = request;
	this.istanzeeventiService = istanzeeventiService;
    }

    @Override
    public void callback(Istanze istanza) throws OperazioniAutomaticheException {

	try {
	    nlaManager.gestioneApplicazioneMappatureSchedeDinamiche(istanza, request);
	} catch (Exception e) {
	    istanzeeventiService.insert("Errore nella mappatura delle schede dinamiche: " + e.getMessage(),
		    IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, null, istanza);
	    throw new OperazioniAutomaticheException(e.getMessage(), e);
	}
    }
}
