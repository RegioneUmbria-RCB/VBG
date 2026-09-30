package it.gruppoinit.pal.gp.core.features.istanze.metadati;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadati;
import it.gruppoinit.pal.gp.core.domain.IstanzeMetadatiId;

@Repository
public class IstanzeMetadatiDAOImpl extends BaseDAOImpl<IstanzeMetadati, IstanzeMetadatiId> implements IIstanzeMetadatiDAO {

    @Override
    public void deleteByIstanza(Integer codiceIstanza) {

	Query q = getSession().createQuery("delete from IstanzeMetadati imd where imd.id.idcomune=? and imd.id.codiceistanza=?");
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.executeUpdate();
    }

    @Override
    public Class<IstanzeMetadati> getEntityClass() {

	return IstanzeMetadati.class;
    }
}
