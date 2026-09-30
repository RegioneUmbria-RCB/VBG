package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

public class AlberoprocBusinessRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public AlberoprocBusinessRules() {

	buildCustomRules();
    }

    public AlberoprocBusinessRules(boolean isInsert, boolean isUpdate) {

	buildCustomRules();
	this.setInsert(isInsert);
	this.setUpdate(isUpdate);
    }

    @Override
    protected void buildCustomRules() {

	setInsert(false);
	setUpdate(false);
	customRules = new HashMap<String, Boolean>();
	for (CustomRuleEnum ruleEnum : CustomRuleEnum.values()) {
	    customRules.put(ruleEnum.name(), Boolean.TRUE);
	}
    }

    public enum CustomRuleEnum {
	eseguiOperazioniSuCache
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
