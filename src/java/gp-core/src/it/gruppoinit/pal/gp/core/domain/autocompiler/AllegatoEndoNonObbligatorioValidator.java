/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.autocompiler;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Validatore custom che serve per eliminare i controlli di validazione 
 * sull'obbligatorietà dei campi file nella tabella del quadro allegati.
 * in pratica il validator AllegatoEndoObbligatorioValidator verifica la presenza dell'allegato
 * non in base all'obbligatorietà definita dal CART per il campo
 * ma in base all'obbligatorietà di ciascun allegato così come definita nel BO.
 * Ma poiché nel caso STD_0 il campo file della tabella allegati è 
 * definito sempre come obbligatorio dalla modulistica questa yulteriore validazione interferisce con la prima.
 * Questo validator ha il compito di impedire che sia eseguita questa validazione
 * sull'obbligatorietà dell'allegato lasciando che sia solo AllegatoEndoObbligatorioValidator
 * ad occuparsi di questo tipo di validazione tenendo in considerazione solo le impostazioni di 
 * obbligatorietà degli allegati definite nel BO.
 * 
 * @author francol
 *
 */
@Component(value = "AllegatoEndoNonObbligatorioValidator")
public class AllegatoEndoNonObbligatorioValidator implements IAutocompilerValidator {

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreCampo(it.eng.suap.xengine.model.modulistica.CampoType, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreCampo(CampoType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions) {
	// non implementato perchè il validator si applica sempre a campi file
	return null;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreFile(it.eng.suap.xengine.model.modulistica.FileType, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreFile(FileType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions) {
	//modifico la condizione di obbligatorietà del campo file fissa a false per impedire la validazione di obbligatorietà standard della modulistica
	EspressioneType mandatoryExpr = new EspressioneType();
	mandatoryExpr.setValore(Boolean.FALSE);
	campo.setObbligatorio(mandatoryExpr);
	return new ArrayList<ErroreValidazione>();
    }

}
