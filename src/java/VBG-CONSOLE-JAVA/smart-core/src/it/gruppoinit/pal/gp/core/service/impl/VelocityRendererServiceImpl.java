package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoLoc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.cart.ElenchiEndoFACCT;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.StpInventarioprocedimentiComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.StpCommand;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoLocService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.EndoRegioneToscanaService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VelocityRendererService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;

import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.EscapeTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class VelocityRendererServiceImpl implements VelocityRendererService {

    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private StpEndoTipo2Service stpEndoTipo2Service;
    @Autowired
    private StpEndoTipo1Service stpEndoTipo1Service;
    @Autowired
    private EndoRegioneToscanaService endoRegioneToscanaService;
    @Autowired
    private VelocityEngine templateEngine;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private AlberoprocEndoLocService alberoprocEndoLocService;
    @Autowired
    private UserSecurityService userSecurityService;
    private static Logger log = LoggerFactory.getLogger(VelocityRendererServiceImpl.class);

    private boolean populateModelPerEndo2(Integer codiceAlberoproc, Map<Object, Object> contextData, String codiceComune, String codificaEnteRfc53) {

	boolean isSchedaSpiegazione = false;
	if (log.isDebugEnabled()) {
	    log.debug("StpController schedaSpiegazioneEndo2: Codice Alberoproc= " + codiceAlberoproc);
	}
	StpCommand stpCommand = new StpCommand();
	Alberoproc alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceAlberoproc));
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyAlberoproc(ORMHelper.getIdcomunebase(), codiceAlberoproc);
	if (stpEndoTipo2 != null) {
	    try {
		/*
		 * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
		 */
		ORMHelper.setSoftware(alberoproc.getSoftware().getCodice());
		InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = null;
		// CartServiziDizionario csd = cartServiziDizionarioDAO.getCartServiziDizionario();
		String url = "";//csd.getUrlDownloadSchedaTipo2();
//		invioSchedaEndoTipo2 = cartInvioSchedaEndo2Service.downloadMessaggioSchedaEndo2(ORMHelper.getIdcomunebase(),
//			stpEndoTipo2.getCodiceStp(), url, codificaEnteRfc53, stpEndoTipo2.getCodiceEndoRegionale());
//		stpCommand.setInvioSchedaEndoTipo2(invioSchedaEndoTipo2);
		List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisPrima = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
		if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima() != null) {
		    List<Serializable> idEndo1ListPrima = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2()
			    .getElencoEndoRegionaliPrevistiPrima().getEndoTipo1AndEndoObbligatorio();
		    for (int i = 0; i < idEndo1ListPrima.size(); i += 2) {
			Serializable idEndo1 = idEndo1ListPrima.get(i);
			Serializable obbligatorio = idEndo1ListPrima.get(i + 1);
			if (idEndo1 instanceof BigInteger) {
			    StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(((BigInteger) idEndo1).intValue());
			    if (stpEndoTipo1 != null) {
				Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
				ChiaveValoreBean<Inventarioprocedimenti, Boolean> record = new ChiaveValoreBean<Inventarioprocedimenti, Boolean>();
				record.setChiave(inventarioprocedimenti);
				if (obbligatorio != null) {
				    if (obbligatorio instanceof Boolean) {
					record.setValore((Boolean) obbligatorio);
				    } else {
					record.setValore(Boolean.FALSE);
				    }
				} else {
				    record.setValore(Boolean.FALSE);
				}
				inventarioprocedimentisPrima.add(record);
			    }
			}
		    }
		    Collections.sort(inventarioprocedimentisPrima, new StpInventarioprocedimentiComparator());
		}
		stpCommand.setInventarioprocedimentisPrima(inventarioprocedimentisPrima);
		List<ChiaveValoreBean<Inventarioprocedimenti, Boolean>> inventarioprocedimentisDopo = new ArrayList<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>();
		if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo() != null) {
		    List<Serializable> idEndo1ListDopo = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo()
			    .getEndoTipo1AndEndoObbligatorio();
		    for (int i = 0; i < idEndo1ListDopo.size(); i += 2) {
			Serializable idEndo1 = idEndo1ListDopo.get(i);
			Serializable obbligatorio = idEndo1ListDopo.get(i + 1);
			if (idEndo1 instanceof BigInteger) {
			    StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(((BigInteger) idEndo1).intValue());
			    if (stpEndoTipo1 != null) {
				Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
				ChiaveValoreBean<Inventarioprocedimenti, Boolean> record = new ChiaveValoreBean<Inventarioprocedimenti, Boolean>();
				record.setChiave(inventarioprocedimenti);
				if (obbligatorio != null) {
				    if (obbligatorio instanceof Boolean) {
					record.setValore((Boolean) obbligatorio);
				    } else {
					record.setValore(Boolean.FALSE);
				    }
				} else {
				    record.setValore(Boolean.FALSE);
				}
				inventarioprocedimentisDopo.add(record);
			    }
			}
		    }
		    Collections.sort(inventarioprocedimentisDopo, new StpInventarioprocedimentiComparator());
		}
		stpCommand.setInventarioprocedimentisDopo(inventarioprocedimentisDopo);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataInizioValidita() != null) {
		    stpCommand.setDataInizioValidita(sdf.format(invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataInizioValidita()
			    .toGregorianCalendar().getTime()));
		}
		if (invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataFineValidita() != null) {
		    stpCommand.setDataFineValidita(sdf.format(invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getDataFineValidita()
			    .toGregorianCalendar().getTime()));
		}
		contextData.put("stpCommand", stpCommand);
		contextData.put("alberoproc", alberoproc);
		Map<String, String> adempimentiMap = getAdempimentiNormativeMap();
		contextData.put("adempimentiMap", adempimentiMap);
		isSchedaSpiegazione = true;
		return isSchedaSpiegazione;
	    } catch (Exception e) {
		log.error("Errore nel recupero delle informazioni della scheda di spiegazione per l'intervento {}, {}", codiceAlberoproc, e);
	    }
	}
	alberoproc = alberoprocService.findById(new PkId(ORMHelper.getIdcomunebase(), codiceAlberoproc));
	AlberoprocHelper helper = alberoprocService.findAlberoprocHelper(alberoproc, codiceComune);
	contextData.put("helper", helper);
	// String codiceComune = (String) request.getSession().getAttribute(WebConstants.COMUNE_SELEZIONATO_DOMANDA_ATTIVA_SESSION_VARIABLE_NAME);
	ElenchiEndoFACCT endos = endoRegioneToscanaService.getElenchiEndoPerAttivita(ORMHelper.getIdcomunebase(), codiceAlberoproc, null,
		codiceComune);
	contextData.put("endos", endos);
	return isSchedaSpiegazione;
    }

    @Override
    public String getHtmlEndo2(Integer codiceAlberoproc, Map<Object, Object> contextData, String codiceComune, String codificaEnteRfc53,
	    boolean soloSchedaregionale) {

	boolean isSchedaRegionale = populateModelPerEndo2(codiceAlberoproc, contextData, codiceComune, codificaEnteRfc53);
	if (soloSchedaregionale) {
	    if (!isSchedaRegionale) {
		return null;
	    }
	}
	String htmlModulo = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine,
		isSchedaRegionale ? "/dizionario/schedaSpiegazioneEndo2.vm" : "/dizionario/schedaIntervento.vm", FACCTConstants.DEFAULT_CHARSET,
		contextData);
	return htmlModulo;
    }

    private Map<String, String> getAdempimentiNormativeMap() {

	Map<String, String> a = new HashMap<String, String>();
	a.put("1", "Esercizio di attività");
	a.put("2", "Edilizi");
	a.put("3", "Ambientali");
	a.put("4", "Igienico-sanitari");
	a.put("5", "Prevenzione incendi");
	a.put("6", "Sicurezza");
	a.put("7", "Esercizio di attività");
	a.put("8", "Denuncia inizio attività per preparazione e/o somministrazione di alimenti e bevande");
	a.put("9", "Altri endoprocedimenti da inserire nel caso specifico");
	return a;
    }

    @Override
    public String getHtmlEndo1(String idcomuneinventario, Integer codiceinventario, Integer idAlberoproc, Map<Object, Object> contextData,
	    String codiceComune, String codificaEnteRfc53, boolean soloSchedaregionale, String idcomuneAPEndoLoc, String codiceAPEndoLoc) {

	StpCommand stpCommand = new StpCommand();
	Inventarioprocedimenti inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(idcomuneinventario, codiceinventario));
	stpCommand.setInventarioprocedimenti(inventarioprocedimenti);
	contextData.put("Utilities", new Utilities());
	contextData.put("descrizioneEndo", inventarioprocedimenti.getProcedimento());
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findByInventarioProcedimenti(idcomuneinventario, codiceinventario);
	if (StringUtils.isNotBlank(idcomuneAPEndoLoc) && StringUtils.isNotBlank(codiceAPEndoLoc)) {
	    if (Utilities.isInteger(codiceAPEndoLoc)) {
		PkId idAPEndoLoc = new PkId(idcomuneAPEndoLoc, Integer.parseInt(codiceAPEndoLoc));
		AlberoprocEndoLoc aaa = alberoprocEndoLocService.findById(idAPEndoLoc);
		if (aaa != null) {
		    if (StringUtils.isNotBlank(StringUtils.defaultString(aaa.getDescrizione()).trim())) {
			contextData.put("descrizioneEndo", aaa.getDescrizione());
		    }
		}
	    }
	}
	if (stpEndoTipo1 != null) {
	    /*
	     * Viene settato il software poichè la funzionalità non viene chiamata utilizzando il menù
	     */
	    ORMHelper.setSoftware(inventarioprocedimenti.getSoftware().getCodice());
	    // CartServiziDizionario csd = cartServiziDizionarioDAO.getCartServiziDizionario();
	    String url = "";//csd.getUrlDownloadSchedaTipo1();
	    try {
		//		InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = cartInvioSchedaEndo1Service.downloadMessaggioSchedaEndo1(ORMHelper.getIdcomunebase(),
		//			stpEndoTipo1.getCodiceStp(), url, codificaEnteRfc53, stpEndoTipo1.getCodiceEndoRegionale());
		//		stpCommand.setInvioSchedaEndoTipo1(invioSchedaEndoTipo1);
		//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//		if (!EntityUtils.isNestedPropertyBlank(invioSchedaEndoTipo1, "parteRegionaleSchedaEndoTipo1.dataInizioValidita")) {
		//		    stpCommand.setDataInizioValidita(sdf.format(invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getDataInizioValidita()
		//			    .toGregorianCalendar().getTime()));
		//		}
		//		if (!EntityUtils.isNestedPropertyBlank(invioSchedaEndoTipo1, "parteRegionaleSchedaEndoTipo1.dataFineValidita")) {
		//		    stpCommand.setDataFineValidita(sdf.format(invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getDataFineValidita()
		//			    .toGregorianCalendar().getTime()));
		//		}
		contextData.put("codice", stpEndoTipo1.getCodiceStp());
		contextData.put("stpCommand", stpCommand);
		Map<String, String> adempimentiMap = getAdempimentiNormativeMap();
		contextData.put("adempimentiMap", adempimentiMap);
		String htmlModulo = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, "/dizionario/schedaSpiegazioneEndo1.vm",
			FACCTConstants.DEFAULT_CHARSET, contextData);
		return htmlModulo;
	    } catch (Exception e) {
		log.error("Errore nel recupero delle informazioni della scheda di spiegazione per l'endoprocedimento {}, {}", codiceinventario, e);
	    }
	} else {
	    StpEndoTipo2 stpEndoTipo2 = null;
	    //se idalberoproc != null interrogo stpendotipo2 per codiceinventario e idalberoproc
	    //se alberoproc è null cerco di identificare stpendotipo2 solo per codiceinventario e solo se ce n'è uno solo
	    if (idAlberoproc != null) {
		stpEndoTipo2 = stpEndoTipo2Service.findByAlberoprocInventarioproc(idcomuneinventario, idAlberoproc, codiceinventario);
	    } else {
		List<StpEndoTipo2> endosTipo2 = stpEndoTipo2Service.findByInventarioproc(idcomuneinventario, codiceinventario);
		if (endosTipo2.size() == 1) {
		    stpEndoTipo2 = endosTipo2.get(0);
		}
	    }
	    if (stpEndoTipo2 != null) {
		if (stpEndoTipo2.getAlberoproc() != null) {
		    if (stpEndoTipo2.getAlberoproc().getId() != null) {
			contextData = new HashMap<Object, Object>();
			return getHtmlEndo2(stpEndoTipo2.getAlberoproc().getId().getCodice(), contextData, codiceComune, codificaEnteRfc53,
				soloSchedaregionale);
		    }
		}
	    }
	}
	if (!soloSchedaregionale) {
	    contextData.put("codiceComune", codiceComune);
	    inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(idcomuneinventario, codiceinventario));
	    contextData.put("endo", inventarioprocedimenti);
	    return VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, "/dizionario/schedaEndo.vm", FACCTConstants.DEFAULT_CHARSET,
		    contextData);
	}
	return null;
    }

    @Override
    public String renderTemplate(Map<Object, Object> contextData, String template) {

	if (contextData == null) {
	    contextData = new HashMap<Object, Object>();
	}
	if (StringUtils.isBlank(template)) {
	    template = "empty.vm";
	}
	Object r = userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (r instanceof Responsabili) {
	    contextData.put("utenteConnessoVTY", (Responsabili) r);
	} else if (r instanceof Anagrafe) {
	    contextData.put("utenteConnessoVTY", (Anagrafe) r);
	}
	contextData.put("Utilities", new Utilities());
	contextData.put("esc", new EscapeTool());
	String htmlModulo = VelocityEngineUtils.mergeTemplateIntoString(this.templateEngine, template, FACCTConstants.DEFAULT_CHARSET, contextData);
	return htmlModulo;
    }
}
