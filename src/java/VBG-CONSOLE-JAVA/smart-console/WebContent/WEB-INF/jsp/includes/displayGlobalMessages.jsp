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
	    <c:if test="${param.status_msg eq '03'}">
	    	<div id="status_msg" class="error_header" >
	    		<fmt:message key="${param.status_msg}"/><a href="#" onclick="showErrors()" title="<fmt:message key="errors.display_extended_desc" />">[E]</a>
	    	</div>
	    </c:if>
	    <c:if test="${param.status_msg ne '03'}">
	    	<div id="status_msg" class="success_header" >
	    		<fmt:message key="${param.status_msg}"/>
	    	</div>
	    </c:if>
	    <script type="text/javascript" >
	    	$('status_msg').pulsate({ pulses: 2, duration: 1.0 });
	    </script>
	</c:when>
	<c:otherwise>
		<spring:hasBindErrors name='<%= request.getParameter("commandName") %>'>
		 	<div id="error_msg" class="error_header" >
		 		<div><fmt:message key="03"/>. <a href="#" onclick="showErrors()" title="<fmt:message key="errors.display_extended_desc" />">[E]</a></div>
		 		<c:forEach items="${errors.globalErrors}" var="g_err">
		         	<div>
			         	<c:if test="${g_err.code eq ''}">
			         		${g_err.defaultMessage}
			         	</c:if>
			         	<c:if test="${g_err.code ne ''}">
			         		<spring:message message="${g_err}" />
			         	</c:if>
			        </div>
		 		</c:forEach>
		 	</div>
		 	
		    <script type="text/javascript">
		    	$('error_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>  
		</spring:hasBindErrors>
	</c:otherwise>
</c:choose>	
	 <div id="infosWarningsDiv" style="display:none;">
	 </div>
	 <div id="_debugInfoId" style="display: none;">
	 </div>
	<script type="text/javascript">
	function checkWarningsOInfos(){
		
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
				} 
			});		
	}	
	jQuery(document).ready(function () {
		checkWarningsOInfos();
	});
	</script>
&nbsp;
</div>