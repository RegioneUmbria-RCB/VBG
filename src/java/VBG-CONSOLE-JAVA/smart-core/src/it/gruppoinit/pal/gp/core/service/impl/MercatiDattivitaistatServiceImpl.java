package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDattivitaistatDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistatId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiDattivitaistatServiceImpl extends BaseServiceImpl<MercatiDattivitaistat, MercatiDattivitaistatId> implements
	MercatiDattivitaistatService {

    private MercatiDattivitaistatDAO mercatiDattivitaistatDAO;
    private MercatiDDAO mercatiDDAO;
    private MercatiDAO mercatiDAO;

    @Autowired
    public void setMercatiDAO(MercatiDAO mercatiDAO) {

	this.mercatiDAO = mercatiDAO;
    }

    @Autowired
    public void setMercatiDDAO(MercatiDDAO mercatiDDAO) {

	this.mercatiDDAO = mercatiDDAO;
    }

    public MercatiDAO getMercatiDAO() {

	return mercatiDAO;
    }

    @Autowired
    public void setMercatiDattivitaistatDAO(MercatiDattivitaistatDAO mercatiDattivitaistatDAO) {

	this.mercatiDattivitaistatDAO = mercatiDattivitaistatDAO;
    }

    @Override
    protected Class<MercatiDattivitaistat> getEntityClass() {

	return MercatiDattivitaistat.class;
    }

    @Override
    public void delete(MercatiDattivitaistat entity) {

	mercatiDattivitaistatDAO.delete(entity);
    }

    @Override
    public List<MercatiDattivitaistat> findAll(Integer firstResult, Integer maxResult) {

	return mercatiDattivitaistatDAO.findAll(null, null);
    }

    @Override
    public MercatiDattivitaistat findById(MercatiDattivitaistatId id) {

	return mercatiDattivitaistatDAO.findById(id);
    }

    @Override
    public void insert(MercatiDattivitaistat entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    mercatiDattivitaistatDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatiDattivitaistat entity) {

	if (validateEntity(entity)) {
	    mercatiDattivitaistatDAO.update(entity);
	}
    }

    @Override
    public void insertMerceologieAPosteggi(MercatiDattivitaistat mercatiDattivitaistat) {

	if (validateEntity(mercatiDattivitaistat)) {
	    String[] arraycodiceposteggi = mercatiDattivitaistat.getPosteggio().getListacodici();
	    for (int i = 0; i < arraycodiceposteggi.length; i++) {
		String string = arraycodiceposteggi[i];
		MercatiD mercatid = mercatiDDAO.findById(new PkId(Integer.valueOf(string)));
		Set<MercatiDattivitaistat> listAttivita = new HashSet<MercatiDattivitaistat>();
		MercatiDattivitaistatId id = new MercatiDattivitaistatId(ORMHelper.getIdcomune(), mercatid.getMercati().getId().getCodice(), mercatid
			.getId().getCodice(), mercatiDattivitaistat.getAttivita().getId().getCodiceistat());
		mercatiDattivitaistat.setId(id);
		listAttivita.add(mercatiDattivitaistat);
		mercatid.setMercatiDattivitaistats(listAttivita);
		mercatiDDAO.insert(mercatid);
	    }
	}
    }

    @Override
    public List<MercatiDattivitaistat> findAttivitaPosteggi(Integer codicemercato, List<Integer> listcodici) {

	return mercatiDattivitaistatDAO.findAttivitaPosteggi(codicemercato, listcodici);
    }

    /**
     * Il metodo deve controllare che non esiste già un record con il codice merceologia che stiamo passando
     * 
     * @param entity
     * @return
     */
    protected boolean isInsertAllowed(MercatiDattivitaistat entity) {

	// controlla che già non esista un recordo con quel codice posteggio
	boolean insert = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	MercatiDattivitaistat objectDB = null;
	MercatiDattivitaistatId id = new MercatiDattivitaistatId();
	id.setIdcomune(ORMHelper.getIdcomune());
	id.setFkcodiceattivitaistat(entity.getId().getFkcodiceattivitaistat());
	id.setFkidposteggio(entity.getPosteggio().getId().getCodice());
	id.setFkcodicemercato(entity.getMercato().getId().getCodice());
	objectDB = mercatiDattivitaistatDAO.findById(id);
	if (objectDB != null && objectDB.getId().getFkcodiceattivitaistat() != null) {
	    _ivs.add(new InvalidValue("mercadid.service_error.duplicate_codice_attivita", null, null, entity.getAttivita().getIstat(), null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }
}
