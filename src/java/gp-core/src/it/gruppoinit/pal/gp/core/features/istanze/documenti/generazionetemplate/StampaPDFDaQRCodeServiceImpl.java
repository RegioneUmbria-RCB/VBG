package it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.auditing.StampaQRCodeLogger;
import it.gruppoinit.pal.gp.core.features.istanze.documenti.generazionetemplate.verticalizzazione.VerticalizzazioneQRCodeServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;

@Service
public class StampaPDFDaQRCodeServiceImpl implements IStampaPDFDaQRCodeService {

    private static Logger log = LoggerFactory.getLogger(StampaPDFDaQRCodeServiceImpl.class);
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private DocumentMergeService documentMergeService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private LetteretipoService letteretipoService;

    @Override
    public OggettoPdfBean stampaPDFDaQr(ParametriPerPDFHelper p) {

	//Verifico se i parametri passati coincidono con il mac passato
	String alias = p.getAlias();
	String software = p.getSoftware();
	Integer idtemplate = p.getIdtemplate();
	String guid = p.getGuid();
	String url = new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, alias).getUrl();
	String urlInput = url.replace("alias", alias).replace("software", software).replace("guid", guid);
	urlInput = urlInput.replace("idtemplate", idtemplate.toString()).replace("mac", p.getChiaveMac());
	String chiaveMAC = new VerticalizzazioneQRCodeServiceImpl(verticalizzazioniService, alias).getMac();
	String mac = this.encode(alias + software + guid + idtemplate + chiaveMAC);
	Oggetti oggPdf = null;
	OggettoPdfBean resp = null;
	StampaQRCodeLogger auditLog = new StampaQRCodeLogger(urlInput);
	if (StringUtils.equals(mac, p.getChiaveMac())) {
	    //Uguali vado avanti
	    Istanze istanza = istanzeService.findByUiid(p.getGuid());
	    byte[] doc = documentMergeService.eseguiSostituzioniBaseDocumento(idtemplate, istanza.getId().getCodice(), null,
		    new DocumentMergeHelper());
	    Letteretipo lt = letteretipoService.findById(new PkId(idtemplate));
	    Oggetti ogg = oggettiService.findById(new PkId(lt.getFile().getId().getCodice()));
	    ogg.setOggetto(doc);
	    ORMHelper.setToken(System.currentTimeMillis() + "");// token non necessario
	    try {
		oggPdf = oggettiService.convertFileInPdf(ogg);
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("Errore durante la conversione in PDF.");
	    }
	    resp = new OggettoPdfBean(oggPdf, "OK");
	    auditLog.log();
	} else {
	    resp = new OggettoPdfBean(oggPdf, "KO");
	}
	return resp;
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
