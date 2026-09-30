package it.gruppoinit.pal.gp.core.features.messaggimail;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.MessaggiMail;
import it.gruppoinit.pal.gp.core.domain.MessaggiMailAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.IAllegatoMail;
import it.gruppoinit.pal.gp.core.features.messaggimail.model.IMessaggiMail;

@Repository
public class MessaggiMailDAOImpl extends BaseDAOImpl<MessaggiMail, PkId> implements IMessaggiMailDAO {

    @Override
    public Integer salvaMessaggioMail(IMessaggiMail messaggio) {

	MessaggiMail messaggiMail = new MessaggiMail();
	messaggiMail.setCorpo(messaggio.getCorpo());
	messaggiMail.setDataInvio(messaggio.getDataInvio());
	messaggiMail.setDestinatario(messaggio.getDestinatario());
	messaggiMail.setDestinatariocc(messaggio.getDestinatariocc());
	messaggiMail.setDestinatarioabcc(messaggio.getDestinatariobcc());
	messaggiMail.setMessageId(messaggio.getMessageId());
	messaggiMail.setMittente(messaggio.getMittente());
	messaggiMail.setOggetto(messaggio.getOggetto());
	Integer accountId = messaggio.getAccountId();
	if (messaggio.getIdPadre() != null) {
	    messaggiMail.setMessaggiMailPadre((MessaggiMail) this.getById(MessaggiMail.class, new PkId(messaggio.getIdPadre())));
	    if (accountId == null && messaggiMail.getMessaggiMailPadre() != null && messaggiMail.getMessaggiMailPadre().getaccountId() != null
		    && messaggiMail.getMessaggiMailPadre().getaccountId() != null
		    && messaggiMail.getMessaggiMailPadre().getaccountId().getId() != null
		    && messaggiMail.getMessaggiMailPadre().getaccountId().getId().getCodice() != null) {
		accountId = messaggiMail.getMessaggiMailPadre().getaccountId().getId().getCodice();
	    }
	}
	if (accountId != null) {
	    messaggiMail.setAccountId((MailConfig) this.getById(MailConfig.class, new PkId(accountId)));
	}
	this.saveEntity(messaggiMail);
	if (messaggio.getAllegati() != null) {
	    for (IAllegatoMail at : messaggio.getAllegati()) {
		if (at.getCodiceOggetto() != null) {
		    MessaggiMailAllegati mailAllegati = new MessaggiMailAllegati();
		    mailAllegati.setmessaggiMail(messaggiMail);
		    mailAllegati.setCodiceOggetto(at.getCodiceOggetto());
		    this.saveEntity(mailAllegati);
		}
	    }
	}
	return messaggiMail.getId().getCodice();
    }

    @Override
    public Class<MessaggiMail> getEntityClass() {

	return MessaggiMail.class;
    }

    @Override
    public Integer trovaPadreDelMessaggioConId(String messageId) {

	String sql = "select id from messaggi_mail where idcomune=? and message_id=? order by id desc";
	SQLQuery q = getSession().createSQLQuery(sql).addSynchronizedEntityClass(MessaggiMail.class);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, messageId);
	q.addScalar("id", Hibernate.INTEGER);
	List<Integer> l = q.list();
	if (l.isEmpty()) {
	    return null;
	}
	return l.get(0);
    }
}
