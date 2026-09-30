package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;

@Service
public class ParametriProtocolloHelperComunicazioniServiceImpl implements IParametriProtocolloHelperComunicazioniService {

    private VerticalizzazioniService verticalizzazioniService;
    private AmministrazioniService amministrazioniService;
    private ProtocollazioneService protocollazioneService;

    @Autowired
    public ParametriProtocolloHelperComunicazioniServiceImpl(VerticalizzazioniService verticalizzazioniService,
	    AmministrazioniService amministrazioniService, ProtocollazioneService protocollazioneService) {

	this.verticalizzazioniService = verticalizzazioniService;
	this.amministrazioniService = amministrazioniService;
	this.protocollazioneService = protocollazioneService;
    }

    @Override
    public List<IParametriProtocolloPerEnteHelper> popolaParametri(List<ISoftwareComuneData> softwareAndComuneForBollettazione) {

	List<IParametriProtocolloPerEnteHelper> helps = new ArrayList<IParametriProtocolloPerEnteHelper>();
	List<Verticalizzazioni> attivazioni = this.verticalizzazioniService
		.findAttivazioni(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE);
	Map<String, List<IdentificativoDescrizioneBean>> amministrazioniPerEnte = popolaAmministrazioniPerEnte(softwareAndComuneForBollettazione);
	Map<String, List<CodiceDescrizioneBean>> classifichePerEnte = popolaClassifichePerEnte(softwareAndComuneForBollettazione);
	Map<String, List<CodiceDescrizioneBean>> tipiDocumentoPerEnte = popolaTipidocumentoPerEnte(softwareAndComuneForBollettazione);
	Map<String, Boolean> mappaConfigurazioniAttive = new HashMap<String, Boolean>();
	// trovo le configurazioni attive es
	//	SS	H501
	//	TT 	H501
	//	SS	null	
	//	TT	null		
	for (Verticalizzazioni attivazione : attivazioni) {
	    String codice = attivazione.getSoftware().getCodice() +
		    "_" +
		    (attivazione.getComune() != null ? attivazione.getComune().getCodicecomune() : "TUTTI");
	    mappaConfigurazioniAttive.put(codice, Boolean.valueOf(false));
	}
	Map<String, String> mappaConfigurazionePerEnte = new HashMap<String, String>();
	for (ISoftwareComuneData c : softwareAndComuneForBollettazione) {
	    String codiceComune = c.getCodiceComune();
	    String key = ORMHelper.getSoftware() + "_" + codiceComune;
	    Boolean attivo = mappaConfigurazioniAttive.get(key);
	    if (attivo != null) {
		// trovato per ente e software corrente
		mappaConfigurazioniAttive.put(key, Boolean.valueOf(true));
		mappaConfigurazionePerEnte.put(codiceComune, key);
		continue;
	    }
	    key = WebConstants.SOFTWARE_TT + "_" + codiceComune;
	    attivo = mappaConfigurazioniAttive.get(key);
	    if (attivo != null) {
		// trovato per ente e software corrente
		mappaConfigurazioniAttive.put(key, Boolean.valueOf(true));
		mappaConfigurazionePerEnte.put(codiceComune, key);
		continue;
	    }
	    key = ORMHelper.getSoftware() + "_TUTTI";
	    attivo = mappaConfigurazioniAttive.get(key);
	    if (attivo != null) {
		// trovato per ente e software corrente
		mappaConfigurazioniAttive.put(key, Boolean.valueOf(true));
		mappaConfigurazionePerEnte.put(codiceComune, key);
		continue;
	    }
	    key = WebConstants.SOFTWARE_TT + "_TUTTI";
	    attivo = mappaConfigurazioniAttive.get(key);
	    if (attivo != null) {
		// trovato per ente e software corrente
		mappaConfigurazioniAttive.put(key, Boolean.valueOf(true));
		mappaConfigurazionePerEnte.put(codiceComune, key);
		continue;
	    }
	}
	Map<String, IParametriProtocolloPerEnteHelper> helpers = new HashMap<String, IParametriProtocolloPerEnteHelper>();
	for (ISoftwareComuneData c : softwareAndComuneForBollettazione) {
	    String keyConfigurazione = mappaConfigurazionePerEnte.get(c.getCodiceComune());
	    String codiceComune = c.getCodiceComune();
	    IParametriProtocolloPerEnteHelper ph = helpers.get(keyConfigurazione);
	    if (ph == null) {
		for (Verticalizzazioni attivazione : attivazioni) {
		    String key = attivazione.getSoftware().getCodice() +
			    "_" +
			    (attivazione.getComune() != null ? attivazione.getComune().getCodicecomune() : "TUTTI");
		    String comune = attivazione.getComune() != null ? attivazione.getComune().getComune() : "Tutti i comuni";
		    if (key.equalsIgnoreCase(keyConfigurazione)) {
			IParametriProtocolloPerEnteHelper p = new ParametriprotocolloPerEnteHelper();
			p.setComune(new CodiceDescrizioneBean(key, comune));
			p.setListaAmministrazioni(amministrazioniPerEnte.get(codiceComune));
			p.setListaClassifiche(classifichePerEnte.get(codiceComune));
			p.setListaTipiDocumento(tipiDocumentoPerEnte.get(codiceComune));
			helpers.put(keyConfigurazione, p);
			break;
		    }
		}
	    }
	}
	for (Entry<String, IParametriProtocolloPerEnteHelper> conf : helpers.entrySet()) {
	    helps.add(conf.getValue());
	}
	return helps;
    }

    private Map<String, List<CodiceDescrizioneBean>> popolaTipidocumentoPerEnte(List<ISoftwareComuneData> softwareAndComuneForBollettazione) {

	Map<String, List<CodiceDescrizioneBean>> m = new HashMap<String, List<CodiceDescrizioneBean>>();
	for (ISoftwareComuneData c : softwareAndComuneForBollettazione) {
	    if (!this.verticalizzazioniService.isAttivaPerComuneESoftware(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    c.getSoftware(), c.getCodiceComune())) {
		continue;
	    }
	    List<CodiceDescrizioneBean> list = getTipiDocumento(c.getCodiceComune(), c.getSoftware());
	    m.put(c.getCodiceComune(), list);
	}
	return m;
    }

    private List<CodiceDescrizioneBean> getTipiDocumento(String codiceComune, String software) {

	CodiceDescrizioneBean[] tDocs = protocollazioneService.getListaTipiDocumento(software, codiceComune);
	if (tDocs == null) {
	    return new ArrayList<CodiceDescrizioneBean>();
	}
	return Arrays.asList(tDocs);
    }

    private Map<String, List<CodiceDescrizioneBean>> popolaClassifichePerEnte(List<ISoftwareComuneData> softwareAndComuneForBollettazione) {

	Map<String, List<CodiceDescrizioneBean>> m = new HashMap<String, List<CodiceDescrizioneBean>>();
	for (ISoftwareComuneData c : softwareAndComuneForBollettazione) {
	    if (!this.verticalizzazioniService.isAttivaPerComuneESoftware(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    c.getSoftware(), c.getCodiceComune())) {
		continue;
	    }
	    List<CodiceDescrizioneBean> list = getListaClassifiche(c.getCodiceComune(), c.getSoftware());
	    m.put(c.getCodiceComune(), list);
	}
	return m;
    }

    private List<CodiceDescrizioneBean> getListaClassifiche(String codiceComune, String software) {

	CodiceDescrizioneBean[] classifiche = protocollazioneService.getListaClassifiche(software, codiceComune);
	if (classifiche == null) {
	    return new ArrayList<CodiceDescrizioneBean>();
	}
	return Arrays.asList(classifiche);
    }

    private Map<String, List<IdentificativoDescrizioneBean>> popolaAmministrazioniPerEnte(
	    List<ISoftwareComuneData> softwareAndComuneForBollettazione) {

	Map<String, List<IdentificativoDescrizioneBean>> m = new HashMap<String, List<IdentificativoDescrizioneBean>>();
	for (ISoftwareComuneData c : softwareAndComuneForBollettazione) {
	    if (!this.verticalizzazioniService.isAttivaPerComuneESoftware(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		    c.getSoftware(), c.getCodiceComune())) {
		continue;
	    }
	    List<IdentificativoDescrizioneBean> list = getListaAmministrazioni(c.getCodiceComune(), c.getSoftware());
	    m.put(c.getCodiceComune(), list);
	}
	return m;
    }

    private List<IdentificativoDescrizioneBean> getListaAmministrazioni(String codiceComune, String software) {

	List<Amministrazioni> amms = amministrazioniService.findAmministrazioniByDescrizioneForProtocolloRegistri(null, false, codiceComune,
		software);
	List<IdentificativoDescrizioneBean> ret = new ArrayList<IdentificativoDescrizioneBean>();
	for (Amministrazioni amm : amms) {
	    ret.add(new IdentificativoDescrizioneBean(amm.getId().getCodice(), amm.getAmministrazione()));
	}
	return ret;
    }
}
