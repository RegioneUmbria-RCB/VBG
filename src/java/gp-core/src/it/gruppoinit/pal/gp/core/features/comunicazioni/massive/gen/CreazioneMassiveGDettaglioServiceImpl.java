package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.IComunicazioniMassiveDettaglioDAO;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.StatoComunicazioniBollettazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametroConfigurazioneComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@Service
public class CreazioneMassiveGDettaglioServiceImpl implements ICreazioneMassiveGDettaglioService {

    @Autowired
    private IComunicazioniToGenService comunicazioniToGenService;
    @Autowired
    private IComunicazioniMassiveDAO comunicazioniMassiveDAO;
    @Autowired
    private IComunicazioniMassiveDettaglioDAO comunicazioniMassiveDettaglioDAO;
    @Autowired
    private ICurrentDateService currentDateService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    AutorizzazioniService autorizzazioniService;

    @Override
    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	comunicazioniToGenService.collegaRigheMercatiAComunicazioni(idTestata, configurazioneComunicazione);
	MassiveTestata testata = comunicazioniMassiveDAO.getTestataById(idTestata);
	List<DettaglioRigaGen> dettaglioGenL = this.comunicazioniToGenService.getDettagli(configurazioneComunicazione);
	//Prima di tutto raggruppiamo tutto per anagrafe a prescindere
	Map<Integer, Map<Integer, List<DettaglioRigaGen>>> m = new HashMap<Integer, Map<Integer, List<DettaglioRigaGen>>>();
	for (DettaglioRigaGen dettaglioriga : dettaglioGenL) {
	    ComunicazioniMassUtils.addMapMapList(m,
		    configurazioneComunicazione.isIstitolare() ? dettaglioriga.getTitolare() : dettaglioriga.getOccupante(),
		    dettaglioriga.getCodiceAutorizzazione(), dettaglioriga);
	}
	//Se è per anagrafe sarà un massive_dettaglio, più mercati_massive_d, uno per autorizzazione, altrimenti sarà un massive_dettaglio per autorizzazione
	for (Entry<Integer, Map<Integer, List<DettaglioRigaGen>>> entry : m.entrySet()) {
	    Anagrafe a = anagrafeService.findById(new PkId(entry.getKey()));
	    String cf = a.getCodicefiscale();
	    String mail = getMailOPec(configurazioneComunicazione.getSceltaTipoMailAnagrafe(), a.getEmail(), a.getPec());
	    if (StringUtils.isBlank(mail) && configurazioneComunicazione.isEscludiDestinatariSenzaMail()
		    && !configurazioneComunicazione.isAppioInvio()) {
		continue;
	    }
	    boolean isOccupante = configurazioneComunicazione.isIstitolare() ? false : true;
	    //Caso raggruppamento per Anagrafe
	    if (!configurazioneComunicazione.isAutorizzazioniGroup()) {
		
		//Nel caso in cui isAppio e cf != 16 , è occupante oppure titolare e occupanti coincidono
		if (configurazioneComunicazione.isAppioInvio() && (StringUtils.isBlank(cf) || cf.length() != 16)) {
		    		    		    
		    boolean isSoloAppio = true;
		    List<ParametroConfigurazioneComunicazione> parametritmp = configurazioneComunicazione.getParametriPerDb().getParametri();
		    if (parametritmp == null) {
			//isSoloAppio = true;
		    } else {
			for (ParametroConfigurazioneComunicazione p : parametritmp) {
			    if ("GESTIONE_SCELTA_MAIL_ANAGRAFE".equals(p.getChiave())) {
				isSoloAppio = false;
				break;
			    }
			    if (ParametriConstants.RICHIEDE_PROTOCOLLAZIONE.equals(p.getChiave()) && "1".equals(p.getValore())) {
				isSoloAppio = false;
				break;
			    }
			}
		    }
		    
		    
		    if(!isSoloAppio){
			popolaDestinatarioBase(testata, a, mail, entry.getValue());
		    }

		    
		    //QUI E' DOVE STAI SCRIVENDO AUTORIZZAZIONE PER AUTORIZZAZIONE
		    for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
			
			
			if(!isOccupante){//è titolare
			    if(entri.getValue() == null || entri.getValue().isEmpty()){
				continue;
			    }
			    if(entri.getValue().get(0).getTitolare() == null || entri.getValue().get(0).getOccupante() == null){
				continue;
			    }
			    if(entri.getValue().get(0).getTitolare().intValue() != entri.getValue().get(0).getOccupante().intValue()){
				continue;
			    }
			}
			
			MassiveDettaglio mds1 = new MassiveDettaglio();
			Autorizzazioni auto = autorizzazioniService.findById(new PkId(entri.getKey()));
			if (auto != null && auto.getIstanza() != null && auto.getIstanza().getId() != null
				&& auto.getIstanza().getId().getCodice() != null) {
			    Istanze istanza = auto.getIstanza();
			    Anagrafe richiedente = istanza.getRichiedente();
			    if (istanza != null && richiedente != null && StringUtils.isNotBlank(richiedente.getCodicefiscale())
				    && richiedente.getCodicefiscale().length() == 16) {
				mds1.setMassiveTestata(testata);
				mds1.setUltimoStatoCompletato( isSoloAppio ? StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE.name() : StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
				mds1.setUltimoStatoData(currentDateService.getCurrentDate());
				comunicazioniMassiveDettaglioDAO.insert(mds1);
				MassiveDettDestinatari destDettDestinatari = new MassiveDettDestinatari();
				destDettDestinatari.setMassiveDettaglio(mds1);
				destDettDestinatari.setAnagrafe(richiedente);
				destDettDestinatari.setMailDestinatario(mail);
				comunicazioniMassiveDAO.saveEntity(destDettDestinatari);
				mds1.setDestinatari(destDettDestinatari);
				//END popolaMassiveDettaglioCommissionesingle
				//Salviamo solo su mercatiMassiveD per tracciarci l'autorizzazione di riferimento
				Map<Integer, List<DettaglioRigaGen>> tempMap = new HashMap<Integer, List<DettaglioRigaGen>>();

				
				//In questo modo non riutilizzo la stessa DettaglioRigaGen perché contiene informazioni che possono incrociarsi pericolosamente
				List<DettaglioRigaGen> tempDettaglioRigaList = new ArrayList<DettaglioRigaGen>();
				for(DettaglioRigaGen dettaglio : entri.getValue()){
				    DettaglioRigaGen d = new DettaglioRigaGen();
				    d.setCodiceMercato(dettaglio.getCodiceMercato());
				    tempDettaglioRigaList.add(d);
				}
				
				tempMap.put(entri.getKey(), tempDettaglioRigaList);
				comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds1.getId().getCodice(), tempMap);

			    }
			} else {
			    continue;
			}
		    }
		}else{
		    popolaDestinatarioBase(testata, a, mail, entry.getValue());
		}
	    }
	    //Caso singolo
	    else {
		//Uno per autorizzazione
		for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
		    Map<Integer, List<DettaglioRigaGen>> tempm = new HashMap<Integer, List<DettaglioRigaGen>>();
		    tempm.put(entri.getKey(), entri.getValue());
		    
		    //Nel caso in cui isAppio e cf != 16 ed è occupante
		    if (configurazioneComunicazione.isAppioInvio() && (StringUtils.isBlank(cf) || cf.length() != 16)) {
			
			
			boolean isSoloAppio = true;
			List<ParametroConfigurazioneComunicazione> parametritmp = configurazioneComunicazione.getParametriPerDb().getParametri();
			if (parametritmp == null) {
			    //isSoloAppio = true;
			} else {
			    for (ParametroConfigurazioneComunicazione p : parametritmp) {
				if ("GESTIONE_SCELTA_MAIL_ANAGRAFE".equals(p.getChiave())) {
				    isSoloAppio = false;
				    break;
				}
				if (ParametriConstants.RICHIEDE_PROTOCOLLAZIONE.equals(p.getChiave()) && "1".equals(p.getValore())) {
				    isSoloAppio = false;
				    break;
				}
			    }
			}
			
			if (!isSoloAppio) {
			    popolaDestinatarioBase(testata, a, mail, tempm);
			}
			
			
			if(!isOccupante){//è titolare
			    if(entri.getValue() == null || entri.getValue().isEmpty()){
				continue;
			    }
			    if(entri.getValue().get(0).getTitolare() == null || entri.getValue().get(0).getOccupante() == null){
				continue;
			    }
			    if(entri.getValue().get(0).getTitolare().intValue() != entri.getValue().get(0).getOccupante().intValue()){
				continue;
			    }
			}
			
			
			MassiveDettaglio mds1 = new MassiveDettaglio();
			Autorizzazioni auto = autorizzazioniService.findById(new PkId(entri.getKey()));
			if (auto != null && auto.getIstanza() != null && auto.getIstanza().getId() != null
				&& auto.getIstanza().getId().getCodice() != null) {
			    Istanze istanza = auto.getIstanza();
			    Anagrafe richiedente = istanza.getRichiedente();
			    if (istanza != null && richiedente != null && StringUtils.isNotBlank(richiedente.getCodicefiscale())
				    && richiedente.getCodicefiscale().length() == 16) {
				mds1.setMassiveTestata(testata);
				mds1.setUltimoStatoCompletato( isSoloAppio ? StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE.name() : StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
				mds1.setUltimoStatoData(currentDateService.getCurrentDate());
				comunicazioniMassiveDettaglioDAO.insert(mds1);
				MassiveDettDestinatari dettDestinatari = new MassiveDettDestinatari();
				dettDestinatari.setMassiveDettaglio(mds1);
				dettDestinatari.setAnagrafe(richiedente);
				dettDestinatari.setMailDestinatario(mail);
				comunicazioniMassiveDAO.saveEntity(dettDestinatari);
				mds1.setDestinatari(dettDestinatari);
				//END popolaMassiveDettaglioCommissionesingle
				//Salviamo solo su mercatiMassiveD per tracciarci l'autorizzazione di riferimento
				Map<Integer, List<DettaglioRigaGen>> tempMap = new HashMap<Integer, List<DettaglioRigaGen>>();

				
				//In questo modo non riutilizzo la stessa DettaglioRigaGen perché contiene informazioni che possono incrociarsi pericolosamente
				List<DettaglioRigaGen> tempDettaglioRigaList = new ArrayList<DettaglioRigaGen>();
				for(DettaglioRigaGen dettaglio : entri.getValue()){
				    DettaglioRigaGen d = new DettaglioRigaGen();
				    d.setCodiceMercato(dettaglio.getCodiceMercato());
				    tempDettaglioRigaList.add(d);
				}
				
				tempMap.put(entri.getKey(), tempDettaglioRigaList);
				comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds1.getId().getCodice(), tempMap);
			    }
			} else {
			    continue;
			}
		    }else{
			popolaDestinatarioBase(testata, a, mail, tempm);
		    }
		}
	    }
	}
	//	    if (!configurazioneComunicazione.isAutorizzazioniGroup()) {
	//		//BEGIN popolaMassiveDettaglioCommissionesingle
	//		MassiveDettaglio mds = new MassiveDettaglio();
	//		//CASO TITOLARE
	//		if (configurazioneComunicazione.isAppioInvio()) {
	//		    if (configurazioneComunicazione.isIstitolare()) {
	//			//if (a != null && a.getCodicefiscale().length() == 16) {
	//			mds.setMassiveTestata(testata);
	//			mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//			mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//			comunicazioniMassiveDettaglioDAO.insert(mds);
	//			MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//			dest.setMassiveDettaglio(mds);
	//			dest.setAnagrafe(a);
	//			dest.setMailDestinatario(mail);
	//			comunicazioniMassiveDAO.saveEntity(dest);
	//			mds.setDestinatari(dest);
	//			//END popolaMassiveDettaglioCommissionesingle
	//			comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), entry.getValue());
	//			//}
	//			//CASO OCCUPANTE
	//		    } else if (!configurazioneComunicazione.isIstitolare()) {
	//			if (a != null && StringUtils.isNotBlank(a.getCodicefiscale()) && a.getCodicefiscale().length() != 16) {
	//			    //List<Autorizzazioni> auts = autorizzazioniService.findByAnagrafe(a.getId().getCodice());
	//			    for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
	//				Autorizzazioni auto = autorizzazioniService.findById(new PkId(entri.getKey()));
	//				if (auto != null && auto.getIstanza() != null && auto.getIstanza().getId() != null
	//					&& auto.getIstanza().getId().getCodice() != null) {
	//				    Istanze istanza = auto.getIstanza();
	//				    Anagrafe richiedente = istanza.getRichiedente();
	//				    if (istanza != null && richiedente != null && richiedente.getCodicefiscale() != null
	//					    && richiedente.getCodicefiscale() != null && richiedente.getCodicefiscale().length() == 16) {
	//					mds.setMassiveTestata(testata);
	//					mds.setUltimoStatoCompletato(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	//					mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//					comunicazioniMassiveDettaglioDAO.insert(mds);
	//					MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//					dest.setMassiveDettaglio(mds);
	//					dest.setAnagrafe(richiedente);
	//					dest.setMailDestinatario(mail);
	//					comunicazioniMassiveDAO.saveEntity(dest);
	//					mds.setDestinatari(dest);
	//					//END popolaMassiveDettaglioCommissionesingle
	//					comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(),
	//						entry.getValue());
	//				    }
	//				} else {
	//				    mds.setMassiveTestata(testata);
	//				    mds.setUltimoStatoCompletato(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	//				    mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//				    comunicazioniMassiveDettaglioDAO.insert(mds);
	//				    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//				    dest.setMassiveDettaglio(mds);
	//				    dest.setAnagrafe(a);
	//				    dest.setMailDestinatario(mail);
	//				    comunicazioniMassiveDAO.saveEntity(dest);
	//				    mds.setDestinatari(dest);
	//				    //END popolaMassiveDettaglioCommissionesingle
	//				    comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(),
	//					    entry.getValue());
	//				}
	//			    }
	//			}
	//			if (a != null && StringUtils.isNotBlank(a.getCodicefiscale()) && a.getCodicefiscale().length() == 16) {
	//			    mds.setMassiveTestata(testata);
	//			    mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//			    mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//			    comunicazioniMassiveDettaglioDAO.insert(mds);
	//			    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//			    dest.setMassiveDettaglio(mds);
	//			    dest.setAnagrafe(a);
	//			    dest.setMailDestinatario(mail);
	//			    comunicazioniMassiveDAO.saveEntity(dest);
	//			    mds.setDestinatari(dest);
	//			    //END popolaMassiveDettaglioCommissionesingle
	//			    comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), entry.getValue());
	//			}
	//		    }
	//		} else {
	//		    mds.setMassiveTestata(testata);
	//		    mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//		    mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//		    comunicazioniMassiveDettaglioDAO.insert(mds);
	//		    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//		    dest.setMassiveDettaglio(mds);
	//		    dest.setAnagrafe(a);
	//		    dest.setMailDestinatario(mail);
	//		    comunicazioniMassiveDAO.saveEntity(dest);
	//		    mds.setDestinatari(dest);
	//		    //END popolaMassiveDettaglioCommissionesingle
	//		    comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), entry.getValue());
	//		}
	//	    } else {
	//		if (configurazioneComunicazione.isAppioInvio()) {
	//		    if (configurazioneComunicazione.isIstitolare()) {
	//			//if (a != null && a.getCodicefiscale().length() == 16) {
	//			//Uno per autorizzazione
	//			for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
	//			    //BEGIN popolaMassiveDettaglioCommissionemulti
	//			    MassiveDettaglio mds = new MassiveDettaglio();
	//			    mds.setMassiveTestata(testata);
	//			    mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//			    mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//			    comunicazioniMassiveDettaglioDAO.insert(mds);
	//			    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//			    dest.setMassiveDettaglio(mds);
	//			    dest.setAnagrafe(a);
	//			    dest.setMailDestinatario(mail);
	//			    comunicazioniMassiveDAO.saveEntity(dest);
	//			    mds.setDestinatari(dest);
	//			    Map<Integer, List<DettaglioRigaGen>> tempm = new HashMap<Integer, List<DettaglioRigaGen>>();
	//			    tempm.put(entri.getKey(), entri.getValue());
	//			    //END popolaMassiveDettaglioCommissionemulti
	//			    //utilizziamo tempm come mappa con singola chiave per riutilizzare stesso metodo collegaDettaglioMercatoADettaglioComunicazioni
	//			    comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), tempm);
	//			}
	//			//}
	//		    }
	//		    if (!configurazioneComunicazione.isIstitolare()) {
	//			if (a != null && StringUtils.isNotBlank(a.getCodicefiscale()) && a.getCodicefiscale().length() != 16) {
	//			    //Uno per autorizzazione
	//			    for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
	//				List<DettaglioRigaGen> detR = entri.getValue();
	//				Autorizzazioni auto = autorizzazioniService.findById(new PkId(entri.getKey()));
	//				if (auto != null && auto.getIstanza() != null && auto.getIstanza().getId() != null
	//					&& auto.getIstanza().getId().getCodice() != null) {
	//				    Istanze istanza = auto.getIstanza();
	//				    Anagrafe richiedente = istanza.getRichiedente();
	//				    if (istanza != null && richiedente != null && richiedente.getCodicefiscale() != null
	//					    && richiedente.getCodicefiscale() != null && richiedente.getCodicefiscale().length() == 16) {
	//					//BEGIN popolaMassiveDettaglioCommissionemulti
	//					MassiveDettaglio mds = new MassiveDettaglio();
	//					mds.setMassiveTestata(testata);
	//					mds.setUltimoStatoCompletato(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	//					mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//					comunicazioniMassiveDettaglioDAO.insert(mds);
	//					MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//					dest.setMassiveDettaglio(mds);
	//					dest.setAnagrafe(richiedente);
	//					dest.setMailDestinatario(mail);
	//					comunicazioniMassiveDAO.saveEntity(dest);
	//					mds.setDestinatari(dest);
	//					Map<Integer, List<DettaglioRigaGen>> tempm = new HashMap<Integer, List<DettaglioRigaGen>>();
	//					tempm.put(entri.getKey(), entri.getValue());
	//					//END popolaMassiveDettaglioCommissionemulti
	//					//utilizziamo tempm come mappa con singola chiave per riutilizzare stesso metodo collegaDettaglioMercatoADettaglioComunicazioni
	//					comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), tempm);
	//				    }
	//				} else {
	//				    MassiveDettaglio mds = new MassiveDettaglio();
	//				    mds.setMassiveTestata(testata);
	//				    mds.setUltimoStatoCompletato(StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO.name());
	//				    mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//				    comunicazioniMassiveDettaglioDAO.insert(mds);
	//				    MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//				    dest.setMassiveDettaglio(mds);
	//				    dest.setAnagrafe(a);
	//				    dest.setMailDestinatario(mail);
	//				    comunicazioniMassiveDAO.saveEntity(dest);
	//				    mds.setDestinatari(dest);
	//				    //END popolaMassiveDettaglioCommissionesingle
	//				    comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(),
	//					    entry.getValue());
	//				}
	//			    }
	//			}
	//			if (a != null && StringUtils.isNotBlank(a.getCodicefiscale()) && a.getCodicefiscale().length() == 16) {
	//			    //Uno per autorizzazione
	//			    for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
	//				//BEGIN popolaMassiveDettaglioCommissionemulti
	//				MassiveDettaglio mds = new MassiveDettaglio();
	//				mds.setMassiveTestata(testata);
	//				mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//				mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//				comunicazioniMassiveDettaglioDAO.insert(mds);
	//				MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//				dest.setMassiveDettaglio(mds);
	//				dest.setAnagrafe(a);
	//				dest.setMailDestinatario(mail);
	//				comunicazioniMassiveDAO.saveEntity(dest);
	//				mds.setDestinatari(dest);
	//				Map<Integer, List<DettaglioRigaGen>> tempm = new HashMap<Integer, List<DettaglioRigaGen>>();
	//				tempm.put(entri.getKey(), entri.getValue());
	//				//END popolaMassiveDettaglioCommissionemulti
	//				//utilizziamo tempm come mappa con singola chiave per riutilizzare stesso metodo collegaDettaglioMercatoADettaglioComunicazioni
	//				comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), tempm);
	//			    }
	//			}
	//		    }
	//		} else {
	//		    //Uno per autorizzazione
	//		    for (Entry<Integer, List<DettaglioRigaGen>> entri : entry.getValue().entrySet()) {
	//			//BEGIN popolaMassiveDettaglioCommissionemulti
	//			MassiveDettaglio mds = new MassiveDettaglio();
	//			mds.setMassiveTestata(testata);
	//			mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	//			mds.setUltimoStatoData(currentDateService.getCurrentDate());
	//			comunicazioniMassiveDettaglioDAO.insert(mds);
	//			MassiveDettDestinatari dest = new MassiveDettDestinatari();
	//			dest.setMassiveDettaglio(mds);
	//			dest.setAnagrafe(a);
	//			dest.setMailDestinatario(mail);
	//			comunicazioniMassiveDAO.saveEntity(dest);
	//			mds.setDestinatari(dest);
	//			Map<Integer, List<DettaglioRigaGen>> tempm = new HashMap<Integer, List<DettaglioRigaGen>>();
	//			tempm.put(entri.getKey(), entri.getValue());
	//			//END popolaMassiveDettaglioCommissionemulti
	//			//utilizziamo tempm come mappa con singola chiave per riutilizzare stesso metodo collegaDettaglioMercatoADettaglioComunicazioni
	//			comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), tempm);
	//		    }
	//		}
    }
    
    private void popolaDestinatarioBase(MassiveTestata testata, Anagrafe a, String mail, Map<Integer, List<DettaglioRigaGen>> m){
	
	//testata = testata
	//a = a
	//mail = mail
	//m = entry.getValue()
	
	MassiveDettaglio mds = new MassiveDettaglio();
	//Tutto questo lasciamolo sempre, va bene così, in ogni caso
	mds.setMassiveTestata(testata);
	mds.setUltimoStatoCompletato(StatoComunicazioniBollettazioneEnum.PRONTA_PER_ELABORAZIONE.name());
	mds.setUltimoStatoData(currentDateService.getCurrentDate());
	comunicazioniMassiveDettaglioDAO.insert(mds);
	MassiveDettDestinatari dest = new MassiveDettDestinatari();
	dest.setMassiveDettaglio(mds);
	dest.setAnagrafe(a);
	dest.setMailDestinatario(mail);
	comunicazioniMassiveDAO.saveEntity(dest);
	mds.setDestinatari(dest);
	//END popolaMassiveDettaglioCommissionesingle
	comunicazioniToGenService.collegaDettaglioMercatoADettaglioComunicazioni(mds.getId().getCodice(), m);
	//}
    }

    @Override
    public void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	comunicazioniToGenService.collegaRigheIstanzeAComunicazioni(idTestata, configurazioneComunicazione);
	configurazioneComunicazione.setIdGen(idTestata);
	MassiveTestata testata = comunicazioniMassiveDAO.getTestataById(idTestata);
	List<DettaglioRigaIstanze> dettaglioGen = this.istanzeService.findIstanzeListHelperByFilterMass(configurazioneComunicazione.getFilter(),
		HelperTypeEnum.ISTANZE, null, null);
	if (configurazioneComunicazione.isIstanzeGroup()) {
	    
	    //Verifico se è appio e se è solo appio per il caso isrichiedente
	    boolean isSoloAppio = false;
	    if(configurazioneComunicazione.isAppioInvio()){
		isSoloAppio = true;
		List<ParametroConfigurazioneComunicazione> parametritmp = configurazioneComunicazione.getParametriPerDb().getParametri();
		if (parametritmp == null) {
		    //isSoloAppio = true;
		} else {
		    for (ParametroConfigurazioneComunicazione p : parametritmp) {
			if ("GESTIONE_SCELTA_MAIL_ANAGRAFE".equals(p.getChiave())) {
			    isSoloAppio = false;
			    break;
			}
			if (ParametriConstants.RICHIEDE_PROTOCOLLAZIONE.equals(p.getChiave()) && "1".equals(p.getValore())) {
			    isSoloAppio = false;
			    break;
			}
		    }
		}
	    }

	    for (DettaglioRigaGen riga : dettaglioGen) {
		Map<StatoComunicazioniGenEnum, List<Integer>> anagrafeIstanzaLMap = new HashMap<StatoComunicazioniGenEnum, List<Integer>>();
		
		if(configurazioneComunicazione.isRichiedente()){
		    if(isSoloAppio){
			    if (riga.getCodicerichiedente() != null) {
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente());
			    } 
			}else if(configurazioneComunicazione.isAppioInvio()){
			    if(riga.getCodicetitolarelegale() != null){
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicetitolarelegale());
			    }else if(riga.getCodicerichiedente() != null){
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente());
			    }
			    
			    if(riga.getCodicetitolarelegale() != null && riga.getCodicerichiedente() != null && riga.getCodicetitolarelegale().intValue() != riga.getCodicerichiedente().intValue()){
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO, riga.getCodicerichiedente());
			    }
			}else{
			    if(riga.getCodicetitolarelegale() != null){
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicetitolarelegale());
			    }else if(riga.getCodicerichiedente() != null){
				ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente());
			    }
			}
		}
		
		if (configurazioneComunicazione.isIntermediario() && riga.getCodiceprofessionista() != null) {
		    ComunicazioniMassUtils.addToMapList(anagrafeIstanzaLMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodiceprofessionista());
		}
		
		for (Map.Entry<StatoComunicazioniGenEnum, List<Integer>> entry : anagrafeIstanzaLMap.entrySet()) {
		    
		    for (Integer anagrafeIstanza : entry.getValue()) {
			Anagrafe a = anagrafeService.findById(new PkId(anagrafeIstanza));
			String mail = getMailOPec(configurazioneComunicazione.getSceltaTipoMailAnagrafe(), a.getEmail(), a.getPec());
			if (StringUtils.isBlank(mail) && configurazioneComunicazione.isEscludiDestinatariSenzaMail()
				&& !configurazioneComunicazione.isAppioInvio()) {
			    continue;
			}
			MassiveDettaglio mds = popolaMassiveDettaglio(testata, a, mail,entry.getKey());
			int[] idistanze = new int[1];
			idistanze[0] = riga.getCodiceIstanza();
			Set<Integer> set = new HashSet<Integer>();
			set.add(riga.getCodiceIstanza());
			comunicazioniToGenService.collegaDettaglioIstanzeADettaglioComunicazioni(mds.getId().getCodice(), set,
				configurazioneComunicazione.isMovimenti(), configurazioneComunicazione.getTipomovimento(),
				configurazioneComunicazione.getAmministrazione());
		    }
		    
		}
		
		
	    }
	} else {
	    //Raggruppiamo per anagrafica. Usiamo il set perché essendo multianagrafica per istanza potrebbero risultare istanze duplicate
	   Map<StatoComunicazioniGenEnum,Map<Integer, Set<Integer>>> mapAnagrafeIstanzeMap = new HashMap<StatoComunicazioniGenEnum,Map<Integer, Set<Integer>>>();
	    
	    
	  //Verifico se è appio e se è solo appio per il caso isrichiedente
	    boolean isSoloAppio = false;
	    if(configurazioneComunicazione.isAppioInvio()){
		isSoloAppio = true;
		List<ParametroConfigurazioneComunicazione> parametritmp = configurazioneComunicazione.getParametriPerDb().getParametri();
		if (parametritmp == null) {
		    //isSoloAppio = true;
		} else {
		    for (ParametroConfigurazioneComunicazione p : parametritmp) {
			if ("GESTIONE_SCELTA_MAIL_ANAGRAFE".equals(p.getChiave())) {
			    isSoloAppio = false;
			    break;
			}
			if (ParametriConstants.RICHIEDE_PROTOCOLLAZIONE.equals(p.getChiave()) && "1".equals(p.getValore())) {
			    isSoloAppio = false;
			    break;
			}
		    }
		}
	    }
	    
	    
	    for (DettaglioRigaGen riga : dettaglioGen) {
		if(configurazioneComunicazione.isRichiedente()){
		    if(isSoloAppio){
			    if (riga.getCodicerichiedente() != null) {
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente(), riga.getCodiceIstanza());
			    } 
			}else if(configurazioneComunicazione.isAppioInvio()){
			    if(riga.getCodicetitolarelegale() != null){
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicetitolarelegale(), riga.getCodiceIstanza());
			    }else if(riga.getCodicerichiedente() != null){
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente(), riga.getCodiceIstanza());
			    }
			    
			    if(riga.getCodicetitolarelegale() != null && riga.getCodicerichiedente() != null && riga.getCodicetitolarelegale().intValue() != riga.getCodicerichiedente().intValue()){
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_ALL_INVIO_APPIO, riga.getCodicerichiedente(), riga.getCodiceIstanza());
			    }
			}else{
			    if(riga.getCodicetitolarelegale() != null){
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicetitolarelegale(), riga.getCodiceIstanza());
			    }else if(riga.getCodicerichiedente() != null){
				ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodicerichiedente(), riga.getCodiceIstanza());
			    }
			}
		}
		if (configurazioneComunicazione.isIntermediario() && riga.getCodiceprofessionista() != null) {
		    ComunicazioniMassUtils.addMapMapSet(mapAnagrafeIstanzeMap, StatoComunicazioniGenEnum.PRONTA_PER_ELABORAZIONE, riga.getCodiceprofessionista(), riga.getCodiceIstanza());
		}
	    }
	    
	    for (Map.Entry<StatoComunicazioniGenEnum, Map<Integer, Set<Integer>>> entri : mapAnagrafeIstanzeMap.entrySet()) {
		for (Map.Entry<Integer, Set<Integer>> entry : entri.getValue().entrySet()) {
		    Anagrafe a = anagrafeService.findById(new PkId(entry.getKey()));
		    String mail = getMailOPec(configurazioneComunicazione.getSceltaTipoMailAnagrafe(), a.getEmail(), a.getPec());
		    if (StringUtils.isBlank(mail) && configurazioneComunicazione.isEscludiDestinatariSenzaMail()
			    && !configurazioneComunicazione.isAppioInvio()) {
			continue;
		    }
		    MassiveDettaglio mds = popolaMassiveDettaglio(testata, a, mail,entri.getKey());
		    comunicazioniToGenService.collegaDettaglioIstanzeADettaglioComunicazioni(mds.getId().getCodice(), entry.getValue(),
			    configurazioneComunicazione.isMovimenti(), configurazioneComunicazione.getTipomovimento(),
			    configurazioneComunicazione.getAmministrazione());
		}
	    }
   
	}
    }

    //per ora lo uso solo per le istanze
    private MassiveDettaglio popolaMassiveDettaglio(MassiveTestata testata, Anagrafe a, String mail, StatoComunicazioniGenEnum stato) {

	MassiveDettaglio mds = new MassiveDettaglio();
	mds.setMassiveTestata(testata);
	mds.setUltimoStatoCompletato(stato.name());
	mds.setUltimoStatoData(currentDateService.getCurrentDate());
	comunicazioniMassiveDettaglioDAO.insert(mds);
	MassiveDettDestinatari dest = new MassiveDettDestinatari();
	dest.setMassiveDettaglio(mds);
	dest.setAnagrafe(a);
	dest.setMailDestinatario(mail);
	comunicazioniMassiveDAO.saveEntity(dest);
	mds.setDestinatari(dest);
	return mds;
    }

    private String getMailOPec(SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum, String email, String pec) {

	if (sceltaTipoMailAnagrafeEnum == null) {
	    return null;
	}
	switch (sceltaTipoMailAnagrafeEnum) {
	case PEC_O_MAIL:
	    return StringUtils.defaultString(pec, email);
	case SOLO_MAIL:
	    return email;
	case SOLO_PEC:
	    return pec;
	default:
	    break;
	}
	return null;
    }
}
