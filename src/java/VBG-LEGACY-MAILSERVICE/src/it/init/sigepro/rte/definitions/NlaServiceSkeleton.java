/**
 * NlaServiceSkeleton.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.1 Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package it.init.sigepro.rte.definitions;

import it.gruppoinit.mailservice.ws.NlaService;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.XsdTypesVersion;

/**
 * NlaServiceSkeleton java skeleton for the axisService
 */
public class NlaServiceSkeleton implements NlaServiceSkeletonInterface {

    private final String err = "Il nodo NLA MAILSERVICE non dispone del servizio: ";

    /**
     * Auto generated method signature
     * 
     * @param inserimentoPraticaNLARequest0
     * @return inserimentoPraticaNLAResponse1
     */
    public it.init.sigepro.rte.InserimentoPraticaNLAResponse inserimentoPraticaNLA(
	    it.init.sigepro.rte.InserimentoPraticaNLARequest inserimentoPraticaNLARequest0) {

	InserimentoPraticaNLAResponse resp = new InserimentoPraticaNLAResponse();
	DettaglioPraticaType pratica = inserimentoPraticaNLARequest0.getDettaglioPratica();
	RiferimentiPraticaType rif = new RiferimentiPraticaType();
	rif.setIdPratica(pratica.getIdPratica());
	rif.setNumeroPratica(pratica.getNumeroPratica());
	rif.setDataPratica(pratica.getDataPratica());
	resp.setDettaglioPratica(rif);
	return resp;
    }

    /**
     * Auto generated method signature
     * 
     * @param richiestaPraticheListaNLARequest2
     * @return richiestaPraticheListaNLAResponse3
     */
    public it.init.sigepro.rte.RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(
	    it.init.sigepro.rte.RichiestaPraticheListaNLARequest richiestaPraticheListaNLARequest2) {

	throw new java.lang.UnsupportedOperationException(err + "richiestaPraticheListaNLA");
    }

    /**
     * Auto generated method signature
     * 
     * @param allegatoBinarioNLARequest4
     * @return allegatoBinarioNLAResponse5
     */
    public it.init.sigepro.rte.AllegatoBinarioNLAResponse allegatoBinarioNLA(it.init.sigepro.rte.AllegatoBinarioNLARequest allegatoBinarioNLARequest4) {

	throw new java.lang.UnsupportedOperationException(err + "allegatoBinarioNLA");
    }

    /**
     * Auto generated method signature
     * 
     * @param aggiungiDocumentiNLARequest6
     * @return aggiungiDocumentiNLAResponse7
     */
    public it.init.sigepro.rte.AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(
	    it.init.sigepro.rte.AggiungiDocumentiNLARequest aggiungiDocumentiNLARequest6) {

	throw new java.lang.UnsupportedOperationException(err + "aggiungiDocumentiNLA");
    }

    /**
     * Auto generated method signature
     * 
     * @param testNLARequest8
     * @return testNLAResponse9
     */
    public it.init.sigepro.rte.TestNLAResponse testNLA(it.init.sigepro.rte.TestNLARequest testNLARequest8) {

	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }

    /**
     * Auto generated method signature
     * 
     * @param richiestaPraticaNLARequest10
     * @return richiestaPraticaNLAResponse11
     */
    public it.init.sigepro.rte.RichiestaPraticaNLAResponse richiestaPraticaNLA(
	    it.init.sigepro.rte.RichiestaPraticaNLARequest richiestaPraticaNLARequest10) {

	throw new java.lang.UnsupportedOperationException(err + "richiestaPraticaNLA");
    }

    /**
     * Auto generated method signature
     * 
     * @param inserimentoAttivitaNLARequest12
     * @return inserimentoAttivitaNLAResponse13
     */
    public it.init.sigepro.rte.InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(
	    it.init.sigepro.rte.InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest12) {

	NlaService nlaService = new NlaService();
	return nlaService.inserimentoAttivitaNLA(inserimentoAttivitaNLARequest12);
    }
}
