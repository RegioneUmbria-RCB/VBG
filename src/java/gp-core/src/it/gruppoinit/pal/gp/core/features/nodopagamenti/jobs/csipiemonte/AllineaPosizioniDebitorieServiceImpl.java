package it.gruppoinit.pal.gp.core.features.nodopagamenti.jobs.csipiemonte;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.DettPosizioneDebitoriaBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;

@Service
public class AllineaPosizioniDebitorieServiceImpl implements AllineaPosizioniDebitorieService {

    private static final Logger log = LoggerFactory.getLogger(AllineaPosizioniDebitorieServiceImpl.class);
    private AllineaPosizioniDebitorieDAO allineaPosizioniDebitorieDAO;
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;

    @Autowired
    public void setAllineaPosizioniDebitorieDAO(AllineaPosizioniDebitorieDAO allineaPosizioniDebitorieDAO) {

	this.allineaPosizioniDebitorieDAO = allineaPosizioniDebitorieDAO;
    }

    @Autowired
    public void setDettPosizioneDebitoriaService(DettPosizioneDebitoriaService dettPosizioneDebitoriaService) {

	this.dettPosizioneDebitoriaService = dettPosizioneDebitoriaService;
    }

    @Override
    public void allinea() {

	log.debug("AllineaPosizioniDebitorieServiceImpl inizio allineamento posizioni debitorie");
	try {
	    List<DettPosizioneDebitoriaBean> elenco = this.allineaPosizioniDebitorieDAO.getPosizioniDaAllineare();
	    log.debug("AllineaPosizioniDebitorieServiceImpl trovate {} posizioni da allineare", elenco.size());
	    for (DettPosizioneDebitoriaBean posizione : elenco) {
		log.debug("AllineaPosizioniDebitorieServiceImpl inizio allineamento posizione {}", posizione.getId());
		DettPosizioneDebitoria dpd = this.dettPosizioneDebitoriaService.findById(new PkId(posizione.getId()));
		dpd.setCodiceAvviso(posizione.getCodiceAvviso());
		dpd.setDataUltimoStato(posizione.getDataEvento());
		dpd.setDescStato(posizione.getDescStato());
		dpd.setIuv(posizione.getIuv());
		dpd.setQrcode(posizione.getQrCode());
		dpd.setStato(posizione.getStato());
		this.dettPosizioneDebitoriaService.update(dpd);
		log.debug("AllineaPosizioniDebitorieServiceImpl fine allineamento posizione {}", posizione.getId());
	    }
	} catch (Exception e) {
	    log.error("AllineaPosizioniDebitorieServiceImpl errore: ", e);
	}
	log.debug("AllineaPosizioniDebitorieServiceImpl fine allineamento posizioni debitorie");
    }
}
