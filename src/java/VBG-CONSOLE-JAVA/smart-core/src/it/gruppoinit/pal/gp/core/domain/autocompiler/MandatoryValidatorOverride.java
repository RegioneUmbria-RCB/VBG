/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.autocompiler;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.EspressioneType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Validatore custom che serve per eliminare i controlli di validazione sull'obbligatorietà dei campi file nella tabella
 * del quadro allegati. in pratica il validator AllegatoEndoObbligatorioValidator verifica la presenza dell'allegato non
 * in base all'obbligatorietà definita dal CART per il campo ma in base all'obbligatorietà di ciascun allegato così come
 * definita nel BO. Ma poiché nel caso STD_0 il campo file della tabella allegati è definito sempre come obbligatorio
 * dalla modulistica questa ulteriore validazione interferisce con la prima. Questo validator ha il compito di impedire
 * che sia eseguita questa validazione sull'obbligatorietà dell'allegato lasciando che sia solo
 * AllegatoEndoObbligatorioValidator ad occuparsi di questo tipo di validazione tenendo in considerazione solo le
 * impostazioni di obbligatorietà degli allegati definite nel BO.
 * 
 * @author francol
 *
 */
@Component(value = "MandatoryValidatorOverride")
public class MandatoryValidatorOverride implements IAutocompilerValidator {

    public final String OPTION_NAME_MANDATORY = "mandatory";

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreCampo(it.eng.suap.xengine.model.modulistica.CampoType, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreCampo(CampoType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	EspressioneType mandatoryExpr = new EspressioneType();
	Boolean isMandatory = this.isMandatory(configOptions);
	mandatoryExpr.setValore(isMandatory);
	campo.setObbligatorio(mandatoryExpr);
	return new ArrayList<ErroreValidazione>();
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreFile(it.eng.suap.xengine.model.modulistica.FileType, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreFile(FileType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	//modifico la condizione di obbligatorietà del campo file fissa a false per impedire la validazione di obbligatorietà standard della modulistica
	EspressioneType mandatoryExpr = new EspressioneType();
	Boolean isMandatory = this.isMandatory(configOptions);
	mandatoryExpr.setValore(isMandatory);
	campo.setObbligatorio(mandatoryExpr);
	return new ArrayList<ErroreValidazione>();
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreCampoDinamico(it.gruppoinit.pal.gp.core.domain.Dyn2Campi, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions, java.lang.String)
     */
    @Override
    public List<ErroreValidazione> validaValoreCampoDinamico(ModellidinamiciCampoHelper campo, String refModulo, DatiDomandaCart userData, String idQuadro,
	    String idModulo, IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	campo.setObbligatorio(this.isMandatory(configOptions));
	return new ArrayList<ErroreValidazione>();
    }

    private Boolean isMandatory(ConfigOptions configOptions) {

	Boolean isMandatory = Boolean.FALSE;
	if (configOptions != null) {
	    List<ConfigOption> opts = configOptions.getConfigOptions() != null ? configOptions.getConfigOptions() : new ArrayList<ConfigOption>();
	    String optValue = null;
	    for (Iterator iterator = opts.iterator(); iterator.hasNext();) {
		ConfigOption configOption = (ConfigOption) iterator.next();
		if (configOption.getOptionName().equals(OPTION_NAME_MANDATORY)) {
		    optValue = configOption.getOptionValue();
		    break;
		}
	    }
	    if (StringUtils.isNotBlank(optValue)) {
		Boolean optValueBool = BooleanUtils.toBooleanObject(optValue);
		if (optValueBool != null) {
		    isMandatory = optValueBool;
		}
	    }
	}
	return isMandatory;
    }
}
