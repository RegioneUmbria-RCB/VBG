package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.commissioni.cds.dao;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.CommedilizieAppello;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.segnaposto.TipoFileEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.datisostiuzionesegnaposto.IUsefulDataForPlaceholderReplacement;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.FormatUtils;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.OdtConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.RtfConstants;
import it.gruppoinit.pal.gp.core.features.segnaposto.legacy.shared.IndirizzoDestinatario;

@Repository
public class CommissioniToCdsDAOImpl extends BaseDAOImpl implements CommissioniToCdsDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    private List<CdsSoggettiInvitatiBean> getSoggettiCommissionePerIstanza(IUsefulDataForPlaceholderReplacement data,
	    TIPO_SOGGETTO_INVITATO anagrafica) {

	FilterFromUsefulData filter = new FilterFromUsefulData(data);
	Session session = getSession();
	String sql = "select  " + //
		     " commedilizie_appello.codiceanagrafe,  " + //
		     " commedilizie_appello.codiceamministrazione,  " + //
		     " commedilizie_appello.codiceresponsabile,  " + //
		     " anagrafe.nominativo, " + //
		     " anagrafe.nome, " + //
		     " amministrazioni.amministrazione, " + //
		     " responsabili.responsabile " + //
		     " from  " + //
		     " commedilizie_appello  " + //
		     " inner join commissioniedilizie_r on  " + //
		     " commissioniedilizie_r.idcomune=commedilizie_appello.idcomune and " + //
		     " commissioniedilizie_r.codicecommissione=commedilizie_appello.codicecommissione " + //
		     " inner join movimenti on  " + //
		     " movimenti.idcomune=commissioniedilizie_r.idcomune and " + //
		     " movimenti.codicemovimento=commissioniedilizie_r.codicemovimento " + //
		     " left join anagrafe on " + //
		     " anagrafe.idcomune=commedilizie_appello.idcomune and " + //
		     " anagrafe.codiceanagrafe=commedilizie_appello.codiceanagrafe " + //
		     " left join amministrazioni on " + //
		     " amministrazioni.idcomune=commedilizie_appello.idcomune and " + //
		     " amministrazioni.codiceamministrazione=commedilizie_appello.codiceamministrazione " + //
		     " LEFT JOIN responsabili ON  " + //
		     " responsabili.idcomune=commedilizie_appello.idcomune AND " + //
		     " responsabili.codiceresponsabile=commedilizie_appello.codiceresponsabile " + //
		     " where movimenti.idcomune=? "; //
	if (filter.getCodiceMovimento() != null) {
	    sql += " and movimenti.codicemovimento=? ";
	} else {
	    sql += " and movimenti.codiceistanza=? ";
	}
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppello.class);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("codiceamministrazione", Hibernate.INTEGER);
	q.addScalar("codiceresponsabile", Hibernate.INTEGER);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("amministrazione", Hibernate.STRING);
	q.addScalar("responsabile", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	if (filter.getCodiceMovimento() != null) {
	    q.setInteger(1, filter.getCodiceMovimento());
	} else {
	    q.setInteger(1, filter.getCodiceIstanza());
	}
	q.setResultTransformer(Transformers.aliasToBean(CdsSoggettiInvitatiBean.class));
	// POTREBBERO TORNARE PIù RECORD DI CDS PER ISTANZA il metodo SET rosolve l'univocità
	return q.list();
    }

    @Override
    public Set<String> findInvitatiCommissioniPerIstanza(IUsefulDataForPlaceholderReplacement data, TIPO_SOGGETTO_INVITATO anagrafica) {

	Set<String> ret = new TreeSet<String>();
	List<CdsSoggettiInvitatiBean> list = getSoggettiCommissionePerIstanza(data, anagrafica);
	for (CdsSoggettiInvitatiBean cdsSoggettiInvitatiBean : list) {
	    switch (anagrafica) {
	    case AMMINISTRAZIONE:
		if (cdsSoggettiInvitatiBean.getCodiceamministrazione() != null) {
		    ret.add(cdsSoggettiInvitatiBean.getAmministrazione().trim());
		}
		break;
	    case ANAGRAFICA_RESPONSABILI:
		if (cdsSoggettiInvitatiBean.getCodiceamministrazione() == null) {
		    ret.add(fromAnagrafeOResponsabile(cdsSoggettiInvitatiBean));
		}
		break;
	    }
	}
	return ret;
    }

    private String fromAnagrafeOResponsabile(CdsSoggettiInvitatiBean invitato) {

	if (StringUtils.isNotBlank(invitato.getResponsabile())) {
	    return invitato.getResponsabile().trim();
	}
	String nominativo = StringUtils.defaultString(invitato.getNominativo());
	if (StringUtils.isNotBlank(invitato.getNome())) {
	    nominativo = nominativo + " " + invitato.getNome();
	}
	return nominativo;
    }

    @Override
    public Set<String> findIndirizziInvitatiCommissioniPerIstanza(IUsefulDataForPlaceholderReplacement data, TIPO_SOGGETTO_INVITATO tiposoggetto,
	    TipoFileEnum tipoFile) {

	Set<String> ret = new TreeSet<String>();
	List<CdsSoggettiInvitatiBean> list = getSoggettiCommissionePerIstanza(data, tiposoggetto);
	for (CdsSoggettiInvitatiBean inv : list) {
	    switch (tiposoggetto) {
	    case AMMINISTRAZIONE:
		aggiungiIndirizzoPerAmministrazione(ret, inv, tipoFile);
		break;
	    case ANAGRAFICA_RESPONSABILI:
		aggiungiIndirizzoPerAnagrafe(ret, inv, tipoFile);
		break;
	    }
	}
	return ret;
    }

    private void aggiungiIndirizzoPerAnagrafe(Set<String> ret, CdsSoggettiInvitatiBean inv, TipoFileEnum tipoFile) {

	if (inv.getCodiceamministrazione() != null) {
	    return;
	}
	if (inv.getCodiceamministrazione() == null && inv.getCodiceanagrafe() != null) {
	    StringBuffer sb = new StringBuffer();
	    Anagrafe an = (Anagrafe) this.getById(Anagrafe.class, inv.getCodiceanagrafe());
	    sb.append(FormatUtils.stringFormat(an.getNome()));
	    if (sb.length() > 0) {
		sb.append(" ");
	    }
	    sb.append(an.getNominativo());
	    IndirizzoDestinatario ind = new IndirizzoDestinatario();
	    ind.setNominativo(sb.toString());
	    ind.setIndirizzo(an.getIndirizzo());
	    ind.setCap(an.getCap());
	    ind.setCitta(an.getCitta());
	    ind.setProvincia(an.getProvincia());
	    ret.add(ind.buildIndirizzo(getSeparator(tipoFile)).toString());
	}
	if (inv.getCodiceresponsabile() != null) {
	    StringBuffer sb = new StringBuffer();
	    Responsabili an = (Responsabili) this.getById(Responsabili.class, inv.getCodiceresponsabile());
	    sb.append(FormatUtils.stringFormat(an.getResponsabile()));
	    IndirizzoDestinatario ind = new IndirizzoDestinatario();
	    ind.setNominativo(sb.toString());
	    ind.setIndirizzo(an.getIndirizzo());
	    ind.setCap(an.getCap());
	    ind.setCitta(an.getCitta());
	    ind.setProvincia(an.getProvincia());
	    ret.add(ind.buildIndirizzo(getSeparator(tipoFile)).toString());
	}
    }

    private void aggiungiIndirizzoPerAmministrazione(Set<String> ret, CdsSoggettiInvitatiBean inv, TipoFileEnum tipoFile) {

	if (inv.getCodiceamministrazione() != null) {
	    Amministrazioni amm = (Amministrazioni) this.getById(Amministrazioni.class, inv.getCodiceamministrazione());
	    IndirizzoDestinatario ind = new IndirizzoDestinatario();
	    ind.setNominativo(amm.getAmministrazione());
	    ind.setUfficio(amm.getUfficio());
	    ind.setIndirizzo(amm.getIndirizzo());
	    ind.setCap(amm.getCap());
	    ind.setCitta(amm.getCitta());
	    ind.setProvincia(amm.getProvincia());
	    ret.add(ind.buildIndirizzo(getSeparator(tipoFile)).toString());
	}
    }

    private String getSeparator(TipoFileEnum tipoFile) {

	switch (tipoFile) {
	case ODT:
	    return OdtConstants.ODT_CRLF;
	case RTF:
	    return RtfConstants.RTF_CRLF;
	}
	return " ";
    }

    @Override
    public CommissioniedilizieT findUltimaCommissionePerIstanza(IUsefulDataForPlaceholderReplacement data) {

	FilterFromUsefulData filter = new FilterFromUsefulData(data);
	Session session = getSession();
	String sql = "SELECT codicecommissione " + // 
		     " FROM movimenti " + //
		     " INNER JOIN commissioniedilizie_r ON " + // 
		     " commissioniedilizie_r.idcomune=movimenti.idcomune AND " + // 
		     " commissioniedilizie_r.codicemovimento=movimenti.codicemovimento " + // 
		     " WHERE movimenti.idcomune=? "; //
	if (filter.getCodiceMovimento() != null) {
	    sql += " AND movimenti.codicemovimento=? "; //
	} else {
	    sql += " AND movimenti.codiceistanza=? "; //
	}
	sql += " AND movimenti.data IS NOT NULL" + //
	       " ORDER BY movimenti.data DESC  "; //	
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommedilizieAppello.class);
	q.addScalar("codicecommissione", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	if (filter.getCodiceMovimento() != null) {
	    q.setInteger(1, filter.getCodiceMovimento());
	} else {
	    q.setInteger(1, filter.getCodiceIstanza());
	}
	q.setMaxResults(2);
	// POTREBBERO TORNARE PIù RECORD DI CDS PER ISTANZA il metodo SET rosolve l'univocità
	List<Integer> list = q.list();
	for (Integer codiceCommissione : list) {
	    return (CommissioniedilizieT) this.getById(CommissioniedilizieT.class, codiceCommissione);
	}
	return null;
    }

    @Override
    public DataOraBean findConvocazioneUltimaCommissionePerIstanza(IUsefulDataForPlaceholderReplacement data) {

	FilterFromUsefulData filter = new FilterFromUsefulData(data);
	Session session = getSession();
	String sql = "SELECT commedilizie_convocazioni.dataconvocazione AS data, " + //
		     " commedilizie_convocazioni.oraconvocazione AS ora  " + //
		     "  FROM movimenti  " + //
		     "  INNER JOIN commissioniedilizie_r ON  " + //
		     "  commissioniedilizie_r.idcomune=movimenti.idcomune AND  " + //
		     "  commissioniedilizie_r.codicemovimento=movimenti.codicemovimento  " + //
		     "  INNER JOIN commissioniedilizie_t ON " + //
		     "  commissioniedilizie_t.idcomune=commissioniedilizie_r.idcomune AND " + //
		     "  commissioniedilizie_t.codicecommissione=commissioniedilizie_r.codicecommissione " + //
		     "  INNER JOIN commedilizie_convocazioni ON  " + //
		     " commissioniedilizie_t.idcomune=commedilizie_convocazioni.idcomune AND " + //
		     " commissioniedilizie_t.codicecommissione=commedilizie_convocazioni.codicecommissione AND " + //
		     " commissioniedilizie_t.idconvocazione=commedilizie_convocazioni.id " + //
		     "  WHERE movimenti.idcomune=?  ";//
	if (filter.getCodiceMovimento() != null) {
	    sql += " AND movimenti.codicemovimento=? "; //
	} else {
	    sql += " AND movimenti.codiceistanza=? "; //
	}
	sql += " ORDER BY movimenti.data DESC ";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("ora", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	if (filter.getCodiceMovimento() != null) {
	    q.setInteger(1, filter.getCodiceMovimento());
	} else {
	    q.setInteger(1, filter.getCodiceIstanza());
	}
	q.setMaxResults(2);
	q.setResultTransformer(Transformers.aliasToBean(DataOraBean.class));
	List<DataOraBean> list = q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
