package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.AppIoCoda;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MassiveDAllegati;
import it.gruppoinit.pal.gp.core.domain.MassiveDettAppiocoda;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDestinatari;
import it.gruppoinit.pal.gp.core.domain.MassiveDettDocdafirmare;
import it.gruppoinit.pal.gp.core.domain.MassiveDettMessaggimail;
import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.domain.MassiveTFirmatari;
import it.gruppoinit.pal.gp.core.domain.MessaggiMail;
import it.gruppoinit.pal.gp.core.domain.MessaggiMailAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.upgr.QueryMassiveDettDestinatariHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.upgr.UpgrMassiveDestinatariHelper;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettagliMailComunicazione;
import it.gruppoinit.pal.gp.core.utils.ICurrentDateService;

@SuppressWarnings("rawtypes")
@Repository
public class ComunicazioniMassiveDettaglioDAOImpl extends BaseDAOImpl implements IComunicazioniMassiveDettaglioDAO {

    private static Logger log = LoggerFactory.getLogger(ComunicazioniMassiveDettaglioDAOImpl.class);
    private ICurrentDateService currentDateService;

    @Autowired
    public ComunicazioniMassiveDettaglioDAOImpl(ICurrentDateService currentDateService) {

	this.currentDateService = currentDateService;
    }

    @Override
    public void insert(MassiveDettaglio dettaglio) {

	saveEntity(dettaglio);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MassiveDettaglio> getRigheByIdTestata(int idTestata) {

	String hql = "select md from MassiveDettaglio md inner join md.massiveDettDestinataris  dest left join dest.anagrafe where md.id.idcomune=? and md.massiveTestata.id.codice=? order by dest.anagrafe.nominativo, dest.anagrafe.nome ";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	return q.list();
    }

    @Override
    public List<MassiveDettaglio> getRigheByIdTestata(int idTestata, String statoDaEscludere) {

	String hql = "select md from MassiveDettaglio md inner join md.massiveDettDestinataris dest left join dest.anagrafe where md.id.idcomune=? and md.massiveTestata.id.codice=? and not md.ultimoStatoCompletato=?  order by dest.anagrafe.nominativo,dest.anagrafe.nome ";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestata);
	q.setString(2, statoDaEscludere);
	return q.list();
    }

    @Override
    public Class getEntityClass() {

	return null;
    }

    @Override
    public MassiveDAllegati insertAllegato(int idRigaDettaglio, int codiceOggetto) {

	MassiveDAllegati entity = new MassiveDAllegati();
	entity.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(idRigaDettaglio)));
	entity.setCodiceOggetto(codiceOggetto);
	saveEntity(entity);
	return entity;
    }

    @Override
    public void salvaErrore(int idDettaglio, String messaggioErrore) {

	MassiveDettaglio d = this.getById(idDettaglio);
	d.setErrore(messaggioErrore);
	update(d);
	this.flush();
	this.commit();
	this.flush();
    }

    @Override
    public MassiveDettaglio getById(int idDettaglio) {

	return (MassiveDettaglio) getById(MassiveDettaglio.class, idDettaglio);
    }

    @Override
    public void impostaStatoConCommit(int idDettaglio, String nuovoStato) {

	String hql = "update MassiveDettaglio set ultimoStatoCompletato=?, ultimoStatoData=? where idcomune=? and id.codice=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, nuovoStato);
	q.setDate(1, currentDateService.getCurrentDate());
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, idDettaglio);
	q.executeUpdate();
	this.flush();
	this.commit();
	this.flush();
	this.clear();
    }

    @Override
    public List<MassiveDAllegati> getMassiveDAllegatiByIdDettaglio(int idDettaglio) {

	String hql = "from MassiveDAllegati mda where mda.id.idcomune=? and mda.massiveDettaglio.id.codice=? order by mda.id.codice";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglio);
	return q.list();
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getCodiciOggettoMassiveDAllegatiByIdDettaglio(int idDettaglio) {

	String sql = "select codiceoggetto from massive_d_allegati where idcomune=? and fkid_dettaglio=? order by id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveTFirmatari.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglio);
	q.addScalar("codiceoggetto", Hibernate.INTEGER);
	return q.list();
    }

    @Override
    public MassiveDettDocdafirmare insertDocumentoDaFirmare(int idRigaDettaglio, int idDocDaFirmare) {

	MassiveDettDocdafirmare d = new MassiveDettDocdafirmare();
	d.setMassiveDettaglio((MassiveDettaglio) getById(MassiveDettaglio.class, new PkId(idRigaDettaglio)));
	d.setDocumentiDaFirmare((DocumentiDaFirmare) getById(DocumentiDaFirmare.class, new PkId(idDocDaFirmare)));
	this.saveEntity(d);
	return d;
    }

    @Override
    public List<MassiveDettDocdafirmare> findDocDaFirmarePerDettaglio(int idRigaDettaglio) {

	String hql = "from MassiveDettDocdafirmare mda where mda.id.idcomune=? and mda.massiveDettaglio.id.codice=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRigaDettaglio);
	return q.list();
    }

    @Override
    public void aggiornaRiferimentiProtocollo(int idRigaDettaglio, String numeroProtocollo, Date dateDDMMYYYY, String idProtocollo) {

	String hql = "update MassiveDettaglio set numeroprotocollo=?, dataprotocollo=?, fkidprotocollo=? where idcomune=? and id.codice=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, numeroProtocollo);
	q.setDate(1, dateDDMMYYYY);
	q.setString(2, idProtocollo);
	q.setString(3, ORMHelper.getIdcomune());
	q.setInteger(4, idRigaDettaglio);
	q.executeUpdate();
    }

    @Override
    public void salvaMailMessage(MailMessageType messaggio, MassiveDettaglio dettaglio, int senderAccount) {

	MessaggiMail messaggiMail = new MessaggiMail();
	messaggiMail.setCorpo(messaggio.getCorpoMail());
	messaggiMail.setDataInvio(new Date());
	messaggiMail.setDestinatario(messaggio.getDestinatari());
	messaggiMail.setDestinatariocc(messaggio.getDestinatariInCopia());
	messaggiMail.setDestinatarioabcc(messaggio.getDestinatariInCopiaNascosta());
	messaggiMail.setMessageId(messaggio.getMessageID());
	messaggiMail.setMittente(messaggio.getMittente());
	messaggiMail.setOggetto(messaggio.getOggetto());
	messaggiMail.setAccountId((MailConfig) this.getById(MailConfig.class, new PkId(senderAccount)));
	this.saveEntity(messaggiMail);
	if (messaggio.getAttachments() != null) {
	    for (AttachmentType at : messaggio.getAttachments().getAttachment()) {
		MessaggiMailAllegati mailAllegati = new MessaggiMailAllegati();
		mailAllegati.setmessaggiMail(messaggiMail);
		mailAllegati.setCodiceOggetto(Integer.parseInt(at.getId()));
		this.saveEntity(mailAllegati);
	    }
	}
	MassiveDettMessaggimail dettMessaggimail = new MassiveDettMessaggimail();
	dettMessaggimail.setMassiveDettaglio(dettaglio);
	dettMessaggimail.setMessaggiMail(messaggiMail);
	this.saveEntity(dettMessaggimail);
    }
    
    
    
    @Override
    public void salvaAppioMessage(String guid, MassiveDettaglio dettaglio) {

	MassiveDettAppiocoda dettAppiocoda = new MassiveDettAppiocoda();
	dettAppiocoda.setMassiveDettaglio(dettaglio);
	dettAppiocoda.setFkidAppIoCoda(guid);
	this.saveEntity(dettAppiocoda);
    }
    
    

    @Override
    public List<MassiveDettDocdafirmare> findDocumentiDaFirmareByIdDocDaFirmare(int idDocumentoDaFirmare) {

	String hql = "from MassiveDettDocdafirmare mda where mda.id.idcomune=? and mda.documentiDaFirmare.id.codice=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDocumentoDaFirmare);
	return q.list();
    }

    @Override
    public long countDocumentiDaFirmareByIdDocDaFirmare(int idDocumentoDaFirmare) {

	Query query = getSession().createQuery(
		"select count(*) from MassiveDettDocdafirmare mda where mda.id.idcomune=:idcomune and mda.documentiDaFirmare.id.codice=:iddoc");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("iddoc", idDocumentoDaFirmare);
	Long count = (Long) query.uniqueResult();
	return count == null ? 0 : count.longValue();
    }

    @Override
    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerAnagrafe(Integer codiceAnagrafe) {

	String sql = "select " + //
		     " massive_dettaglio.idcomune, " + //
		     " massive_dettaglio.fkid_testata as idmassivetestata, " + //
		     " massive_dettaglio.id as idmassivedettaglio, " + //
		     " massive_dett_destinatari.codiceanagrafe, " + //
		     " massive_parametri.valore, " + //
		     " anagrafe.email, " + //
		     " anagrafe.pec " + //
		     "from " + //
		     " massive_dettaglio " + //
		     " inner join massive_parametri on massive_parametri.idcomune = massive_dettaglio.idcomune " + //
		     " and massive_parametri.fkid_testata = massive_dettaglio.fkid_testata " + //
		     " inner join massive_dett_destinatari on massive_dettaglio.idcomune = massive_dett_destinatari.idcomune " + //
		     " and massive_dettaglio.id = massive_dett_destinatari.fkid_massive_d " + //
		     " inner join anagrafe on massive_dett_destinatari.idcomune = anagrafe.idcomune " + //
		     " and massive_dett_destinatari.codiceanagrafe = anagrafe.codiceanagrafe " + //
		     "where " + //
		     " massive_dettaglio.idcomune = ? " + //
		     " and massive_dett_destinatari.codiceanagrafe = ? " + //
		     " and massive_parametri.chiave = ? " + //
		     " and massive_dett_destinatari.mail_destinatario is null ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Anagrafe.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAnagrafe);
	q.setString(2, ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("idmassivetestata", Hibernate.INTEGER);
	q.addScalar("idmassivedettaglio", Hibernate.INTEGER);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.STRING);
	q.addScalar("email", Hibernate.STRING);
	q.addScalar("pec", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(MassiveDettaglioAggiornaMail.class));
	return (List<MassiveDettaglioAggiornaMail>) q.list();
    }

    @Override
    public void aggiornaMailAnagrafe(Integer codiceAnagrafe) {

	log.debug("aggiornaMailAnagrafe {}", codiceAnagrafe);
	List<MassiveDettaglioAggiornaMail> ms = trovaMailDaAggiornarePerAnagrafe(codiceAnagrafe);
	for (MassiveDettaglioAggiornaMail md : ms) {
	    String mailDaAggiornare = "";
	    SceltaTipoMailAnagrafeEnum tipoMail = SceltaTipoMailAnagrafeEnum.valueOf(md.getValore());
	    log.debug("aggiornaMailAnagrafe {}==>tipoMail {}", codiceAnagrafe, tipoMail);
	    switch (tipoMail) {
	    case PEC_O_MAIL:
		mailDaAggiornare = StringUtils.defaultIfEmpty(md.getPec(), md.getEmail());
		break;
	    case SOLO_MAIL:
		mailDaAggiornare = md.getEmail();
		break;
	    case SOLO_PEC:
		mailDaAggiornare = md.getPec();
		break;
	    default:
		break;
	    }
	    log.debug("aggiornaMailAnagrafe {}==>mailDaAggiornare {}", codiceAnagrafe, mailDaAggiornare);
	    if (StringUtils.isNotBlank(mailDaAggiornare)) {
		MassiveDettaglio m = this.getById(md.getIdmassivedettaglio());
		MassiveDettDestinatari destinatari = m.getDestinatari();
		destinatari.setMailDestinatario(mailDaAggiornare);
		this.saveEntity(destinatari);
	    }
	}
    }

    @Override
    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerAmministrazione(Integer codiceAmministrazione) {

	String sql = "select " + //
		     " massive_dettaglio.idcomune, " + //
		     " massive_dettaglio.fkid_testata as idmassivetestata, " + //
		     " massive_dettaglio.id as idmassivedettaglio, " + //
		     " massive_dett_destinatari.codiceamministrazione, " + //
		     " massive_parametri.valore, " + //
		     " amministrazioni.email, " + //
		     " amministrazioni.pec " + //
		     "from " + //
		     " massive_dettaglio " + //
		     " inner join massive_parametri on massive_parametri.idcomune = massive_dettaglio.idcomune " + //
		     " and massive_parametri.fkid_testata = massive_dettaglio.fkid_testata " + //
		     " inner join massive_dett_destinatari on massive_dettaglio.idcomune = massive_dett_destinatari.idcomune " + //
		     " and massive_dettaglio.id = massive_dett_destinatari.fkid_massive_d " + //
		     " inner join amministrazioni on massive_dett_destinatari.idcomune = amministrazioni.idcomune " + //
		     " and massive_dett_destinatari.codiceamministrazione = amministrazioni.codiceamministrazione " + //
		     "where " + //
		     " massive_dettaglio.idcomune = ? " + //
		     " and massive_dett_destinatari.codiceamministrazione = ? " + //
		     " and massive_parametri.chiave = ? " + //
		     " and massive_dett_destinatari.mail_destinatario is null ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Amministrazioni.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceAmministrazione);
	q.setString(2, ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("idmassivetestata", Hibernate.INTEGER);
	q.addScalar("idmassivedettaglio", Hibernate.INTEGER);
	q.addScalar("codiceamministrazione", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.STRING);
	q.addScalar("email", Hibernate.STRING);
	q.addScalar("pec", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(MassiveDettaglioAggiornaMail.class));
	return (List<MassiveDettaglioAggiornaMail>) q.list();
    }

    @Override
    public void aggiornaMailAmministrazioni(Integer codiceAmministrazioni) {

	log.debug("aggiornaMailAnagrafe {}", codiceAmministrazioni);
	List<MassiveDettaglioAggiornaMail> ms = trovaMailDaAggiornarePerAmministrazione(codiceAmministrazioni);
	for (MassiveDettaglioAggiornaMail md : ms) {
	    String mailDaAggiornare = "";
	    SceltaTipoMailAnagrafeEnum tipoMail = SceltaTipoMailAnagrafeEnum.valueOf(md.getValore());
	    log.debug("aggiornaMailAnagrafe {}==>tipoMail {}", codiceAmministrazioni, tipoMail);
	    switch (tipoMail) {
	    case PEC_O_MAIL:
		mailDaAggiornare = StringUtils.defaultIfEmpty(md.getPec(), md.getEmail());
		break;
	    case SOLO_MAIL:
		mailDaAggiornare = md.getEmail();
		break;
	    case SOLO_PEC:
		mailDaAggiornare = md.getPec();
		break;
	    default:
		break;
	    }
	    log.debug("aggiornaMailAnagrafe {}==>mailDaAggiornare {}", codiceAmministrazioni, mailDaAggiornare);
	    if (StringUtils.isNotBlank(mailDaAggiornare)) {
		MassiveDettaglio m = this.getById(md.getIdmassivedettaglio());
		MassiveDettDestinatari destinatari = m.getDestinatari();
		destinatari.setMailDestinatario(mailDaAggiornare);
		this.saveEntity(destinatari);
	    }
	}
    }

    @Override
    public List<MassiveDettaglioAggiornaMail> trovaMailDaAggiornarePerResponsabile(Integer codiceResponsabile) {

	String sql = "select " + //
		     " massive_dettaglio.idcomune, " + //
		     " massive_dettaglio.fkid_testata as idmassivetestata, " + //
		     " massive_dettaglio.id as idmassivedettaglio, " + //
		     " massive_dett_destinatari.codiceresponsabile, " + //
		     " massive_parametri.valore, " + //
		     " responsabili.email, " + //
		     " null as pec " + //
		     "from " + //
		     " massive_dettaglio " + //
		     " inner join massive_parametri on massive_parametri.idcomune = massive_dettaglio.idcomune " + //
		     " and massive_parametri.fkid_testata = massive_dettaglio.fkid_testata " + //
		     " inner join massive_dett_destinatari on massive_dettaglio.idcomune = massive_dett_destinatari.idcomune " + //
		     " and massive_dettaglio.id = massive_dett_destinatari.fkid_massive_d " + //
		     " inner join responsabili on massive_dett_destinatari.idcomune = responsabili.idcomune " + //
		     " and massive_dett_destinatari.codiceresponsabile = responsabili.codiceresponsabile " + //
		     "where " + //
		     " massive_dettaglio.idcomune = ? " + //
		     " and massive_dett_destinatari.codiceresponsabile = ? " + //
		     " and massive_parametri.chiave = ? " + //
		     " and massive_dett_destinatari.mail_destinatario is null ";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Amministrazioni.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceResponsabile);
	q.setString(2, ParametriConstants.GESTIONE_SCELTA_MAIL_ANAGRAFE);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("idmassivetestata", Hibernate.INTEGER);
	q.addScalar("idmassivedettaglio", Hibernate.INTEGER);
	q.addScalar("codiceresponsabile", Hibernate.INTEGER);
	q.addScalar("valore", Hibernate.STRING);
	q.addScalar("email", Hibernate.STRING);
	q.addScalar("pec", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(MassiveDettaglioAggiornaMail.class));
	return (List<MassiveDettaglioAggiornaMail>) q.list();
    }

    @Override
    public void aggiornaMailResponsabili(Integer codiceResponsabili) {

	log.debug("aggiornaMailResponsabili {}", codiceResponsabili);
	List<MassiveDettaglioAggiornaMail> ms = trovaMailDaAggiornarePerResponsabile(codiceResponsabili);
	for (MassiveDettaglioAggiornaMail md : ms) {
	    String mailDaAggiornare = "";
	    SceltaTipoMailAnagrafeEnum tipoMail = SceltaTipoMailAnagrafeEnum.valueOf(md.getValore());
	    log.debug("aggiornaMailResponsabili {}==>tipoMail {}", codiceResponsabili, tipoMail);
	    switch (tipoMail) {
	    case PEC_O_MAIL:
		mailDaAggiornare = StringUtils.defaultIfEmpty(md.getPec(), md.getEmail());
		break;
	    case SOLO_MAIL:
		mailDaAggiornare = md.getEmail();
		break;
	    case SOLO_PEC:
		mailDaAggiornare = md.getPec();
		break;
	    default:
		break;
	    }
	    log.debug("aggiornaMailResponsabili {}==>mailDaAggiornare {}", codiceResponsabili, mailDaAggiornare);
	    if (StringUtils.isNotBlank(mailDaAggiornare)) {
		MassiveDettaglio m = this.getById(md.getIdmassivedettaglio());
		MassiveDettDestinatari destinatari = m.getDestinatari();
		destinatari.setMailDestinatario(mailDaAggiornare);
		this.saveEntity(destinatari);
	    }
	}
    }

    @Override
    public List<RiferimentoPosizioneDebitoria> recuperaDettPosizioneDebitoriaFromMassiva(Integer idDettaglioMassiva) {

	String sql = "SELECT " + //
		     " dett_posizione_debitoria.id AS iddettposizionedebitoria, " + //
		     " dett_posizione_debitoria.cf_ente_creditore AS cfentecreditore " + //
		     "FROM " + //
		     " boll_gest_dettaglio " + //
		     " INNER JOIN boll_massive_d ON boll_gest_dettaglio.idcomune = boll_massive_d.idcomune " + //
		     " AND boll_gest_dettaglio.id = boll_massive_d.fkid_boll_gest_dettaglio " + //
		     " INNER JOIN massive_dettaglio ON massive_dettaglio.idcomune = boll_massive_d.idcomune " + //
		     " AND massive_dettaglio.id = boll_massive_d.fkid_massive_d " + //
		     " INNER JOIN dett_posizione_debitoria ON dett_posizione_debitoria.idcomune = boll_gest_dettaglio.idcomune " + //
		     " AND dett_posizione_debitoria.id = boll_gest_dettaglio.fk_posdebdettaglio_id " + //
		     "WHERE " + //
		     " massive_dettaglio.idcomune = ? " + //
		     " AND massive_dettaglio.id = ? " + //
		     "GROUP BY " + //
		     " dett_posizione_debitoria.id, " + //
		     " dett_posizione_debitoria.cf_ente_creditore " + //
		     "UNION " + //
		     "SELECT " + //
		     " dett_posizione_debitoria.id AS iddettposizionedebitoria, " + //
		     " dett_posizione_debitoria.cf_ente_creditore AS cfentecreditore " + //
		     "FROM " + //
		     " boll_gest_dettaglio " + //
		     " INNER JOIN boll_massive_d ON boll_gest_dettaglio.idcomune = boll_massive_d.idcomune " + //
		     " AND boll_gest_dettaglio.id = boll_massive_d.fkid_boll_gest_dettaglio " + //
		     " INNER JOIN boll_gest_dett_rate ON boll_gest_dett_rate.idcomune = boll_massive_d.idcomune " + //
		     " AND boll_gest_dett_rate.fk_bollgestdet_id = boll_massive_d.fkid_boll_gest_dettaglio " + //
		     " INNER JOIN dett_posizione_debitoria ON dett_posizione_debitoria.idcomune = boll_gest_dett_rate.idcomune " + //
		     " AND dett_posizione_debitoria.id = boll_gest_dett_rate.FK_POSDEBDETTAGLIO_ID " + //
		     "WHERE " + //
		     " boll_massive_d.idcomune = ? " + //
		     " AND boll_massive_d.fkid_massive_d = ? " + //
		     "GROUP BY " + //
		     " dett_posizione_debitoria.id, " + //
		     " dett_posizione_debitoria.cf_ente_creditore";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettaglio.class);
	q.addScalar("idDettPosizioneDebitoria", Hibernate.INTEGER);
	q.addScalar("cfEnteCreditore", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglioMassiva);
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, idDettaglioMassiva);
	q.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(RiferimentoPosizioneDebitoria.class));
	return q.list();
    }

    @Override
    public List<Integer> getDocumentiDaFirmarePerIdTestata(int idTestataComunicazione) {

	String sql = "select fk_docdafirmare_id as iddoc " //
		     + "from massive_dett_docdafirmare " //
		     +
		     "inner join massive_dettaglio on massive_dettaglio.idcomune=massive_dett_docdafirmare.idcomune and massive_dettaglio.id=massive_dett_docdafirmare.fkid_massive_d "// 
		     +
		     "where massive_dettaglio.idcomune=? and massive_dettaglio.fkid_testata=? order by massive_dett_docdafirmare.idcomune,massive_dett_docdafirmare.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettDocdafirmare.class);
	q.addScalar("iddoc", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idTestataComunicazione);
	return q.list();
    }

    @Override
    public void eliminaMassiveDocDaFirmareByIdDocDaFirmare(List<Integer> idDocDaFirmare) {

	if (idDocDaFirmare == null) {
	    throw new IllegalArgumentException("La lista dei documenti da cancellare non può essere nulla");
	}
	String sql = "delete from massive_dett_docdafirmare where idcomune = ? and fk_docdafirmare_id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettDocdafirmare.class);
	for (Integer idDoc : idDocDaFirmare) {
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, idDoc);
	    query.executeUpdate();
	}
    }

    @Override
    public List<UpgrMassiveDestinatariHelper> upgrDestinatariComunicazioniCommissioniDettagli() {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryMassiveDettDestinatariHelper queryHelper = new QueryMassiveDettDestinatariHelper(sessimpl);
	String sql = queryHelper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(UpgrMassiveDestinatariHelper.class));
	return q.list();
    }

    @Override
    public int contaRicevuteMailPerDettaglio(int idDettaglio) {

	String sql = "select count(*) as conta from massive_dett_messaggimail inner join messaggi_mail ricevute on ricevute.idcomune=massive_dett_messaggimail.idcomune and ricevute.id_padre=massive_dett_messaggimail.fkid_messaggimail where massive_dett_messaggimail.idcomune=? and massive_dett_messaggimail.fkid_massive_d=?";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettMessaggimail.class)
		.addSynchronizedEntityClass(MessaggiMail.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idDettaglio);
	q.addScalar("conta", Hibernate.INTEGER);
	List<Integer> rs = q.list();
	return ((Integer) rs.get(0)).intValue();
    }

    @Override
    public List<DettagliMailComunicazione> findDettagliMailInviate(int idRiga) {

	List<DettagliMailComunicazione> ret = new ArrayList<DettagliMailComunicazione>();
	String hql = "from MassiveDettMessaggimail mda where mda.id.idcomune=? and mda.massiveDettaglioId=?";
	Query q = getSession().createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRiga);
	List<MassiveDettMessaggimail> list = q.list();
	for (MassiveDettMessaggimail mm : list) {
	    MessaggiMail messaggiMail = mm.getMessaggiMail();
	    if (messaggiMail != null) {
		ret.add(DettagliMailComunicazione.fromMessaggiMail(messaggiMail));
	    }
	}
	return ret;
    }

    @Override
    public List<String> findDettagliAppIoInviate(int idRiga) {

	SQLQuery query = getSession().createSQLQuery(
		"select a.* from app_io_coda a join massive_dett_appiocoda b on a.idcomune = b.idcomune and a.guid = b.fkid_app_io_coda where b.idcomune = ? and b.fkid_massive_d =? ");
	query.addEntity(AppIoCoda.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idRiga);
	List<AppIoCoda> ret = query.list();
	List<String> returnList = new ArrayList<String>();
	if (ret != null) {
	    for (AppIoCoda coda : ret) {
		returnList.add(coda.getStato());
	    }
	}
	return returnList;
    }

    @Override
    public List<Integer> getCodiciIstanzeDaDettaglioForIstanzeOneri(int idRiga) {

	StringBuilder sql = new StringBuilder();
	sql.append("select "); // 
	sql.append(" istanzeoneri.codiceistanza as codiceistanza "); // 
	sql.append(" from "); // 
	sql.append(" massive_dettaglio"); // 
	sql.append(" inner join boll_massive_d on "); // 
	sql.append(" boll_massive_d.idcomune=massive_dettaglio.idcomune and"); // 
	sql.append(" boll_massive_d.fkid_massive_d=massive_dettaglio.id "); // 
	sql.append(" inner join boll_gest_istanzeoneri on "); // 
	sql.append(" boll_massive_d.idcomune=boll_gest_istanzeoneri.idcomune and"); // 
	sql.append(" boll_massive_d.fkid_boll_gest_dettaglio=boll_gest_istanzeoneri.fk_bollgestdet_id"); // 
	sql.append(" inner join istanzeoneri on "); // 
	sql.append(" istanzeoneri.idcomune=boll_gest_istanzeoneri.idcomune and"); // 
	sql.append(" istanzeoneri.id=boll_gest_istanzeoneri.fk_codiceistanzeoneri"); // 
	sql.append(" where massive_dettaglio.idcomune=? "); // 
	sql.append(" and  massive_dettaglio.id=? "); // 
	sql.append(" group by istanzeoneri.codiceistanza");
	SQLQuery q = getSession().createSQLQuery(sql.toString()).addSynchronizedEntityClass(MassiveDettaglio.class)
		.addSynchronizedEntityClass(MessaggiMail.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, idRiga);
	q.addScalar("codiceistanza", Hibernate.INTEGER);
	return q.list();
    }

    @Override
    public List<Integer> getDocumentiDaFirmarePerIdTestataAndDettaglio(Integer idTestata, Integer idDettaglio) {

	String sql = "select fk_docdafirmare_id as iddoc " + //
		     "from massive_dett_docdafirmare " + //
		     "inner join massive_dettaglio on massive_dettaglio.idcomune=massive_dett_docdafirmare.idcomune and massive_dettaglio.id=massive_dett_docdafirmare.fkid_massive_d " + // 
		     "where massive_dettaglio.idcomune=:idcomune and massive_dettaglio.fkid_testata=:idtestata and massive_dettaglio.id=:iddettaglio order by massive_dett_docdafirmare.idcomune,massive_dett_docdafirmare.id";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MassiveDettDocdafirmare.class);
	q.addScalar("iddoc", Hibernate.INTEGER);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idtestata", idTestata);
	q.setInteger("iddettaglio", idDettaglio);
	return q.list();
    }

    @Override
    public void eliminaMassivaDettaglio(Integer idDettaglio) {

	MassiveDettaglio d = this.getById(idDettaglio);
	Set<MassiveDAllegati> allegati = d.getAllegati();
	for (MassiveDAllegati massiveDAllegati : allegati) {
	    delete(massiveDAllegati);
	}
	Set<MassiveDettMessaggimail> mail = d.getMail();
	for (MassiveDettMessaggimail m : mail) {
	    delete(m);
	}
	Set<MassiveDettDestinatari> massiveDettDestinataris = d.getMassiveDettDestinataris();
	for (MassiveDettDestinatari md : massiveDettDestinataris) {
	    delete(md);
	}
	delete(d);
	this.flush();
    }

    @Override
    public void insertMassiveDettDestinatari(MassiveDettDestinatari destinatari) {

	this.saveEntity(destinatari);
    }
}
