<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_inteventi_configurati.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.lista_inteventi_configurati.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
		
		<spring-form:form commandName="bandialberoproc" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bandialberoproc" />
		    </jsp:include>
		
		    <table>
		    <tr>
			<td><fmt:message key="form.bandi.alberoproc" /></td>
			<td>
			
				<script type='text/javascript'>
				function setHiddenFieldalberoproc(inputField, listItem) {
					var a = listItem.id;
					document.getElementById('alberoproc_id').value = inputField.value;
					document.getElementById('alberoproc_hidden').value = a;
					<%-- 
					Assegnazione al campo nascosto effettuata per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
					e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
					da errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
					--%>
					document.getElementById('alberoproc_hidden_descrizione').value = inputField.value;
					$('alberoproc_id_choices').fade();
				}
				</script>
				<spring-form:textarea id="alberoproc_id"  tabindex="5" path="alberoproc.vwAlberoproc.scDescrizione" cssClass="searchbox" onchange="checkValue(this,'alberoproc_hidden')" onkeydown="javascript:return searchAll(this,event)" cols="62" rows="2" />
				<init:autocompleter methodAjax="findAlberoproc.htm" afterUpdateElement="setHiddenFieldalberoproc" idHidden="alberoproc_hidden" idInput="alberoproc_id" inputTitleKey="label.ricerca_intervento"/>
				<spring-form:errors path="alberoproc" cssClass="error"/> 
				<spring-form:hidden	id="alberoproc_hidden" path="alberoproc.id.codice"/>
				<%-- 
					Campo nascosto per evitare errore di validazione: siccome visualizziamo alberoproc.vwAlberoproc.scDescrizione
					e l'oggetto di dominio ha il controllo di validazione @Valid allora trovando alberoproc.scDescrizione vuoto o nullo
					da errore nella validazione (vedi it.gruppoinit.pal.gp.core.domain.Alberoproc.getVwAlberoproc())
				--%>
				<spring-form:hidden	id="alberoproc_hidden_descrizione" path="alberoproc.scDescrizione"/>
		   
		    </td>
		    
		    </tr>
		    <tr>
		    	<td><fmt:message key="label.ordine" /></td>
		    	<td>
		    		<spring-form:input id="ordine_id" path="ordine" size="10" readonly="true"/>
					<spring-form:errors path="ordine" cssClass="error"/>
				</td>
		    </tr>
		    <tr>
		    	<td><fmt:message key="label.percentuale" /></td>
		    	<td>
		    		<spring-form:input id="percentualeEstrazione_id" path="percentualeEstrazione" size="10" />
					<spring-form:errors path="percentualeEstrazione" cssClass="error"/>
		    	</td>
		    </tr>
		    </table>
		    </spring-form:form>
		    <div id="functions">
				<ul>
					<li><a href="javascript:doSubmit('insert.htm','',document.inviodati);"><fmt:message key="button.aggiungi" /></a></li>
					<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
				</ul>
		    </div>
			<%-- 
			<form name="bandialberoprocForm" action="list.htm">
				<jmesa:springTableFacade
					id="bandialberoproc_id" 
					items="${bandialberoprocList}" 
					var="bandialberoproc_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="view.htm?codice=${bandialberoproc_var.id.codice}">${bandialberoproc_var.id.codice}</a>
                         	</jmesa:htmlColumn>								
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="dettaglioColumn" href="view.htm?codice=${bandialberoproc_var.id.codice}" title="<fmt:message key="label.edit.record" />">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			--%>
			
			<div class="jmesa" >
			<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td><fmt:message key="label.ordine" /></td>
						<td><fmt:message key="label.alberoproc" /></td>
						<td><fmt:message key="label.percentuale" /></td>
						<td width="2%"><fmt:message key="label.azioni" /></td>
					</tr>
				</thead>
				<tbody class="tbody" >
				<%int i = 0; %>
				<c:forEach var="bandialberoproc_var" items="${bandialberoprocList}" varStatus="status">
					<tr class="<%=(i%2)==0?"odd":"even"%>">
						<td>
							${bandialberoproc_var.ordine}
						    <c:if test="${status.index!=0}">
								<a class="upColumn" href="upColumn.htm?codiceBandoAlberoprocSup=${bandialberoprocList[status.index-1].id.codice}&codiceBandoAlberoproc=${bandialberoprocList[status.index].id.codice}" title="<fmt:message key="label.up" /> ">
									<label><fmt:message key="label.azioni" /></label>
								</a>
							</c:if>
							<c:if test="${status.index !=fn:length(bandialberoprocList)-1}">
							    <a class="downColumn" href="downColumn.htm?codiceBandoAlberoproc=${bandialberoprocList[status.index].id.codice}&codiceBandoAlberoprocInf=${bandialberoprocList[status.index+1].id.codice}" title="<fmt:message key="label.down" /> ">
									<label><fmt:message key="label.azioni" /></label>
								</a>
							</c:if>
						</td>
						<td>${bandialberoproc_var.alberoproc.vwAlberoproc.scDescrizione}</td>
						<td>${bandialberoproc_var.percentualeEstrazione}</td>
						<td>
						<a class="eliminaRiga" href="javascript:doSubmit('deletePubblicazioneFromCodice.htm?codiceBandialberoproc=${bandialberoproc_var.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" /> ${albopubblicazioni_var.id.codice}">
									<label><fmt:message key="label.elimina" /></label>
						</a>
						</td>								
					</tr>
					<%i++; %>					
							
				</c:forEach>
				</tbody>
			</table>
		</div>

		</div>
		
	</body>
</html>