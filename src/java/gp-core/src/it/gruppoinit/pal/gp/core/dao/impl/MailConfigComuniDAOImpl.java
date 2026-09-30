package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MailConfigComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MailConfigComuni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MailConfigComuniDAOImpl extends BaseDAOImpl<MailConfigComuni, PkId> implements MailConfigComuniDAO {

    @Override
    public Class<MailConfigComuni> getEntityClass() {

	return MailConfigComuni.class;
    }

    @Override
    public List<MailConfigComuni> findByMailConfigId(Integer id) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mailConfigId", id, Integer.class));
	ft.addRestriction(fr);
	List<MailConfigComuni> mailConfigComuni = this.findByFilterTable(ft, null, null);
	return mailConfigComuni;
    }

    @Override
    public Set<MailConfigComuni> findListaByResponsabile(Responsabili responsabili) {

	Set<Responsabilicomuni> responsabilicomuni = responsabili.getResponsabilicomunis();
	String[] codiciComuni = new String[responsabilicomuni.size()];
	int i = 0;
	for (Responsabilicomuni responsabilicomune : responsabilicomuni) {
	    codiciComuni[i] = responsabilicomune.getComune().getCodicecomune();
	    i++;
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("codicecomune", codiciComuni, String.class));
	ft.addRestriction(fr);
	List<MailConfigComuni> m = this.findByFilterTable(ft, null, null);
	Set<MailConfigComuni> mailConfigComuni = new HashSet<MailConfigComuni>(m);
	return mailConfigComuni;
    }
}
