package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaDettagli;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;

public class ComunicazioniBollGestDettaglioQueryHelper extends BaseQueryHelper {

    private FiltriRicercaDettagli filtri;
    private StatiPosizioniDebitorieConverter statiConverter;

    public ComunicazioniBollGestDettaglioQueryHelper(SessionFactoryImplementor sessimpl, FiltriRicercaDettagli filtri) {

	String hibernateDialect = sessimpl.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.filtri = filtri;
	this.statiConverter = new StatiPosizioniDebitorieConverter();
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, filtri.getIdBollettazione());
	q.setInteger(pos++, 1); // VALIDATA
	if (filtri.isSoloPosizioniDebitorieNonPagate()) {
	    StatoPagamentoType[] statiPosizioniInCorso = statiConverter.getStatiPosizioniInCorso();
	    for (StatoPagamentoType statoPagamentoType : statiPosizioniInCorso) {
		q.setString(pos++, statoPagamentoType.name());
	    }
	    for (StatoPagamentoType statoPagamentoType : statiPosizioniInCorso) {
		q.setString(pos++, statoPagamentoType.name());
	    }
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idRigaDettaglio", Hibernate.INTEGER);
	q.addScalar("codiceAnagrafe", Hibernate.INTEGER);
	q.addScalar("mail", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	// COMPORTAMENTO PER GESTIRE LA MAIL
	// USA SOLO PEC
	// PRIMA PEC SE DISPONIBILE POI MAIL
	// SOLO MAIL
	// COALESCE(ANAGRAFE.PEC,ANAGRAFE.EMAIL)
	String mailFragment = getMailFragment();
	String sql = "SELECT BOLL_GEST_DETTAGLIO.ID AS idRigaDettaglio, FK_CODICEANAGRAFE AS codiceAnagrafe, " +
		mailFragment +
		" AS mail FROM BOLL_GEST_DETTAGLIO " +
		" INNER JOIN ANAGRAFE ON ANAGRAFE.IDCOMUNE=BOLL_GEST_DETTAGLIO.IDCOMUNE AND ANAGRAFE.CODICEANAGRAFE=BOLL_GEST_DETTAGLIO.FK_CODICEANAGRAFE ";
	sql += " LEFT JOIN DETT_POSIZIONE_DEBITORIA ON ";
	sql += " DETT_POSIZIONE_DEBITORIA.IDCOMUNE=BOLL_GEST_DETTAGLIO.IDCOMUNE AND ";
	sql += " DETT_POSIZIONE_DEBITORIA.ID=BOLL_GEST_DETTAGLIO.FK_POSDEBDETTAGLIO_ID ";
	sql += " LEFT JOIN BOLL_GEST_DETT_RATE RATE ON "; //
	sql += " RATE.IDCOMUNE=BOLL_GEST_DETTAGLIO.IDCOMUNE AND "; // 
	sql += " RATE.FK_BOLLGESTDET_ID=BOLL_GEST_DETTAGLIO.ID  "; //	 
	sql += " LEFT JOIN DETT_POSIZIONE_DEBITORIA RATEPOSDEB ON  "; // 
	sql += "  RATEPOSDEB.IDCOMUNE=RATE.IDCOMUNE AND "; //
	sql += " RATEPOSDEB.ID=RATE.FK_POSDEBDETTAGLIO_ID ";//	 
	sql += " WHERE BOLL_GEST_DETTAGLIO.IDCOMUNE=? AND BOLL_GEST_DETTAGLIO.FK_BOLLGEST_ID=? ";
	sql += " AND BOLL_GEST_DETTAGLIO.FLAG_VALIDATA=? ";
	if (filtri.isSoloPosizioniDebitorieNonPagate()) {
	    sql += "  AND (RATEPOSDEB.STATO IN (" +
		    parametriPosizioniDaPagare() + // 
		    ") OR DETT_POSIZIONE_DEBITORIA.STATO IN (" +
		    parametriPosizioniDaPagare() +
		    ")) ";
	}
	if (filtri.isEscludiAnagraficheSenzaMail()) {
	    sql += " AND  " + mailFragment + " IS NOT NULL ";
	}
	sql += " GROUP BY BOLL_GEST_DETTAGLIO.ID, FK_CODICEANAGRAFE," + mailFragment;
	sql += " ORDER BY BOLL_GEST_DETTAGLIO.ID ";
	return sql;
    }

    private String getMailFragment() {

	switch (this.filtri.getSceltaTipoMailAnagrafeEnum()) {
	    case PEC_O_MAIL:
		return "COALESCE(ANAGRAFE.PEC,ANAGRAFE.EMAIL)";
	    case SOLO_PEC:
		return "ANAGRAFE.PEC";
	    case SOLO_MAIL:
		return "ANAGRAFE.EMAIL";
	}
	throw new IllegalArgumentException("Scelta tipo mail non valida: " + this.filtri.getSceltaTipoMailAnagrafeEnum().name());
    }

    private String parametriPosizioniDaPagare() {

	String params = StringUtils.repeat("?,", statiConverter.getStatiPosizioniInCorso().length);
	return params.substring(0, (params.length() - 1));
    }
}
