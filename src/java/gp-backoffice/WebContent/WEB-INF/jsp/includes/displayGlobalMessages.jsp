<%@page import="java.util.Date"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.service.helper.FlashMessages"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="taglibs.jsp" %>
<div class="global_messages">
<script type="text/javascript">	    	
   	function showErrors(){
   		showHideDiv('error_panel');
   	}
</script>
<c:choose>
	<c:when test="${not empty param.status_msg}">
		<c:choose>
	    <c:when test="${param.status_msg eq '03'}">
	    	<div id="status_msg" class="error_header alert alert-danger" >
	    		<fmt:message key="${param.status_msg}"/><a href="#" onclick="showErrors()" title="<fmt:message key="errors.display_extended_desc" />">[E]</a>
	    	</div>
	    </c:when>
	    <c:when test="${param.status_msg eq '06'}">
	    	<div id="status_msg" class="warning_header alert alert-warning" >
	    		<fmt:message key="${param.status_msg}"/></a>
	    	</div>
	    </c:when>
	    <c:when test="${param.status_msg eq '09'}">
	    	<div id="status_msg" class="warning_header alert alert-warning" >
	    		<fmt:message key="${param.status_msg}"/></a>
	    	</div>
	    </c:when>
	    <c:otherwise>
	    	<div id="status_msg" class="success_header alert alert-success" >
	    		<fmt:message key="${param.status_msg}"/>
	    	</div>
	    </c:otherwise>
	    </c:choose>	
	</c:when>
	<c:otherwise>
		<spring:hasBindErrors name='<%= request.getParameter("commandName") %>'>
		 	<div id="error_msg" class="error_header alert alert-danger" >
		 		<div><fmt:message key="03"/>. <a href="#" onclick="showErrors()" title="<fmt:message key="errors.display_extended_desc" />">[E]</a></div>
		 		<c:forEach items="${errors.allErrors}" var="g_err">
		 			<c:choose>
		 			<c:when test="${fn:indexOf(g_err, 'Failed to convert property value') < 0}">
		         	<div>
			         	<c:if test="${g_err.code eq ''}">
			         		${g_err.defaultMessage} 
			         	</c:if>
			         	<c:if test="${g_err.code ne ''}">
			         		<spring:message message="${g_err}" />
			         	</c:if>
			        </div>
			        </c:when>
			        <c:otherwise>
			        	<%
			        	System.out.println("============= Errore non visualizzato ===============");
			        	System.out.println("Data: " + new Date());
			        	System.out.println("IdComuneAlias: " + ORMHelper.getIdcomuneAlias());
			        	System.out.println("Token: " + ORMHelper.getToken());
			        	System.out.println("Url: " + request.getRequestURL());
			        	System.out.println("request.getQueryString: " + request.getQueryString());			        	
			        	System.out.println(pageContext.getAttribute("g_err")); 
			        	System.out.println("===========================================");
			        	%>
			        </c:otherwise>
			        </c:choose>
		 		</c:forEach>
		 	</div>
		</spring:hasBindErrors>
	</c:otherwise>
</c:choose>	
	 <div id="infosWarningsDiv" style="display:none;">
	 </div>
	 <div id="_debugInfoId" style="display: none;">
	 </div>
	<script type="text/javascript">
	function checkWarningsOInfos(){
		
		disableFunctions();
		
		var jhqr = jQuery.ajax({
			  url: '../ajax/getFlashMessages.htm',
			  context: document.body,
			  cache: false,				
			  dataType: "html",
			  success: function(data) {
				  
				  if(data!=''){
					$('infosWarningsDiv').innerHTML=data;  	
				  	$('infosWarningsDiv').show();
				  }
				  enableFunctions();
				},
			 complete: enableFunctions()
			});		
	}	
	jQuery(document).ready(function () {
		checkWarningsOInfos();
	});
	</script>
&nbsp;
</div>