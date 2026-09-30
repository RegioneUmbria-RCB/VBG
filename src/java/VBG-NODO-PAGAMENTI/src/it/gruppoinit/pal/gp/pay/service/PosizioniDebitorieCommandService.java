package it.gruppoinit.pal.gp.pay.service;

import java.util.List;

import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;

public interface PosizioniDebitorieCommandService {

    PosizioniDebitorieCommand popolaPosizioniDebitorie(List<PayRegistrazioniContabili> registrazioniPosizioni, String idRichiesta);

    public String findValoreUnicoParametroFromCommand(PosizioniDebitorieCommand cmd, IParameter parametro) throws PayConfigurationException;

    public String findCodiceVersamentoFromCommand(PosizioniDebitorieCommand cmd) throws PayConfigurationException;

    public String findCodiceVersamentoFromPosizioneDebitoria(PayPosizioniDebitorie payPos) throws PayConfigurationException;

    String findValoreUnicoParametroFromPosizioneDebitoria(PayPosizioniDebitorie payPos, IParameter parametro) throws PayException;
}
