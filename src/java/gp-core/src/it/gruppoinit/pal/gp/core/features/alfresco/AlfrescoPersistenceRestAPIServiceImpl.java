package it.gruppoinit.pal.gp.core.features.alfresco;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.OggettiDAO;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.alfresco.client.ClientAlfrescoAPIService;
import it.gruppoinit.pal.gp.core.features.alfresco.config.IVerticalizzazioneNodoAlfrescoAPIService;
import it.gruppoinit.pal.gp.core.features.alfresco.exception.ECMException;
import it.gruppoinit.pal.gp.core.features.alfresco.model.AlfrescoObjectId;
import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.Entry;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.NodeRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.UploadNodeDocumentRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.response.NodeResponse;
import it.gruppoinit.pal.gp.core.features.alfresco.model.types.ECMMetadataTypes;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class AlfrescoPersistenceRestAPIServiceImpl implements IAlfrescoPersistenceRestAPIService {

    private static final Logger log = LoggerFactory.getLogger(AlfrescoPersistenceRestAPIServiceImpl.class);
    @Autowired
    private ClientAlfrescoAPIService clientAlfrescoAPIService;
    @Autowired
    private IVerticalizzazioneNodoAlfrescoAPIService verticalizzazioneNodoAlfrescoAPIService;
    @Autowired
    private OggettiDAO oggettiDAO;

    @Override
    public String creaDocumento(Integer codiceOggetto, String nomeFile, byte[] content) {

	String id = null;
	String version = null;
	String rootFolderId = StringUtils.defaultString(verticalizzazioneNodoAlfrescoAPIService.getAlfrescoDocumentRootFolder())
		.replace(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER, "");
	String[] descrizioneCartella = Utilities.foldersFromCodiceOggetto(codiceOggetto);
	//recupero l'ID della root folder "VBG"
	NodeResponse nodeRoot = clientAlfrescoAPIService.getNode(rootFolderId);
	if (nodeRoot.getCode() != 200) {
	    throw new InvalidConfigurationException(
		    "Non è stato possibile risalire alla cartella contenitore. Controllare la configurazione. Errore: " + //
						    nodeRoot.getCode() + " - " + //
						    nodeRoot.getDescription());
	}
	NodeResponse newNode = clientAlfrescoAPIService.creaStrutturaCartelle(nodeRoot, descrizioneCartella);
	String textFileName = nomeFile;
	log.debug("creaDocumentoConAlfrescoApi: codiceoggetto={},nomefile={}", codiceOggetto, textFileName);
	if (!textFileName.startsWith(codiceOggetto + "-")) {
	    textFileName = codiceOggetto + "-" + nomeFile;
	}
	UploadNodeDocumentRequest uploadRequest = new UploadNodeDocumentRequest();
	uploadRequest.setName(textFileName);
	uploadRequest.setFiledata(content);
	uploadRequest.setNodeType(verticalizzazioneNodoAlfrescoAPIService.alfrescoDocumentContentName());
	uploadRequest.setParentId(newNode.getEntry().getId());
	NodeResponse newDocument = clientAlfrescoAPIService.uploadNewNodeDocument(uploadRequest);
	if (newDocument.getCode() == 201) { //201 è l'OK in INSERT
	    log.debug("Il documento con nome [{}] ed parentid [{}] inserito.", textFileName, newNode.getEntry().getId());
	    id = newDocument.getEntry().getId();
	    for (Map.Entry<String, String> entry : newDocument.getEntry().getProperties().entrySet()) {
		if (ECMMetadataTypes.VERSIONLABEL.getValue().equals(entry.getKey())) {
		    if (entry.getValue() != null) {
			version = entry.getValue().toString();
		    }
		    break;
		}
	    }
	} else {
	    throw new RuntimeException("Non è stato possibile caricare il documento [" + textFileName + "] nel nodo [" + newNode.getEntry().getId() +
				       "] . Errore: " + newDocument.getCode() + " - " + newDocument.getDescription());
	}
	return id + ";" + version;
    }

    @Override
    public String aggiornaDocumento(Integer codiceOggetto, String nomeFile, String percorso, byte[] content) {

	String id = null;
	String version = null;
	AlfrescoObjectId oid = getIdDocumentoApiAlfresco(percorso);
	String pathNodeID1 = oid.getUid();
	log.debug("aggiornaDocumento API ALFRESCO: recupero da da alfresco l'oggetto [{}]", pathNodeID1);
	UploadNodeDocumentRequest uploadRequest = new UploadNodeDocumentRequest();
	uploadRequest.setFiledata(content);
	//
	uploadRequest.setName(codiceOggetto + "-" + nomeFile);
	uploadRequest.setId(pathNodeID1);
	NodeResponse nResponse = clientAlfrescoAPIService.uploadExistingNodeDocument(uploadRequest);
	if (nResponse.getCode() == 200) { //
	    log.debug("Il documento con nome [{}] ed id [{}] è stato aggiornato.", nomeFile, pathNodeID1);
	    id = pathNodeID1;
	    for (Map.Entry<String, String> entry : nResponse.getEntry().getProperties().entrySet()) {
		if (ECMMetadataTypes.VERSIONLABEL.getValue().equals(entry.getKey())) {
		    if (entry.getValue() != null) {
			version = entry.getValue();
		    }
		    break;
		}
	    }
	} else {
	    throw new BusinessValidationException(
		    "Non è stato possibile aggiornare il documento [" + nomeFile + "] nel nodo [" + nResponse.getEntry().getParentId() +
						  "] . Errore: " + nResponse.getCode() + " - " + nResponse.getDescription());
	}
	return id + ";" + version;
    }

    private AlfrescoObjectId getIdDocumentoApiAlfresco(String entityGetPercorso) {

	AlfrescoObjectId ret = new AlfrescoObjectId();
	String[] sPathNodeID = entityGetPercorso.split(";");
	ret.setUid(sPathNodeID[0].replace(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER, ""));
	if (sPathNodeID.length == 2) {
	    ret.setVersione(sPathNodeID[1]);
	}
	return ret;
    }

    @Override
    public InputStream getContenuto(Integer codiceOggetto, String nomeFile, String percorso) {

	//chiamata ad api rest alfresco
	log.debug("retrieveOggetto: recupero da Alfresco API l'oggetto [{}]", percorso);
	String nodeId = null;
	String nVersion = null;
	String pathNodeID = percorso;
	if (pathNodeID != null) {
	    String[] sPathNodeID = pathNodeID.split(";");
	    nodeId = sPathNodeID[0].replace(IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER, "");
	    if (sPathNodeID.length > 1) {
		nVersion = sPathNodeID[1];
	    }
	}
	log.debug("retrieveOggetto: recupero lo stream");
	try {
	    return clientAlfrescoAPIService.downloadNodeDocument(nodeId, nVersion);
	} catch (ECMException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void aggiornaMetadati(Integer codiceOggetto, String nomeFile, String percorso, Map<String, CodiceDescrizioneBean> mdcmisss) {

	log.debug("aggiornaMetadatiCMIS: recupero da Alfresco l'oggetto [{}]", percorso);
	String nodeId = null;
	String nVersion = null;
	AlfrescoObjectId oid = getIdDocumentoApiAlfresco(percorso);
	nodeId = oid.getUid();
	nVersion = oid.getVersione();
	try {
	    log.debug("aggiornaMetadatiCMIS: in Alfresco nodeId [{}]", nodeId, nVersion);
	    NodeRequest nodoRequest = new NodeRequest();
	    Entry e = new Entry();
	    //			    
	    Map<String, String> map = new HashMap<String, String>();
	    for (Map.Entry<String, CodiceDescrizioneBean> mdcmis : mdcmisss.entrySet()) {
		if (StringUtils.isNotBlank(mdcmis.getValue().getCodice())) {
		    map.put(mdcmis.getKey(), convertValoreToTypeApiAlfresco(mdcmis.getValue().getDescrizione(), mdcmis.getValue().getCodice()));
		}
	    }
	    //valori settati per ricercare il nodo specifico
	    map.put(ECMMetadataTypes.VERSIONLABEL.getValue(), nVersion);
	    e.setProperties(map);
	    e.setId(nodeId);
	    nodoRequest.setEntry(e);
	    NodeResponse nodeResponse = clientAlfrescoAPIService.updateNode(nodoRequest);
	    if (nodeResponse.getCode() != 200) { //
		throw new RuntimeException(
			"Non è stato possibile aggiornare il documento [" + nomeFile + "] nel nodo [" + nodeResponse.getEntry().getParentId() +
					   "] . Errore: " + nodeResponse.getCode() + " - " + nodeResponse.getDescription());
	    }
	    log.debug("Il documento con nome [{}] ed id [{}] è stato aggiornato.", nomeFile, nodeId);
	    String version = null;
	    for (Map.Entry<String, String> entry : nodeResponse.getEntry().getProperties().entrySet()) {
		if (ECMMetadataTypes.VERSIONLABEL.getValue().equals(entry.getKey())) {
		    if (entry.getValue() != null) {
			version = entry.getValue().toString();
		    }
		    break;
		}
	    }
	    oggettiDAO.updatePercorsoOggetto(codiceOggetto,
		    IAlfrescoPersistenceService.CMIS_WORKSPACE_FILE_IDENTIFIER + nodeResponse.getEntry().getId() + ";" + version);
	    oggettiDAO.flush();
	} catch (Exception e) {
	    log.error("aggiornaMetadatiCMIS: Non è stato possibile aggiornare i metadati del file {} con objId [{}] da Alfresco a causa di {}",
		    new Object[] { nomeFile, percorso, e.getMessage() });
	    throw new RuntimeException("Non è stato possibile aggiornare i metadati file " + nomeFile + " con objId[" + percorso +
				       "] da Alfresco a causa di " + e.getMessage(),
		    e);
	}
    }

    private String convertValoreToTypeApiAlfresco(String nuovoValore, String tipoConversione) {

	if (tipoConversione.equals("DATE")) {
	    String dt = null;
	    try {
		if (!nuovoValore.isEmpty()) {
		    dt = convertdateIsoFormat(nuovoValore);
		}
		return dt;
	    } catch (ParseException e) {
		return null;
	    }
	}
	return nuovoValore;
    }

    private static String convertdateIsoFormat(String reciviedDate) throws ParseException {

	SimpleDateFormat in = new SimpleDateFormat("dd/mm/yyyy");
	Date dateRecivied = in.parse(reciviedDate);
	return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(dateRecivied);
    }
}
