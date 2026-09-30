package it.gruppoinit.pal.gp.core.features.alfresco.client.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.ContentDisposition;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.transport.http.HTTPConduit;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.features.alfresco.client.ClientAlfrescoAPIService;
import it.gruppoinit.pal.gp.core.features.alfresco.config.ApiPaths;
import it.gruppoinit.pal.gp.core.features.alfresco.config.IVerticalizzazioneNodoAlfrescoAPIService;
import it.gruppoinit.pal.gp.core.features.alfresco.exception.ECMException;
import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.Entry;
import it.gruppoinit.pal.gp.core.features.alfresco.model.dto.ListNodesChildren;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.NodeRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.UploadNodeDocumentRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.response.NodeResponse;
import it.gruppoinit.pal.gp.core.features.alfresco.model.response.NodesChildrenResponse;
import it.gruppoinit.pal.gp.core.features.alfresco.model.types.ECMMetadataTypes;

@Service
public class ClientAlfrescoAPIServiceImpl implements ClientAlfrescoAPIService {

    // public static final String ALFRESCO_REST_BASE_URL = "alfresco/api/-default-/public/alfresco/versions/1/";
    private static final Logger log = LoggerFactory.getLogger(ClientAlfrescoAPIServiceImpl.class);
    private IVerticalizzazioneNodoAlfrescoAPIService verticalizzazioneNodoAlfrescoAPIService;

    @Autowired
    public void setVerticalizzazioneNodoAlfrescoAPIService(IVerticalizzazioneNodoAlfrescoAPIService verticalizzazioneNodoAlfrescoAPIService) {

	this.verticalizzazioneNodoAlfrescoAPIService = verticalizzazioneNodoAlfrescoAPIService;
    }

    @Override
    public NodeResponse getNode(String nodeId) {

	return getNode(nodeId, null);
    }

    @Override
    public NodeResponse getNode(String nodeId, String nVersion) {

	NodeResponse nodeResponse = new NodeResponse();
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error verticalizzazioneNodoAlfrescoAPIServiceImpl.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error verticalizzazioneNodoAlfrescoAPIServiceImpl.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = null;
	if (nVersion != null) {
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_ID_VERSION, nodeId, nVersion)).type("application/json")
		    .accept("application/json").get();
	} else {
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_ID, nodeId)).type("application/json").accept("application/json").get();
	}
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(200);
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } catch (JAXBException e) {
		log.error("Error ClientAlfrescoAPIServiceImpl.getNode: ", e);
		nodeResponse.setCode(500);
		nodeResponse.setDescription("JAXBException - Errore nel recupero nodo [" + nodeId + "]. Dettaglio Errore: \n" + e);
		return nodeResponse;
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
		log.error("Error ClientAlfrescoAPIService Impl.getNode: nel recupero nodo [" + nodeId + "]. Dettaglio Errore: \n", e);
	    }
	    nodeResponse.setCode(response.getStatus());
	    nodeResponse.setDescription("Errore nel recupero nodo [" + nodeId + "]. Dettaglio Errore: \n" + s);
	    return nodeResponse;
	}
    }

    @Override
    public NodeResponse updateNode(NodeRequest nodeRequest) {

	NodeResponse nodeResponse = new NodeResponse();
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = null;
	try {
	    StringWriter stringWriter = new StringWriter();
	    Marshaller marshaller = JAXBContext.newInstance(nodeRequest.getEntry().getClass()).createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(nodeRequest.getEntry(), stringWriter);
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_ID, nodeRequest.getEntry().getId())).put(stringWriter.toString());
	    // 
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(response.getStatus());
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		    log.error("Error ClientAlfrescoAPIServiceImpl.updateNode: nodo [{}{}]. Dettaglio Errore: \n{}. Exception: \n",
			    new Object[] { nodeRequest.getEntry().getId(), s, e });
		}
		nodeResponse.setCode(500);
		nodeResponse.setDescription("Errore in aggiornamento nodo [" + nodeRequest.getEntry().getId() + "]. Dettaglio Errore: \n" + s);
		return nodeResponse;
	    }
	} catch (JAXBException e) {
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription(
		    "JAXBException - Errore in aggiornamento nodo [" + nodeRequest.getEntry().getId() + "]. Dettaglio Errore: \n" + e);
	    return nodeResponse;
	}
    }

    @Override
    public NodeResponse createNode(NodeRequest nodeRequest) {

	NodeResponse nodeResponse = new NodeResponse();
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = null;
	try {
	    StringWriter stringWriter = new StringWriter();
	    Marshaller marshaller = JAXBContext.newInstance(nodeRequest.getEntry().getClass()).createMarshaller();
	    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
	    marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
	    marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	    marshaller.marshal(nodeRequest.getEntry(), stringWriter);
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CHILDREN, nodeRequest.getEntry().getParentId())).post(stringWriter.toString());
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 201) {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(201);
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		    log.error("Error ClientAlfrescoAPIService Impl.createNode: nel recupero nodo [" + nodeRequest.getEntry().getParentId() +
			      "]. Dettaglio Errore: \n",
			    e);
		}
		nodeResponse.setCode(500);
		nodeResponse
			.setDescription("Errore in creazione nodo parent [" + nodeRequest.getEntry().getParentId() + "]. Dettaglio Errore: \n" + s);
		return nodeResponse;
	    }
	} catch (JAXBException e) {
	    log.error("Error ClientAlfrescoAPIServiceImpl.createNode: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription(
		    "JAXBException - Errore in creazione nodo parent [" + nodeRequest.getEntry().getParentId() + "]. Dettaglio Errore: \n" + e);
	    return nodeResponse;
	}
    }

    @Override
    public boolean deleteNode(String nodeId) throws ECMException {

	return deleteNode(nodeId, null);
    }

    @Override
    public NodesChildrenResponse getNodesChildren(String nodeId) {

	NodesChildrenResponse nodesChildrenResponse = new NodesChildrenResponse();
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodesChildrenResponse.setCode(500);
	    nodesChildrenResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodesChildrenResponse;
	}
	Response response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CHILDREN, nodeId)).query("includeSource", true).type("application/json")
		.accept("application/json").get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(ListNodesChildren.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		ListNodesChildren listNodesChildren = unmarshaller.unmarshal(new StreamSource(is), ListNodesChildren.class).getValue();
		nodesChildrenResponse.setCode(200);
		nodesChildrenResponse.setList(listNodesChildren);
		nodesChildrenResponse.setDescription("ok");
		return nodesChildrenResponse;
	    } catch (JAXBException e) {
		nodesChildrenResponse.setCode(response.getStatus());
		nodesChildrenResponse.setDescription("JAXBException - Errore nel recupero nodi children [" + nodeId + "]. Dettaglio Errore: \n" + e);
		return nodesChildrenResponse;
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    nodesChildrenResponse.setCode(response.getStatus());
	    nodesChildrenResponse.setDescription("Errore nel recupero nodi children [" + nodeId + "]. Dettaglio Errore: \n" + s);
	    return nodesChildrenResponse;
	}
    }

    @Override
    public InputStream downloadNodeDocument(String nodeId) throws ECMException {

	return downloadNodeDocument(nodeId, null);
    }

    @Override
    public InputStream downloadNodeDocument(String nodeId, String nVersion) throws ECMException {

	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    throw new ECMException("Error metodo alfrescoServiceConfigHelper.getRestWebClient: ", e);
	}
	Response response = null;
	if (nVersion != null) {
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CONTENT_VERSION, nodeId, nVersion)).query("attachment", true)
		    .type("application/json").accept("application/json").get();
	} else {
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CONTENT, nodeId)).query("attachment", true).type("application/json")
		    .accept("application/json").get();
	}
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    return is;
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
		log.error("Error ClientAlfrescoAPIServiceImpl.downloadNode: nodo [" + nodeId + "]. Dettaglio Errore: \n" + s + " - Exeption: " + e);
		throw new ECMException("Error ClientAlfrescoAPIServiceImpl.downloadNodeDocument: nel recupero nodo [" + nodeId +
				       "]. Dettaglio Errore: \n" + s + " - Exeption: " + e);
	    }
	    log.error("Error ClientAlfrescoAPIServiceImpl.downloadNodeDocument: nodo [{}]. Dettaglio Errore: \n{}", nodeId, s);
	    throw new ECMException("Error ClientAlfrescoAPIServiceImpl.downloadNodeDocument: nel recupero nodo documento [" + nodeId +
				   "]. Dettaglio Errore: \n" + s);
	}
    }

    @Override
    public NodeResponse uploadNewNodeDocument(UploadNodeDocumentRequest uploadRequest) {

	NodeResponse nodeResponse = new NodeResponse();
	WebClient webClient;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = null;
	try {
	    InputStream dataFileByteArray = new ByteArrayInputStream(uploadRequest.getFiledata());
	    webClient.type("multipart/form-data").accept(MediaType.APPLICATION_XML, MediaType.MEDIA_TYPE_WILDCARD);
	    List<Attachment> atts = new ArrayList<Attachment>();
	    ContentDisposition cdDATAFILE = new ContentDisposition("form-data;name=\"filedata\";filename=\"" + uploadRequest.getName() + "\"");
	    Attachment attDATAFILE = new Attachment("filedata", dataFileByteArray, cdDATAFILE);
	    atts.add(attDATAFILE);
	    MultipartBody mpb = new MultipartBody(atts);
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CHILDREN, uploadRequest.getParentId()))
		    .query("autoRename", uploadRequest.isAutoRename()).post(mpb);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 201) {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(response.getStatus());
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		    log.error("Error ClientAlfrescoAPIServiceImpl.uploadNewNodeDocument: nodo [" + uploadRequest.getParentId() + "] - fileName [" +
			      uploadRequest.getFiledata() + "]. Dettaglio Errore: \n" + s + " - Exeption: ",
			    e);
		    nodeResponse.setCode(500);
		    nodeResponse.setDescription("Error ClientAlfrescoAPIServiceImpl.uploadNewNodeDocument: nodo [" + uploadRequest.getParentId() +
						"] - fileName [" + uploadRequest.getFiledata() + "]. Dettaglio Errore: \n" + s + " - Exeption: " + e);
		    return nodeResponse;
		}
	    }
	} catch (JAXBException e) {
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("JAXBException - Errore in uploadNewNodeDocument nodo parent [" + uploadRequest.getParentId() +
					"] - fileName [" + uploadRequest.getFiledata() + "]. Dettaglio Errore: \n" + e);
	    return nodeResponse;
	}
	return nodeResponse;
    }

    @Override
    public NodeResponse uploadExistingNodeDocument(UploadNodeDocumentRequest uploadRequest) {

	NodeResponse nodeResponse = new NodeResponse();
	if (uploadRequest.getId() == null) {
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error parametri in input: il campo ID deve essere popolato");
	    return nodeResponse;
	}
	WebClient webClient;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = null;
	try {
	    InputStream dataFileByteArray = new ByteArrayInputStream(uploadRequest.getFiledata());
	    response = webClient.path(ApiPaths.getPath(ApiPaths.NODES_CONTENT, uploadRequest.getId())).put(dataFileByteArray);
	    InputStream is = ((InputStream) response.getEntity());
	    if (response.getStatus() == 200) {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(response.getStatus());
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } else {
		String s = "";
		try {
		    s = IOUtils.toString(is);
		} catch (Exception e) {
		    log.error("Error ClientAlfrescoAPIService.uploadExistingNodeDocument: nodo [" + uploadRequest.getId() + "] - fileName [" +
			      uploadRequest.getFiledata() + "]. Dettaglio Errore: " + s + " - Exeption: ",
			    e);
		}
		nodeResponse.setCode(response.getStatus());
		nodeResponse.setDescription("Error ClientAlfrescoAPIService.uploadExistingNodeDocument: nodo [" + uploadRequest.getId() +
					    "] - fileName [" + uploadRequest.getFiledata() + "]. Dettaglio Errore: \n" + s);
		return nodeResponse;
	    }
	} catch (JAXBException e) {
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription(
		    "JAXBException - Errore in uploadExistingNodeDocument nodo  [" + uploadRequest.getId() + "]. Dettaglio Errore: \n" + e);
	    return nodeResponse;
	}
    }

    @Override
    public boolean deleteNode(String nodeId, String nVersion) throws ECMException {

	boolean b = false;
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: {}", e.getMessage(), e);
	    throw new ECMException("Error metodo alfrescoServiceConfigHelper.getRestWebClient: ", e);
	}
	try {
	    Response response = null;
	    if (nVersion != null) {
		response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_ID_VERSION, nodeId, nVersion)).query("permanent", true)
			.type("application/json").accept("application/json").delete();
	    } else {
		response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_ID, nodeId)).query("permanent", true).type("application/json")
			.accept("application/json").delete();
	    }
	    if (response.getStatus() == 204) {
		b = true;
	    }
	    return b;
	} catch (Exception e) {
	    log.error("Error ClientAlfrescoAPIServiceImpl.deleteNode: ", e);
	    throw new ECMException("Error ClientAlfrescoAPIServiceImpl.deleteNode: ", e);
	}
    }

    @Override
    public NodeResponse getNodeByRelativePath(String relativePath) {

	NodeResponse nodeResponse = new NodeResponse();
	WebClient webClient = null;
	try {
	    webClient = getRestWebClient();
	} catch (Exception e) {
	    log.error("Error alfrescoServiceConfigHelper.getRestWebClient: ", e);
	    nodeResponse.setCode(500);
	    nodeResponse.setDescription("Error alfrescoServiceConfigHelper.getRestWebClient: " + e);
	    return nodeResponse;
	}
	Response response = webClient.path(ApiPaths.getPath(ApiPaths.NODE_RELATIVE_PATH)).query("relativePath", relativePath).type("application/json")
		.accept("application/json").get();
	InputStream is = ((InputStream) response.getEntity());
	if (response.getStatus() == 200) {
	    try {
		JAXBContext jc = JAXBContext.newInstance(Entry.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		unmarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);
		unmarshaller.setProperty("eclipselink.media-type", "application/json");
		Entry e = unmarshaller.unmarshal(new StreamSource(is), Entry.class).getValue();
		nodeResponse.setCode(200);
		nodeResponse.setEntry(e);
		nodeResponse.setDescription("ok");
		return nodeResponse;
	    } catch (JAXBException e) {
		log.error("Error ClientAlfrescoAPIServiceImpl.getNodeByRelativePath: ", e);
		nodeResponse.setCode(500);
		nodeResponse.setDescription("JAXBException - Errore nel recupero nodo relativePath [" + relativePath + "]. Dettaglio Errore: \n" + e);
		return nodeResponse;
	    }
	} else {
	    String s = "";
	    try {
		s = IOUtils.toString(is);
	    } catch (Exception e) {
		log.error("Error ClientAlfrescoAPIServiceImpl.getNodeByRelativePath: nel recupero nodo relativePath [" + relativePath +
			  "]. Dettaglio Errore: \n" + s,
			e);
	    }
	    nodeResponse.setCode(response.getStatus());
	    nodeResponse.setDescription("Errore nel recupero nodo relativePath [" + relativePath + "]. Dettaglio Errore: \n" + s);
	    return nodeResponse;
	}
    }

    private WebClient getRestWebClient() {

	String urlAlfresco = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoApiUrl();
	String username = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoApiUser();
	String password = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoApiPwd();
	//valori di default
	int connTimeOut = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoApiConnTimeout();
	int readTimeOut = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoApiReadTimeout();
	WebClient webClient = WebClient.create(urlAlfresco, username, password, null); // Spring config file - we don't use this
	HTTPConduit conduit = WebClient.getConfig(webClient).getHttpConduit();
	conduit.getClient().setConnectionTimeout(connTimeOut);
	conduit.getClient().setReceiveTimeout(readTimeOut);
	return webClient;
    }

    @Override
    public NodeResponse creaStrutturaCartelle(NodeResponse nodeRoot, String[] descrizioneCartella) {

	StringBuilder pathCartelle = new StringBuilder();
	NodeResponse nodeFolder = null;
	String parentId = nodeRoot.getEntry().getId();
	String relPath = verticalizzazioneNodoAlfrescoAPIService.getAlfrescoRootRelativePath();
	if (!relPath.endsWith("/")) {
	    relPath += "/";
	}
	String percorso = relPath + nodeRoot.getEntry().getName();
	for (int i = 0; i < descrizioneCartella.length; i++) {
	    percorso = percorso + "/" + descrizioneCartella[i];
	    log.debug("verifico la presenza della cartella {}", pathCartelle);
	    nodeFolder = this.getNodeByRelativePath(percorso);
	    if (nodeFolder.getCode() == 200) {
		parentId = nodeFolder.getEntry().getId();
	    } else {
		NodeRequest nodeRequest = new NodeRequest();
		Entry e = new Entry();
		e.setName(descrizioneCartella[i]);
		e.setParentId(parentId);
		e.setNodeType("cm:folder");
		e.seteFolder(true);
		e.seteFile(false);
		Map<String, String> map = new HashMap<String, String>();
		map.put(ECMMetadataTypes.TITLE.getValue(), descrizioneCartella[i]);
		e.setProperties(map);
		nodeRequest.setEntry(e);
		nodeFolder = this.createNode(nodeRequest);
		if (nodeFolder.getCode() != 201) { //201 è l'OK in INSERT
		    throw new RuntimeException(
			    "Errore in chiamata a servizio clientAlfrescoAPIService.createNode Cartella con nome [" + descrizioneCartella[i] +
					       "] ed id [" + parentId + "]. Errore: " + nodeFolder.getCode() + " - " + nodeFolder.getDescription());
		}
		parentId = nodeFolder.getEntry().getId();
		//cartella creata correttamente
		log.debug("Cartella con nome [{}] ed id [{}] creata correttamente.", descrizioneCartella[i], nodeFolder.getEntry().getId());
	    }
	    pathCartelle.append(descrizioneCartella[i]).append("/");
	}
	return nodeFolder;
    }
}
