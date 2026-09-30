<% response.setContentType("application/x-java-jnlp-file"); %>
<% response.addHeader("Content-Disposition", "inline; filename=signApp.jnlp"); %>
<?xml version="1.0" encoding="utf-8"?>
<jnlp spec="1.0+" codebase="<%=request.getAttribute("uri")%>/" href="">
    <information>
        <title>Digital Sign Application</title>
        <vendor>In.I.T.</vendor>
    </information>
    <security>
     	<all-permissions/>
	</security>
    <resources os="Windows">
        <j2se version="1.6+" href="http://java.sun.com/products/autodl/j2se" />
        <jar href="applets/SimpleSignApplication-signed.jar" main="true" />
        <jar href="applets/SmartCardAccess-signed.jar" />
        <nativelib href="applets/NativeLibWin-signed.jar" />
    </resources>
    <%
    String height = "400";
    String debug = request.getParameter("debug");
    if(debug == null || debug.equals("false")){
		debug = "false";
		height = "240";
    }
    %>
    <application-desc main-class="it.trento.comune.j4sign.examples.SimpleSignApplication">
    	 <!--uri (obbligatorio)-->
    	 <argument><%=request.getAttribute("uri")%>/sign</argument>
    	 <!--sessionId (obbligatorio)-->
    	 <argument><%=request.getParameter("sessionId")%></argument>
    	 <!--fileId (obbligatorio)-->
    	 <argument><%=request.getParameter("fileId")%></argument>
    	 <!--signerLabel -->
    	 <argument>null</argument>
    	 <!--pbErrorColor -->
    	 <argument>null</argument>
    	 <!--pbProcessingColor -->
    	 <argument>null</argument>
    	 <!--pbSuccessColor -->
    	 <argument>null</argument>
    	 <!--bgColor 0-255-->
    	 <argument>null</argument>
    	 <!--cryptokiLib -->
    	 <argument>null</argument>
    	 <!--debug (true/false se false settare height=240) -->
    	 <argument><%=debug %></argument>
    	 <!--width: 500 -->
    	 <argument>500</argument>
    	 <!--height: 240/400 -->
    	 <argument><%=height %></argument>
    </application-desc>
    <update check="background"/>
</jnlp>