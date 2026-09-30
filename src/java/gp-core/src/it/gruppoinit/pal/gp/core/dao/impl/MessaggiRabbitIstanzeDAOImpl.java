package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.MessaggiRabbitIstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitIstanze;
import it.gruppoinit.pal.gp.core.domain.MessaggiRabbitIstanzeId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MessaggiRabbitIstanzeDAOImpl extends BaseDAOImpl<MessaggiRabbitIstanze, MessaggiRabbitIstanzeId> implements MessaggiRabbitIstanzeDAO {

    @Override
    public Class<MessaggiRabbitIstanze> getEntityClass() {

	return MessaggiRabbitIstanze.class;
    }

    @Override
    public int countByUidIstanza(String uuIdIstanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.uuIdIstanza", uuIdIstanza, String.class));
	ft.addRestriction(fr);
	return countRecord(ft);
    }
}
