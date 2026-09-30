package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AllegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AllegatiService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AllegatiServiceImpl extends BaseServiceImpl<Allegati, PkId> implements AllegatiService {

    private AllegatiDAO allegatiDAO;
    private AmministrazioniService amministrazioniService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private OggettiService oggettiService;
    private ComuniService comuniService;
    private static final Logger log = LoggerFactory.getLogger(AllegatiServiceImpl.class);

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
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
    public void setAllegatiDAO(AllegatiDAO allegatiDAO) {

	this.allegatiDAO = allegatiDAO;
    }

    @Override
    protected Class<Allegati> getEntityClass() {

	return Allegati.class;
    }

    @Override
    public void delete(Allegati entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    allegatiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Allegati> findAll(Integer firstResult, Integer maxResult) {

	return allegatiDAO.findAll(null, null);
    }

    @Override
    public Allegati findById(PkId id) {

	return allegatiDAO.findById(id);
    }

    @Override
    public void insert(Allegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    allegatiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void update(Allegati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    allegatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    private void dataIntegration(Allegati entity) {

	if (entity == null) {
	    log.error("dataIntegration: Il parametro allegato non può essere nullo");
	    throw new IllegalArgumentException("Il parametro allegato non può essere nullo");
	}
	if (entity.getFlagInserimentoAut() == null) {
	    entity.setFlagInserimentoAut(Boolean.FALSE);
	}
	if (entity.getFoRichiedefirma() == null) {
	    entity.setFoRichiedefirma(Boolean.FALSE);
	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(Integer.valueOf(0));
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Allegati entity) {

	Amministrazioni amministrazione = amministrazioniService.bindDomainObject(entity.getAmministrazioni(), PkId.class, "id.codice");
	entity.setAmministrazioni(amministrazione);
	Oggetti oggetto = oggettiService.bindDomainObjectForPkId(entity.getOggetti());
	entity.setOggetti(oggetto);
	Inventarioprocedimenti endo = inventarioprocedimentiService.bindDomainObjectForPkId(entity.getInventarioprocedimento());
	entity.setInventarioprocedimento(endo);
	Comuni c = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(c);
    }

    @Override
    public List<Allegati> findByFilterTable(FilterTable filterTable) {

	return allegatiDAO.findByFilterTable(filterTable);
    }

    /**
     * childDelete particolare, non fa una vera e propria cancellazione, ma va a scollegate l'allegato a tutte le
     * istanze a cui è collegato , andando a mettere a null il campo FK_IDALLEGATO nella tabella istanzeallegati.
     * 
     */
    protected void childDelete(Allegati entity) {

	//	Set<Istanzeallegati> list = entity.getIstanzeallegatis();
	//	for (Istanzeallegati istanzeallegati : list) {
	//	    istanzeallegati.setAllegati(null);
	//	    istanzeallegatiService.update(istanzeallegati);
	//	}
    }

    //    @Override
    //    protected boolean isDeleteAllowed(Allegati entity) {
    //
    ////	Boolean insert = true;
    //	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //	if (entity != null && !entity.getIstanzeallegatis().isEmpty()) {
    //	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ISTANZEALLEGATI", null));
    //	}
    //	if (!_ivs.isEmpty()) {
    //	    this.throwValidationMessages(_ivs);
    //	}
    //	return insert;
    //    }
    @Override
    public List<Allegati> findByInventarioprocedimenti(String idcomune, Integer codiceInventario) {

	Assert.notNull(codiceInventario);
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, "inventarioprocedimento", String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceInventario, "inventarioprocedimento", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("allegato"));
	return allegatiDAO.findByFilterTable(ft);
    }

    @Override
    public int countByInventarioprocedimenti(String idcomune, Integer codiceInventario) {

	Assert.notNull(codiceInventario);
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, "inventarioprocedimento", String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceInventario, "inventarioprocedimento", Integer.class));
	ft.addRestriction(fr);
	return allegatiDAO.countRecord(ft);
    }

    @Override
    public List<Allegati> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return allegatiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Allegati> findByInventarioprocedimentoAndComune(Integer codiceInventario, String idcomunecodiceinventario, String idcomunerecord) {

	Assert.notNull(codiceInventario);
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomunerecord, String.class));
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomunecodiceinventario, "inventarioprocedimento", String.class));
	fr.addFilterField(FilterUtils.equals("id.codice", codiceInventario, "inventarioprocedimento", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	ft.addOrder(FilterUtils.orderAsc("allegato"));
	return allegatiDAO.findByFilterTable(ft);
    }
}
