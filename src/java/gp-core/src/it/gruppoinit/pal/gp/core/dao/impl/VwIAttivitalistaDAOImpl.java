package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.VwIAttivitalistaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIAttivitalista;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

/**
 * 
 * @author
 */
@Repository
public class VwIAttivitalistaDAOImpl extends BaseDAOImpl<VwIAttivitalista, PkId> implements VwIAttivitalistaDAO {

    @Override
    public Class<VwIAttivitalista> getEntityClass() {

	return VwIAttivitalista.class;
    }

    @Override
    public List<VwIAttivitalista> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Integer> findBydenominazione(String denominazione, String[] software, boolean checkAttiva, Integer firstResult, Integer maxResult) {

	List<Integer> elencoIdAttivita = new ArrayList<Integer>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equalsIgnoreCase("denominazione", denominazione));
	if (software != null) {
	    fr.addFilterField(FilterUtils.in("software", software, String.class));
	}
	if (checkAttiva) {
	    fr.addFilterField(FilterUtils.equals("attiva", Integer.valueOf(1), Integer.class));
	}
	ft.addRestriction(fr);
	List<VwIAttivitalista> elenco = super.findByFilterTable(ft, firstResult, maxResult);
	for (VwIAttivitalista attivita : elenco) {
	    elencoIdAttivita.add(attivita.getId().getCodice());
	}
	return elencoIdAttivita;
    }

    @Override
    public List<Integer> findByLocalizzazione(Istanzestradario localizzazione, String[] software, boolean checkAttiva, Integer firstResult,
	    Integer maxResult) {

	if (localizzazione == null || localizzazione.getStradario() == null || localizzazione.getStradario().getId() == null) {
	    throw new IllegalArgumentException("Impossibile richiamare il metodo findByLocalizzazione senza passare l'id di uno stradario valido");
	}
	List<Integer> elencoIdAttivita = new ArrayList<Integer>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (software != null) {
	    fr.addFilterField(FilterUtils.in("software", software, String.class));
	}
	if (StringUtils.isNotBlank(localizzazione.getCivico())) {
	    fr.addFilterField(FilterUtils.equals("civico", localizzazione.getCivico(), String.class));
	}
	if (EntityUtils.getNestedProperty(localizzazione.getStradariocolore(), "id.codicecolore") != null
		&& StringUtils.isNotBlank(localizzazione.getStradariocolore().getId().getCodicecolore())) {
	    fr.addFilterField(FilterUtils.equals("stradariocoloreId", localizzazione.getStradariocolore().getId().getCodicecolore(),
		    "istanza.istanzestradarios", String.class));
	}
	fr.addFilterField(FilterUtils.equals("id.codice", localizzazione.getStradario().getId().getCodice(), "stradario", Integer.class));
	if (checkAttiva) {
	    fr.addFilterField(FilterUtils.equals("attiva", Integer.valueOf(1), Integer.class));
	}
	ft.addRestriction(fr);
	List<VwIAttivitalista> elenco = super.findByFilterTable(ft, firstResult, maxResult);
	for (VwIAttivitalista attivita : elenco) {
	    elencoIdAttivita.add(attivita.getId().getCodice());
	}
	return elencoIdAttivita;
    }
}
