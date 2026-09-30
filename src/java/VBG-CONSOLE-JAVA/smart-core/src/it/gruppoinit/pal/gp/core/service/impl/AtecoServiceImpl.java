package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AtecoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Ateco;
import it.gruppoinit.pal.gp.core.domain.web.AtecoChildrenCommand;
import it.gruppoinit.pal.gp.core.domain.web.AtecoCommand;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AtecoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class AtecoServiceImpl extends BaseServiceImpl<Ateco, Integer> implements AtecoService {

    private AtecoDAO atecoDAO;

    @Autowired
    public void setAtecoDAO(AtecoDAO atecoDAO) {

	this.atecoDAO = atecoDAO;
    }

    @Override
    protected Class<Ateco> getEntityClass() {

	return Ateco.class;
    }

    @Override
    public List<Ateco> findAll(Integer firstResult, Integer maxResult) {

	return atecoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Ateco entity) {

	if (validateEntity(entity)) {
	    atecoDAO.insert(entity);
	}
    }

    @Override
    public Ateco findById(Integer id) {

	return atecoDAO.findById(id);
    }

    @Override
    public void update(Ateco entity) {

	if (validateEntity(entity)) {
	    atecoDAO.update(entity);
	}
    }

    @Override
    public void delete(Ateco entity) {

	if (isDeleteAllowed(entity)) {
	    atecoDAO.delete(entity);
	}
    }

    @Override
    public List<AtecoCommand> findAtecoHierarchy(String codeStartsWith, boolean inspectProperties) {

	List<Ateco> atecos = null;
	if (StringUtils.isNotBlank(StringUtils.defaultString(codeStartsWith).trim())) {
	    atecos = this.findByCodice(codeStartsWith);
	} else {
	    atecos = atecoDAO.findAll(null, null);
	}
	List<AtecoCommand> resultList = new ArrayList<AtecoCommand>();
	for (Ateco ateco : atecos) {
	    AtecoCommand atecoCommand = new AtecoCommand();
	    if (ateco.getAteco() == null) {
		atecoCommand.setRoot(1);
	    } else {
		atecoCommand.setRoot(0);
	    }
	    atecoCommand.setId(ateco.getId());
	    atecoCommand.setTitolo(ateco.getTitolo());
	    atecoCommand.setCodice(ateco.getCodice());
	    atecoCommand.setCodiceDescrizione(StringUtils.defaultString(ateco.getCodice()).concat(" - ")
		    .concat(StringUtils.defaultString(ateco.getTitolo())));
	    atecoCommand.setChildren(inspectHierarchy(ateco.getAtecos()));
	    resultList.add(atecoCommand);
	}
	return resultList;
    }

    private List<Ateco> findByCodice(String codeStartsWith) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.startsWith("codice", codeStartsWith));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("codice"));
	return atecoDAO.findByFilterTable(ft);
    }

    private List<AtecoChildrenCommand> inspectHierarchy(Set<Ateco> atecos) {

	List<AtecoChildrenCommand> childrens = new ArrayList<AtecoChildrenCommand>();
	for (Ateco ateco : atecos) {
	    AtecoChildrenCommand atecoChildrenCommand = new AtecoChildrenCommand();
	    atecoChildrenCommand.set_reference(ateco.getId());
	    childrens.add(atecoChildrenCommand);
	}
	return childrens.size() > 0 ? childrens : null;
    }
}
