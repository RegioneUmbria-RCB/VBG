package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClmenuDAO;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ClmenuDAOImpl extends BaseDAOImpl<Clmenu, Integer> implements ClmenuDAO {

    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    public Class<Clmenu> getEntityClass() {

	return Clmenu.class;
    }

    // E' stato fatto l'override del metodo findByExample
    // perchè il metodo di BaseDAOImpl aggiunge idcomune come restriction
    @Override
    @SuppressWarnings("unchecked")
    public List<Clmenu> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<Clmenu>) getHibernateTemplate().findByExample(new Clmenu(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Clmenu>) getHibernateTemplate().findByExample(new Clmenu());
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Clmenu> findFirstLevelMenu() {

	String hqlQuery = "FROM Clmenu _clmenu WHERE length(_clmenu.menulink) = ? order by _clmenu.menulink asc";
	Object[] values = new Object[] { Long.valueOf(1) };
	List<Clmenu> dynList = (List<Clmenu>) getHibernateTemplate().find(hqlQuery, values);
	return dynList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Clmenu> findMenu(String parentLevelCode, int length) {

	String hqlQuery = "FROM Clmenu _clmenu WHERE  length(_clmenu.menulink) = ? and _clmenu.menulink like ? order by _clmenu.menulink asc";
	Object[] values = new Object[] { Long.valueOf(length), parentLevelCode + "%" };
	List<Clmenu> dynList = (List<Clmenu>) getHibernateTemplate().find(hqlQuery, values);
	return dynList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Clmenu> findTreeBySoftware(String pSoftware, boolean v2) {

	if (StringUtils.isBlank(pSoftware)) {
	    throw new IllegalArgumentException("Il parametro pSoftware è obbligatorio");
	}
	String column_link = "_clmenu.menulink";
	if(v2){
	    column_link = "_clmenu.menulinkV2";
	}
	String hqlQuery = "FROM Clmenu _clmenu WHERE  (not " + column_link + " is null  ) and  (length(" + column_link
		+ " ) = ? or _clmenu.software in (?,?)) ";
	if (!isEnterprise()) {
	    hqlQuery += " and not _clmenu.tipoFunzionalita=? ";
	}
	hqlQuery += " order by " + column_link + " asc";
	Object[] values = null;
	if (!isEnterprise()) {
	    values = new Object[] { Long.valueOf(1), "*", pSoftware, WebConstants.CLMENU_JAVA_TIPO_FUNZIONALITA_ENTERPRISE };
	} else {
	    values = new Object[] { Long.valueOf(1), "*", pSoftware };
	}
	List<Clmenu> dynList = (List<Clmenu>) getHibernateTemplate().find(hqlQuery, values);
	return dynList;
    }

    private boolean isEnterprise() {

	return verticalizzazioniService.isInstallazioneEnterprise();
    }
}
