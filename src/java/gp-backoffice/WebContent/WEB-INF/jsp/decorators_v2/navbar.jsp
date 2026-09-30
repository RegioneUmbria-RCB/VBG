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

        

	   
    <div id="inserisci-nel-menu" style="display:none">
        <div class="menu-utente">
            <div class="dati-utente">
            
            	<img src="<%= imgUrl%>" height="75" />
            <%--    <i class="glyphicon glyphicon-user"></i> --%>
                <h2><spring-security:authentication property="principal.responsabile" />
                	<%--
                    <br/>
                    <small>Ruolo utente</small>
                     --%>
                </h2>
            </div>

            <ul style="position: relative">
                <li>
                    <a href="${pageContext.request.contextPath}/welcome/start.htm">
                        <i class="glyphicon glyphicon-home pull-right"></i> Home page</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/configurazioneutente/view.htm">
                        <i class="glyphicon glyphicon-cog pull-right"></i> Impostazioni</a>
                </li>

                <li>
                    <div class="dropdown" id="dropdown-preferiti-container">

                        <a class="dropdown-toggle" type="button" id="menu-preferiti" data-target="#dropdown-preferiti-container" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                            <i class="glyphicon glyphicon-heart pull-right"></i> Preferiti
                        </a>
                        <ul class="dropdown-menu slidemenu-ignore" aria-labelledby="menu-preferiti" id="preferiti-dropdown-menu">
							<c:forEach items="${links}" var="link">
								<c:if test="${empty link.target}">
                              	 	<c:set var="click_preferiti">javascript:doHref('${pageContext.request.contextPath}/${link.url}')</c:set>	                					
				               </c:if>
				               <c:if test="${not empty link.target}">
			               			<c:set var="click_preferiti">javascript:callLink('${link.url}','${link.target}')</c:set>
				               </c:if>
                           		<li><a href='#' onclick="${click_preferiti}">${link.descrizione}</a></li>                        
	                        </c:forEach> 

                        </ul>
                    </div>
                </li>

            </ul>
        </div>
	</div>