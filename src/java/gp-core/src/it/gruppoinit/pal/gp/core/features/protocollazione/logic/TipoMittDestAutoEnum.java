package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

/**
 * <pre>
 TIPO_MITTDEST_AUTO
 Indica con quale dato deve essere valorizzato il mittente / destinatario per le protocollazioni automatiche, se una protocollazione è in arrivo allora il 
	    soggetto interessato sarà il mittente, se la protocollazione automatica sarà in partenza allora il soggetto sarà il destinatario.
	    Può assumere i seguenti valori: 
 se valorizzato a 0 (o nullo) il sistema indicherà come mittente / destinatario il Richiedente e l'Azienda, 
 se valorizzato a 1 verrà proposto solo il Richiedente, 
 se valorizzato a 2 il sistema indicherà come Mittenti solo l'Azienda, se, in questo caso l'Azienda non è presente indicherà solamente il Richiedente,
 se valorizzato a 3 il sistema indicherà l'azienda e il tecnico, se l'azienda non è presente indicherà il richiedente, 
 se valorizzato a 4 il sistema indicherà solo il tecnico, se non presente il richiedente.
 se valorizzato a 5 se azienda ha stesso cf di richiedente e qualifica del soggetto ha quella mappatura allora prendo richiedente
 * </pre>
 */
public enum TipoMittDestAutoEnum {

    RICHIEDENTE_AZIENDA("0"), //
    RICHIEDENTE("1"), //
    AZIENDA_O_RICHIEDENTE("2"), //
    TECNICO_E_AZIENDA_O_RICHIEDENTE("3"), //
    TECNICO_O_RICHIEDENTE("4"), //
    DITTA_INDIVIDUALE("5");

    private String valore;

    private TipoMittDestAutoEnum(String s) {

	valore = s;
    }

    public String getValore() {

	return valore;
    }

    public static TipoMittDestAutoEnum fromParametroVerticalizzazione(String valore) {

	if (StringUtils.isBlank(valore) || valore.equalsIgnoreCase("0")) {
	    return RICHIEDENTE_AZIENDA;
	} else if (valore.equalsIgnoreCase("1")) {
	    return RICHIEDENTE;
	} else if (valore.equalsIgnoreCase("2")) {
	    return AZIENDA_O_RICHIEDENTE;
	} else if (valore.equalsIgnoreCase("3")) {
	    return TECNICO_E_AZIENDA_O_RICHIEDENTE;
	} else if (valore.equalsIgnoreCase("4")) {
	    return TECNICO_O_RICHIEDENTE;
	} else if (valore.equalsIgnoreCase("5")) {
	    return DITTA_INDIVIDUALE;
	}
	throw new InvalidConfigurationException("Valore " + valore + " non trovato per enumerazione " + TipoMittDestAutoEnum.class);
    }
}
