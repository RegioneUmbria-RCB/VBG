package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

public class SecurityServiceRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public SecurityServiceRules() {

	this.setInsert(false);
	this.setUpdate(false);
	buildCustomRules();
    }

    /**
     * <ul>
     * <li>
     * {@link #permettiUtenteLoggatoNullo} default false</li>
     * </ul>
     */
    public enum CustomRuleEnum {
	/**
	 * 
	 */
	permettiUtenteLoggatoNullo
    };

    @Override
    protected void buildCustomRules() {

	setInsert(false);
	setUpdate(false);
	customRules = new HashMap<String, Boolean>();
	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    customRules.put(ruleEnum.name(), Boolean.FALSE);
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

    public void setpermettiUtenteLoggatoNullo(boolean permettiUtenteLoggatoNullo) {

	setCustomRule(CustomRuleEnum.permettiUtenteLoggatoNullo.name(), permettiUtenteLoggatoNullo);
    }

    public boolean ispermettiUtenteLoggatoNullo() {

	return getCustomRule(CustomRuleEnum.permettiUtenteLoggatoNullo.name());
    }
}
