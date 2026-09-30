package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglio;
import it.gruppoinit.pal.gp.core.domain.AssegnazioneGruppiDettaglioId;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

@Repository
public class AssegnazioneGruppiDettaglioDAOImpl extends BaseDAOImpl<AssegnazioneGruppiDettaglio, AssegnazioneGruppiDettaglioId>
	implements IAssegnazioneGruppiDettaglioDAO {

    @Override
    public Class<AssegnazioneGruppiDettaglio> getEntityClass() {

	return AssegnazioneGruppiDettaglio.class;
    }

    @Override
    public List<IdentificativoDescrizioneBean> findIstanzeByResponsabileETestata(Integer codiceResp, Integer idTestata, String tipo) {

	String sql = " select " + // 
		" i.CODICEISTANZA as id, i.NUMEROISTANZA as descrizione " + // 
		" from " + // 
		" assegnazione_gruppi_dettaglio agd " + // 
		" inner join assegnazione_gruppi_testata agt on " + // 
		" agt.IDCOMUNE = agd.IDCOMUNE " + // 
		" and agt.ID = agd.IDTESTATA " + // 
		" inner join istanze i on " + // 
		" i.IDCOMUNE = agd.IDCOMUNE " + // 
		" and agd.CODICEISTANZA = i.CODICEISTANZA " + // 
		" inner join responsabili r on " + // 
		" r.IDCOMUNE = i.IDCOMUNE ";
	if (tipo.equals(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " and r.CODICERESPONSABILE =i.CODICERESPONSABILEPROC ";
	} else {
	    sql += " and r.CODICERESPONSABILE =i.CODICEISTRUTTORE ";
	}
	sql += " where " + // 
		" agd.IDCOMUNE = ? " + // 
		" and agd.IDTESTATA = ? " + //
		" and agt.AMBITO = ?";
	if (tipo.equals(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += "  and i.codiceresponsabileproc = ? ";
	} else {
	    sql += " and i.CODICEISTRUTTORE = ? ";
	}
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.setString(2, tipo);
	query.setInteger(3, codiceResp);
	query.addScalar("descrizione", Hibernate.STRING);
	query.addScalar("id", Hibernate.INTEGER);
	query.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IdentificativoDescrizioneBean.class));
	return query.list();
    }

    @Override
    public List<ChiaveValoreBean<Integer, Integer>> countPresenzeRespPerAssegnazioneTestata(Integer idTestata, String tipo) {

	String sql = " select ";
	if (tipo.equalsIgnoreCase(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " i.CODICERESPONSABILEPROC as chiave, ";
	} else {
	    sql += " i.CODICEISTRUTTORE as chiave, ";
	}
	sql += " count(*) as valore " + // 
		" from assegnazione_gruppi_dettaglio agd " + // 
		" inner join assegnazione_gruppi_testata agt on " + // 
		" agt.IDCOMUNE = agd.IDCOMUNE " + // 
		" and agt.ID = agd.IDTESTATA " + // 
		" inner join istanze i on " + // 
		" i.IDCOMUNE = agd.IDCOMUNE " + // 
		" and agd.CODICEISTANZA = i.CODICEISTANZA " + // 
		" inner join responsabili r on " + // 
		" r.IDCOMUNE = i.IDCOMUNE " + // 
		" and r.CODICERESPONSABILE = ";
	if (tipo.equalsIgnoreCase(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " i.CODICERESPONSABILEPROC  ";
	} else {
	    sql += " i.CODICEISTRUTTORE  ";
	}
	sql += " where " + // 
		" agd.IDCOMUNE = ? " + // 
		" and agd.IDTESTATA = ? " + // 
		" group by ";
	if (tipo.equalsIgnoreCase(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " i.CODICERESPONSABILEPROC  ";
	} else {
	    sql += " i.CODICEISTRUTTORE  ";
	}
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.addScalar("chiave", Hibernate.INTEGER);
	query.addScalar("valore", Hibernate.INTEGER);
	query.setResultTransformer(Transformers.aliasToBean(ChiaveValoreBean.class));
	return query.list();
    }

    @Override
    public List<Integer> findIstanzeAperte(Integer idTestata, Integer codiceResponsabile, String tipo) {

	String sql = " select i.codiceistanza as codiceistanza  " + // 
		" from " + // 
		" assegnazione_gruppi_dettaglio agd " + // 
		" inner join  " + // 
		" istanze i on " + // 
		" i.idcomune = agd.idcomune " + // 
		" and i.codiceistanza = agd.codiceistanza " + // 
		" inner join statiistanza s on " + // 
		" s.idcomune = i.idcomune " + // 
		" and s.software = i.software " + // 
		" and s.codicestato = i.chiusura " + // 
		" inner join responsabili r on " + // 
		" r.idcomune = i.idcomune " + // 
		" and r.codiceresponsabile = ";
	if (tipo.equalsIgnoreCase(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " i.CODICERESPONSABILEPROC ";
	} else {
	    sql += " i.CODICEISTRUTTORE  ";
	}
	sql += " where " + // 
		" i.idcomune = ? " + // 
		" and i.software = ? " + // 
		" and s.fkcodcomportamento = ? " + // 
		" and agd.idtestata = ? ";
	if (tipo.equalsIgnoreCase(ResponsabileIstanzaEnum.RESPONSABILE_PROCEDIMENTO.name())) {
	    sql += " and i.codiceresponsabileproc = ? ";
	} else {
	    sql += " and i.CODICEISTRUTTORE = ? ";
	}
	sql += " order by i.data asc ";
	SQLQuery query = getSession().createSQLQuery(sql);
	query.setString(0, ORMHelper.getIdcomune());
	query.setString(1, ORMHelper.getSoftware());
	query.setInteger(2, 0);
	query.setInteger(3, idTestata);
	query.setInteger(4, codiceResponsabile);
	query.addScalar("codiceistanza", Hibernate.INTEGER);
	return query.list();
    }
}
