package it.gruppoinit.service.impl;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.gruppoinit.domain.helper.LeggiAttoPlusHelper;
import it.gruppoinit.service.AttiService;
import it.gruppoinit.ws.client.atti.AttiWSServiceClient;
import it.gruppoinit.ws.wsatti.AttoOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttiServiceImpl implements AttiService {

    @Autowired
    private AttiWSServiceClient attiWSServiceClient;

    @Override
    public it.gruppoinit.ws.wsatti.inserisciattistring.AttoInseritoOut inserisciDetermina(InserisciDeterminaHelper inserisciDeterminaHelper,
	    String token) {

	return attiWSServiceClient.inserisciDeterminaString(inserisciDeterminaHelper, token);
    }

    @Override
    public AttoOut leggiAtto(LeggiAttoHelper leggiAttoHelper) {

	return attiWSServiceClient.leggiAtto(leggiAttoHelper);
    }

    @Override
    public it.gruppoinit.ws.wsatti.leggiattoplus.attoout.AttoOut leggiAttoPlus(LeggiAttoPlusHelper leggiAttoPlusHelper) {

	return attiWSServiceClient.leggiAttoPlus(leggiAttoPlusHelper);
    }
}
