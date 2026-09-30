package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.ManipulatePdfService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.QrcodeService.TipoImmagine;

@Service
public class ApplicaQRCodeServiceImpl implements IApplicaQRCodeService {

    private static Logger log = LoggerFactory.getLogger(ApplicaQRCodeServiceImpl.class);
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IstanzeService istanzeservice;
    @Autowired
    private MovimentiService movimentiservice;
    @Autowired
    private QrcodeService qrcodeService;
    @Autowired
    private ManipulatePdfService manipulatePdfService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;

    @Override
    public void applicaQRCode(Integer codice, Oggetti oggetto, boolean isIstanza) {

	log.debug("#applicaQRCode - Inizio.. ");
	if (new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, ORMHelper.getIdcomune()).isAttiva()) {
	    log.debug("#applicaQRCode - la verticalizzazione è stata attivata..");
	    GenerazioneQRCodeHelper qr = new GenerazioneQRCodeHelper(verticalizzazioniService);
	    Istanze i = null;
	    Movimenti m = null;
	    if (isIstanza) {
		i = istanzeservice.findById(new PkId(codice));
		log.debug("#applicaQRCode - istanza n. {} ", i.getNumeroistanza());
	    } else {
		m = movimentiservice.findById(new PkId(codice));
		i = m.getIstanza();
		log.debug("#applicaQRCode - movimento n. {} - istanza n. {} ", m.getId().getCodice(), i.getNumeroistanza());
	    }
	    String software = isIstanza ? i.getSoftware().getCodice() : m.getIstanza().getSoftware().getCodice();
	    String url = qr.getUrl();
	    String codiceLettera = qr.getCodLetteraTipo();
	    String chiaveMAC = qr.getChiaveMAC();
	    if (StringUtils.isBlank(url)) {
		log.error("L'url passato è nullo");
		return;
	    }
	    if (StringUtils.isBlank(codiceLettera)) {
		log.error("Non è stato settato correttamente il codice della lettera tipo.");
		return;
	    }
	    if (StringUtils.isEmpty(chiaveMAC)) {
		log.error("Non è stata inserita la verticalizzazione chiave_mac ");
		return;
	    }
	    String alias = ORMHelper.getIdcomuneAlias();
	    url = url.replace("{alias}", alias);
	    url = url.replace("{software}", software);
	    String uuid = isIstanza ? i.getUuid() : m.getIstanza().getUuid();
	    url = url.replace("{guid}", uuid);
	    url = url.replace("{idtemplate}", codiceLettera);
	    String mac = this.encode(alias + software + uuid + codiceLettera + chiaveMAC);
	    url = url.replace("{mac}", mac);
	    byte[] qrcode = qrcodeService.createQRcode(url, Integer.valueOf((int) qr.getWidth()), Integer.valueOf((int) qr.getHeigth()),
		    TipoImmagine.PNG);
	    if (oggetto == null) {
		if (isIstanza) {
		    Set<Documentiistanza> documentiistanzas = i.getDocumentiistanzas();
		    if (documentiistanzas != null) {
			for (Documentiistanza di : documentiistanzas) {
			    if (di.getOggetto() == null || di.getOggetto().getId() == null || di.getOggetto().getId().getCodice() == null) {
				continue;
			    }
			    Oggetti o = oggettiService.findById(new PkId(di.getOggetto().getId().getCodice()));
			    if (o.getNomefile().contains(".pdf") && !o.getNomefile().contains(".p7m")) {
				log.debug("#applicaQRCode - Oggetto istanza con codice {}", o.getId().getCodice());
				byte[] pdfModificato = manipulatePdfService.addImageToPDF(o.getOggetto(), qrcode, qr.getNumPag(),
					Integer.valueOf((int) qr.getPosX()), Integer.valueOf((int) qr.getPosY()));
				o.setOggetto(pdfModificato);
				oggettiService.update(o);
				di.setOggetto(o);
				documentiistanzaService.update(di);
			    }
			}
		    }
		} else {
		    List<Movimentiallegati> movAllegatiList = movimentiallegatiService.findByMovimento(m.getId().getCodice());
		    if (!movAllegatiList.isEmpty()) {
			for (Movimentiallegati movimentiallegati : movAllegatiList) {
			    if (movimentiallegati.getOggetto() == null || movimentiallegati.getOggetto().getId() == null
				    || movimentiallegati.getOggetto().getId().getCodice() == null) {
				continue;
			    }
			    Oggetti o = oggettiService.findById(new PkId(movimentiallegati.getOggetto().getId().getCodice()));
			    if (o.getNomefile().contains(".pdf") && !o.getNomefile().contains(".p7m")) {
				log.debug("#applicaQRCode - Oggetto movimento con codice {}", o.getId().getCodice());
				byte[] pdfModificato = manipulatePdfService.addImageToPDF(o.getOggetto(), qrcode, qr.getNumPag(),
					Integer.valueOf((int) qr.getPosX()), Integer.valueOf((int) qr.getPosY()));
				o.setOggetto(pdfModificato);
				oggettiService.update(o);
				movimentiallegati.setOggetto(o);
				movimentiallegatiService.update(movimentiallegati);
			    }
			}
		    }
		}
	    } else {
		byte[] pdfModificato = manipulatePdfService.addImageToPDF(oggetto.getOggetto(), qrcode, qr.getNumPag(),
			Integer.valueOf((int) qr.getPosX()), Integer.valueOf((int) qr.getPosY()));
		oggetto.setOggetto(pdfModificato);
	    }
	}
	log.debug("#applicaQRCode - Fine ");
    }

    private String encode(String toEncode) {

	try {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update((toEncode).getBytes());
	    byte[] out = md.digest();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < out.length; i++) {
		sb.append(Integer.toString((out[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	} catch (NoSuchAlgorithmException e) {
	    throw new RuntimeException(e);
	}
    }
}
