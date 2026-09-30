package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempiFoT;

@Repository
public class TempiFoTDAOImpl extends BaseDAOImpl<TempiFoT, PkId> implements TempiFoTDAO {

    private TempiFoDDAO tempiFoDDAO;

    @Autowired
    public void setTempiFoDDAO(TempiFoDDAO tempiFoDDAO) {

	this.tempiFoDDAO = tempiFoDDAO;
    }

    @Override
    public Class<TempiFoT> getEntityClass() {

	return TempiFoT.class;
    }

    @Override
    public void deleteById(Integer idTempot) {

	if (idTempot == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteById senza passare il riferimento della testata");
	}
	this.tempiFoDDAO.deleteByIdTestata(idTempot);
	String sql = "delete  " + // 
		"from " + // 
		" tempi_fo_t " + // 
		"where  " + // 
		" idcomune = ? and " + // 
		" id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(TempiFoT.class)
		.addSynchronizedEntityClass(AlberoprocTempi.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTempot);
	query.executeUpdate();
    }
}
