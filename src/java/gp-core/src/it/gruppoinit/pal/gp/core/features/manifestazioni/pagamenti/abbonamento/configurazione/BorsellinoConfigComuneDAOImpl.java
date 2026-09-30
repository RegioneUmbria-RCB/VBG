package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigComune;
import it.gruppoinit.pal.gp.core.domain.BorsellinoInformative;
import it.gruppoinit.pal.gp.core.domain.BorsellinoRicariche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

@Repository
public class BorsellinoConfigComuneDAOImpl extends BaseDAOImpl<BorsellinoConfigComune, PkId> implements IBorsellinoConfigComuneDAO {

    private IBorsellinoInformativeDAO borsellinoInformativeDAO;
    private IBorsellinoRicaricheDAO borsellinoRicaricheDAO;

    @Autowired
    public void setBorsellinoInformativeDAO(IBorsellinoInformativeDAO borsellinoInformativeDAO) {

	this.borsellinoInformativeDAO = borsellinoInformativeDAO;
    }

    @Autowired
    public void setBorsellinoRicaricheDAO(IBorsellinoRicaricheDAO borsellinoRicaricheDAO) {

	this.borsellinoRicaricheDAO = borsellinoRicaricheDAO;
    }

    @Override
    public Class<BorsellinoConfigComune> getEntityClass() {

	return BorsellinoConfigComune.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BorsellinoConfigComune findByCodiceComune(String comune) {

	if (StringUtils.isBlank(comune)) {
	    throw new IllegalArgumentException("Impossibile utilizzare la ricerca per codice comune senza passare il comune");
	}
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("comune.codicecomune", comune));
	List<BorsellinoConfigComune> list = getHibernateTemplate().findByCriteria(criteria, 0, 2);
	if (list.isEmpty()) {
	    return null;
	}
	if (list.size() > 1) {
	    throw new InvalidOperationException("Sono state trovate più configurazioni riferite al codice comune " + comune);
	}
	return list.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public DettaglioComuneModel findAbbonamentoConfigComune(String codiceComune) {

	if (StringUtils.isBlank(codiceComune)) {
	    throw new IllegalArgumentException("Impossibile ricavare il dettaglio per codice comune senza passare il comune");
	}
	String sql = "select" + // 
		" comuni.codicecomune as codice, " +
		" comuni.comune," + // 
		" borsellino_config_comune.msg_nodopag_nondisp as msgRicaricaNonDisponibile " + //
		"from" + //
		" comuniassociati" + //
		"  left join borsellino_config_comune on comuniassociati.codicecomune = borsellino_config_comune.codicecomune " + //
		"  inner join comuni on comuniassociati.codicecomune = comuni.codicecomune " + //
		"where" + //
		" comuniassociati.idcomune = ? and" + //
		" comuniassociati.codicecomune = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoConfigComune.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, codiceComune);
	query.addScalar("codice", Hibernate.STRING);
	query.addScalar("comune", Hibernate.STRING);
	query.addScalar("msgRicaricaNonDisponibile", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(DettaglioComuneModel.class));
	List<DettaglioComuneModel> list = query.list();
	if (list.isEmpty()) {
	    return new DettaglioComuneModel();
	}
	if (list.size() > 1) {
	    throw new InvalidConfigurationException("Sono state trovate più configurazioni per il comune con codice " + codiceComune);
	}
	DettaglioComuneModel model = list.get(0);
	model.getInformative().addAll(this.findInformativeConfigComune(codiceComune));
	model.getRicariche().addAll(this.findRicaricheConfigComune(codiceComune));
	return model;
    }

    private List<DettaglioInformativaModel> findInformativeConfigComune(String codiceComune) {

	List<BorsellinoInformative> informative = this.borsellinoInformativeDAO.findByCodiceComune(codiceComune);
	if (informative == null || informative.isEmpty()) {
	    return new ArrayList<DettaglioInformativaModel>();
	}
	List<DettaglioInformativaModel> dettagli = new ArrayList<DettaglioInformativaModel>();
	for (BorsellinoInformative informativa : informative) {
	    dettagli.add(DettaglioInformativaModel.fromBorsellinoInformative(informativa));
	}
	return dettagli;
    }

    private List<DettaglioRicaricaModel> findRicaricheConfigComune(String codiceComune) {

	List<BorsellinoRicariche> ricariche = this.borsellinoRicaricheDAO.findByCodiceComune(codiceComune);
	if (ricariche == null || ricariche.isEmpty()) {
	    return new ArrayList<DettaglioRicaricaModel>();
	}
	List<DettaglioRicaricaModel> dettagli = new ArrayList<DettaglioRicaricaModel>();
	for (BorsellinoRicariche ricarica : ricariche) {
	    dettagli.add(DettaglioRicaricaModel.fromBorsellinoRicariche(ricarica));
	}
	return dettagli;
    }
}
