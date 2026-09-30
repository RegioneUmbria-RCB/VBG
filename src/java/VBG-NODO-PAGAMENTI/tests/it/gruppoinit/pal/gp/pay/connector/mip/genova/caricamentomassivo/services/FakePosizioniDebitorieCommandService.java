package it.gruppoinit.pal.gp.pay.connector.mip.genova.caricamentomassivo.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;

public class FakePosizioniDebitorieCommandService implements PosizioniDebitorieCommandService {

    private String codiceVersamento;
    private String valoreUnicoParametro;

    public FakePosizioniDebitorieCommandService(String codiceVersamento, String valoreUnicoParametro) {

	this.codiceVersamento = codiceVersamento;
	this.valoreUnicoParametro = valoreUnicoParametro;
    }

    @Override
    public PosizioniDebitorieCommand popolaPosizioniDebitorie(List<PayRegistrazioniContabili> registrazioniPosizioni, String idRichiesta) {

	PosizioniDebitorieCommand cmd = PosizioniDebitorieCommand.getCommand(idRichiesta);
	List<Integer> lista = new ArrayList<>(Arrays.asList(2379, 2380, 2381, 2382));
	for (Integer id : lista) {
	    PayRegistrazioniContabili registrazioni = new PayRegistrazioniContabili();
	    registrazioni.setId(new PkId(id));
	    cmd.getRegistrazioniPosizioni().add(registrazioni);
	}
	return cmd;
    }

    @Override
    public String findValoreUnicoParametroFromCommand(PosizioniDebitorieCommand cmd, IParameter parametro) throws PayConfigurationException {

	return this.valoreUnicoParametro;
    }

    @Override
    public String findCodiceVersamentoFromCommand(PosizioniDebitorieCommand cmd) throws PayConfigurationException {

	return this.codiceVersamento;
    }

    @Override
    public String findCodiceVersamentoFromPosizioneDebitoria(PayPosizioniDebitorie payPos) throws PayConfigurationException {

	return this.codiceVersamento;
    }

    @Override
    public String findValoreUnicoParametroFromPosizioneDebitoria(PayPosizioniDebitorie payPos, IParameter parametro) throws PayException {

	return this.valoreUnicoParametro;
    }
}
