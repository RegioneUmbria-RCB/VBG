package it.gruppoinit.pal.gp.core.features.segnaposto.v2.segnaposto.implementazioni.mercati;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class VariabiliAutPrecQueryHelper extends BaseQueryHelper {

    private VariabiliMercatiBean vMercati;

    public VariabiliAutPrecQueryHelper(SessionFactoryImplementor sfi, VariabiliMercatiBean v) {

	super();
	String hibernateDialect = sfi.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sfi.getSettings().getDefaultSchemaName(), "");
	this.vMercati = v;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, vMercati.getConcid());
	if (vMercati.getDatastorico() != null) {
	    q.setDate(2, vMercati.getDatastorico());
	    q.setInteger(3, vMercati.getProgressivo());
	    // lsql2 = lsql2 + " and autorizzazioni_subentri.data_cessazione <= ?";
	    // lsql2 = lsql2 + " and autorizzazioni_subentri.id < ? "; //& lrs("progressivo") & " "
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("precautnum", Hibernate.STRING);
	q.addScalar("precautdata", Hibernate.DATE);
	q.addScalar("precresponsabile", Hibernate.STRING);
	q.addScalar("prectitolare", Hibernate.STRING);
	q.addScalar("preccomune", Hibernate.STRING);
    }

    private String[] titolare = new String[] { "titolare.nominativo", "titolare.nome" };

    @Override
    public String buildQuery() {

	String lsql2 = "select " + //
		" autorizzazioni_subentri.autoriznumero as precautnum," + //
		" autorizzazioni_subentri.autorizdata as precautdata, " + //
		" autorizzazioni_subentri.autorizresponsabile as precresponsabile, " + //
		applyConcatFunction("' '", titolare) +
		" as prectitolare, " + // 
		" comuni.comune as preccomune " + //
		" from " + //
		" autorizzazioni_subentri " + //
		" inner join anagrafe titolare on autorizzazioni_subentri.idcomune = titolare.idcomune and autorizzazioni_subentri.fk_codiceanagrafe = titolare.codiceanagrafe " + //
		" inner join comuni on autorizzazioni_subentri.autorizcomune = comuni.codicecomune " + //
		" where " + //
		" autorizzazioni_subentri.idcomune = ? and " + //
		" autorizzazioni_subentri.fk_idaut_attuale = ?";
	if (vMercati.getDatastorico() != null) {
	    lsql2 = lsql2 + " and autorizzazioni_subentri.data_cessazione <= ?";
	    lsql2 = lsql2 + " and autorizzazioni_subentri.id < ? "; //& lrs("progressivo") & " "
	}
	return lsql2 + " order by autorizzazioni_subentri.data_cessazione desc, autorizzazioni_subentri.id desc";
    }
}
