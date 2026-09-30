package it.gruppoinit.pal.gp.pay.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.pay.parameters.IParameter;
import it.gruppoinit.pal.gp.pay.parameters.ParametroAnnoAccertamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroDatiRiscossione;
import it.gruppoinit.pal.gp.pay.parameters.ParametroNumeroAccertamento;
import it.gruppoinit.pal.gp.pay.parameters.ParametroNumeroSottoAccertamento;
import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.service.RegistrazioniContabiliConverterService;
import it.gruppoinit.pal.gp.pay.service.helper.InfoCausaleBean;
import it.gruppoinit.pal.gp.pay.ws.schema.DettaglioImportoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.RateizzazioneType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileWsInType;

@Service
public class RegistrazioniContabiliConverterServiceImpl implements RegistrazioniContabiliConverterService {

    @Autowired
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;
    private static final Logger log = LoggerFactory.getLogger(RegistrazioniContabiliConverterServiceImpl.class);

    @Override
    public RegistrazioneContabileType completaRegistrazioneContabile(RegistrazioneContabileWsInType regContabileWsIn) {

	log.debug("completaRegistrazioneContabile() - Rimappo la registrazioneContabileWsIn in Registrazione contabile {}",
		regContabileWsIn.getDescrizione());
	RegistrazioneContabileType out = new RegistrazioneContabileType();
	out.setAnno(regContabileWsIn.getAnno());
	out.setData(regContabileWsIn.getData());
	out.setDescrizione(regContabileWsIn.getDescrizione());
	out.setImporto(calcolaImporto(regContabileWsIn));
	out.setNote(regContabileWsIn.getNote());
	out.setRate(convertToRate(regContabileWsIn));
	out.setSoggettoDebitore(regContabileWsIn.getSoggettoDebitore());
	return out;
    }

    private RateizzazioneType convertToRate(RegistrazioneContabileWsInType regContabileWsIn) {

	RateizzazioneType rateizzazioneType = new RateizzazioneType();
	for (PosizioneDebitoriaWsInType rata : regContabileWsIn.getRate()) {
	    rateizzazioneType.getRata().add(convertPosizioneDebitoria(rata));
	}
	return rateizzazioneType;
    }

    private PosizioneDebitoriaType convertPosizioneDebitoria(PosizioneDebitoriaWsInType rata) {

	PosizioneDebitoriaType pd = new PosizioneDebitoriaType();
	pd.setDataScadenza(rata.getDataScadenza());
	pd.setDescrizione(rata.getDescrizione());
	pd.setImporto(getImportiPosizioneDebitoria(rata));
	pd.setNumeroRata(rata.getNumeroRata());
	pd.setRiferimentoClient(rata.getRiferimentiClient());
	return pd;
    }

    private DettaglioImportoType getImportiPosizioneDebitoria(PosizioneDebitoriaWsInType rata) {

	DettaglioImportoType di = new DettaglioImportoType();
	for (ImportoPagamentoWsInType imp : rata.getImporti()) {
	    di.getComponenteImporto().add(convertImporto(imp));
	}
	return di;
    }

    private ImportoPagamentoType convertImporto(ImportoPagamentoWsInType imp) {

	ImportoPagamentoType ipt = new ImportoPagamentoType();
	ipt.setImporto(imp.getImporto());
	InfoCausaleBean info = payRegistrazioniCausaliService.findInfoCausale(imp.getCodiceMappatura());
	ipt.setAnnoAccertamento(getAnnoAccertamento(info.getParams()));
	ipt.setDatiRiscossione(getDatiRiscossione(info.getParams()));
	ipt.setDescrizioneCausale(info.getDescrizione());
	ipt.setNumeroAccertamento(getNumeroAccertamento(info.getParams()));
	ipt.setNumeroSottoAccertamento(getNumeroSottoAccertamento(info.getParams()));
	ipt.setCodiceMappatura(imp.getCodiceMappatura());
	return ipt;
    }

    private String getStringOrDefaultFromParams(Set<IParameter> params, String nomeParametro, String defaultVal) {

	if (params == null || params.isEmpty()) {
	    return defaultVal;
	}
	for (IParameter p : params) {
	    if (StringUtils.defaultString(p.getNomeParametro()).trim().equalsIgnoreCase(nomeParametro)) {
		if (StringUtils.isNotBlank(p.getValore())) {
		    return p.getValore();
		}
	    }
	}
	return defaultVal;
    }

    private String getNumeroSottoAccertamento(Set<IParameter> params) {

	return getStringOrDefaultFromParams(params, new ParametroNumeroSottoAccertamento().getNomeParametro(), null);
    }

    private String getNumeroAccertamento(Set<IParameter> params) {

	return getStringOrDefaultFromParams(params, new ParametroNumeroAccertamento().getNomeParametro(), null);
    }

    private String getDatiRiscossione(Set<IParameter> params) {

	return getStringOrDefaultFromParams(params, new ParametroDatiRiscossione().getNomeParametro(), null);
    }

    private Integer getAnnoAccertamento(Set<IParameter> params) {

	String anno = getStringOrDefaultFromParams(params, new ParametroAnnoAccertamento().getNomeParametro(), "").trim();
	if (StringUtils.isBlank(anno) || !Utilities.isInteger(anno)) {
	    return null;
	}
	return Integer.parseInt(anno);
    }

    private BigDecimal calcolaImporto(RegistrazioneContabileWsInType regContabileWsIn) {

	BigDecimal impTotale = BigDecimal.ZERO;
	for (PosizioneDebitoriaWsInType rata : regContabileWsIn.getRate()) {
	    List<ImportoPagamentoWsInType> importi = rata.getImporti();
	    for (ImportoPagamentoWsInType importoBean : importi) {
		impTotale = impTotale.add(importoBean.getImporto());
	    }
	}
	return impTotale;
    }
}
