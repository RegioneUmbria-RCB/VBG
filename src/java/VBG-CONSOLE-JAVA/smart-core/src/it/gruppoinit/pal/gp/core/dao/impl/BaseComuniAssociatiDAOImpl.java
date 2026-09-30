package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Classe che i DAOImpl che Utilizzano la separazione dei record per comune nelle installazioni <b>Comuni Associati</b>
 * devono estendere al posto di BaseDAOImpl
 * 
 * @author Riccardo Bocci
 * 
 * @param <E>
 *            Oggetto di Dominio
 * @param <F>
 *            Oggetto che rappresenta la chiave primaria dell'oggetto di dominio
 */
public abstract class BaseComuniAssociatiDAOImpl<E, F extends Serializable> extends BaseDAOImpl<E, F> {

    private ComuniDAO comuniDAO;

    @Autowired
    public void setComuniDAO(ComuniDAO comuniDAO) {

	this.comuniDAO = comuniDAO;
    }

    @Override
    public void update(E entity) {

	setCodiceComune(entity);
	super.update(entity);
    }

    @Override
    public void insert(E entity) {

	setCodiceComune(entity);
	super.insert(entity);
    }

    /**
     * Il metodo deve essere implementato per mettere il comune di default
     * 
     * @see AreeDAOImpl#setCodiceComune(it.gruppoinit.pal.gp.core.domain.Aree)
     * @param entity
     *            l'oggetto di dominio che contiene la proprietà che indica il comune di appartenenza dei record
     */
    protected abstract void setCodiceComune(E entity);

    /**
     * controlla se un oggetto comuni è stato settato
     * 
     * @param comune
     *            è la proprietà dell'entity che si intende controllare
     * @return
     */
    protected boolean checkIfCodiceComuneIsSet(Comuni comune) {

	if (comune == null) {
	    return false;
	}
	if (StringUtils.isBlank(comune.getCodicecomune())) {
	    return false;
	}
	return true;
    }

    /**
     * funzione che torna il comune di default da associare alla tabella se la proprietà è nulla
     * 
     * @return
     */
    protected Comuni getDefaultComune() {

	String codiceComune = ORMHelper.getIdcomune();
	Comuni comune = comuniDAO.findById(codiceComune);
	return comune;
    }
}
