package it.gruppoinit.pal.gp.core.service.impl;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.openspcoop.pdd.services.SPCoopException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndo;
import it.gruppoinit.pal.gp.core.domain.AlberoprocEndoId;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Azioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo2;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo2;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametribase;
import it.gruppoinit.pal.gp.core.domain.VerticalizzazioniparametribaseId;
import it.gruppoinit.pal.gp.core.domain.helper.CartAlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.CategorieTree;
import it.gruppoinit.pal.gp.core.domain.helper.EndoTipo1Helper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocEndoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AzioniService;
import it.gruppoinit.pal.gp.core.service.CartInvioDizionarioService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StpCategorieEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo2Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo2Service;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiendoService;
import it.gruppoinit.pal.gp.core.service.TipifamiglieendoService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametriService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniparametribaseService;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.rules.AlberoprocBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.Attivita;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.CategoriaAttivita;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.CategoriaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.EndoTipo2;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.InvioDizionario;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.TipologiaEndoTipo1;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.TipologiaEndoTipo2;
import it.gruppoinit.sigepro.cart.service.ero.CartRfc184InvioDizionarioService;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationHelper;
import it.gruppoinit.sigepro.cart.service.helper.CartServiceConfigurationParameters;

@Service
public class CartInvioDizionarioServiceImpl extends CartBaseServiceEROImpl implements CartInvioDizionarioService {

    private static final String CODICE_DESCRIZIONE_SEPARATOR = " - ";
    private static final Logger log = LoggerFactory.getLogger(CartInvioDizionarioServiceImpl.class);
    private static final double STP_CODICE_TRUST_LIMIT = 0.1;
    private AlberoprocService alberoprocService;
    private AlberoprocEndoService alberoprocEndoService;
    private AmministrazioniService amministrazioniService;
    private AzioniService azioniService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private NaturaendoService naturaendoService;
    private SoftwareService softwareService;
    private StpCategorieEndo1Service stpCategorieEndo1Service;
    private StpEndoTipo1Service stpEndoTipo1Service;
    private StpEndoTipo2Service stpEndoTipo2Service;
    private StpTipologieEndo1Service stpTipologieEndo1Service;
    private StpTipologieEndo2Service stpTipologieEndo2Service;
    private TempificazioniService tempificazioniService;
    private TipiendoService tipiendoService;
    private TipifamiglieendoService tipifamiglieendoService;
    private TipiMovimentoService tipiMovimentoService;
    private CartRfc184InvioDizionarioService cartInvioDizionarioService;
    private VerticalizzazioniService verticalizzazioniService;
    private VerticalizzazioniparametriService verticalizzazioniParametriService;
    private VerticalizzazioniparametribaseService verticalizzazioniparametribaseService;
    private AlberoprocDAO alberoprocDAO;

    @Autowired
    public void setAlberoprocDAO(AlberoprocDAO alberoprocDAO) {

	this.alberoprocDAO = alberoprocDAO;
    }

    /**
     * 0 - Non pubblicare <br/>
     * 1 - Area Riservata e Front Office<br/>
     * 2 - Solo Area Riservata <br/>
     * 3 - Solo Front Office
     */
    public final Integer statoPubblicazioneAlberoproc = 3;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocEndoService(AlberoprocEndoService alberoprocEndoService) {

	this.alberoprocEndoService = alberoprocEndoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setAzioniService(AzioniService azioniService) {

	this.azioniService = azioniService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setStpEndoTipo1Service(StpEndoTipo1Service stpEndoTipo1Service) {

	this.stpEndoTipo1Service = stpEndoTipo1Service;
    }

    @Autowired
    public void setStpEndoTipo2Service(StpEndoTipo2Service stpEndoTipo2Service) {

	this.stpEndoTipo2Service = stpEndoTipo2Service;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setStpCategorieEndo1Service(StpCategorieEndo1Service stpCategorieEndo1Service) {

	this.stpCategorieEndo1Service = stpCategorieEndo1Service;
    }

    @Autowired
    public void setStpTipologieEndo1Service(StpTipologieEndo1Service stpTipologieEndo1Service) {

	this.stpTipologieEndo1Service = stpTipologieEndo1Service;
    }

    @Autowired
    public void setStpTipologieEndo2Service(StpTipologieEndo2Service stpTipologieEndo2Service) {

	this.stpTipologieEndo2Service = stpTipologieEndo2Service;
    }

    @Autowired
    public void setTempificazioniService(TempificazioniService tempificazioniService) {

	this.tempificazioniService = tempificazioniService;
    }

    @Autowired
    public void setTipiendoService(TipiendoService tipiendoService) {

	this.tipiendoService = tipiendoService;
    }

    @Autowired
    public void setTipifamiglieendoService(TipifamiglieendoService tipifamiglieendoService) {

	this.tipifamiglieendoService = tipifamiglieendoService;
    }

    @Autowired
    public void setCartInvioDizionarioService(CartRfc184InvioDizionarioService cartService) {

	this.cartService = cartService;
	this.cartInvioDizionarioService = cartService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioniParametriService(VerticalizzazioniparametriService verticalizzazioniParametriService) {

	this.verticalizzazioniParametriService = verticalizzazioniParametriService;
    }

    @Autowired
    public void setVerticalizzazioniparametribaseService(VerticalizzazioniparametribaseService verticalizzazioniparametribaseService) {

	this.verticalizzazioniparametribaseService = verticalizzazioniparametribaseService;
    }

    /**
     * La funzione elaboramessaggio esegue le seguenti operazioni:
     * <ul>
     * <li>Trasforma l'xml del messaggio nell'oggetto {@link InvioDizionario};</li>
     * <li>Inserisce gli endo di tipo 1 nelle tabelle TIPIFAMIGLIEENDO, TIPIENDO, INVENTARIOPROCEDIMENTI;</li>
     * <li>Popola un oggetto helper che contiene la gerarchia dell'alberatura degli endo di tipo 2 che saranno inseriti
     * in ALBEROPRO;</li>
     * <li>Inserisce / aggiorna le voci di alberoproc;</li>
     * <li>Qualora il parametro {@link WebConstants#VERTICALIZZAZIONE_CART_INVENTARIO} sia settato a
     * {@link WebConstants#VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO} trasforma gli elementi del dizionario ENDO TIPO 2
     * in endoprocedimenti e li associa come endo principali della voce dell'albero;</li>
     * <li>Se tutto è andato a buon fine cancella il messaggio arrivato dalla regione.</li>
     * </ul>
     */
    @Override
    public void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	InvioDizionario dizionario = cartInvioDizionarioService.elaboraMessaggio(idEgov);
	if (dizionario != null) {
	    // recupero la lista di oggetti
	    List<Object> list = dizionario.getTipologiaEndoTipo1OrCategoriaEndoTipo1OrEndoTipo1();
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    if (log.isDebugEnabled()) {
		log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di inserire i procedimenti di tipo 1", idEgov,
			getCartService().getServiceIdEnum());
	    }
	    //inserimentoInventarioprocedimenti(list, software);
	    //  prepareInseriementoInventarioProcedimenti(list, software);
	    if (log.isDebugEnabled()) {
		log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di Creare alberoprocHelper", idEgov,
			getCartService().getServiceIdEnum());
	    }
	    CartAlberoprocHelper helper = createCartAlberoprocHelper(list);
	    if (log.isDebugEnabled()) {
		log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di inserire i procedimenti di tipo 2", idEgov,
			getCartService().getServiceIdEnum());
	    }
	    inserimentoAlberoproc(helper, software, true, true);
	    if (log.isDebugEnabled()) {
		log.debug(
			"Elaborazione del messaggio [{}] di tipo {}, Prima di associare gli endo di tipo 1 a quelli di tipo 2 procedimenti di tipo 2",
			idEgov, getCartService().getServiceIdEnum());
	    }
	    inserimentoEndo2ComeEndo1(software);
	    this.flushAndClearSession();
	    alberoprocDAO.commit();
	    this.flushAndClearSession();
	    // this.deleteMessage(idEgov);
	}
	// §§§END§§§
    }

    @Override
    public void elaboraMessaggio(CartInfoDizionarioHelper cartInfoDizionarioHelper) throws RemoteException, SPCoopException {

	Software software = softwareService.findById(ORMHelper.getSoftware());
	if (log.isDebugEnabled()) {
	    //	    log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di inserire i procedimenti di tipo 1", idEgov, getCartService()
	    //		    .getServiceIdEnum());
	}
	inserimentoInventarioprocedimenti(cartInfoDizionarioHelper, software);
	if (log.isDebugEnabled()) {
	    log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di Creare alberoprocHelper", "", getCartService().getServiceIdEnum());
	}
	CartAlberoprocHelper helper = createCartAlberoprocHelper(cartInfoDizionarioHelper.getListDaDizionario());
	if (log.isDebugEnabled()) {
	    log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di inserire i procedimenti di tipo 2", "",
		    getCartService().getServiceIdEnum());
	}
	inserimentoAlberoproc(helper, software, cartInfoDizionarioHelper.getIsSovrascriviConfigurazioneAlberoproc(),
		cartInfoDizionarioHelper.isTrustStpCodice());
	if (log.isDebugEnabled()) {
	    log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di associare gli endo di tipo 1 a quelli di tipo 2 procedimenti di tipo 2",
		    "", getCartService().getServiceIdEnum());
	}
	inserimentoEndo2ComeEndo1(software);
	//LION 14-06-19 al termine dell'importazione i codici STP sono stati comunque allineati e sicuramente saranno considerati affidabili per l'importazione successiva
	aggiornaVerticalizzazioneTrustStpCodice(cartInfoDizionarioHelper);
	//END LION
	alberoprocDAO.commit();
	alberoprocDAO.flush();
	// this.deleteMessage(cartInfoDizionarioHelper.getIdEgov());
    }

    @Override
    public CartInfoDizionarioHelper preElaboraMessaggio(String idEgov) throws RemoteException, SPCoopException {

	// carico il dizionario dal messaggio
	//////////////////////////////// CODICE DI TEST PER UTILIZZARE UN DIZIONARIO IN LOCALE ////////////////////////////////
	/*
	InputStream in = null;
	try {
	    in = new FileInputStream("c:/temp/dizionario-20201020-small.xml");
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	}
	//read it with BufferedReader
	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	StringBuilder sb = new StringBuilder();
	String line = null;
	try {
	    while ((line = reader.readLine()) != null) {
		sb.append(line + "\n");
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	try {
	    in.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	InvioDizionario dizionario = (InvioDizionario) Utilities.unMarshallString(sb.toString(), InvioDizionario.class);
	*/
	////////////////////////////////////////// METODO CHE DOVRà ESSERE USATO ///////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	InvioDizionario dizionario = cartInvioDizionarioService.elaboraMessaggio(idEgov);
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	 * Lion 10-06-19: verifico se la sincronizzazione del dizionario può fare affidamento sui valori di CODICE_STP per identifcare i record da sincronizzare
	 * se la verifica è già stata effettuata allora l'esito della verifica è scritto in una specifica verticalizzazione CART.SYNC_DIZIONARIO_USA_STP_CODICE dove
	 * S = usa codice codice stp e N = usa codice regionale. se l verticalizzazione non esiste o il suovalore non è S o N allora nella preelaborazione valuto 
	 * se esitono le condizioni per basare la sincronizzazione sui codici stp e valorizzo di conseguenza la verticalizzazione.
	 */
	boolean trustStpCodice = trustStpCodice();
	if (log.isDebugEnabled()) {
	    String dizionarioStr = Utilities.marshallObject(dizionario);
	    log.debug("{}", dizionarioStr);
	}
	CartInfoDizionarioHelper risultato = new CartInfoDizionarioHelper();
	risultato.setIdEgov(idEgov);
	risultato.setTrustStpCodice(trustStpCodice);
	List<StpTipologieEndo2> stpTipologieEndo2s = new ArrayList<StpTipologieEndo2>();
	if (dizionario != null) {
	    // recupero la lista di oggetti
	    List<Object> list = dizionario.getTipologiaEndoTipo1OrCategoriaEndoTipo1OrEndoTipo1();
	    risultato.setListDaDizionario(list);
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    if (log.isDebugEnabled()) {
		log.debug(
			"Elaborazione del messaggio [{}] di tipo {}, Inizializzo l'oggetto CartInfoDizionarioHelper che conterrà le informazioni per la configurazione preliminari e l'inserimento dei dati elaborati dal dizionario ",
			idEgov, getCartService().getServiceIdEnum());
	    }
	    //  inserimentoInventarioprocedimenti(list, software);
	    prepareInserimentoInventarioprocedimento(risultato, software, trustStpCodice);
	    //risultato.setEndoTipo1Helpers(endoTipo1Helpers);
	    if (log.isDebugEnabled()) {
		log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di Creare alberoprocHelper", idEgov,
			getCartService().getServiceIdEnum());
	    }
	    //  CartAlberoprocHelper helper = createCartAlberoprocHelper(list);
	    prepareInserimentoStpTipologiaendo2(risultato, software, trustStpCodice);
	    //risultato.setStpTipologieEndo2s(stpTipologieEndo2s);
	    if (log.isDebugEnabled()) {
		log.debug("Elaborazione del messaggio [{}] di tipo {}, Prima di inserire i procedimenti di tipo 2", idEgov,
			getCartService().getServiceIdEnum());
	    }
	    //la verifica della validità degli STP CODICE viene scritta in verticalizzazione
	    this.gestisciVerticalizzazioneTrustStpCodice(risultato);
	    // inserimentoAlberoproc(helper, software);
	    if (log.isDebugEnabled()) {
		log.debug(
			"Elaborazione del messaggio [{}] di tipo {}, Prima di associare gli endo di tipo 1 a quelli di tipo 2 procedimenti di tipo 2",
			idEgov, getCartService().getServiceIdEnum());
	    }
	    //  inserimentoEndo2ComeEndo1(software);
	    //  this.deleteMessage(idEgov);
	}
	// §§§END§§§
	return risultato;
    }

    private boolean trustStpCodice() {

	/*
	 * Lion 10-06-19: verifico se la sincronizzazione del dizionario può fare affidamento sui valori di CODICE_STP per identifcare i record da sincronizzare
	 * se la verifica è già stata effettuata allora l'esito della verifica è scritto in una specifica verticalizzazione CART.SYNC_DIZIONARIO_USA_STP_CODICE dove
	 * S = usa codice codice stp e N = usa codice regionale. se l verticalizzazione non esiste o il suovalore non è S o N allora nella preelaborazione valuto 
	 * se esitono le condizioni per basare la sincronizzazione sui codici stp e valorizzo di conseguenza la verticalizzazione.
	 */
	//	String vert_stp_codice = this.verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CART,
	//		WebConstants.VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE);
	boolean trustStpCodice = false;
	//	if (StringUtils.isNotBlank(vert_stp_codice)) {
	//	    if (WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO.equalsIgnoreCase(vert_stp_codice)) {
	//		trustStpCodice = Boolean.TRUE;
	//	    } else if (WebConstants.VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO.equalsIgnoreCase(vert_stp_codice)) {
	//		trustStpCodice = Boolean.FALSE;
	//	    }
	//	}
	return false;
    }

    private void prepareInserimentoStpTipologiaendo2(CartInfoDizionarioHelper dizioHelper, Software software, boolean checkStpCodice) {

	List<Object> list = dizioHelper.getListDaDizionario();
	List<StpTipologieEndo2> stpTipologieEndo2s = new ArrayList<StpTipologieEndo2>();
	StpTipologieEndo2 stpTipologieEndo2 = null;
	//LION 10-06-19 se checkStpCodice == true verifico se i codici stp di STP_ENDO_TIPO2 sono affidabili riferimenti per la sincronizzazione del dizionario
	int totAttivita = 0;
	int numAttivitaCodiceStpValido = 0;
	int prevStpCodice = -100;
	List<Integer> stpNonValidi = new ArrayList<Integer>();
	List<String> codRegNonValidi = new ArrayList<String>();
	for (int i = list.size() - 1; i > -1; i--) {
	    Object object = list.get(i);
	    if (checkStpCodice && object instanceof Attivita) {
		Attivita att = (Attivita) object;
		if (att.getId().intValue() == prevStpCodice) {
		    continue;
		}
		prevStpCodice = att.getId().intValue();
		totAttivita++;
		List<StpEndoTipo2> matchList = this.stpEndoTipo2Service.findAllByStpCodice(prevStpCodice, StpEndoTipo2Service.TIPO_ATTIVITA);
		boolean realMatch = false;
		for (int j = 0; j < matchList.size() && !realMatch; j++) {
		    StpEndoTipo2 stp2 = matchList.get(j);
		    if (stp2 != null && stp2.getCodiceEndoRegionale().equals(att.getCodice())) {
			numAttivitaCodiceStpValido++;
			realMatch = true;
		    }
		}
		if (!realMatch) {
		    stpNonValidi.add(prevStpCodice);
		    codRegNonValidi.add(att.getCodice());
		}
	    }
	    //	    if (object instanceof CategoriaAttivita) {
	    //		CategorieTree categorieTree = new CategorieTree();
	    //		categorieTree.setCategoriaAttivita((CategoriaAttivita) object);
	    //		categorieTrees.add(idCategorie, categorieTree);
	    //		idCategorie++;
	    //	    }
	    else if (object instanceof TipologiaEndoTipo2) {
		TipologiaEndoTipo2 endoTipo2 = (TipologiaEndoTipo2) object;
		// Controllo se già è stato salvato questa tipologia di endo 2
		stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(endoTipo2.getId().intValue()));
		if (EntityUtils.getNestedProperty(stpTipologieEndo2, "id.codice") != null) {
		    stpTipologieEndo2s.add(stpTipologieEndo2);
		} else {
		    stpTipologieEndo2 = new StpTipologieEndo2();
		    stpTipologieEndo2.setId(new PkId(new Integer(endoTipo2.getId().toString())));
		    stpTipologieEndo2.setDescrizione(endoTipo2.getNome());
		    stpTipologieEndo2s.add(stpTipologieEndo2);
		    stpTipologieEndo2Service.insert(stpTipologieEndo2);
		}
	    }
	    //LION 10-06-19
	    if (checkStpCodice) {
		dizioHelper.setTotAttivita(totAttivita);
		dizioHelper.setNumAttivitaStpCodiceValido(numAttivitaCodiceStpValido);
		dizioHelper.setStpAttivitaNonValidi(stpNonValidi.toString());
		dizioHelper.setCodiciRegStpAttivitaNonValidi(codRegNonValidi.toString());
	    }
	    //END LION
	}
	dizioHelper.setStpTipologieEndo2s(stpTipologieEndo2s);
    }

    /**
     * Qualora il parametro {@link WebConstants#VERTICALIZZAZIONE_CART_INVENTARIO} sia settato a
     * {@link WebConstants#VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO} trasforma gli elementi del dizionario ENDO TIPO 2
     * in endoprocedimenti e li associa come endo principali della voce dell'albero.
     * 
     * @param helper
     * @param list
     * @param software
     */
    private void inserimentoEndo2ComeEndo1(Software software) {

	// §§§BEGIN§§§
	CartServiceConfigurationParameters configParams = CartServiceConfigurationHelper.getConfigurationParameters();
	String valoreInventario = configParams.getInventarioProcedimenti();// verticalizzazioniparametriINVENTARIO.getValore();
	boolean isAbilitatoInserimentoInventario = false;
	if (StringUtils.defaultIfEmpty(valoreInventario, WebConstants.VERTICALIZZAZIONE_CART_VALORE_N_INVENTARIO)
		.equalsIgnoreCase(WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO)) {
	    isAbilitatoInserimentoInventario = true;
	}
	if (isAbilitatoInserimentoInventario) {
	    Naturaendo naturaendo = null;
	    Tipimovimento tipimovimento = null;
	    Amministrazioni amministrazioni = null;
	    Azioni azioni = null;
	    Tipifamiglieendo tipifamiglieendo = null;
	    log.debug(
		    "inserimentoEndo2ComeEndo1: recupero gli oggetti dalle verticalizzazioni per impostarli come default ai nuovi endo da creare/modificare");
	    /*
	     * NATURAENDO.CODICENATURA
	     */
	    String codicenatura = configParams.getCodiceNatura(); // verticalizzazioniparametriCODICENATURA.getValore();
	    if (StringUtils.isNotBlank(codicenatura)) {
		naturaendo = naturaendoService.findById(new NaturaendoId(new Integer(codicenatura.trim())));
		//naturaendo = naturaendoService.findById(new PkId(new Integer(codicenatura.trim())));
	    }
	    /*
	     * TIPIMOVIMENTO.TIPOMOVIMENTO
	     */
	    String tipomovimento = configParams.getTipiMovimento();
	    // if (verticalizzazioniparametriTIPIMOVIMENTO != null) {
	    if (StringUtils.isNotBlank(tipomovimento)) {
		tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), tipomovimento));
	    }
	    /*
	     * AMMINISTRAZIONE. PARAMETRO VERTICALIZZAZIONE: ENDO2_INVENTARIOPROC.AMMIN
	     */
	    String codiceammin = configParams.getAmministrazione(); // verticalizzazioniparametriAMMINISTRAZIONE.getValore();
	    if (StringUtils.isNotBlank(codiceammin)) {
		amministrazioni = amministrazioniService.findById(new PkId(new Integer(codiceammin.trim())));
	    }
	    /*
	     * AZIONI
	     */
	    String codiceazione = configParams.getAzioni();// verticalizzazioniparametriAZIONI.getValore();
	    if (StringUtils.isNotBlank(codiceazione)) {
		azioni = azioniService.findById(new Integer(codiceazione.trim()));
	    }
	    /*
	     * ENDO2_TIPIFAMIGLIEENDO.CODICE
	     */
	    String codiceFAMIGLIAENDO = configParams.getFamigliaEndo();// verticalizzazioniparametriFAMIGLIAENDO.getValore();
	    if (StringUtils.isNotBlank(codiceFAMIGLIAENDO)) {
		tipifamiglieendo = tipifamiglieendoService.findById(new PkId(new Integer(codiceFAMIGLIAENDO.trim())));
	    }
	    String codiceTempificazione = configParams.getTempificazione();
	    Tempificazioni tempificazioni = null;
	    if (StringUtils.isNotBlank(codiceTempificazione)) {
		tempificazioni = tempificazioniService.findById(new PkId(Integer.valueOf(codiceTempificazione.trim())));
	    }
	    String idAlberoproc = configParams.getRootAlberoproc();
	    log.debug("inserimentoAlberoproc: recuperato il parametro {}={}", WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC, idAlberoproc);
	    if (StringUtils.isNotBlank(idAlberoproc)) {
		Alberoproc alberoprocRoot = alberoprocService.findById(new PkId(new Integer(idAlberoproc.trim())));
		if (alberoprocRoot != null) {
		    Tipiendo tipiendo = null;
		    /*
		     * RECUPERO LA PRIMA VOCE DELL'ALBERO. LA CATEGORIA VA INSERITA IN STP_CATEGORIE_ENDO1 E IN TIPIENDO
		     */
		    String descrizioneCategoria = alberoprocRoot.getScDescrizione();
		    StpCategorieEndo1 categoriaEndoAlberoproc = stpCategorieEndo1Service.findByStpCodice(CODICE_ALBEROPROC_TIPIENDO_DEFAULT);
		    if (categoriaEndoAlberoproc == null) {
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
			StpCategorieEndo1 categorieEndo1 = new StpCategorieEndo1();
			categorieEndo1.setTipiendo(tipiendo);
			categorieEndo1.setCodiceStp(CODICE_ALBEROPROC_TIPIENDO_DEFAULT);
			categorieEndo1.setDescrizione(tipiendo.getTipo());
			stpCategorieEndo1Service.insert(categorieEndo1);
		    } else {
			tipiendo = categoriaEndoAlberoproc.getTipiendo();
			tipiendo.setTipifamiglieendo(tipifamiglieendo);
			tipiendo.setSoftware(software);
			tipiendo.setTipo(descrizioneCategoria);
			tipiendo.setOrdine(0);
			tipiendoService.update(tipiendo);
			categoriaEndoAlberoproc.setDescrizione(tipiendo.getTipo());
			stpCategorieEndo1Service.update(categoriaEndoAlberoproc);
		    }
		    // TROVARE TUTTI I RECORD DI STP_ENDO_TIPO2 E CONTROLLARE SE SONO STATI INSERITI COME ENDOPROCEDIMENTI
		    // SE NO VANNO INSERITI ED ASSOCIATI A ALBEROPROC_ENDO
		    log.debug("prima di recuperare la lista dei codici STP");
		    List<Integer> endoInseriti = stpEndoTipo2Service.findCodiciBySoftwareAndTipo(software.getCodice(), StpEndoTipo2Service.TIPO_ENDO);
		    log.debug("lista trovata? {}", !endoInseriti.isEmpty());
		    for (Integer stpEndoTipo2Id : endoInseriti) {
			alberoprocService.flush();
			StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findById(new PkId(stpEndoTipo2Id));
			Inventarioprocedimenti inventarioprocedimenti = stpEndoTipo2.getInventarioprocedimenti();
			Alberoproc albero = stpEndoTipo2.getAlberoproc();
			String scCodice = albero.getScCodice();
			String descrizionePadre = "";
			String descrizione = albero.getScDescrizione();
			String scCodicePadre = scCodice.substring(0, (scCodice.length() - 2));
			Alberoproc alberoprocPadre = alberoprocService.findByScCodice(scCodicePadre);
			if (alberoprocPadre != null) {
			    descrizionePadre = alberoprocPadre.getScDescrizione();
			}
			if (StringUtils.isNotBlank(descrizionePadre)) {
			    descrizione += CODICE_DESCRIZIONE_SEPARATOR + descrizionePadre;
			}
			if (descrizione.length() > 255) {
			    descrizione = descrizione.substring(0, 255);
			}
			if (inventarioprocedimenti == null) {
			    inventarioprocedimenti = new Inventarioprocedimenti();
			    inventarioprocedimenti.setTipoendo(tipiendo);
			    inventarioprocedimenti.setProcedimento(descrizione);
			    inventarioprocedimenti.setSoftware(software);
			    inventarioprocedimenti.setDataaggiornamento(new Date());
			    inventarioprocedimenti.setNaturaendo(naturaendo);
			    inventarioprocedimenti.setTipomovimento(tipimovimento);
			    inventarioprocedimenti.setAmministrazioni(amministrazioni);
			    inventarioprocedimenti.setTempificazione(tempificazioni);
			    inventarioprocedimenti.setCodiceancitel(stpEndoTipo2.getCodiceEndoRegionale());
			    inventarioprocedimenti.setDataaggiornamento(Calendar.getInstance().getTime());
			    if (log.isDebugEnabled()) {
				log.debug("Inserisco su inventario procedimenti {}", descrizione);
			    }
			    inventarioprocedimenti.setOrdine(0);
			    inventarioprocedimentiService.insert(inventarioprocedimenti);
			    stpEndoTipo2.setInventarioprocedimenti(inventarioprocedimenti);
			    stpEndoTipo2Service.update(stpEndoTipo2);
			} else {
			    inventarioprocedimenti = inventarioprocedimentiService.findById(new PkId(inventarioprocedimenti.getId().getCodice()));
			    StpEndoTipo2 stpEndoTipo2Temp = new StpEndoTipo2();
			    stpEndoTipo2Temp = stpEndoTipo2Service.findById(new PkId(stpEndoTipo2.getId().getCodice()));
			    inventarioprocedimenti.setCodiceancitel(stpEndoTipo2Temp.getCodiceEndoRegionale());
			    // inventarioprocedimenti.setTempificazione(tempificazioni);
			    // inventarioprocedimenti.setAmministrazioni(amministrazioni);
			    // inventarioprocedimenti.setTipoendo(tipiendo);
			    // inventarioprocedimenti.setProcedimento(descrizione);
			    inventarioprocedimenti.setDataaggiornamento(new Date());
			    if (inventarioprocedimenti.getNaturaendo() == null) {
				inventarioprocedimenti.setNaturaendo(naturaendo);
			    }
			    if (inventarioprocedimenti.getTipomovimento() == null) {
				inventarioprocedimenti.setTipomovimento(tipimovimento);
			    }
			    if (inventarioprocedimenti.getAmministrazioni() == null) {
				inventarioprocedimenti.setAmministrazioni(amministrazioni);
			    }
			    inventarioprocedimentiService.update(inventarioprocedimenti);
			}
			AlberoprocEndoId alberoprocEndoId = new AlberoprocEndoId();
			alberoprocEndoId.setFkscid(albero.getId().getCodice());
			alberoprocEndoId.setCodiceinventario(inventarioprocedimenti.getId().getCodice());
			AlberoprocEndo alberoprocEndo = alberoprocEndoService.findById(alberoprocEndoId);
			if (alberoprocEndo == null) {
			    alberoprocEndo = new AlberoprocEndo();
			    alberoprocEndo.setAlberoproc(albero);
			    alberoprocEndo.setInventarioprocedimento(inventarioprocedimenti);
			    alberoprocEndo.setId(alberoprocEndoId);
			    alberoprocEndo.setFlagRichiesto(Boolean.TRUE);
			    alberoprocEndo.setFlagPrincipale(Boolean.TRUE);
			    //TODO
			    //			    todo da matterci il valore =
			    // alberoprocEndo.setAzione(azioni);
			    alberoprocEndo.setAzione(null);
			    if (log.isDebugEnabled()) {
				log.debug("Inserisco alberoprocendo");
			    }
			    List<AlberoprocEndo> lis = alberoprocEndoService.findAllByAlberoproc(albero.getId().getCodice());
			    for (AlberoprocEndo ae2 : lis) {
				if (BooleanUtils.toBoolean(ae2.getFlagPrincipale())) {
				    alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
				    log.error("Errore in inserimento endo principale per la voce dell'albero " +
					    albero.getId().getCodice() +
					    " endo " +
					    inventarioprocedimenti.getId().getCodice() +
					    ", codice endo regionale=" +
					    stpEndoTipo2.getCodiceEndoRegionale());
				    break;
				}
			    }
			    alberoprocEndoService.insert(alberoprocEndo);
			} else {
			    alberoprocEndo.setAlberoproc(albero);
			    alberoprocEndo.setInventarioprocedimento(inventarioprocedimenti);
			    alberoprocEndo.setFlagRichiesto(Boolean.TRUE);
			    alberoprocEndo.setFlagPrincipale(Boolean.TRUE);
			    //TODO
			    //			    todo non aggiornare il valore
			    alberoprocEndo.setAzione(azioni);
			    //alberoprocEndo.setAzione(null);
			    if (log.isDebugEnabled()) {
				log.debug("aggiorno alberoprocendo");
			    }
			    List<AlberoprocEndo> lis = alberoprocEndoService.findAllByAlberoproc(albero.getId().getCodice());
			    for (AlberoprocEndo ae2 : lis) {
				if (BooleanUtils.toBoolean(ae2.getFlagPrincipale())) {
				    alberoprocEndo.setFlagPrincipale(Boolean.FALSE);
				    log.error("Errore in inserimento endo principale per la voce dell'albero " +
					    albero.getId().getCodice() +
					    " endo " +
					    inventarioprocedimenti.getId().getCodice() +
					    ", codice endo regionale=" +
					    stpEndoTipo2.getCodiceEndoRegionale());
				    break;
				}
			    }
			    alberoprocEndoService.update(alberoprocEndo);
			}
			flushAndClearSession();
		    }
		}
	    }
	}
	// §§§END§§§
    }

    /**
     * La funzione recupera la radice dell'albero dei procedimenti alla quale attaccare l'alberatura del dizionario.
     * Questa voce è configurata nelle verticalizzazioni. Una volta individuata la voce viene richiamata la funzione di
     * inserimento ricorsivo.
     * 
     * @param helper
     * @param software
     */
    private void inserimentoAlberoproc(CartAlberoprocHelper helper, Software software, boolean isSovrascriviConfigurazioneAlbero,
	    boolean trustStpCodice) {

	// prima voce è vuota e va agganciata all'id dell'alberoprocedimenti presa da verticalizzazioni
	CartServiceConfigurationParameters configParams = CartServiceConfigurationHelper.getConfigurationParameters();
	String idAlberoproc = configParams.getRootAlberoproc();
	if (log.isDebugEnabled()) {
	    log.debug("inserimentoAlberoproc: recuperato il parametro {}={}", WebConstants.VERTICALIZZAZIONE_CART_ROOT_ALBEROPROC, idAlberoproc);
	}
	if (StringUtils.isNotBlank(idAlberoproc)) {
	    Alberoproc alberoproc = alberoprocService.findById(new PkId(new Integer(idAlberoproc)));
	    if (alberoproc != null) {
		// update di tutte le voci dell'albero della colonna sc_codice con codice temporaneo
		String scCodiceIniziale = alberoproc.getScCodice();
		//		if (log.isDebugEnabled()) {
		//		    log.debug("inserimentoAlberoproc: scCodiceIniziale={}", scCodiceIniziale);
		//		    log.debug("inserimentoAlberoproc: prima di spostare le voci dell'albero");
		//		}
		//		alberoprocService.updateAssegnaCodiceASottoVoci(scCodiceIniziale);
		// elaborazione di helper con funzione ricorsiva
		log.debug("inserimentoAlberoproc: prima di inserire ricorsivamente le voci del dizionario");
		AlberoprocBusinessRules rule = new AlberoprocBusinessRules();
		rule.setCustomRule(AlberoprocBusinessRules.CustomRuleEnum.eseguiOperazioniSuCache.name(), false);
		SigeproBusinessRules.setClassRules(AlberoprocBusinessRules.class, rule);
		this.insertAlberaturaRicorsivamente(helper.getChilds(), scCodiceIniziale, software, isSovrascriviConfigurazioneAlbero,
			trustStpCodice);
		this.bonificaGerarchiaAlbero(helper);
		SigeproBusinessRules.buildDefaultRules();
		alberoprocService.updateAlberoprocCache();
	    }
	} else {
	    // TODO rilanciare un'eccezione???
	}
    }

    /**
     * La funzione inserisce/modifica dalla struttura ricorsiva creata tutte le voci del dizionario in ALBEROPROC e
     * STP_ENDO_TIPO2
     * 
     * @param childs
     * @param scCodiceIniziale
     * @param software
     * @param trustStpCodice
     *            TODO
     */
    private void insertAlberaturaRicorsivamente(List<CartAlberoprocHelper> childs, String scCodiceIniziale, Software software,
	    boolean isSovrascriviConfigurazioneAlbero, boolean trustStpCodice) {

	// §§§BEGIN§§§
	int codice = 0;
	for (CartAlberoprocHelper cartAlberoprocHelper : childs) {
	    Alberoproc padre = alberoprocService.findByScCodice(scCodiceIniziale);
	    if (padre != null) {
		log.debug("insertAlberaturaRicorsivamente# padre {}-{}-{}",
			new String[] { String.valueOf(padre.getId().getCodice()), scCodiceIniziale, padre.getScDescrizione() });
	    }
	    codice++;
	    String nuovoCodice = "";
	    // String nuovoCodice = scCodiceIniziale + calcolaNuovoCodice(codice);
	    // verifico se esiste già una voce dell'albero con quel codice
	    // altrimenti potrei avere errore chiave duplicata
	    // controllare se inserito anche stpendotipo2
	    // se si probabilmente inserire su alberoproc altrimenti aggiornare la voce
	    Integer codiceStp = Integer.valueOf(cartAlberoprocHelper.getCodiceStp());
	    log.debug("insertAlberaturaRicorsivamente# codiceStp: {}", codiceStp);
	    String tipologiaEndo = cartAlberoprocHelper.getTipologiaEndo();
	    log.debug("insertAlberaturaRicorsivamente# tipologiaEndo: {}", tipologiaEndo);
	    BigInteger codiceTipologiaEndoRegionale = cartAlberoprocHelper.getCodiceTipologiaEndoRegionale();
	    log.debug("insertAlberaturaRicorsivamente# codiceTipologiaEndoRegionale: {}", codiceTipologiaEndoRegionale);
	    /*
	     * LION 06-06-19 se è considerato attendibile il codice stp lo uso per recuperare l'stp_endo_tipo2
	     * altrimenti lo recupero per codice regionale ed aggiorno il codice stp con quello che arriva dal servizio
	     */
	    StpEndoTipo2 endoTipo2 = null;
	    if (trustStpCodice) {
		endoTipo2 = stpEndoTipo2Service.findbyStpCodice(codiceStp, tipologiaEndo);
	    } else {
		endoTipo2 = stpEndoTipo2Service.findbyTipoAndCodiceTipologiaEndoAndCodiceRegionale(
			codiceTipologiaEndoRegionale == null ? null : codiceTipologiaEndoRegionale.intValue(), tipologiaEndo,
			StringUtils.defaultString(cartAlberoprocHelper.getCodiceEndoRegionale()).trim());
	    }
	    //END LION
	    StpTipologieEndo2 stpTipologieEndo2 = null;
	    if (codiceTipologiaEndoRegionale != null) {
		log.debug("insertAlberaturaRicorsivamente# codiceTipologiaEndoRegionale not null");
		stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(codiceTipologiaEndoRegionale.intValue()));
	    }
	    //LION 06-06-19
	    if (endoTipo2 != null && endoTipo2.getAlberoproc() != null && endoTipo2.getAlberoproc().getId().getCodice() != null) {
		//END LION
		Integer oldCodiceStp = endoTipo2.getCodiceStp();
		log.debug("insertAlberaturaRicorsivamente# oldCodiceStp {}", codiceStp);
		// Nel caso in cui non vogliamo aggiornare (isSovrascriviConfigurazioneAlbero==false )devo salvarmi l'azione 
		//che  associata alla  voce dell'albero.Questo perchè devo comunque fare l'update della voce dell'albero
		//in quanto deve essere cambiato il codice, per rispettare l'ordine di inserimento di eventuali nuove voci 
		// dell'albero.		
		// update alberoproc
		Alberoproc alberoproc = endoTipo2.getAlberoproc();
		nuovoCodice = alberoproc.getScCodice();
		log.debug("insertAlberaturaRicorsivamente# nuovoCodice prima {}", nuovoCodice);
		if (!(nuovoCodice.startsWith(scCodiceIniziale) && nuovoCodice.length() == (scCodiceIniziale.length() + 2))) {
		    // è cambiato scCodice ne devo trovare un altro
		    List<Alberoproc> figli = alberoprocService.findAlberoprocFigli(scCodiceIniziale, true, DAOOrderTypeEnum.ASC, true);
		    nuovoCodice = "";
		    if (figli.size() > 0) {
			for (Alberoproc alberoproc2 : figli) {
			    nuovoCodice = alberoproc2.getScCodice();
			}
			nuovoCodice = alberoprocService.calcolaProssimoCodice(nuovoCodice);
		    } else {
			nuovoCodice = scCodiceIniziale + "01";
		    }
		}
		log.debug("insertAlberaturaRicorsivamente# nuovoCodice dopo {}", nuovoCodice);
		alberoproc.setScCodice(nuovoCodice);
		alberoproc.setScDescrizione(cartAlberoprocHelper.getAlberoprocData().getScDescrizione());
		log.debug("insertAlberaturaRicorsivamente# alberoproc.setScDescrizione {}", alberoproc.getScDescrizione());
		alberoproc.setScNote(cartAlberoprocHelper.getAlberoprocData().getScNote());
		// Nuovo comportamento (Gianpaolo Todini BUG 700); in inserimento deve essere null e in modifica deve
		// mantenere la configurazione data.
		//alberoproc.setScPubblica(statoPubblicazioneAlberoproc);
		alberoproc.setScStatoControllo("M");
		// Nel caso devo sovra scrivere le vecchie informazioni con le nuove recupero la nuova azione impostata
		// nella maschera di preelaborazione
		if (isSovrascriviConfigurazioneAlbero) {
		    Azioni azioni = azioniService.findById(cartAlberoprocHelper.getAlberoprocData().getAzione().getAzId());
		    alberoproc.setAzione(azioni);
		    alberoproc.setScOrdine(0);
		}
		if (cartAlberoprocHelper.getDataFineValidita() != null) {
		    if (cartAlberoprocHelper.getDataFineValidita().compareTo(Calendar.getInstance()) <= 0) {
			alberoproc.setScAttivo(Boolean.TRUE); // true vuol dire che è disattivo
		    } else {
			alberoproc.setScAttivo(Boolean.FALSE); // false vuol dire che è attivo
		    }
		}
		if (!codiceStp.equals(oldCodiceStp)) {
		    endoTipo2.setCodiceStp(codiceStp);
		    endoTipo2.setOggetti(null); // cambiando il codice STP devo invalidare la scheda di spiegazione
		}
		//LION se ho identificato l'stp_endo_tipo2 per stp_codice allora aggiorno codice endo regionale
		// else if (trustStpCodice) {
		//    endoTipo2.setCodiceEndoRegionale(cartAlberoprocHelper.getCodiceEndoRegionale().trim());
		// }
		log.debug("insertAlberaturaRicorsivamente# prima dell'update");
		alberoprocService.update(alberoproc);
		endoTipo2.setCodiceEndoRegionale(StringUtils.defaultString(cartAlberoprocHelper.getCodiceEndoRegionale()).trim());
		endoTipo2.setStpTipologieEndo2(stpTipologieEndo2);
		stpEndoTipo2Service.update(endoTipo2);
	    } else {
		// insert alberoproc
		Alberoproc alberoproc = cartAlberoprocHelper.getAlberoprocData();
		// Nuovo comportamento (Gianpaolo Todini BUG 700); in inserimento deve essere null
		alberoproc.setScPubblica(null);
		//		alberoproc.setScPubblica(statoPubblicazioneAlberoproc);
		alberoproc.setScStatoControllo("I");
		alberoproc.setScOrdine(0);
		alberoproc.setSoftware(software);
		if (cartAlberoprocHelper.getDataFineValidita() != null) {
		    if (cartAlberoprocHelper.getDataFineValidita().compareTo(Calendar.getInstance()) <= 0) {
			alberoproc.setScAttivo(Boolean.TRUE); // true vuol dire che è disattivo
		    } else {
			alberoproc.setScAttivo(Boolean.FALSE); // false vuol dire che è attivo
		    }
		}
		log.debug("insertAlberaturaRicorsivamente# INSERT alberoproc.getScDescrizione {}", alberoproc.getScDescrizione());
		// Azione configurata sull'albero dei procedimenti in fase di inserimento del dizionario.
		alberoproc.setAzione(cartAlberoprocHelper.getAlberoprocData().getAzione());
		alberoprocService.insertAlberoproc(alberoproc, padre);
		log.debug("insertAlberaturaRicorsivamente# INSERT alberoproc.getScScodice {}", alberoproc.getScCodice());
		nuovoCodice = alberoproc.getScCodice();
		// insert stpendotipo2 
		endoTipo2 = new StpEndoTipo2();
		endoTipo2.setAlberoproc(alberoproc);
		endoTipo2.setCodiceStp(codiceStp);
		log.debug("insertAlberaturaRicorsivamente# INSERT endoTipo2.setCodiceStp {}", endoTipo2.getCodiceStp());
		endoTipo2.setTipo(cartAlberoprocHelper.getTipologiaEndo());
		endoTipo2.setCodiceEndoRegionale(StringUtils.defaultString(cartAlberoprocHelper.getCodiceEndoRegionale()).trim());
		log.debug("insertAlberaturaRicorsivamente# INSERT endoTipo2.setCodiceEndoRegionale {}", endoTipo2.getCodiceEndoRegionale());
		endoTipo2.setStpTipologieEndo2(stpTipologieEndo2);
		stpEndoTipo2Service.insert(endoTipo2);
	    }
	    flushAndClearSession();
	    if (!cartAlberoprocHelper.getChilds().isEmpty()) {
		insertAlberaturaRicorsivamente(cartAlberoprocHelper.getChilds(), nuovoCodice, software, isSovrascriviConfigurazioneAlbero,
			trustStpCodice);
	    }
	}
	// §§§END§§§
    }

    /**
     * La funzione popola l'oggetto helper che conterrà la gerarchia che rappresenta le voci di ALBEROPROC. Ci sono
     * quattro oggetti principali del dizionario:
     * <ul>
     * <li>TipologiaEndoTipo2: Rappresenta la specifica operazione sull'attività (Avvio, cessazione, ecc...)</li>
     * <li>CategorieAttivita: rappresentano una struttura ad albero che può contenere più livelli (ES: AGRICOLTURA,
     * SILVICOLTURA E PESCA - COLTIVAZIONI AGRICOLE E PRODUZIONE DI PRODOTTI ANIMALI, CACCIA E SERVIZI CONNESSI -
     * COLTIVAZIONE DI COLTURE AGRICOLE NON PERMANENTI)</li>
     * <li>Attività: rappresentano l'ultima cartella dell'alberatura ovvero la descrizione dell'endo di tipo 2 (ES:
     * Coltivazione di riso)</li>
     * <li>EndoTipo2: è l'elemento che collega una attività ad una tipologia di endo (es: Coltivazione di
     * riso-avvio)</li>
     * </ul>
     * Queste entita vengono riportate nella struttura ricorsiva {@link CartAlberoprocHelper}
     * 
     * @param list
     * @return
     */
    private CartAlberoprocHelper createCartAlberoprocHelper(List<Object> list) {

	// §§§BEGIN§§§
	CartAlberoprocHelper ret = new CartAlberoprocHelper();
	ret.setRoot(true);
	List<EndoTipo2> endoTipo2s = new ArrayList<EndoTipo2>();
	Map<BigInteger, String> tipologiaEndoTipo2Map = new HashMap<BigInteger, String>();
	Map<String, CartAlberoprocHelper> endoMap = new HashMap<String, CartAlberoprocHelper>();
	List<Attivita> attivitas = new ArrayList<Attivita>();
	List<CategorieTree> categorieTrees = new ArrayList<CategorieTree>();
	// aggiungo i vari oggetti alle liste 
	log.debug("createCartAlberoprocHelper: aggiungo gli oggetti in memoria");
	int idEndo2 = 0;
	int idCategorie = 0;
	/*
	 * LION 06-06-19 : a cusa della storicizzazione del dizionario per ogni attività arrivano più elementi XML in ordine cronologico per data fine di validità  
	 * con il record dello stato attuale per ultimo perciò cilo al contrario e scarto tutte le attività che hanno un stp_codice uguale al precedente 
	 */
	//for (Object object : list) {
	Integer prevId = -100;
	for (int i = list.size() - 1; i > -1; i--) {
	    Object object = list.get(i);
	    if (object instanceof EndoTipo2) {
		endoTipo2s.add(idEndo2, (EndoTipo2) object);
		idEndo2++;
	    } else if (object instanceof CategoriaAttivita) {
		CategorieTree categorieTree = new CategorieTree();
		categorieTree.setCategoriaAttivita((CategoriaAttivita) object);
		categorieTrees.add(idCategorie, categorieTree);
		idCategorie++;
	    }
	    //vengono pre configurate e salvate nella fase preliminare
	    else if (object instanceof TipologiaEndoTipo2) {
		tipologiaEndoTipo2Map.put(((TipologiaEndoTipo2) object).getId(), ((TipologiaEndoTipo2) object).getNome());
	    } else if (object instanceof Attivita) {
		//LION 06-06-19 
		Attivita att = (Attivita) object;
		if (att.getId().intValue() != prevId) {
		    attivitas.add(att);
		    prevId = att.getId().intValue();
		}
		//END LION
	    }
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//      VENGONO CONFIGURATI E SALVATI NELLA FASE PRELIMINARE/////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	log.debug("createCartAlberoprocHelper: elaboro le tipologie endo tipo 2");
	//	Iterator<Map.Entry<BigInteger, String>> it = tipologiaEndoTipo2Map.entrySet().iterator();
	//	StpTipologieEndo2 stpTipologieEndo2 = null;
	//	while (it.hasNext()) {
	//	    Map.Entry<BigInteger, String> pairs = it.next();
	//	    Integer codice = pairs.getKey().intValue();
	//	    stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(codice));
	//	    if (stpTipologieEndo2 != null) {
	//		stpTipologieEndo2.setDescrizione(pairs.getValue());
	//		stpTipologieEndo2Service.update(stpTipologieEndo2);
	//	    } else {
	//		stpTipologieEndo2 = new StpTipologieEndo2();
	//		stpTipologieEndo2.getId().setCodice(codice);
	//		stpTipologieEndo2.setDescrizione(pairs.getValue());
	//		stpTipologieEndo2Service.insert(stpTipologieEndo2);
	//	    }
	//	}
	//TODO
	//	committare i dati e controllare se la tabella stpTipologieEndo2 abbia 
	//	tutte le fkidazioni settate se non sono state settate se non sono state settate generare l'errore con le spiegazioni delle
	//	configurazioni da fare
	// elaboro le categorie
	log.debug("createCartAlberoprocHelper: elaboro le categorie");
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
	List<CategorieTree> tree = root.getFigli();
	if (log.isDebugEnabled()) {
	    log.debug("Prima di elaborare ricorsivamente le categorie");
	}
	recurseCategorieAlberoprocHelper(ret, tree, endoMap);
	// fine elaborazione categorie endo
	//inizio elaborazione attività
	if (log.isDebugEnabled()) {
	    log.debug("Prima di gestire le attività");
	}
	for (Attivita attivita : attivitas) {
	    CartAlberoprocHelper helper = endoMap.get(StpEndoTipo2Service.TIPO_CATEGORIA + "_" + attivita.getCategoria());
	    if (helper != null) {
		CartAlberoprocHelper attivitahelper = new CartAlberoprocHelper();
		Alberoproc alberoproc = new Alberoproc();
		//LION: 12-06-19 il codice regionale non viene più anteposto alla descrizione perchè il nome dell'attività arriva dalla BDR già con il codice all'inizio del nome
		String descrizione = attivita.getNome();
		//END LION
		// BOCCI 18-05-2020 SEMBRA CHE IL NOME DELL'ATTIVITA' CHE ARRIVA DA DIZIONARIO NON CONTENGA IL CODICE ATTIVITA'.
		// QUESTO CODICE TIENE CONTO ANCHE DELLE CONSIDERAZIONI DI LION
		if (StringUtils.defaultString(descrizione).indexOf(StringUtils.defaultString(attivita.getCodice())) < 0) {
		    descrizione = attivita.getCodice() + CODICE_DESCRIZIONE_SEPARATOR + attivita.getNome();
		}
		// END BOCCI
		if (descrizione.length() > 300) {
		    descrizione = descrizione.substring(0, 299);
		}
		alberoproc.setScDescrizione(descrizione);
		alberoproc.setScNote(attivita.getNome());
		attivitahelper.setAlberoprocData(alberoproc);
		attivitahelper.setCodiceStp(String.valueOf(attivita.getId()));
		attivitahelper.setTipologiaEndo(StpEndoTipo2Service.TIPO_ATTIVITA);
		attivitahelper.setCodiceEndoRegionale(StringUtils.defaultString(attivita.getCodice()).trim());
		if (attivita.getDataFineValidita().toGregorianCalendar() != null) {
		    attivitahelper.setDataFineValidita(attivita.getDataFineValidita().toGregorianCalendar());
		}
		endoMap.put(StpEndoTipo2Service.TIPO_ATTIVITA + "_" + attivita.getId(), attivitahelper);
		helper.getChilds().add(attivitahelper);
	    } else {
		//TODO NON E' STATA TROVATA LA CATEGORIA POSSIBILE ERRORE DI COERENZA DEL DIZIONARIO???
	    }
	}
	//fine elaborazione attività
	//inizio elaborazione endo tipo2
	if (log.isDebugEnabled()) {
	    log.debug("Prima elaborare ricorsivamente gli endo di tipo2");
	}
	for (EndoTipo2 endoTipo2 : endoTipo2s) {
	    if (endoTipo2.getId().intValue() != -1) { // FIXME POSSONO ARRIVARE ANCHE ENDO CON ID=-1 HO CHIESTO AD ENGINEERING
						      // E NON HO OTTENUTO ANCORA RISPOSTA SU QUESTI CODICI
		if (log.isDebugEnabled()) {
		    log.debug("elaboro endo {} con codice {} ", new Object[] { endoTipo2s.indexOf(endoTipo2), endoTipo2.getId() });
		}
		Integer attivita = endoTipo2.getAttivita().intValue();
		CartAlberoprocHelper helper = endoMap.get(StpEndoTipo2Service.TIPO_ATTIVITA + "_" + String.valueOf(attivita));
		if (helper != null) {
		    CartAlberoprocHelper endo = new CartAlberoprocHelper();
		    Alberoproc alberoproc = new Alberoproc();
		    String descrizione = tipologiaEndoTipo2Map.get(endoTipo2.getTipologia());
		    if (StringUtils.isNotBlank(descrizione)) {
			if (descrizione.length() > 300) {
			    descrizione = descrizione.substring(0, 299);
			}
		    } else {
			descrizione = "descrizione non presente per l'endo con id=" + endoTipo2.getId() + ", attivita=" + attivita;
		    }
		    // recupero l'azione dalla tipologia endo 2 (stp_tipologia_endo2) collegata all' endo tipo 2(stp_endotipo2)
		    // Integer codiceTipologiaendo2 = endoTipo2.getAttivita().intValue();
		    Integer codiceTipologiaendo2 = endoTipo2.getTipologia().intValue();
		    if (log.isDebugEnabled()) {
			log.debug("Cerco stp tipologia endo 2 con codice {} ", codiceTipologiaendo2);
		    }
		    // Recupero da di la tipologia 2 
		    StpTipologieEndo2 stpTipologieEndo2DB = stpTipologieEndo2Service.findById(new PkId(codiceTipologiaendo2));
		    if (stpTipologieEndo2DB != null
			    && (stpTipologieEndo2DB.getAzioni() != null && stpTipologieEndo2DB.getAzioni().getAzId() != null)) {
			if (log.isDebugEnabled()) {
			    log.debug("Trovato stp tipologia endo 2 con azione {} ", stpTipologieEndo2DB.getAzioni().getAzDescrizione());
			}
			// setto all'albero proc l'azione trovata nell' endo tipologia 2 
			alberoproc.setAzione(stpTipologieEndo2DB.getAzioni());
		    } else {
			// NON è possibile che non esista la tipologia endo 2 per un tipo endo 2 
		    }
		    alberoproc.setScDescrizione(descrizione);
		    alberoproc.setScNote(tipologiaEndoTipo2Map.get(endoTipo2.getTipologia()));
		    endo.setAlberoprocData(alberoproc);
		    endo.setCodiceStp(String.valueOf(endoTipo2.getId()));
		    endo.setTipologiaEndo(StpEndoTipo2Service.TIPO_ENDO);
		    endo.setCodiceEndoRegionale(StringUtils.defaultString(helper.getCodiceEndoRegionale()).trim());
		    endo.setCodiceTipologiaEndoRegionale(endoTipo2.getTipologia());
		    if (endoTipo2.getDataFineValidita().toGregorianCalendar() != null) {
			endo.setDataFineValidita(endoTipo2.getDataFineValidita().toGregorianCalendar());
		    }
		    endoMap.put(StpEndoTipo2Service.TIPO_ENDO + "_" + endoTipo2.getId(), endo);
		    helper.getChilds().add(endo);
		} else {
		    //TODO NON E' STATA TROVATA L'ATTIVITA' POSSIBILE ERRORE DI COERENZA DEL DIZIONARIO???
		}
	    } else {
		log.warn("Trovato un endo con id={},Attività={},Tipologia={}",
			new Object[] { endoTipo2.getId(), endoTipo2.getAttivita(), endoTipo2.getTipologia() });
	    }
	}
	//fine elaborazione endo tipo2
	return ret;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void recurseCategorieAlberoprocHelper(CartAlberoprocHelper ret, List<CategorieTree> tree, Map<String, CartAlberoprocHelper> endoMap) {

	// §§§BEGIN§§§
	List<CartAlberoprocHelper> figli = new ArrayList<CartAlberoprocHelper>();
	for (CategorieTree categorieTree : tree) {
	    CartAlberoprocHelper ret2 = new CartAlberoprocHelper();
	    Alberoproc alberoproc = new Alberoproc();
	    String descrizione = categorieTree.getCategoriaAttivita().getCodice()
		    + CODICE_DESCRIZIONE_SEPARATOR
		    + categorieTree.getCategoriaAttivita().getNome();
	    if (descrizione.length() > 300) {
		descrizione = descrizione.substring(0, 299);
	    }
	    alberoproc.setScDescrizione(descrizione);
	    alberoproc.setScNote(categorieTree.getCategoriaAttivita().getNome());
	    ret2.setTipologiaEndo(StpEndoTipo2Service.TIPO_CATEGORIA);
	    ret2.setAlberoprocData(alberoproc);
	    ret2.setCodiceStp(String.valueOf(categorieTree.getCategoriaAttivita().getId()));
	    ret2.setCodiceEndoRegionale(StringUtils.defaultString(categorieTree.getCategoriaAttivita().getCodice()).trim());
	    if (categorieTree.getCategoriaAttivita().getDataFineValidita().toGregorianCalendar() != null) {
		ret2.setDataFineValidita(categorieTree.getCategoriaAttivita().getDataFineValidita().toGregorianCalendar());
	    }
	    figli.add(ret2);
	    endoMap.put(StpEndoTipo2Service.TIPO_CATEGORIA + "_" + String.valueOf(categorieTree.getCategoriaAttivita().getId()), ret2);
	    if (!categorieTree.getFigli().isEmpty()) {
		if (log.isDebugEnabled()) {
		    log.debug("recurseAlberoprocHelper: \n\t elaboro figli: {}", categorieTree.getFigli().size());
		}
		recurseCategorieAlberoprocHelper(ret2, categorieTree.getFigli(), endoMap);
	    }
	}
	ret.setChilds(figli);
	// §§§END§§§
    }

    private void flushAndClearSession() {

	alberoprocService.flush();
	alberoprocService.clear();
    }

    /**
     * Deve recuperare daglie gli endo di tipo 2 il codice contenuto nel campo codice_endo_regionale per determire il
     * tipo di amministrazione Es. ASL 54.3 devo recuperare: ASL
     * 
     * @param list
     * @param software
     */
    private void prepareInserimentoInventarioprocedimento(CartInfoDizionarioHelper dizioHelper, Software software, boolean checkStpCodice) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("prepareInseriemento# Inizio preInserimento........");
	}
	List<Object> list = dizioHelper.getListDaDizionario();
	// Definizioni variabili
	//CartInfoDizionarioHelper cartInfoDizionarioHelper = new CartInfoDizionarioHelper();
	//List<TipologiaEndoTipo1> tipologiaEndoTipo1s = new ArrayList<TipologiaEndoTipo1>();
	//List<CategoriaEndoTipo1> categoriaEndoTipo1s = new ArrayList<CategoriaEndoTipo1>();
	List<EndoTipo1> endoTipo1s = new ArrayList<EndoTipo1>();
	List<EndoTipo1Helper> endoTipo1Helpers = new ArrayList<EndoTipo1Helper>();
	// Scorro tutta la lista per separare i tre diversi tipi di oggetti presenti TipologiaEndoTipo1,CategoriaEndoTipo1,EndoTipo1
	if (log.isDebugEnabled()) {
	    log.debug(
		    "prepareInseriemento# Scorro la lista di object generici per creare le tre liste di TipologiaEndoTipo1,CategoriaEndoTipo1,EndoTipo1 ");
	}
	for (Object object : list) {
	    //	    if (object instanceof TipologiaEndoTipo1) {
	    //		tipologiaEndoTipo1s.add((TipologiaEndoTipo1) object);
	    //	    }
	    //	    cartInfoDizionarioHelper.setTipologiaEndoTipo1s(tipologiaEndoTipo1s);
	    //	    if (object instanceof CategoriaEndoTipo1) {
	    //		categoriaEndoTipo1s.add((CategoriaEndoTipo1) object);
	    //	    }
	    //	    cartInfoDizionarioHelper.setCategoriaEndoTipo1s(categoriaEndoTipo1s);
	    if (object instanceof EndoTipo1) {
		endoTipo1s.add((EndoTipo1) object);
	    }
	    //	    cartInfoDizionarioHelper.setEndoTipo1s(endoTipo1s);
	}
	if (log.isDebugEnabled()) {
	    log.debug(
		    "prepareInseriemento# Creo la lista di EndoTipo1Helper utilizzato per la pre configurazione necessaria per l'elaborazione dei messaggi ");
	}
	//Creo la lista di EndoTipo1Helper utilizzato per la pre configurazione necessaria per l'elaborazione dei messaggi 
	List<String> amministrazioneTrovate = new ArrayList<String>();
	EndoTipo1Helper endoTipo1Helper = null;
	/*
	 * LION 05-06-2019: modifiche per gestione dati storici del dizioanrio endo. 
	 * I record storici vengono serviti per primi quindi si elaborano solo gli ultimi EndoTipo1 fra quelli che hanno lo stesso id.
	 * Ciclando la lista al contrario gli ultimi diventano i primi e i record storici vengono rimossi affinché non siano processati
	 */
	BigInteger prevId = new BigInteger("-100");
	//for (EndoTipo1 endoTipo1 : endoTipo1s) {
	List<Integer> stpNonValidi = new ArrayList<Integer>();
	int numValidi = 0;
	int totEndo = endoTipo1s.size();
	for (int i = endoTipo1s.size() - 1; i > -1; i--) {
	    EndoTipo1 endoTipo1 = endoTipo1s.get(i);
	    if (endoTipo1.getId().equals(prevId)) {
		endoTipo1s.remove(i);
		totEndo--;
		continue;
	    }
	    prevId = endoTipo1.getId();
	    endoTipo1Helper = new EndoTipo1Helper();
	    endoTipo1Helper.setDatiEndo(endoTipo1);
	    //END LION 05-06-2019
	    String codiceAmministrazione = null;
	    if (StringUtils.isNotBlank(endoTipo1.getEnteCompetente())) {
		codiceAmministrazione = endoTipo1.getEnteCompetente();
	    } else {
		codiceAmministrazione = recuperaCodAmministrazioneDaCodRegionale(endoTipo1.getCodiceRegionale());
	    }
	    if (!amministrazioneTrovate.contains(codiceAmministrazione)) {
		endoTipo1Helper.setCodiceAmministrazioneCart(codiceAmministrazione);
		endoTipo1Helpers.add(endoTipo1Helper);
		amministrazioneTrovate.add(codiceAmministrazione);
	    }
	    //LION 10-06-19 conteggio dei codici stp validi per gli endo tipo 1
	    if (checkStpCodice) {
		List<StpEndoTipo1> matchList = this.stpEndoTipo1Service.findAllByStpCodice(prevId.intValue());
		boolean realMatch = false;
		for (int j = 0; j < matchList.size() && !realMatch; j++) {
		    StpEndoTipo1 stp1 = matchList.get(j);
		    if (stp1 != null && endoTipo1.getCodiceRegionale().equals(stp1.getCodiceEndoRegionale())) {
			numValidi++;
			realMatch = true;
		    }
		}
		if (!realMatch) {
		    stpNonValidi.add(prevId.intValue());
		}
	    }
	}
	if (checkStpCodice) {
	    dizioHelper.setNumEndoTipo1StpCodiceValido(numValidi);
	    dizioHelper.setTotEndoTipo1(endoTipo1s.size());
	    dizioHelper.setStpEndo1NonValidi(stpNonValidi.toString());
	}
	//END LION 10-06-19
	dizioHelper.setEndoTipo1Helpers(endoTipo1Helpers);
	if (log.isDebugEnabled()) {
	    log.debug("prepareInseriemento# Fine preInserimento........");
	}
    }

    private String recuperaCodAmministrazioneDaCodRegionale(String codiceEndoRegionale) {

	String field[] = StringUtils.defaultString(codiceEndoRegionale).split(" ");
	String codiceAmministrazione = StringUtils.defaultString(field[0]);
	int pos = 0;
	for (pos = 0; pos < codiceAmministrazione.length(); pos++) {
	    char c = codiceAmministrazione.charAt(pos);
	    if (Character.isDigit(c)) {
		break;
	    }
	}
	if (pos > 0) {
	    codiceAmministrazione = codiceAmministrazione.substring(0, pos);
	}
	return codiceAmministrazione;
    }

    /**
     * La funzione recupera dal dizionario gli elementi che saranno riversati in TIPIFAMIGLIEENDO, TIPIENDO E
     * INVENTARIOPROCEDIMENTI. Per ogni record inserito sarà creato un riferimento in STP_CATEGORIE_ENDO1
     * (TIPIFAMIGLIEENDO), STP_CATEGORIE_ENDO1 (TIPIENDO), STP_ENDO_TIPO1 (INVENTARIOPROCEDIMENTI)
     * 
     * @param software
     * @param trustStpCodice
     *            TODO
     * @param list
     */
    private void inserimentoInventarioprocedimenti(CartInfoDizionarioHelper cartInfoDizionarioHelper, Software software) {

	//recupero i collegamenti tra codice amministrazioni cart con le amministrazioni e tipi movimento, verranno utilizzati 
	// per associare agli inventario procedimenti che andremo a creare o modificare l'amministrazione e il tipo movimento.
	if (log.isDebugEnabled()) {
	    log.debug(
		    "elaboraMessaggio# Creo le mappa che conterranno rispettivamente [cod. amministrazioni cart-cod. amministrazione] e [cod. amministrazioni cart-cod. tipi moviemnto]...");
	}
	List<EndoTipo1Helper> endoTipo1Helpers = cartInfoDizionarioHelper.getEndoTipo1Helpers();
	Map<String, Integer> codiceAmministrCartCodiceAmministrMap = new HashMap<String, Integer>();
	Map<String, String> codiceAmministrCartCodiceTipoMovMap = new HashMap<String, String>();
	for (EndoTipo1Helper endoTipo1Helper : endoTipo1Helpers) {
	    codiceAmministrCartCodiceAmministrMap.put(endoTipo1Helper.getCodiceAmministrazioneCart(),
		    endoTipo1Helper.getAmministrazioni().getId().getCodice());
	    codiceAmministrCartCodiceTipoMovMap.put(endoTipo1Helper.getCodiceAmministrazioneCart(),
		    endoTipo1Helper.getTipimovimento().getId().getTipomovimento());
	}
	if (log.isDebugEnabled()) {
	    log.debug("elaboraMessaggio# Inizio inserimento Inventario procedimenti.....");
	}
	// Recupero la lista di oggetti genrici object che contiente tutte le informazioni del dizionario scaricato
	List<Object> list = cartInfoDizionarioHelper.getListDaDizionario();
	// §§§BEGIN§§§
	List<TipologiaEndoTipo1> tipologiaEndoTipo1s = new ArrayList<TipologiaEndoTipo1>();
	List<CategoriaEndoTipo1> categoriaEndoTipo1s = new ArrayList<CategoriaEndoTipo1>();
	List<EndoTipo1> endoTipo1s = new ArrayList<EndoTipo1>();
	for (Object object : list) {
	    if (object instanceof TipologiaEndoTipo1) {
		tipologiaEndoTipo1s.add((TipologiaEndoTipo1) object);
	    }
	    if (object instanceof CategoriaEndoTipo1) {
		categoriaEndoTipo1s.add((CategoriaEndoTipo1) object);
	    }
	    if (object instanceof EndoTipo1) {
		//LION 07-06-19
		endoTipo1s.add((EndoTipo1) object);
	    }
	}
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
		    log.debug("La tipologia [{}] non è censita la inserisco", nome);
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
		    log.debug("La tipologia [{}] esiste la aggiorno", nome);
		}
		tipifamiglieendo = stpTipologieEndo1.getTipifamiglieendo();
		tipifamiglieendo.setTipo(nome);
		tipifamiglieendoService.update(tipifamiglieendo);
	    }
	}
	flushAndClearSession();
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
		    log.debug("La categoria [{}] non esiste la inserisco", nome);
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
		    log.debug("La categoria [{}] esiste la aggiorno", nome);
		}
		tipiendo = stpCategorieEndo1.getTipiendo();
		tipiendo.setTipo(nome);
		tipiendoService.update(tipiendo);
	    }
	}
	flushAndClearSession();
	// EndoTipo1
	Integer categoria = null;
	String codiceRegionale = null;
	StpEndoTipo1 stpEndoTipo1 = null;
	id = null;
	nome = null;
	/*
	 * NATURAENDO.CODICENATURA
	 */
	CartServiceConfigurationParameters configParams = CartServiceConfigurationHelper.getConfigurationParameters();
	String codicenatura = configParams.getCodiceNatura();// verticalizzazioniparametriCODICENATURA.getValore();
	Naturaendo naturaendo = naturaendoService.findById(new NaturaendoId(new Integer(codicenatura)));
	//Naturaendo naturaendo = naturaendoService.findById(new PkId(new Integer(codicenatura)));
	//	/*
	//	 * TIPIMOVIMENTO.TIPOMOVIMENTO
	//	 */
	//	// Il movimento viene recuparato dalla configurazione all'interno della tabella AMMINISTRAZIONI.TIPOMOVIMENTO_CART
	//	Tipimovimento tipimovimento = null;
	//	String tipomovimento = configParams.getTipiMovimento();
	//	String codiceTempificazione = configParams.getTempificazione();
	//	Tempificazioni tempificazioni = null;
	//	if (StringUtils.isNotBlank(codiceTempificazione)) {
	//	    tempificazioni = tempificazioniService.findById(new PkId(Integer.valueOf(codiceTempificazione)));
	//	}
	//	if (StringUtils.isNotBlank(tipomovimento)) {
	//	    tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(ORMHelper.getIdcomune(), tipomovimento));
	//	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////RECUPERO DALLA CONFIGURAZIONE LA TEMPISTICA ///////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	String codiceTempificazione = configParams.getTempificazione();
	Tempificazioni tempificazioni = null;
	if (StringUtils.isNotBlank(codiceTempificazione)) {
	    tempificazioni = tempificazioniService.findById(new PkId(Integer.valueOf(codiceTempificazione)));
	}
	if (log.isDebugEnabled()) {
	    log.debug("Inserisco gli endoprocedimenti di tipo 1 ({})", endoTipo1s.size());
	}
	// MODIFICARE QUESTO COMPORTAMENTO
	Calendar oggi = GregorianCalendar.getInstance();
	//LION 07-06-19: deve essere ciclata tutta la lista degli endo tipo 1 e non la lista degli endo1helpers che contiene solo un elemento per ogni amministrazione
	for (EndoTipo1 endoTipo1 : endoTipo1s) {
	    //for (EndoTipo1Helper endoTipo1h : cartInfoDizionarioHelper.getEndoTipo1Helpers()) {
	    Inventarioprocedimenti inventarioprocedimenti = null;
	    id = endoTipo1.getId().intValue();
	    if (endoTipo1.getCategoria() != null) {
		categoria = endoTipo1.getCategoria().intValue();
	    } else {
		categoria = null;
	    }
	    if (log.isDebugEnabled()) {
		log.debug("Inserisco gli endoprocedimenti di tipo 1 ({})", endoTipo1s.size());
	    }
	    codiceRegionale = StringUtils.defaultString(endoTipo1.getCodiceRegionale()).trim();
	    Amministrazioni amministrazioni = null;
	    String tipoMovimento = "";
	    if (StringUtils.isNotBlank(codiceRegionale)) {
		// FIXME COME CODICE È STATO TROVATO ANCHE [cancellare - ASL.18] PER CUI ADOTTIAMO QUESTO TRICK
		codiceRegionale = codiceRegionale.replaceAll("cancellare - ", "").trim();
		// ENDFIXME
		String codiceAmministrazione = "";
		if (StringUtils.isNotBlank(endoTipo1.getEnteCompetente())) {
		    codiceAmministrazione = endoTipo1.getEnteCompetente();
		} else {
		    codiceAmministrazione = recuperaCodAmministrazioneDaCodRegionale(codiceRegionale);
		}
		tipoMovimento = codiceAmministrCartCodiceTipoMovMap.get(codiceAmministrazione);
		log.debug("insertEndoProcTipo1: cerco l'amministrazione con CodiceRegionale [{}]", codiceRegionale);
		// Dal codice amministrazione cart trovato recupero l'amministrazione a cui è collegato
		Integer codiceAmministrazioneDB = codiceAmministrCartCodiceAmministrMap.get(codiceAmministrazione);
		if (log.isDebugEnabled()) {
		    log.debug("Il codice amministrazione cart {} è associato all'amministrazione con codice {}({})",
			    new Object[] { codiceAmministrazione, codiceAmministrazioneDB, ORMHelper.getIdcomune() });
		}
		// L'amministrazione ci permetterà di configurare sull' inventario procedimento sia l'amministrazione
		// sia il movimento (ad ogni amministrazione collegata ad un'amministrazione cart (popolato il campo AMMINISTRAZIONE.CODICE_CART) 
		//è anche popolato obbligatoriamente anche AMMINISTRAZIONE.TIPOMOVIMENTO_CART)
		amministrazioni = amministrazioniService.findById(new PkId(codiceAmministrazioneDB));
		//amministrazioni = amministrazioniService.findAmministrazioniByCodiceancitel(codiceAmministrazione);
	    }
	    if (amministrazioni == null) {
		//		log.error("Non è stata trovata l'amministrazione con codice {} per l'endo: {}", codiceRegionale, id);
		//		// FIXME COSA FARE IN CASO CHE L'AMMINISTRAZIONE NON SIA STATA RECUPERATA METTERNE UNA DI DEAFULT? 
		//		amministrazioni = amministrazioniService.findAmministrazioneSportelloUnico();
		throw new RuntimeException("Non è stata configurata l'amministrazione cart con codice [" + codiceRegionale + "]");
	    }
	    nome = endoTipo1.getNome();
	    if (log.isDebugEnabled()) {
		log.debug("Elaboro l'endo di tipo 1 con id: {}, nome: {}", id, nome);
	    }
	    //LION 05-06-19 il codice regionale può essere modificato equindi vanno cercati per stp_codice
	    if (cartInfoDizionarioHelper.isTrustStpCodice()) {
		stpEndoTipo1 = stpEndoTipo1Service.findbyStpCodice(endoTipo1.getId().intValue());
	    } else {
		stpEndoTipo1 = stpEndoTipo1Service.findByCodiceEndoRegionale(codiceRegionale);
	    }
	    //END LION
	    XMLGregorianCalendar dataFine = endoTipo1.getDataFineValidita();
	    Boolean disabilitato = Boolean.FALSE;
	    if (dataFine != null) {
		if (dataFine.toGregorianCalendar().compareTo(oggi) <= 0) {
		    log.debug(
			    "insertEndoProcTipo1: l'endo di tipo 1 con id: {}, nome: {} è scaduto in data (dataFineValidità={}) lo segno come disabilitato",
			    new Object[] { id, nome, dataFine.toString() });
		    disabilitato = Boolean.TRUE;
		}
	    }
	    if (stpEndoTipo1 == null) {
		if (log.isDebugEnabled()) {
		    log.debug("L'endo non esiste lo inserisco in inventarioprocedimenti");
		}
		inventarioprocedimenti = new Inventarioprocedimenti();
		inventarioprocedimenti.setDisabilitato(disabilitato);
		inventarioprocedimenti.setAmministrazioni(amministrazioni);
		inventarioprocedimenti.setProcedimento(nome);
		inventarioprocedimenti.setCodiceancitel(codiceRegionale);
		inventarioprocedimenti.setSoftware(software);
		inventarioprocedimenti.setDataaggiornamento(new Date());
		inventarioprocedimenti.setNaturaendo(naturaendo);
		if (StringUtils.isNotBlank(tipoMovimento)) {
		    Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipoMovimento));
		    if (tm != null) {
			inventarioprocedimenti.setTipomovimento(tm);
		    }
		}
		inventarioprocedimenti.setTempificazione(tempificazioni);
		if (categoria != null) {
		    stpCategorieEndo1 = stpCategorieEndo1Service.findByStpCodice(categoria);
		    inventarioprocedimenti.setTipoendo(stpCategorieEndo1.getTipiendo());
		}
		inventarioprocedimenti.setOrdine(id);
		inventarioprocedimentiService.insert(inventarioprocedimenti);
		if (log.isDebugEnabled()) {
		    log.debug("L'endo non esiste lo inserisco in stp_Endo_Tipo1");
		}
		stpEndoTipo1 = new StpEndoTipo1();
		stpEndoTipo1.setCodiceStp(id);
		stpEndoTipo1.setCodiceEndoRegionale(codiceRegionale);
		stpEndoTipo1.setInventarioprocedimenti(inventarioprocedimenti);
		stpEndoTipo1Service.insert(stpEndoTipo1);
	    } else {
		if (log.isDebugEnabled()) {
		    log.debug("L'endo esiste lo aggiorno in inventario procedimenti");
		}
		//L'aggiornamento deve essere fatto solo se esplicitamente configurato in fase di pre elaborazione.
		// altrimenti per gli endo 1 già inseriti non verranno modificati.
		Integer oldCodiceStp = stpEndoTipo1.getCodiceStp();
		if (!id.equals(oldCodiceStp)) {
		    stpEndoTipo1.setCodiceStp(id);
		    stpEndoTipo1.setOggetti(null); // se cambiato il codice STP allora azzero la scheda di spiegazione
		    stpEndoTipo1Service.update(stpEndoTipo1);
		}
		if (cartInfoDizionarioHelper.getIsSovrascriviConfigurazioneEndo()) {
		    //LION 06-06-19: se i codici stp sono considerati affidabili allora devo aggiornate il codice regionale su stp_endo_tipo1
		    if (cartInfoDizionarioHelper.isTrustStpCodice()) {
			stpEndoTipo1.setCodiceEndoRegionale(codiceRegionale);
			stpEndoTipo1Service.update(stpEndoTipo1);
		    }
		    //END LION
		    inventarioprocedimenti = stpEndoTipo1.getInventarioprocedimenti();
		    inventarioprocedimenti.setDisabilitato(disabilitato);
		    inventarioprocedimenti.setAmministrazioni(amministrazioni);
		    inventarioprocedimenti.setProcedimento(nome);
		    inventarioprocedimenti.setCodiceancitel(codiceRegionale);
		    // La nuova logica non prevede l'aggiornamento della natura endo in modifica di
		    // un inventario procedimento già inserito.
		    // (Gianpaolo Todini BUG 700)
		    //inventarioprocedimenti.setNaturaendo(naturaendo);
		    if (inventarioprocedimenti.getNaturaendo() == null) {
			// BOCCI 20160504 con l'inserimento automatico degli endo da NLA-CART-COMUNICAZIONIINTERNE si rende  necessario scrivere la natura endo
			// se nulla altrimenti da errore di validazione dell'entity
			inventarioprocedimenti.setNaturaendo(naturaendo);
		    }
		    if (StringUtils.isNotBlank(tipoMovimento)) {
			Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(tipoMovimento));
			if (tm != null) {
			    inventarioprocedimenti.setTipomovimento(tm);
			}
		    }
		    inventarioprocedimenti.setTempificazione(tempificazioni);
		    inventarioprocedimenti.setDataaggiornamento(new Date());
		    if (inventarioprocedimenti.getOrdine() == null) {
			inventarioprocedimenti.setOrdine(id);
		    }
		    inventarioprocedimentiService.update(inventarioprocedimenti);
		}
	    }
	    flushAndClearSession();
	}
	if (log.isDebugEnabled()) {
	    log.debug("Ho finito l'inserimento degli endo di tipo 1");
	}
	// §§§END§§§
    }

    private void gestisciVerticalizzazioneTrustStpCodice(CartInfoDizionarioHelper hlp) {

	if (hlp != null && hlp.getTotAttivita() != null && hlp.getNumAttivitaStpCodiceValido() != null && hlp.getTotEndoTipo1() != null
		&& hlp.getNumEndoTipo1StpCodiceValido() != null) {
	    double attivitaTrustIndex = hlp.getNumAttivitaStpCodiceValido().doubleValue() / hlp.getTotAttivita();
	    double endoT1TrustIndex = hlp.getNumEndoTipo1StpCodiceValido().doubleValue() / hlp.getTotEndoTipo1();
	    boolean trust = false;
	    String vertValue = "N";
	    if (attivitaTrustIndex > this.STP_CODICE_TRUST_LIMIT && endoT1TrustIndex > STP_CODICE_TRUST_LIMIT) {
		trust = true;
		vertValue = "S";
	    }
	    hlp.setTrustStpCodice(trust);
	    //this.verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE);
	    Verticalizzazioniparametri vp = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		    WebConstants.VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE);
	    if (vp == null) {
		vp = new Verticalizzazioniparametri();
		Verticalizzazioniparametribase vpb = this.verticalizzazioniparametribaseService.findById(new VerticalizzazioniparametribaseId(
			WebConstants.VERTICALIZZAZIONE_CART, WebConstants.VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE));
		if (vpb != null) {
		    vp.setVerticalizzazioniparametribase(vpb);
		    Software sft = new Software();
		    sft.setCodice(ORMHelper.getSoftware());
		    vp.setSoftware(sft);
		    this.verticalizzazioniParametriService.insert(vp);
		} else {
		    throw new RuntimeException(
			    "E' impossibile scaricare il dizionario perché manca il parametro base della verticalizzazione CART.SYNC_DIZIONARIO_USA_STP_CODICE.");
		}
	    } else {
		vp.setValore(vertValue);
		this.verticalizzazioniParametriService.update(vp);
	    }
	}
    }

    private void aggiornaVerticalizzazioneTrustStpCodice(CartInfoDizionarioHelper dizioHelper) {

	boolean trust = true;
	Verticalizzazioniparametri vp = this.verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_SYNC_DIZIONARIO_USA_STP_CODICE);
	if (vp != null) {
	    vp.setValore(WebConstants.VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO);
	    this.verticalizzazioniParametriService.update(vp);
	} else {
	    throw new RuntimeException(
		    "E' impossibile completare l'importazione del dizionario perché manca il parametro della verticalizzazione CART.SYNC_DIZIONARIO_USA_STP_CODICE.");
	}
	dizioHelper.setTrustStpCodice(trust);
    }

    @Override
    public void insertInterventiDaCart(List<Integer> codiciStpTipologiaEndo2) {

	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart# Inizio  Inserimento Interventi dal cart........  ");
	}
	// Recupero le stp tipologie endo 2 dai codice passati dal dialog 
	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart# Recupero le stp tipologie tipologie endo 2...");
	}
	List<StpTipologieEndo2> stpTipologieEndo2s = new ArrayList<StpTipologieEndo2>();
	for (Integer codice : codiciStpTipologiaEndo2) {
	    if (log.isDebugEnabled()) {
		log.debug("insertInterventiDaCart# Stp Tipologia endo 2 con codice {}", codice);
	    }
	    StpTipologieEndo2 stpTipologieEndo2 = stpTipologieEndo2Service.findById(new PkId(codice));
	    stpTipologieEndo2s.add(stpTipologieEndo2);
	}
	// Recupero la lista di stp endo  tipo 2
	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart# Ricerco la lista di stp endo tipo 2 filtrando per idcomune:{}, tipo:{}, codice_tipologia_endo{} ",
		    new Object[] { ORMHelper.getIdcomune(), "ENDO", 1 });
	}
	List<StpEndoTipo2> stpEndoTipo2s = stpEndoTipo2Service.findbyTipoAndCodiceTipologiaEndo(1, "ENDO");
	// Ciclo StpEndoTipo2 trovati
	for (StpEndoTipo2 stpEndoTipo2 : stpEndoTipo2s) {
	    for (StpTipologieEndo2 stpTipologieEndo2 : stpTipologieEndo2s) {
		if (log.isDebugEnabled()) {
		    log.debug(
			    "insertInterventiDaCart# Creo ed inserisco un nuovo oggetto AlberoProc per stpTipologieEndo2 (descrizione:{}, codice:{}) e stpEndoTipo2(codice:{})..... ",
			    new Object[] { stpTipologieEndo2.getDescrizione(), stpTipologieEndo2.getId().getCodice(),
				    stpEndoTipo2.getId().getCodice() });
		}
		// Devo controllare che già non sia stato importato nell'albero proc di VBG voce dell'albero per la stp_tipologia_endo_2
		// in esame.
		// variabile di controllo per vedere se l'importazione è stata stata fatta
		if (log.isDebugEnabled()) {
		    log.debug(
			    "insertInterventiDaCart# Controllo che non sia già stato importato nell'alebero proc un intervento con le seguenti caratteristiche: codice_tipologia_endo = {}, tipo = {}, codice_endo_regionale = {}",
			    new Object[] { stpTipologieEndo2.getId().getCodice(), "ENDO", stpEndoTipo2.getCodiceEndoRegionale() });
		}
		StpEndoTipo2 stpEndoTipo2Check = stpEndoTipo2Service.findbyTipoAndCodiceTipologiaEndoAndCodiceRegionale(
			stpTipologieEndo2.getId().getCodice(), "ENDO", stpEndoTipo2.getCodiceEndoRegionale());
		if (stpEndoTipo2Check != null) {
		    if (log.isDebugEnabled()) {
			log.warn(
				"insertInterventiDaCart# E' gia presente un record in stpEndoTipo2 con le caratteristiche codice_tipologia_endo: {},tipo: {}, codice_endo_regionale: {} con codice {}",
				new Object[] { stpTipologieEndo2.getId().getCodice(), "ENDO", stpEndoTipo2.getCodiceEndoRegionale(),
					stpEndoTipo2Check.getId().getCodice() });
		    }
		} else {
		    if (log.isDebugEnabled()) {
			log.warn("insertInterventiDaCart# Record non trovato, continuo con l'inserimento");
		    }
		    if (stpEndoTipo2.getAlberoproc() != null) {
			// Il medodo crea un oggetto albero proc a partire dalle informazioni di stpTipologieEndo2 (sc_desc=descrione
			//sc_ordine=id) e di stpEndoTipo2 (Per logica vedi commento metodo)
			Alberoproc alberoproc = createAndInsertAberoProcDastpEndoTipo2(stpTipologieEndo2, stpEndoTipo2);
			// Tengo traccia di ogni nuovo inserimento di un record di albero proc all'interno della tabella stpEndoTipo2
			// (Per la logica vedi commento del metodo)
			StpEndoTipo2 stpendoTipo2Nuovo = createAndInsertStpEndoTipo2(alberoproc, stpEndoTipo2, stpTipologieEndo2);
		    }
		}
	    }
	}
	Software software = softwareService.findById(ORMHelper.getSoftware());
	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart#Inizio Inserimento Invetarioprocedimenti collegati alle voci dell'albero create.... ");
	}
	this.inserimentoEndo2ComeEndo1(software);
	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart# Fine  Inserimento Invetarioprocedimenti collegati alle voci dell'albero create...  ");
	}
	if (log.isDebugEnabled()) {
	    log.debug("insertInterventiDaCart# Fine  Inserimento Interventi dal cart........  ");
	}
    }

    /**
     * <pre>
     * Il medodo crea ed inserisce un oggetto albero proc a partire dalle informazioni di stpTipologieEndo2
     * (sc_desc=descrione sc_ordine=id) e di stpEndoTipo2 da cui recupera sc_codice del padre della nuova foglia da
     * inserire. ES. se stpEndoTipo2.getAlberoproc().getId().getCodice() è assocoato a una voce dell'albero con
     * sc_codice =050401 l' sc_codice del padrea sarà sc_codice =0504. Da questo partiremo per calcolare l'sc_codice del
     * nuovo figlio
     * 
     * 
     * &#64;param stpTipologieEndo2
     * &#64;param stpEndoTipo2
     * &#64;return Alberoproc
     * 
     * </pre>
     */
    private Alberoproc createAndInsertAberoProcDastpEndoTipo2(StpTipologieEndo2 stpTipologieEndo2, StpEndoTipo2 stpEndoTipo2) {

	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Inizio creazione ed inserimento albero proc.....");
	}
	// Popolo in campo sc_descrizione e sc_ordine
	Alberoproc alberoproc = new Alberoproc();
	alberoproc.setScDescrizione(stpTipologieEndo2.getDescrizione());
	alberoproc.setScOrdine(stpTipologieEndo2.getId().getCodice());
	if (stpTipologieEndo2.getAzioni() != null) {
	    if (stpTipologieEndo2.getAzioni().getAzId() != null) {
		alberoproc.setAzione(stpTipologieEndo2.getAzioni());
	    }
	}
	// Recupero il padre a cui collegheremo la nuova foglia.Recupero il padre da stpEndoTipo2 che nell'albero di
	//VBG rappresenta una foglia (procedimento di AVVIO)
	if (log.isDebugEnabled()) {
	    log.debug(
		    "createAndInsertAberoProcDastpEndoTipo2# Recupero la foglia Avvio salvata su Alberoproc (codice:{}) e corrispondente al record di stpEndoTipo2 (codice:{})",
		    new Object[] { stpEndoTipo2.getAlberoproc().getId().getCodice(), stpEndoTipo2.getId().getCodice() });
	}
	Alberoproc alberoprocFogliaAvvio = alberoprocService.findById(new PkId(stpEndoTipo2.getAlberoproc().getId().getCodice()));
	// Recupero sc_codice del padre facendo il substring del sc_codice della foglia (Avvio)
	String scCodiceFogliaAvvio = alberoprocFogliaAvvio.getScCodice();
	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Calcolo sc_codice padre a a partire dal sc_codice figlia {}", scCodiceFogliaAvvio);
	}
	String scCodicePadre = scCodiceFogliaAvvio.substring(0, scCodiceFogliaAvvio.length() - 2);
	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Recupero alberoproc padre a con sc_codice {}", scCodicePadre);
	}
	Alberoproc alberoprocPadre = alberoprocService.findByScCodice(scCodicePadre);
	alberoprocService.insertAlberoproc(alberoproc, alberoprocPadre);
	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Fine creazione ed inserimento albero proc (codice{}).....",
		    alberoproc.getId().getCodice());
	}
	return alberoproc;
    }

    /**
     * <pre>
     * Il metodo inserisce un nuovo record nella tabella StpEndoTipo2 a partire da un oggetto Alberoproc seguendo il mapping:
     * <ol>
     * 	<li>Idcomune = ORMHelper.idcomune</li>
     * 	<li>Id : nuovo progressivo</li>
     * 	<li>Fk_sc_id : alberoproc.getIt.getCodice</li>
     * 	<li>Codice_stp= null</li>
     * 	<li>Tipo=”Endo”</li>
     * 	<li>codice_tipologia_endo= 1</li>
     * 	<li>codiceinventario:codice dell’inventario procedimento associato ad alberoproc al momento della sua creazione </li>	
     *  <li> Gli altri non menzionati devono mantenere gli stessi valori presenti in stpEndoTipo2 passato.</li>
     * </ol> 
     * 
     * &#64;param alberoproc
     * &#64;return StpEndoTipo2
     * </pre>
     */
    private StpEndoTipo2 createAndInsertStpEndoTipo2(Alberoproc alberoproc, StpEndoTipo2 stpEndoTipo2, StpTipologieEndo2 stpTipologieEndo2) {

	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Inizio creazione ed inserimento stpEndoTipo2.....");
	}
	StpEndoTipo2 stpEndoTipoNew = new StpEndoTipo2();
	// Parte fissa
	stpEndoTipoNew.setCodiceStp(null);
	stpEndoTipoNew.setTipo("ENDO");
	// Parte dipendente da albero proc
	stpEndoTipoNew.setAlberoproc(alberoproc);
	//stpEndoTipoNew.setInventarioprocedimenti(alberoproc.get)
	//Parte dipendente da StpTipologieEndo2
	stpEndoTipoNew.setStpTipologieEndo2(stpTipologieEndo2);
	//Parte dipendente da StpEndoTipo2
	stpEndoTipoNew.setCodiceEndoRegionale(StringUtils.defaultString(stpEndoTipo2.getCodiceEndoRegionale()).trim());
	// Gli oggetti per ora non verranno passati (Gianpaolo Todini/Riccardo Bocci)
	//	if (stpEndoTipo2.getOggetti() != null) {
	//	    stpEndoTipoNew.setOggetti(stpEndoTipo2.getOggetti());
	//	}
	stpEndoTipo2Service.insert(stpEndoTipoNew);
	if (log.isDebugEnabled()) {
	    log.debug("createAndInsertAberoProcDastpEndoTipo2# Fine creazione ed inserimento stpEndoTipo2 (codice {}).....",
		    stpEndoTipo2.getId().getCodice());
	}
	return stpEndoTipoNew;
    }

    @Override
    public void bonificaGerarchiaAlbero() {

	this.bonificaGerarchiaAlbero(null);
    }

    private Set<String> populateCodiciEndoRegionali(CartAlberoprocHelper helper) {

	String codiceEndoRegionale = helper.getCodiceEndoRegionale();
	Set<String> result = new TreeSet<String>();
	if (StringUtils.isNotBlank(codiceEndoRegionale)) {
	    result.add(codiceEndoRegionale);
	}
	if (helper.getChilds() != null) {
	    for (CartAlberoprocHelper h : helper.getChilds()) {
		Set<String> recurse = populateCodiciEndoRegionali(h);
		result.addAll(recurse);
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("populateCodiciEndoRegionali# result: {}", result);
	}
	return result;
    }

    private void bonificaGerarchiaAlbero(CartAlberoprocHelper helper) {

	this.flushAndClearSession();
	Set<String> codiciEndoDizionario = new TreeSet<String>();
	if (helper != null) {
	    codiciEndoDizionario = populateCodiciEndoRegionali(helper);
	}
	Map<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> m = stpEndoTipo2Service.findListaAttivitaCartOrdinate();
	for (Entry<String, ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>>> att : m.entrySet()) {
	    ChiaveValoreBean<String, List<ChiaveValoreBean<Integer, String>>> v = att.getValue();
	    String codiceEndoRegionale = att.getKey();
	    if (!codiciEndoDizionario.isEmpty() && !codiciEndoDizionario.contains(codiceEndoRegionale)) {
		log.debug("bonificaGerarchiaAlbero# il codice endo regionale '{}' non è presente nel dizionario lo disabilito", codiceEndoRegionale);
		disabilitaAttivita(codiceEndoRegionale);
	    }
	    String scCodicePadre = v.getChiave();
	    List<ChiaveValoreBean<Integer, String>> e = v.getValore();
	    if (log.isDebugEnabled()) {
		log.debug("bonificaGerarchiaAlbero#  PROCESSO IL CODICE ENDO REGIONALE: {}, {}", new Object[] { att.getKey(), scCodicePadre });
	    }
	    if (null != e) {
		for (ChiaveValoreBean<Integer, String> endo : e) {
		    String scCodice = endo.getValore();
		    Integer codiceAlberoProc = endo.getChiave();
		    log.debug("CVB: ScCodice: {}, codiceAlberoProc: {}", new Object[] { scCodice, codiceAlberoProc });
		    if (scCodice != null && codiceAlberoProc != null) {
			if (scCodicePadre != null) {
			    if (!(scCodice.startsWith(scCodicePadre) && (scCodice.length() == (scCodicePadre.length() + 2)))) {
				String elementoTerminale = StringUtils.right(scCodice, 2);
				scCodice = scCodicePadre + elementoTerminale;
				Alberoproc ap = alberoprocService.findByScCodice(scCodice);
				if (ap == null) {
				    alberoprocService.updateScCodice(codiceAlberoProc, scCodice);
				} else {
				    log.warn("Voce dell'abero già esistente con codice {}", scCodice);
				    int i = 0;
				    boolean assegnato = false;
				    while (i < 20) {
					scCodice = alberoprocService.calcolaProssimoCodice(scCodice);
					log.warn("tento di assegnare il nuovo codice {}, iterazione: {}", scCodice, i);
					ap = alberoprocService.findByScCodice(scCodice);
					if (ap == null) {
					    log.warn("Assegno il nuovo codice {}, iterazione: {}", scCodice, i);
					    assegnato = true;
					    alberoprocService.updateScCodice(codiceAlberoProc, scCodice);
					    i = 20;
					}
					i++;
				    }
				    if (!assegnato) {
					log.error("non è stato possibile assegnare il codice alla voce d'albero {}", codiceAlberoProc);
					FlashMessages.getWarnings()
						.add("non è stato possibile assegnare il codice alla voce d'albero " + codiceAlberoProc);
				    }
				}
			    }
			    this.flushAndClearSession();
			} else {
			    // probabilmente è stato disabilitato dalla regione e lo disabilito dall'albero dei procedimenti
			    log.warn(
				    "bonificaGerarchiaAlbero# scCodicePadre è nullo disattivo la voce dell'albero sc_id: {}, sc_codice '{}', codice_endo_regionale: '{}'",
				    new Object[] { codiceAlberoProc, scCodice, att.getKey() });
			    Alberoproc ap = alberoprocService.findById(new PkId(codiceAlberoProc));
			    if (ap != null) {
				ap.setScAttivo(Boolean.FALSE);
			    }
			    alberoprocService.update(ap);
			    this.flushAndClearSession();
			    // trovo l'attivita e dall'attivita salto all' alberoproc PADRE da disabilitare
			    List<StpEndoTipo2> stp2s = stpEndoTipo2Service.findbyTipoAndCodiceEndoRegionale(att.getKey(),
				    StpEndoTipo2Service.TIPO_ATTIVITA);
			    for (StpEndoTipo2 stpEndoTipo2 : stp2s) {
				Alberoproc attivita = stpEndoTipo2.getAlberoproc();
				if (attivita != null) {
				    if (!BooleanUtils.isTrue(attivita.getScAttivo())) {
					attivita.setScAttivo(Boolean.FALSE);
					alberoprocService.update(attivita);
				    }
				}
			    }
			    this.flushAndClearSession();
			}
		    }
		}
	    }
	}
	rimuoviAttivitaECategorie(helper);
	this.flushAndClearSession();
    }

    private void rimuoviAttivitaECategorie(CartAlberoprocHelper helper) {

	if (helper != null) {
	    this.flushAndClearSession();
	    // metto in una mappa le categorie e le attività
	    Set<String> map = new HashSet<String>();
	    populateMap(map, helper);
	    // ciclo la tabella stp_endo_tipo 2 con categoria e attivita
	    List<Integer> stp2 = stpEndoTipo2Service.findListCategorieAndAttivita();
	    String key = null;
	    // 	se trovo un record su stp e non su dizionario allora disabilito la voce
	    for (Integer stpEndoTipo2Id : stp2) {
		//	se trovo un record su stp e non su dizionario allora disabilito la voce
		StpEndoTipo2 stpEndoTipo2 = stpEndoTipo2Service.findById(new PkId(stpEndoTipo2Id));
		key = getKey(stpEndoTipo2.getTipo(), stpEndoTipo2.getCodiceEndoRegionale());
		if (!map.contains(key)) {
		    if (stpEndoTipo2.getTipo().equalsIgnoreCase(StpEndoTipo2Service.TIPO_ATTIVITA)) {
			disabilitaAttivita(stpEndoTipo2.getCodiceEndoRegionale());
		    } else if (stpEndoTipo2.getTipo().equalsIgnoreCase(StpEndoTipo2Service.TIPO_CATEGORIA)) {
			disabilitaCategoria(stpEndoTipo2.getCodiceEndoRegionale());
		    }
		}
	    }
	}
    }

    private String getKey(String tipo, String codiceRegionale) {

	return tipo + "_" + codiceRegionale;
    }

    private void populateMap(Set<String> map, CartAlberoprocHelper helper) {

	if (!helper.isRoot()) {
	    if (helper.getTipologiaEndo().equalsIgnoreCase(StpEndoTipo2Service.TIPO_CATEGORIA)
		    || helper.getTipologiaEndo().equalsIgnoreCase(StpEndoTipo2Service.TIPO_ATTIVITA)) {
		String key = getKey(helper.getTipologiaEndo(), helper.getCodiceEndoRegionale());
		map.add(key);
	    }
	}
	if (helper.getChilds() != null) {
	    List<CartAlberoprocHelper> hlps = helper.getChilds();
	    for (CartAlberoprocHelper cah : hlps) {
		populateMap(map, cah);
	    }
	}
    }

    private void disabilitaAttivita(String codiceEndoRegionale) {

	List<StpEndoTipo2> stp2s = stpEndoTipo2Service.findbyTipoAndCodiceEndoRegionale(codiceEndoRegionale, StpEndoTipo2Service.TIPO_ATTIVITA);
	for (StpEndoTipo2 stpEndoTipo2 : stp2s) {
	    Alberoproc attivita = stpEndoTipo2.getAlberoproc();
	    if (attivita != null) {
		if (log.isDebugEnabled()) {
		    log.debug("disabilitaAttivita# ATTIVITA TROVATA {}", codiceEndoRegionale);
		}
		attivita.setScAttivo(Boolean.TRUE);
		alberoprocService.update(attivita);
	    }
	    // AZZERO I RIFERIMENTI A CODICE_STP
	    stpEndoTipo2.setCodiceStp(null);
	    stpEndoTipo2Service.update(stpEndoTipo2);
	}
	List<StpEndoTipo2> endos = stpEndoTipo2Service.findbyTipoAndCodiceEndoRegionale(codiceEndoRegionale, StpEndoTipo2Service.TIPO_ENDO);
	for (StpEndoTipo2 stpEndoTipo2 : endos) {
	    Alberoproc endo = stpEndoTipo2.getAlberoproc();
	    if (endo != null) {
		if (log.isDebugEnabled()) {
		    log.debug("disabilitaAttivita# ENDO TROVATO {}", codiceEndoRegionale);
		}
		endo.setScAttivo(Boolean.TRUE);
		alberoprocService.update(endo);
		// AZZERO I RIFERIMENTI A CODICE_STP
		stpEndoTipo2.setCodiceStp(null);
		stpEndoTipo2Service.update(stpEndoTipo2);
	    }
	}
	this.flushAndClearSession();
    }

    private void disabilitaCategoria(String codiceEndoRegionale) {

	List<StpEndoTipo2> stp2s = stpEndoTipo2Service.findbyTipoAndCodiceEndoRegionale(codiceEndoRegionale, StpEndoTipo2Service.TIPO_CATEGORIA);
	for (StpEndoTipo2 stpEndoTipo2 : stp2s) {
	    Alberoproc categoria = stpEndoTipo2.getAlberoproc();
	    if (categoria != null) {
		if (log.isDebugEnabled()) {
		    log.debug("disabilitaAttivita# categoria TROVATA");
		}
		categoria.setScAttivo(Boolean.TRUE);
		alberoprocService.update(categoria);
	    }
	    // AZZERO I RIFERIMENTI A CODICE_STP
	    stpEndoTipo2.setCodiceStp(null);
	    stpEndoTipo2Service.update(stpEndoTipo2);
	}
	this.flushAndClearSession();
    }
}
