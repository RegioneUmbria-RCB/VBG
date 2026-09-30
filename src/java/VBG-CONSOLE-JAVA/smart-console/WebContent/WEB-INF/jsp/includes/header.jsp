<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.LinkPreferitiUtente"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%
	String uri = request.getRequestURI();
	String ctx = request.getContextPath();
	String res = uri.substring(uri.indexOf(ctx)+ctx.length());
	HttpSession httpSession=request.getSession();
	if((List<LinkPreferitiUtente>)httpSession.getAttribute("linkPreferitis")!=null)
	{
	    List<LinkPreferitiUtente> links=(List<LinkPreferitiUtente>)httpSession.getAttribute("linkPreferitis");
	    pageContext.setAttribute("links", links);
	}
%>

<script type="text/javascript">
function callLink(link,target)
{
	window.open(link, target);
	window.focus();
	//document.location.href=link;
}
</script>



<div id="header_left">
	<div class="">
		Smart Console - ${comune}
	</div>	
</div>
<div id="header_center">

	<c:if test="${not empty applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}">
		<span class="header_alert_message">
			<img src="${pageContext.request.contextPath}/images/warning.gif" alt="warning" align="bottom" />
			${applicationScope.MESSAGGIO_AGGIORNAMENTO_APPLICATIVO}
		</span>
	</c:if>
</div>
<div id="header_right">
    
	<div  style="float: right; padding:5">
				<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR">
				<c:if test="${empty sessionScope.userName}">
                			<c:set var="userName" scope="session">
                                  <spring-security:authentication property="principal.responsabile" />
                                  (<spring-security:authentication property="principal.codiceResponsabile" />
                                  	- <%= it.gruppoinit.pal.gp.core.dao.helper.ORMHelper.getIdcomuneAlias()%>)
                                   amministratore: <spring-security:authentication property="principal.amministratore" />
                            </c:set>
                </c:if>
				<spring-security:authentication property="principal.responsabile" />			
			</spring-security:authorize>

			<c:if test="${inite:isEnterprise()}">
				<spring-security:authorize ifAllGranted="ROLE_PREVIOUS_ADMINISTRATOR">
					<label style="color: red;">
						<fmt:message key="label.switch.user.alert" />
					</label>
					<br />
					<spring-security:authentication property="principal.responsabile" />
					<a href="<%=request.getContextPath()%>/j_spring_security_exit_user" title="<fmt:message key="label.switch.user" />">
						<img src="../images/switch.gif" alt="<fmt:message key="label.switch.user" />" />
					</a>
				</spring-security:authorize>
			</c:if>
					
			</div>
</div> 


