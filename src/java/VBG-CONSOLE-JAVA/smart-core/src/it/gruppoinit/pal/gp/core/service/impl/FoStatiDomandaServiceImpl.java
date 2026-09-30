/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoStatiDomandaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.domain.FoStatiDomanda;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;
import it.gruppoinit.pal.gp.core.service.FoStatiDomandaService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francol
 *
 */
@Service
public class FoStatiDomandaServiceImpl extends BaseServiceImpl<FoStatiDomanda, PkId> implements FoStatiDomandaService {

    //private final Logger log = LoggerFactory.getLogger(FoStatiDomandaServiceImpl.class);
    private FoStatiDomandaDAO foStatiDomandaDao;
    private FoDomandeService foDomandeService;

    @Autowired
    public void setFoStatiDomandaDao(FoStatiDomandaDAO dao) {

	this.foStatiDomandaDao = dao;
    }

    @Autowired
    public void setFoDomandeService(FoDomandeService srvc) {

	this.foDomandeService = srvc;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#insert(java.lang.Object)
     */
    @Override
    public void insert(FoStatiDomanda entity) {

	this.foStatiDomandaDao.insert(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#update(java.lang.Object)
     */
    @Override
    public void update(FoStatiDomanda entity) {

	this.foStatiDomandaDao.update(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#delete(java.lang.Object)
     */
    @Override
    public void delete(FoStatiDomanda entity) {

	this.foStatiDomandaDao.delete(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findAll(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<FoStatiDomanda> findAll(Integer firstResult, Integer maxResult) {

	return this.foStatiDomandaDao.findAll(firstResult, maxResult);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#findById(java.io.Serializable)
     */
    @Override
    public FoStatiDomanda findById(PkId id) {

	return this.foStatiDomandaDao.findById(id);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#bindDomainObject(java.lang.Object, java.lang.Class, java.lang.String)
     */
    @Override
    public FoStatiDomanda bindDomainObject(FoStatiDomanda entity, Class<?> idClass, String idPath) {

	return entity;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.BaseService#newIdFromSequencetable(java.lang.Object)
     */
    @Override
    public PkId newIdFromSequencetable(FoStatiDomanda entity) {

	return this.foStatiDomandaDao.newIdFromSequence(entity);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.FoDomandeEventiService#findByIdDomanda(java.lang.Integer)
     */
    @Override
    public List<FoStatiDomanda> findByIdDomanda(String idComuneDomanda, Integer idDomanda) {

	return this.foStatiDomandaDao.findByIdDomanda(idComuneDomanda, idDomanda);
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl#getEntityClass()
     */
    @Override
    protected Class<FoStatiDomanda> getEntityClass() {

	return FoStatiDomanda.class;
    }

    @Override
    public FoStatiDomanda aggiornaStatoDomanda(Integer idDomanda, StatoDomandaFacctEnum newStatus, String message, String azioneRichiesta) {

	FoDomande fkDomanda = foDomandeService.findById(new PkId(idDomanda));
	FoStatiDomanda status = new FoStatiDomanda();
	status.setFoDomande(fkDomanda);
	status.setStato(newStatus.name());
	status.setData(new Date());
	status.setMessaggioErrore(message);
	status.setAzioneRichiesta(azioneRichiesta);
	foStatiDomandaDao.insert(status);
	return status;
    }

    @Override
    public FoStatiDomanda getStatoDomanda(String idComuneDomanda, Integer idDomanda) {

	FoStatiDomanda status = null;
	if (idDomanda != null) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("id.idcomune", idComuneDomanda, String.class));
	    fr.addFilterField(FilterUtils.equals("fkDomanda", idDomanda, Integer.class));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderDesc("data"));
	    List<FoStatiDomanda> stati = foStatiDomandaDao.findByFilterTable(ft, 0, 1);
	    if (stati.size() > 0) {
		status = stati.get(0);
	    }
	}
	return status;
    }
}
