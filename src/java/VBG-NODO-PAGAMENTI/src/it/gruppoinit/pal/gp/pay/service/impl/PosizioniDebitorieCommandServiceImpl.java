package it.gruppoinit.pal.gp.pay.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.pay.command.CausaliRaggruppateBean;
import it.gruppoinit.pal.gp.pay.command.PosizioniDebitorieCommand;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.service.PayDettaglioImportiService;
import it.gruppoinit.pal.gp.pay.service.PosizioniDebitorieCommandService;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;

@Service
public class PosizioniDebitorieCommandServiceImpl implements PosizioniDebitorieCommandService {

    private PayDettaglioImportiService payDettaglioImportiService;

    @Autowired
    public void setPayDettaglioImportiService(PayDettaglioImportiService payDettaglioImportiService) {

	this.payDettaglioImportiService = payDettaglioImportiService;
    }

    @Override
    public PosizioniDebitorieCommand popolaPosizioniDebitorie(List<PayRegistrazioniContabili> registrazioniPosizioni, String idRichiesta) {

	PosizioniDebitorieCommand command = PosizioniDebitorieCommand.getCommand(idRichiesta);
	Map<CausaliRaggruppateBean, List<InfoCausaleBean>> mappaInfoCausali = payDettaglioImportiService.findMappaCausali(registrazioniPosizioni);
	command.getMappaInfoCausali().clear();
	command.getMappaInfoCausali().putAll(mappaInfoCausali);
	command.getRegistrazioniPosizioni().addAll(registrazioniPosizioni);
	return command;
    }

    @Override
    public String findValoreUnicoParametroFromCommand(PosizioniDebitorieCommand cmd, IParameter parametro) throws PayConfigurationException {

	List<InfoCausaleBean> list = new ArrayList<>();
	for (Entry<CausaliRaggruppateBean, List<InfoCausaleBean>> infoCausaleBean : cmd.getMappaInfoCausali().entrySet()) {
	    list.addAll(infoCausaleBean.getValue());
	}
	if (list.isEmpty()) {
	    throw new PayConfigurationException("Verificare la configurazione delle causali. Nessun parametro specificato");
	}
	return findValoreUnicoParametroMappaCausali(list, parametro);
    }

    @Override
    public String findValoreUnicoParametroFromPosizioneDebitoria(PayPosizioniDebitorie payPos, IParameter parametro) throws PayException {

	Map<CausaliRaggruppateBean, List<InfoCausaleBean>> mappaCausali = payDettaglioImportiService.findMappaCausaliPosizioneDebitoria(payPos);
	if (mappaCausali.entrySet().size() == 1) {
	    return findValoreUnicoParametroMappaCausali(mappaCausali.entrySet().iterator().next().getValue(), parametro);
	}
	return null;
    }

    @Override
    public String findCodiceVersamentoFromCommand(PosizioniDebitorieCommand cmd) throws PayConfigurationException {

	Set<String> codiciConfigurati = new HashSet<>();
	for (Entry<CausaliRaggruppateBean, List<InfoCausaleBean>> infoCausaleBean : cmd.getMappaInfoCausali().entrySet()) {
	    if (StringUtils.isNotBlank(infoCausaleBean.getKey().getCodiceVersamento())) {
		codiciConfigurati.add(infoCausaleBean.getKey().getCodiceVersamento());
	    }
	}
	if (codiciConfigurati.size() != 1) {
	    throw new PayConfigurationException(
		    "Verificare la configurazione delle causali. Codice versamento non è configurato correttamente. Codici Configurati " +
						codiciConfigurati);
	}
	return codiciConfigurati.iterator().next();
    }

    @Override
    public String findCodiceVersamentoFromPosizioneDebitoria(PayPosizioniDebitorie payPos) throws PayConfigurationException {

	String valore = null;
	String valoreConcatenato = "";
	List<PayDettaglioImporti> importi = payDettaglioImportiService.findByIdPosizioneDebitoria(payPos.getId().getCodice());
	int numParametriDaTrovare = importi.size();
	for (PayDettaglioImporti pdi : importi) {
	    String codiceVersamento = pdi.getCausaleRegistrazione().getCodiceVersamento();
	    if (StringUtils.isBlank(valore)) {
		valore = codiceVersamento;
	    }
	    valoreConcatenato += codiceVersamento;
	}
	String resultDaVerificare = StringUtils.repeat(valore, numParametriDaTrovare);
	if (!resultDaVerificare.equalsIgnoreCase(valoreConcatenato)) {
	    throw new PayConfigurationException(
		    "Verificare la configurazione delle causali. Codice versamento non è configurato correttamente. Risultano salvati valori differenti");
	}
	return valore;
    }

    private String findValoreUnicoParametroMappaCausali(List<InfoCausaleBean> list, IParameter parametro) throws PayConfigurationException {

	String nomeParametro = parametro.getNomeParametro();
	int numParametriDaTrovare = list.size();
	int numParametriTrovati = 0;
	boolean trovato = false;
	String valore = null;
	String valoreConcatenato = "";
	for (InfoCausaleBean icb : list) {
	    Set<IParameter> params = icb.getParams();
	    for (IParameter p : params) {
		if (p.getNomeParametro().equalsIgnoreCase(nomeParametro)) {
		    if (!trovato) {
			valore = p.getValore();
			trovato = true;
		    }
		    valoreConcatenato += p.getValore();
		    numParametriTrovati++;
		}
	    }
	}
	if (!trovato) {
	    return null;
	}
	if (numParametriDaTrovare != numParametriTrovati) {
	    throw new PayConfigurationException("Verificare la configurazione delle causali. Il parametro " + nomeParametro +
						" non è configurato correttamente. Il parametro è configurato in " + numParametriTrovati + " dei " +
						numParametriDaTrovare + " parametri.");
	}
	String resultDaVerificare = StringUtils.repeat(valore, numParametriDaTrovare);
	if (!resultDaVerificare.equalsIgnoreCase(valoreConcatenato)) {
	    throw new PayConfigurationException("Verificare la configurazione delle causali. Il parametro " + nomeParametro +
						" non è configurato correttamente. Risultano salvati valori differenti");
	}
	return valore;
    }
}
