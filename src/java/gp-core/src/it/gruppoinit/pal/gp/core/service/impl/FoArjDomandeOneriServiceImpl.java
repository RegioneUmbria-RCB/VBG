package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjDomandeOneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.FoArjDomande;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentioneri;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.TipicausalioneriService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class FoArjDomandeOneriServiceImpl extends BaseServiceImpl<FoArjDomandeOneri, PkId> implements FoArjDomandeOneriService {

    private FoArjDomandeOneriDAO foarjdomandeoneriDAO;
    private FoArjDomandeService foArjDomandeService;
    private OggettiService oggettiService;
    private AlberoprocService alberoprocService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private TipicausalioneriService tipicausalioneriService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setFoArjDomandeService(FoArjDomandeService foArjDomandeService) {

	this.foArjDomandeService = foArjDomandeService;
    }

    @Autowired
    public void setFoArjDomandeOneriDAO(FoArjDomandeOneriDAO foarjdomandeoneriDAO) {

	this.foarjdomandeoneriDAO = foarjdomandeoneriDAO;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTipicausalioneriService(TipicausalioneriService tipicausalioneriService) {

	this.tipicausalioneriService = tipicausalioneriService;
    }

    @Override
    protected Class<FoArjDomandeOneri> getEntityClass() {

	return FoArjDomandeOneri.class;
    }

    @Override
    public List<FoArjDomandeOneri> findAll(Integer firstResult, Integer maxResult) {

	return foarjdomandeoneriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoArjDomandeOneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoXmlDaCancellare = controllaCancellaOggetti(entity, "oggettoXml", false, entity.getId());
	    Integer codiceOggettoPdfDaCancellare = controllaCancellaOggetti(entity, "oggettoPdf", false, entity.getId());
	    foarjdomandeoneriDAO.insert(entity);
	    if (codiceOggettoXmlDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoXmlDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    if (codiceOggettoPdfDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoPdfDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    private void dataIntegration(FoArjDomandeOneri entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'oggetto passato è nullo");
	}
	if (entity.getFlagOnline() == null) {
	    entity.setFlagOnline(Boolean.FALSE);
	}
	if (entity.getFlagStato() == null) {
	    entity.setFlagStato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(FoArjDomandeOneri entity) {

	Inventarioprocedimenti ip = inventarioprocedimentiService.bindDomainObject(entity.getInventarioprocedimenti(), PkId.class, "id.codice");
	entity.setInventarioprocedimenti(ip);
	Tipicausalioneri tco = tipicausalioneriService.bindDomainObject(entity.getTipicausalioneri(), PkId.class, "id.codice");
	entity.setTipicausalioneri(tco);
	FoArjDomande domanda = foArjDomandeService.bindDomainObject(entity.getFoArjDomande(), PkId.class, "id.codice");
	entity.setFoArjDomande(domanda);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggettoXml(), PkId.class, "id.codice");
	entity.setOggettoXml(oggetto);
	oggetto = oggettiService.bindDomainObject(entity.getOggettoPdf(), PkId.class, "id.codice");
	entity.setOggettoPdf(oggetto);
    }

    @Override
    public FoArjDomandeOneri findById(PkId id) {

	return foarjdomandeoneriDAO.findById(id);
    }

    @Override
    public void update(FoArjDomandeOneri entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoXmlDaCancellare = controllaCancellaOggetti(entity, "oggettoXml", false, entity.getId());
	    Integer codiceOggettoPdfDaCancellare = controllaCancellaOggetti(entity, "oggettoPdf", false, entity.getId());
	    foarjdomandeoneriDAO.update(entity);
	    if (codiceOggettoXmlDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoXmlDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    if (codiceOggettoPdfDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoPdfDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(FoArjDomandeOneri entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoXmlDaCancellare = controllaCancellaOggetti(entity, "oggettoXml", true, entity.getId());
	    Integer codiceOggettoPdfDaCancellare = controllaCancellaOggetti(entity, "oggettoPdf", true, entity.getId());
	    foarjdomandeoneriDAO.delete(entity);
	    if (codiceOggettoXmlDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoXmlDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	    if (codiceOggettoPdfDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoPdfDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(FoArjDomandeOneri entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<FoArjDomandeOneri> findByIdDomanda(Integer codiceDomanda) {

	FilterTable filterTable = getFilterForCodiceDomanda(codiceDomanda);
	return foarjdomandeoneriDAO.findByFilterTable(filterTable);
    }

    private FilterTable getFilterForCodiceDomanda(Integer codiceDomanda) {

	if (codiceDomanda == null) {
	    throw new RuntimeException("Il parametro codice domanda non può essere nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("foArjDomandeId", codiceDomanda, Integer.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderAsc("ordine", "inventarioprocedimenti"));
	filterTable.addOrder(FilterUtils.orderAsc("procedimento", "inventarioprocedimenti"));
	filterTable.addOrder(FilterUtils.orderAsc("coOrdinamento", "tipicausalioneri"));
	filterTable.addOrder(FilterUtils.orderAsc("coDescrizione", "tipicausalioneri"));
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return filterTable;
    }

    @Override
    public List<FoArjDomandeOneri> findPagatiByIdDomanda(Integer codiceDomanda) {

	FilterTable filterTable = getFilterForCodiceDomanda(codiceDomanda);
	filterTable.addRestriction(statoPagato());
	return foarjdomandeoneriDAO.findByFilterTable(filterTable);
    }

    private FilterRestriction statoPagato() {

	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("flagStato", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.isNotNull("oggettoPdfId"));
	return fr;
    }

    private FilterRestriction statoNonPagato() {

	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("flagStato", Boolean.FALSE, Boolean.class));
	fr.addFilterField(FilterUtils.isNull("oggettoPdfId"));
	return fr;
    }

    @Override
    public void deleteByDomanda(Integer codiceDomanda) {

	List<FoArjDomandeOneri> oneris = this.findByIdDomanda(codiceDomanda);
	for (FoArjDomandeOneri entity : oneris) {
	    this.delete(entity);
	}
    }

    @Override
    public void insertOneriPerIntervento(Integer codiceDomanda, Integer codiceIntervento) {

	Alberoproc ap = alberoprocService.findById(new PkId(codiceIntervento));
	AlberoprocHelper h = alberoprocService.findAlberoprocHelper(ap);
	Set<AlberoprocOneri> oneris = h.getAlberoprocOneris();
	if (oneris.size() > 0) {
	    FoArjDomande foArjDomande = foArjDomandeService.findById(new PkId(codiceDomanda));
	    for (AlberoprocOneri ao : oneris) {
		if (ao.getAoImportocausale() != null) {
		    if (ao.getAoImportocausale().compareTo(BigDecimal.ZERO) > 0) {
			FoArjDomandeOneri fado = new FoArjDomandeOneri();
			fado.setTipicausalioneri(ao.getTipicausalioneri());
			fado.setFlagOnline(Boolean.FALSE);
			fado.setFlagStato(Boolean.FALSE);
			fado.setImporto(ao.getAoImportocausale());
			fado.setFoArjDomande(foArjDomande);
			fado.setNote(ao.getNote());
			this.insert(fado);
		    }
		}
	    }
	}
    }

    @Override
    public List<FoArjDomandeOneri> findByIdDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceInventario) {

	FilterTable ft = getFilterForCodiceDomanda(codiceDomanda);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	return foarjdomandeoneriDAO.findByFilterTable(ft);
    }

    @Override
    public List<FoArjDomandeOneri> findPagatiByIdDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceInventario) {

	FilterTable ft = getFilterForCodiceDomanda(codiceDomanda);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("inventarioprocedimentiId", codiceInventario, Integer.class));
	ft.addRestriction(fr);
	ft.addRestriction(statoPagato());
	return foarjdomandeoneriDAO.findByFilterTable(ft);
    }

    @Override
    public void insertOneriPerCodiceInventario(Integer codiceDomanda, Integer codiceProcedimento) {

	Inventarioprocedimenti i = inventarioprocedimentiService.findById(new PkId(codiceProcedimento));
	Set<Inventarioprocedimentioneri> oneris = i.getInventarioprocedimentioneris();
	if (oneris.size() > 0) {
	    FoArjDomande foArjDomande = foArjDomandeService.findById(new PkId(codiceDomanda));
	    for (Inventarioprocedimentioneri ao : oneris) {
		if (ao.getImporto() != null) {
		    if (ao.getImporto().compareTo(BigDecimal.ZERO) > 0) {
			FoArjDomandeOneri fado = new FoArjDomandeOneri();
			fado.setTipicausalioneri(ao.getTipicausalioneri());
			fado.setFlagOnline(Boolean.FALSE);
			fado.setFlagStato(Boolean.FALSE);
			fado.setImporto(ao.getImporto());
			fado.setInventarioprocedimenti(i);
			fado.setFoArjDomande(foArjDomande);
			fado.setNote(ao.getNote());
			this.insert(fado);
		    }
		}
	    }
	}
    }

    @Override
    public void deleteByDomandaAndCodiceInventario(Integer codiceDomanda, Integer codiceProcedimento) {

	List<FoArjDomandeOneri> oneris = this.findByIdDomandaAndCodiceInventario(codiceDomanda, codiceProcedimento);
	for (FoArjDomandeOneri foArjDomandeOneri : oneris) {
	    this.delete(foArjDomandeOneri);
	}
    }

    @Override
    public void updatePagamentiOnline(FoArjDomande domanda, String numeroOperazione, String idTransazione, Oggetti oggettoXml, Oggetti oggettoPdf) {

	FilterTable filterTable = getFilterForCodiceDomanda(domanda.getId().getCodice());
	filterTable.addRestriction(statoNonPagato());
	FilterRestriction nope = new FilterRestriction();
	nope.addFilterField(FilterUtils.equals("idNumOperazOnline", numeroOperazione, String.class));
	filterTable.addRestriction(nope);
	List<FoArjDomandeOneri> oneris = foarjdomandeoneriDAO.findByFilterTable(filterTable);
	for (FoArjDomandeOneri fado : oneris) {
	    fado.setFlagStato(Boolean.TRUE);
	    if (oggettoXml != null) {
		fado.setOggettoXml(oggettoXml);
	    }
	    if (oggettoPdf != null) {
		fado.setOggettoPdf(oggettoPdf);
	    }
	    fado.setIdordineSistemaPagamenti(idTransazione);
	    this.update(fado);
	}
    }

    @Override
    public void updatePagamentiOnlineSetPagato(FoArjDomande domanda, String numeroOperazione, String idTransazione) {

	FilterTable filterTable = getFilterForCodiceDomanda(domanda.getId().getCodice());
	filterTable.addRestriction(statoNonPagato());
	FilterRestriction nope = new FilterRestriction();
	nope.addFilterField(FilterUtils.equals("idNumOperazOnline", numeroOperazione, String.class));
	filterTable.addRestriction(nope);
	List<FoArjDomandeOneri> oneris = foarjdomandeoneriDAO.findByFilterTable(filterTable);
	for (FoArjDomandeOneri fado : oneris) {
	    fado.setFlagStato(Boolean.TRUE);
	    fado.setIdordineSistemaPagamenti(idTransazione);
	    this.update(fado);
	}
    }

    @Override
    public void updatePagamentiOnlineSetRicevute(FoArjDomande domanda, String numeroOperazione, Oggetti oggettoXml, Oggetti oggettoPdf) {

	FilterTable filterTable = getFilterForCodiceDomanda(domanda.getId().getCodice());
	filterTable.addRestriction(statoPagato());
	FilterRestriction nope = new FilterRestriction();
	nope.addFilterField(FilterUtils.equals("idNumOperazOnline", numeroOperazione, String.class));
	filterTable.addRestriction(nope);
	List<FoArjDomandeOneri> oneris = foarjdomandeoneriDAO.findByFilterTable(filterTable);
	for (FoArjDomandeOneri fado : oneris) {
	    fado.setFlagStato(Boolean.TRUE);
	    if (oggettoXml != null) {
		fado.setOggettoXml(oggettoXml);
	    }
	    if (oggettoPdf != null) {
		fado.setOggettoPdf(oggettoPdf);
	    }
	    this.update(fado);
	}
    }

    @Override
    public List<FoArjDomandeOneri> findOneriInterventoByIdDomanda(Integer codiceDomanda) {

	FilterTable ft = getFilterForCodiceDomanda(codiceDomanda);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("inventarioprocedimentiId"));
	ft.addRestriction(fr);
	return foarjdomandeoneriDAO.findByFilterTable(ft);
    }
}
