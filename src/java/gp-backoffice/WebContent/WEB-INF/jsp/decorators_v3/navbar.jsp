<%@page import="org.springframework.security.providers.UsernamePasswordAuthenticationToken"%>
<%@page import="it.gruppoinit.pal.gp.core.security.LoggedUser"%>
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
	//String imgUrl = request.getUserPrincipal().getClass().getName();
	UsernamePasswordAuthenticationToken upat = (UsernamePasswordAuthenticationToken)request.getUserPrincipal();
	LoggedUser usr = (LoggedUser)upat.getPrincipal();
	String imgUrl = request.getContextPath() + "/images/user-unknown.png";
	if(usr.getCodiceOggettoImmagine() != null){
	    imgUrl = request.getContextPath() + "/file/ajaxDownload.htm?fileId=" + usr.getCodiceOggettoImmagine() + "&modifica=true";
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

    <nav class="navbar yamm navbar-fixed-top main-nav-bar" role="navigation" data-nav-banner=".banner">

        <ul class="nav navbar-nav" id="menu-root">
        </ul>

        <ul class="nav navbar-nav navbar-right">

            <li class="dropdown yamm-ignore">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                    <i class="glyphicon glyphicon-user"></i> <spring-security:authentication property="principal.responsabile" />
                    <span class="caret"></span>
                </a>
                <div class="row dropdown-menu user-menu">
                    <div class="menu-dati-utente">
                        <div class="user-menu-image">
                            <!-- <img src="https://secure.gravatar.com/avatar/3b6e40d8a242b1a01a8a7d20c1d16022?s=100&r=g&d=mm" /> -->
                            <img src="<%= imgUrl%>" height="150" />
                        </div>
                        <div class="user-menu-name">
                           	<spring-security:authorize ifNotGranted="ROLE_PREVIOUS_ADMINISTRATOR">
								<%-- BOCCI 20120629 PER CAPIRE DALLA LISTA DELLE SESSIONI DELL'APPLICATIVO TOMCAT MANAGER 
									CHI E' L'UTENTE LOGGATO ("GUESSED USER NAME") E' NECESSARIO SETTARE QUESTA VAR NELLA SESSION --%>
								<c:if test="${empty sessionScope.userName}">
				                			<c:set var="userName" scope="session">
				                                  <spring-security:authentication property="principal.responsabile" />
				                                  (<spring-security:authentication property="principal.codiceResponsabile" />
				                                  	- <%= it.gruppoinit.pal.gp.core.dao.helper.ORMHelper.getIdcomuneAlias()%>)
				                                   amministratore: <spring-security:authentication property="principal.amministratore" />
				                            </c:set>
				                </c:if>
				                <%-- BOCCI 20120629 "GUESSED USER NAME" END --%>
								<spring-security:authentication property="principal.responsabile" />			
							</spring-security:authorize>
							<!-- §§§BEGIN§§§ -->
							
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
							
							<!-- §§§END§§§ -->
                        </div>
                        <%--
                        <div class="user-menu-title">
                            Istruttore tecnico
                        </div>
                         --%>

                        <div class="user-menu-items">
                            <ul class="nav">
                                <li><a href="${pageContext.request.contextPath}/welcome/start.htm"><i class="glyphicon glyphicon-home"></i> Home page</a></li>
                                <%--
                                	<li><a href="#"><i class="glyphicon glyphicon-user"></i> Modifica profilo</a></li>
                                 --%>
                                <li><a href="${pageContext.request.contextPath}/configurazioneutente/view.htm"><i class="glyphicon glyphicon-cog"></i> Impostazioni</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/batchscadenzario/listPerOperatore.htm?software=TT"><i class="glyphicon glyphicon-calendar"></i> Scadenzario</a></li>
                                <li class="dropdown-submenu">
                                	<a href="#" id="linkPreferiti1">
                                		<i class="glyphicon glyphicon-heart"></i> 
                                		Preferiti
                                	</a>
                                	
                                	<ul class="dropdown-menu" aria-labelledby="linkPreferiti1">
                                	
	                                	<c:forEach items="${links}" var="link">
	                                	 	<c:if test="${empty link.target}">
	                                	 		<c:set var="click_preferiti">javascript:doHref('${pageContext.request.contextPath}/${link.url}')</c:set>	                					
							               </c:if>
							               <c:if test="${not empty link.target}">
						               			<c:set var="click_preferiti">javascript:callLink('${link.url}','${link.target}')</c:set>
							               </c:if>
	                                		<li><a href='#' onclick="${click_preferiti}">${link.descrizione}</a></li>
	                                	</c:forEach>
	                                	
                                		<li><a href='#' onclick="doHref('${pageContext.request.contextPath}/configurazioneutente/view.htm?software=TT')"><fmt:message key="label.add_preferiti"/></a></li>                       		
                                	</ul>
                                	
                                </li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${pageContext.request.contextPath}/welcome/logout.htm"><i class="glyphicon glyphicon-off"></i> Esci</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </nav>