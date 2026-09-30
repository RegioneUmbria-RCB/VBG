package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeManager;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagrafeManagerImpl implements AnagrafeManager {

    private static final Logger log = LoggerFactory.getLogger(AnagrafeManagerImpl.class);
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    private void writeToReport(String codice, String message) {

	ChiaveValoreBean<String, String> reportCVB = new ChiaveValoreBean<String, String>();
	reportCVB.setChiave(codice);
	reportCVB.setValore(Utilities.formatDate(new Date(), true) + " - " + message);
	report.put(ORMHelper.getIdcomuneAlias(), reportCVB);
    }

    @Override
    public void updateAllineaPersoneGiuridiche() {

	inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.TRUE);
	this.stopAllineamento = false;
	FilterTable ft = getPersoneGiuridicheAttiveFT();
	int count = anagrafeService.countRecord(ft);
	double pageNumber = 0;
	double conta = 0;
	int startRow = 0;
	int rowEnd;
	if (log.isDebugEnabled()) {
	    log.debug("updateAllineaPersoneGiuridiche# {} record da aggiornare", count);
	}
	if (count > 0) {
	    if (count < pageSize) {
		pageNumber = 1;
	    } else {
		conta = Double.valueOf(count) / Double.valueOf(pageSize);
		pageNumber = Math.ceil(conta);
	    }
	    List<Anagrafe> listAnagrafe = null;
	    String cfPiva = null;
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * (Double.valueOf(pageSize).intValue());
		rowEnd = (Double.valueOf(pageSize).intValue());
		writeToReport("01", "Persone Giuridiche:  record da aggiornare " + count + ", processo la pagina " + i + "dal record " + startRow
			+ " al record " + rowEnd);
		if (log.isDebugEnabled()) {
		    log.debug("updateAllineaPersoneGiuridiche# record da aggiornare {}, processo la pagina {} dal record {} al record {}",
			    new Object[] { count, i, startRow, rowEnd });
		}
		listAnagrafe = anagrafeService.findByFilterTable(ft, startRow, rowEnd);
		for (Anagrafe anagrafe : listAnagrafe) {
		    try {
			if (stopAllineamento == true) {
			    writeToReport("99", "Persone Giuridiche: La procedura è stata fermata");
			    log.error("updateAllineaPersoneGiuridiche# richiesto lo stop dell'allineamento");
			    inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.FALSE);
			    return;
			}
			if (log.isDebugEnabled()) {
			    log.debug("updateAllineaPersoneGiuridiche# cerco di aggiornare l'anagrafe {}", anagrafe.getDescrizioneRichiedente());
			}
			cfPiva = StringUtils.defaultString(anagrafe.getCodicefiscale()).trim();
			Anagrafe mod = null;
			if (StringUtils.isNotBlank(cfPiva)) {
			    mod = anagrafeService.findAnagrafeAggiornataByCF(anagrafe);
			} else {
			    cfPiva = StringUtils.defaultString(anagrafe.getPartitaiva()).trim();
			    if (StringUtils.isNotBlank(cfPiva)) {
				mod = anagrafeService.findAnagrafeAggiornataByPI(anagrafe);
			    }
			}
			if (mod != null) {
			    anagrafeService.update(mod);
			}
		    } catch (Exception e) {
			log.error("updateAllineaPersoneGiuridiche# errore nell''aggiornamento dell''anagrafe {}", e);
		    }
		}
	    }
	}
	inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.FALSE);
	writeToReport("99", "Terminata la procedura di allineamento di " + count + " anagrafiche persone giuridiche");
    }

    @Override
    public void updateAllineaPersoneFisiche() {

	inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.TRUE);
	this.stopAllineamento = false;
	FilterTable ft = getPersoneFisicheAttiveFT();
	int count = anagrafeService.countRecord(ft);
	double pageNumber = 0;
	double conta = 0;
	int startRow = 0;
	int rowEnd = 0;
	if (log.isDebugEnabled()) {
	    log.debug("updateAllineaPersoneFisiche# {} record da aggiornare", count);
	}
	if (count > 0) {
	    if (count < pageSize) {
		pageNumber = 1;
	    } else {
		conta = Double.valueOf(count) / Double.valueOf(pageSize);
		pageNumber = Math.ceil(conta);
	    }
	    List<Anagrafe> listAnagrafe = null;
	    String cf = null;
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * (Double.valueOf(pageSize).intValue());
		rowEnd = (Double.valueOf(pageSize).intValue());
		writeToReport("01", "Persone Fisiche:  record da aggiornare " + count + ", processo la pagina " + i + "dal record " + startRow
			+ " al record " + rowEnd);
		if (log.isDebugEnabled()) {
		    log.debug("updateAllineaPersoneFisiche# record da aggiornare {}, processo la pagina {} dal record {} al record {}", new Object[] {
			    count, i, startRow, rowEnd });
		}
		listAnagrafe = anagrafeService.findByFilterTable(ft, startRow, rowEnd);
		for (Anagrafe anagrafe : listAnagrafe) {
		    try {
			if (stopAllineamento == true) {
			    writeToReport("01", "Persone Fisiche: La procedura è stata fermata");
			    log.error("updateAllineaPersoneFisiche# richiesto lo stop dell'allineamento");
			    inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.FALSE);
			    return;
			}
			if (log.isDebugEnabled()) {
			    log.debug("updateAllineaPersoneFisiche# cerco di aggiornare l'anagrafe {}", anagrafe.getDescrizioneRichiedente());
			}
			cf = StringUtils.defaultString(anagrafe.getCodicefiscale()).trim();
			Anagrafe mod = null;
			if (StringUtils.isNotBlank(cf)) {
			    mod = anagrafeService.findAnagrafeAggiornataByCF(anagrafe);
			}
			if (mod != null) {
			    anagrafeService.update(mod);
			}
		    } catch (Exception e) {
			log.error("updateAllineaPersoneGiuridiche# errore nell''aggiornamento dell''anagrafe {}", e);
		    }
		}
	    }
	}
	inElaborazione.put(ORMHelper.getIdcomuneAlias(), Boolean.FALSE);
	writeToReport("99", "Terminata la procedura di allineamento di " + count + " anagrafiche persone fisiche");
    }

    private int pageSize = 50;

    private FilterTable getPersoneGiuridicheAttiveFT() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipoanagrafe", WebConstants.PERSONA_GIURIDICA, String.class));
	FilterRestriction dis = new FilterRestriction();
	dis.setAndOrRestriction(AndOrRestriction.OR);
	dis.addFilterField(FilterUtils.equals("flagDisabilitato", Integer.valueOf(0), Integer.class));
	dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	FilterRestriction pivaOCf = new FilterRestriction();
	pivaOCf.setAndOrRestriction(AndOrRestriction.OR);
	pivaOCf.addFilterField(FilterUtils.isNotNull("codicefiscale"));
	pivaOCf.addFilterField(FilterUtils.isNotNull("partitaiva"));
	ft.addRestriction(fr);
	ft.addRestriction(dis);
	ft.addRestriction(pivaOCf);
	return ft;
    }

    private FilterTable getPersoneFisicheAttiveFT() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipoanagrafe", WebConstants.PERSONA_FISICA, String.class));
	FilterRestriction dis = new FilterRestriction();
	dis.setAndOrRestriction(AndOrRestriction.OR);
	dis.addFilterField(FilterUtils.equals("flagDisabilitato", Integer.valueOf(0), Integer.class));
	dis.addFilterField(FilterUtils.isNull("flagDisabilitato"));
	FilterRestriction cf = new FilterRestriction();
	cf.addFilterField(FilterUtils.isNotNull("codicefiscale"));
	ft.addRestriction(fr);
	ft.addRestriction(dis);
	ft.addRestriction(cf);
	return ft;
    }

    private boolean stopAllineamento = false;

    public void setStopAllineamento(boolean stopAllineamento) {

	this.stopAllineamento = stopAllineamento;
    }

    @Override
    public void updateAllineaTuttaAnagrafe() {

	this.stopAllineamento = false;
	if (log.isDebugEnabled()) {
	    log.debug("updateAllineaTuttaAnagrafe# inizio allineamento persone giuridiche");
	}
	updateAllineaPersoneGiuridiche();
	if (log.isDebugEnabled()) {
	    log.debug("updateAllineaTuttaAnagrafe# inizio allineamento persone fisiche");
	}
	if (this.stopAllineamento == false) {
	    updateAllineaPersoneFisiche();
	}
	if (log.isDebugEnabled()) {
	    log.debug("updateAllineaTuttaAnagrafe# fine...");
	}
	writeToReport("99", "Terminato l'allineamento di tutte le anagrafiche");
    }

    @Override
    public void fermaAllineamentoAnagrafiche() {

	this.stopAllineamento = true;
    }

    private Map<String, ChiaveValoreBean<String, String>> report = new HashMap<String, ChiaveValoreBean<String, String>>();

    @Override
    public ChiaveValoreBean<String, String> reportAllineamento() {

	ChiaveValoreBean<String, String> result = new ChiaveValoreBean<String, String>();
	result.setChiave("00");
	result.setValore("Nessuna operazione in corso");
	if (report != null) {
	    result = report.get(ORMHelper.getIdcomuneAlias());
	}
	return result;
    }

    private Map<String, Boolean> inElaborazione = new HashMap<String, Boolean>();

    @Override
    public boolean elaborazioneInCorso() {

	Boolean isInCorso = inElaborazione.get(ORMHelper.getIdcomuneAlias());
	if (isInCorso != null) {
	    return isInCorso.booleanValue();
	}
	return false;
    }
}
