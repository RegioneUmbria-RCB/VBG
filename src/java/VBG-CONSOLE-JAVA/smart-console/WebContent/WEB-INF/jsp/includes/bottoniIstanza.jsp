<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%-- BEGIN RECUPERO PARAMETRI --%>
<%-- END RECUPERO PARAMETRI--%>
<%-- BEGIN SEZIONE FUNZIONI --%>
<c:set var="draggableDivClass" value=""/>
<c:set var="draggableDivFunction" value=""/>
<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
	<c:set var="draggableDivFunction" value="showHideElement(document.getElementById('dragDiv_id'))"></c:set>
	<c:set var="draggableDivClass" value="draggable"/>
</c:if>							
					
		
<div class="${draggableDivClass}" id="draggable_menu" onmouseover="${draggableDivFunction}" onmouseout="${draggableDivFunction}">
		<div id="functions">
			<ul>
				<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.NEW}">
						<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				</c:if>
				<c:if test="${istanzeCommand.displayMode == istanzeCommand.displayConstants.VIEW}">
					<c:if test="${isModificaIstanza eq true}">
						<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
					</c:if>
					<c:if test="${isSorteggiata eq true}">
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnElaborazione')}">						
							<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','')"><fmt:message key="button.elaborazione" /></a></li>						
						</c:if>
						<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnMovimenti')}">								
							<li><a href="javascript:historySet('${_urlback}','../movimenti/list.htm?codiceIstanza=${istanzeCommand.entity.id.codice}','')"><fmt:message key="button.movimenti" /></a></li>									
						</c:if>
								<%--
									<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnGestioneIstanza')}">
										<li><a href="${_ }"><fmt:message key="button.gestione_istanza" /></a></li>
										
										<li><a href="javascript:historySet('${_urlback}','../istanze/gestioneIstanza.htm?codice=${stanzeCommand.entity.id.codice}','')"><fmt:message key="button.gestione_istanza" /></a></li>
										
									</c:if>
								 --%>
					</c:if>
					<c:if test="${isModificaIstanza eq true}">

						<c:choose>		 
							<c:when test="${isCancellaIstanzePerOperatore eq true }">
								<li><a href="javascript:cancellaIstanzaConfirm()"><fmt:message key="button.delete" /></a>
								<div dojoType="dijit.Dialog" id="cancellaIstanzeDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />"  style="display: none;">
									<input type="checkbox" id="cancellazioneistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
									<label for="cancellazioneistanzachk_id">
										<fmt:message key="label.messaggio_cancellazione_istanza_per_operatore">
											<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
											<fmt:param>${istanzeCommand.entity.numeroistanza}</fmt:param>
										</fmt:message>
									</label>
									<div id="functions">
										<ul>
											<li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
											<li><a href="javascript:void 0" onclick="dijit.byId('cancellaIstanzeDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
										</ul>
									</div>
									<br class="clear" />	
								</div>
								<script type="text/javascript">
									function cancellaIstanzaConfirm(){
										dijit.byId('cancellaIstanzeDialogDiv').show();
									}	
								</script>								
								</li>
							</c:when>
							<c:otherwise>
								<li class="buttondisabled"><a href="javascript:alert('<fmt:message key="javascript.alert.operatore_non_puo_cancellare_pratica" />');"><fmt:message key="button.delete" /></a></li>
							</c:otherwise> 
						</c:choose>	
					</c:if>
				</c:if>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
			
			<%--
			
			<span id="dragDiv_id" style="display: none;" title="<fmt:message key="label.drag_menu" />">
				[+]
			</span>
			
			<span id="dragDiv_id" title="<fmt:message key="label.floating_menu" />" onclick="floatMenu();">
				[*]
			</span>
			 --%>
		</div>	
</div>
<%--
<script type="text/javascript">

function JSFX_FloatDiv(id, sx, sy)
{
	var ns = (navigator.appName.indexOf("Netscape") != -1);
	var d = document;
	var el=d.getElementById?d.getElementById(id):d.all?d.all[id]:d.layers[id];
	var px = document.layers ? "" : "px";
	window[id + "_obj"] = el;
	if(d.layers)el.style=el;
	el.cx = el.sx = sx;el.cy = el.sy = sy;
	el.sP=function(x,y){this.style.left=x+px;this.style.top=y+px;};

	el.floatIt=function()
	{
		var pX, pY;
		pX = (this.sx >= 0) ? 0 : ns ? innerWidth : 
		document.documentElement && document.documentElement.clientWidth ? 
		document.documentElement.clientWidth : document.body.clientWidth;
		pY = ns ? pageYOffset : document.documentElement && document.documentElement.scrollTop ? 
		document.documentElement.scrollTop : document.body.scrollTop;
		if(this.sy<0) 
		pY += ns ? innerHeight : document.documentElement && document.documentElement.clientHeight ? 
		document.documentElement.clientHeight : document.body.clientHeight;
		this.cx += (pX + this.sx - this.cx)/8;this.cy += (pY + this.sy - this.cy)/8;
		this.sP(this.cx, this.cy);
		setTimeout(this.id + "_obj.floatIt()", 40);
	}
	return el;
}

function floatMenu(){
	JSFX_FloatDiv("draggable_menu", 0, 0).floatIt();
}
</script>
 --%>
 
<%-- END SEZIONE FUNZIONI --%>