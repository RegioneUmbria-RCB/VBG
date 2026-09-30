<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_verticalizzazioni_base" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="label.lista_verticalizzazioni_base_parametri" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<div id="subcontent">

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="verticalizzazionibase" />
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
		<div><c:out value="${verticalizzazionibase.entity.modulo}" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${verticalizzazionibase.entity.descrizione}" /></div>
		</div>
	</div>
	<div class="parametriDiv">
		<div class="etichetta">
			<div><fmt:message key="label.comune" />:</div>
		</div>
		<div class="parametro">
			<div>
				<c:if test="${empty verticalizzazioni.comune.codicecomune }">
					<fmt:message key="label.tutti" />
				</c:if>
				<c:if test="${not empty verticalizzazioni.comune.codicecomune }">
					${verticalizzazioni.comune.comune }
				</c:if>
			</div>
		</div>
		<br class="clear"/>
		
		<div class="etichetta">
			<div><fmt:message key="label.software" />:</div>
		</div>
		<div class="parametro">
			<div>
				${verticalizzazioni.software.descrizione}
			</div>
		</div>
	</div>
	<spring-form:form commandName="verticalizzazionibase" name="inviodati">
	<input type="hidden" name="codice" value="${verticalizzazioni.id.codice}" />
	<div class="jmesa">
	<table border="0" cellpadding="2" cellspacing="0" class="table">
		
		<c:forEach
			items="${verticalizzazionibase.verticalizzazioniparametriHelpers}"
			var="verticalizzazioneparametriHelper" varStatus="a">

			<thead>
				<tr class="titoloSezione">
					<td colspan="4">
					${verticalizzazioneparametriHelper.software.descrizione}</td>
				</tr>
				<tr class="header">
					<td width="10%"><fmt:message key="label.parametro" /></td>
					<td width="35%"><fmt:message key="label.valore" /></td>
					<td width="52%"><fmt:message key="label.descrizione" /></td>
					<td width="2%"><fmt:message key="label.azioni" /></td>
				</tr>
			</thead>
			<tbody class="tbody">
				<%int j=1;%>

				<c:forEach
					items="${verticalizzazioneparametriHelper.verticalizzazioniparametris}"
					var="verticalizzazioneparametri" varStatus="b">
					<tr class="<%=(j%2)==0?"odd":"even"%>">
						<td>${verticalizzazioneparametri.verticalizzazioniparametribase.id.parametro}
						<c:set var="textType">text</c:set> 
						<c:if test="${fn:contains(verticalizzazioneparametri.verticalizzazioniparametribase.id.parametro,'PASSWORD')}">
							<c:set var="textType">password</c:set>
						</c:if>
						
						<spring:bind path="verticalizzazioniparametriHelpers[${a.index}].verticalizzazioniparametris[${b.index}].verticalizzazioniparametribase.id.parametro">
							<input type="hidden" name="${status.expression}"
								value="${status.value}" />
						</spring:bind></td>
						<c:if
							test="${verticalizzazioneparametri.flagSoftwarePerAbilitatotransiet eq true}">
							<spring:bind
								path="verticalizzazioniparametriHelpers[${a.index}].verticalizzazioniparametris[${b.index}].valore">
								<td><input type="${textType}" name="${status.expression}"
									value="<c:out value="${status.value}" escapeXml="true"/>" size="80" /></td>
							</spring:bind>
						</c:if>
						<c:if
							test="${verticalizzazioneparametri.flagSoftwarePerAbilitatotransiet eq false}">
							<spring:bind path="verticalizzazioniparametriHelpers[${a.index}].verticalizzazioniparametris[${b.index}].valore">
								<td><input type="${textType}" name="${status.expression}"
									value="<c:out value="${status.value}" escapeXml="true"/>" size="65" readonly="readonly"
									title="<fmt:message key="label.operatore_non_abilitato_per_software" />" /></td>
							</spring:bind>
						</c:if>
						<td><c:out value="${verticalizzazioneparametri.verticalizzazioniparametribase.descrizione}" escapeXml="true"/></td>
						<td><a class="eliminaRiga"
							href="javascript: void(0)"; onclick="doHref('deleteParametriverticalizzazione.htm?codice=${verticalizzazioneparametri.id.codice}&codiceVerticalizzazione=${verticalizzazioni.id.codice}','<fmt:message key="javascript.confirm.delete" />');"
							title="<fmt:message key="label.azioni" /> ${verticalizzazionibase.entity.modulo}">
						<label><fmt:message key="label.azioni" /></label> </a></td>

					</tr>
					<%j++; %>
				</c:forEach>

			</tbody>
		</c:forEach>
		<c:if
			test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.NEW && fn:length(verticalizzazionibase.verticalizzazioniparametriHelpers)>0 }">
			<tr class="functions">
				<td>
				<div id="functions">
				<ul>
					<li><a
						href="javascript:doSubmit('updateParametriverticalizzazione.htm','',document.inviodati)"><fmt:message
						key="button.update" /></a></li>
				</ul>
				</div>
				</td>
			</tr>
		</c:if>
	</table>
	</div>

	<br class="clear" />

	<c:if
		test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.NEW}">

		<table width="100%">
			<tr class="titoloSezione">
				<td colspan="3"><label><fmt:message
					key="label.inserimento_nuovo_parametro_modulo" />&nbsp;${verticalizzazionibase.entity.modulo}</label>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.parametro" /></td>
				<td><spring-form:select id="parametri_id" path="verticalizzazioniparametri.verticalizzazioniparametribase.id.parametro" onchange="javascript:descrizione('parametri_id','descrizione_id')">
					<spring-form:options items="${verticalizzazionibase.entity.verticalizzazioniparametribases}" itemValue="id.parametro" itemLabel="id.parametro" />
				</spring-form:select> 
				<spring-form:errors path="verticalizzazioniparametri.verticalizzazioniparametribase.id.parametro" cssClass="error" /></td>
				<td><label id="descrizione_id"></label></td>
			</tr>
			<tr>
				<td><fmt:message key="label.software" /></td>
				<td>
					<c:choose>
					<c:when test="${isSoftwareTT eq true}">
						<spring-form:select id="software_id" path="verticalizzazioniparametri.software.codice">
							<spring-form:options items="${softwareList}" itemValue="codice" itemLabel="descrizione" />
		                </spring-form:select> 
						<spring-form:errors path="verticalizzazioniparametri.software" cssClass="error" />						
					</c:when>
					<c:otherwise>					
						<b>${verticalizzazioni.software.descrizione}</b>
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.valore" /></td>
				<td><spring-form:input id="valore_id"
					path="verticalizzazioniparametri.valore" size="60" /> <spring-form:errors
					path="verticalizzazioniparametri.valore" cssClass="error" /></td>
			</tr>
		</table>

	</c:if>

	<script type="text/javascript">
			
	
			function replaceT(obj, inputType){
				var newO = document.createElement('input');
					newO.setAttribute('type',inputType);
					newO.setAttribute('name',obj.getAttribute('name'));
					newO.setAttribute('id',obj.getAttribute('id'));
					newO.setAttribute('size',obj.getAttribute('size'));
					obj.parentNode.replaceChild(newO,obj);
				newO.focus();
			}
	
			function descrizione(obj,id){
				var a = document.getElementById(obj);
				var selectOptions = a.options;
				if(escape(a.value).indexOf('PASSWORD') >= 0){
					replaceT($('valore_id'),'password');
				}else{
					replaceT($('valore_id'),'text');
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/verticalizzazionibase/ajaxDescrizione.htm?modulo=${verticalizzazionibase.entity.modulo}'+'&parametro='+escape(a.value), {
					  method: 'post',	
					  onSuccess: function(transport){
						$(id).innerHTML = transport.responseText;
						$(id).style.display='';				
				      },
					  onFailure: function(transport){ 
						$(id).innerHTML= transport.responseText;
						$(id).className='error_header'
						$(id).style.display='';
						$(id).pulsate({ pulses: 2, duration: 1.0 });
					  }						    		 
				});
			}
			
			jQuery(document).ready(function(){
					if($('parametri_id')){
						descrizione('parametri_id','descrizione_id');
					}
				}
			);
			
		</script>

</spring-form:form></div>
<div id="functions">
<ul>
	<c:if
		test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.NEW}">
		<li><a
			href="javascript:doSubmit('insertParametriverticalizzazione.htm','',document.inviodati)"><fmt:message
			key="button.insert" /></a></li>
	</c:if>
	<c:if
		test="${verticalizzazionibase.displayMode == verticalizzazionibase.displayConstants.LIST}">
		<li><a
			href="javascript:doHref('createParametriverticalizzazione.htm?codice=${verticalizzazioni.id.codice}','');"><fmt:message
			key="button.new" /></a></li>
	    <c:if test="${fn:length(verticalizzazionibase.verticalizzazioniparametriHelpers)>0}">
		<li><a
			href="javascript:doSubmit('updateParametriverticalizzazione.htm','',document.inviodati)"><fmt:message
			key="button.update" /></a></li>
		</c:if>	
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message
		key="button.back" /></a></li>
</ul>
</div>
</body>
</html>