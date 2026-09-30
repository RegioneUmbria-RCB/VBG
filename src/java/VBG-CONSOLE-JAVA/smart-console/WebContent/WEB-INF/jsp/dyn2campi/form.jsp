<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Dyn2Campi"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.dyn2campi.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.dyn2campi.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="dyn2campi" />
    </jsp:include>
    
    <jsp:include page="../includes/history.jsp">
	   	<jsp:param name="path" value="../dyn2campi/view" />
	   	<jsp:param name="qs" value="codice%3D${dyn2campi.entity.id.codice}"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="dyn2campi" name="inviodati">
			<spring-form:hidden path="popup" />
			<spring-form:hidden path="callerId" />
			<spring-form:hidden path="newId" />
		    <spring-form:hidden path="dyn2ModellotId" />
		    <spring-form:hidden path="entity.id.codice" />
			<table width="100%">
				<tr>
					<td>
						<fmt:message key="label.nome_campo" />
					</td>
					<td>
						<spring-form:input id="nomecampo_id" path="entity.nomecampo" size="50" />
						<spring-form:errors path="entity.nomecampo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.etichetta" />
					</td>
					<td>
						<spring-form:input id="etichetta_id" path="entity.etichetta" size="50" />
						<spring-form:errors path="entity.etichetta" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:textarea id="descrizione_id" path="entity.descrizione" cols="57" rows="6" />
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>
				<input type="hidden" name="entity.dyn2Basecontesti.id" value="IS" id="contesti_id"/>				
				<%--
				<tr>
					<td><fmt:message key="label.contesti"/></td>
					<td>
						
						<spring-form:select id="contesti_id" path="dyn2Basecontesti.id"> 
							<spring-form:option value="" ><fmt:message key='label.nessuno_funzioni_non_disponibili'/></spring-form:option>
							<spring-form:options items="${basecontestis}" itemLabel="contesto" itemValue="id" />
						</spring-form:select>
						<spring-form:errors path="dyn2Basecontesti" cssClass="error"/>
					</td>
				</tr>
				 --%>
				<%--<c:if test="${dyn2campi.entity.id.codice==null}"> --%>
				<tr>
					<td><fmt:message key="label.tipo_dato"/></td>
					<td>
						<spring-form:select id="tipo_dato_id" path="entity.tipodato" onchange="aggiornaCampiTipoDato(this)"> 
							<spring-form:options items="${enumTipi}" itemLabel="valore" itemValue="chiave"  />
						</spring-form:select>
						<spring-form:errors path="entity.tipodato" cssClass="error"/>
					</td>
				</tr>
				<%-- </c:if> --%>
				<%-- 
				<c:if test="${dyn2campi.entity.id.codice!=null}">
					<tr>
						<td><fmt:message key="label.tipo_dato"/></td>
						<td>
						<spring-form:select id="tipo_dato_id" path="tipodato"> 
							<spring-form:options items="${enumTipi}" itemLabel="valore" itemValue="chiave"  disabled="true"/>
						</spring-form:select>
						<spring-form:errors path="tipodato" cssClass="error"/>
						</td>
					</tr>
				</c:if>
				--%>
				<c:if test="${dyn2campi.entity.id.codice!=null}">
					<tr  class="titoloSezione">
						<td colspan="2"><fmt:message key="label.proprieta_controllo"/></td>
					</tr>
					
					<c:forEach items="${dyn2campi.entity.dyn2Campiproprietas}" var="current" varStatus="a">
					<tr>
						<td width="30%">
							${current.etichettaTransiet}
						</td>
						<td>
						
							<c:if test="${current.tipologiaCampoTransient eq 'selectTipoRicerca'}">
							<spring:bind path="entity.dyn2Campiproprietas[${a.index}].valore">
								<c:if test="${status.value eq 0}" >
								<select name="${status.expression}" >
								  	<option value="0"><fmt:message key="label.mostra_risultati_con_testo_ricercato"/></option>
								  	<option value="1"><fmt:message key="label.mostra_risultati_che_iniziano_con_testo_ricercato"/></option>
								</select>
								</c:if>
								<c:if test="${status.value eq 1}" >
								<select name="${status.expression}" >
								  	<option value="1"><fmt:message key="label.mostra_risultati_che_iniziano_con_testo_ricercato"/></option>
								  	<option value="0"><fmt:message key="label.mostra_risultati_con_testo_ricercato"/></option>
								</select>
								</c:if>
						        <spring-form:errors path="entity.dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
							</spring:bind> 
							</c:if>
						
							<c:if test="${current.tipologiaCampoTransient eq 'select'}">
							<spring:bind path="entity.dyn2Campiproprietas[${a.index}].valore">
								<c:if test="${status.value eq false}" >
								<select name="${status.expression}" >
								  	<option value="false">No</option>
								  	<option value="true">Si</option>
								</select>
								</c:if>
								<c:if test="${status.value eq true}" >
								<select name="${status.expression}" >
								  	<option value="true">Si</option>
								  	<option value="false">No</option>
								</select>
								</c:if>
						        <spring-form:errors path="entity.dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
							</spring:bind> 
							</c:if>
							<c:if test="${current.tipologiaCampoTransient eq 'input'}">
							<spring:bind path="entity.dyn2Campiproprietas[${a.index}].valore">
								<input type="text" name="${status.expression}" value="${status.value}" size="50"/>
								<spring-form:errors path="entity.dyn2Campiproprietas[${a.index}].valore" cssClass="error" />
							</spring:bind> 
						    </c:if>
					    </td>
					</tr>
					</c:forEach>
			</c:if>
			</table>
			<table width="100%">
				<tr class="titoloSezione">
					<td><fmt:message key="label.campo_usato_in_quadri"/></td>
				</tr>
				<c:forEach items="${dyn2campi.entity.dyn2Modellids}" var="current" varStatus="a">
					<tr>
						<td>
							<c:if test="${dyn2campi.popup eq false or empty dyn2campi.popup}">	
								<a href="javascript:doHref('../dyn2modellit/view.htm?id.codice=${current.dyn2Modellit.id.codice }','')">${current.dyn2Modellit.codiceScheda }</a>
							</c:if>
							<c:if test="${dyn2campi.popup eq true}">	
								${current.dyn2Modellit.codiceScheda }
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<script type='text/javascript'>
			if($('nomecampo_id')){
				$('nomecampo_id').focus();
			}
			
			function aggiornaCampiTipoDato(id)
			{
				
				if (${dyn2campi.entity.id.codice!=null} && checkConfirmMessage("Attenzione, verrano cancellate tutte le proprietà di controllo")) {
					// Controllo se è popup o no
					if(${dyn2campi.popup==true})
					{
						//var caller = 'dyn2CampiInputId';
						var codiceDyn=${dyn2campi.entity.id.codice};
						//doHref('../dyn2campi/popupview.htm?codice=${dyn2campi.entity.id.codice}&changeTipodato=1&tipodato='+id.value+'&popupCaller="+caller,69,"+status=1,menubar=0,scrollbars=1,width=800, height=600"');
						url = "../dyn2campi/popupview.htm?codice="+codiceDyn+"&changeTipodato=true&tipodato="+ id.value;
						if(${dyn2campi.newId!=null}){
							url += "&newId=${dyn2campi.newId}";
						}
						if(${dyn2campi.callerId!=null}){
							url += "&callerId=${dyn2campi.callerId}";
						}
					}else
					{
						if(${dyn2campi.entity.id.codice!=null})
						{
							url = '../dyn2campi/view.htm?codice=${dyn2campi.entity.id.codice}&changeTipodato=true&tipodato='+id.value;
						}
						else
						{
						    url = '../dyn2campi/view.htm?codice=${dyn2campi.entity.id.codice}';
						}
				    }
					doHref(url,'');
				}
			}

			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:set var="urlPrefix" value=""></c:set>
		    <c:if test="${dyn2campi.popup eq true}">
				<c:set var="urlPrefix" value="popup"></c:set>
			</c:if>
			<c:if test="${dyn2campi.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('${ urlPrefix }insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${dyn2campi.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('${ urlPrefix }update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<c:if test="${dyn2campi.popup eq false or empty dyn2campi.popup}">	
					<%-- TODO impostare etichetta da message properties <fmt:message key="javascript.confirm.delete" />--%>
					<c:set var="aHref" value="javascript:doSubmit('delete.htm','Confermi la cancellazione di questo id semantico?',document.inviodati)"></c:set>
					<c:set var="aClass" value=""></c:set>
					<c:set var="aTitle" value=""></c:set>
					<c:if test="${dyn2campi.deleteAllowed eq false}">
						<c:set var="aHref" value="javascript:void(0);"></c:set>
						<c:set var="aClass" value="buttondisabled"></c:set>
						<%-- TODO impostare etichetta da message properties --%>
						<c:set var="aTitle" value="L'id semantico non può essere cancellato perchè è utilizzato in ${dyn2campi.numeroQuadri} quadri."></c:set>
					</c:if>
					<li class="${aClass}"><a href="${aHref}" title="${aTitle}"><fmt:message key="button.delete" /></a></li>
				</c:if>
			</c:if>
			<%-- se la scheda del campo è stata aperta dalla lista dei campi --%>
			<c:if test="${dyn2campi.popup eq false}">
				<li><a href="javascript:doHref('../dyn2campi/list.htm','')"><fmt:message key="button.back" /></a></li>
			</c:if>
			<%-- se la scheda del campo è stata aperta cliccando su 'nuovo id semantico' dalla scheda della riga del modello in inserimento o in modifica --%>
			<c:if test="${dyn2campi.popup eq true and not empty dyn2campi.callerId}">
				<%--  
				<c:if test="${not empty dyn2campi.newId}">
				 --%>
					<c:if test="${dyn2campi.callerId == 0}">
						<li><a href="javascript:doHref('../dyn2modellid/popupcreate.htm?codiceModelloT=${dyn2campi.dyn2ModellotId}&d2cid=${dyn2campi.newId}&popupCaller=any','')"><fmt:message key="button.back" /></a></li>
					</c:if>
					<c:if test="${dyn2campi.callerId != 0}">
						<li><a href="javascript:doHref('../dyn2modellid/popupview.htm?codice=${dyn2campi.callerId}&d2cid=${dyn2campi.newId}&popupCaller=any','')"><fmt:message key="button.back" /></a></li>
					</c:if>
				<%-- 
				</c:if>
				<c:if test="${empty dyn2campi.newId}">
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
				</c:if>
				 --%>
		    </c:if>
		    <%-- se la scheda del campo è stata aperta dalla scheda del quadro dinamico --%>
		    <c:if test="${dyn2campi.popup eq true and empty dyn2campi.callerId}">
				<li><a href="javascript:closeAndCallback('refreshList')"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
	<c:if test="${dyn2campi.popup eq true}">
		<%if(StringUtils.defaultString(request.getParameter("done"),"false").equalsIgnoreCase("true")){ 		
		String desc = ((Dyn2Campi)request.getAttribute("dyn2campi.entity")).getNomecampo().replace("'","\\'"); 		
		%>			
		<script type="text/javascript">
		jQuery(document).ready(function(){
			
			opener.jQuery('#${dyn2campi.popupCaller}_id1').val('<%= desc%>');
			opener.jQuery('#${dyn2campi.popupCaller}_id2').val('<%= desc%>');
			opener.jQuery('#${dyn2campi.popupCaller}_hidden').val('${dyn2campi.entity.id.codice}');
			opener.jQuery('#${dyn2campi.popupCaller}_id1').change();
			opener.jQuery('#${dyn2campi.popupCaller}_id2').change();
		});
		</script>				
		<%} %>
	</c:if>	
</body>
</html>