package it.gruppoinit.pal.gp.core.features.firmadigitale.firmaremota.configurazione;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametri;
import it.gruppoinit.pal.gp.core.domain.FirmeRemoteParametriId;

@Repository
public class FirmeRemoteParametriDAOImpl extends BaseDAOImpl<FirmeRemoteParametri, FirmeRemoteParametriId> implements FirmeRemoteParametriDAO {

    @Override
    public Class<FirmeRemoteParametri> getEntityClass() {

	return FirmeRemoteParametri.class;
    }

    @Override
    public void deleteByIdFirma(Integer idFirmaRemota) {

	String sql = "delete from firmeremote_parametri where idcomune = ? and fk_idfirmaremota = ?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(FirmeRemoteParametri.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idFirmaRemota, new IntegerType());
	q.executeUpdate();
    }
}