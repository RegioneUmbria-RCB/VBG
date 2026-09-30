package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipimovStcMappingDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.TipiMovimentoStcMappingProtocollo;

@Repository
public class TipimovStcMappingDAOImpl extends BaseDAOImpl<TipimovStcMapping, PkId> implements TipimovStcMappingDAO {

    @Override
    public Class<TipimovStcMapping> getEntityClass() {

	return TipimovStcMapping.class;
    }

    @Override
    public TipiMovimentoStcMappingProtocollo findDatiProtocolloByTipoMovimento(String tipomovimento) {

	String query = "select " + // 
		       "protocollo_flusso as flusso, " + // 
		       "protocollo_tipodocumento as tipoDocumento, " + // 
		       "protocollo_oggetto as oggettoMailTipo, " + //
		       "protocollo_mittente as amministrazioneMittente " + //
		       "from " + // 
		       "tipimov_stc_mapping " + //
		       "where " + //
		       "tipimov_stc_mapping.idcomune = ? and " + //
		       "tipimov_stc_mapping.tipomovimento = ? and " + //
		       "tipimov_stc_mapping.flag_protocolla = ?";
	SQLQuery q = getSession().createSQLQuery(query);
	q.setParameter(0, ORMHelper.getIdcomune(), Hibernate.STRING);
	q.setParameter(1, tipomovimento, Hibernate.STRING);
	q.setParameter(2, 1, Hibernate.INTEGER);
	q.addScalar("flusso", Hibernate.STRING);
	q.addScalar("tipoDocumento", Hibernate.STRING);
	q.addScalar("oggettoMailTipo", Hibernate.INTEGER);
	q.addScalar("amministrazioneMittente", Hibernate.INTEGER);
	q.setResultTransformer(Transformers.aliasToBean(TipiMovimentoStcMappingProtocollo.class));
	List<TipiMovimentoStcMappingProtocollo> elenco = (List<TipiMovimentoStcMappingProtocollo>) q.list();
	if (elenco.isEmpty()) {
	    return null;
	}
	return elenco.get(0);
    }
}
