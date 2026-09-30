package it.gruppoinit.pal.gp.core.features.protocollazione.logic;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class ClassificaResolver {

    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService;
    private Istanze istanza;

    public ClassificaResolver(AlberoprocService alberoprocService, AlberoprocProtocolloService alberoprocProtocolloService,
	    IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService, Istanze istanza) {

	this.alberoprocService = alberoprocService;
	this.alberoprocProtocolloService = alberoprocProtocolloService;
	this.vertProtoAttivoService = vertProtoAttivoService;
	this.istanza = istanza;
    }

    public String resolve() {

	if (this.istanza != null) {
	    Alberoproc ap = this.alberoprocService.findById(new PkId(istanza.getAlberoproc().getId().getCodice()));
	    AlberoprocHelper helper = this.alberoprocService.findAlberoprocHelper(ap);
	    List<PercorsoAlberoprocHelper> percorsoAlberoprocHelpers = helper.getPercorsoAlberoprocHelpers();
	    for (PercorsoAlberoprocHelper pah : percorsoAlberoprocHelpers) {
		AlberoprocProtocollo app = this.alberoprocProtocolloService.findByAlberoprocIdAndComune(pah.getId(),
			istanza.getComune().getCodicecomune());
		if (app != null && StringUtils.isNotBlank(app.getScProtclassifica())) {
		    return app.getScProtclassifica();
		}
	    }
	}
	return this.vertProtoAttivoService.getClassificaDefaultBO();
    }
}
