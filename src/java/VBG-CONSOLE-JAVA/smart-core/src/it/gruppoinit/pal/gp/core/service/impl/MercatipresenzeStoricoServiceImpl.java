package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatiDService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTService;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatipresenzeStoricoServiceImpl extends BaseServiceImpl<MercatipresenzeStorico, PkId> implements MercatipresenzeStoricoService {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeStoricoServiceImpl.class);
    private MercatipresenzeStoricoDAO mercatipresenzeStoricoDAO;

    @Autowired
    public void setMercatipresenzeStoricoDAO(MercatipresenzeStoricoDAO mercatipresenzeStoricoDAO) {

	this.mercatipresenzeStoricoDAO = mercatipresenzeStoricoDAO;
    }

    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private AnagrafeService anagrafeService;

    @Override
    public void delete(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	mercatipresenzeStoricoDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<MercatipresenzeStorico> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findAll(null, null);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeStorico findById(PkId id) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    mercatipresenzeStoricoDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    mercatipresenzeStoricoDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    protected Class<MercatipresenzeStorico> getEntityClass() {

	return MercatipresenzeStorico.class;
    }

    @Override
    public List<Integer> findAnniDaStorico() {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findAnniDaStorico();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Set<Integer> findAnni() {

	// §§§BEGIN§§§
	Set<Integer> listaAnni = new LinkedHashSet<Integer>();
	List<Integer> anniDaStorico = this.findAnniDaStorico();
	List<MercatipresenzeT> anniDaCalendari = mercatipresenzeTService.findAnniMercatiPresenti();
	for (MercatipresenzeT mercatipresenzeT : anniDaCalendari) {
	    listaAnni.add(mercatipresenzeT.getAnno().intValue());
	}
	for (Integer anno : anniDaStorico) {
	    listaAnni.add(anno);
	}
	return listaAnni;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeStorico> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeStoricoDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }
}
