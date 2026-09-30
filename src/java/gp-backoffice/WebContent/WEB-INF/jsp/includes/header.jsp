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




<style>


	/**
	Classi del foglio di stile nihilo.css per la funzionalià preferiti
	(E' stato necessario ridefinire le classi utlizzate dalla funzionalità in quanto non il decorator MS non risuciva a caricare
	 i fogli di stile)
	**/ 
	
	/** Definisce le regole per il bottone dei preferiti  **/
	
	.dijitReset {
		margin:0 !important;
		border:0 !important;
		padding:0 !important;
		line-height:normal !important;
		font: inherit !important;
		color: inherit !important;
	}

	.dijitInline {
		/*display:inline-block !important;			
		#zoom: 1 !important;
		#display:inline !important;
		*/
		border:0 !important;
		margin-right :4px !important;
		margin-top :2px !important;
		margin-bottom :0px !important;
		padding-bottom: 2px !important;
		vertical-align:middle !important;
	}
	
	.dijitButtonNode {
		border-style: solid !important;
		border-width: thin !important;
		border-color: gray !important;
		color: gray !important;
	}
	
	/** Definisce le regole per la gestione dei bordi della tabella   **/
	
	.dijitMenu,.dijitMenuBar {
		overflow: auto !important;
		border: 1px solid #d3d3d3 !important;
		margin: 0 !important;
		padding: 0 !important;
		background-color: rgb(253, 253, 253) !important;
	}
	
	
	/** Definisce le regole per la gestione  dell'icona dropdown'') **/
	
	.dijitArrowButtonInner {
		background-image: url(${pageContext.request.contextPath}/images/spriteArrows.png) !important;
		background-repeat: no-repeat !important;
		background-position: 0 center !important;
		width: 11px !important;
		height: 11px !important;
	}
	
	/** Definisce le regole che evidenziano la voce di menu quando si passa sopra con il mouse **/
		
 	.dijitMenuItemHover,.dijitMenuItemSelected {
		background-color: #ffe284; 
		color: #243C5F;
	}

	/** Definisce le regole per la gestione del linee di separazione tra una voce di menù e l'altra' **/
	
	.dijitMenuSeparatorBottom {
		border-top: 1px solid #d3d3d3 !important;
	}
	
 	
	/**
		Gestiscono le regole di visualizzazione del testo dei link 
	**/
	.dijitMenuItemLabel {
		position: relative !important;
		vertical-align: middle !important;
	}

    .overwriteStyleFont {
     	font-size : 12px !important;
    }
    
   	.dijitMenuItem TD {
   	    padding-top: 1px !important;	
   	    padding-right: 1px !important;	
   	    padding-bottom: 1px !important;	
   	    padding-left: 1px !important;
   	    font-size : 12px !important;	
    }
	
	
	

	
	</style>

<div id="header_left">
	<table border="0" cellspacing="0" cellpadding="5">
	  
		<tr>
			<td class="comune_logged_arrow">
			<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR"><a style="text-decoration: none;" href="../assistenza/view.htm"><img border="0" src="<%=request.getContextPath() %>/images/item_arrow.gif"/></a>
						</spring-security:authorize>
			</td>
			
			<td class="comune_logged">${comune}</td></tr>
		<tr>
			<td class="user_logged_arrow"><!-- §§§BEGIN§§§ -->
					
						<spring-security:authorize ifAllGranted="ROLE_ADMINISTRATOR"><a style="text-decoration: none;" href="../admin/view.htm"><img border="0" src="<%=request.getContextPath() %>/images/item_arrow.gif"/></a>
						</spring-security:authorize>
					
				<!-- §§§END§§§ --></td>
			<td class="user_logged">
			
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
			</td>
		</tr>
	</table>	
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
    
	<table border="0" cellspacing="0" cellpadding="5" style="float: right;">
		
		<tr>
			<td>
				<div style="float: right;" id="logo-div" >
					<img id="logo-img" src="<c:import url="/ajax/findLogo.htm" />"/>
				</div>		
			</td>
			<td class="app_version_name" title="[${app_version}]">
	            <div dojoType="dijit.form.DropDownButton">
	            <span>
				<img align="bottom" alt="preferiti" src="${pageContext.request.contextPath}/images/favorites-icon.png">
				</span>
	            <div dojoType="dijit.Menu" id="Edit">
	               <c:forEach items="${links}" var="link">
	               <c:if test="${empty link.target}">
	                	<div dojoType="dijit.MenuItem"  label="${link.descrizione}" onclick="javascript:doHref('${pageContext.request.contextPath}/${link.url}')"></div>
	               </c:if>
	               <c:if test="${not empty link.target}">
	               		 <div dojoType="dijit.MenuItem"  label="${link.descrizione}" onclick="javascript:callLink('${link.url}','${link.target}')"></div>
	               </c:if>
	               </c:forEach>
	               	<div data-dojo-type="dijit.MenuSeparator"></div>
	               	<div data-dojo-type="dijit.MenuItem"  data-dojo-props="onClick:function(){doHref('${pageContext.request.contextPath}/')}"><fmt:message key="label.pagina_iniziale"/></div>
	                <div data-dojo-type="dijit.MenuSeparator"></div>
	                <div data-dojo-type="dijit.MenuItem" data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconCreateLink',
	        			 onClick:function(){doHref('../configurazioneutente/view.htm?software=TT')}"><fmt:message key="label.add_preferiti"/></div>
	            </div>
	            </div>
			<label class="app_version">Ver. ${db_version }</label><label class="app_name"><init:editLabel key="label.appname" role="ROLE_EDITLABEL" /></label><br />
			<label class="app_description" style="text-align: right;"><init:editLabel key="label.appname.extended" role="ROLE_EDITLABEL" /></label>
			</td>
			
			<%-- 
			<td>
				<div style="float: right;" id="logo-div" >
					<img id="logo-img" src="${pageContext.request.contextPath}/images/genova/logo_genova.png""/>
				</div>		
			</td>
			--%>
		</tr>
		<%-- <tr><td class="app_description" style="text-align: right;"><fmt:message key="label.appname.extended"/><td></tr> --%>
	</table>
</div> 


