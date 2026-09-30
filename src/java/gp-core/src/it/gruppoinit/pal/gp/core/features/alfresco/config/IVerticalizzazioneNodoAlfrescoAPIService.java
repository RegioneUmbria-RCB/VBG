package it.gruppoinit.pal.gp.core.features.alfresco.config;

public interface IVerticalizzazioneNodoAlfrescoAPIService {

    String getAlfrescoDocumentRootFolder();

    boolean isAttiva();

    String getAlfrescoApiUrl();

    String getAlfrescoApiUser();

    /**
     * 
     * @return
     */
    String getAlfrescoApiPwd();

    /**
     * Non obbligatorio default 20000
     * 
     * @return
     */
    int getAlfrescoApiConnTimeout();

    /**
     * Non obbligatorio default 300000
     * 
     * @return
     */
    int getAlfrescoApiReadTimeout();

    String getAlfrescoRootRelativePath();

    String alfrescoDocumentContentName();

    boolean solaLettura();
}