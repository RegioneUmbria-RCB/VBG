package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;

@SuppressWarnings("rawtypes")
@Repository
public class ProtocollazioneDAOImpl extends BaseDAOImpl implements IProtocollazioneDAO {

    @Override
    public Class getEntityClass() {

	return null;
    }

    @SuppressWarnings({ "unused", "unchecked" })
    @Override
    public List<ProtocolloAttivoBean> verificaProtocolloAttivo(List<ISoftwareComuneData> softwareComuneFromIdDettaglioList) {

	String enteTT = "TUTTI";
	String softwareTT = "TT";
	List<String> enti = new ArrayList<String>();
	List<String> software = new ArrayList<String>();
	for (ISoftwareComuneData iSoftwareComuneData : softwareComuneFromIdDettaglioList) {
	    enti.add(iSoftwareComuneData.getCodiceComune());
	    software.add(iSoftwareComuneData.getSoftware());
	}
	String sql = "select " + // 
		"coalesce(codicecomune,'TUTTI') as ente, software, modulo, attivo " + //
		"from verticalizzazioni " + // 
		"where " + // 
		"idcomune = ? and " + // 
		"modulo like 'PROTOCOLLO_%' and " + // 
		"coalesce(codicecomune,'TUTTI') in (?";
	for (String e : enti) {
	    sql += ",?";
	}
	sql += ") and software in (?";
	for (String s : software) {
	    sql += ",?";
	}
	sql += ")";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Verticalizzazioni.class);
	int idx = 0;
	query.setString(idx, ORMHelper.getIdcomune());
	idx++;
	query.setString(idx, enteTT);
	idx++;
	for (String ente : enti) {
	    query.setString(idx, ente);
	    idx++;
	}
	query.setString(idx, softwareTT);
	idx++;
	for (String s : software) {
	    query.setString(idx, s);
	    idx++;
	}
	query.addScalar("ente", Hibernate.STRING);
	query.addScalar("software", Hibernate.STRING);
	query.addScalar("modulo", Hibernate.STRING);
	query.addScalar("attivo", Hibernate.BOOLEAN);
	query.setResultTransformer(Transformers.aliasToBean(ProtocolloAttivoBean.class));
	return query.list();
    }
}