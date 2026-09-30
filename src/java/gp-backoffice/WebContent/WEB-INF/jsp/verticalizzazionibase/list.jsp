<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_verticalizzazioni_base" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_verticalizzazioni_base" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
     <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../verticalizzazionibase/list" />
	</jsp:include>
	<div id="subcontent">
	
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
			<fieldset >
			<legend><fmt:message key="label.lista_verticalizzazioni_base" /></legend>
				<table id="tabella_regole" class="vbg-table searchable sortable">
					<thead>
						<tr>							
							<th><fmt:message key="label.azioni"/></th>
							<th><fmt:message key="label.regola"/></th>
							<th><fmt:message key="label.descrizione"/></th>					
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${verticalizzazionibaseList }" var="verticalizzazionebase">
							<tr>								
								<td>
									<a class="verticalizzazioniparametriColumn" href="javascript:doHref('listregoleconfigurate.htm?codice=${verticalizzazionebase.modulo }','');" title="<fmt:message key="label.lista_regole" /> ${verticalizzazionebase.modulo}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a> 	
								</td>
								<c:set var="classVert" value=""/>
								<c:if test="${verticalizzazionebase.flagConfigurata eq true}"> 
									<c:set var="classVert"> red </c:set>									
								</c:if>
								
								<td class="${ classVert } descrizione_modulo">
									${verticalizzazionebase.modulo}
								</td>
								<td><c:out value="${verticalizzazionebase.descrizione}" escapeXml="true"/></td>											
							</tr>				
						</c:forEach>
					</tbody>
				</table>
			</fieldset>
			</div>
	</div>

<script type="text/javascript">
		
		function filtraTestoCelle() {
					
			let input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("input_ricerca");
			filter = input.value.toUpperCase();
			table = document.getElementById("tabella_regole");
			tr = table.getElementsByTagName("tr");
			let trovato = false;
			for (i = 1; i < tr.length; i++) {
			  td = tr[i].getElementsByClassName("descrizione_modulo");
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
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>