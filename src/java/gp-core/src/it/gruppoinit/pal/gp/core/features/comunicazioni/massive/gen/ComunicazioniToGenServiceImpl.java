package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.IParametriProtocolloHelperComunicazioniService;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.ISoftwareComuneData;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.common.SoftwareComuneDataBean;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.eventi.IEventoMassiva.ContestoComunicazioneEnum;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.custom.DettaglioRigaIstanze;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.IParametriProtocolloPerEnteHelper;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.ws.client.FileConverterWsClient;

@Service
public class ComunicazioniToGenServiceImpl implements IComunicazioniToGenService {

    private static final Logger log = LoggerFactory.getLogger(ComunicazioniToGenServiceImpl.class);
    private IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO;
    private VerticalizzazioniService verticalizzazioniService;
    private DocumentMergeService documentMergeService;
    private OggettiService oggettiService;
    private LetteretipoService letteretipoService;
    private IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService;
    private IstanzeService istanzeService;

    @Autowired
    public ComunicazioniToGenServiceImpl(IComunicazioniMassiveGenDAO comunicazioniMassiveGenDAO, VerticalizzazioniService verticalizzazioniService,
	    DocumentMergeService documentMergeServic, OggettiService oggettiService, LetteretipoService letteretipoService,
	    IParametriProtocolloHelperComunicazioniService parametriProtocolloHelperComunicazioniService, IstanzeService istanzeService) {

	this.comunicazioniMassiveGenDAO = comunicazioniMassiveGenDAO;
	this.verticalizzazioniService = verticalizzazioniService;
	this.documentMergeService = documentMergeServic;
	this.oggettiService = oggettiService;
	this.letteretipoService = letteretipoService;
	this.parametriProtocolloHelperComunicazioniService = parametriProtocolloHelperComunicazioniService;
	this.istanzeService = istanzeService;
    }

    @Override
    public void collegaRigheMercatiAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	comunicazioniMassiveGenDAO.collegaRigheMercatiAComunicazioni(idTestata, configurazioneComunicazione);
    }

    @Override
    public void collegaDettaglioMercatoADettaglioComunicazioni(Integer codice, Map<Integer, List<DettaglioRigaGen>> m) {

	comunicazioniMassiveGenDAO.collegaDettaglioMercatoADettaglioComunicazioni(codice, m);
    }

    @Override
    public int generaLetteraAccompagnamentoCommissioniDettaglio(int codiceLettera, int idRigaDettaglioMassiva, boolean convertiInPdf,
	    Integer codiceIstanza) {

	// RICHIAMARE IL SERVIZIO DI MERGE (INTERNO NON GMT) DELLA LETTERATIPO
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di recuperare il codice lettera", idRigaDettaglioMassiva, convertiInPdf);
	DocumentMergeHelper userData = new DocumentMergeHelper();
	Letteretipo lettera = letteretipoService.findById(new PkId(codiceLettera));
	userData.getParams().put("ID_DETTAGLIO_MASSIVA", String.valueOf(idRigaDettaglioMassiva));
	userData.getParams().put("IDCOMUNE", ORMHelper.getIdcomune());
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di chiamare la sostituzione con query", idRigaDettaglioMassiva, convertiInPdf);
	byte[] res = documentMergeService.eseguiSostituzioniBaseDocumento(codiceLettera, codiceIstanza, null, userData);
	String nomefile = lettera.getFile().getNomefile();
	if (convertiInPdf) {
	    log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di convertire in pdf", idRigaDettaglioMassiva, convertiInPdf);
	    res = convertiRTFinPDF(res);
	    nomefile = nomefile + ".pdf";
	}
	Oggetti o = new Oggetti();
	o.setNomefile(nomefile);
	o.setOggetto(res);
	log.debug("generaLetteraPerDettaglioMassiva# {}-{} prima di inserire in oggetti", idRigaDettaglioMassiva, convertiInPdf);
	oggettiService.insert(o);
	return o.getId().getCodice();
    }

    private byte[] convertiRTFinPDF(byte[] documentBytes) {

	FileConverterWsClient fileConverterWsClient = new FileConverterWsClient();
	try {
	    return fileConverterWsClient.convertiRtfInPDF(documentBytes);
	} catch (FunzioneBusinessRemotaException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public List<ISoftwareComuneData> getSoftwareComuneFromIdDettaglioComunicazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	return comunicazioniMassiveGenDAO.getSoftwareAndComunePerDettaglioComunicazione(configurazioniComunicazioneGen);
    }

    @Override
    public List<IParametriProtocolloPerEnteHelper> popolaParametriProtocollazione(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen,
	    List<ISoftwareComuneData> softwareComuneData) {

	if (!verticalizzazioniService.isAttiva(VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE)) {
	    return new ArrayList<IParametriProtocolloPerEnteHelper>();
	}
	return this.parametriProtocolloHelperComunicazioniService.popolaParametri(softwareComuneData);
    }

    public List<ISoftwareComuneData> getSoftwareAndComune(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	List<ISoftwareComuneData> softwareAndComune;
	//PER LE ISTANZE DEVO PASSARE PER IL SERVICE ISTANZE
	if (configurazioniComunicazioneGen.getContesto() == ContestoComunicazioneEnum.ISTANZE) {
	    List<DettaglioRigaIstanze> tempResult = istanzeService.findIstanzeListHelperByFilterMass(configurazioniComunicazioneGen.getFilter(),
		    HelperTypeEnum.SOFTWAREANDCOMUNE, null, null);
	    softwareAndComune = new ArrayList<ISoftwareComuneData>();
	    if (tempResult != null) {
		for (DettaglioRigaIstanze riga : tempResult) {
		    SoftwareComuneDataBean bean = new SoftwareComuneDataBean();
		    bean.setSoftware(riga.getSoftware());
		    bean.setCodiceComune(riga.getCodicecomune());
		    softwareAndComune.add(bean);
		}
	    }
	} else {
	    softwareAndComune = comunicazioniMassiveGenDAO.getSoftwareAndComunePerDettaglioComunicazione(configurazioniComunicazioneGen);
	}
	return softwareAndComune;
    }

    @Override
    public List<DettaglioRigaGen> getDettagli(ConfigurazioniComunicazioneGen configurazioniComunicazioneGen) {

	return comunicazioniMassiveGenDAO.getDettagli(configurazioniComunicazioneGen);
    }

    @Override
    public void collegaRigheIstanzeAComunicazioni(int idTestata, ConfigurazioniComunicazioneGen configurazioneComunicazione) {

	comunicazioniMassiveGenDAO.collegaRigheIstanzeAComunicazioni(idTestata, configurazioneComunicazione);
    }

    @Override
    public void collegaDettaglioIstanzeADettaglioComunicazioni(Integer codice, Set<Integer> idistanze, boolean isMovimenti, String tipiMovimento,
	    Integer codiceAmministrazione) {

	comunicazioniMassiveGenDAO.collegaDettaglioIstanzeADettaglioComunicazioni(codice, idistanze, isMovimenti, tipiMovimento,
		codiceAmministrazione);
    }
}
