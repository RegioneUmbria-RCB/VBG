package it.gruppoinit.pal.gp.core.features.movimenti.allegati.resolver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneTipoInstallazioneService;
import it.gruppoinit.pal.gp.core.features.sistema.TecnologiaPaginaEnum;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;

@Service
public class MovimentiAllegatiResolverFactoryServiceImpl implements MovimentiAllegatiResolverFactoryService {

    private IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService;
    private MovimentiService movimentiService;
    private MovimentiallegatiService movimentiallegatiService;
    private LetteretipoService letteretipoService;
    private Map<MovimentiAllegatiResolverEnum, IAllegatiResolver> mappa = new HashMap<MovimentiAllegatiResolverEnum, IAllegatiResolver>(0);

    @Autowired
    public MovimentiAllegatiResolverFactoryServiceImpl(IVerticalizzazioneTipoInstallazioneService verticalizzazioneTipoInstallazioneService,
	    MovimentiService movimentiService, MovimentiallegatiService movimentiallegatiService, DocumentMergeService documentMergeService,
	    OggettiService oggettiService, LetteretipoService letteretipoService) {

	this.verticalizzazioneTipoInstallazioneService = verticalizzazioneTipoInstallazioneService;
	this.movimentiService = movimentiService;
	this.movimentiallegatiService = movimentiallegatiService;
	this.letteretipoService = letteretipoService;
	this.mappa.put(MovimentiAllegatiODTResolver.resolverType(),
		new MovimentiAllegatiODTResolver(movimentiService, movimentiallegatiService, documentMergeService));
	this.mappa.put(MovimentiAllegatiRTFResolver.resolverType(),
		new MovimentiAllegatiRTFResolver(movimentiService, movimentiallegatiService, documentMergeService));
	this.mappa.put(MovimentiAllegatiRTFLegacyResolver.resolverType(), new MovimentiAllegatiRTFLegacyResolver(documentMergeService));
	this.mappa.put(MovimentiAllegatiGenericoResolver.resolverType(),
		new MovimentiAllegatiGenericoResolver(movimentiService, movimentiallegatiService, oggettiService));
    }

    public Movimentiallegati build(DocumentMergeHelper documentMergeHelper, Integer codiceMovimento, Integer codiceLettera)
	    throws MovimentiAllegatiResolverException {

	//1. Raccolta dei parametri
	Movimenti movimento = this.movimentiService.findById(new PkId(codiceMovimento));
	Letteretipo documento = this.letteretipoService.findById(new PkId(codiceLettera));
	MovimentiAllegatiResolverRequest request = new MovimentiAllegatiResolverRequest();
	request.setDocumentMergeHelper(documentMergeHelper);
	request.setCodiceIstanza(movimento.getIstanza().getId().getCodice());
	request.setCodiceMovimento(codiceMovimento);
	request.setTipoMovimento(movimento.getTipomovimento().getId().getTipomovimento());
	request.setCodiceLettera(codiceLettera);
	request.setCodiceOggetto(documento.getFile().getId().getCodice());
	request.setDescrizione(documento.getDescrizione());
	//2. Logica per identificare il resolver corretto
	IAllegatiResolver resolver;
	if (documento.getFile().getNomefile().toLowerCase().endsWith(".odt")) {
	    resolver = this.mappa.get(MovimentiAllegatiResolverEnum.ODT);
	} else if (documento.getFile().getNomefile().toLowerCase().endsWith(".rtf")) {
	    boolean paginaStampaDocTipoJava = this.verticalizzazioneTipoInstallazioneService.isAttiva()
		    && TecnologiaPaginaEnum.JAVA.equals(this.verticalizzazioneTipoInstallazioneService.paginaStampeDocTipo());
	    if (paginaStampaDocTipoJava) {
		resolver = this.mappa.get(MovimentiAllegatiResolverEnum.RTF);
	    } else {
		resolver = this.mappa.get(MovimentiAllegatiResolverEnum.LEGACY);
	    }
	} else {
	    resolver = this.mappa.get(MovimentiAllegatiResolverEnum.GENERICO);
	}
	//3. Generazione dell'allegato
	Integer codiceOggetto = resolver.generaAllegato(request);
	return this.movimentiallegatiService.findByOggetto(codiceOggetto);
    }
}
