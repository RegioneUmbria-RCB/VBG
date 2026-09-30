package it.gruppoinit.pal.gp.core.features.alfresco.client;

import java.io.InputStream;

import it.gruppoinit.pal.gp.core.features.alfresco.exception.ECMException;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.NodeRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.request.UploadNodeDocumentRequest;
import it.gruppoinit.pal.gp.core.features.alfresco.model.response.NodeResponse;
import it.gruppoinit.pal.gp.core.features.alfresco.model.response.NodesChildrenResponse;

public interface ClientAlfrescoAPIService {

    //get a latest version of node or folder
    public NodeResponse getNode(String nodeId);

    //get a specific version of node document
    public NodeResponse getNode(String nodeId, String version);

    public NodeResponse updateNode(NodeRequest nodeRequest);

    public NodeResponse createNode(NodeRequest nodeRequest);

    //delete a node document
    public boolean deleteNode(String nodeId) throws ECMException;

    //delete a specific version of node document
    public boolean deleteNode(String nodeId, String nVersion) throws ECMException;

    public NodesChildrenResponse getNodesChildren(String nodeId);

    //get a major version of node document
    public InputStream downloadNodeDocument(String nodeId) throws ECMException;

    //get a specific version of node document
    public InputStream downloadNodeDocument(String nodeId, String nVersion) throws ECMException;

    public NodeResponse uploadNewNodeDocument(UploadNodeDocumentRequest uploadRequest);

    public NodeResponse uploadExistingNodeDocument(UploadNodeDocumentRequest uploadRequest);

    //get Node from a relativePath (es. "VBG/0000/00/12")
    public NodeResponse getNodeByRelativePath(String relativePath);

    public NodeResponse creaStrutturaCartelle(NodeResponse nodeRoot, String[] descrizioneCartella);
}
