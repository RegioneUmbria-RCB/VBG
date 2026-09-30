package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoAutStorico;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BorsellinoAutStoricoDAOImpl extends BaseDAOImpl<BorsellinoAutStorico, PkId> implements IBorsellinoAutStoricoDAO {

    @Override
    public Class<BorsellinoAutStorico> getEntityClass() {
	return BorsellinoAutStorico.class;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<BorsellinoAutorizzazioniHistModel> findAutStoricoByBorsellino(Integer idborsellino){
	SQLQuery q = getSession().createSQLQuery("SELECT AUTORIZZAZIONI.ID AS id, AUTORIZZAZIONI.AUTORIZNUMERO AS numero, AUTORIZZAZIONI.AUTORIZDATA AS dataaut, "
		+ " BORSELLINO_AUT_STORICO.TIPO_OPERAZIONE AS tipooperazione, BORSELLINO_AUT_STORICO.DATA_OPERAZIONE AS dataoperazione "
		+ " FROM BORSELLINO_AUT_STORICO JOIN AUTORIZZAZIONI ON BORSELLINO_AUT_STORICO.IDCOMUNE = AUTORIZZAZIONI.IDCOMUNE AND BORSELLINO_AUT_STORICO.FKID_AUTORIZZAZIONI = AUTORIZZAZIONI.ID "
		+ " WHERE BORSELLINO_AUT_STORICO.IDCOMUNE = :idcomune AND BORSELLINO_AUT_STORICO.FKID_BORSELLINO = :idborsellino ORDER BY BORSELLINO_AUT_STORICO.DATA_OPERAZIONE DESC ");
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("numero", Hibernate.STRING);
	q.addScalar("dataaut", Hibernate.DATE);
	q.addScalar("tipooperazione", Hibernate.STRING);
	q.addScalar("dataoperazione", Hibernate.TIMESTAMP);
	
	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("idborsellino", idborsellino);
	
	q.setResultTransformer(Transformers.aliasToBean(BorsellinoAutorizzazioniHistModel.class));
	return q.list();
    }
        
}
