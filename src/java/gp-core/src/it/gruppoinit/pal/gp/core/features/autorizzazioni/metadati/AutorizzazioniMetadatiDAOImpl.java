package it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati;

import org.hibernate.SQLQuery;
import org.opensaml.artifact.InvalidArgumentException;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniMetadati;

@Repository
public class AutorizzazioniMetadatiDAOImpl extends BaseDAOImpl<AutorizzazioniMetadati, AutorizzazioniMetadatiId>
	implements AutorizzazioniMetadatiDAO {

    @Override
    public Class<AutorizzazioniMetadati> getEntityClass() {

	return AutorizzazioniMetadati.class;
    }

    @Override
    public void deleteByIdAutorizzazioni(Integer codice) {

	if (codice == null) {
	    throw new InvalidArgumentException("Impossibile richiamare la cancellazione dei metadati senza aver passato l'id dell'autorizzazione");
	}
	String sql = "delete from autorizzazioni_metadati where idcomune = ? and fkidautorizzazione = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(AutorizzazioniMetadati.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codice);
	query.executeUpdate();
    }
}
