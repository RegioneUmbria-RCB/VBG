package it.gruppoinit.pal.gp.core.features.alfresco.config;

public class ApiPaths {

    //  public static final String QUERY = "queries/nodes?term=%s&rootNodeId=%s";
    public static final String NODE_ID = "nodes/%s";
    public static final String NODE_ID_VERSION = "nodes/%s/versions/%s";
    public static final String NODES_CHILDREN = "nodes/%s/children";
    public static final String NODES_CONTENT = "nodes/%s/content"; //attachment=true passato come QUERY PARAM
    public static final String NODES_CONTENT_VERSION = "nodes/%s/versions/%s/content"; //attachment=true passato come QUERY PARAM
    public static final String NODE_RELATIVE_PATH = "nodes/-root-"; //relativePath passato come QUERY PARAM

    public static String getPath(String path, String... param) {

	return String.format(path, param);
    }
}