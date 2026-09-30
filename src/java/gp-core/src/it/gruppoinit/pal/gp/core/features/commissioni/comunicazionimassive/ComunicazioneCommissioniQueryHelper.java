package it.gruppoinit.pal.gp.core.features.commissioni.comunicazionimassive;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.FiltriRicercaTestataCommissioni;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class ComunicazioneCommissioniQueryHelper extends BaseQueryHelper {

    private Integer idTestataMassiva;
    private String nomeFiltroEscludiDestinatariSenzaMail;
    private String nomeFiltroSceltaMailAnagrafe;
    private String nomeFiltroConvertiInPDF;

    public ComunicazioneCommissioniQueryHelper(SessionFactoryImplementor sessimpl, FiltriRicercaTestataCommissioni filtri) {

	if (filtri == null) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe ComunicazioneCommissioniQueryHelper senza passare il parametro filtri");
	}
	String hibernateDialect = sessimpl.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.idTestataMassiva = filtri.getIdTestataMassiva();
	this.nomeFiltroEscludiDestinatariSenzaMail = filtri.getNomeFiltroEscludiDestinatariSenzaMail();
	this.nomeFiltroSceltaMailAnagrafe = filtri.getNomeFiltroSceltaMailAnagrafe();
	this.nomeFiltroConvertiInPDF = filtri.getNomeFiltroConvertiInPDF();
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, this.nomeFiltroEscludiDestinatariSenzaMail);
	q.setString(1, this.nomeFiltroSceltaMailAnagrafe);
	q.setString(2, this.nomeFiltroConvertiInPDF);
	q.setString(3, ORMHelper.getIdcomune());
	q.setInteger(4, this.idTestataMassiva);
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idMassiveTestata", Hibernate.INTEGER);
	q.addScalar("idCommissione", Hibernate.INTEGER);
	q.addScalar("descrizioneCommissione", Hibernate.STRING);
	q.addScalar("dataComunicazione", Hibernate.DATE);
	q.addScalar("descrizioneComunicazione", Hibernate.STRING);
	q.addScalar("escludiDestinatariSenzaMail", Hibernate.BOOLEAN);
	q.addScalar("oggettoProtocollo", Hibernate.STRING);
	q.addScalar("senderAccount", Hibernate.STRING);
	q.addScalar("templateMailTipo", Hibernate.STRING);
	q.addScalar("sceltaMailAnagrafe", Hibernate.STRING);
	q.addScalar("convertiInPDF", Hibernate.BOOLEAN);
    }

    @Override
    public String buildQuery() {

	// TODO verifica query
	return "select" +
		" massive_testata.id as idMassiveTestata, " +
		" commissioniedilizie_t.codicecommissione as idCommissione, " +
		" commissioniedilizie_t.descrizione as descrizioneCommissione, " +
		" massive_testata.data_comunicazione AS dataComunicazione, " +
		" massive_testata.descrizione as descrizioneComunicazione, " +
		" escludi_destinatari.valore as escludiDestinatariSenzaMail, " +
		" oggettoprotocollo.descrizione as oggettoProtocollo, " +
		" mail_config.descrizione as senderAccount, " +
		" mailtipo.descrizione as templateMailTipo, " +
		" scelta_mail.valore as sceltaMailAnagrafe, " +
		" converti_pdf.valore as convertiInPDF " +
		"FROM " +
		" massive_testata " +
		"  inner join commedilizie_massive_t on " +
		"    commedilizie_massive_t.idcomune = massive_testata.idcomune and " +
		"    commedilizie_massive_t.fkid_testata = massive_testata.id " +
		"  inner join commissioniedilizie_t on " +
		"    commissioniedilizie_t.idcomune = commedilizie_massive_t.idcomune and " +
		"    commissioniedilizie_t.codicecommissione  = commedilizie_massive_t.fkid_commedt_id " +
		"  left join massive_parametri escludi_destinatari on " +
		"    massive_testata.idcomune = escludi_destinatari.idcomune and " +
		"    massive_testata.id = escludi_destinatari.fkid_testata and " +
		"    escludi_destinatari.chiave = ? " +
		"  left join massive_parametri scelta_mail on " +
		"    massive_testata.idcomune = scelta_mail.idcomune and " +
		"    massive_testata.id = scelta_mail.fkid_testata and " +
		"    scelta_mail.chiave = ? " +
		"  left join massive_parametri converti_pdf on " +
		"    massive_testata.idcomune = converti_pdf.idcomune and " +
		"    massive_testata.id = converti_pdf.fkid_testata and " +
		"    converti_pdf.chiave = ? " +
		"  left join mailtipo oggettoprotocollo on " +
		"   massive_testata.idcomune = oggettoprotocollo.idcomune and " +
		"   massive_testata.prot_fkid_mailtipo = oggettoprotocollo.codicemail " +
		"  left join mail_config on " +
		"   massive_testata.idcomune = mail_config.idcomune and " +
		"   massive_testata.senderaccount = mail_config.id " +
		"  left join mailtipo on " +
		"   massive_testata.idcomune = mailtipo.idcomune and " +
		"   massive_testata.fkid_mailtipo = mailtipo.codicemail " +
		"where " +
		" massive_testata.idcomune = ? and " +
		" massive_testata.id = ?";
    }
}
