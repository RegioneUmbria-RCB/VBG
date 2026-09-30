package it.gruppoinit.service;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;

public interface DTOService {

    /**
     * Recupera dalla sezione altri dati di InserimentoAttivitaNLARequest i campi per popolare l'oggetto
     * InserisciDeterminaHelper
     * 
     * @param inserimentoAttivitaNLARequest
     * @return
     */
    public InserisciDeterminaHelper inserimentoAttivitaNLARequestToInserisciDeterminaHelper(InserimentoAttivitaNLARequest request);

    /**
     * Recupera dalla sezione altri dati di InserimentoAttivitaNLARequest i campi per popolare l'oggetto LeggiAttoHelper
     * 
     * @param inserimentoAttivitaNLARequest
     * @return
     */
    public LeggiAttoHelper inserimentoAttivitaNLARequestToLeggiAttoHelper(InserimentoAttivitaNLARequest request);
    //    public InserimentoAttivitaNLAResponse attiResponseToInserimentoAttivitaNLAResponse(InserimentoAttivitaNLARequest request);
    //    
    //   
    //    public InserimentoAttivitaNLAResponse attiresponseToInserimentoAttivitaNLAResponse(InserimentoAttivitaNLARequest request);
}
