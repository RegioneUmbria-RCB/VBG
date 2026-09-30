package it.gruppoinit.pal.gp.core.service.rules;

public class OggettiBusinessRules extends BusinessRules {

    public OggettiBusinessRules() {

	buildCustomRules();
    }

    public OggettiBusinessRules(boolean isInsert, boolean isUpdate) {

	buildCustomRules();
	this.setInsert(isInsert);
	this.setUpdate(isUpdate);
    }

    public enum CustomRuleEnum {
    };

    /**
     * i valori di default di inserimento e aggiornamento sono impostati a false
     * 
     * @see BusinessRules#buildCustomRules()
     */
    protected void buildCustomRules() {

	setInsert(false);
	setUpdate(false);
    }

    /**
     * Nessuna regola custom per {@link OggettiBusinessRules}
     */
    public boolean getCustomRule(String rule) {

	return false;
    }

    /**
     * Nessuna regola custom per {@link OggettiBusinessRules}
     */
    public void setCustomRule(String rule, boolean value) {

    }
}
