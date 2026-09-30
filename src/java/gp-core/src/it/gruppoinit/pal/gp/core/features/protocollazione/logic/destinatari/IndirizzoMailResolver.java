package it.gruppoinit.pal.gp.core.features.protocollazione.logic.destinatari;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;

public class IndirizzoMailResolver {

    private Integer regola;

    public IndirizzoMailResolver(Integer regolaGestionePEC, String codiceComune, String software) {

	this.regola = regolaGestionePEC;
    }

    public IndirizzoMailResolver(IVerticalizzazioneProtocolloAttivoService service, String codiceComune, String software) {

	this.regola = service.getGestionePEC();
    }

    public String getMailAnagrafe(Anagrafe anagrafe, String domicilioElettronico, TipoDestinatarioEnum tipoDestinatario) {

	switch (this.regola) {
	case 0: {
	    // le anagrafiche relative a Richiedente e Titolare Legale avranno come indirizzo PEC il 
	    // Domicilio Elettronico della pratica, se non presente sarà valorizzato l'indirizzo PEC 
	    // dell'anagrafica stessa.
	    boolean richiedenteOAzienda = TipoDestinatarioEnum.RICHIEDENTE.equals(tipoDestinatario)
		    || TipoDestinatarioEnum.AZIENDA.equals(tipoDestinatario);
	    if (richiedenteOAzienda && StringUtils.isNotBlank(domicilioElettronico)) {
		return domicilioElettronico;
	    }
	    return anagrafe.getPec();
	}
	case 2: {
	    // se valorizzato a 2 allora la logica sarà la stessa indicata con il valore 1
	    // con la differenza che, se non presenti, la logica tornerà ad essere la stessa con il valore 0
	    boolean richiedenteOAzienda = TipoDestinatarioEnum.RICHIEDENTE.equals(tipoDestinatario)
		    || TipoDestinatarioEnum.AZIENDA.equals(tipoDestinatario);
	    if (StringUtils.isBlank(anagrafe.getPec()) && richiedenteOAzienda) {
		return domicilioElettronico;
	    }
	    return anagrafe.getPec();
	}
	case 3: {
	    // la logica sarà la stessa del valore 2, con la differenza che la logica varrà solo per il titolare legale
	    boolean azienda = TipoDestinatarioEnum.AZIENDA.equals(tipoDestinatario);
	    if (StringUtils.isBlank(anagrafe.getPec()) && azienda) {
		return domicilioElettronico;
	    }
	    return anagrafe.getPec();
	}
	case 4: {
	    // le pec dell'azienda e del richiedente saranno quelle delle anagrafiche, solo il tecnico avrà 
	    // il domicilio elettronico come PEC, a meno che l'azienda non abbia la pec e 
	    // non sia presente il tecnico
	    if (TipoDestinatarioEnum.PROFESSIONISTA.equals(tipoDestinatario)) {
		return domicilioElettronico;
	    }
	    if ((TipoDestinatarioEnum.RICHIEDENTE.equals(tipoDestinatario) || TipoDestinatarioEnum.AZIENDA.equals(tipoDestinatario))
		    && !StringUtils.isBlank(domicilioElettronico)) {
		return domicilioElettronico;
	    }
	    return anagrafe.getPec();
	}
	default:
	    return anagrafe.getPec();
	}
    }
}