package it.gruppoinit.pal.gp.core.features.movimenti.metadati;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadati;
import it.gruppoinit.pal.gp.core.domain.MovimentiMetadatiId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class MovimentiMetadatiDAOImpl extends BaseDAOImpl<MovimentiMetadati, MovimentiMetadatiId> implements IMovimentiMetadatiDAO {

    @Override
    public Class<MovimentiMetadati> getEntityClass() {

	return MovimentiMetadati.class;
    }

    @Override
    public String getUuid(Integer codiceMovimento) {

	MovimentoMetadatoUUID uu = new MovimentoMetadatoUUID();
	List<MovimentiMetadati> mds = findByMetadato(codiceMovimento, uu);
	if (mds.isEmpty()) {
	    return null;
	}
	if (mds.size() > 1) {
	    throw new InvalidConfigurationException(
		    "Il movimento " + new PkId(codiceMovimento) + " ha più di un metadato " + uu.getChiave() + " configurato");
	}
	return mds.get(0).getValore();
    }

    private List<MovimentiMetadati> findByMetadato(Integer codiceMovimento, IMovimentoMetadato md) {

	return findByMetadato(codiceMovimento, md.getChiave());
    }

    private List<MovimentiMetadati> findByMetadato(Integer codiceMovimento, String chiave) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.codicemovimento", codiceMovimento, Integer.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", chiave, String.class));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public void deleteByCodiceMovimento(Integer codiceMovimento) {

	SQLQuery q = getSession().createSQLQuery("delete from MOVIMENTI_METADATI where idcomune=:idcomune and codicemovimento=:codicemovimento");
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("codicemovimento", codiceMovimento);
	q.addSynchronizedEntityClass(MovimentiMetadati.class).addSynchronizedEntityClass(Movimenti.class).executeUpdate();
	flush();
    }

    @Override
    public Movimenti findMovimentoByUuId(String uuidMovimento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", ORMHelper.getIdcomune(), String.class));
	fr.addFilterField(FilterUtils.equals("id.chiave", MovimentoMetadatoUUID.NOME_METADATO, String.class));
	fr.addFilterField(FilterUtils.equals("valore", uuidMovimento, String.class));
	ft.addRestriction(fr);
	List<MovimentiMetadati> lista = findByFilterTable(ft);
	if (lista == null || lista.isEmpty()) {
	    return new Movimenti();
	}
	if (lista.size() > 1) {
	    throw new RuntimeException(
		    "Impossibile identificare univocamente un movimento con " + MovimentoMetadatoUUID.NOME_METADATO + " = " + uuidMovimento);
	}
	return lista.get(0).getMovimento();
    }

    @Override
    public boolean isMetadatoPresente(Integer codiceMovimento, String nomeMetadato) {

	return !findByMetadato(codiceMovimento, nomeMetadato).isEmpty();
    }
}
