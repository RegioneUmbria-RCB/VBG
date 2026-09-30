package it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaDettagliCommissioni;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class ComunicazioniCommissioniDettaglioQueryHelper extends BaseQueryHelper {

    private FiltriRicercaDettagliCommissioni filtri;

    public ComunicazioniCommissioniDettaglioQueryHelper(SessionFactoryImplementor sessimpl, FiltriRicercaDettagliCommissioni filtri) {

	String hibernateDialect = sessimpl.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.filtri = filtri;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, filtri.getIdCommissione());
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idRigaAppello", Hibernate.INTEGER);
	q.addScalar("codiceAnagrafe", Hibernate.INTEGER);
	q.addScalar("codiceAmministrazione", Hibernate.INTEGER);
	q.addScalar("codiceResponsabile", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String sql = "SELECT COMMEDILIZIE_APPELLO.ID AS idRigaAppello, ANAGRAFE.CODICEANAGRAFE AS codiceAnagrafe," +
		"AMMINISTRAZIONI.CODICEAMMINISTRAZIONE as codiceAmministrazione, RESPONSABILI.CODICERESPONSABILE AS codiceResponsabile  " +
		" FROM COMMEDILIZIE_APPELLO " +
		" LEFT JOIN ANAGRAFE ON ANAGRAFE.IDCOMUNE=COMMEDILIZIE_APPELLO.IDCOMUNE AND ANAGRAFE.CODICEANAGRAFE=COMMEDILIZIE_APPELLO.CODICEANAGRAFE " +
		" LEFT JOIN AMMINISTRAZIONI ON AMMINISTRAZIONI.IDCOMUNE=COMMEDILIZIE_APPELLO.IDCOMUNE AND AMMINISTRAZIONI.CODICEAMMINISTRAZIONE=COMMEDILIZIE_APPELLO.CODICEAMMINISTRAZIONE " +
		" LEFT JOIN RESPONSABILI ON RESPONSABILI.IDCOMUNE=COMMEDILIZIE_APPELLO.IDCOMUNE AND RESPONSABILI.CODICERESPONSABILE=COMMEDILIZIE_APPELLO.CODICERESPONSABILE ";
	sql += " WHERE COMMEDILIZIE_APPELLO.IDCOMUNE=? AND COMMEDILIZIE_APPELLO.CODICECOMMISSIONE=? ";
	sql += " ORDER BY COMMEDILIZIE_APPELLO.IDCOMUNE,COMMEDILIZIE_APPELLO.ID ";
	return sql;
    }
    //    private String getMailFragment() {
    //
    //	switch (this.filtri.getSceltaTipoMailAnagrafeEnum()) {
    //	case PEC_O_MAIL:
    //	    return "COALESCE(ANAGRAFE.PEC,ANAGRAFE.EMAIL)";
    //	case SOLO_PEC:
    //	    return "ANAGRAFE.PEC";
    //	case SOLO_MAIL:
    //	    return "ANAGRAFE.EMAIL";
    //	}
    //	throw new IllegalArgumentException("Scelta tipo mail non valida: " + this.filtri.getSceltaTipoMailAnagrafeEnum().name());
    //    }
}
