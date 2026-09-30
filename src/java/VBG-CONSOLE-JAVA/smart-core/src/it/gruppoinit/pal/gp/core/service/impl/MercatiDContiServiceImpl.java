package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDContiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiDContiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiDContiServiceImpl extends BaseServiceImpl<MercatiDConti, PkId> implements MercatiDContiService {

    private MercatiDContiDAO mercatiDContiDAO;

    @Autowired
    public void setMercatiDContiDAO(MercatiDContiDAO mercatiDContiDAO) {

	this.mercatiDContiDAO = mercatiDContiDAO;
    }

    @Override
    public List<MercatiDConti> findByPosteggio(MercatiD posteggio) {

	return mercatiDContiDAO.findByPosteggio(posteggio);
    }

    @Override
    public void delete(MercatiDConti entity) {

	mercatiDContiDAO.delete(entity);
    }

    @Override
    public List<MercatiDConti> findAll(Integer firstResult, Integer maxResult) {

	// return mercatiDContiDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE);
	throw new NotImplementedException();
    }

    @Override
    public MercatiDConti findById(PkId id) {

	return mercatiDContiDAO.findById(id);
    }

    @Override
    public void insert(MercatiDConti entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateLine(entity, true)) {
		mercatiDContiDAO.insert(entity);
	    }
	}
    }

    @Override
    public void update(MercatiDConti entity) {

	if (validateEntity(entity)) {
	    if (validateDuplicateLine(entity, false)) {
		mercatiDContiDAO.update(entity);
	    }
	}
    }

    @Override
    protected Class<MercatiDConti> getEntityClass() {

	return MercatiDConti.class;
    }

    /**
     * Validazione ulteriore per evitare record duplicati non è possibile inserire due righe per lo stesso posteggio che
     * abbiano lo stesso codice conto e lo stesso anno
     * 
     * @param entity
     * @param isInsert
     *            se false allora deve saltare il controllo sullo stesso record
     * @return
     */
    private boolean validateDuplicateLine(MercatiDConti entity, boolean isInsert) {

	List<MercatiDConti> listMc = mercatiDContiDAO.findByPosteggio(entity.getPosteggio());
	Short anno = entity.getAnno();
	Conti conto = entity.getConto();
	// Controllo che non ci sia per quel posteggio due righe replicate ossia con lo stesso anno e conto
	// Nullpointer exception evitato perché le proprietà da controllare sono state validate dal metodo validate
	// che controlla le proprietà dell'oggetto di dominio
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isValid = true;
	for (MercatiDConti mercatiDConti : listMc) {
	    if (anno.intValue() == mercatiDConti.getAnno().intValue()) {
		if (conto.getId().getCodice().intValue() == mercatiDConti.getConto().getId().getCodice().intValue()) {
		    if (mercatiDConti.getContesto().equals(entity.getContesto())) {
			if (!isInsert) { // se non sono in inserimento ed aggiorno devo controllare che non sto
			    // duplicando
			    // un record già inserito
			    if (entity.getId().getCodice().intValue() != mercatiDConti.getId().getCodice().intValue()) {
				// se non sto aggiornando lo stesso record ( nel qual caso devo saltare il controllo )
				InvalidValue iv = new InvalidValue("errors.validator.mercatidconti.duplicate", entity.getClass(), "conto", null,
					entity);
				_ivs.add(iv);
				isValid = false;
				break;
			    }
			} else {
			    InvalidValue iv = new InvalidValue("errors.validator.mercatidconti.duplicate", entity.getClass(), "conto", "", entity);
			    _ivs.add(iv);
			    isValid = false;
			    break;
			}
		    }
		}
	    }
	}
	if (!(isValid)) {
	    this.throwValidationMessages(_ivs);
	}
	return isValid;
    }

    @Override
    public List<MercatiDConti> findByMercatiAndAnno(Mercati mercati, Integer anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", mercati.getId().getCodice(), "posteggio", Integer.class));
	fr.addFilterField(FilterUtils.equals("anno", anno.shortValue(), Short.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codiceposteggio", "posteggio"));
	return mercatiDContiDAO.findByFilterTable(ft);
    }
}
