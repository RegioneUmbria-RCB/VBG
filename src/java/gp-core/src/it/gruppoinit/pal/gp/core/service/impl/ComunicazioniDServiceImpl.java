package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PassoCreazioneComunicazioneEnum;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.TmpStatiComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ComunicazioniDServiceImpl extends BaseServiceImpl<ComunicazioniD, PkId> implements ComunicazioniDService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniDServiceImpl.class);
    private ComunicazioniDDAO comunicazioniDDAO;
    private MovimentiService movimentiService;
    private MovimentimailService movimentimailService;
    private OggettiService oggettiService;
    private TmpStatiComunicazioniDService tmpStatiComunicazioniDService;

    @Autowired
    public void setTmpStatiComunicazioniDService(TmpStatiComunicazioniDService tmpStatiComunicazioniDService) {

	this.tmpStatiComunicazioniDService = tmpStatiComunicazioniDService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setComunicazioniDDAO(ComunicazioniDDAO comunicazioniDDAO) {

	this.comunicazioniDDAO = comunicazioniDDAO;
    }

    @Override
    protected Class<ComunicazioniD> getEntityClass() {

	return ComunicazioniD.class;
    }

    @Override
    public List<ComunicazioniD> findAll(Integer firstResult, Integer maxResult) {

	return comunicazioniDDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ComunicazioniD entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    comunicazioniDDAO.insert(entity);
	}
    }

    @Override
    public ComunicazioniD findById(PkId id) {

	return comunicazioniDDAO.findById(id);
    }

    @Override
    public void update(ComunicazioniD entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    comunicazioniDDAO.update(entity);
	}
    }

    @Override
    public void delete(ComunicazioniD entity) {

	if (isDeleteAllowed(entity)) {
	    comunicazioniDDAO.delete(entity);
	}
    }

    @Override
    public List<ComunicazioniD> findByComunicazioniT(Integer codiceComunicazioneT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceComunicazioneT, "comunicazioniT", Integer.class));
	ft.addRestriction(fr);
	return comunicazioniDDAO.findByFilterTable(ft);
    }

    @Override
    public Integer countComunicazioniT(Integer codiceComunicazioneT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceComunicazioneT, "comunicazioniT", Integer.class));
	ft.addRestriction(fr);
	return comunicazioniDDAO.countRecord(ft);
    }

    //    @Override
    //    public List<ComunicazioniDDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult) {
    //
    //	List<ComunicazioniDDTO> list = comunicazioniDDAO.findByComunicazioniT(codiceComunicazione, firstResult, maxResult);
    //	return list;
    //    }
    //    @Override
    //    public List<ComunicazioniDDTO> findByComunicazioniTWithEvent(Integer codice, Integer firstResult, Integer maxResult) {
    //
    //	List<ComunicazioniDDTO> list = this.findByComunicazioniT(codice, firstResult, maxResult);
    //	for (ComunicazioniDDTO comunicazioniDDTO : list) {
    //	    //	    // Controllo se c'è il movimento, se significa se ho un evento è sul movimento, altrimenti è sull'istanza
    //	    //	    List<Istanzeeventi> istanzeeventis = new ArrayList<Istanzeeventi>();
    //	    //	    IstanzeeventiFilter istanzeeventiFilter = new IstanzeeventiFilter();
    //	    //	    //  Categorieeventibase categorieeventibase = categorieeventibaseService.findById(IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE);
    //	    //	    // istanzeeventiFilter.setCategorieeventibase(categorieeventibase);
    //	    //	    if (graduatoriedComDTO.getMovimenti() != null) {
    //	    //		Movimenti movimento = movimentiService.findById(new PkId(graduatoriedComDTO.getMovimenti()));
    //	    //		istanzeeventiFilter.setMovimenti(movimento);
    //	    //		istanzeeventiFilter.setIstanze(movimento.getIstanza());
    //	    //		istanzeeventis = istanzeeventiService.findByFilter(istanzeeventiFilter, 0, 1);
    //	    //		if (!istanzeeventis.isEmpty()) {
    //	    //		    graduatoriedComDTO.setIstanzeeventi(istanzeeventis.get(0));
    //	    //		}
    //	    //		Istanze istanza = istanzeService.findById(new PkId(graduatoriedComDTO.getGraduatoried().getIstanza().getId().getCodice()));
    //	    //		istanzeeventiFilter.setIstanze(istanza);
    //	    //		istanzeeventis = istanzeeventiService.findByFilter(istanzeeventiFilter, 0, 1);
    //	    //		if (!istanzeeventis.isEmpty()) {
    //	    //		    graduatoriedComDTO.setIstanzeeventi(istanzeeventis.get(0));
    //	    //		}
    //	    //	    }
    //	    //	    // Gestione controllo accettazione e consegna mail nel caso sia stata impostata una mail da inviare
    //	    //	    if (graduatoriedComDTO.getMovimentimail() != null) {
    //	    //		List<Movimentimail> listMailFigle = movimentimailService.findByMailPadre(graduatoriedComDTO.getMovimentimail());
    //	    //		for (Movimentimail movimentimail : listMailFigle) {
    //	    //		    if (movimentimail.getOggetto().contains("ACCETTAZIONE")) {
    //	    //			graduatoriedComDTO.setAccettata(true);
    //	    //		    }
    //	    //		    if (movimentimail.getOggetto().contains("CONSEGNA")) {
    //	    //			graduatoriedComDTO.setConsegnata(true);
    //	    //		    }
    //	    //		}
    //	    //	    }
    //	}
    //	return list;
    //    }
    @Override
    public void upadateStato(Integer codiceComD, ComunicazioniDStatoEnum stato) {

	comunicazioniDDAO.upadateStato(codiceComD, stato);
    }

    @Override
    public void updateComunicazioniDConErrore(Integer codiceComunicazioneD, PassoCreazioneComunicazioneEnum passoCreazioneComunicazioneEnum,
	    String errore) {

	tmpStatiComunicazioniDService.update(codiceComunicazioneD, passoCreazioneComunicazioneEnum, errore);
	log.debug("insertStepMovimento# inserito evento su TmpStatiComunicazioniD. comunicazioned = {}, passo = {}, idcomune = {}", new Object[] {
		codiceComunicazioneD, passoCreazioneComunicazioneEnum, ORMHelper.getIdcomune() });
	this.upadateStato(codiceComunicazioneD, ComunicazioniDStatoEnum.ELABORATA_CON_ERRORI);
	log.debug("insertStepMovimento#update stato comunicazioneD = {} , stato = {}", new Object[] { codiceComunicazioneD,
		ComunicazioniDStatoEnum.ELABORATA_CON_ERRORI.toString() });
    }

    private void dataIntegration(ComunicazioniD entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro MercatiComD da validare è nullo");
	}
	if (entity.getFlgProtocollato() == null) {
	    entity.setFlgProtocollato(false);
	}
	if (entity.getStatoElaborazione() == null) {
	    entity.setStatoElaborazione(0);
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(ComunicazioniD entity) {

	Movimenti movimenti = movimentiService.bindDomainObject(entity.getMovimenti(), PkId.class, "id.codice");
	entity.setMovimenti(movimenti);
	Movimentimail movimentimail = movimentimailService.bindDomainObject(entity.getMovimentimail(), PkId.class, "id.codice");
	entity.setMovimentimail(movimentimail);
	Oggetti oggetti = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetti);
    }

    protected boolean isDeleteAllowed(ComunicazioniD entity) {

	boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
	return delete;
    }
}
