package it.gruppoinit.service.impl;

import it.gruppoinit.domain.helper.InserisciDeterminaHelper;
import it.gruppoinit.domain.helper.LeggiAttoHelper;
import it.gruppoinit.service.DTOService;
import it.gruppoinit.utilities.Campi;
import it.gruppoinit.utilities.Utilities;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class DTOServiceImpl implements DTOService {

    @Override
    public InserisciDeterminaHelper inserimentoAttivitaNLARequestToInserisciDeterminaHelper(InserimentoAttivitaNLARequest request) {

	InserisciDeterminaHelper determinaHelper = new InserisciDeterminaHelper();
	String trattamento = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_TRATTAMENTO), "");
	determinaHelper.setTrattamento(trattamento);
	String proponente = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_PROPONENTE), "");
	determinaHelper.setProponente(proponente);
	String dirigente = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_DIRIGENTE), "");
	determinaHelper.setDirigente(dirigente);
	String classifica = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_CLASSIFICA), "");
	determinaHelper.setClassifica(classifica);
	String utente = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_UTENTE), "");
	determinaHelper.setUtente(utente);
	String tipo = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_TIPO), "");
	determinaHelper.setTipo(tipo);
	String ruolo = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_RUOLO), "");
	determinaHelper.setRuolo(ruolo);
	String oggetto = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_OGGETTO), "");
	determinaHelper.setOggetto(oggetto);
	if (request.getDatiAttivita() != null && !request.getDatiAttivita().getDocumenti().isEmpty()) {
	    determinaHelper.setDocumenti(request.getDatiAttivita().getDocumenti());
	}
	determinaHelper.setToken(request.getToken());
	return determinaHelper;
    }

    @Override
    public LeggiAttoHelper inserimentoAttivitaNLARequestToLeggiAttoHelper(InserimentoAttivitaNLARequest request) {

	LeggiAttoHelper attoHelper = new LeggiAttoHelper();
	String idDocumento = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_ID_DOCUMENTO), "");
	attoHelper.setIdDocumento(idDocumento);
	String organo = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_ORGANO), "");
	attoHelper.setOrgano(organo);
	String anno = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_ANNO), "");
	attoHelper.setAnno(anno);
	String numero = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_NUMERO), "");
	attoHelper.setNumero(numero);
	String utente = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_UTENTE), "");
	attoHelper.setUtente(utente);
	String codiceAmm = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_CODICE_AMM), "");
	attoHelper.setCodiceAmm(codiceAmm);
	String codiceAOO = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_CODICE_AOO), "");
	attoHelper.setCodiceAOO(codiceAOO);
	String tipo = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_TIPO), "");
	attoHelper.setTipo(tipo);
	String ruolo = StringUtils.defaultIfEmpty(Utilities.getValoreAltroDato(request, Campi.CAMPO_RUOLO), "");
	attoHelper.setRuolo(ruolo);
	return attoHelper;
    }
}
