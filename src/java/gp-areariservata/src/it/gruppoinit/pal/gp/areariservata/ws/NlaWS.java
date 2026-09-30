package it.gruppoinit.pal.gp.areariservata.ws;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.NlaService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.TestNLARequest;
import it.init.sigepro.rte.TestNLAResponse;
import it.init.sigepro.rte.XsdNlaVersion;
import it.init.sigepro.rte.definitions.Nla;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DettaglioPraticaVisuraType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.XsdTypesVersion;

import java.util.Properties;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(targetNamespace = "http://sigepro.init.it/rte/definitions", name = "Nla", serviceName = "NlaService", portName = "NlaSoap11", endpointInterface = "it.init.sigepro.rte.definitions.Nla")
public class NlaWS extends BaseWS implements Nla {

    private static final Logger log = LoggerFactory.getLogger(NlaWS.class);
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private NlaService nlaService;

    @Override
    public RichiestaPraticaNLAResponse richiestaPraticaNLA(RichiestaPraticaNLARequest request) {

	log.debug("richiestaPraticaNLA");
	RiferimentiPraticaType rifPratica = request.getRifPratica();
	RichiestaPraticaNLAResponse richiestaPraticaNLAResponse = new RichiestaPraticaNLAResponse();
	DettaglioPraticaVisuraType dettaglioPraticaVisuraType = new DettaglioPraticaVisuraType();
	DettaglioPraticaType dettaglioPraticaType = new DettaglioPraticaType();
	dettaglioPraticaType.setIdPratica(rifPratica.getIdPratica());
	dettaglioPraticaType.setNumeroPratica(rifPratica.getIdPratica());
	dettaglioPraticaType.setDataPratica(Utilities.getToday());
	dettaglioPraticaVisuraType.setDettaglioPratica(dettaglioPraticaType);
	richiestaPraticaNLAResponse.setDettaglioPratica(dettaglioPraticaVisuraType);
	return richiestaPraticaNLAResponse;
    }

    @Override
    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest request) {

	log.error("AREA RISERVATA: inserimentoAttivitaNLA Not implemented");
	throw new RuntimeException("AREA RISERVATA: inserimentoAttivitaNLA Not implemented");
    }

    @Override
    public InserimentoPraticaNLAResponse inserimentoPraticaNLA(InserimentoPraticaNLARequest request) {

	log.error("AREA RISERVATA: inserimentoPraticaNLA Not implemented");
	throw new RuntimeException("AREA RISERVATA: inserimentoPraticaNLA Not implemented");
    }

    @Override
    public RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(RichiestaPraticheListaNLARequest request) {

	log.error("AREA RISERVATA: richiestaPraticheListaNLA Not implemented");
	throw new RuntimeException("AREA RISERVATA: richiestaPraticheListaNLA Not implemented");
    }

    @Override
    public AllegatoBinarioNLAResponse allegatoBinarioNLA(AllegatoBinarioNLARequest request) {

	String idcomunealias = request.getSportelloDestinatario().getIdEnte();
	Properties connProps = externalDBResolver.getConnectionProperties(idcomunealias);
	setORMHelper(request.getSportelloDestinatario().getIdSportello(), connProps.getProperty(WebConstants.TOKEN));
	try {
	    return nlaService.richiestaAllegato(request);
	} catch (Exception e) {
	    log.error("allegatoBinarioNLA(): {}", e.getMessage(), e);
	    throw new RuntimeException(e);
	} finally {
	    resetThreadLocalVars();
	}
    }

    @Override
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(AggiungiDocumentiNLARequest arg0) {

	log.error("AREA RISERVATA: aggiungiDocumentiNLA Not implemented");
	throw new RuntimeException("AREA RISERVATA: aggiungiDocumentiNLA Not implemented");
    }

    public TestNLAResponse testNLA(TestNLARequest request) {

	log.debug("testNLA");
	TestNLAResponse response = new TestNLAResponse();
	response.setNlaXsdVersion(XsdNlaVersion.V_1_13);
	response.setTypesXsdVersion(XsdTypesVersion.V_1_13);
	return response;
    }
}
