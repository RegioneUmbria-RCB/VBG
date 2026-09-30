package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzelavoriTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriD;
import it.gruppoinit.pal.gp.core.domain.IstanzelavoriT;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriDService;
import it.gruppoinit.pal.gp.core.service.IstanzelavoriTService;
import it.gruppoinit.pal.gp.core.service.LavoritipiService;

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
public class IstanzelavoriTServiceImpl extends BaseServiceImpl<IstanzelavoriT, PkId> implements IstanzelavoriTService {

    private IstanzelavoriTDAO istanzelavoritDAO;
    private IstanzeService istanzeService;
    private LavoritipiService lavoritipiService;
    private IstanzestradarioService istanzestradarioService;
    private IstanzelavoriDService istanzelavoriDService;

    @Autowired
    public void setIstanzelavoriTDAO(IstanzelavoriTDAO istanzelavoritDAO) {

	this.istanzelavoritDAO = istanzelavoritDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Autowired
    public void setLavoritipiService(LavoritipiService lavoritipiService) {

	this.lavoritipiService = lavoritipiService;
    }

    @Autowired
    public void setIstanzelavoriDService(IstanzelavoriDService istanzelavoriDService) {

	this.istanzelavoriDService = istanzelavoriDService;
    }

    @Override
    protected Class<IstanzelavoriT> getEntityClass() {

	return IstanzelavoriT.class;
    }

    @Override
    public List<IstanzelavoriT> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return istanzelavoritDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    entity.setLavoro(entity.getLavoritipi().getLavoro());
	    istanzelavoritDAO.insert(entity);
	    childDataInsert(entity);
	}
	// §§§END§§§
    }

    @Override
    public IstanzelavoriT findById(PkId id) {

	// §§§BEGIN§§§
	return istanzelavoritDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    istanzelavoritDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity))
	    childDelete(entity);
	{
	    istanzelavoritDAO.delete(entity);
	}
	// §§§END§§§
    }

    @Override
    public List<IstanzelavoriT> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return istanzelavoritDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<IstanzelavoriT> findByIstanza(Istanze istanze) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("istanze", istanze, Istanze.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderDesc("lavoro", "lavoritipi"));
	return this.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    protected boolean isDeleteAllowed(IstanzelavoriT entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    private void dataIntegration(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("L'istanzaLavorit passata è nulla");
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Lavoritipi lavoritipi = lavoritipiService.bindDomainObject(entity.getLavoritipi(), PkId.class, "id.codice");
	entity.setLavoritipi(lavoritipi);
	Istanzestradario istanzestradario = istanzestradarioService.bindDomainObject(entity.getIstanzestradario(), PkId.class, "id.codice");
	entity.setIstanzestradario(istanzestradario);
	// §§§END§§§
    }

    private void childDataInsert(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	//se la lista di lavori tipi causali oneri di lavori tipi non è vuota
	//collegherò a istanza lavori t un numero di record di istanza lavori d pari alla dimensione della lista ausali oneri di lavori tipi
	// contenete i campi :
	// 1- Lavoriistanzat
	// 2- Unità di misura
	// 3- Costo unitario
	// 4- Tipi causali oneri
	if (!entity.getLavoritipi().getLavoritipiCausalioneris().isEmpty()) {
	    IstanzelavoriD istanzelavoriD = null;
	    Set<LavoritipiCausalioneri> list = entity.getLavoritipi().getLavoritipiCausalioneris();
	    for (LavoritipiCausalioneri lavoritipiCausalioneri : list) {
		istanzelavoriD = new IstanzelavoriD();
		istanzelavoriD.setIstanzelavoriT(entity);
		istanzelavoriD.setTipicausalioneri(lavoritipiCausalioneri.getTipicausalioneri());
		istanzelavoriD.setTipiunitamisura(lavoritipiCausalioneri.getTipiunitamisura());
		istanzelavoriD.setCostoUnitarioUm(lavoritipiCausalioneri.getCostoUnitarioUm());
		istanzelavoriDService.insert(istanzelavoriD);
	    }
	}
	// §§§END§§§
    }

    @Override
    protected void childDelete(IstanzelavoriT entity) {

	// §§§BEGIN§§§
	Set<IstanzelavoriD> lsit = entity.getIstanzelavoriDs();
	for (IstanzelavoriD istanzelavoriD : lsit) {
	    istanzelavoriDService.delete(istanzelavoriD);
	}
	// §§§END§§§
    }
}
