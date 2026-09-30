package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.odftoolkit.simple.TextDocument;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.DocumentMergeDAO;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;

@SuppressWarnings("rawtypes")
@Repository
public class DocumentMergeDAOImpl extends BaseDAOImpl implements DocumentMergeDAO {

    @Override
    public String eseguiSostituzioniConQuery(String fileIn, String tipoFile, DocumentMergeHelper helper) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	DialettoEnum _dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	String dbOwner = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	SostituzioniValoriConQueryWork procedure = new SostituzioniValoriConQueryWork(fileIn, tipoFile, helper.getParams(), _dialetto, dbOwner);
	getSession().doWork(procedure);
	return procedure.output;
    }

    @Override
    public TextDocument eseguiSostituzioniConQuery(TextDocument fileIn, String tipoFile, DocumentMergeHelper helper) {

	// TODO PASSA PARAMETRI ODT
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String hibernateDialect = dialetto.toString();
	DialettoEnum _dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	String dbOwner = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	Map<String, String> params = new HashMap<String, String>();
	SostituzioniValoriConQueryWork procedure = new SostituzioniValoriConQueryWork(tipoFile, params, _dialetto, dbOwner);
	getSession().doWork(procedure);
	return fileIn;
    }

    @Override
    public Class getEntityClass() {

	return null;
    }
}
