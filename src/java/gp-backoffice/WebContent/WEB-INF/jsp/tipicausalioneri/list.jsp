<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" /></title>
</head>
<body>
    <c:set scope="page" var="flgTipicausaliinteressiVar"  value="0"></c:set>
    <c:if test="${isMora}">
    	<c:set scope="page" var="flgTipicausaliinteressiVar"  value="1"></c:set>
    </c:if>
	<span class="titoloPagina"><fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../tipicausalioneri/list" />
	</jsp:include>
	<div id="subcontent">
		<form name="tipicausalioneriForm" action="list.htm">
		
		<div class="vbg-form">
			<fieldset>
			<legend><fmt:message key="label.form_ricerca" /> </legend>
				<div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i>
						<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
					</div>
					<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>		
				</div>
			</fieldset>	
		
			<fieldset>
			<legend><fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title"/></legend>
				<table id="tabella_tco" class="vbg-table">
					<thead>
						<tr>
							<th><fmt:message key="label.codice"/></th>
							<th><fmt:message key="tipicausalioneri.label.coDescrizione"/></th>
							<th><fmt:message key="tipicausalioneri.label.coSerichiedeendo.table"/></th>
							<th><fmt:message key="tipicausalioneri.label.codicecausalepeople"/></th>							
							<th><fmt:message key="label.edit.record"/></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${tipicausalioneriList}" var="tipicausalioneri_var" varStatus="idx">
						<tr>
							<td><a href="javascript:historySet('${_urlback }','../tipicausalioneri/view.htm?codice=${tipicausalioneri_var.id.codice}');">${tipicausalioneri_var.id.codice}</a></td>
							<td>${tipicausalioneri_var.coDescrizione}</td>
							<td>
								<c:if test="${tipicausalioneri_var.coSerichiedeendo}">Si</c:if>
								<c:if test="${tipicausalioneri_var.coSerichiedeendo eq false}">No</c:if> 
							</td>
							<td>${tipicausalioneri_var.codicecausalepeople}</td>							
							<td>
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../tipicausalioneri/view.htm?codice=${tipicausalioneri_var.id.codice}');" title="<fmt:message key="label.edit.record" />${tipicausalioneri_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</fieldset>
		</div>		
			
		<input type="hidden" name="flgTipicausaliinteressi" value="${isMora}" />			
		</form>
		<script type="text/javascript">
			/* var _jmesaUrl='list.htm?flgTipicausaliinteressi=${isMora}&';
			var _captionTab='<fmt:message key="tipicausalioneri.label.lista_tipicausalioneri.title" />'; */
			
			function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("tabella_tco");
				tr = table.getElementsByTagName("tr");
				let trovato = false;
				for (i = 1; i < tr.length; i++) {
				  td = tr[i].getElementsByTagName("td");
				  for (let cell of td) {
				    if (cell) {
				      txtValue = cell.textContent || cell.innerText;			       
				      if (txtValue.toUpperCase().indexOf(filter) > -1) {			        
				      	trovato = true;
				      }			        
				    }
				  }
				  if(trovato){
				  	tr[i].style.display = "";
				  	trovato = false;
				  } else{
				  	tr[i].style.display = "none";
				  	
				  }
				}
			}
		</script>
	</div>
	<div class="form-button">
		<a class="btn btn-primary" href="javascript:historySet('${_urlback }','../tipicausalioneri/create.htm?isMora=${isMora}');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
</body>
</html>