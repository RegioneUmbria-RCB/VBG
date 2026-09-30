/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ContiDAO;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

/**
 * @author lucap
 * 
 */
@Repository
public class ContiDAOImpl extends BaseDAOImpl<Conti, PkId> implements ContiDAO {

    private Logger log = LoggerFactory.getLogger(ContiDAOImpl.class);

    @Override
    public Class<Conti> getEntityClass() {

	return Conti.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Conti> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Conti> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	return (List<Conti>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Conti> findAllActive() {

	return this.findContiAttivi(null);
    }

    @SuppressWarnings("unchecked")
    public Conti findContoAttivoByIdCausaleOnere(Integer idCausaleOnere) throws InvalidConfigurationException {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.createAlias("tipiCausaliOneriDettagli", "_tcod");
	det.add(Restrictions.eq("_tcod.id.fkCausaliId", idCausaleOnere));
	det.add(Restrictions.eq("_tcod.flagAttivo", Boolean.TRUE));
	Calendar c = Calendar.getInstance();
	c.setTime(new Date());
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	Date dataScadenza = c.getTime();
	det.add(Restrictions.or(Restrictions.isNull("dataScadenza"), Restrictions.ge("dataScadenza", dataScadenza)));
	List<Conti> conti = getHibernateTemplate().findByCriteria(det);
	if (conti.isEmpty()) {
	    return null;
	}
	if (conti.size() > 1) {
	    Tipicausalioneri causale = getById(Tipicausalioneri.class, idCausaleOnere);
	    if (causale == null) {
		throw new InvalidConfigurationException("Causale onere con codice " + idCausaleOnere + " non trovata nella base dati");
	    }
	    StringBuilder messaggio = new StringBuilder();
	    messaggio.append("Trovata una configurazione errata per la causale onere ").append(causale.getCoDescrizione()).append(" [")
		    .append(causale.getId()).append("].\n");
	    messaggio.append("Sono presenti più conti configurati nella tabella TIPICAUSALIONERIDETTAGLIO: ");
	    for (Conti conto : conti) {
		messaggio.append("\n - ").append(conto.getDescrizione()).append(" [").append(conto.getId()).append("]; ");
	    }
	    if (log.isErrorEnabled()) {
		log.error(messaggio.toString());
	    }
	    throw new InvalidConfigurationException(messaggio.toString());
	}
	return conti.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Conti> findContiAttivi(Date dataRiferimento) {

	Calendar dataRif = Calendar.getInstance();
	dataRif.setTime(dataRiferimento == null ? new Date() : dataRiferimento);
	dataRif.set(Calendar.HOUR_OF_DAY, 0);
	dataRif.set(Calendar.MINUTE, 0);
	dataRif.set(Calendar.SECOND, 0);
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add( //
		Restrictions.or( //
			Restrictions.isNull("dataScadenza"), // 
			Restrictions.ge("dataScadenza", dataRif.getTime())) //
	);
	return getHibernateTemplate().findByCriteria(det);
    }
}
