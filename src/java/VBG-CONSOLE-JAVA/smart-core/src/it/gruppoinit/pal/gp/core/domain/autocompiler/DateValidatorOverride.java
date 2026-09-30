/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.autocompiler;

import it.eng.suap.xengine.model.modulistica.CampoType;
import it.eng.suap.xengine.model.modulistica.FileType;
import it.eng.suap.xengine.model.modulistica.RangeDataType;
import it.eng.suap.xengine.model.modulistica.SetValoriType;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart;
import it.gruppoinit.pal.gp.core.domain.cart.ErroreValidazione;
import it.gruppoinit.pal.gp.core.domain.cart.IndiceIdSemantico;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciCampoHelper;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.stereotype.Component;

/**
 * @author francol Classe Validator da utilizzare nella configurazione dei campi a compilazione automatica. Questa
 *         classe in realtà si comporta da anti-validatore nel senso che non restituisce mai messaggi di errore ma serve
 *         invece per eliminare o modificare la validazione di un campo data già prevista dalla modulistica CART. Viene
 *         utilizzata per accettare qualunque valore di data (anche nel passato) per il campo DATA_COMPILAZIONE nei casi
 *         in cui i dati di un'istanza esistente vengono caricati nella modulistica FACCT nel BO.
 */
@Component(value = "DateValidatorOverride")
public class DateValidatorOverride implements IAutocompilerValidator {

    //private static final Logger log = LoggerFactory.getLogger(DateValidatorOverride);
    private static final String CFG_OPT_MASSIMO = "massimo";
    private static final String CFG_OPT_MINIMO = "minimo";
    private static final String CFG_OPT_MASSIMO_INCLUSO = "massimo_incluso";
    private static final String CFG_OPT_MINIMO_INCLUSO = "minimo_incluso";

    /**
     * 
     */
    public DateValidatorOverride() {

    }

    /**
     * Non viene di fatto eseguita nessuna validazione e quindi il metodo non restituisce mai nessun
     * {@link ErroreValidazione}. Il metodo (che viene chiamato prima delle validazioni CART sullo stesso campo)
     * modifica l'oggetto {@link CampoType} passato come primo argomento in modo che il range di valori data accettati
     * siano quelli passati nei parametri dell' AutocompilerApplication: - minimo: valori possibili 'oggi', 'unbounded',
     * o un data valida nel formato dd/mm/yyyy (default 'unbounded') - massimo: valori possibili 'oggi', 'unbounded', o
     * un data valida nel formato dd/mm/yyyy (default 'unbounded') - minimo_incluso: valori possibili 'true',
     * 'false'(default 'false') - massimo_incluso: valori possibili 'true', 'false'(default 'false')
     * 
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreCampo(it.eng.suap.xengine.model.modulistica.CampoType,
     *      java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String,
     *      java.lang.Integer, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreCampo(CampoType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	if (campo != null) {
	    SetValoriType setValori = campo.getSetValori();
	    if (setValori != null && setValori.getCalendario() != null) {
		RangeDataType rangeData = new RangeDataType();
		if (configOptions != null) {
		    List<ConfigOption> opts = configOptions.getConfigOptions();
		    for (ConfigOption opt : opts) {
			if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MINIMO)) {
			    rangeData.setMinimo(opt.getOptionValue());
			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MASSIMO)) {
			    rangeData.setMassimo(opt.getOptionValue());
			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MINIMO_INCLUSO)) {
			    boolean incluso = BooleanUtils.toBoolean(opt.getOptionValue());
			    rangeData.setMinimoIncluso(incluso);
			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MASSIMO_INCLUSO)) {
			    boolean incluso = BooleanUtils.toBoolean(opt.getOptionValue());
			    rangeData.setMassimoIncluso(incluso);
			}
		    }
		}
		setValori.getCalendario().setRangeValidita(rangeData);
	    }
	}
	return null;
    }

    /**
     * Non viene di fatto eseguita nessuna validazione e quindi il metodo non restituisce mai nessun
     * {@link ErroreValidazione}. Il metodo (che viene chiamato prima delle validazioni CART sullo stesso campo)
     * modifica l'oggetto {@link CampoType} passato come primo argomento in modo che il range di valori data accettati
     * siano quelli passati nei parametri dell' AutocompilerApplication: - minimo: valori possibili 'oggi', 'unbounded',
     * o un data valida nel formato dd/mm/yyyy (default 'unbounded') - massimo: valori possibili 'oggi', 'unbounded', o
     * un data valida nel formato dd/mm/yyyy (default 'unbounded') - minimo_incluso: valori possibili 'true',
     * 'false'(default 'false') - massimo_incluso: valori possibili 'true', 'false'(default 'false')
     * 
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreCampoDinamico(it.gruppoinit.pal.gp.core.domain.Dyn2Campi,
     *      java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String,
     *      java.lang.Integer, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreCampoDinamico(ModellidinamiciCampoHelper campoH, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	if (campoH != null) {
	    /*
	     * TODO impostare i vincoli di validazione specificati nella configurazione dell'autocompleter negli attributi del campo dinamico
	     * per ora non è stato implementato perchè non è utilizzato su campi dinamici
	     */
//	    SetValoriType setValori = campo.getSetValori();
//	    if (setValori != null && setValori.getCalendario() != null) {
//		RangeDataType rangeData = new RangeDataType();
//		if (configOptions != null) {
//		    List<ConfigOption> opts = configOptions.getConfigOptions();
//		    for (ConfigOption opt : opts) {
//			if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MINIMO)) {
//			    rangeData.setMinimo(opt.getOptionValue());
//			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MASSIMO)) {
//			    rangeData.setMassimo(opt.getOptionValue());
//			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MINIMO_INCLUSO)) {
//			    boolean incluso = BooleanUtils.toBoolean(opt.getOptionValue());
//			    rangeData.setMinimoIncluso(incluso);
//			} else if (opt.getOptionName().equalsIgnoreCase(CFG_OPT_MASSIMO_INCLUSO)) {
//			    boolean incluso = BooleanUtils.toBoolean(opt.getOptionValue());
//			    rangeData.setMassimoIncluso(incluso);
//			}
//		    }
//		}
//		setValori.getCalendario().setRangeValidita(rangeData);
//	    }
	}
	return null;
    }

    /* (non-Javadoc)
     * @see it.gruppoinit.pal.gp.core.domain.autocompiler.IAutocompilerValidator#validaValoreFile(it.eng.suap.xengine.model.modulistica.FileType, java.lang.String, it.gruppoinit.pal.gp.core.domain.cart.DatiDomandaCart, java.lang.String, java.lang.String, java.lang.Integer, it.gruppoinit.pal.gp.core.domain.autocompiler.ConfigOptions)
     */
    @Override
    public List<ErroreValidazione> validaValoreFile(FileType campo, String refModulo, DatiDomandaCart userData, String idQuadro, String idModulo,
	    IndiceIdSemantico rowIndex, ConfigOptions configOptions, String codiceComune) {

	return null;
    }
}
