package it.gruppoinit.stc.service;

import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;


public interface NlaService {

    public InserimentoPraticaNLAResponse inserimentoPratica(InserimentoPraticaNLARequest request);

    public InserimentoAttivitaNLAResponse inserimentoAttivita(InserimentoAttivitaNLARequest request);

    public AllegatoBinarioNLAResponse richiestaAllegato(AllegatoBinarioNLARequest request);

    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request);
}
