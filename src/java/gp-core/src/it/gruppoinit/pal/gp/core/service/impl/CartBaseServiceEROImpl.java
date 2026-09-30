package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.helper.CartInfoDizionarioHelper;
import it.gruppoinit.pal.gp.core.service.CartBaseServiceERO;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.EntityValidationException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.sigepro.cart.service.ero.CartRfcBaseServiceERO;
import it.gruppoinit.sigepro.cart.service.utils.CartUtils;

import java.rmi.RemoteException;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.openspcoop.pdd.services.SPCoopException;
import org.openspcoop.pdd.services.SPCoopMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CartBaseServiceEROImpl extends CartBaseServiceImpl implements CartBaseServiceERO {

    private Logger log = LoggerFactory.getLogger(CartBaseServiceEROImpl.class);
    /**
     * Codice stp da associare alla voce di stp_categorie_endo1 quando
     * {@link WebConstants#VERTICALIZZAZIONE_CART_INVENTARIO}=
     * {@link WebConstants#VERTICALIZZAZIONE_CART_VALORE_S_INVENTARIO} e vengono definiti i tipi endo per gli endo di
     * tipo 2
     */
    public static final int CODICE_ALBEROPROC_TIPIENDO_DEFAULT = -15555;
    //private static final Logger log = LoggerFactory.getLogger(CartBaseServiceEROImpl.class);
    /*
    protected AllegatiService allegatiService;
    protected OggettiService oggettiService;
    protected VerticalizzazioniService verticalizzazioniService;
    */
    protected CartRfcBaseServiceERO<?> cartService;

    /*
    @Autowired
    public void setAllegatiService(AllegatiService allegatiService){
    this.allegatiService = allegatiService;
    }
    @Autowired
    public void setOggettiSeervice(OggettiService oggettiService){
    this.oggettiService = oggettiService;
    }
    
    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService){
    this.verticalizzazioniService = verticalizzazioniService;
    }
    */
    @Override
    protected CartRfcBaseServiceERO<?> getCartService() {

	return cartService;
    }

    @Override
    public abstract void elaboraMessaggio(String idEgov) throws RemoteException, SPCoopException;

    @Override
    public abstract CartInfoDizionarioHelper preElaboraMessaggio(String idEgov) throws RemoteException, SPCoopException;

    @Override
    public SPCoopMessage getMessage(String idEgov) throws RemoteException, SPCoopException {

	return getCartService().getMessage(idEgov);
    }

    @Override
    public void deleteMessage(String idEgov) throws RemoteException, SPCoopException {

	getCartService().deleteMessage(idEgov);
    }

    @Override
    public String[] getAllMessagesId() throws RemoteException, SPCoopException {

	return getCartService().getAllMessagesId();
    }

    @Override
    public String[] getNextMessagesId(int numeroDeiMessaggi) throws RemoteException, SPCoopException {

	return getCartService().getNextMessagesId(numeroDeiMessaggi);
    }

    @Override
    public void deleteAllMessages() throws RemoteException, SPCoopException {

	getCartService().deleteAllMessages();
    }

    @Override
    public void elaboraTuttiMessaggi() {

	// §§§BEGIN§§§
	try {
	    String[] mesgs = getNextMessagesId(100);
	    for (int i = 0; i < mesgs.length; i++) {
		elaboraMessaggio(mesgs[i]);
		FlashMessages.getInfos().add("Elaborato correttamente il messaggio: " + mesgs[i]);
	    }
	} catch (SPCoopException e) {
	    FlashMessages.getWarnings().add(
		    "Non è stato possibile elaborare il messaggio a causa di: [" + ((SPCoopException) e).getCodiceEccezione() + ":"
			    + ((SPCoopException) e).getDescrizioneEccezione() + "]");
	    log.error("Non è stato possibile elaborare il messaggio a causa di: " + CartUtils.SPCoopExceptionToString(e));
	} catch (RemoteException e) {
	    FlashMessages.getWarnings().add("Non è stato possibile elaborare il messaggio a causa di: " + e.getMessage());
	    log.error("Non è stato possibile elaborare il messaggio a causa di: {}", e);
	} catch (Exception e) {
	    String errorMsg = "Non è stato possibile elaborare il messaggio a causa di: ";
	    if (e instanceof BusinessValidationException) {
		List<InvalidValue> ivs = ((BusinessValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    errorMsg += invalidValue.getPropertyName() + " - " + invalidValue.getMessage() + ", ";
		}
	    } else if (e instanceof EntityValidationException) {
		List<InvalidValue> ivs = ((BusinessValidationException) e).getInvalidValues();
		for (InvalidValue invalidValue : ivs) {
		    errorMsg += invalidValue.getPropertyName() + " - " + invalidValue.getMessage() + ", ";
		}
	    } else {
		errorMsg += e.getMessage();
	    }
	    FlashMessages.getWarnings().add(errorMsg);
	    log.error("Non è stato possibile elaborare il messaggio a causa di: {}", e);
	}
	// §§§END§§§
    }
}