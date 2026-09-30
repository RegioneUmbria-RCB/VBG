<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<table height="100%" width="100%">
  <tr>
    <td height="100%" width="100%">
    	
    	<c:choose>
	    	<c:when test="${isPost eq false}">
		    	<iframe id="sigeproms" name="bottom" src="${external_url}" width="100%" height="100%" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</c:when>
			<c:otherwise>
			
			<form id="sigeprofx001" name="gotoPopupPost001" action="${external_url}" method="post" target="sigepromsIFr000">
				<c:forEach var='parameter' items='${paramValues}'>
					<c:if test="${parameter.key ne 'software' and parameter.key ne 'Software' and parameter.key ne 'idcomune' and parameter.key ne 'url' and parameter.key ne 'Token'}">
						<c:forEach var='value' items='${parameter.value}'>
							<input type="hidden" name="${parameter.key}" value="<c:out value='${value}' escapeXml="false"/>" />
						</c:forEach>
					</c:if>
				</c:forEach>
			</form>
				<!-- when the form is submitted, the server response will appear in this iframe -->
				<iframe name="sigepromsIFr000" src="" width="100%" height="100%" frameborder="0" scrolling="auto" marginheight="0" marginwidth="0">
					<p>Your browser does not support iframes.</p>
				</iframe>				
				<script type="text/javascript">
					document.forms["gotoPopupPost001"].submit();
				</script>
			</c:otherwise>	
		</c:choose>
    </td>
  </tr>
</table>
<%

String productStyle = (String)request.getSession().getAttribute(WebConstants.SecurityParams.PRODUCT_STYLE.name());

if("netbuk".equalsIgnoreCase(productStyle)){
%>
	<script type="text/javascript">
	
	(function($){
		
		var iframe = $('#sigeproms');
	
		iframe.load(function () {
		    var frameBody = $(this).contents().find("body");
		    $(this).height(frameBody.height()+50);
		    
		});
	})(jQuery);
	
	
	
	
	</script>

<%}
%>