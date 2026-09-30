package it.gruppoinit.pal.gp.core.service.impl;

import it.eng.suap.xengine.model.modulistica.ModuloType;
import it.eng.suap.xengine.model.service.xcommon.ModulisticaContentType;
import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.dao.FoDomandeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti;
import it.gruppoinit.pal.gp.core.domain.FoMessaggi;
import it.gruppoinit.pal.gp.core.domain.FoSottoscrizioni;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.ModulisticaSTAR;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DomandeFrontOfficeService;
import it.gruppoinit.pal.gp.core.service.FoDomandeOggettiService;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.FoMessaggiService;
import it.gruppoinit.pal.gp.core.service.FoSottoscrizioniService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.helper.FiltriRicercafoDomande;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class FoDomandeServiceImpl extends BaseServiceImpl<FoDomande, PkId> implements FoDomandeService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(FoDomandeServiceImpl.class);
    private FoDomandeDAO fodomandeDAO;
    private FoMessaggiService foMessaggiService;
    private FoSottoscrizioniService foSottoscrizioniService;
    private FoDomandeOggettiService foDomandeOggettiService;
    private OggettiService oggettiService;
    private DomandeFrontOfficeService domandeFrontOfficeService;

    @Autowired
    public void setDomandeFrontOfficeService(DomandeFrontOfficeService domandeFrontOfficeService) {

	this.domandeFrontOfficeService = domandeFrontOfficeService;
    }

    @Autowired
    public void setFoDomandeDAO(FoDomandeDAO fodomandeDAO) {

	this.fodomandeDAO = fodomandeDAO;
    }

    @Autowired
    public void setFoMessaggiService(FoMessaggiService foMessaggiService) {

	this.foMessaggiService = foMessaggiService;
    }

    @Autowired
    public void setFoSottoscrizioniService(FoSottoscrizioniService foSottoscrizioniService) {

	this.foSottoscrizioniService = foSottoscrizioniService;
    }

    @Autowired
    public void setFoDomandeOggettiService(FoDomandeOggettiService foDomandeOggettiService) {

	this.foDomandeOggettiService = foDomandeOggettiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<FoDomande> getEntityClass() {

	return FoDomande.class;
    }

    @Override
    public List<FoDomande> findAll(Integer firstResult, Integer maxResult) {

	return fodomandeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoDomande entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    fodomandeDAO.insert(entity);
	}
    }

    @Override
    public FoDomande findById(PkId id) {

	return fodomandeDAO.findById(id);
    }

    @Override
    public void update(FoDomande entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    fodomandeDAO.update(entity);
	}
    }

    @Override
    public void delete(FoDomande entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    fodomandeDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(FoDomande entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoDomande> findByIdentificativodomanda(String identificativodomanda) {

	if (identificativodomanda == null) {
	    throw new IllegalArgumentException("findByIdentificativodomanda: il parametro identificativodomanda e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("identificativodomanda", identificativodomanda, String.class));
	filterTable.addRestriction(fr);
	return fodomandeDAO.findByFilterTable(filterTable);
    }

    @Override
    public FoDomande findByIdentificativoCart(String idCart) {

	if (idCart == null) {
	    throw new IllegalArgumentException("findByIdentificativoCart: il parametro identificativo CART e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("identificativoCart", idCart, String.class));
	filterTable.addRestriction(fr);
	List<FoDomande> results = fodomandeDAO.findByFilterTable(filterTable);
	if (results.isEmpty()) {
	    return null;
	} else {
	    return results.get(0);
	}
    }

    @Override
    protected void childDelete(FoDomande entity) {

	Set<FoMessaggi> foMessaggis = entity.getFoMessaggis();
	for (FoMessaggi foMessaggi : foMessaggis) {
	    foMessaggiService.delete(foMessaggi);
	}
	Set<FoSottoscrizioni> foSottoscrizionis = entity.getFoSottoscrizionis();
	for (FoSottoscrizioni foSottoscrizioni : foSottoscrizionis) {
	    foSottoscrizioniService.delete(foSottoscrizioni);
	}
	Set<FoDomandeOggetti> foDomandeOggettis = entity.getFoDomandeOggettis();
	for (FoDomandeOggetti foDomandeOggetti : foDomandeOggettis) {
	    foDomandeOggettiService.delete(foDomandeOggetti);
	}
    }

    @Override
    public List<FoDomande> findInCompilazioneByAnagrafe(String cfUtenteLoggato, Integer firstResult, Integer maxResult) {

	if (StringUtils.isBlank(cfUtenteLoggato)) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro cfUtenteLoggato e' nullo");
	}
	FilterTable filterTable = byAnagrafeAndSdeProxy(cfUtenteLoggato, false, false);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.isNull("flgPresentata"));
	fr.addFilterField(FilterUtils.equals("flgPresentata", Boolean.FALSE, Boolean.class));
	filterTable.addRestriction(fr);
	FilterRestriction eliminate = escludiLeEliminate();
	filterTable.addRestriction(eliminate);
	return fodomandeDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    private FilterTable byAnagrafeAndSdeProxy(String cfUtenteLoggato, boolean isCount, boolean checkSoloPresentatore) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	FilterRestriction a = new FilterRestriction();
	if (!checkSoloPresentatore) {
	    // DISCRIMINA LE DOMANDE SOLO PER CF PRESENTATORE MODIFICHE SCRIVANIA VIRTUALE
	    a.setAndOrRestriction(AndOrRestriction.OR);
	    a.addFilterField(FilterUtils.equals("richCodicefiscale", cfUtenteLoggato, String.class));
	}
	a.addFilterField(FilterUtils.equals("presentatoreCodfiscale", cfUtenteLoggato, String.class));
	filterTable.addRestriction(a);
	fr.addFilterField(FilterUtils.equals("idente", ORMHelper.getIdente(), "sdeproxy", String.class));
	filterTable.addRestriction(fr);
	if (!isCount) {
	    filterTable.addOrder(FilterUtils.orderDesc("dataUltimaModifica"));
	}
	return filterTable;
    }

    @Override
    public int countInCompilazioneByUtenteLoggato(String cfUtenteLoggato) {

	if (StringUtils.isBlank(cfUtenteLoggato)) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro cfUtenteLoggato e' nullo");
	}
	FilterTable filterTable = byAnagrafeAndSdeProxy(cfUtenteLoggato, true, false);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.isNull("flgPresentata"));
	fr.addFilterField(FilterUtils.equals("flgPresentata", Boolean.FALSE, Boolean.class));
	filterTable.addRestriction(fr);
	FilterRestriction eliminate = escludiLeEliminate();
	filterTable.addRestriction(eliminate);
	return fodomandeDAO.countRecord(filterTable);
    }

    @Override
    public List<FoDomande> findByFiltriRicerca(FiltriRicercafoDomande filtriRicercafoDomande, Integer firstResult, Integer maxResults) {

	FilterTable filterTable = getByFiltriRicerca(filtriRicercafoDomande, false);
	FilterRestriction fr = escludiLeEliminate();
	filterTable.addRestriction(fr);
	return fodomandeDAO.findByFilterTable(filterTable, firstResult, maxResults);
    }

    @Override
    public int countByFiltriRicerca(FiltriRicercafoDomande filtriRicercafoDomande) {

	FilterTable filterTable = getByFiltriRicerca(filtriRicercafoDomande, true);
	FilterRestriction fr = escludiLeEliminate();
	filterTable.addRestriction(fr);
	return fodomandeDAO.countRecord(filterTable);
    }

    private FilterTable getByFiltriRicerca(FiltriRicercafoDomande filtriRicercafoDomande, boolean isCount) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("software.codice", ORMHelper.getSoftware(), String.class));
	fr.addFilterField(FilterUtils.equals("idente", ORMHelper.getIdente(), "sdeproxy", String.class));
	// MODIFICHE SCRIVANIA VIRTUALE
	if (filtriRicercafoDomande.getAnagrafe() != null) {
	    String codicefiscale = filtriRicercafoDomande.getAnagrafe().getCodicefiscale();
	    if (StringUtils.isNotBlank(codicefiscale)) {
		FilterRestriction utenteConnesso = new FilterRestriction();
		utenteConnesso.setAndOrRestriction(AndOrRestriction.OR);
		utenteConnesso.addFilterField(FilterUtils.equalsIgnoreCase("richCodicefiscale", codicefiscale.trim()));
		utenteConnesso.addFilterField(FilterUtils.equalsIgnoreCase("presentatoreCodfiscale", codicefiscale.trim()));
		filterTable.addRestriction(utenteConnesso);
	    } else {
		throw new RuntimeException("Anagrafe Obbligatoria. Codice fiscale non presente");
	    }
	} else {
	    throw new RuntimeException("Anagrafe Obbligatoria");
	}
	//	if (filtriRicercafoDomande.getAnagrafe() != null) {
	//	    if (StringUtils.isNotBlank(filtriRicercafoDomande.getAnagrafe().getCodicefiscale())) {
	//		fr.addFilterField(FilterUtils.equalsIgnoreCase("codicefiscale", filtriRicercafoDomande.getAnagrafe().getCodicefiscale(), "anagrafe"));
	//	    } else {
	//		throw new RuntimeException("Anagrafe Obbligatoria. Codice fiscale non presente");
	//	    }
	//	} else {
	//	    throw new RuntimeException("Anagrafe Obbligatoria");
	//	}
	if (StringUtils.isNotBlank(filtriRicercafoDomande.getTitolarePratica())) {
	    FilterRestriction titolare = new FilterRestriction();
	    titolare.setAndOrRestriction(AndOrRestriction.OR);
	    titolare.addFilterField(FilterUtils.like("richNome", filtriRicercafoDomande.getTitolarePratica()));
	    titolare.addFilterField(FilterUtils.like("richCognome", filtriRicercafoDomande.getTitolarePratica()));
	    titolare.addFilterField(FilterUtils.like("richCodicefiscale", filtriRicercafoDomande.getTitolarePratica()));
	    filterTable.addRestriction(titolare);
	}
	filterTable.addRestriction(fr);
	if (filtriRicercafoDomande.getPresentata() != null) {
	    FilterRestriction presentata = new FilterRestriction();
	    if (filtriRicercafoDomande.getPresentata().booleanValue()) {
		presentata.addFilterField(FilterUtils.equals("flgPresentata", Boolean.TRUE, Boolean.class));
	    } else {
		presentata.setAndOrRestriction(AndOrRestriction.OR);
		presentata.addFilterField(FilterUtils.isNull("flgPresentata"));
		presentata.addFilterField(FilterUtils.equals("flgPresentata", Boolean.FALSE, Boolean.class));
	    }
	    filterTable.addRestriction(presentata);
	}
	if (StringUtils.isNotBlank(filtriRicercafoDomande.getIdentificativoDomanda())) {
	    FilterRestriction identificativo = new FilterRestriction();
	    identificativo.addFilterField(FilterUtils.equalsIgnoreCase("identificativodomanda", filtriRicercafoDomande.getIdentificativoDomanda()));
	    filterTable.addRestriction(identificativo);
	}
	if (BooleanUtils.isTrue(filtriRicercafoDomande.getEscludiDomandeComunica())) {
	    FilterRestriction comunica = new FilterRestriction();
	    comunica.setAndOrRestriction(AndOrRestriction.OR);
	    comunica.addFilterField(FilterUtils.equals("flagComunica", Boolean.FALSE, Boolean.class));
	    comunica.addFilterField(FilterUtils.isNull("flagComunica"));
	    filterTable.addRestriction(comunica);
	}
	boolean addData = false;
	FilterRestriction dataF = new FilterRestriction();
	if (filtriRicercafoDomande.getDataInvioDa() != null && filtriRicercafoDomande.getDataInvioA() != null) {
	    addData = true;
	    dataF.addFilterField(FilterUtils.between("datainvio", filtriRicercafoDomande.getDataInvioDa(), filtriRicercafoDomande.getDataInvioA(),
		    java.util.Date.class));
	} else {
	    if (filtriRicercafoDomande.getDataInvioDa() != null) {
		dataF.addFilterField(FilterUtils.greaterEqual("datainvio", filtriRicercafoDomande.getDataInvioDa(), Date.class));
		addData = true;
	    }
	    if (filtriRicercafoDomande.getDataInvioA() != null) {
		dataF.addFilterField(FilterUtils.smallerEqual("datainvio", filtriRicercafoDomande.getDataInvioA(), Date.class));
		addData = true;
	    }
	}
	if (addData) {
	    filterTable.addRestriction(dataF);
	}
	if (!isCount) {
	    filterTable.addOrder(FilterUtils.orderDesc("datainvio"));
	}
	return filterTable;
    }

    @Override
    public void checkDomandaDellUtente(String cfUtenteLoggato, Integer fodomandaid, boolean checkSoloPresentatore) throws SecurityException {

	FilterTable filterTable = byAnagrafeAndSdeProxy(cfUtenteLoggato, true, checkSoloPresentatore);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", fodomandaid, Integer.class));
	filterTable.addRestriction(fr);
	if (fodomandeDAO.findByFilterTable(filterTable, 0, 2).isEmpty()) {
	    logger.error("L'utente con codice anagrafe {} ha tentato di accedere alla domanda {}", cfUtenteLoggato, fodomandaid);
	    throw new SecurityException("Operazione non consentita");
	}
    }

    private FilterRestriction escludiLeEliminate() {

	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("flgEliminata", Boolean.FALSE, Integer.class));
	fr.addFilterField(FilterUtils.isNull("flgEliminata"));
	return fr;
    }

    @Override
    public void updateSegnaDomandaCancellata(Integer fodomandaid) {

	FoDomande dom = this.findById(new PkId(fodomandaid));
	dom.setFlgEliminata(Boolean.TRUE);
	this.update(dom);
    }

    @Override
    public PkId newIdFromSequencetable(FoDomande entity) {

	return fodomandeDAO.newIdFromSequence(entity);
    }

    @Override
    public List<CodiceDescrizioneBean> getListaModuli(String idcomune, Integer codice) {

	List<CodiceDescrizioneBean> ret = new ArrayList<CodiceDescrizioneBean>();
	FoDomande fod = this.findById(new PkId(idcomune, codice));
	if (fod != null) {
	    try {
		DatiDomandaCart s = domandeFrontOfficeService.getDatiDomandaCart(codice, false);
		if (s != null) {
		    ModulisticaContentType d = s.getModulistica();
		    ret = new ArrayList<CodiceDescrizioneBean>();
		    if (d != null && d.getModulistica().getModulo() != null && d.getModulistica().getModulo().size() > 0) {
			List<ModuloType> ms = d.getModulistica().getModulo();
			for (ModuloType modulo : ms) {
			    String riferimento = modulo.getRiferimento().getCodiceEndoProcedimento();
			    if (StringUtils.isBlank(riferimento)) {
				riferimento = modulo.getRiferimento().getCodiceModello();
			    }
			    CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
			    cdb.setCodice(riferimento);
			    String destinatario = "";
			    if (modulo.getDestinatariET() != null && modulo.getDestinatariET().size() > 0) {
				destinatario = modulo.getDestinatariET().get(0).getTipo();
			    }
			    cdb.setDescrizione(destinatario);
			    ret.add(cdb);
			}
		    }
		    if (s.getModulisticaSTAR() != null) {
			ModulisticaSTAR mstar = s.getModulisticaSTAR();
			if (mstar.getModuli() != null && !mstar.getModuli().isEmpty()) {
			    for (Entry<String, ModuloType> m : mstar.getModuli().entrySet()) {
				ModuloType mt = m.getValue();
				String riferimento = mt.getRiferimento().getCodiceEndoProcedimento();
				if (StringUtils.isBlank(riferimento)) {
				    riferimento = mt.getRiferimento().getCodiceModello();
				}
				CodiceDescrizioneBean cdb = new CodiceDescrizioneBean();
				cdb.setCodice(riferimento);
				String destinatario = "";
				if (mt.getDestinatariET() != null && mt.getDestinatariET().size() > 0) {
				    destinatario = mt.getDestinatariET().get(0).getTipo();
				}
				cdb.setDescrizione(destinatario);
				ret.add(cdb);
			    }
			}
		    }
		}
	    } catch (Exception err) {
		logger.error("{}", err);
	    }
	}
	if (ret.isEmpty()) {
	    CodiceDescrizioneBean e = new CodiceDescrizioneBean();
	    e.setCodice(FACCTConstants.STANDARD_0);
	    e.setDescrizione("");
	    ret.add(e);
	}
	return ret;
    }

    private void dataIntegration(FoDomande entity, boolean isInsert) {

	if (entity == null) {
	    throw new RuntimeException("Entity non può essere nulla");
	}
	//	if (entity.getFlagComunica() == null) {
	//	    entity.setFlagComunica(Boolean.FALSE);
	//	}
	//	if (isInsert) {
	//	    if (entity.getAnagrafe() != null) {
	//		if (StringUtils.isBlank(entity.getPresentatoreCodfiscale())) {
	//		    entity.setPresentatoreCodfiscale(entity.getAnagrafe().getCodicefiscale());
	//		}
	//		if (StringUtils.isBlank(entity.getPresentatoreCognome())) {
	//		    entity.setPresentatoreCodfiscale(entity.getAnagrafe().getNominativo());
	//		}
	//		if (StringUtils.isBlank(entity.getPresentatoreNome())) {
	//		    entity.setPresentatoreCodfiscale(entity.getAnagrafe().getNome());
	//		}
	//	    }
	//	}
    }

    @Override
    public boolean checkPermessoTitolare(String idcomune, Integer codice, String cfUtenteLoggato) {

	FoDomande dom = this.findById(new PkId(idcomune, codice));
	if (dom == null) {
	    return false;
	}
	if (StringUtils.isBlank(cfUtenteLoggato)) {
	    return false;
	}
	//	if (cfUtenteLoggato.trim().equalsIgnoreCase(StringUtils.defaultString(dom.getRichCodicefiscale()).trim())) {
	//	    return true;
	//	}
	return false;
    }
}
