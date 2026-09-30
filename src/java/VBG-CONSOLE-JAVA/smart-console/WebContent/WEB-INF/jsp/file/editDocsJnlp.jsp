<% response.setContentType("application/x-java-jnlp-file"); %>
<% response.addHeader("Content-Disposition", "inline; filename=editDocs.jnlp"); %> 
<?xml version="1.0" encoding="utf-8"?>
<jnlp spec="1.0+" codebase="<%=request.getAttribute("uri")%>/" href="">
    <information>
        <title>Edit Docs Application</title>
        <vendor>In.I.T.</vendor>
    </information>
    <security>
     	<all-permissions/>
	</security>
    <resources os="Windows">
        <j2se version="1.6+" href="http://java.sun.com/products/autodl/j2se" />
        <jar href="applets/init-editdocs-jws.jar" main="true" />
    </resources>
    <application-desc main-class="it.gruppoinit.pal.gp.backoffice.jws.EditDocsApplication">
    	 <!--urlOggetti (obbligatorio)-->
    	 <argument><%=request.getAttribute("uri")%>/file/ajaxDownload.htm</argument>
    	 <!--urlUploadOggetti (obbligatorio)-->
    	 <argument><%=request.getAttribute("uri")%>/file/ajaxUpdate.htm</argument>
    	 <!-- fileId (obbligatorio)-->
    	 <argument><%=request.getAttribute("fileId")%></argument>
    	 <!-- Token (obbligatorio)-->
    	 <argument><%=request.getAttribute("token")%></argument>
    	 <!--labelBtnInviaModifiche-->
    	 <argument>null</argument>
    	 <!--labelDownloadCompletato -->
    	 <argument>null</argument>
    	 <!--debug -->
    	 <argument>true</argument>
    	 <!-- idComuneOggetto -->
    	 <argument><%=request.getAttribute("idComuneOggetto")%></argument>
    </application-desc>
    <update check="background"/>
</jnlp>