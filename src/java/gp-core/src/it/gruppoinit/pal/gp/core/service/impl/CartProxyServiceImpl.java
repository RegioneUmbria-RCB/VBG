package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.CategorieTree;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CartProxyService;
import it.gruppoinit.pal.gp.core.service.CartWsClientService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpCategorieEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Attivita;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.CategoriaAttivita;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.CategoriaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioDizionario;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioLocalizzazioneSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioLocalizzazioneSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.RichiestaDizionario;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.RichiestaSchedaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.RichiestaSchedaEndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.TipologiaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.TipologiaEndoTipo2;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopHeaderInfo;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProxyServiceImpl implements CartProxyService {

    private final String PREFISSO_BUSTA_SOAP = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><env:Envelope "
	    + "env:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" "
	    + "xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
	    + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><env:Header/><env:Body>";
    private final String POSTFISSO_BUSTA_SOAP = "</env:Body></env:Envelope>";
    private static final Logger log = LoggerFactory.getLogger(CartProxyServiceImpl.class);
    private CartWsClientService cartWsClientService;
    private StpTipologieEndo1Service stpTipologieEndo1Service;
    private SoftwareService softwareService;
    private TipifamiglieendoService tipifamiglieendoService;
    private StpCategorieEndo1Service stpCategorieEndo1Service;
    private TipiendoService tipiendoService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private AlberoprocService alberoprocService;
    private AlberoprocEndoService alberoprocEndoService;
    private OggettiService oggettiService;
    private NaturaendoService naturaendoService;
    private AzioniService azioniService;
    private TipiMovimentoService tipiMovimentoService;
    private AmministrazioniService amministrazioniService;
    private int vociElaborate = 0;
    private String ATTIVITA = StpEndoTipo2Service.TIPO_ATTIVITA;
    private String CATEGORIA = StpEndoTipo2Service.TIPO_CATEGORIA;
    private String ENDO = StpEndoTipo2Service.TIPO_ENDO;
    private VerticalizzazioniService verticalizzazioniService;
    private Map<String, Map<String, String>> parametriVerticalizzazioni = new HashMap<String, Map<String, String>>();
    /**
     * 0 - Non pubblicare <br/>
     * 1 - Area Riservata e Front Office<br/>
     * 2 - Solo Area Riservata <br/>
     * 3 - Solo Front Office
     */
    public final Integer statoPubblicazioneAlberoproc = 1;

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setTipiendoService(TipiendoService tipiendoService) {

	this.tipiendoService = tipiendoService;
    }

    @Autowired
    public void setStpCategorieEndo1Service(StpCategorieEndo1Service stpCategorieEndo1Service) {

	this.stpCategorieEndo1Service = stpCategorieEndo1Service;
    }

    @Autowired
    public void setTipifamiglieendoService(TipifamiglieendoService tipifamiglieendoService) {

	this.tipifamiglieendoService = tipifamiglieendoService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStpTipologieEndo1Service(StpTipologieEndo1Service stpTipologieEndo1Service) {

	this.stpTipologieEndo1Service = stpTipologieEndo1Service;
    }

    @Autowired
    public void setCartWsClientService(CartWsClientService cartWsClientService) {

	this.cartWsClientService = cartWsClientService;
    }

    @Override
    public void deleteMessaggio(String idMessaggio) {

	try {
	    this.cartWsClientService.deleteMessage(idMessaggio);
	} catch (SPCoopException e) {
	    log.error("deleteMessaggio '{}', errore: {}", idMessaggio, e.getMessage());
	    throw new RuntimeException("deleteMessaggio '" + idMessaggio + "', " + e.getMessage(), e.getCause());
	} catch (MalformedURLException e) {
	    log.error("deleteMessaggio '{}', errore: {}", idMessaggio, e.getMessage());
	    throw new RuntimeException("deleteMessaggio '" + idMessaggio + "', " + e.getMessage(), e.getCause());
	} catch (RemoteException e) {
	    log.error("deleteMessaggio '{}', errore: {}", idMessaggio, e.getMessage());
	    throw new RuntimeException("deleteMessaggio '" + idMessaggio + "', " + e.getMessage(), e.getCause());
	} catch (ServiceException e) {
	    log.error("deleteMessaggio '{}', errore: {}", idMessaggio, e.getMessage());
	    throw new RuntimeException("deleteMessaggio '" + idMessaggio + "', " + e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("FUNZIONE ELIMINA MESSAGGIO. ID MESSAGGIO ELIMINATO: {}", idMessaggio);
	}
    }

    @Override
    public void deleteTuttiMessaggi() {

	try {
	    this.cartWsClientService.deleteAllMessage();
	} catch (SPCoopException e) {
	    log.error("deleteTuttiMessaggi, errore: {}", e.getMessage());
	    throw new RuntimeException("deleteTuttiMessaggi " + e.getMessage(), e.getCause());
	} catch (MalformedURLException e) {
	    log.error("deleteTuttiMessaggi, errore: {}", e.getMessage());
	    throw new RuntimeException("deleteTuttiMessaggi " + e.getMessage(), e.getCause());
	} catch (RemoteException e) {
	    log.error("deleteTuttiMessaggi, errore: {}", e.getMessage());
	    throw new RuntimeException("deleteTuttiMessaggi " + e.getMessage(), e.getCause());
	} catch (ServiceException e) {
	    log.error("deleteTuttiMessaggi, errore: {}", e.getMessage());
	    throw new RuntimeException("deleteTuttiMessaggi " + e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("FUNZIONE deleteTuttiMessaggi. Cancellati tutti i messaggi!!");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    /**
     * 1 GETMESSAGGIO
     * 2 SE MESSAGGIO.SERVIZIOAPPL=iNVIO DIZIONARIO --> ELABORAINVIODIZIONARIO
     * 2.1 DA MESSAGGIO PRENDO BODY CON wsClientService.getBodyMessaggio
     * 2.2 FACCIO UNMARSHALL CON L'OGGETTO CORRISPONDENTE (it.gruppoinit.rs.rfc4_35.invio.dizionario.InvioDizionario)
     * 3 INSERISCO ENDO 
     * 3.1 elaboro I TAG TIPOLOGIAENDO1
     * 3.1.1 inserisco su sigepro.tipifamiglieendo e sigepro.stpTipologieendo1 (controllo se già esiste)
     * 		mapping xml:nome = tipifamiglieendo.tipo
     * 
     * 3.2 elaboro i tag CATEGORIAENDO1
     * 3.2.1 inserisco su sigepro.tipiendo e stpCategorieendo1 (devo trovare il tipofamigliaendo collegato)
     * 		mapping xml:nome = tipiendo.tipo
     * 		mapping xml:tipologia = ??? 
     * 3.3 elaboro i tag ENDOTIPO1
     * 3.3.1 inserisco su sigepro.inventarioprocedimenti (devo trovare la categoria collegata)  
     * 		mapping xml:nome = inventarioprocedimenti.procedimento
     * 		mapping xml:codiceRegionale = inventarioprocedimenti.codiceancitel		
     * 		mapping xml:categoria = ??? 
     * 
     * 4 INSERISCO PROCEDIMENTI
     * 4.1 MEMORIZZO I TAG xml:tipologiaendotipo2
     * 4.2 separo le liste di categoriaAttività, attività, endoTipo2
     * 4.3 inserisco le categorie attività
     * 4.3.1 dal codice procedimento in configurazione trovo l'sc_codice
     * 	     inserisco tutte le categorie attività in alberoproc (sc_padre=1) e in stpEndotipo2
     * 4.3.2 inserisco le attività prendendo come padre xml:categoria
     * 
     * 4.3.4 inserisco xml:endotipo2 collegandolo a attività su stpEndoTipo2
     * 	     la descrizione dell'endo la prendo dalla lista xml:tipologiaendotipo2
     * 
     * 
     */
    public void elaboraMessaggio(String idMessaggio) {

	if (log.isDebugEnabled()) {
	    log.debug("FUNZIONE ELABORA MESSAGGIO. ID MESSAGGIO:{}", idMessaggio);
	}
	Software software = softwareService.findById(ORMHelper.getSoftware());
	SPCoopMessage message = this.getMessaggio(idMessaggio);
	// le credenziali determinano il routing dei vari messaggi
	String azione = message.getSpcoopHeaderInfo().getServizio();
	if (log.isDebugEnabled()) {
	    log.debug("L'azione del messaggio da elaborare è {}", azione);
	}
	if (azione.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioDizionario.toString())) {
	    if (log.isDebugEnabled()) {
		log.debug("Elaboro il messaggio di tipo {}", CartProxyService.MessaggiAzioni.InvioDizionario.toString());
	    }
	    String body = this.getMessaggioBody(message);
	    byte[] bodyByte = null;
	    try {
		bodyByte = body.getBytes("UTF-8");
	    } catch (UnsupportedEncodingException e1) {
		bodyByte = body.getBytes();
	    }
	    JAXBElement<InvioDizionario> po = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(InvioDizionario.class);
		Unmarshaller u = jc.createUnmarshaller();
		po = (JAXBElement<InvioDizionario>) u.unmarshal(new ByteArrayInputStream(bodyByte));
		InvioDizionario dizionario = po.getValue();
		List<Object> list = dizionario.getTipologiaEndoTipo1OrCategoriaEndoTipo1OrEndoTipo1();
		// DIVIDO list in TRE liste
		List<TipologiaEndoTipo1> tipologiaEndoTipo1s = new ArrayList<TipologiaEndoTipo1>();
		List<CategoriaEndoTipo1> categoriaEndoTipo1s = new ArrayList<CategoriaEndoTipo1>();
		List<EndoTipo1> endoTipo1s = new ArrayList<EndoTipo1>();
		List<EndoTipo2> endoTipo2s = new ArrayList<EndoTipo2>();
		Map<BigInteger, String> tipologiaEndoTipo2Map = new HashMap<BigInteger, String>();
		List<Attivita> attivitas = new ArrayList<Attivita>();
		List<CategorieTree> categorieTrees = new ArrayList<CategorieTree>();
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio di tipo {}, Prima di caricare in memoria gli oggetti",
			    CartProxyService.MessaggiAzioni.InvioDizionario.toString());
		}
		for (Object object : list) {
		    if (object instanceof TipologiaEndoTipo1) {
			tipologiaEndoTipo1s.add((TipologiaEndoTipo1) object);
		    }
		    if (object instanceof CategoriaEndoTipo1) {
			categoriaEndoTipo1s.add((CategoriaEndoTipo1) object);
		    }
		    if (object instanceof EndoTipo1) {
			endoTipo1s.add((EndoTipo1) object);
		    }
		    if (object instanceof EndoTipo2) {
			endoTipo2s.add((EndoTipo2) object);
		    }
		    if (object instanceof CategoriaAttivita) {
			CategorieTree categorieTree = new CategorieTree();
			categorieTree.setCategoriaAttivita((CategoriaAttivita) object);
			categorieTrees.add(categorieTree);
		    }
		    if (object instanceof TipologiaEndoTipo2) {
			tipologiaEndoTipo2Map.put(((TipologiaEndoTipo2) object).getId(), ((TipologiaEndoTipo2) object).getNome());
		    }
		    if (object instanceof Attivita) {
			attivitas.add((Attivita) object);
		    }
		}
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, Inserisco gli endo di tipo 1", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioDizionario.toString());
		}
		this.insertEndoProcTipo1(tipologiaEndoTipo1s, categoriaEndoTipo1s, endoTipo1s, software);
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, Inserisco gli endo di tipo 2", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioDizionario.toString());
		}
		this.endoProcTipo2(endoTipo2s, categorieTrees, attivitas, tipologiaEndoTipo2Map, software);
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, elimino il messaggio da quelli non elaborati", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioDizionario.toString());
		}
		this.deleteMessaggio(idMessaggio);
	    } catch (JAXBException e) {
		log.error("Errore in elaborazione del messaggio {}, JAXBException: {}", idMessaggio, e.getMessage());
		throw new RuntimeException(e.getMessage(), e);
	    }
	}
	/*
	 * Elabora Scheda Endo 2
	 */
	if (azione.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioSchedaEP.toString())) {
	    String body = this.getMessaggioBody(message);
	    JAXBElement<InvioSchedaEndoTipo2> po = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(InvioSchedaEndoTipo2.class);
		Unmarshaller u = jc.createUnmarshaller();
		byte[] content = null;
		try {
		    content = body.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		    content = body.getBytes();
		}
		po = (JAXBElement<InvioSchedaEndoTipo2>) u.unmarshal(new ByteArrayInputStream(content));
		InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = po.getValue();
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, Inserisco la scheda di tipo 2", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioSchedaEP.toString());
		}
		this.insertSchedaEndo2(invioSchedaEndoTipo2, body);
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, cancello il messaggio", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioSchedaEP.toString());
		}
		this.deleteMessaggio(idMessaggio);
	    } catch (JAXBException e) {
		log.error("Errore in elaborazione del messaggio {}, JAXBException: {}", idMessaggio, e.getMessage());
		throw new RuntimeException(e.getMessage(), e);
	    }
	}
	/*
	 * Elabora Scheda Endo 1
	 */
	if (azione.equalsIgnoreCase(CartProxyService.MessaggiAzioni.InvioSchedaEC.toString())) {
	    String body = this.getMessaggioBody(message);
	    JAXBElement<InvioSchedaEndoTipo1> po = null;
	    try {
		JAXBContext jc = JAXBContext.newInstance(InvioSchedaEndoTipo1.class);
		Unmarshaller u = jc.createUnmarshaller();
		byte[] content = null;
		try {
		    content = body.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
		    content = body.getBytes();
		}
		po = (JAXBElement<InvioSchedaEndoTipo1>) u.unmarshal(new ByteArrayInputStream(content));
		InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = po.getValue();
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, Inserisco la scheda di tipo 1", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioSchedaEC.toString());
		}
		this.insertSchedaEndo1(invioSchedaEndoTipo1, body);
		if (log.isDebugEnabled()) {
		    log.debug("Elaborazione del messaggio [{}] di tipo {}, Cancello il messaggio", idMessaggio,
			    CartProxyService.MessaggiAzioni.InvioSchedaEC.toString());
		}
		this.deleteMessaggio(idMessaggio);
	    } catch (JAXBException e) {
		log.error("Errore in elaborazione del messaggio {}, JAXBException: {}", idMessaggio, e.getMessage());
		throw new RuntimeException(e.getMessage(), e);
	    }
	}
    }

    private void insertSchedaEndo2(InvioSchedaEndoTipo2 invioSchedaEndoTipo2, String body) {

	Integer idEndo = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getEndoprocedimento().intValue();
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco la scheda dell'endo di tipo 2 dell'endo regionale {}", idEndo.intValue());
	}
	// cerco su stpendo ENDO e codice idEndo
	StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(idEndo, ENDO);
	if (null == stpEndoTipo2) {
	    log.error("Non è stata trovata la scheda endo di tipo 2 per stp_codice: {}", idEndo);
	    throw new RuntimeException("Non è stata trovata la scheda endo di tipo 2 per stp_codice: " + idEndo);
	}
	Oggetti oggetti = null;
	if (EntityUtils.getNestedProperty(stpEndoTipo2, "oggetti.id.codice") != null) {
	    oggetti = stpEndoTipo2.getOggetti();
	    try {
		oggetti.setOggetto(body.getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		log.error("Errore in insertSchedaEndo2 di tipo UnsupportedEncodingException: {}", e.getMessage());
		throw new RuntimeException("Errore in InsertSchedaEndo2 di tipo UnsupportedEncodingException", e.getCause());
	    }
	    oggetti.setDimensioneFile(oggetti.getOggetto().length);
	    oggetti.setNomefile("SchedaEndo2_" + idEndo.intValue() + ".xml");
	    if (log.isDebugEnabled()) {
		log.debug("Aggiorno il file {} nella tabella oggetti.", oggetti.getNomefile());
	    }
	} else {
	    oggetti = new Oggetti();
	    try {
		oggetti.setOggetto(body.getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		log.error("Errore in insertSchedaEndo2 di tipo UnsupportedEncodingException: {}", e.getMessage());
		throw new RuntimeException("Errore in InsertSchedaEndo2 di tipo UnsupportedEncodingException", e.getCause());
	    }
	    oggetti.setDimensioneFile(oggetti.getOggetto().length);
	    oggetti.setNomefile("SchedaEndo2_" + idEndo.intValue() + ".xml");
	    if (log.isDebugEnabled()) {
		log.debug("Inserisco il file {} nella tabella oggetti.", oggetti.getNomefile());
	    }
	    oggettiService.insert(oggetti);
	}
	if (log.isDebugEnabled()) {
	    log.debug("Il file {} è stato inserito nella tabella oggetti", oggetti.getNomefile());
	}
	stpEndoTipo2.setOggetti(oggetti);
	if (log.isDebugEnabled()) {
	    log.debug("Aggiorno la scheda dell'endo di tipo 2 col nuovo file");
	}
	stpEndoTipo2Service.update(stpEndoTipo2);
	Alberoproc alberoproc = stpEndoTipo2.getAlberoproc();
	List<Serializable> endoTipo1List = new ArrayList<Serializable>();
	List<Serializable> endoTipo1Dopo = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiDopo()
		.getEndoTipo1AndEndoObbligatorio();
	List<Serializable> endoTipo1Prima = invioSchedaEndoTipo2.getParteRegionaleSchedaEndoTipo2().getElencoEndoRegionaliPrevistiPrima()
		.getEndoTipo1AndEndoObbligatorio();
	endoTipo1List.addAll(endoTipo1Dopo);
	endoTipo1List.addAll(endoTipo1Prima);
	if (log.isDebugEnabled()) {
	    log.debug("Aggiorno la lista degli endo di AlberoProc sc_id: {}", alberoproc.getId().getCodice().intValue());
	}
	// List<AlberoprocEndo> listaEndo = alberoprocEndoService.findAllByAlberoproc(alberoproc.getId().getCodice());
	Set<AlberoprocEndo> listaEndo = alberoproc.getAlberoprocEndos();
	if (log.isDebugEnabled()) {
	    if (!listaEndo.isEmpty()) {
		log.debug("Cancello gli endo procedimenti già associati a AlberoProc sc_id: {}", alberoproc.getId().getCodice().intValue());
	    }
	}
	for (AlberoprocEndo alberoprocEndo : listaEndo) {
	    alberoprocEndoService.delete(alberoprocEndo);
	}
	alberoproc.setAlberoprocEndos(new HashSet<AlberoprocEndo>(0));
	if (!listaEndo.isEmpty()) {
	    log.debug("Finito di cancellare gli endo procedimenti già associati a AlberoProc sc_id: {}", alberoproc.getId().getCodice().intValue());
	}
	for (Serializable idEndo1 : endoTipo1List) {
	    if (idEndo1 instanceof BigInteger) {
		AlberoprocEndo alberoprocEndo = new AlberoprocEndo();
		AlberoprocEndoId id = new AlberoprocEndoId();
		alberoprocEndo.setAlberoproc(alberoproc);
		id.setFkscid(alberoproc.getId().getCodice());
		StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(((BigInteger) idEndo1).intValue());
		alberoprocEndo.setInventarioprocedimento(stpEndoTipo1.getInventarioprocedimenti());
		id.setCodiceinventario(stpEndoTipo1.getInventarioprocedimenti().getId().getCodice());
		id.setIdcomune(ORMHelper.getIdcomune());
		alberoprocEndo.setFlagRichiesto(true);
		String codiceazione = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_AZIONI);// verticalizzazioniparametriAZIONI.getValore();
		Azioni azioni = azioniService.findById(new Integer(codiceazione));
		alberoprocEndo.setAzione(azioni);
		alberoprocEndo.setId(id);
		if (log.isDebugEnabled()) {
		    log.debug("Assegno l'endo {}  a AlberoProc sc_id: {}", stpEndoTipo1.getInventarioprocedimenti().getId().getCodice().intValue(),
			    alberoproc.getId().getCodice().intValue());
		}
		alberoprocEndoService.insert(alberoprocEndo);
	    }
	}
    }

    private void insertSchedaEndo1(InvioSchedaEndoTipo1 invioSchedaEndoTipo1, String body) {

	Integer idEndo = invioSchedaEndoTipo1.getParteRegionaleSchedaEndoTipo1().getEndoprocedimento().intValue();
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco la scheda dell'endo di tipo 1 dell'endo regionale {}", idEndo.intValue());
	}
	// cerco su stpendo ENDO e codice idEndo
	StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(idEndo);
	if (null == stpEndoTipo1) {
	    log.error("Non è stata trovata la scheda endo di tipo 1 per stp_codice: {}", idEndo);
	    throw new RuntimeException("Non è stata trovata la scheda endo di tipo 1 per stp_codice: " + idEndo);
	}
	Oggetti oggetti = null;
	if (EntityUtils.getNestedProperty(stpEndoTipo1, "oggetti.id.codice") != null) {
	    oggetti = stpEndoTipo1.getOggetti();
	    try {
		oggetti.setOggetto(body.getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		log.error("Errore in insertSchedaEndo1 di tipo UnsupportedEncodingException: {}", e.getMessage());
		throw new RuntimeException("Errore in InsertSchedaEndo1 di tipo UnsupportedEncodingException", e.getCause());
	    }
	    oggetti.setDimensioneFile(oggetti.getOggetto().length);
	    oggetti.setNomefile("SchedaEndo1_" + idEndo.intValue() + ".xml");
	    if (log.isDebugEnabled()) {
		log.debug("Aggiorno il file {} nella tabella oggetti.", oggetti.getNomefile());
	    }
	} else {
	    oggetti = new Oggetti();
	    try {
		oggetti.setOggetto(body.getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException e) {
		log.error("Errore in insertSchedaEndo1 di tipo UnsupportedEncodingException: {}", e.getMessage());
		throw new RuntimeException("Errore in InsertSchedaEndo1 di tipo UnsupportedEncodingException", e.getCause());
	    }
	    oggetti.setDimensioneFile(oggetti.getOggetto().length);
	    oggetti.setNomefile("SchedaEndo1_" + idEndo.intValue() + ".xml");
	    if (log.isDebugEnabled()) {
		log.debug("Inserisco il file {} nella tabella oggetti.", oggetti.getNomefile());
	    }
	    oggettiService.insert(oggetti);
	}
	stpEndoTipo1.setOggetti(oggetti);
	if (log.isDebugEnabled()) {
	    log.debug("Aggiorno la tabella stp_Endo_Tipo1 codice_stp: {}", idEndo.intValue());
	}
	stpEndoTipo1Service.update(stpEndoTipo1);
    }

    private void insertEndoProcTipo1(List<TipologiaEndoTipo1> tipologiaEndoTipo1s, List<CategoriaEndoTipo1> categoriaEndoTipo1s,
	    List<EndoTipo1> endoTipo1s, Software software) {

	if (log.isDebugEnabled()) {
	    log.debug("Inizio l'inserimento degli endo di tipo 1 per il Modulo software {}", software.getCodice());
	}
	/*
	 * ENDO PROCEDIMENTI DI TIPO 1
	 */
	// TipologiaEndoTipo1
	Tipifamiglieendo tipifamiglieendo = null;
	Integer id = null;
	String nome = null;
	StpTipologieEndo1 stpTipologieEndo1 = null;
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco le tipologie endo ({})", tipologiaEndoTipo1s.size());
	}
	for (TipologiaEndoTipo1 tipologiaEndoTipo1 : tipologiaEndoTipo1s) {
	    id = tipologiaEndoTipo1.getId().intValue();
	    nome = tipologiaEndoTipo1.getNome();
	    if (log.isDebugEnabled()) {
		log.debug("Elaboro la tipologia endo ({})", nome);
	    }
	    stpTipologieEndo1 = stpTipologieEndo1Service.findbyStpCodice(id);
	    if (stpTipologieEndo1 == null) {
		if (log.isDebugEnabled()) {
		    log.debug("La tipologia non è censita la inserisco");
		}
		tipifamiglieendo = new Tipifamiglieendo();
		tipifamiglieendo.setTipo(nome);
		tipifamiglieendo.setSoftware(software);
		tipifamiglieendoService.insert(tipifamiglieendo);
		stpTipologieEndo1 = new StpTipologieEndo1();
		stpTipologieEndo1.setTipifamiglieendo(tipifamiglieendo);
		stpTipologieEndo1.setCodiceStp(id);
		stpTipologieEndo1.setDescrizione(nome);
		stpTipologieEndo1Service.insert(stpTipologieEndo1);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("La tipologia esiste la aggiorno");
		}
		tipifamiglieendo = stpTipologieEndo1.getTipifamiglieendo();
		tipifamiglieendo.setTipo(nome);
		tipifamiglieendoService.update(tipifamiglieendo);
	    }
	}
	// CategoriaEndoTipo1
	StpCategorieEndo1 stpCategorieEndo1 = null;
	Integer tipologia = null;
	id = null;
	nome = null;
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco le categorie endo ({})", categoriaEndoTipo1s.size());
	}
	for (CategoriaEndoTipo1 categoriaEndoTipo1 : categoriaEndoTipo1s) {
	    Tipiendo tipiendo = null;
	    id = categoriaEndoTipo1.getId().intValue();
	    nome = categoriaEndoTipo1.getNome();
	    if (log.isDebugEnabled()) {
		log.debug("Elaboro la categoria endo ({})", nome);
	    }
	    tipologia = categoriaEndoTipo1.getTipologia().intValue();
	    stpCategorieEndo1 = stpCategorieEndo1Service.findByStpCodice(id);
	    if (stpCategorieEndo1 == null) {
		if (log.isDebugEnabled()) {
		    log.debug("La categoria non esiste la inserisco");
		}
		tipiendo = new Tipiendo();
		tipiendo.setSoftware(software);
		tipiendo.setTipo(nome);
		stpTipologieEndo1 = stpTipologieEndo1Service.findbyStpCodice(tipologia);
		tipiendo.setTipifamiglieendo(stpTipologieEndo1.getTipifamiglieendo());
		tipiendoService.insert(tipiendo);
		stpCategorieEndo1 = new StpCategorieEndo1();
		stpCategorieEndo1.setCodiceStp(id);
		stpCategorieEndo1.setDescrizione(nome);
		stpCategorieEndo1.setTipiendo(tipiendo);
		stpCategorieEndo1Service.insert(stpCategorieEndo1);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("La categoria esiste la aggiorno");
		}
		tipiendo = stpCategorieEndo1.getTipiendo();
		tipiendo.setTipo(nome);
		tipiendoService.update(tipiendo);
	    }
	}
	// EndoTipo1
	Integer categoria = null;
	String codiceRegionale = null;
	StpEndoTipo1 stpEndoTipo1 = null;
	id = null;
	nome = null;
	/*
	 * NATURAENDO.CODICENATURA
	 */
	String codicenatura = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA);// verticalizzazioniparametriCODICENATURA.getValore();
	Naturaendo naturaendo = naturaendoService.findById(new NaturaendoId(new Integer(codicenatura)));
	//Naturaendo naturaendo = naturaendoService.findById(new PkId(new Integer(codicenatura)));
	/*
	 * TIPIMOVIMENTO.TIPOMOVIMENTO
	 */
	Tipimovimento tipimovimento = null;
	String tipomovimento = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	if (StringUtils.isNotBlank(tipomovimento)) {
	    tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), tipomovimento));
	}
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco gli endoprocedimenti di tipo 1 ({})", endoTipo1s.size());
	}
	for (EndoTipo1 endoTipo1 : endoTipo1s) {
	    Inventarioprocedimenti inventarioprocedimenti = null;
	    id = endoTipo1.getId().intValue();
	    categoria = endoTipo1.getCategoria().intValue();
	    codiceRegionale = endoTipo1.getCodiceRegionale();
	    String codiceAmministrazione = codiceRegionale;
	    if (codiceRegionale.indexOf(" ") != -1) {
		codiceAmministrazione = codiceRegionale.substring(0, codiceRegionale.indexOf(" "));
	    }
	    Amministrazioni amministrazioni = amministrazioniService.findAmministrazioniByCodiceancitel(codiceAmministrazione);
	    if (amministrazioni == null) {
		log.error("Non è stata trovata l'amministrazione con codice {} per l'endo: {}", codiceRegionale, id);
		throw new RuntimeException("Non è stata trovata l'amministrazione con codice " + codiceRegionale + " per l'endo: " + id);
	    }
	    nome = endoTipo1.getNome();
	    if (log.isDebugEnabled()) {
		log.debug("Elaboro l'endo di tipo 1 con id: {}, nome: {}", id, nome);
	    }
	    stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(id);
	    if (stpEndoTipo1 == null) {
		if (log.isDebugEnabled()) {
		    log.debug("L'endo non esiste lo inserisco in inventarioprocedimenti");
		}
		inventarioprocedimenti = new Inventarioprocedimenti();
		inventarioprocedimenti.setAmministrazioni(amministrazioni);
		inventarioprocedimenti.setProcedimento(nome);
		inventarioprocedimenti.setCodiceancitel(codiceRegionale);
		inventarioprocedimenti.setSoftware(software);
		inventarioprocedimenti.setDataaggiornamento(new Date());
		inventarioprocedimenti.setNaturaendo(naturaendo);
		inventarioprocedimenti.setTipomovimento(tipimovimento);
		stpCategorieEndo1 = stpCategorieEndo1Service.findByStpCodice(categoria);
		inventarioprocedimenti.setTipoendo(stpCategorieEndo1.getTipiendo());
		inventarioprocedimenti.setOrdine(id);
		inventarioprocedimentiService.insert(inventarioprocedimenti);
		if (log.isDebugEnabled()) {
		    log.debug("L'endo non esiste lo inserisco in stp_Endo_Tipo1");
		}
		stpEndoTipo1 = new StpEndoTipo1();
		stpEndoTipo1.setCodiceStp(id);
		stpEndoTipo1.setInventarioprocedimenti(inventarioprocedimenti);
		stpEndoTipo1Service.insert(stpEndoTipo1);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("L'endo esiste lo aggiorno in inventario procedimenti");
		}
		inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
		inventarioprocedimenti.setAmministrazioni(amministrazioni);
		inventarioprocedimenti.setProcedimento(nome);
		inventarioprocedimenti.setCodiceancitel(codiceRegionale);
		inventarioprocedimenti.setNaturaendo(naturaendo);
		inventarioprocedimenti.setTipomovimento(tipimovimento);
		inventarioprocedimenti.setDataaggiornamento(new Date());
		if (inventarioprocedimenti.getOrdine() == null) {
		    inventarioprocedimenti.setOrdine(id);
		}
		inventarioprocedimentiService.update(inventarioprocedimenti);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("Ho finito l'inserimento degli endo di tipo 1");
	}
    }

    @SuppressWarnings("unchecked")
    private void endoProcTipo2(List<EndoTipo2> endoTipo2s, List<CategorieTree> categorieTrees, List<Attivita> attivitas,
	    Map<BigInteger, String> tipologiaEndoTipo2Map, Software software) {

	Integer id = null;
	String nome = null;
	Integer categoria = null;
	if (log.isDebugEnabled()) {
	    log.debug("Inizio l'inserimento degli endo di tipo 2");
	}
	/*
	 * ENDO PROCEDIMENTI DI TIPO 2
	 */
	if (log.isDebugEnabled()) {
	    log.debug("Carico in memoria l'alberatura dei procedimenti");
	}
	for (CategorieTree categorieTree1 : categorieTrees) {
	    for (CategorieTree categorieTree2 : categorieTrees) {
		if (categorieTree1.getCategoriaAttivita().getCategoriaPadre() != null) {
		    if (categorieTree1.getCategoriaAttivita().getCategoriaPadre().compareTo(categorieTree2.getCategoriaAttivita().getId()) == 0) {
			categorieTree1.setPadre(categorieTree2);
		    }
		}
	    }
	}
	CategorieTree root = new CategorieTree();
	for (CategorieTree categorieTree : categorieTrees) {
	    if (categorieTree.getPadre() != null) {
		categorieTree.getPadre().getFigli().add(categorieTree);
	    } else {
		root.getFigli().add(categorieTree);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("Chiamo l'inserimento in alberoProc");
	}
	this.insertAlberoProc(root, software);
	if (log.isDebugEnabled()) {
	    log.debug("Finito l'inserimento in alberoProc");
	}
	// INSERISCO ATTIVITA
	Map<String, List<Attivita>> map = new HashMap<String, List<Attivita>>();
	if (log.isDebugEnabled()) {
	    log.debug("Elaboro {} attività: ", attivitas.size());
	}
	for (Attivita attivita : attivitas) {
	    if (log.isDebugEnabled()) {
		log.debug("elaboro attività {} con codice {} ({})",
			new Object[] { attivitas.indexOf(attivita), attivita.getId(), attivita.getCodice() });
	    }
	    categoria = attivita.getCategoria().intValue();
	    id = attivita.getId().intValue();
	    StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(categoria, CATEGORIA);
	    if (stpEndoTipo2 != null) {
		Alberoproc alberoproc = stpEndoTipo2.getAlberoproc();
		List<Attivita> attivitaList = map.get(alberoproc.getScCodice());
		if (attivitaList == null) {
		    attivitaList = new ArrayList<Attivita>();
		}
		attivitaList.add(attivita);
		map.put(alberoproc.getScCodice(), attivitaList);
	    } else {
		log.error("CATEGORIA ({}) NON TROVATA PER L'ATTIVITA' ({})", categoria, id);
		throw new RuntimeException("CATEGORIA NON TROVATA");
	    }
	}
	Iterator it = map.entrySet().iterator();
	List<Attivita> list2 = null;
	String indice = null;
	while (it.hasNext()) {
	    Map.Entry<String, List<Attivita>> pairs = (Map.Entry) it.next();
	    String sccodice = pairs.getKey();
	    list2 = pairs.getValue();
	    for (Attivita attivita : list2) {
		++vociElaborate;
		String currentTemp = null;
		int index = list2.indexOf(attivita);
		index += 1;
		boolean trovato = false;
		while (!trovato) {
		    currentTemp = sccodice;
		    indice = String.valueOf(index);
		    if (index < 10) {
			indice = "0" + (indice);
		    }
		    currentTemp = currentTemp + (indice);
		    Alberoproc alberoprocTemp = alberoprocService.findByScCodice(currentTemp);
		    if (alberoprocTemp != null) {
			index++;
		    } else {
			trovato = true;
		    }
		}
		nome = attivita.getNome();
		int idAtt = attivita.getId().intValue();
		StpEndoTipo2 endoTipo2 = stpEndoTipo2Service.findbyStpCodice(idAtt, ATTIVITA);
		if (endoTipo2 == null) {
		    Alberoproc albproc = new Alberoproc();
		    albproc.setScCodice(currentTemp);
		    albproc.setSoftware(software);
		    String descrizione = nome;
		    if (descrizione.length() > 300) {
			descrizione = descrizione.substring(0, 299);
		    }
		    albproc.setScDescrizione(descrizione);
		    albproc.setScNote(nome);
		    albproc.setScOrdine(index);
		    albproc.setScAttivo(false);
		    albproc.setScPadre(true);
		    albproc.setAzione(null);
		    albproc.setScPubblica(statoPubblicazioneAlberoproc);
		    albproc.setScStatoControllo("I");
		    alberoprocService.insert(albproc);
		    endoTipo2 = new StpEndoTipo2();
		    endoTipo2.setAlberoproc(albproc);
		    endoTipo2.setCodiceStp(idAtt);
		    endoTipo2.setTipo(ATTIVITA);
		    stpEndoTipo2Service.insert(endoTipo2);
		} else {
		    Alberoproc albproc = new Alberoproc();
		    albproc = endoTipo2.getAlberoproc();
		    albproc.setScCodice(currentTemp);
		    String descrizione = nome;
		    if (descrizione.length() > 300) {
			descrizione = descrizione.substring(0, 299);
		    }
		    albproc.setScNote(nome);
		    albproc.setScDescrizione(descrizione);
		    albproc.setScOrdine(index);
		    alberoprocService.update(albproc);
		}
		if (log.isDebugEnabled()) {
		    log.debug("inserisco/aggiorno attività {} con codice {} ({}) sccodice {}",
			    new Object[] { list2.indexOf(attivita), attivita.getId(), attivita.getCodice(), currentTemp });
		}
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("Elaborate {} voci: ", attivitas.size());
	    log.debug("Comincio l'elaborazione degli endo tipo 2");
	}
	// ENDOTIPO2
	Map<String, List<EndoTipo2>> mapEndo2 = new HashMap<String, List<EndoTipo2>>();
	for (EndoTipo2 endoTipo2 : endoTipo2s) {
	    if (log.isDebugEnabled()) {
		log.debug("elaboro endo {} con codice {} ", new Object[] { endoTipo2s.indexOf(endoTipo2), endoTipo2.getId() });
	    }
	    Integer attivita = endoTipo2.getAttivita().intValue();
	    StpEndoTipo2 stpEndoo2 = stpEndoTipo2Service.findbyStpCodice(attivita, ATTIVITA);
	    if (stpEndoo2 != null) {
		Alberoproc alberoproc = stpEndoo2.getAlberoproc();
		List<EndoTipo2> endoTipo2List = mapEndo2.get(alberoproc.getScCodice());
		if (endoTipo2List == null) {
		    endoTipo2List = new ArrayList<EndoTipo2>();
		}
		endoTipo2List.add(endoTipo2);
		mapEndo2.put(alberoproc.getScCodice(), endoTipo2List);
	    } else {
		log.error("ATTIVITA' ({}) NON TROVATA ", attivita);
		throw new RuntimeException("ATTIVITA' NON TROVATA");
	    }
	}
	/*
	 * RECUPERO PARAMETRI VERTICALIZZAZIONE CART VERIFICO SE E' ABILITATA LA GESTIONE DEGLI INVENTARIPROCEDIMENTI.
	 */
	String valoreInventario = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO);// verticalizzazioniparametriINVENTARIO.getValore();
	boolean isAbilitatoInserimentoInventario = false;
	if (valoreInventario.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO)) {
	    isAbilitatoInserimentoInventario = true;
	} else {
	    isAbilitatoInserimentoInventario = false;
	}
	Naturaendo naturaendo = null;
	Tipimovimento tipimovimento = null;
	Amministrazioni amministrazioni = null;
	Azioni azioni = null;
	Tipifamiglieendo tipifamiglieendo = null;
	if (isAbilitatoInserimentoInventario) {
	    /*
	     * NATURAENDO.CODICENATURA
	     */
	    String codicenatura = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA); // verticalizzazioniparametriCODICENATURA.getValore();
	    naturaendo = naturaendoService.findById(new NaturaendoId(new Integer(codicenatura)));
	    //naturaendo = naturaendoService.findById(new PkId(new Integer(codicenatura)));
	    /*
	     * TIPIMOVIMENTO.TIPOMOVIMENTO
	     */
	    String tipomovimento = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	    // if (verticalizzazioniparametriTIPIMOVIMENTO != null) {
	    if (StringUtils.isNotBlank(tipomovimento)) {
		tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), tipomovimento));
	    }
	    /*
	     * AMMINISTRAZIONE. PARAMETRO VERTICALIZZAZIONE: ENDO2_INVENTARIOPROC.AMMIN
	     */
	    String codiceammin = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_AMMINISTRAZIONE); // verticalizzazioniparametriAMMINISTRAZIONE.getValore();
	    amministrazioni = amministrazioniService.findById(new PkId(new Integer(codiceammin)));
	    /*
	     * AZIONI
	     */
	    String codiceazione = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_AZIONI);// verticalizzazioniparametriAZIONI.getValore();
	    azioni = azioniService.findById(new Integer(codiceazione));
	    /*
	     * ENDO2_TIPIFAMIGLIEENDO.CODICE
	     */
	    String codiceFAMIGLIAENDO = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO);// verticalizzazioniparametriFAMIGLIAENDO.getValore();
	    tipifamiglieendo = tipifamiglieendoService.findById(new PkId(new Integer(codiceFAMIGLIAENDO)));
	}
	Iterator itEndo = mapEndo2.entrySet().iterator();
	if (log.isDebugEnabled()) {
	    if (isAbilitatoInserimentoInventario) {
		log.debug("È abilitato l'inserimento degli endo di tipo 2 su inventario procedimenti");
	    } else {
		log.debug("Non è abilitato l'inserimento degli endo di tipo 2 su inventario procedimenti");
	    }
	}
	while (itEndo.hasNext()) {
	    Map.Entry pairs = (Map.Entry) itEndo.next();
	    String sccodice = (String) pairs.getKey();
	    // CONTROLLO SE E' ABILITATO INSERIMENTO INVENTARIOPROCEDIMENTO DI TIPO 2
	    Tipiendo tipiendo = null;
	    if (isAbilitatoInserimentoInventario) {
		/*
		 * RECUPERO LA PRIMA VOCE DELL'ALBERO. LA CATEGORIA VA INSERITA IN STPCATEGORIEENDO1 E IN TIPIENDO
		 */
		String scCodicecategoria = sccodice.substring(0, 4);
		Alberoproc alberoprocCategoria = alberoprocService.findByScCodice(scCodicecategoria);
		String descrizioneCategoria = alberoprocCategoria.getScDescrizione();
		tipiendo = new Tipiendo();
		tipiendo.setTipifamiglieendo(tipifamiglieendo);
		tipiendo.setSoftware(software);
		tipiendo.setTipo(descrizioneCategoria);
		tipiendo.setOrdine(0);
		if (log.isDebugEnabled()) {
		    log.debug("Inserisco il tipo di endo {}", descrizioneCategoria);
		}
		tipiendoService.insert(tipiendo);
		if (log.isDebugEnabled()) {
		    log.debug("Inserito il tipo di endo {}", descrizioneCategoria);
		}
	    }
	    Alberoproc alberoprocAttivita = alberoprocService.findByScCodice(sccodice);
	    List<EndoTipo2> listEndoTipo2 = (List<EndoTipo2>) pairs.getValue();
	    if (log.isDebugEnabled()) {
		log.debug("Inserisco le attività");
	    }
	    for (EndoTipo2 endoTipo2 : listEndoTipo2) {
		if (log.isDebugEnabled()) {
		    log.debug("inserisco/aggiorno endo {} con codice {}", new Object[] { listEndoTipo2.indexOf(endoTipo2), endoTipo2.getId() });
		}
		++vociElaborate;
		String currentTemp = sccodice;
		int index = listEndoTipo2.indexOf(endoTipo2);
		index += 1;
		indice = String.valueOf(index);
		if (index < 10) {
		    indice = "0".concat(indice);
		}
		currentTemp = currentTemp.concat(indice);
		String tipologiaStr = tipologiaEndoTipo2Map.get(endoTipo2.getTipologia());
		int idEndo = endoTipo2.getId().intValue();
		Alberoproc alberoproc = new Alberoproc();
		Inventarioprocedimenti inventarioprocedimenti = new Inventarioprocedimenti();
		if (isAbilitatoInserimentoInventario) {
		    // Aggiungo voce su INVENTARIOPROCEDIMENTI
		    // CODICENATURA
		    inventarioprocedimenti.setNaturaendo(naturaendo);
		    // TIPOMOVIMENTO
		    inventarioprocedimenti.setTipomovimento(tipimovimento);
		    // AMMINISTRAZIONE. PARAMETRO VERTICALIZZAZIONE: ENDO2_INVENTARIOPROC.AMMIN
		    inventarioprocedimenti.setAmministrazioni(amministrazioni);
		    // TIPOENDO
		    inventarioprocedimenti.setTipoendo(tipiendo);
		}
		StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(idEndo, ENDO);
		if (stpEndoTipo2 == null) {
		    if (log.isDebugEnabled()) {
			log.debug("Non ho trovato STP_ENDO_TIPO2 per il procedimento {}", tipologiaStr);
		    }
		    // INIZIO BLOCCO TRICK QUESTO CODICE È STATO INSERITO PERCHÈ RICALCOLANDO SC_CODICE POTREI
		    // AGGIORNARE RECORD CHE GIA' USANO CURRENTTEMP COME VALORE E PROVOCANO VIOLATA RESTRIZIONE DI
		    // UNIVOCITA' SU IDX_ALBEROPROC_001
		    trickAlberoprocRecord(currentTemp);
		    // FINE BLOCCO TRICK
		    alberoproc.setScCodice(currentTemp);
		    alberoproc.setSoftware(software);
		    alberoproc.setScDescrizione(tipologiaStr);
		    alberoproc.setScOrdine(index);
		    alberoproc.setScAttivo(false);
		    alberoproc.setScPadre(false);
		    alberoproc.setAzione(null);
		    alberoproc.setScPubblica(statoPubblicazioneAlberoproc);
		    alberoproc.setScStatoControllo("I");
		    if (log.isDebugEnabled()) {
			log.debug("Inserisco l'attività {}", tipologiaStr);
		    }
		    alberoprocService.insert(alberoproc);
		    /*
		     * INIZIO LOGICA stpEndoTipo2Service.insert Andrea Chiocci 09/06/2010 Preparo stpEndoTipo2 per
		     * l'inserimento con il service stpEndoTipo2Service.insert(stpEndoTipo2) L'inserimento sarà
		     * effettuato dopo aver controllato se c'è bisogno di aggiungere il codiceinventario
		     * (isAbilitatoInserimentoInventario=true)
		     */
		    stpEndoTipo2 = new StpEndoTipo2();
		    stpEndoTipo2.setAlberoproc(alberoproc);
		    stpEndoTipo2.setCodiceStp(idEndo);
		    stpEndoTipo2.setTipo(ENDO);
		    /*
		     * Continua LOGICA stpEndoTipo2Service.insert
		     */
		    if (isAbilitatoInserimentoInventario) {
			// Descrizione costituita dalla descrizione dell' endo concatenato alla descrizione
			// dell'attività
			String procedimento = alberoproc.getScDescrizione() + " - " + alberoprocAttivita.getScDescrizione();
			if (procedimento.length() > 255) {
			    procedimento = procedimento.substring(0, 255);
			}
			inventarioprocedimenti.setProcedimento(procedimento);
			inventarioprocedimenti.setSoftware(software);
			/*
			 * FIXME - CART - CONTROLLARE INSERIMENTO INVENTARIOPROCEDIMENTI
			 */
			inventarioprocedimenti.setDataaggiornamento(new Date());
			if (log.isDebugEnabled()) {
			    log.debug("Inserisco su inventario procedimenti {}", procedimento);
			}
			inventarioprocedimenti.setOrdine(idEndo);
			inventarioprocedimentiService.insert(inventarioprocedimenti);
			/*
			 * END FIX
			 */
			// CONTINUA LOGICA stpEndoTipo2Service.insert
			stpEndoTipo2.setInventarioprocedimenti(inventarioprocedimenti);
			AlberoprocEndo alberoprocEndo = new AlberoprocEndo();
			AlberoprocEndoId alberoprocEndoId = new AlberoprocEndoId();
			alberoprocEndoId.setFkscid(alberoproc.getId().getCodice());
			alberoprocEndoId.setCodiceinventario(inventarioprocedimenti.getId().getCodice());
			alberoprocEndo.setId(alberoprocEndoId);
			alberoprocEndo.setFlagRichiesto(true);
			alberoprocEndo.setAzione(azioni);
			if (log.isDebugEnabled()) {
			    log.debug("Inserisco su alberoprocendo");
			}
			alberoprocEndoService.insert(alberoprocEndo);
		    }
		    // FINE LOGICA stpEndoTipo2Service.insert
		    stpEndoTipo2Service.insert(stpEndoTipo2);
		} else {
		    if (log.isDebugEnabled()) {
			log.debug("Il procedimento {} è già presente in STP_ENDO_TIPO2", tipologiaStr);
		    }
		    // INIZIO BLOCCO TRICK QUESTO CODICE È STATO INSERITO PERCHÈ RICALCOLANDO SC_CODICE POTREI
		    // AGGIORNARE RECORD CHE GIA' USANO CURRENTTEMP COME VALORE E PROVOCANO VIOLATA RESTRIZIONE DI
		    // UNIVOCITA' SU IDX_ALBEROPROC_001
		    trickAlberoprocRecord(currentTemp);
		    // FINE BLOCCO TRICK
		    alberoproc = stpEndoTipo2.getAlberoproc();
		    alberoproc.setScCodice(currentTemp);
		    alberoproc.setScDescrizione(tipologiaStr);
		    alberoproc.setScOrdine(index);
		    if (log.isDebugEnabled()) {
			log.debug("Aggiorno alberoproc");
		    }
		    alberoprocService.update(alberoproc);
		    if (isAbilitatoInserimentoInventario) {
			if (stpEndoTipo2.getInventarioprocedimenti() != null) {
			    inventarioprocedimenti = stpEndoTipo2.getInventarioprocedimenti();
			}
			// Descrizione costituita dalla descrizione dell' endo concatenato alla descrizione
			// dell'attività
			String procedimento = alberoproc.getScDescrizione() + " - " + alberoprocAttivita.getScDescrizione();
			if (procedimento.length() > 255) {
			    procedimento = procedimento.substring(0, 255);
			}
			inventarioprocedimenti.setSoftware(software);
			inventarioprocedimenti.setProcedimento(procedimento);
			inventarioprocedimenti.setDataaggiornamento(new Date());
			if (inventarioprocedimenti.getOrdine() == null) {
			    inventarioprocedimenti.setOrdine(idEndo);
			}
			if (stpEndoTipo2.getInventarioprocedimenti() != null) {
			    if (log.isDebugEnabled()) {
				log.debug("Aggiorno su inventario procedimenti {}", procedimento);
			    }
			    inventarioprocedimentiService.update(inventarioprocedimenti);
			} else {
			    if (log.isDebugEnabled()) {
				log.debug("Inserisco su inventario procedimenti {}", procedimento);
			    }
			    inventarioprocedimentiService.insert(inventarioprocedimenti);
			}
			stpEndoTipo2.setInventarioprocedimenti(inventarioprocedimenti);
			if (log.isDebugEnabled()) {
			    log.debug("Aggiorno stp_endo_tipo2");
			}
			stpEndoTipo2Service.update(stpEndoTipo2);
			AlberoprocEndo alberoprocEndo = new AlberoprocEndo();
			AlberoprocEndoId alberoprocEndoId = new AlberoprocEndoId();
			alberoprocEndoId.setFkscid(alberoproc.getId().getCodice());
			alberoprocEndoId.setCodiceinventario(inventarioprocedimenti.getId().getCodice());
			alberoprocEndo.setId(alberoprocEndoId);
			alberoprocEndo.setFlagRichiesto(true);
			alberoprocEndo.setAzione(azioni);
			if (log.isDebugEnabled()) {
			    log.debug("Inserisco su AlberoprocEndo");
			}
			alberoprocEndoService.insert(alberoprocEndo);
		    }
		}
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("Elaborate {} voci: ", attivitas.size());
	}
    }

    /**
     * 
     * @param currentTemp
     */
    private void trickAlberoprocRecord(String currentTemp) {

	// INIZIO BLOCCO TRICK QUESTO CODICE È STATO INSERITO PERCHÈ RICALCOLANDO SC_CODICE POTREI
	// AGGIORNARE RECORD CHE GIA' USANO CURRENTTEMP COME VALORE E PROVOCANO VIOLATA RESTRIZIONE DI
	// UNIVOCITA' SU IDX_ALBEROPROC_001
	Alberoproc alberoprocDaControllare = alberoprocService.findByScCodice(currentTemp);
	if (null != alberoprocDaControllare && alberoprocDaControllare.getId() != null && alberoprocDaControllare.getId().getCodice() != null) {
	    alberoprocDaControllare.setScCodice(String.valueOf(System.currentTimeMillis()));
	    alberoprocService.update(alberoprocDaControllare);
	}
    }

    private void insertAlberoProc(CategorieTree root, Software software) {

	String idAlberoproc = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC);// verticalizzazioniparametriROOTALBEROPROC.getValore();
	if (root.getFigli().size() > 0) {
	    if (log.isDebugEnabled()) {
		log.debug("insertAlberoProc: root.size {}", root.getFigli().size());
	    }
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(new Integer(idAlberoproc)));
	    if (alberoproc != null) {
		insertAlberoProcRic(root, alberoproc.getScCodice(), software);
		alberoproc.setScPadre(true);
		alberoprocService.update(alberoproc);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("insertAlberoProc: elaborati {} voci", vociElaborate);
	    }
	}
    }

    private void insertAlberoProcRic(CategorieTree ct, final String currentRes, Software software) {

	List<CategorieTree> list = ct.getFigli();
	String currentTemp = null;
	String indice = null;
	int index = 0;
	String descrizione = null;
	if (!list.isEmpty()) {
	    for (CategorieTree categorieTree : list) {
		currentTemp = currentRes;
		index = list.indexOf(categorieTree);
		index += 1;
		indice = String.valueOf(index);
		if (index < 10) {
		    indice = "0".concat(indice);
		}
		currentTemp = currentTemp.concat(indice);
		if (log.isDebugEnabled()) {
		    log.debug("insertAlberoProcRic: \n\tscCodice: {}, \n\tcodiceStp: {}", currentTemp, categorieTree.getCategoriaAttivita().getId()
			    .intValue());
		}
		StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(categorieTree.getCategoriaAttivita().getId().intValue(), CATEGORIA);
		Alberoproc alberoproc = null;
		++vociElaborate;
		if (stpEndoTipo2 == null) {
		    alberoproc = new Alberoproc();
		    alberoproc.setScCodice(currentTemp);
		    alberoproc.setSoftware(software);
		    descrizione = categorieTree.getCategoriaAttivita().getNome();
		    if (descrizione.length() > 300) {
			descrizione = descrizione.substring(0, 299);
		    }
		    alberoproc.setScDescrizione(descrizione);
		    alberoproc.setScNote(categorieTree.getCategoriaAttivita().getNome());
		    alberoproc.setScOrdine(index);
		    alberoproc.setScAttivo(false);
		    alberoproc.setScPadre(true);
		    alberoproc.setAzione(null);
		    alberoproc.setScPubblica(statoPubblicazioneAlberoproc);
		    alberoproc.setScStatoControllo("I");
		    alberoprocService.insert(alberoproc);
		    stpEndoTipo2 = new StpEndoTipo2();
		    stpEndoTipo2.setAlberoproc(alberoproc);
		    stpEndoTipo2.setCodiceStp(categorieTree.getCategoriaAttivita().getId().intValue());
		    stpEndoTipo2.setTipo(CATEGORIA);
		    stpEndoTipo2Service.insert(stpEndoTipo2);
		} else {
		    alberoproc = stpEndoTipo2.getAlberoproc();
		    alberoproc.setScCodice(currentTemp);
		    descrizione = categorieTree.getCategoriaAttivita().getNome();
		    if (descrizione.length() > 300) {
			descrizione = descrizione.substring(0, 299);
		    }
		    alberoproc.setScDescrizione(descrizione);
		    alberoproc.setScNote(categorieTree.getCategoriaAttivita().getNome());
		    alberoproc.setScOrdine(index);
		    alberoprocService.update(alberoproc);
		}
		if (!categorieTree.getFigli().isEmpty()) {
		    if (log.isDebugEnabled()) {
			log.debug("insertAlberoProcRic: \n\t elaboro figli: {}", categorieTree.getFigli().size());
		    }
		    insertAlberoProcRic(categorieTree, currentTemp, software);
		}
	    }
	}
    }

    @Override
    public String getDescrizioneMessaggio(String idMessaggio) {

	return "descrizione messaggio=".concat(idMessaggio);
    }

    @Override
    public String[] getMessaggiPerServizio(MessaggiAzioni azione) {

	String[] result = null;
	try {
	    // non deve più essere invocato quello per azione
	    // result = cartWsClientService.getAllMessagesIdByService(azione.name());
	    result = cartWsClientService.getAllMessagesId();
	} catch (SPCoopException e) {
	    log.error("getMessaggiPerServizio '{}', errore: {}", azione.toString(), e.getDescrizioneEccezione());
	    throw new RuntimeException("getMessaggiPerServizio '" + azione.toString() + "', " + e.getDescrizioneEccezione(), e.getCause());
	} catch (RemoteException e) {
	    log.error("getMessaggiPerServizio '{}', errore: {}", azione.toString(), e.getMessage());
	    throw new RuntimeException("getMessaggiPerServizio '" + azione.toString() + "', " + e.getMessage(), e.getCause());
	} catch (MalformedURLException e) {
	    log.error("getMessaggiPerServizio '{}', errore: {}", azione.toString(), e.getMessage());
	    throw new RuntimeException("getMessaggiPerServizio '" + azione.toString() + "', " + e.getMessage(), e.getCause());
	} catch (ServiceException e) {
	    log.error("getMessaggiPerServizio '{}', errore: {}", azione.toString(), e.getMessage());
	    throw new RuntimeException("getMessaggiPerServizio '" + azione.toString() + "', " + e.getMessage(), e.getCause());
	}
	return result;
    }

    @Override
    public void inviaRichiestaDizionario() {

	if (log.isDebugEnabled()) {
	    log.debug("Invio della richiesta nuovo dizionario");
	}
	SPCoopMessage message = prepareMessage(CartProxyService.MessaggiAzioni.DownloadDizionario.toString());
	RichiestaDizionario body = new RichiestaDizionario();
	try {
	    JAXBContext jc = JAXBContext.newInstance(RichiestaDizionario.class);
	    Marshaller m = jc.createMarshaller();
	    // è necessario eliminare l'xml declaration
	    m.setProperty("jaxb.fragment", true);
	    StringWriter writer = new StringWriter();
	    m.marshal(body, writer);
	    String messaggioString = writer.toString();
	    String xmlSOAPdata = PREFISSO_BUSTA_SOAP + messaggioString + POSTFISSO_BUSTA_SOAP;
	    message.setMessage(xmlSOAPdata.getBytes("UTF-8"));
	    if (log.isDebugEnabled()) {
		log.debug("Invio della richiesta dizionario: {}", xmlSOAPdata);
	    }
	} catch (PropertyException e1) {
	    log.error("Errore in inviaRichiestaDizionario di tipo PropertyException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaRichiestaDizionario di tipo PropertyException " + e1.getMessage(), e1.getCause());
	} catch (UnsupportedEncodingException e1) {
	    log.error("Errore in inviaRichiestaDizionario di tipo UnsupportedEncodingException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaRichiestaDizionario di tipo UnsupportedEncodingException " + e1.getMessage(), e1.getCause());
	} catch (JAXBException e1) {
	    log.error("Errore in inviaRichiestaDizionario di tipo JAXBException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaRichiestaDizionario di tipo JAXBException " + e1.getMessage(), e1.getCause());
	}
	String PDD_LOCATION = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION);
	if (log.isDebugEnabled()) {
	    log.debug("COMPLETATA INVIO RICHIESTA DIZIONARIO: {}", CartWsClientServiceImpl.getDescrizioneMessaggio(message));
	}
	try {
	    cartWsClientService.invocaPortaDelegata(PDD_LOCATION, message);
	} catch (SPCoopException e) {
	    log.error("inviaRichiestaDizionario, errore: {}-{}\n{}",
		    new Object[] { e.getCodiceEccezione(), e.getDescrizioneEccezione(), e.toString() });
	    throw new RuntimeException("inviaRichiestaDizionario, " + e.getDescrizioneEccezione(), e.getCause());
	} catch (RemoteException e) {
	    log.error("inviaRichiestaDizionario, errore: {}", e.getMessage());
	    throw new RuntimeException("inviaRichiestaDizionario, " + e.getMessage(), e.getCause());
	} catch (MalformedURLException e) {
	    log.error("inviaRichiestaDizionario, errore: {}", e.getMessage());
	    throw new RuntimeException("inviaRichiestaDizionario, " + e.getMessage(), e.getCause());
	} catch (ServiceException e) {
	    log.error("inviaRichiestaDizionario, errore: {}", e.getMessage());
	    throw new RuntimeException("inviaRichiestaDizionario, " + e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("COMPLETATA INVIO RICHIESTA DIZIONARIO");
	}
    }

    private SPCoopMessage prepareMessage(String azione) {

	String mittente = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_MITTENTE);
	String servizio = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO);
	String tipo_servizio = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO);
	String tipoDestinatario = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPODESTINATARIO);
	String tipoMittente = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_TIPOMITTENTE);
	String servizioApplicativo = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO);
	String destinatario = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_DESTINATARIO);
	SPCoopMessage message = new SPCoopMessage();
	String idApplicativo = ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + mittente + "_" + System.currentTimeMillis();
	message.setIDApplicativo(idApplicativo);
	message.setServizioApplicativo(servizioApplicativo);
	SPCoopHeaderInfo header = new SPCoopHeaderInfo();
	// azione non va più specificata
	// header.setAzione(azione);
	// Destinatario può essere omesso
	header.setDestinatario(destinatario);
	header.setMittente(mittente);
	header.setID(idApplicativo);
	header.setServizio(servizio);
	header.setTipoDestinatario(tipoDestinatario);
	header.setTipoMittente(tipoMittente);
	header.setTipoServizio(tipo_servizio);
	message.setSpcoopHeaderInfo(header);
	return message;
    }

    @Override
    public void inviaRichiestaScheda(String tipoEndo, String tipoRichiesta, String codiceEndo) throws SPCoopException, RemoteException,
	    MalformedURLException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("Invio della richiesta della scheda di spiegazione per l'endo stp {}, tipoEndo: {}, tipoRichiesta: {}", new Object[] {
		    codiceEndo, tipoEndo, tipoRichiesta });
	}
	SPCoopMessage message = null;
	String suapId = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
	if (tipoEndo.equals(TipoEndo.TIPO_1.toString())) {
	    message = prepareMessage(CartProxyService.MessaggiAzioni.DownloadSchedaEC.toString());
	    RichiestaSchedaEndoTipo1 body = new RichiestaSchedaEndoTipo1();
	    body.setEndoprocedimento(new BigInteger(codiceEndo));
	    body.setDataValidita(Utilities.getToday());
	    body.setSUAP(suapId);
	    if (tipoRichiesta.equalsIgnoreCase(CartProxyService.TipoRichiesta.DISPONIBILITA.toString())) {
		body.setTipoRichiesta("Disponibilità");
	    }
	    if (tipoRichiesta.equalsIgnoreCase(CartProxyService.TipoRichiesta.INVIO.toString())) {
		body.setTipoRichiesta("Invio");
	    }
	    try {
		JAXBContext jc = JAXBContext.newInstance(RichiestaSchedaEndoTipo1.class);
		Marshaller m = jc.createMarshaller();
		// è necessario eliminare l'xml declaration
		m.setProperty("jaxb.fragment", true);
		StringWriter writer = new StringWriter();
		m.marshal(body, writer);
		String messaggioString = writer.toString();
		String xmlSOAPdata = PREFISSO_BUSTA_SOAP + messaggioString + POSTFISSO_BUSTA_SOAP;
		if (log.isDebugEnabled()) {
		    log.debug("Invio della richiesta della scheda di spiegazione per l'endo messaggio SOAP: {}", xmlSOAPdata);
		}
		message.setMessage(xmlSOAPdata.getBytes("UTF-8"));
	    } catch (PropertyException e) {
		e.printStackTrace();
	    } catch (JAXBException e) {
		e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	}
	if (tipoEndo.equals(TipoEndo.TIPO_2.toString())) {
	    message = prepareMessage(CartProxyService.MessaggiAzioni.DownloadSchedaEP.toString());
	    RichiestaSchedaEndoTipo2 body = new RichiestaSchedaEndoTipo2();
	    body.setEndoprocedimento(new BigInteger(codiceEndo));
	    body.setDataValidita(Utilities.getToday());
	    body.setSUAP(suapId);
	    if (tipoRichiesta.equalsIgnoreCase(CartProxyService.TipoRichiesta.DISPONIBILITA.toString())) {
		body.setTipoRichiesta("Disponibilità");
	    }
	    if (tipoRichiesta.equalsIgnoreCase(CartProxyService.TipoRichiesta.INVIO.toString())) {
		body.setTipoRichiesta("Invio");
	    }
	    try {
		JAXBContext jc = JAXBContext.newInstance(RichiestaSchedaEndoTipo2.class);
		Marshaller m = jc.createMarshaller();
		// è necessario eliminare l'xml declaration
		m.setProperty("jaxb.fragment", true);
		StringWriter writer = new StringWriter();
		m.marshal(body, writer);
		String messaggioString = writer.toString();
		String xmlSOAPdata = PREFISSO_BUSTA_SOAP + messaggioString + POSTFISSO_BUSTA_SOAP;
		message.setMessage(xmlSOAPdata.getBytes("UTF-8"));
		if (log.isDebugEnabled()) {
		    log.debug("Invio della richiesta della scheda di spiegazione per l'endo messaggio SOAP: {}", xmlSOAPdata);
		}
	    } catch (PropertyException e) {
		log.error("Errore in inviaRichiestaScheda di tipo PropertyException: {}", e.getMessage());
		e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
		log.error("Errore in inviaRichiestaScheda di tipo UnsupportedEncodingException: {}", e.getMessage());
		e.printStackTrace();
	    } catch (JAXBException e) {
		log.error("Errore in inviaRichiestaScheda di tipo JAXBException: {}", e.getMessage());
		e.printStackTrace();
	    }
	}
	if (message == null) {
	    log.error("Inviarichiesta Scheda messaggio non inizializzato {},{},{}", new Object[] { tipoEndo, tipoRichiesta, codiceEndo });
	    throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PDD_LOCATION");
	}
	String PDD_LOCATION = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION); // verticalizzazioniparametriPDD_LOCATION.getValore();
	if (log.isDebugEnabled()) {
	    log.debug("PRIMA DELL' INVIO RICHIESTA SCHEDA: {}", CartWsClientServiceImpl.getDescrizioneMessaggio(message));
	}
	cartWsClientService.invocaPortaDelegata(PDD_LOCATION, message);
	if (log.isDebugEnabled()) {
	    log.debug("INVIATA LA RICHIESTA SCHEDA");
	}
    }

    @Override
    public SPCoopMessage getMessaggio(String idEGov) {

	try {
	    return cartWsClientService.getMessaggio(idEGov);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    @Override
    public String getMessaggioBody(SPCoopMessage message) {

	return cartWsClientService.getMessaggioBody(message);
    }

    @Override
    public String getDescrizioneErrore(SPCoopException e) {

	return cartWsClientService.getDescrizioneEccezione(e);
    }

    @Override
    public void elaboraTuttiMessaggi() {

	String[] messaggi = null;
	try {
	    messaggi = cartWsClientService.getAllMessagesId();
	    for (String idEgov : messaggi) {
		if (log.isDebugEnabled()) {
		    log.debug("inizio l'elaborazione del messaggio egov '{}'", idEgov);
		}
		this.elaboraMessaggio(idEgov);
		if (log.isDebugEnabled()) {
		    log.debug("finita l'elaborazione del messaggio egov '{}'", idEgov);
		}
	    }
	} catch (SPCoopException e) {
	    log.error("Errore nell'aleborazione del messaggio di tipo SPCoopException:{}", e.getMessage());
	    e.printStackTrace();
	} catch (RemoteException e) {
	    log.error("Errore nell'aleborazione del messaggio di tipo RemoteException:{}", e.getMessage());
	    e.printStackTrace();
	} catch (MalformedURLException e) {
	    log.error("Errore nell'aleborazione del messaggio di tipo MalformedURLException:{}", e.getMessage());
	    e.printStackTrace();
	} catch (ServiceException e) {
	    log.error("Errore nell'aleborazione del messaggio di tipo ServiceException:{}", e.getMessage());
	    e.printStackTrace();
	}
    }

    @Override
    public void aggiornaDizionario(String idMessaggio) throws SPCoopException, MalformedURLException, RemoteException, ServiceException {

	if (log.isDebugEnabled()) {
	    log.debug("Inizio l'aggiornamento del dizionario dei procedimenti per il messaggio {}", idMessaggio);
	}
	this.elaboraMessaggio(idMessaggio);
	if (log.isDebugEnabled()) {
	    log.debug(
		    "Finito di elaborare l'aggiornamento del dizionario dei procedimenti per il messaggio {}, inizio la richiesta delle schede di spiegazione",
		    idMessaggio);
	}
	List<StpEndoTipo1> endoprocedimenti = stpEndoTipo1Service.findBySoftware(ORMHelper.getSoftware());
	for (StpEndoTipo1 stpEndoTipo1 : endoprocedimenti) {
	    inviaRichiestaScheda(CartProxyService.TipoEndo.TIPO_1.toString(), "Invio", String.valueOf(stpEndoTipo1.getCodiceStp()));
	}
	if (log.isDebugEnabled()) {
	    log.debug("Finita la richiesta delle schede di spiegazione per numero {} di endo tipo 1 ", endoprocedimenti.size());
	}
	List<StpEndoTipo2> procedimenti = stpEndoTipo2Service.findBySoftwareAndTipo(ORMHelper.getSoftware(), StpEndoTipo2Service.TIPO_ENDO);
	for (StpEndoTipo2 stpEndoTipo2 : procedimenti) {
	    inviaRichiestaScheda(CartProxyService.TipoEndo.TIPO_2.toString(), "Invio", String.valueOf(stpEndoTipo2.getCodiceStp()));
	}
	if (log.isDebugEnabled()) {
	    log.debug("Finita la richiesta delle schede di spiegazione per numero {} di endo tipo 2 ", procedimenti.size());
	    log.debug("Terminato l'aggiornamento del dizionario dei procedimenti per il messaggio {}", idMessaggio);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inviaLocalizzazioneEndo2(ParteLocaleSchedaEndoTipo2 entity, Integer idEndo) {

	SPCoopMessage message = prepareMessage(CartProxyService.MessaggiAzioni.InvioLocalizzazioneEP.toString());
	InvioLocalizzazioneSchedaEndoTipo2 body = new InvioLocalizzazioneSchedaEndoTipo2();
	body.setParteLocaleSchedaEndoTipo2(entity);
	try {
	    JAXBContext jc = JAXBContext.newInstance(InvioLocalizzazioneSchedaEndoTipo2.class);
	    Marshaller m = jc.createMarshaller();
	    // è necessario eliminare l'xml declaration
	    m.setProperty("jaxb.fragment", true);
	    StringWriter writer = new StringWriter();
	    m.marshal(body, writer);
	    String messaggioString = writer.toString();
	    String xmlSOAPdata = PREFISSO_BUSTA_SOAP + messaggioString + POSTFISSO_BUSTA_SOAP;
	    message.setMessage(xmlSOAPdata.getBytes("UTF-8"));
	    if (log.isDebugEnabled()) {
		log.debug("Invio della richiesta dizionario: {}", xmlSOAPdata);
	    }
	    String PDD_LOCATION = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION); // verticalizzazioniparametriPDD_LOCATION.getValore();
	    if (log.isDebugEnabled()) {
		log.debug("PRIMA DELL' INVIO RICHIESTA SCHEDA: {}", CartWsClientServiceImpl.getDescrizioneMessaggio(message));
	    }
	    SPCoopMessage answer = cartWsClientService.invocaPortaDelegata(PDD_LOCATION, message);
	    if (log.isDebugEnabled()) {
		log.debug("inviaLocalizzazioneEndo2: risposta al messaggio inviato {}",
			ToStringBuilder.reflectionToString(answer, ToStringStyle.MULTI_LINE_STYLE));
	    }
	    StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findbyStpCodice(idEndo, ENDO);
	    Oggetti schedaEndo = stpEndoTipo2.getOggetti();
	    JAXBElement<InvioSchedaEndoTipo2> po = null;
	    Unmarshaller u = jc.createUnmarshaller();
	    po = (JAXBElement<InvioSchedaEndoTipo2>) u.unmarshal(new ByteArrayInputStream(schedaEndo.getOggetto()));
	    InvioSchedaEndoTipo2 invioSchedaEndoTipo2 = po.getValue();
	    invioSchedaEndoTipo2.setParteLocaleSchedaEndoTipo2(entity);
	    writer = new StringWriter();
	    m.marshal(invioSchedaEndoTipo2, writer);
	    String invioSchedaEndoTipo2Str = writer.toString();
	    if (!invioSchedaEndoTipo2Str.startsWith("<?xml version=")) {
		invioSchedaEndoTipo2Str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + invioSchedaEndoTipo2Str;
	    }
	    schedaEndo.setOggetto(invioSchedaEndoTipo2Str.getBytes("UTF-8"));
	    oggettiService.update(schedaEndo);
	    if (log.isDebugEnabled()) {
		log.debug("inviaLocalizzazioneEndo2: scheda endo {} aggiornata", idEndo);
	    }
	} catch (PropertyException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo2 di tipo PropertyException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo2 di tipo PropertyException " + e1.getMessage(), e1.getCause());
	} catch (UnsupportedEncodingException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo2 di tipo UnsupportedEncodingException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo2 di tipo UnsupportedEncodingException " + e1.getMessage(), e1.getCause());
	} catch (JAXBException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo2 di tipo JAXBException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo2 di tipo JAXBException " + e1.getMessage(), e1.getCause());
	} catch (Exception e) {
	    log.error("Errore in inviaLocalizzazioneEndo2: {}", e.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo2: " + e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("INVIATA LA RICHIESTA SCHEDA");
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public void inviaLocalizzazioneEndo1(ParteLocaleSchedaEndoTipo1 entity, Integer idEndo) {

	SPCoopMessage message = prepareMessage(CartProxyService.MessaggiAzioni.InvioLocalizzazioneEC.toString());
	InvioLocalizzazioneSchedaEndoTipo1 body = new InvioLocalizzazioneSchedaEndoTipo1();
	body.setParteLocaleSchedaEndoTipo1(entity);
	try {
	    JAXBContext jc = JAXBContext.newInstance(InvioLocalizzazioneSchedaEndoTipo1.class);
	    Marshaller m = jc.createMarshaller();
	    // è necessario eliminare l'xml declaration
	    m.setProperty("jaxb.fragment", true);
	    StringWriter writer = new StringWriter();
	    m.marshal(body, writer);
	    String messaggioString = writer.toString();
	    String xmlSOAPdata = PREFISSO_BUSTA_SOAP + messaggioString + POSTFISSO_BUSTA_SOAP;
	    message.setMessage(xmlSOAPdata.getBytes("UTF-8"));
	    if (log.isDebugEnabled()) {
		log.debug("Invio della richiesta dizionario: {}", xmlSOAPdata);
	    }
	    String PDD_LOCATION = getParametriConfigurazione().get(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION); // verticalizzazioniparametriPDD_LOCATION.getValore();
	    if (log.isDebugEnabled()) {
		log.debug("PRIMA DELL' INVIO RICHIESTA SCHEDA: {}", CartWsClientServiceImpl.getDescrizioneMessaggio(message));
	    }
	    SPCoopMessage answer = cartWsClientService.invocaPortaDelegata(PDD_LOCATION, message);
	    if (log.isDebugEnabled()) {
		log.debug("inviaLocalizzazioneEndo1: risposta al messaggio inviato {}",
			ToStringBuilder.reflectionToString(answer, ToStringStyle.MULTI_LINE_STYLE));
	    }
	    StpEndoTipo1 stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(idEndo);
	    Oggetti schedaEndo = stpEndoTipo1.getOggetti();
	    JAXBElement<InvioSchedaEndoTipo1> po = null;
	    Unmarshaller u = jc.createUnmarshaller();
	    po = (JAXBElement<InvioSchedaEndoTipo1>) u.unmarshal(new ByteArrayInputStream(schedaEndo.getOggetto()));
	    InvioSchedaEndoTipo1 invioSchedaEndoTipo1 = po.getValue();
	    invioSchedaEndoTipo1.setParteLocaleSchedaEndoTipo1(entity);
	    writer = new StringWriter();
	    m.marshal(invioSchedaEndoTipo1, writer);
	    String invioSchedaEndoTipo1Str = writer.toString();
	    if (!invioSchedaEndoTipo1Str.startsWith("<?xml version=")) {
		invioSchedaEndoTipo1Str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + invioSchedaEndoTipo1Str;
	    }
	    schedaEndo.setOggetto(invioSchedaEndoTipo1Str.getBytes("UTF-8"));
	    oggettiService.update(schedaEndo);
	    if (log.isDebugEnabled()) {
		log.debug("inviaLocalizzazioneEndo1: scheda endo {} aggiornata", idEndo);
	    }
	} catch (PropertyException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo1 di tipo PropertyException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo1 di tipo PropertyException " + e1.getMessage(), e1.getCause());
	} catch (UnsupportedEncodingException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo1 di tipo UnsupportedEncodingException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo1 di tipo UnsupportedEncodingException " + e1.getMessage(), e1.getCause());
	} catch (JAXBException e1) {
	    log.error("Errore in inviaLocalizzazioneEndo1 di tipo JAXBException: {}", e1.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo1 di tipo JAXBException " + e1.getMessage(), e1.getCause());
	} catch (Exception e) {
	    log.error("Errore in inviaLocalizzazioneEndo1: {}", e.getMessage());
	    throw new RuntimeException("Errore in inviaLocalizzazioneEndo1: " + e.getMessage(), e.getCause());
	}
	if (log.isDebugEnabled()) {
	    log.debug("INVIATA LA RICHIESTA SCHEDA");
	}
    }

    @Override
    public void aggiornaConfigurazioni() {

	parametriVerticalizzazioni.remove(ORMHelper.getIdcomune());
	cartWsClientService.reloadPortConfiguration();
    }

    private Map<String, String> getParametriConfigurazione() {

	if (parametriVerticalizzazioni.get(ORMHelper.getIdcomune()) == null) {
	    Map<String, String> parametri = new HashMap<String, String>();
	    // azioni
	    Verticalizzazioniparametri verticalizzazioniparametriAZIONI = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_AZIONI);
	    if (verticalizzazioniparametriAZIONI == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_ALBEROPROC_ENDO.FKAZID");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_AZIONI, verticalizzazioniparametriAZIONI.getValore());
	    // CODICE NATURA
	    Verticalizzazioniparametri verticalizzazioniparametriCODICENATURA = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA);
	    if (verticalizzazioniparametriCODICENATURA == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO_INVENTARIOPROC.CODNATURA");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_CODICENATURA, verticalizzazioniparametriCODICENATURA.getValore());
	    // destinatario
	    Verticalizzazioniparametri verticalizzazioniparametriDESTINATARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_DESTINATARIO);
	    if (verticalizzazioniparametriDESTINATARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO DESTINATARIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO DESTINATARIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_DESTINATARIO, verticalizzazioniparametriDESTINATARIO.getValore());
	    // famiglia endo dei procedimenti di tipo 2
	    Verticalizzazioniparametri verticalizzazioniparametriFAMIGLIAENDO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO);
	    if (verticalizzazioniparametriFAMIGLIAENDO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_TIPIFAMIGLIEENDO.CODICE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_FAMIGLIAENDO, verticalizzazioniparametriFAMIGLIAENDO.getValore());
	    // integration manager url
	    Verticalizzazioniparametri verticalizzazioniparametriINTEGRATION_MANAGER_URL = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL);
	    if (verticalizzazioniparametriINTEGRATION_MANAGER_URL == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO INTEGRATION_MANAGER_URL");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO INTEGRATION_MANAGER_URL");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_INTEGRATION_MANAGER_URL, verticalizzazioniparametriINTEGRATION_MANAGER_URL.getValore());
	    // RECUPERO PARAMETRI VERTICALIZZAZIONE CART VERIFICO SE E' ABILITATA LA GESTIONE DEGLI
	    // INVENTARIPROCEDIMENTI.
	    Verticalizzazioniparametri verticalizzazioniparametriINVENTARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO);
	    if (verticalizzazioniparametriINVENTARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ENDO2_INVENTARIOPROCEDIMENTI");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_INVENTARIO, verticalizzazioniparametriINVENTARIO.getValore());
	    // mittente
	    Verticalizzazioniparametri verticalizzazioniparametriMITTENTE = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_MITTENTE);
	    if (verticalizzazioniparametriMITTENTE == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO MITTENTE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO MITTENTE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_MITTENTE, verticalizzazioniparametriMITTENTE.getValore());
	    // password
	    Verticalizzazioniparametri verticalizzazioniparametriPASSWORD = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_PASSWORD);
	    if (verticalizzazioniparametriPASSWORD == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PASSWORD");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PASSWORD");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_PASSWORD, verticalizzazioniparametriPASSWORD.getValore());
	    // pdd location
	    Verticalizzazioniparametri verticalizzazioniparametriPDD_LOCATION = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION);
	    if (verticalizzazioniparametriPDD_LOCATION == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PDD_LOCATION");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO PDD_LOCATION");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_PDD_LOCATION, verticalizzazioniparametriPDD_LOCATION.getValore());
	    // root alberoproc
	    Verticalizzazioniparametri verticalizzazioniparametriROOTALBEROPROC = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC);
	    if (verticalizzazioniparametriROOTALBEROPROC == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO ROOT_ALBEROPROC_ID");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC, verticalizzazioniparametriROOTALBEROPROC.getValore());
	    // servizio applicativo
	    Verticalizzazioniparametri verticalizzazioniparametriSERVIZIOAPPLICATIVO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO);
	    if (verticalizzazioniparametriSERVIZIOAPPLICATIVO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIOAPPLICATIVO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIOAPPLICATIVO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIOAPPLICATIVO, verticalizzazioniparametriSERVIZIOAPPLICATIVO.getValore());
	    // servizio
	    Verticalizzazioniparametri verticalizzazioniparametriSERVIZIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO);
	    if (verticalizzazioniparametriSERVIZIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SERVIZIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_SERVIZIO, verticalizzazioniparametriSERVIZIO.getValore());
	    // SUAP_ID
	    Verticalizzazioniparametri verticalizzazioniparametriSUAP_ID = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID);
	    if (verticalizzazioniparametriSUAP_ID == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO SUAP_ID");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO VERTICALIZZAZIONE_CART_SUAP_ID");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_SUAP_ID, verticalizzazioniparametriSUAP_ID.getValore());
	    // TIPI MOVIMENTO
	    Verticalizzazioniparametri verticalizzazioniparametriTIPIMOVIMENTO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO);
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPIMOVIMENTO, verticalizzazioniparametriTIPIMOVIMENTO.getValore());
	    // tipo destinatario
	    Verticalizzazioniparametri verticalizzazioniparametriTIPODESTINATARIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPODESTINATARIO);
	    if (verticalizzazioniparametriTIPODESTINATARIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPODESTINATARIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPODESTINATARIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPODESTINATARIO, verticalizzazioniparametriTIPODESTINATARIO.getValore());
	    // tipo mittente
	    Verticalizzazioniparametri verticalizzazioniparametriTIPOMITTENTE = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPOMITTENTE);
	    if (verticalizzazioniparametriTIPOMITTENTE == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPOMITTENTE");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPOMITTENTE");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPOMITTENTE, verticalizzazioniparametriTIPOMITTENTE.getValore());
	    // tipo servizio
	    Verticalizzazioniparametri verticalizzazioniparametriTIPO_SERVIZIO = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO);
	    if (verticalizzazioniparametriTIPO_SERVIZIO == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPO_SERVIZIO");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO TIPO_SERVIZIO");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_TIPO_SERVIZIO, verticalizzazioniparametriTIPO_SERVIZIO.getValore());
	    // username
	    Verticalizzazioniparametri verticalizzazioniparametriUSERNAME = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_USERNAME);
	    if (verticalizzazioniparametriUSERNAME == null) {
		log.error("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO USERNAME");
		throw new RuntimeException("VERTICALIZZAZIONE CART NON ATTIVA : PARAMETRO USERNAME");
	    }
	    parametri.put(WebConstants.VERTICALIZZAZIONE_CART_USERNAME, verticalizzazioniparametriUSERNAME.getValore());
	    parametriVerticalizzazioni.put(ORMHelper.getIdcomune(), parametri);
	    return parametri;
	} else {
	    return parametriVerticalizzazioni.get(ORMHelper.getIdcomune());
	}
    }

    @Override
    public void elaboraTuttiMessaggiPerServizio(MessaggiAzioni azione) {

	String[] idMessaggi = getMessaggiPerServizio(azione);
	for (String messaggio : idMessaggi) {
	    this.elaboraMessaggio(messaggio);
	}
    }

    @Override
    public MessaggiAzioni getAzione(String azione) {

	if (azione.equalsIgnoreCase(MessaggiAzioni.AttivazioneProcedimento.toString())) {
	    return MessaggiAzioni.AttivazioneProcedimento;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.ConclusioneProcedimento.toString())) {
	    return MessaggiAzioni.ConclusioneProcedimento;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DisponibilitaDizionario.toString())) {
	    return MessaggiAzioni.DisponibilitaDizionario;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DisponibilitaSchedaEC.toString())) {
	    return MessaggiAzioni.DisponibilitaSchedaEC;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DisponibilitaSchedaEP.toString())) {
	    return MessaggiAzioni.DisponibilitaSchedaEP;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DownloadDizionario.toString())) {
	    return MessaggiAzioni.DownloadDizionario;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DownloadSchedaEC.toString())) {
	    return MessaggiAzioni.DownloadSchedaEC;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.DownloadSchedaEP.toString())) {
	    return MessaggiAzioni.DownloadSchedaEP;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.InvioDizionario.toString())) {
	    return MessaggiAzioni.InvioDizionario;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.InvioLocalizzazioneEC.toString())) {
	    return MessaggiAzioni.InvioLocalizzazioneEC;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.InvioLocalizzazioneEP.toString())) {
	    return MessaggiAzioni.InvioLocalizzazioneEP;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.InvioSchedaEC.toString())) {
	    return MessaggiAzioni.InvioSchedaEC;
	}
	if (azione.equalsIgnoreCase(MessaggiAzioni.InvioSchedaEP.toString())) {
	    return MessaggiAzioni.InvioSchedaEP;
	}
	throw new RuntimeException("Azione non codificata");
    }
}
