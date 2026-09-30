package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;
import java.util.Map;

public class SigeproBusinessRules {

    private static ThreadLocal<Boolean> helperCancellazioneDatiMaster = new ThreadLocal<Boolean>();
    private static ThreadLocal<Map<Class<? extends ManagingRules>, BusinessRules>> rulesHelper = new ThreadLocal<Map<Class<? extends ManagingRules>, BusinessRules>>();

    public static Boolean getCancellazioneDatiMaster() {

	return helperCancellazioneDatiMaster.get();
    }

    public static void setCancellazioneDatiMaster(Boolean cancellazioneDatiMaster) {

	helperCancellazioneDatiMaster.set(cancellazioneDatiMaster);
    }

    public static Map<Class<? extends ManagingRules>, BusinessRules> getRules() {

	Map<Class<? extends ManagingRules>, BusinessRules> rules = rulesHelper.get();
	if (rules == null || rules.isEmpty()) {
	    buildDefaultRules();
	}
	return rulesHelper.get();
    }

    public static BusinessRules getClassRules(Class<? extends ManagingRules> clazz) {

	Map<Class<? extends ManagingRules>, BusinessRules> rules = getRules();
	return rules.get(clazz);
    }

    public static void setClassRules(Class<? extends ManagingRules> clazz, BusinessRules rule) {

	Map<Class<? extends ManagingRules>, BusinessRules> rules = getRules();
	rules.put(clazz, rule);
    }

    /**
     * Resetta le business rules ai valori di default
     */
    public static void buildDefaultRules() {

	Map<Class<? extends ManagingRules>, BusinessRules> rules = rulesHelper.get();
	if (rules == null) {
	    rules = new HashMap<Class<? extends ManagingRules>, BusinessRules>();
	}
	AlberoprocBusinessRules alberoprocRule = new AlberoprocBusinessRules();
	rules.put(AlberoprocBusinessRules.class, alberoprocRule);
	AnagrafeBusinessRules anagrafeRule = new AnagrafeBusinessRules();
	rules.put(AnagrafeBusinessRules.class, anagrafeRule);
	IstanzeBusinessRules istanzeBindingRules = new IstanzeBusinessRules();
	rules.put(IstanzeBusinessRules.class, istanzeBindingRules);
	OggettiBusinessRules oggettiBindingRule = new OggettiBusinessRules();
	rules.put(OggettiBusinessRules.class, oggettiBindingRule);
	OperazioniAutomaticheBusinessRules operazioniAutomaticheBusinessRules = new OperazioniAutomaticheBusinessRules();
	rules.put(OperazioniAutomaticheBusinessRules.class, operazioniAutomaticheBusinessRules);
	ServiceValidationRules serviceValidationRules = new ServiceValidationRules();
	rules.put(ServiceValidationRules.class, serviceValidationRules);
	SecurityServiceRules securityServiceRules = new SecurityServiceRules();
	rules.put(SecurityServiceRules.class, securityServiceRules);
	rulesHelper.set(rules);
    }
}
