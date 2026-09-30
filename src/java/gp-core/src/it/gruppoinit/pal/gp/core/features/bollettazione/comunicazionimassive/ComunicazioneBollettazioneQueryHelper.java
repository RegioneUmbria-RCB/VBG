package it.gruppoinit.pal.gp.core.features.bollettazione.comunicazionimassive;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione.FiltriRicercaTestata;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class ComunicazioneBollettazioneQueryHelper extends BaseQueryHelper {

    private Integer idTestataMassiva;
    private String nomeFiltroEscludiDestinatariSenzaMail;
    private String nomeFiltroPosizioniNonPagate;
    private String nomeFiltroAllegaAvvisoPagamento;
    private String nomeFiltroSceltaMailAnagrafe;
    private String nomeFiltroConvertiInPDF;

    public ComunicazioneBollettazioneQueryHelper(SessionFactoryImplementor sessimpl, FiltriRicercaTestata filtri) {

	if (filtri == null) {
	    throw new IllegalArgumentException(
		    "Impossibile istanziare la classe ComunicazioneBollettazioneQueryHelper senza passare il parametro filtri");
	}
	String hibernateDialect = sessimpl.getDialect().toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	this.idTestataMassiva = filtri.getIdTestataMassiva();
	this.nomeFiltroEscludiDestinatariSenzaMail = filtri.getNomeFiltroEscludiDestinatariSenzaMail();
	this.nomeFiltroPosizioniNonPagate = filtri.getNomeFiltroPosizioniNonPagate();
	this.nomeFiltroAllegaAvvisoPagamento = filtri.getNomeFiltroAllegaAvvisoPagamento();
	this.nomeFiltroSceltaMailAnagrafe = filtri.getNomeFiltroSceltaMailAnagrafe();
	this.nomeFiltroConvertiInPDF = filtri.getNomeFiltroConvertiInPDF();
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	q.setString(0, this.nomeFiltroEscludiDestinatariSenzaMail);
	q.setString(1, this.nomeFiltroPosizioniNonPagate);
	q.setString(2, this.nomeFiltroAllegaAvvisoPagamento);
	q.setString(3, this.nomeFiltroSceltaMailAnagrafe);
	q.setString(4, this.nomeFiltroConvertiInPDF);
	q.setString(5, ORMHelper.getIdcomune());
	q.setInteger(6, this.idTestataMassiva);
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idMassiveTestata", Hibernate.INTEGER);
	q.addScalar("idBollettazione", Hibernate.INTEGER);
	q.addScalar("descrizioneBollettazione", Hibernate.STRING);
	q.addScalar("dataComunicazione", Hibernate.DATE);
	q.addScalar("descrizioneComunicazione", Hibernate.STRING);
	q.addScalar("escludiDestinatariSenzaMail", Hibernate.BOOLEAN);
	q.addScalar("soloPosizioniNonPagate", Hibernate.BOOLEAN);
	q.addScalar("oggettoProtocollo", Hibernate.STRING);
	q.addScalar("senderAccount", Hibernate.STRING);
	q.addScalar("templateMailTipo", Hibernate.STRING);
	q.addScalar("allegaAvvisoPagamento", Hibernate.BOOLEAN);
	q.addScalar("sceltaMailAnagrafe", Hibernate.STRING);
	q.addScalar("convertiInPDF", Hibernate.BOOLEAN);
    }

    @Override
    public String buildQuery() {

	return "select" +
		" massive_testata.id as idMassiveTestata, " +
		" boll_gest_testata.id as idBollettazione, " +
		" boll_gest_testata.descrizione as descrizioneBollettazione, " +
		" massive_testata.data_comunicazione AS dataComunicazione, " +
		" massive_testata.descrizione as descrizioneComunicazione, " +
		" escludi_destinatari.valore as escludiDestinatariSenzaMail, " +
		" posizioni_non_pagate.valore as soloPosizioniNonPagate, " +
		" oggettoprotocollo.descrizione as oggettoProtocollo, " +
		" mail_config.descrizione as senderAccount, " +
		" mailtipo.descrizione as templateMailTipo, " +
		" avviso_pagamento.valore as allegaAvvisoPagamento, " +
		" scelta_mail.valore as sceltaMailAnagrafe, " +
		" converti_pdf.valore as convertiInPDF " +
		"from " +
		" massive_testata " +
		"  inner join boll_massive_t on " +
		"    boll_massive_t.idcomune = massive_testata.idcomune and " +
		"    boll_massive_t.fkid_testata = massive_testata.id " +
		"  inner join boll_gest_testata on " +
		"    boll_gest_testata.idcomune = boll_massive_t.idcomune and " +
		"    boll_gest_testata.id  = boll_massive_t.fkid_bollettazione " +
		"  left join massive_parametri escludi_destinatari on " +
		"    massive_testata.idcomune = escludi_destinatari.idcomune and " +
		"    massive_testata.id = escludi_destinatari.fkid_testata and " +
		"    escludi_destinatari.chiave = ? " +
		"  left join massive_parametri posizioni_non_pagate on " +
		"    massive_testata.idcomune = posizioni_non_pagate.idcomune and " +
		"    massive_testata.id = posizioni_non_pagate.fkid_testata and " +
		"    posizioni_non_pagate.chiave = ? " +
		"  left join massive_parametri avviso_pagamento on " +
		"    massive_testata.idcomune = avviso_pagamento.idcomune and " +
		"    massive_testata.id = avviso_pagamento.fkid_testata and " +
		"    avviso_pagamento.chiave = ? " +
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
