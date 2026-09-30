package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

/**
 * La seguente Business Rule serve per effettuare lo skip della validazione di business o delle entità di dominio
 * all'interno dei vari services. In pratica di default la validazione viene effettuata.<br />
 * Programmi di importazione/aggiornamento potrebbero settare questo parametro a false per velocizzare gli inserimenti
 * update. P.s.: Le configurazioni di insert/update non devono essere impostate
 * 
 * @author riccardob
 * 
 */
public class ServiceValidationRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public ServiceValidationRules() {

	super();
	buildCustomRules();
    }

    /**
     * 
     <ul>
     * <li>
     * {@link #doBusinessValidation} default true</li>
     * <li>
     * {@link #doEntityValidation} default true</li>
     * </ul>
     * 
     */
    public enum CustomRuleEnum {
	/**
	 * 
	 */
	doBusinessValidation,
	/**
	 * 
	 */
	doEntityValidation
    };

    @Override
    protected void buildCustomRules() {

	setInsert(false);
	setUpdate(false);
	customRules = new HashMap<String, Boolean>();
	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    customRules.put(ruleEnum.name(), Boolean.TRUE);
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
