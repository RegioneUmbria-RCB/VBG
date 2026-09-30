package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDConcessioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TmpStatiComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDConcessioniService;
import it.gruppoinit.pal.gp.core.service.ComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.MovimentimailService;
import it.gruppoinit.pal.gp.core.service.TmpStatiComunicazioniDService;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class ComunicazioniDConcessioniServiceImpl extends BaseServiceImpl<ComunicazioniDConcessioni, PkId> implements
	ComunicazioniDConcessioniService {

    private ComunicazioniDConcessioniDAO comunicazioniDConcessioniDAO;
    private ComunicazioniDService comunicazioniDService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private MovimentimailService movimentimailService;
    private TmpStatiComunicazioniDService tmpStatiComunicazioniDService;

    @Autowired
    public void setTmpStatiComunicazioniDService(TmpStatiComunicazioniDService tmpStatiComunicazioniDService) {

	this.tmpStatiComunicazioniDService = tmpStatiComunicazioniDService;
    }

    @Autowired
    public void setMovimentimailService(MovimentimailService movimentimailService) {

	this.movimentimailService = movimentimailService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setComunicazioniDService(ComunicazioniDService comunicazioniDService) {

	this.comunicazioniDService = comunicazioniDService;
    }

    @Autowired
    public void setComunicazioniDConcessioniDAO(ComunicazioniDConcessioniDAO comunicazioniDConcessioniDAO) {

	this.comunicazioniDConcessioniDAO = comunicazioniDConcessioniDAO;
    }

    @Override
    protected Class<ComunicazioniDConcessioni> getEntityClass() {

	return ComunicazioniDConcessioni.class;
    }

    @Override
    public List<ComunicazioniDConcessioni> findAll(Integer firstResult, Integer maxResult) {

	return comunicazioniDConcessioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ComunicazioniDConcessioni entity) {

	dataIntegration(entity, false);
	if (validateEntity(entity)) {
	    comunicazioniDConcessioniDAO.insert(entity);
	}
    }

    @Override
    public ComunicazioniDConcessioni findById(PkId id) {

	return comunicazioniDConcessioniDAO.findById(id);
    }

    @Override
    public void update(ComunicazioniDConcessioni entity) {

	dataIntegration(entity, true);
	if (validateEntity(entity)) {
	    comunicazioniDConcessioniDAO.update(entity);
	}
    }

    @Override
    public void delete(ComunicazioniDConcessioni entity) {

	if (isDeleteAllowed(entity)) {
	    comunicazioniDConcessioniDAO.delete(entity);
	}
    }

    @Override
    public List<ComunicazioniDConcessioni> findByComunicazioniD(Integer codiceComunicazioniD) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceComunicazioniD, "comunicazioniD", Integer.class));
	ft.addRestriction(fr);
	return comunicazioniDConcessioniDAO.findByFilterTable(ft);
    }

    @Override
    public List<ComunicazioniDConcessioni> findByComunicazioniT(Integer codicecomunicaziot) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	// query da migliorare snellire
	fr.addFilterField(FilterUtils.equals("id.codice", codicecomunicaziot, "comunicazioniD.comunicazioniT", Integer.class));
	ft.addRestriction(fr);
	List<ComunicazioniDConcessioni> list = comunicazioniDConcessioniDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<ComunicazioniDConcessioni> findByComunicazioniTNonTerminate(Integer codicecomunicaziot) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	// query da migliorare snellire
	fr.addFilterField(FilterUtils.equals("id.codice", codicecomunicaziot, "comunicazioniD.comunicazioniT", Integer.class));
	fr.addFilterField(FilterUtils.notEquals("statoElaborazione", ComunicazioniDStatoEnum.ELABORATA_TERMINATA.value(), "comunicazioniD",
		Integer.class));
	ft.addRestriction(fr);
	List<ComunicazioniDConcessioni> list = comunicazioniDConcessioniDAO.findByFilterTable(ft);
	return list;
    }

    private void dataIntegration(ComunicazioniDConcessioni entity, boolean isUpdate) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro ComunicazioniDMercato da validare è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(ComunicazioniDConcessioni entity) {

	AutorizzazioniConcessioni autorizzazioniConcessioni = autorizzazioniConcessioniService.bindDomainObject(
		entity.getAutorizzazioniConcessioni(), PkId.class, "id.codice");
	entity.setAutorizzazioniConcessioni(autorizzazioniConcessioni);
	ComunicazioniD comunicazioniD = comunicazioniDService.bindDomainObject(entity.getComunicazioniD(), PkId.class, "id.codice");
	entity.setComunicazioniD(comunicazioniD);
    }

    protected boolean isDeleteAllowed(ComunicazioniDConcessioni entity) {

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

    @Override
    public List<ComunicazioniDConcessioniDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult) {

	List<ComunicazioniDConcessioniDTO> list = comunicazioniDConcessioniDAO.findByComunicazioniT(codiceComunicazione, firstResult, maxResult);
	return list;
    }

    @Override
    public List<ComunicazioniDConcessioniDTO> findByComunicazioniTWithEvent(Integer codiceComunicazioneT, int startRowPage, int endRowPage) {

	List<ComunicazioniDConcessioniDTO> list = this.findByComunicazioniT(codiceComunicazioneT, startRowPage, endRowPage);
	for (ComunicazioniDConcessioniDTO comunicazioniDConcessioniDTO : list) {
	    if (comunicazioniDConcessioniDTO.getStatoelaborazione().equals(ComunicazioniDStatoEnum.ELABORATA_CON_ERRORI.value())) {
		TmpStatiComunicazioniD tmpStatiComunicazioniD = tmpStatiComunicazioniDService.findPrimoPassoConErrore(comunicazioniDConcessioniDTO
			.getIdcomunicazioned());
		if (tmpStatiComunicazioniD != null) {
		    comunicazioniDConcessioniDTO.setEventoTmpStatiComunicazioniD(tmpStatiComunicazioniD.getEvento());
		}
	    }
	    // Gestione controllo accettazione e consegna mail nel caso sia stata impostata una mail da inviare
	    if (comunicazioniDConcessioniDTO.getMovimentimail() != null) {
		List<Movimentimail> listMailFigle = movimentimailService.findByMailPadre(comunicazioniDConcessioniDTO.getMovimentimail());
		for (Movimentimail movimentimail : listMailFigle) {
		    if (movimentimail.getOggetto().contains("ACCETTAZIONE")) {
			comunicazioniDConcessioniDTO.setAccettata(true);
		    }
		    if (movimentimail.getOggetto().contains("CONSEGNA")) {
			comunicazioniDConcessioniDTO.setConsegnata(true);
		    }
		}
	    }
	}
	return list;
    }

    @Override
    public int countComunicazioniT(Integer codiceComunicazioneT) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceComunicazioneT, "comunicazioniD.comunicazioniT", Integer.class));
	ft.addRestriction(fr);
	return comunicazioniDConcessioniDAO.countRecord(ft);
    }
}
