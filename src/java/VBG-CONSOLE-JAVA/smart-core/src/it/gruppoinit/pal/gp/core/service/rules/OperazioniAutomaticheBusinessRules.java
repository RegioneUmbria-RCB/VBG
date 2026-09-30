package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

public class OperazioniAutomaticheBusinessRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public OperazioniAutomaticheBusinessRules() {

	this.setInsert(false);
	this.setUpdate(false);
	buildCustomRules();
    }

    @Override
    protected void buildCustomRules() {

	this.setInsert(false);
	this.setUpdate(false);
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

    public void setProtocollazioneIstanza(boolean attiva) {

	setCustomRule(CustomRuleEnum.protocollazioneIstanza.name(), attiva);
    }

    public void setNotificaStcAutomatica(boolean attiva) {

	setCustomRule(CustomRuleEnum.notificaStcAutomatica.name(), attiva);
    }

    public boolean isProtocollazioneIstanza() {

	return getCustomRule(CustomRuleEnum.protocollazioneIstanza.name());
    }

    public boolean isNotificaStcAutomatica() {

	return getCustomRule(CustomRuleEnum.notificaStcAutomatica.name());
    }

    /**
     * <ul>
     * <li>
     * {@link #protocollazioneIstanza}</li>
     * <li>
     * {@link #notificaStcAutomatica}</li>
     * </ul>
     */
    public enum CustomRuleEnum {
	/**
	 * In fase di inserimento di una istanza esegue la protocollazione (Default true)
	 */
	protocollazioneIstanza,
	/**
	 * In fase di inserimento di un movimento esegue la notifica automatica tramite STC (Default true)
	 */
	notificaStcAutomatica
    };
}
