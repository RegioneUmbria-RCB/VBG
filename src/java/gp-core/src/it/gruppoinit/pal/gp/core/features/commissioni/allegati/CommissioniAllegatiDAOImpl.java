package it.gruppoinit.pal.gp.core.features.commissioni.allegati;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAllFirma;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdilizieAllegatiFirmeModel;

@SuppressWarnings("rawtypes")
@Repository
public class CommissioniAllegatiDAOImpl extends BaseDAOImpl implements ICommissioniAllegatiDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public long countFirmePerAllegato(Integer idAllegato) {

	Query query = getSession().createQuery(
		"select count(allegati.id.codice) from CommedilizieAllFirma allegati where allegati.id.idcomune=:idcomune and allegati.fkCommedilizieAllegatiId=:fk_allegato");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("fk_allegato", idAllegato);
	Long count = (Long) query.uniqueResult();
	return count == null ? 0 : count.longValue();
    }

    @Override
    public List<CommissioniEdilizieAllegatiFirmeModel> findFirmePerAllegato(Integer idAllegato) {

	Query query = getSession().createQuery(
		"from CommedilizieAllFirma allegati where allegati.id.idcomune=:idcomune and allegati.fkCommedilizieAllegatiId=:fk_allegato order by allegati.dataFirma");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("fk_allegato", idAllegato);
	List<CommedilizieAllFirma> list = query.list();
	List<CommissioniEdilizieAllegatiFirmeModel> result = new ArrayList<CommissioniEdilizieAllegatiFirmeModel>(list.size());
	for (CommedilizieAllFirma entity : list) {
	    result.add(CommissioniEdilizieAllegatiFirmeModel.fromEntity(entity));
	}
	return result;
    }

    @Override
    public void eliminaFirmePerAllegato(Integer idAllegato) {

	Query query = getSession().createQuery(
		"from CommedilizieAllFirma allegati where allegati.id.idcomune=:idcomune and allegati.fkCommedilizieAllegatiId=:fk_allegato order by allegati.dataFirma");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("fk_allegato", idAllegato);
	List<CommedilizieAllFirma> list = query.list();
	for (CommedilizieAllFirma firma : list) {
	    delete(firma);
	}
    }
}
