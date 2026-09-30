<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="esportazioni.label.lista_esportazioni.title" /></title>
	<style type="text/css" >
		.funzioni-dettaglio {
			margin: 0;
			padding: 0;
			list-style-type: none;
			width: 100%;
		}
		
		.funzioni-dettaglio>li {
			display: inline-block;
			margin-right: 16px;
			cursor: pointer;
			color: #b61218;
		}
		
		.funzioni-dettaglio>li:hover {
			text-decoration: underline;
		}
	</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="esportazioni.label.lista_esportazioni.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent" class="vbg-form">
	
	<div id="popup" class="vgb-modal vbg-form">
		<fieldset>
			<legend>Job</legend>		
		<div class="intestazione"></div>
		<div class="vbg-modal-body">
			<input type="hidden" id="id_hidden" />
			<div class="form-group">
				<label>
					<fmt:message key="esportazioni.label.lista_esportazioni.idcomune" /> 
                </label>
				<input type="text" id="in_idcomune" name="idcomune"/>
			</div>
			<div class="form-group">
				<label>
					<fmt:message key="esportazioni.label.lista_esportazioni.descrizione" />  
                </label>
				<input type="text" id="in_descrizione" name="descrizione"/>
			</div>
			<div class="form-group">
				<label>
                	<fmt:message key="esportazioni.label.lista_esportazioni.trasformazione" />  
                </label>
				<input type="text" id="in_trasformazione" name="trasformazione"/>
			</div>
			<div class="form-group">
				<label>
                	<fmt:message key="esportazioni.label.lista_esportazioni.contesto" />
                </label>                
                <spring-form:select path="tipicontestoesportazione" id="in_contesti" >
                	<spring-form:options items="${tipicontestoesportazione}" itemLabel="descrizione" itemValue="codice" />               
                </spring-form:select>  
			</div>
			<div class="form-group">
				<label>
                	<fmt:message key="esportazioni.label.lista_esportazioni.software" />
                </label>
                <spring-form:select path="softwareList" id="in_software">
					<spring-form:options items="${softwareList}" itemLabel="descrizione" itemValue="codice" />
				</spring-form:select>
			</div>
			<div class="form-group">
				<label>
                	<fmt:message key="esportazioni.label.lista_esportazioni.abilitata" />   
                </label>
				<input type="checkbox" id="in_flgabilitata" name="abilitata"/>
			</div>		
			<div class="vbg-modal-footer">
				<a id="bottone_inserisci" href="javascript:void(0)" class='bottone-salvataggio btn btn-primary'><fmt:message key="button.insert" /></a>
				<a id="bottone_salva" href="javascript:void(0)" class='bottone-salvataggio btn btn-primary'><fmt:message key="button.update" /></a>
				<a id="bottone_chiudi" href="#" data-role='toggle-popup' class="btn btn-secondary"><fmt:message key="button.back" /></a>
			</div>
		</div>	
		</fieldset>
	</div>	
	
	<div class="tabella_esportazioni vbg-form">
		<fieldset>
			<legend>Lista jobs</legend>
		
			<table class="vbg-table" id="tabella_esportazioni">
				<thead>
					<tr>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.idcomune" /></th>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.descrizione" /></th>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.trasformazione" /></th>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.contesto" /></th>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.software" /></th>
						<th><fmt:message key="esportazioni.label.lista_esportazioni.abilitata" /></th>
						<th><fmt:message key="label.azioni" /></th> 
					</tr>
				</thead>
				
				<tbody class="tbody">
					<c:forEach items="${listEsportazioni}" var="esportazione" varStatus="loop">
						<tr class="riga-esportazione" data-id-riga="${esportazione.id.codice}" id="riga-esportazione${esportazione.id.codice}">
						<input type="hidden" value="${esportazione.id.codice}" id="idExport"/>
							<td class="riga_idcomune" data-id-comune="${esportazione.id.idcomune }">${esportazione.id.idcomune }</td>
							<td class="riga_descrizione">${esportazione.descrizione }</td>
							<td class="riga_trasformazione">${esportazione.trasformazione }</td>
							<td class="riga_tipicontesto">${esportazione.tipicontestoesportazione.descrizione }
							<input type="hidden" id="riga_tipicontesto_hidden" value="${esportazione.tipicontestoesportazione.codice }"/>
							</td>
							<td class="riga_software" data-codice-software="${esportazione.software.codice }">${esportazione.software.codice }</td>
							<td class="riga_abilitato"><input type="checkbox" id="flagabilitato_id" ${esportazione.flgAbilitata?'checked':'' } disabled></td>
							<td>
								<ul class="funzioni-dettaglio">														
									<li class="azione cmd-rettifica" id="modifica" data-id-riga="${esportazione.id.codice}">
										<i class="fa fa-edit"></i>
										<fmt:message key="label.rettifica" />
									</li>							
									<li class="azione cmd-cancella" id="elimina">
										<i class="fa fa-trash-o"></i>
										<fmt:message key="label.elimina" />
									</li>							
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
			
		</div>
	<div class="vbg-form">
		<div class="form-button">						
			<a class="btn btn-primary" id="bottoneNuovo" href="javascript:void(0)"><fmt:message key="button.new" /></a>
			<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>	
		</div>
	</div>
	 <jsp:include page="./funzioniJS.jsp" />
	
</body>
</html>