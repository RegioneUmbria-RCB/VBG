package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.ParametriConstants;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ConfigurazioneComunicazioniCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneFlyweight;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ConfigurazioneMail;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.LetteraComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocollazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.ParametriProtocolloPerEnte;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.ComunicazioniCommissioniCommand;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class ComunicazioniMassUtils {
    
    public static ConfigurazioniComunicazioneGen popolaConfigurazioneComunicazioniCommissioni(ComunicazioniCommissioniCommand commissioneCommand, ContestoComunicazioneEnum contesto, int idGen, Map<String,String> altriparametri) {

	validaCommand(commissioneCommand);
	ConfigurazioniComunicazioneGen configurazioneComunicazioniCommissioni = new ConfigurazioniComunicazioneGen(contesto, idGen);
	ConfigurazioneFlyweight flyweight = new ConfigurazioneFlyweight();
	List<Responsabili> firmatari = commissioneCommand.getFirmatari();
	for (Responsabili responsabili : firmatari) {
	    flyweight.getSoggettiFirmatari().add(responsabili.getId().getCodice());
	}
	flyweight.setDescrizione(commissioneCommand.getDescrizione());
	if (commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount() != null
		&& commissioneCommand.getConfiguraParametriMailCommand().getMailtipo() != null) {
	    ConfigurazioneMail confMail = new ConfigurazioneMail(
		    commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice(),
		    //commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice()
		    -1);
	    flyweight.setConfigurazioneMail(confMail);
	}
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.ESCLUDI_DESTINATARI_SENZA_MAIL,
		commissioneCommand.getConfiguraParametriMailCommand().isEscludiNoMail());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.RICHIEDE_PROTOCOLLAZIONE,
		commissioneCommand.getProtocollaParametriCommand().isProtocolla());
	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.TRASFORMA_ALLEGATI_COMPILABILI_IN_PDF, commissioneCommand.isConvertiPDF());
//	flyweight.addParametro(ConfigurazioneComunicazioniCommissioni.GESTIONE_SCELTA_MAIL_ANAGRAFE,
//		commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice());
	
	
	if(altriparametri != null) {
	    for(Map.Entry<String, String> parametri : altriparametri.entrySet()) {
		flyweight.addParametro(parametri.getKey(), parametri.getValue());
	    }
	}
	
	
	
	
	for (Integer idAllegatoFisso : commissioneCommand.getAllegatiFissi()) {
	    flyweight.getAllegatiFissi().add(new AllegatoComunicazione(idAllegatoFisso));
	}
	for (Letteretipo letteretipo : commissioneCommand.getAllegaticompilabili()) {
	    LetteraComunicazione comunicazione = new LetteraComunicazione();
	    comunicazione.setCodiceLettera(letteretipo.getId().getCodice());
	    flyweight.getLettereComunicazione().add(comunicazione);
	}
	if (commissioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    List<ParametriProtocolloPerEnte> listParametriPerEnte = convertParametriPerEnte(
		    commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte());
	    ParametriProtocollazione parametriProtocollazione = new ParametriProtocollazione(
		    commissioneCommand.getProtocollaParametriCommand().getMailtipo().getId().getCodice(), listParametriPerEnte);
	    flyweight.setParametriProtocollazione(parametriProtocollazione);
	}
	configurazioneComunicazioniCommissioni.inizializzaDaDatiDb(flyweight);
	return configurazioneComunicazioniCommissioni;
    }
    
    public static void validaCommand(ComunicazioniCommissioniCommand commissioneCommand) throws BusinessValidationException {

	if (commissioneCommand.getDescrizione() == null) {
	    throw new BusinessValidationException("Il campo Descrizione è obbligatorio");
	}
	validaConfigurazioneMail(commissioneCommand);
	validaProtocollazione(commissioneCommand);
    }
    
    private static void validaConfigurazioneMail(ComunicazioniCommissioniCommand commissioneCommand) {

	List<InvalidValue> errori = new ArrayList<InvalidValue>();
//	if (commissioneCommand.getConfiguraParametriMailCommand() == null
//		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo() == null
//		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId() == null
//		|| commissioneCommand.getConfiguraParametriMailCommand().getMailtipo().getId().getCodice() == null) {
//	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.mailtipo", null,
//		    commissioneCommand));
//	}
	if (commissioneCommand.getConfiguraParametriMailCommand() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSenderAccount().getId().getCodice() == null) {
	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.senderAccount", null,
		    commissioneCommand));
	}
	if (commissioneCommand.getConfiguraParametriMailCommand() == null
		|| commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe() == null
		|| StringUtils.isBlank(commissioneCommand.getConfiguraParametriMailCommand().getSceltaMailAnagrafe().getCodice())) {
	    errori.add(new InvalidValue("alert.required", commissioneCommand.getClass(), "configuraParametriMailCommand.sceltaMailAnagrafe", null,
		    commissioneCommand));
	}
	if (!errori.isEmpty()) {
	    throw new BusinessValidationException(errori, "Errore di validazione dei parametri mail", null);
	}
    }
    
    private static void validaProtocollazione(ComunicazioniCommissioniCommand commissioneCommand) {

	if (commissioneCommand.getProtocollaParametriCommand() != null && commissioneCommand.getProtocollaParametriCommand().isProtocolla()) {
	    if (commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte().isEmpty()) {
		throw new BusinessValidationException("Non sono stati indicati i parametri di protocollazione");
	    }
	    for (IParametriProtocolloPerEnteHelper pp : commissioneCommand.getProtocollaParametriCommand().getParametriPerEnte()) {
		if (StringUtils.isBlank(pp.getClassifica())) {
		    throw new BusinessValidationException("Il campo Classifica del protocollo è obbligatorio");
		}
		if (pp.getAmmMittente() == null || pp.getAmmMittente().getId() == null) {
		    throw new BusinessValidationException("Il campo Ammistrazione del protocollo è obbligatorio");
		}
		if (StringUtils.isBlank(pp.getTipodocumento())) {
		    if (pp.getListaTipiDocumento() != null && pp.getListaTipiDocumento().size() > 0) {
			throw new BusinessValidationException("Il campo Tipodocumento del protocollo è obbligatorio");
		    }
		}
	    }
	    if (commissioneCommand.getProtocollaParametriCommand().getMailtipo() == null) {
		throw new BusinessValidationException("Il campo MailTipo del protocollo è obbligatorio");
	    }
	}
    }
    
    private static List<ParametriProtocolloPerEnte> convertParametriPerEnte(List<IParametriProtocolloPerEnteHelper> parametriPerEnte) {

	List<ParametriProtocolloPerEnte> ret = new ArrayList<ParametriProtocolloPerEnte>();
	for (IParametriProtocolloPerEnteHelper ph : parametriPerEnte) {
	    // check NPE
	    // la validazione dovrebbe garantire che non siano presenti valori nulli
	    String codiceComune = null;
	    if (ph.getComune() != null && StringUtils.isNotBlank(ph.getComune().getCodice())) {
		codiceComune = ph.getComune().getCodice();
	    }
	    ret.add(ParametriProtocolloPerEnte.fromIParametriProtocolloPerEnteHelper(ph));
	}
	return ret;
    }
    
    
    public static <K, V> void addToMapList(Map<K, List<V>> map, K key, V value) {
	    if (!map.containsKey(key)) {
	        map.put(key, new ArrayList<V>());
	    }
	    map.get(key).add(value);
    }
    
    public static <A, K, V> void addMapMapList(Map<A, Map<K, List<V>>> map, A key1, K key2, V value) {
	    if (!map.containsKey(key1)) {
	        map.put(key1, new HashMap<K,List<V>>());
	    }
	    addToMapList(map.get(key1), key2, value);
    }
    
    public static <K, V> void addToMapSet(Map<K, Set<V>> map, K key, V value) {

	if (!map.containsKey(key)) {
	    map.put(key, new HashSet<V>());
	}
	map.get(key).add(value);
    }
    
    public static <A, K, V> void addMapMapSet(Map<A, Map<K, Set<V>>> map, A key1, K key2, V value) {

	if (!map.containsKey(key1)) {
	    map.put(key1, new HashMap<K, Set<V>>());
	}
	addToMapSet(map.get(key1), key2, value);
    }
}
