package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.CommedilizieTipopareriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommediliziePareriTmov;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.commissioni.model.CommissioniEdiliziePareriMovimentiModel;
import it.gruppoinit.pal.gp.core.filters.FilterTable;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class CommedilizieTipopareriDAOImpl extends BaseDAOImpl<CommedilizieTipopareri, PkId> implements CommedilizieTipopareriDAO {

    @Override
    public Class<CommedilizieTipopareri> getEntityClass() {

	return CommedilizieTipopareri.class;
    }

    @Override
    public List<CommedilizieTipopareri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<CommissioniEdiliziePareriMovimentiModel> findMovimentiConfigurati(Integer codiceTipologiaParere) {

	String sql = "SELECT " + //
		     "	commedilizie_pareri_tmov.fk_commedpareri_id AS codiceTipologiaParere," + //
		     "	software.codice AS codicesoftware ," + //
		     "	software.descrizione AS software," + //
		     "	tipimovimento.tipomovimento AS tipoMovimento," + //
		     "	tipimovimento.movimento AS descrizioneMovimento	" + //
		     "	 FROM commedilizie_pareri_tmov INNER JOIN software ON " + //Guarda sto a sgui
		     "	commedilizie_pareri_tmov.software=software.codice" + //
		     "	INNER JOIN tipimovimento ON " + //
		     "	tipimovimento.idcomune=commedilizie_pareri_tmov.idcomune AND" + //
		     "	tipimovimento.tipomovimento=commedilizie_pareri_tmov.tipomovimento" + //
		     "	WHERE " + //
		     "	commedilizie_pareri_tmov.idcomune=:idcomune AND commedilizie_pareri_tmov.fk_commedpareri_id=:id " + //
		     " ORDER BY software.moduloopzionale,software.descrizione";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.addScalar("codiceTipologiaParere", Hibernate.INTEGER);
	q.addScalar("codicesoftware", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("tipoMovimento", Hibernate.STRING);
	q.addScalar("descrizioneMovimento", Hibernate.STRING);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("id", codiceTipologiaParere);
	q.setResultTransformer(Transformers.aliasToBean(CommissioniEdiliziePareriMovimentiModel.class));
	return q.list();
    }

    @Override
    public boolean findConfigurazioniPerSoftware(Integer codiceTipologiaParere, String software) {

	String sql = "SELECT software " + //
		     " FROM commedilizie_pareri_tmov WHERE " + //
		     "	commedilizie_pareri_tmov.idcomune=:idcomune AND commedilizie_pareri_tmov.fk_commedpareri_id=:id " + //
		     " and commedilizie_pareri_tmov.software=:software";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.addScalar("software", Hibernate.STRING);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("id", codiceTipologiaParere);
	q.setString("software", software);
	return q.list().size() > 0;
    }

    @Override
    public void insertMovimentoPerSoftware(Integer codiceTipologiaParere, String software, String tipomovimento) {

	String sql = "insert into commedilizie_pareri_tmov (idcomune,fk_commedpareri_id,software,tipomovimento) values (:idcomune,:fk_commedpareri_id,:software,:tipomovimento)";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("fk_commedpareri_id", codiceTipologiaParere);
	q.setString("software", software);
	q.setString("tipomovimento", tipomovimento);
	q.executeUpdate();
    }

    @Override
    public void eliminaMovimentoPerSoftware(Integer codiceTipologiaParere, String software) {

	String sql = "delete from commedilizie_pareri_tmov where idcomune=:idcomune and fk_commedpareri_id=:fk_commedpareri_id and software=:software";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("fk_commedpareri_id", codiceTipologiaParere);
	q.setString("software", software);
	q.executeUpdate();
    }

    @Override
    public String findTipomovPerTipologiaParereECommissioniEdilizieR(Integer codiceTipoparere, Integer codiceCommissioniEdilizieR) {

	String sql = "SELECT " + //
		     "  commedilizie_pareri_tmov.tipomovimento AS tipomovimento " + //
		     "FROM " + //
		     "  commedilizie_pareri_tmov " + //
		     "  INNER JOIN " + //
		     "    software " + //
		     "    ON " + //
		     "      commedilizie_pareri_tmov.software=software.codice " + //
		     "WHERE " + //
		     "  commedilizie_pareri_tmov.idcomune               = :idcomune " + //
		     "  AND commedilizie_pareri_tmov.fk_commedpareri_id = :idparere " + //
		     "  AND " + //
		     "  ( " + //
		     "    commedilizie_pareri_tmov.software = :softwarett " + //
		     "    OR commedilizie_pareri_tmov.software IN " + //
		     "    ( " + //
		     "      SELECT " + //
		     "        software " + //
		     "      FROM " + //
		     "        commissioniedilizie_r " + //
		     "        INNER JOIN " + //
		     "          movimenti " + //
		     "          ON " + //
		     "            MOVIMENTI.IDCOMUNE           =commissioniedilizie_r.IDCOMUNE " + //
		     "            AND MOVIMENTI.CODICEMOVIMENTO=commissioniedilizie_r.CODICEMOVIMENTO " + //
		     "        INNER JOIN " + //
		     "          istanze " + //
		     "          ON " + //
		     "            movimenti.idcomune         =istanze.idcomune " + //
		     "            AND movimenti.codiceistanza=istanze.codiceistanza " + //
		     "      WHERE " + //
		     "        commissioniedilizie_r.idcomune=:idcomunecr " + //
		     "        AND commissioniedilizie_r.id  =:idcr " + //
		     "    ) " + //
		     "  ) " + //
		     "ORDER BY " + //
		     "  software.moduloopzionale DESC";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.addScalar("tipomovimento", Hibernate.STRING);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idparere", codiceTipoparere);
	q.setString("softwarett", WebConstants.SOFTWARE_TT);
	q.setString("idcomunecr", ORMHelper.getIdcomune());
	q.setInteger("idcr", codiceCommissioniEdilizieR);
	List<String> list = q.list();
	for (String primorecord : list) {
	    return primorecord;
	}
	return null;
    }

    @Override
    public void deleteMovimentiConfigurati(Integer codiceTipologiaParere) {

	String sql = "delete from commedilizie_pareri_tmov where idcomune=:idcomune and fk_commedpareri_id=:fk_commedpareri_id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("fk_commedpareri_id", codiceTipologiaParere);
	q.executeUpdate();
	flush();
    }

    @Override
    public List<CommedilizieTipopareri> findConfigurazioniPerTipomovimento(String tipoMovimento) {

	List<CommedilizieTipopareri> ret = new ArrayList<CommedilizieTipopareri>(0);
	String sql = "select fk_commedpareri_id as idtipoparere from commedilizie_pareri_tmov where idcomune=:idcomune  and tipomovimento=:tipomov";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommediliziePareriTmov.class);
	q.addScalar("idtipoparere", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setString("tipomov", tipoMovimento);
	List<Integer> list = q.list();
	for (Integer id : list) {
	    ret.add(this.findById(new PkId((id))));
	}
	return ret;
    }
}
