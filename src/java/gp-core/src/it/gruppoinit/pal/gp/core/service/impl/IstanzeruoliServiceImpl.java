/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeruoli;
import it.gruppoinit.pal.gp.core.domain.IstanzeruoliId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Ruoli;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeruoliDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeruoliService;
import it.gruppoinit.pal.gp.core.service.RuoliService;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author francescop
 * 
 */
@Service
public class IstanzeruoliServiceImpl extends BaseServiceImpl<Istanzeruoli, IstanzeruoliId> implements IstanzeruoliService {

    private IstanzeruoliDAO istanzeruoliDAO;
    private IstanzeService istanzeService;
    private RuoliService ruoliService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setRuoliService(RuoliService ruoliService) {

	this.ruoliService = ruoliService;
    }

    @Autowired
    public void setIstanzeruoliDAO(IstanzeruoliDAO istanzeruoliDAO) {

	this.istanzeruoliDAO = istanzeruoliDAO;
    }

    @Override
    protected Class<Istanzeruoli> getEntityClass() {

	return Istanzeruoli.class;
    }

    @Override
    public void delete(Istanzeruoli entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzeruoliDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzeruoli> findAll(Integer firstResult, Integer maxResult) {

	return istanzeruoliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Istanzeruoli findById(IstanzeruoliId id) {

	return istanzeruoliDAO.findById(id);
    }

    @Override
    public void insert(Istanzeruoli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeruoliDAO.insert(entity);
	}
    }

    @Override
    public void update(Istanzeruoli entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeruoliDAO.update(entity);
	}
    }

    private void dataIntegration(Istanzeruoli entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'istanza passata è nulla");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Istanzeruoli entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Ruoli ruoli = ruoliService.bindDomainObject(entity.getRuolo(), PkId.class, "id.codice");
	entity.setRuolo(ruoli);
	if (istanze != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.codiceistanza") == null) {
		entity.getId().setCodiceistanza(istanze.getId().getCodice());
	    }
	}
	if (ruoli != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.idruolo") == null) {
		entity.getId().setIdruolo(ruoli.getId().getCodice());
	    }
	}
    }

    @Override
    public TipoAccessoEnum checkByIstanzaAndResponsabile(Istanze istanza, Responsabili responsabile) {

	if (EntityUtils.getNestedProperty(istanza, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro Istanza non può essere nullo.");
	}
	if (EntityUtils.getNestedProperty(responsabile, "id.codice") == null) {
	    throw new IllegalArgumentException("Il parametro responsabile non può essere nullo.");
	}
	List<Istanzeruoli> ruolis = istanzeruoliDAO.findByIstanzaAndResponsabile(istanza, responsabile);
	TipoAccessoEnum accesso = TipoAccessoEnum.NON_CONSENTITO;
	if (ruolis.size() > 0) {
	    Istanzeruoli ruolo = ruolis.get(0);
	    if (ruolo.getRuolo().isReadonly()) {
		if (BooleanUtils.isTrue(ruolo.getRuolo().getFlagGestmovimenti()) && BooleanUtils.isTrue(ruolo.getRuolo().getFlagDisgestmovamm())) {
		    accesso = TipoAccessoEnum.SOLA_LETTURA_MOVIMENTI_AMM_INTERNA;
		} else if (BooleanUtils.isTrue(ruolo.getRuolo().getFlagGestmovimenti())) {
		    accesso = TipoAccessoEnum.SOLA_LETTURA_TUTTI_MOVIMENTI;
		} else {
		    accesso = TipoAccessoEnum.SOLA_LETTURA;
		}
	    } else {
		accesso = TipoAccessoEnum.CONSENTITO;
	    }
	}
	return accesso;
    }

    @Override
    public List<Istanzeruoli> findByIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", istanza.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	return istanzeruoliDAO.findByFilterTable(ft);
    }

    @Override
    public void insertInstanzeRuoloDaAlberoproc(Integer codiceAlberoproc, Integer idRuolo) {

	Assert.notNull(codiceAlberoproc);
	Assert.notNull(idRuolo);
	IstanzeFilter filter = new IstanzeFilter();
	filter.setDefaultWhereCondition(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	PkId idAlbProc = new PkId(codiceAlberoproc);
	Alberoproc alberoproc = alberoprocService.findById(idAlbProc);
	Assert.notNull(alberoproc);
	filter.setAlberoproc(alberoproc);
	int count = istanzeService.countIstanzeListHelperByFilter(filter);
	if (count > 0) {
	    double pageNumber = trovaNumPagineRecordSet(count);
	    int startRow = 0;
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * NUMRECS_PER_PAGE;
		List<IstanzeListHelper> istanzes = istanzeService.findIstanzeListHelperByFilter(filter, startRow, NUMRECS_PER_PAGE);
		for (IstanzeListHelper ih : istanzes) {
		    IstanzeruoliDTO ir = istanzeruoliDAO.findDTOById(ih.getIdcomune(), ih.getCodiceistanza().intValue(), idRuolo);
		    if (ir == null) {
			istanzeruoliDAO.insertDTO(ih.getIdcomune(), ih.getCodiceistanza().intValue(), idRuolo);
			istanzeruoliDAO.commit();
			istanzeruoliDAO.flush();
			istanzeruoliDAO.clear();
		    }
		}
	    }
	}
    }

    @Override
    public void deleteInstanzeRuoloDaAlberoproc(Integer codiceAlberoproc, Integer idRuolo) {

	int count = istanzeruoliDAO.countByAlberoprocAndIdRuolo(codiceAlberoproc, idRuolo);
	if (count > 0) {
	    double pageNumber = trovaNumPagineRecordSet(count);
	    int startRow = 0;
	    for (int i = 0; i < pageNumber; i++) {
		startRow = i * NUMRECS_PER_PAGE;
		List<IstanzeruoliDTO> irs = istanzeruoliDAO.findDTOByAlberoprocAndIdRuolo(codiceAlberoproc, idRuolo, startRow, NUMRECS_PER_PAGE);
		for (IstanzeruoliDTO dto : irs) {
		    istanzeruoliDAO.deleteDTO(dto.getIdcomune(), dto.getCodiceistanza(), dto.getIdruolo());
		}
		istanzeruoliDAO.commit();
		istanzeruoliDAO.flush();
		istanzeruoliDAO.clear();
	    }
	}
    }

    @Override
    public void deleteInstanzeRuoliDaAlberoproc(Integer codiceAlberoproc) {

	istanzeruoliDAO.deleteAllByAlberoproc(codiceAlberoproc);
    }

    private double trovaNumPagineRecordSet(int totRecords) {

	double conta = 0;
	double pageNumber = 0;
	if (totRecords < NUMRECS_PER_PAGE) {
	    pageNumber = 1;
	} else {
	    conta = Double.valueOf(totRecords) / Double.valueOf(NUMRECS_PER_PAGE);
	    pageNumber = Math.ceil(conta);
	}
	return pageNumber;
    }

    private int NUMRECS_PER_PAGE = 100;
}
