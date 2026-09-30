<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commissioniediliziet.entity.id.codice==null}">
			<fmt:message key="label.nuova_commissione_edilizia" />
		</c:if> 
		<c:if test="${commissioniediliziet.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_commissione_edilizia" />
		</c:if>
	</title>
</head>
<body>

    <c:set scope="page" value="${sizeConvocazionis}" var="sizeList"></c:set>
	<span class="titoloPagina">
		<c:if test="${commissioniediliziet.entity.id.codice==null}">
			<fmt:message key="label.nuova_commissione_edilizia" />
		</c:if> 
		<c:if test="${commissioniediliziet.entity.id.codice!=null}">
			<fmt:message key="label.dettaglio_commissione_edilizia" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commissioniediliziet" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissioniediliziet" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.numero_commissione" />
					</td>
					<td>
						<spring-form:input id="numprotocollo_id" path="entity.numprotocollo" size="12" />
						<spring-form:errors path="entity.numprotocollo" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" />
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="entity.note" cols="40" />
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.stato" />
					</td>
					<td>
						<spring-form:select id="flagaperta_id" path="entity.flagaperta">
						 	<c:if test="${commissioniediliziet.entity.flagaperta eq null }">
								<option value="1" ><fmt:message key="label.aperta" /></option>
								<option value="0" ><fmt:message key="label.chiusa" /></option>
							</c:if>
						    <c:if test="${commissioniediliziet.entity.flagaperta eq true }">
								<option value="1" selected="selected"><fmt:message key="label.aperta" /></option>
								<option value="0" ><fmt:message key="label.chiusa" /></option>
							</c:if>
							<c:if test="${commissioniediliziet.entity.flagaperta eq false }">
								<option value="1" ><fmt:message key="label.aperta" /></option>
								<option value="0" selected="selected"><fmt:message key="label.chiusa" /></option>
							</c:if>
						</spring-form:select>
						<spring-form:errors path="entity.flagaperta" cssClass="error"/>
					</td>
				</tr>
				
				<tr>
					<td>
						<fmt:message key="label.tipologia" />
					</td>
					<td>						
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipologia_id" />
							<jsp:param name="propertyPath" value="entity.commedilizieTipologie" />										
							<jsp:param name="pathPropertyDescription" value="entity.commedilizieTipologie.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.commedilizieTipologie.id.codice" />
							<jsp:param name="autocompleterAjax" value="findCommedilizieTipologia.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipologia_commissione" />
							<jsp:param name="autocompleterInputSize" value="68" />
						</jsp:include>
					</td>
				</tr>
			</table>
			
			<!-- TABELLA STILE JMESA CHE VISUALIZA TUTTE LE ICONVOCAZIONI(cOMMEDILIZIECONVOCAZIONI) -->
 			 <div class="jmesa" >
        			<table  cellpadding="0"  cellspacing="0"  class="table" width="100%">
 						<thead>
							<tr  class="header">
								<td colspan="5"><fmt:message key="label.convocazione"/></td>
							</tr>
						</thead>
		                <%
					    int i=0;
					    %>
						<tbody class="tbody">
						<c:if test="${not empty commedilizieConvocazionis}">
						<c:forEach items="${commedilizieConvocazionis}" var="convocazioni" varStatus="indice_convocazioni">
						
							<tr class="<%=(i%2)==0?"odd":"even"%>">
								<td width="10%">
								    <c:if test="${commissioniediliziet.entity.idconvocazione!=null && commissioniediliziet.entity.idconvocazione==convocazioni.id.codice}">
										<input id="id_checkbox${indice_convocazioni.index}" type="checkbox" checked="checked" onclick="javascript:updateIdConvocazione(${commissioniediliziet.entity.id.codice},${convocazioni.id.codice},${sizeList},'id_checkbox${indice_convocazioni.index}')"/>
									    
									</c:if>
									<c:if test="${(commissioniediliziet.entity.idconvocazione!=null && commissioniediliziet.entity.idconvocazione!=convocazioni.id.codice) || commissioniediliziet.entity.idconvocazione==null}">
										<input id="id_checkbox${indice_convocazioni.index}" type="checkbox" onclick="javascript:updateIdConvocazione(${commissioniediliziet.entity.id.codice},${convocazioni.id.codice},${sizeList},'id_checkbox${indice_convocazioni.index}')"/>
									    
									</c:if>
									
								</td>		
								<td  width="10%">
									<a href="javascript:doHref('../commedilizieconvocazioni/view.htm?codice=${convocazioni.id.codice}','')" title="<fmt:message key="label.azioni" /> ${convocazioni.id.codice}">
									<%=i+1%><fmt:message key="label.a"/>&nbsp;<fmt:message key="label.convocazione"/>
								</a>:
								</td> 
								<td width="10%" align="right" ><fmt:formatDate value="${convocazioni.dataconvocazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
								<td width="10%" align="right">${convocazioni.oraconvocazione}</td>
								<td><div id="aggiornato${convocazioni.id.codice}" style="display: none;"></div></td>
							</tr>
						<%i++;%>
					 </c:forEach>
					 </c:if>
					 <c:if test="${empty commedilizieConvocazionis}">
					 <tr class="even">
						<td align="center"><fmt:message key="label.convocazioni_non_presenti"/></td>
					</tr>
					 </c:if>	
					</tbody>
				</table>
 			</div>
			<script type='text/javascript'>
				$('numprotocollo_id').focus();
				
				function updateIdConvocazione(idcommissione,idconvocazione,size,isCheck){
					// va a mettere i check a false tranne quello che abbiamo cliccato
					if(size!=1 &&  document.getElementById(isCheck).checked==true)
					{
					
					for(i=0;i<size;i++)
					{	
						if(document.getElementById('id_checkbox'+i) != document.getElementById(isCheck))
							{
								document.getElementById('id_checkbox'+i).checked=false; 
							}
					}
					
					doHref('../commissioniediliziet/updateIdConvocazione.htm?codiceCommissione='+idcommissione+'&codiceConvocazione='+idconvocazione,'');
					}
					else
					{
						alert('<fmt:message key="alert.convocazioni_in_commissione_obbligatoria"/>')
						document.getElementById(isCheck).checked=true;
					}
			}
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commissioniediliziet.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commissioniediliziet.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				<li><a href="javascript:doHref('listCommissioniedilizieR.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}','');"><fmt:message key="button.dettaglio" /></a></li>
				<li><a href="javascript:doHref('../commedilizieconvocazioni/create.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}','');"><fmt:message key="button.nuova_convocazione" /></a></li>
				<li><a href="javascript:doHref('../commedilizieappello/list.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}','');"><fmt:message key="button.appello_iniziale" /></a></li>
				<!--  da definire -->
				<%-- STAMPA --%>
				<%pageContext.setAttribute("URL_STAMPA",BackofficeNETConstants.getURL_STAMPA_COMMISSIONI_EDILIZIE_T());%>
				<c:set var="_URL_STAMPA" value="${URL_STAMPA}?SoloDocTipo=1&windowed=S&CodiceCommissione=${commissioniediliziet.entity.id.codice}" /><c:set var="_URL_STAMPA" value="${inite:geturlto(pageContext.request, _URL_STAMPA, _urlback, null, true)}" />
				<li><a href="javascript:void 0"	onclick="window.open('${_URL_STAMPA}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message key="button.stampa" /></a></li>
				<%-- END STAMPA --%>
				<li><a href="javascript:doHref('../commedilizieallegati/list.htm?codiceCommissione=${commissioniediliziet.entity.id.codice}','');"><fmt:message key="button.allegati" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>