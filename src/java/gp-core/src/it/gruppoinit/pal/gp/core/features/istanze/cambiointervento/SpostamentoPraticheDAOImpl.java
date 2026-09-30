package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.IstanzeruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Istanze;

@SuppressWarnings("rawtypes")
@Repository
public class SpostamentoPraticheDAOImpl extends BaseDAOImpl implements ISpostamentoPraticheDAO {

    @Autowired
    private IstanzeruoliDAO ruoliDAO;
    @Autowired
    private Istanzedyn2modellitDAO dyn2ModelliTIstanzeDAO;

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Integer> findIstanzeDaSpostare(String idComune, Integer codiceInterventoOrigine) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare SpostamentoPraticheDAO.findIstanzeDaSpostare senza passare idComune");
	}
	if (codiceInterventoOrigine == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare SpostamentoPraticheDAO.findIstanzeDaSpostare senza passare un codiceInterventoOrigine");
	}
	StringBuilder sql = new StringBuilder("" +
		"select " +
		" codiceistanza " +
		"from " +
		"  istanze " +
		"where " +
		"  idcomune = ? and " +
		"  codiceinterventoproc = ? " +
		"order by " +
		"  codiceistanza desc");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(Istanze.class);
	int index = 0;
	query.setString(index, idComune);
	index++;
	query.setInteger(index, codiceInterventoOrigine);
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	return new HashSet<Integer>(query.list());
    }

    @Override
    public void spostaIntervento(String idComune, Integer codiceInterventoOrigine, Integer codiceInterventoDestinazione) {

	if (StringUtils.isBlank(idComune)) {
	    throw new IllegalArgumentException("Impossibile richiamare SpostamentoPraticheDAO.spostaIntervento senza passare idComune");
	}
	if (codiceInterventoOrigine == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare SpostamentoPraticheDAO.spostaIntervento senza passare un codiceInterventoOrigine");
	}
	if (codiceInterventoDestinazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare SpostamentoPraticheDAO.spostaIntervento senza passare un codiceInterventoDestinazione");
	}
	StringBuilder sql = new StringBuilder("" +
		"update " +
		" istanze " +
		"set " +
		"  codiceinterventoproc = ? " +
		"where " +
		"  idcomune = ? and " +
		"  codiceinterventoproc = ?");
	SQLQuery query = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(Istanze.class);
	int index = 0;
	query.setInteger(index, codiceInterventoDestinazione);
	index++;
	query.setString(index, idComune);
	index++;
	query.setInteger(index, codiceInterventoOrigine);
	query.executeUpdate();
    }

    @Override
    public void aggiungiRuoli(Set<Integer> elencoIstanze, Set<Integer> idRuoliDaAggiungere) {

	for (Integer codiceIstanza : elencoIstanze) {
	    this.ruoliDAO.delete(ORMHelper.getIdcomune(), codiceIstanza, idRuoliDaAggiungere);
	    this.ruoliDAO.insert(ORMHelper.getIdcomune(), codiceIstanza, idRuoliDaAggiungere);
	}
    }

    @Override
    public Set<Integer> schedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> idSchedeDaAggiungere) {

	return this.dyn2ModelliTIstanzeDAO.schedeMancanti(idComune, codiceIstanza, idSchedeDaAggiungere);
    }

    @Override
    public void insertSchedeMancanti(String idComune, Integer codiceIstanza, Set<Integer> elenco) {

	this.dyn2ModelliTIstanzeDAO.insert(idComune, codiceIstanza, elenco);
    }
}
