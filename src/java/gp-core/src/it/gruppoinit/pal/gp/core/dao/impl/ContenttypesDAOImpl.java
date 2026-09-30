package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ContenttypesDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.ContenttypesId;
import it.gruppoinit.pal.gp.core.service.impl.RegistrazioniServiceImpl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ContenttypesDAOImpl extends BaseDAOImpl<Contenttypes, ContenttypesId> implements ContenttypesDAO {

    private static final Logger log = LoggerFactory.getLogger(RegistrazioniServiceImpl.class);
    private static String DEFAULT_MIME_TYPE = "text/plain";

    @SuppressWarnings("unchecked")
    @Override
    public String findMimeTypeByFileName(String nomeFile) {

	String estensioneFile = extractExtension(nomeFile);
	String risposta = "";
	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("id.ctExtension", "%;" + estensioneFile + ";%"));
	List<Contenttypes> risultatoQuery = getHibernateTemplate().findByCriteria(det);
	if (risultatoQuery == null || risultatoQuery.size() == 0) {
	    log.warn("Attenzione!! non è stata trovata la configurazione mimetype per il file: " + nomeFile + ". Assegnato il mimetype di Default"
		    + DEFAULT_MIME_TYPE);
	    risposta = DEFAULT_MIME_TYPE;
	} else {
	    if (risultatoQuery.size() > 1) {
		log.warn("Attenzione!! E' stata trovata più di una configurazione mimetype per il file: " + nomeFile
			+ ". Assegnato il mimetype di Default" + DEFAULT_MIME_TYPE);
		risposta = DEFAULT_MIME_TYPE;
	    } else {
		risposta = ((Contenttypes) risultatoQuery.get(0)).getId().getCtMimetype();
	    }
	}
	if (log.isDebugEnabled()) {
	    log.debug("il Mimetype trovato per il file (" + nomeFile + ") è " + risposta);
	}
	return risposta;
    }

    @Override
    public void delete(Contenttypes entity) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contenttypes> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	if (null != firstResult && null != maxResult) {
	    return (List<Contenttypes>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Contenttypes>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    public List<Contenttypes> findAll(Integer firstResult, Integer maxResult, DAOEnum whereClauseMandatoryFields, String orderProperty,
	    DAOOrderTypeEnum orderType) {

	throw new NotImplementedException();
    }

    @Override
    public Contenttypes findById(ContenttypesId id) {

	throw new NotImplementedException();
    }

    @Override
    public Class<Contenttypes> getEntityClass() {

	return Contenttypes.class;
    }

    @Override
    public void insert(Contenttypes entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Contenttypes entity) {

	throw new NotImplementedException();
    }

    private String extractExtension(String nomeFile) {

	if (nomeFile == null || nomeFile.equals("")) {
	    throw new RuntimeException("Attenzione!! il nome del file non può essere nullo o vuoto");
	}
	return nomeFile.substring(nomeFile.lastIndexOf('.') + 1);
    }
}
