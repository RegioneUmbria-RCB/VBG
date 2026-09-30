<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page session="false" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="pragma" content="no-cache" />
	<link type="text/css" rel="stylesheet" media="screen" href="${pageContext.request.contextPath}/css/styles/login1.css" />
	<title><fmt:message key="label.login.table.caption" /></title>
</head>
<body>
	<script type="text/javascript">
		var buttonSubmitted = false;
		
		function loginSubmit(){
			if (buttonSubmitted) {
				return;
			}
			buttonSubmitted = true;
			$("submit_id").innerHTML = "<img src='../images/spinner.gif'>";
			//document.login.submit();
		}
	</script>
	<div id="container">
		<div id="header_container">
			<div id="header">
			</div>
		</div>
		<div id="content">
			<div id="inset">
				<div id="inset_l">&nbsp;</div>
				<div id="inset_c"><div class="slogan"><fmt:message key="label.login.table.caption"/></div></div>
				<div id="inset_r">&nbsp;</div>
			</div>
			<div id="form">
				<div id="form_l">
					&nbsp;
					<c:if test="${softwareAttiviList!=null}">
					<ul class="modulo">
						<c:forEach items="${softwareAttiviList}" var="sa">
						<li>${sa.descrizione}</li>
						</c:forEach>
					</ul>
				</c:if>
				</div>
				<div id="form_c">
					<div class="title"><fmt:message key="label.appname"/></div>
					<div class="subtitle"><fmt:message key="label.appname.extended"/></div>
					<div class="comunetitle">${comune }</div>
				</div>
				<div id="form_r">
					<div class="messages">
					<%
					HttpSession session = request.getSession(false);
					String errorMsg = "none";
					String lastUser = "";
					if(session != null) {
					    pageContext.setAttribute(WebConstants.IDCOMUNE_ALIAS,session.getAttribute(WebConstants.IDCOMUNE_ALIAS));
						lastUser = (String) session.getAttribute(org.springframework.security.ui.webapp.AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY);
						org.springframework.security.AuthenticationException ex = (org.springframework.security.AuthenticationException) session.getAttribute(org.springframework.security.ui.AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY);
					    errorMsg = ex != null ? ex.getLocalizedMessage() : "none";
					    if (lastUser == null) {
					    	lastUser = "";
					    }
					    if(!errorMsg.equals("none")){
						%>
							<label class="error">
						<% 
							out.print(errorMsg);
						%>
							</label>
						<%
					    }
					}
					%>
					
					</div>
					<form action="<%=request.getContextPath() %>/j_spring_security_check" name="login" method="post" onsubmit="loginSubmit();">
						<%--il campo hidden serve per riaccendere la sessione quando la pagina di login rimane aperta oltre al tempo max della sessione in web.xml --%>
						<input type="hidden" name="<%=WebConstants.IDCOMUNE_ALIAS %>" value="${idcomunealias}"/>
						<div style="margin-top: 20px;">&nbsp;</div>
						<label for="username"><fmt:message key="login.username" /></label>
						<div style="margin-bottom: 10px;">
						<input class="inputtext" type="text" name="j_username" id="login_id" />
						</div>
						<label for="password"><fmt:message key="login.password" /></label>
						<div style="margin-bottom: 20px;">
						<input class="inputtext" type="password" name="j_password" id="password_id"/>
						</div>
						<div id="submit_id">
						<input class="login_button" type="submit" value="<fmt:message key="button.login"/>" />
						</div>
						<div style="margin-top: 20px;">&nbsp;</div>
					</form>
				</div>
			</div>
		</div>
		<div id="footer"></div>
	</div>
	<%-- utilizzato per abilitare il submit tramite tasto Invio --%>
	<input type="image" onclick="loginSubmit(); return false;" src="<%=request.getContextPath() %>/images/transparent.gif"/>
	<script type="text/javascript">
	<!--
		$('login_id').focus();
	//-->
	</script>
</body>
</html>