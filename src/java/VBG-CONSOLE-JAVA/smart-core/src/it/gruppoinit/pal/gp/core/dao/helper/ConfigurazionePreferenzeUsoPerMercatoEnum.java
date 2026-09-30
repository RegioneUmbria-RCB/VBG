package it.gruppoinit.pal.gp.core.dao.helper;

/**
 * <pre>
 * Elenca le possbili situazioni che si possono verificare sulla configurazione delle preferenza di un particolare USO
 * (MERCATIUSO) per un mercato.
 * 
 * MERCATO_USO_DA_ALBERO="mercato_uso_da_albero" 		: la preferenza è segnalata sulla voce dell'albero a cui è collegtao il mercato 
 * MERCATO_NON_CONFIG_SU_ALBERO="mercato_non_config_su_albero" 	: l'albero scelto non ha un mercato configurato
 * MERCATO_USO_CONFIG_DA_MERCATI="mercato_uso_da_config_mercato": la configurazione sulla preferenza dell'uso è stata configurata sul mercato
 * 								   tramite campi dinamici
 * PREFERENZA_USO_NON_CONFIG="preferenza_uso_non_config"	: Non è stato configurata nessuna preferenza sull'uso 
 * MERCATO_USO_NON_DA_ALBERO="mercato_uso_non_da_albero"        : Non  stata configurato un uso sulla voce del'albero a cui è assciato il mercato
 * MERCATO_USO_NON_CONFIG_DA_MERCATI="mercato_uso_non_da_albero": Non è stata configurata nessuna preferenza sull'uso del mercato dai dati dinamici 
 * 								  del mercato
 * MERCATI_CONFIG_SU_ALBERO = "mercati_config_su_albero"	: Sono stati configurati mercati diversi sugli interventi associati al bando (Caso multi intervento)
 * 
 * MERCATI_USO_CONFIG_SU_ALBERO = "mercati_uso_config_da_albero":Sono stati configurati mercati uso diversi sugli interventi associati al bando (Caso multi intervento)
 * 
 * @author gianpaolot
 * </pre>
 */
public enum ConfigurazionePreferenzeUsoPerMercatoEnum {
    MERCATO_USO_DA_ALBERO("mercato_uso_da_albero"), MERCATO_NON_CONFIG_SU_ALBERO("mercato_non_config_su_albero"), MERCATO_USO_CONFIG_DA_MERCATI(
	    "mercato_uso_config_da_mercato"), PREFERENZA_USO_NON_CONFIG("preferenza_uso_non_config"), MERCATO_USO_NON_DA_ALBERO(
	    "mercato_uso_non_da_albero"), MERCATO_USO_NON_CONFIG_DA_MERCATI("mercato_uso_non_config_da_albero"), MERCATI_CONFIG_SU_ALBERO(
	    "mercati_config_su_albero"), MERCATI_USO_CONFIG_SU_ALBERO("mercati_uso_config_da_albero");

    private String value;

    private ConfigurazionePreferenzeUsoPerMercatoEnum(String v) {

	value = v;
    }

    public String value() {

	return value;
    }
}
