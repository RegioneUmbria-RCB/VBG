package it.gruppoinit.pal.gp.pay.dao.impl;

import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayDocumentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDocumenti;

@Repository
public class PayDocumentiDAOImpl extends BaseDAOImpl<PayDocumenti, PkId> implements PayDocumentiDAO {

    @Override
    public Class<PayDocumenti> getEntityClass() {

	return PayDocumenti.class;
    }

}
