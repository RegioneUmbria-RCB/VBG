package it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommedilizieMassiveD;
import it.gruppoinit.pal.gp.core.domain.CommedilizieMassiveT;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ComunicazioneCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.DettaglioRigaCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaDettagliCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaTestataCommissioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@Repository
public class CommissioniComunicazioniMassiveDAOImpl extends BaseDAOImpl<CommedilizieMassiveT, PkId> implements ICommissioniComunicazioniMassiveDAO {

    private static final Logger log = LoggerFactory.getLogger(CommissioniComunicazioniMassiveDAOImpl.class);

    @Override
    public void collegaCommissioniAComunicazioni(int idTestata, int idCommissioni) {

	CommedilizieMassiveT commedilizieMassiveT = new CommedilizieMassiveT();
	commedilizieMassiveT.setMassiveTestata((MassiveTestata) getById(MassiveTestata.class, new PkId(idTestata)));
	commedilizieMassiveT.setCommissioniedilizieT((CommissioniedilizieT) getById(CommissioniedilizieT.class, new PkId(idCommissioni)));
	saveEntity(commedilizieMassiveT);
    }

    @Override
    public void collegaDettaglioCommissioniADettaglioComunicazioni(int idDettaglioComunicazione, int idAppelloCommissioni) {

	CommedilizieMassiveD commedilizieMassiveD = new CommedilizieMassiveD();
	commedilizieMassiveD.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(idDettaglioComunicazione)));
	commedilizieMassiveD.setCommedilizieAppello((CommedilizieAppello) getById(CommedilizieAppello.class, new PkId(idAppelloCommissioni)));
	saveEntity(commedilizieMassiveD);
    }

    @Override
    public List<DettaglioRigaCommissione> getDettagli(FiltriRicercaDettagliCommissioni filtri) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	ComunicazioniCommissioniDettaglioQueryHelper queryHelper = new ComunicazioniCommissioniDettaglioQueryHelper(sessimpl, filtri);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveD.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(DettaglioRigaCommissione.class));
	List<DettaglioRigaCommissione> ret = new ArrayList<DettaglioRigaCommissione>();
	List<DettaglioRigaCommissione> list = q.list();
	for (DettaglioRigaCommissione dettaglioRigaCommissione : list) {
	    String mail = "";
	    if (dettaglioRigaCommissione.getCodiceAmministrazione() != null) {
		Amministrazioni amm = (Amministrazioni) this.getById(Amministrazioni.class,
			new PkId(dettaglioRigaCommissione.getCodiceAmministrazione()));
		mail = getMailOPec(filtri.getSceltaTipoMailAnagrafeEnum(), amm.getEmail(), amm.getPec());
	    } else if (dettaglioRigaCommissione.getCodiceAnagrafe() != null) {
		Anagrafe an = (Anagrafe) this.getById(Anagrafe.class, new PkId(dettaglioRigaCommissione.getCodiceAnagrafe()));
		mail = getMailOPec(filtri.getSceltaTipoMailAnagrafeEnum(), an.getEmail(), an.getPec());
	    } else if (dettaglioRigaCommissione.getCodiceResponsabile() != null) {
		Responsabili respo = (Responsabili) this.getById(Responsabili.class, new PkId(dettaglioRigaCommissione.getCodiceResponsabile()));
		mail = getMailOPec(filtri.getSceltaTipoMailAnagrafeEnum(), respo.getEmail(), null);
	    } else {
		throw new RuntimeException("Riga non valida, non sono presenti anagrafiche, amministrazioni o responsabili");
	    }
	    if (StringUtils.isBlank(mail) && filtri.isEscludiAnagraficheSenzaMail()) {
		continue;
	    }
	    dettaglioRigaCommissione.setMail(mail);
	    ret.add(dettaglioRigaCommissione);
	}
	return ret;
    }

    private String getMailOPec(SceltaTipoMailAnagrafeEnum sceltaTipoMailAnagrafeEnum, String email, String pec) {

	switch (sceltaTipoMailAnagrafeEnum) {
	case PEC_O_MAIL:
	    return StringUtils.defaultString(pec, email);
	case SOLO_MAIL:
	    return email;
	case SOLO_PEC:
	    return pec;
	default:
	    break;
	}
	return null;
    }

    @Override
    public ComunicazioneCommissione getComunicazioneCommissioni(FiltriRicercaTestataCommissioni filtri) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	ComunicazioneCommissioniQueryHelper queryHelper = new ComunicazioneCommissioniQueryHelper(sessimpl, filtri);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(ComunicazioneCommissione.class));
	return (ComunicazioneCommissione) q.list().get(0);
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioComunicazione(int idDettaglioComunicazione) {

	String sql = this.sqlGetCodicecomuneAndSoftware();
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglioComunicazione);
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    private String sqlGetCodicecomuneAndSoftware() {

	StringBuilder sql = new StringBuilder();
	sql.append("SELECT istanze.software as software, istanze.codicecomune as codicecomune ");
	sql.append(" FROM commedilizie_massive_d");
	sql.append("  INNER JOIN commedilizie_appello ON commedilizie_massive_d.idcomune = commedilizie_appello.idcomune");
	sql.append("    AND commedilizie_massive_d.fkid_appello = commedilizie_appello.id");
	sql.append("  INNER JOIN commedilizie_appello_pratiche ON commedilizie_appello_pratiche.idcomune = commedilizie_appello.idcomune");
	sql.append("    AND commedilizie_appello_pratiche.fk_appello = commedilizie_appello.id");
	sql.append("  INNER JOIN commissioniedilizie_r ON commissioniedilizie_r.idcomune = commedilizie_appello_pratiche.idcomune");
	sql.append("  AND commissioniedilizie_r.id = commedilizie_appello_pratiche.fk_commedilizier");
	sql.append("  INNER JOIN movimenti ON movimenti.idcomune = commissioniedilizie_r.idcomune");
	sql.append("  AND movimenti.codicemovimento = commissioniedilizie_r.codicemovimento");
	sql.append("  INNER JOIN istanze ON istanze.idcomune = movimenti.idcomune");
	sql.append("   AND istanze.codiceistanza = movimenti.codiceistanza");
	sql.append(" WHERE");
	sql.append("  commedilizie_massive_d.idcomune = ?");
	sql.append("  AND commedilizie_massive_d.fkid_massive_d = ?");
	sql.append(" GROUP BY");
	sql.append("  istanze.codicecomune,istanze.software");
	return sql.toString();
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareAndComunePerDettaglioCommissione(Integer idCommissione) {

	String sql = this.sqlGetCodicecomuneAndSoftware(true);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idCommissione);
	q.setResultTransformer(Transformers.aliasToBean(SoftwareComuneDataBean.class));
	return q.list();
    }

    private String sqlGetCodicecomuneAndSoftware(boolean isTestata) {

	StringBuilder sql = new StringBuilder();
	sql.append("SELECT istanze.software as software, istanze.codicecomune as codicecomune ");
	sql.append(" FROM commissioniedilizie_r");
	sql.append("  INNER JOIN commedilizie_appello_pratiche ON commissioniedilizie_r.idcomune = commedilizie_appello_pratiche.idcomune");
	sql.append("  AND commissioniedilizie_r.id = commedilizie_appello_pratiche.fk_commedilizier");
	sql.append("  INNER JOIN movimenti ON movimenti.idcomune = commissioniedilizie_r.idcomune");
	sql.append("  AND movimenti.codicemovimento = commissioniedilizie_r.codicemovimento");
	sql.append("  INNER JOIN istanze ON istanze.idcomune = movimenti.idcomune");
	sql.append("   AND istanze.codiceistanza = movimenti.codiceistanza");
	sql.append(" WHERE");
	sql.append("  commissioniedilizie_r.idcomune = ?");
	sql.append("  AND commissioniedilizie_r.codicecommissione = ?");
	sql.append(" GROUP BY");
	sql.append("  istanze.codicecomune,istanze.software");
	return sql.toString();
    }

    @Override
    public boolean sonoPresentiComunicazioni(Integer idCommissione) {

	String hql = "Select count(*) from CommedilizieMassiveT a where a.id.idcomune=? and a.commissioniedilizieTId=? ";
	Object[] values = new Object[] { ORMHelper.getIdcomune(), idCommissione };
	Long ris = ((Long) getHibernateTemplate().find(hql, values).get(0)).longValue();
	return ris.intValue() > 0;
    }

    @Override
    public boolean exists(Integer idTestata) {

	if (idTestata == null) {
	    throw new IllegalArgumentException(
		    "Impossibile verificare se esiste una comunicazione per le commissioni senza passare l'id della comunicazione");
	}
	String sql = "select count(id) as conteggio from commedilizie_massive_t where idcomune = ? and fkid_testata = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieMassiveT.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idTestata);
	query.addScalar("conteggio", Hibernate.INTEGER);
	return new Integer(query.uniqueResult().toString()) > 0;
    }

    @Override
    public CommedilizieMassiveT findByIdTestata(int idTestata) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("massiveTestata.id.codice", idTestata, Integer.class));
	ft.addRestriction(fr);
	List<CommedilizieMassiveT> massive = this.findByFilterTable(ft);
	if (massive == null || massive.isEmpty()) {
	    return new CommedilizieMassiveT();
	}
	if (massive.size() > 1) {
	    throw new RuntimeException(
		    "Caso anomalo: la comunicazione massiva con id " + idTestata + " è collegata a " + massive.size() + " commissioni edilizie");
	}
	return massive.get(0);
    }
}
