/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.helper;

import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;

/**
 * @author francol
 *
 */
public class PayConfigurationHelper {

    private static ThreadLocal<PayProfiliEntiCreditori> tlProfiloEnte = new ThreadLocal<PayProfiliEntiCreditori>();
    private static ThreadLocal<String> documentiFilesystemPath = new ThreadLocal<String>();
    private boolean contabilitaAttiva = false;
    private boolean inserisciCausaliRegistrazione = false;
    private boolean inserisciConti = false;
    private boolean consentiAnnullamentoRipetuto = true;

    public static PayProfiliEntiCreditori getProfiloEnteCreditore() {

	return tlProfiloEnte.get();
    }

    public static void setProfiloEnteCreditore(PayProfiliEntiCreditori tlProfiloEnte) {

	PayConfigurationHelper.tlProfiloEnte.set(tlProfiloEnte);
    }

    public static boolean isDocumentiSuFilesystem() {

	return PayConfigurationHelper.documentiFilesystemPath.get() == null ? Boolean.FALSE : Boolean.TRUE;
    }

    public static String getDocumentiFilesystemPath() {

	return documentiFilesystemPath.get();
    }

    public static void setDocumentiFilesystemPath(String rootPath) {

	documentiFilesystemPath.set(rootPath);
    }

    public boolean isContabilitaAttiva() {

	return this.contabilitaAttiva;
    }

    public void setContabilitaAttiva(boolean contabilitaAttiva) {

	this.contabilitaAttiva = contabilitaAttiva;
    }

    public boolean isInserisciCausaliRegistrazione() {

	return inserisciCausaliRegistrazione;
    }

    public void setInserisciCausaliRegistrazione(boolean inserisciCausaliRegistrazione) {

	this.inserisciCausaliRegistrazione = inserisciCausaliRegistrazione;
    }

    public boolean isInserisciConti() {

	return inserisciConti;
    }

    public void setInserisciConti(boolean inserisciConti) {

	this.inserisciConti = inserisciConti;
    }

    public boolean isConsentiAnnullamentoRipetuto() {

	return consentiAnnullamentoRipetuto;
    }

    public void setConsentiAnnullamentoRipetuto(boolean consentiAnnullamentoRipetuto) {

	this.consentiAnnullamentoRipetuto = consentiAnnullamentoRipetuto;
    }
}
