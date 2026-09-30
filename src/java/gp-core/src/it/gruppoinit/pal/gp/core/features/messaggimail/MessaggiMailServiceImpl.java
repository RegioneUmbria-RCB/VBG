package it.gruppoinit.pal.gp.core.features.messaggimail;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.messaggimail.model.IMessaggiMail;

@Service
public class MessaggiMailServiceImpl implements IMessaggiMailService {

    private org.slf4j.Logger log = LoggerFactory.getLogger(MessaggiMailServiceImpl.class);
    @Autowired
    private IMessaggiMailDAO messaggiMailDAO;

    @Override
    public Integer collegaRicevutaAMessaggioMail(IMessaggiMail mail, Integer idPadre) {

	log.debug("mail.getMessageId() {}- mail padre {}", mail.getMessageId(), idPadre);
	if (idPadre == null) {
	    return null;
	}
	mail.setIdPadre(idPadre);
	mail.setMessageId(null); //Setto a null il messageID se specificato il padre
	Integer messaggio = messaggiMailDAO.salvaMessaggioMail(mail);
	log.debug("mail.getMessageId() {}- mail padre {} messaggio salvato {}", new Object[] { mail.getMessageId(), idPadre, messaggio });
	return messaggio;
    }

    @Override
    public Integer trovaPadreDelMessaggioConId(String messageId) {

	return messaggiMailDAO.trovaPadreDelMessaggioConId(messageId);
    }
}
