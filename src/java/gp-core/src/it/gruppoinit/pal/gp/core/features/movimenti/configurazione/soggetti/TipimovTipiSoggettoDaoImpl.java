package it.gruppoinit.pal.gp.core.features.movimenti.configurazione.soggetti;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.TipimovTipiSoggetto;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipisoggetto;

@SuppressWarnings("rawtypes")
@Repository
public class TipimovTipiSoggettoDaoImpl extends BaseDAOImpl implements ITipimovTipiSoggettoDao {

    @SuppressWarnings("unchecked")
    @Override
    public void elimina(int id) {

	delete(TipimovTipiSoggetto.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int aggiungi(String idTipoMovimento, int idTipoSoggetto) {

	// Verifica se esistono già records a parità di tipo movimento e tipo soggetto
	String sql = "SELECT Count(*) FROM tipimov_tipisoggetto WHERE idcomune=? AND fk_idtipomovimento=? AND fk_idtiposoggetto=?";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, idTipoMovimento);
	query.setInteger(2, idTipoSoggetto);
	if (((Number) query.uniqueResult()).longValue() > 0) {
	    throw new RuntimeException("Soggetto già configurato");
	}
	TipimovTipiSoggetto cls = new TipimovTipiSoggetto();
	Tipisoggetto tipoSoggetto = (Tipisoggetto) getById(Tipisoggetto.class, idTipoSoggetto);
	Tipimovimento tipoMovimento = (Tipimovimento) getHibernateTemplate().get(Tipimovimento.class, new TipimovimentoId(idTipoMovimento));
	cls.setTipisoggetto(tipoSoggetto);
	cls.setTipimovimento(tipoMovimento);
	insert(cls);
	return cls.getId().getCodice();
    }

    @Override
    public Class getEntityClass() {

	// TODO Auto-generated method stub
	return null;
    }
}
