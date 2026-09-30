package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ArchiviazioniIstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ArchiviazioniIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ArchiviazioniIstanzeService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author fabrizioc
 */
@Service
public class ArchiviazioniIstanzeServiceImpl extends BaseServiceImpl<ArchiviazioniIstanze, PkId> implements ArchiviazioniIstanzeService {

    private ArchiviazioniIstanzeDAO archiviazioniistanzeDAO;

    @Autowired
    public void setArchiviazioniIstanzeDAO(ArchiviazioniIstanzeDAO archiviazioniistanzeDAO) {

	this.archiviazioniistanzeDAO = archiviazioniistanzeDAO;
    }

    @Override
    protected Class<ArchiviazioniIstanze> getEntityClass() {

	return ArchiviazioniIstanze.class;
    }

    @Override
    public List<ArchiviazioniIstanze> findAll(Integer firstResult, Integer maxResult) {

	return archiviazioniistanzeDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<ArchiviazioniIstanze> findEscluse(Integer codiceArchiviazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("esclusa", true, Boolean.class));
	r.addFilterField(FilterUtils.equals("archiviazioniId", codiceArchiviazione, Integer.class));
	ft.addRestriction(r);
	return archiviazioniistanzeDAO.findByFilterTable(ft);
    }

    @Override
    public void insert(ArchiviazioniIstanze entity) {

	if (validateEntity(entity)) {
	    archiviazioniistanzeDAO.insert(entity);
	}
    }

    @Override
    public Integer insert(Integer codiceArchiviazione, Integer codiceIstanza) {

	return archiviazioniistanzeDAO.insert(codiceArchiviazione, codiceIstanza);
    }

    @Override
    public List<ArchiviazioniIstanze> findByIstanza(Integer codiceIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanzaR = new FilterRestriction();
	istanzaR.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	ft.addRestriction(istanzaR);
	return archiviazioniistanzeDAO.findByFilterTable(ft);
    }

    @Override
    public List<ArchiviazioniIstanze> findIstanzaOggettoArchiviato(Integer codiceArchiviazione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction r = new FilterRestriction();
	r.addFilterField(FilterUtils.equals("esclusa", false, Boolean.class));
	r.addFilterField(FilterUtils.equals("archiviazioniId", codiceArchiviazione, Integer.class));
	ft.addRestriction(r);
	return archiviazioniistanzeDAO.findByFilterTable(ft);
    }

    @Override
    public ArchiviazioniIstanze findById(PkId id) {

	return archiviazioniistanzeDAO.findById(id);
    }

    @Override
    public void update(ArchiviazioniIstanze entity) {

	if (validateEntity(entity)) {
	    archiviazioniistanzeDAO.update(entity);
	}
    }

    @Override
    public void delete(ArchiviazioniIstanze entity) {

	if (isDeleteAllowed(entity)) {
	    archiviazioniistanzeDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ArchiviazioniIstanze entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO validare_la_delete
	// esempio:
	// if (archiviazioniOggettiService.count(entity) > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ARCHIVIAZIONI_OGGETTI", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public void evict(ArchiviazioniIstanze entity) {

	archiviazioniistanzeDAO.evict(entity);
    }

    @Override
    public void deleteByIstanza(Integer codiceIstanza) {

	List<ArchiviazioniIstanze> list = this.findByIstanza(codiceIstanza);
	for (ArchiviazioniIstanze archiviazioniIstanze : list) {
	    this.delete(archiviazioniIstanze);
	}
    }
}
