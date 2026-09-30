package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class BorsellinoDAOImpl extends BaseDAOImpl<Borsellino, PkId> implements IBorsellinoDAO {

    @Override
    public Class<Borsellino> getEntityClass() {

	return Borsellino.class;
    }

    @Override
    public List<SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia(Set<Integer> auts, BigDecimal soglia) {

	String sql = "SELECT  " + //
		     "AUTORIZZAZIONI.ID as id, " + //
		     " SUM(BORSELLINO_MOVIMENTI.IMPORTO) AS totale, " + //
		     " CASE WHEN SUM(BORSELLINO_MOVIMENTI.IMPORTO) - ? < 0 THEN 1 ELSE 0 END AS sottosoglia" + //
		     " FROM AUTORIZZAZIONI " + //
		     "    LEFT JOIN BORSELLINO_AUTORIZZAZIONI ON  " + //
		     "    AUTORIZZAZIONI.IDCOMUNE = BORSELLINO_AUTORIZZAZIONI.IDCOMUNE AND  " + //
		     "    AUTORIZZAZIONI.ID = BORSELLINO_AUTORIZZAZIONI.FKID_AUTORIZZAZIONI " + //
		     "    LEFT JOIN BORSELLINO ON  " + //
		     "    BORSELLINO_AUTORIZZAZIONI.IDCOMUNE = BORSELLINO.IDCOMUNE AND  " + //
		     "    BORSELLINO_AUTORIZZAZIONI.FKID_BORSELLINO = BORSELLINO.ID AND  " + //
		     "    BORSELLINO.STATO = ? " + //
		     "    LEFT JOIN BORSELLINO_MOVIMENTI ON " + //
		     "    BORSELLINO.IDCOMUNE = BORSELLINO_MOVIMENTI.IDCOMUNE AND " + //
		     "    BORSELLINO.ID = BORSELLINO_MOVIMENTI.FKID_BORSELLINO  " + //
		     "WHERE " + //
		     "AUTORIZZAZIONI.IDCOMUNE = ?  " + //
		     "AND AUTS_Q_MARKS " + //
		     "GROUP BY  " + //
		     "AUTORIZZAZIONI.ID ";
	String qMarks = "";
	int num = auts.size();
	if (num == 0) {
	    return new ArrayList<SituazioneBorsellinoPerSoglia>();
	}
	if (num < 1000) {
	    String qm = StringUtils.repeat("?,", num);
	    qm = qm.substring(0, qm.length() - 1);
	    qMarks = " AUTORIZZAZIONI.ID  in  (" + qm + ")";
	} else {
	    Double cicli = Double.valueOf(num) / 1000;
	    int cicliDaMille = cicli.intValue();
	    int resto = num - (cicliDaMille * 1000);
	    qMarks += " ( 1=2 ";
	    for (int i = 0; i < cicliDaMille; i++) {
		String qm = StringUtils.repeat("?,", 1000);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or AUTORIZZAZIONI.ID  in  (" + qm + ")";
	    }
	    if (resto > 0) {
		String qm = StringUtils.repeat("?,", resto);
		qm = qm.substring(0, qm.length() - 1);
		qMarks += " or AUTORIZZAZIONI.ID  in (" + qm + ")";
	    }
	    qMarks += ")";
	}
	sql = sql.replace("AUTS_Q_MARKS", qMarks);
	SQLQuery q = getSession().createSQLQuery(sql);
	int pos = 0;
	q.setBigDecimal(pos++, soglia);
	q.setString(pos++, StatoBorsellinoEnum.ATTIVO.name());
	q.setString(pos++, ORMHelper.getIdcomune());
	for (Integer id : auts) {
	    q.setInteger(pos++, id);
	}
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("sottosoglia", Hibernate.BOOLEAN);
	q.addScalar("totale", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(SituazioneBorsellinoPerSoglia.class));
	return (List<SituazioneBorsellinoPerSoglia>) q.list();
    }

    @Override
    public List<Borsellino> findByCodiceAnagrafe(Integer codiceAnagrafe) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafeID", codiceAnagrafe, Integer.class));
	ft.addOrder(FilterUtils.orderAsc("dataCreazione"));
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	ft.addRestriction(fr);
	return findByFilterTable(ft);
    }

    @Override
    public Borsellino findBorsellinoAttivoByCodiceAnagrafe(Integer codiceAnagrafe) throws BorsellinoException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafeID", codiceAnagrafe, Integer.class));
	fr.addFilterField(FilterUtils.equals("stato", StatoBorsellinoEnum.ATTIVO.name(), String.class));
	ft.addRestriction(fr);
	List<Borsellino> results = findByFilterTable(ft);
	if (results.isEmpty()) {
	    return null;
	}
	if (results.size() > 1) {
	    throw new BorsellinoException("Trovati più borsellini attivi per il codice anagrafe " + codiceAnagrafe);
	}
	return results.get(0);
    }

    @Override
    public Borsellino findByUuid(String uuidBorsellino) throws BorsellinoException {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("uuid", uuidBorsellino, String.class));
	ft.addRestriction(fr);
	List<Borsellino> results = findByFilterTable(ft);
	if (results.isEmpty()) {
	    throw new BorsellinoException("Nessun borsellino trovato per il riferimento" + uuidBorsellino);
	}
	if (results.size() > 1) {
	    throw new BorsellinoException("Trovati più borsellini per il riferimento" + uuidBorsellino);
	}
	return results.get(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AbbonamentoTabellaModelCompleta> findListaBorsellini(RicercaBorselliniRequest filtri) {

	StatoPagamentoType[] statiPosizioniChiusePositivamente = new StatiPosizioniDebitorieConverter().getStatiPosizioniChiusePositivamente();
	String sql = "SELECT  " + //
		     " b.id AS id,  " + //
		     " b.DESCRIZIONE AS DESCRIZIONE,  " + //
		     " b.STATO AS stato,  " + //
		     " b.DATACREAZIONE AS datacreazione,  " + //
		     " b.FKID_ANAGRAFE AS idanagrafe,  " + //
		     " a.NOMINATIVO AS cognome,  " + //
		     " a.NOME AS nome,  " + //
		     " a.TIPOANAGRAFE AS tipoanagrafe,  " + //
		     " a.CODICEFISCALE AS cf,  " + //
		     " a.PARTITAIVA AS piva,  " + //
		     " f.FORMAGIURIDICA  AS formagiuridica,  " + //
		     " COALESCE(SUM(bm.IMPORTO),0) - " + //
		     "  ( SELECT COALESCE (SUM(bm2.IMPORTO),0) AS PAGAMENTI " + //
		     "    FROM borsellino_movimenti BM2  " + //
		     "             INNER JOIN dett_posizione_debitoria ON  " + //
		     "     bm2.idcomune=dett_posizione_debitoria.idcomune AND " + //
		     "     bm2.FKID_DETTPOSIZIONEDEBITORIA=dett_posizione_debitoria.id  " + //
		     "            WHERE  " + //
		     "                bm2.IDCOMUNE = bM.IDCOMUNE AND  " + //
		     "                bm2.FKID_BORSELLINO=BM.FKID_BORSELLINO AND " + //
		     "                bm2.tipo=:tipomovimentoricarica AND " + //			
		     "   not dett_posizione_debitoria.STATO IN (";
	StringBuilder statiParam = new StringBuilder(":statopagamentoiniziale");
	for (int i = 0; i < statiPosizioniChiusePositivamente.length; i++) {
	    statiParam.append(",:statopagamento" + i);
	}
	sql += statiParam;
	sql += ")  " + //
	       "   ) " + //
	       " AS creditoResiduo  " + //
	       " FROM borsellino b  " + //
	       " INNER JOIN anagrafe a ON  " + //
	       " b.IDCOMUNE = a.IDCOMUNE  " + //
	       " AND b.FKID_ANAGRAFE = a.CODICEANAGRAFE  " + //
	       " LEFT JOIN formegiuridiche f ON  " + //
	       " a.IDCOMUNE = f.IDCOMUNE  " + //
	       " AND a.FORMAGIURIDICA = f.CODICEFORMAGIURIDICA  " + //
	       " LEFT JOIN borsellino_movimenti bm ON  " + //
	       " b.IDCOMUNE = bm.IDCOMUNE  " + //
	       " AND b.ID = bm.FKID_BORSELLINO  " + //
	       " WHERE  " + //
	       " b.IDCOMUNE = :idcomune ";
	if (!StringUtils.defaultString(filtri.getStato()).equalsIgnoreCase("0")) {
	    sql += "and b.stato = :stato ";
	}
	if (filtri.getIdAutorizzazione() != null) {
	    sql += " and exists (select 1 from borsellino_autorizzazioni aut where aut.idcomune=b.idcomune and aut.fkid_borsellino=b.id and aut.id=:id_autorizzazione)";
	}
	// sql += " group by b.id " + // come fa a funzionare?????
	sql += " group by b.id , " + // 
	       "b.DESCRIZIONE, " + // 
	       "b.STATO , " + // 
	       "b.DATACREAZIONE , " + // 
	       "b.FKID_ANAGRAFE , " + // 
	       "a.NOMINATIVO , " + // 
	       "a.NOME , " + // 
	       "a.TIPOANAGRAFE , " + // 
	       "a.CODICEFISCALE, " + // 
	       "a.PARTITAIVA , " + // 
	       "f.FORMAGIURIDICA , " + //
	       "bm.FKID_BORSELLINO,bM.IDCOMUNE";
	sql += " order by a.nominativo asc,a.nome asc, b.DATACREAZIONE desc";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString("tipomovimentoricarica", TipoEnum.RICARICA.name());
	q.setString("statopagamentoiniziale", "NON_VALIDO_PER_LA_QUERY");
	for (int i = 0; i < statiPosizioniChiusePositivamente.length; i++) {
	    q.setString("statopagamento" + i, statiPosizioniChiusePositivamente[i].name());
	}
	q.setString("idcomune", ORMHelper.getIdcomune());
	if (!StringUtils.defaultString(filtri.getStato()).equalsIgnoreCase("0")) {
	    q.setString("stato", filtri.getStato());
	}
	if (filtri.getIdAutorizzazione() != null) {
	    q.setInteger("id_autorizzazione", filtri.getIdAutorizzazione());
	}
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("stato", Hibernate.STRING);
	q.addScalar("dataCreazione", Hibernate.DATE);
	q.addScalar("idAnagrafe", Hibernate.INTEGER);
	q.addScalar("cognome", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("tipoanagrafe", Hibernate.STRING);
	q.addScalar("cf", Hibernate.STRING);
	q.addScalar("piva", Hibernate.STRING);
	q.addScalar("formagiuridica", Hibernate.STRING);
	q.addScalar("creditoResiduo", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(AbbonamentoTabellaModelCompleta.class));
	return (List<AbbonamentoTabellaModelCompleta>) q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniModel> findAllAutorizzazioni() {

	String sql = "select b.id as idBorsellino," + // 
		     "ba.fkid_autorizzazioni as id, " + // 
		     "a.autoriznumero as numeroAutorizzazione " + // 
		     "from " + // 
		     "borsellino b " + // 
		     "inner join borsellino_autorizzazioni ba " + // 
		     "on b.idcomune=ba.idcomune " + // 
		     "and b.id = ba.fkid_borsellino " + // 
		     "inner join autorizzazioni a " + // 
		     "on ba.idcomune = a.idcomune " + // 
		     "and ba.fkid_autorizzazioni=a.id " + // 
		     "where " + // 
		     "b.idcomune= ? " + // 
		     "group by " + // 
		     "b.id, ba.fkid_autorizzazioni,a.autoriznumero ";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("numeroAutorizzazione", Hibernate.STRING);
	q.addScalar("idBorsellino", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(AutorizzazioniModel.class));
	return (List<AutorizzazioniModel>) q.list();
    }

    @Override
    public List<Integer> findBorselliniPerAutorizzazione(String autorizzazione) {

	String sql = "SELECT b.id AS idBorsellino " + //
		     " FROM " + //
		     " borsellino b " + //
		     " INNER JOIN borsellino_autorizzazioni ba " + //
		     " ON b.idcomune=ba.idcomune " + //
		     " AND b.id = ba.fkid_borsellino " + //
		     " INNER JOIN autorizzazioni a " + //
		     " ON ba.idcomune = a.idcomune " + //
		     " AND ba.fkid_autorizzazioni=a.id " + //
		     " WHERE" + //
		     " A.idcomune= ? AND (a.autoriznumero) LIKE ?" + //
		     " GROUP BY b.id";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, "%" + autorizzazione.toUpperCase() + "%");
	q.addScalar("idBorsellino", Hibernate.INTEGER);
	return (List<Integer>) q.list();
    }
}
