package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

public class IstanzeBusinessRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public IstanzeBusinessRules() {

	buildCustomRules();
    }

    public IstanzeBusinessRules(boolean isInsert, boolean isUpdate) {

	buildCustomRules();
	this.setInsert(isInsert);
	this.setUpdate(isUpdate);
    }

    /**
     * 
     <ul>
     * <li>
     * {@link #generaAutomaticamenteDocumentiIstanza} default true</li>
     * <li>
     * {@link #generaAutomaticamenteModelliDinamiciIstanza} default true</li>
     * <li>
     * {@link #generaAutomaticamenteOneriIstanza} default true</li>
     * <li>
     * {@link #generaAutomaticamentePermessiIstanza} default true</li>
     * <li>
     * {@link #inserisciResponsabileProcedimentoseNonPresenteOperatore} default false</li>
     * <li>
     * {@link #inserimentoDaStc} default false</li>
     * <li>
     * {@link #inserimentoDaStcDiretto} default false</li>
     * <li>
     * {@link #forzaCancellazionePratica} default false</li>
     * </ul>
     * 
     */
    public enum CustomRuleEnum {
	/**
	 * In inserimento istanza e inventario procedimenti non recupera le informazioni dei documenti da
	 * AlberoprocDocumenti e TipiprocedureDocumenti e Allegati degli endo
	 */
	generaAutomaticamenteDocumentiIstanza,
	/**
	 * In inserimento istanza, inventario procedimenti, procedure non recupera le informazioni dei modelli e dei
	 * dati dinamici
	 */
	generaAutomaticamenteModelliDinamiciIstanza,
	/**
	 * In inserimento istanza, inventario procedimenti non recupera le informazioni degli oneri da inserire in
	 * automatico
	 * 
	 */
	generaAutomaticamenteOneriIstanza,
	/**
	 * 
	 */
	generaAutomaticamentePermessiIstanza,
	/**
	 * 
	 */
	inserisciResponsabileProcedimentoseNonPresenteOperatore,
	/**
	 * 
	 */
	inserimentoDaStc,
	/**
	 * Specifica che la chiamata al metodo inserimentoPratica del nodo NLA di backoffice proviene da un nodo NLA di
	 * frontend (discriminabile dalla presenza della costante $INSERIMENTO_DIRETTO$ nella sezione AltriDati del
	 * DettaglioPratica)
	 */
	inserimentoDaStcDiretto,
	/**
	 * Se true allora non esegue i controlli sulla cancellazione di una pratica (Default false)
	 */
	forzaCancellazionePratica
    };

    @Override
    protected void buildCustomRules() {

	setInsert(false);
	setUpdate(false);
	customRules = new HashMap<String, Boolean>();
	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    if (ruleEnum.name().equalsIgnoreCase(CustomRuleEnum.inserisciResponsabileProcedimentoseNonPresenteOperatore.name())
		    || ruleEnum.name().equalsIgnoreCase(CustomRuleEnum.inserimentoDaStc.name())
		    || ruleEnum.name().equalsIgnoreCase(CustomRuleEnum.inserimentoDaStcDiretto.name())
		    || ruleEnum.name().equalsIgnoreCase(CustomRuleEnum.forzaCancellazionePratica.name())) {
		customRules.put(ruleEnum.name(), Boolean.FALSE);
	    } else {
		customRules.put(ruleEnum.name(), Boolean.TRUE);
	    }
	}
    }

    @Override
    public boolean getCustomRule(String rule) {

	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    if (rule.equals(ruleEnum.name())) {
		return customRules.get(rule);
	    }
	}
	return false;
    }

    @Override
    public void setCustomRule(String rule, boolean value) {

	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    if (rule.equals(ruleEnum.name())) {
		customRules.put(rule, Boolean.valueOf(value));
	    }
	}
    }
}
