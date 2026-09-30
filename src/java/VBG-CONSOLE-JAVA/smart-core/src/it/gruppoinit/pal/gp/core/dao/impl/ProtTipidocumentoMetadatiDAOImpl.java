package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ProtTipidocumentoMetadatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadati;
import it.gruppoinit.pal.gp.core.domain.ProtTipidocumentoMetadatiId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ProtTipidocumentoMetadatiDAOImpl extends BaseDAOImpl<ProtTipidocumentoMetadati, ProtTipidocumentoMetadatiId> implements
	ProtTipidocumentoMetadatiDAO {

    @Override
    public Class<ProtTipidocumentoMetadati> getEntityClass() {

	return ProtTipidocumentoMetadati.class;
    }

    @Override
    public List<CodiceDescrizioneBean> findByTipoDocumentoCodice(String codiceTipoDocumento, String codiceComune, String software) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = SQL.toString();
	sql = sql.replaceAll("SCHEMA_NAME", schemaName);
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codice");
	q.addScalar("descrizione");
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, codiceComune);
	q.setString(2, WebConstants.SOFTWARE_TT);
	q.setString(3, software);
	q.setString(4, codiceTipoDocumento);
	q.setResultTransformer(Transformers.aliasToBean(CodiceDescrizioneBean.class));
	return (List<CodiceDescrizioneBean>) q.list();
    }

    private static String SQL = "select " + //
	    "  mb.id as codice, " + //
	    "  mb.label as descrizione " + //
	    " from SCHEMA_NAME.protocollo_tipidocumento pt " + //
	    " inner join SCHEMA_NAME.prot_tipidocumento_metadati ptm " + //
	    " on pt.idcomune=ptm.idcomune " + //
	    " and pt.id     =ptm.fkidprottpdoc " + //
	    " inner join SCHEMA_NAME.metadati_diz_base mb " + //
	    " on ptm.fkidmetadatidizbase=mb.id " + //
	    " where pt.idcomune         =? " + //
	    " and (pt.codicecomune      =? " + //
	    " or pt.codicecomune       is null) " + //
	    " and pt.software          in (?, ?) " + //
	    " and pt.codice = ? " + //  
	    " group by mb.id,mb.label " + //
	    " order by mb.id";

    @Override
    public List<ProtTipidocumentoMetadati> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "id.fkidprottpdoc", DAOOrderTypeEnum.ASC);
    }
}
