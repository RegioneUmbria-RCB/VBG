package it.gruppoinit.pal.gp.core.service.rules;

import java.util.HashMap;

public class AnagrafeBusinessRules extends BusinessRules {

    private HashMap<String, Boolean> customRules = new HashMap<String, Boolean>();

    public AnagrafeBusinessRules() {

	buildCustomRules();
    }

    public AnagrafeBusinessRules(boolean isInsert, boolean isUpdate) {

	buildCustomRules();
	this.setInsert(isInsert);
	this.setUpdate(isUpdate);
    }

    /**
     * <ul>
     * <li>
     * {@link #verificaIncongruenze}</li>
     * </ul>
     */
    public enum CustomRuleEnum {
	/**
	 * Quando viene trovata una anagrafica che corrisponde a quella ricercata viene fatta una verifica dei dati
	 * sensibili per verificare che effettivamente le due anagrafice coincidano. I dati confrontati sono i seguenti
	 * ( naturalmente il confronto può avvenire solamente se entrambe hanno il dato valorizzato ): <br />
	 * CODICEFISCALE ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * PARTITAIVA ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * NOMINATIVO ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * NOME ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * COMUNERESIDENZA ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * INDIRIZZO ( sia per quelle fisiche che per quelle giuridiche ) <br />
	 * DATANASCITA ( solo se l'anagrafica cercata è fisica ) SESSO ( solo se l'anagrafica cercata è fisica ) <br />
	 * CODCOMNASCITA ( solo se l'anagrafica cercata è fisica )<br />
	 * DATANOMINATIVO ( solo se l'anagrafica cercata è giuridica )<br />
	 * 
	 */
	verificaIncongruenze,
	/**
	 * Cerca l'anagrafica solamente su cf o piva (e tipo anagrafe F o G DA VERIFICARE) non utilizzando gli altri.
	 * deve rilanciare errore - comprensibile - se cf o piva non sono passati
	 */
	ricercaSoloCf_Piva,
	/**
	 * Non va a cercare su anagrafiche disabilitate
	 */
	escludiControlliSuAnagrafeDisabilitate,
	/**
	 * se {@link AnagrafeBusinessRules#isInsert()}==true inserisce l'anagrafica senza effettuare ulteriori controlli
	 * su esistenza anagrafe.
	 */
	forzaInserimentoAnagrafe
    };

    /**
     * I valori di default di insert, update e delle altre regole custom sono false
     * 
     * @see BusinessRules#buildCustomRules()
     */
    protected void buildCustomRules() {

	this.setInsert(false);
	this.setUpdate(false);
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
}
