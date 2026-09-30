package it.gruppoinit.pal.gp.core.features.protocollazione.logic.tipodocumento;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AlberoprocHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PercorsoAlberoprocHelper;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocolloSourceEnum;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.IVerticalizzazioneProtocolloAttivoService;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;

public class TipoDocumentoResolver {

    private AlberoprocService alberoprocService;
    private AlberoprocProtocolloService alberoprocProtocolloService;
    private IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService;
    private Istanze istanza;
    private ProtocolloSourceEnum tipoInserimento;

    public TipoDocumentoResolver(AlberoprocService alberoprocService, AlberoprocProtocolloService alberoprocProtocolloService,
	    IVerticalizzazioneProtocolloAttivoService vertProtoAttivoService, ProtocolloSourceEnum tipoInserimento, Istanze istanza) {

	this.alberoprocService = alberoprocService;
	this.alberoprocProtocolloService = alberoprocProtocolloService;
	this.vertProtoAttivoService = vertProtoAttivoService;
	this.tipoInserimento = tipoInserimento;
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
		if (app != null && StringUtils.isNotBlank(app.getScProttipodocumento())) {
		    return app.getScProttipodocumento();
		}
	    }
	}
	return ProtocolloSourceEnum.ON_LINE.equals(this.tipoInserimento) ? this.vertProtoAttivoService.getTipoDocumentoDefault()
		: this.vertProtoAttivoService.getTipoDocumentoDefaultBo();
    }
}
