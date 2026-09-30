<%@page contentType="application/x-java-jnlp-file"%><%
 StringBuffer codebaseBuffer = new StringBuffer();
 codebaseBuffer.append(!request.isSecure() ? "http://" : "https://");
 codebaseBuffer.append(request.getServerName());
 if (request.getServerPort() != (!request.isSecure() ? 80 : 443))
 {
   codebaseBuffer.append(':');
   codebaseBuffer.append(request.getServerPort());
 }
 String contextPath = request.getRequestURI();
 if(contextPath.indexOf("/") >= 0) {
     contextPath = contextPath.substring(0, contextPath.lastIndexOf("/"));
 }
 codebaseBuffer.append(contextPath);
%><?xml version="1.0" encoding="UTF-8"?>
<jnlp spec="1.0+" codebase="<%= codebaseBuffer.toString() %>">

    <information>
        <title>Trusted List Manager</title>
        <vendor>Arhs Developments</vendor>
    </information>
    
    <resources>
        <j2se version="1.6+"
              href="http://java.sun.com/products/autodl/j2se"/>
        <jar href="tlmanager-package-r5.jar" main="true" />
        <property name="tlmanager.common.mode" value="lotl"/>
    </resources>
    
    <application-desc
         name="TLManager"
         main-class="eu.europa.ec.markt.tlmanager.TLManager"
         width="800"
         height="600">
     </application-desc>
     
     <update check="background"/>
     
    <security>
    	<all-permissions/>
	</security>
    
</jnlp>