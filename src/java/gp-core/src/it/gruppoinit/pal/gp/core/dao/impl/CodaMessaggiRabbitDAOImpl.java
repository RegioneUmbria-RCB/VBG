package it.gruppoinit.pal.gp.core.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.CodaMessaggiRabbitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CodaMessaggiRabbit;
import it.gruppoinit.pal.gp.core.domain.CodaMessaggiRabbitId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class CodaMessaggiRabbitDAOImpl extends BaseDAOImpl<CodaMessaggiRabbit, CodaMessaggiRabbitId> implements CodaMessaggiRabbitDAO {

    @Override
    public Class<CodaMessaggiRabbit> getEntityClass() {

	return CodaMessaggiRabbit.class;
    }

    @Override
    public int countByUidIstanza(String idcomune, String uuidPratica) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("uuidIstanza", uuidPratica, String.class));
	ft.addRestriction(fr);
	return countRecord(ft);
    }
}
