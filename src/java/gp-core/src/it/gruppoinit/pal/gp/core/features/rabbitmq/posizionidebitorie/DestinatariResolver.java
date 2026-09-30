package it.gruppoinit.pal.gp.core.features.rabbitmq.posizionidebitorie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CfRichiedentiBean;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CodiciFiscaliDestinatariBean;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;

public class DestinatariResolver {

    private IstanzeService istanzeService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private AnagrafeService anagrafeService;

    public DestinatariResolver(IstanzeService istanzeService, IstanzerichiedentiService istanzerichiedentiService, AnagrafeService anagrafeService) {

	this.istanzeService = istanzeService;
	this.istanzerichiedentiService = istanzerichiedentiService;
	this.anagrafeService = anagrafeService;
    }

    protected CodiciFiscaliDestinatariBean calcolaDestinatari(Integer codiceIstanza) {

	CodiciFiscaliDestinatariBean ret = new CodiciFiscaliDestinatariBean();
	Set<String> cfSet = new HashSet<String>();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	//richiedente, se persona fisica oppure se giuridica ed ha cf=16
	if (istanza.getRichiedente() != null && StringUtils.isNotBlank(istanza.getRichiedente().getCodicefiscale())
		&& (istanza.getRichiedente().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_FISICA)
			|| (istanza.getRichiedente().getTipoanagrafe().equalsIgnoreCase(WebConstants.PERSONA_GIURIDICA)
				&& istanza.getRichiedente().getCodicefiscale().length() == 16))) {
	    cfSet.add(istanza.getRichiedente().getCodicefiscale());
	}
	//intermediario
	if (istanza.getProfessionista() != null) {
	    Anagrafe intermediario = anagrafeService.findById(new PkId(istanza.getProfessionista().getId().getCodice()));
	    if (StringUtils.isNotBlank(intermediario.getCodicefiscale()) && intermediario.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)
		    || (intermediario.getTipoanagrafe().equals(WebConstants.PERSONA_GIURIDICA) && intermediario.getCodicefiscale().length() == 16)) {
		cfSet.add(intermediario.getCodicefiscale());
	    }
	}
	//istanzerichiedenti
	List<CfRichiedentiBean> istanzerichiedentis = istanzerichiedentiService.findBeanByCodiceIstanza(codiceIstanza);
	for (CfRichiedentiBean irb : istanzerichiedentis) {
	    if (StringUtils.defaultString(irb.getCodicefiscale()).length() == 16) {
		// solo se persone fisiche e codice fiscale presente
		cfSet.add(irb.getCodicefiscale());
	    }
	}
	List<String> cfList = new ArrayList<String>(cfSet);
	ret.setCodiciFiscaliDestinatari(cfList);
	return ret;
    }
}
