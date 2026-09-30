package it.gruppoinit.stc.ws;

import javax.jws.WebService;

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
import it.init.sigepro.rte.definitions.Nla;

@WebService(targetNamespace = "http://sigepro.init.it/rte/definitions", name = "Nla", serviceName = "NlaService", portName = "NlaSoap11", endpointInterface = "it.init.sigepro.rte.definitions.Nla")
public class NlaMock implements Nla {

    @Override
    public InserimentoPraticaNLAResponse inserimentoPraticaNLA(InserimentoPraticaNLARequest inserimentoPraticaNLARequest) {

	return null;
    }

    @Override
    public RichiestaPraticheListaNLAResponse richiestaPraticheListaNLA(RichiestaPraticheListaNLARequest richiestaPraticheListaNLARequest) {

	return null;
    }

    @Override
    public AllegatoBinarioNLAResponse allegatoBinarioNLA(AllegatoBinarioNLARequest allegatoBinarioNLARequest) {

	return null;
    }

    @Override
    public TestNLAResponse testNLA(TestNLARequest testNLARequest) {

	return null;
    }

    @Override
    public RichiestaPraticaNLAResponse richiestaPraticaNLA(RichiestaPraticaNLARequest richiestaPraticaNLARequest) {

	return null;
    }

    @Override
    public InserimentoAttivitaNLAResponse inserimentoAttivitaNLA(InserimentoAttivitaNLARequest inserimentoAttivitaNLARequest) {

	return null;
    }

    @Override
    public AggiungiDocumentiNLAResponse aggiungiDocumentiNLA(AggiungiDocumentiNLARequest aggiungiDocumentiNLARequest) {

	return null;
    }
}
