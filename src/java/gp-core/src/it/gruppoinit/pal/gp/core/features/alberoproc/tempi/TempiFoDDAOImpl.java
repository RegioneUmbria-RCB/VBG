package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempiFoD;

@Repository
public class TempiFoDDAOImpl extends BaseDAOImpl<TempiFoD, PkId> implements TempiFoDDAO {

    @Override
    public Class<TempiFoD> getEntityClass() {

	return TempiFoD.class;
    }

    @Override
    public void deleteByIdTestata(Integer idTempot) {

	if (idTempot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteByIdTestata senza passare il riferimento della testata");
	}
	String sql = "delete  " + // 
		"from " + // 
		" tempi_fo_d " + // 
		"where  " + // 
		" idcomune = ? and " + // 
		" fkid_tempi = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(TempiFoD.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTempot);
	query.executeUpdate();
    }
}
