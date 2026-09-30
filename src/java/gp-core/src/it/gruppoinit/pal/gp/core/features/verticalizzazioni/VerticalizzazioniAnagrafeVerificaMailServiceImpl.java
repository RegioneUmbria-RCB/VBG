package it.gruppoinit.pal.gp.core.features.verticalizzazioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.MailConfig;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.service.MailConfigService;

@Service
public class VerticalizzazioniAnagrafeVerificaMailServiceImpl implements IVerticalizzazioniAnagrafeVerificaMailService {

    @Autowired
    private VerticalizzazioniService service;
    private String nomeVerticalizzazione = "ANAGRAFE_VERIFICA_MAIL";
    private String parMailTipo = "VERIFICAMAIL_MAIL_TIPO";
    private String parAccountId = "VERIFICAMAIL_ACCOUNTID";
    @Autowired
    private MailConfigService mailConfigService;
    @Autowired
    private MailtipoService mailtipoService;

    @Override
    public boolean isAttiva() {

	return this.service.isAttiva(this.nomeVerticalizzazione);
    }

    @Override
    public MailConfig codiceVerificaMailAccountId() {

	Integer accountId = this.service.getInteger(this.nomeVerticalizzazione, this.parAccountId, -1);
	if (accountId < 0) {
	    return null;
	}
	MailConfig ret = mailConfigService.findById(new PkId(accountId));
	if (ret == null) {
	    throw new InvalidConfigurationException("Parametro " + this.parAccountId + " della verticalizzazione " + this.nomeVerticalizzazione +
						    " non configurato correttamente " + accountId);
	}
	return ret;
    }

    @Override
    public Mailtipo codiceVerificaMailEmailTipo() {

	Integer mailtipoId = this.service.getInteger(this.nomeVerticalizzazione, this.parMailTipo, -1);
	if (mailtipoId < 0) {
	    return null;
	}
	Mailtipo ret = mailtipoService.findById(new PkId(mailtipoId));
	if (ret == null) {
	    throw new InvalidConfigurationException("Parametro " + this.parMailTipo + " della verticalizzazione " + this.nomeVerticalizzazione +
						    " non configurato correttamente " + mailtipoId);
	}
	return ret;
    }
}
