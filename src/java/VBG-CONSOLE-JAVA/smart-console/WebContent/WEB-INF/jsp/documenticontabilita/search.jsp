<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.ricerca_documenti_contabilita.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.ricerca_documenti_contabilita.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search"/>
	</jsp:include> 
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../documenticontabilita/searchDocumenti" />
	</jsp:include>
	<div id="subcontent">	
		<spring-form:form commandName="documenticontabilita" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="documenticontabilita" />
		    </jsp:include>
	    	<fieldset>
    		<legend><fmt:message key="label.form_ricerca" /></legend>
	    	<table width="50%">	
				<tr>
					<td><fmt:message key="label.da" /></td>
					<td>	
						<spring-form:input tabindex="5" id="dataDa_id" path="filter.dataDa" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatada" idInput="dataDa_id" textKey="label.calendar"/>
				    </td>
					<td><fmt:message key="label.a" /></td>
					<td>
						<spring-form:input tabindex="6" id="dataA_id" path="filter.dataA" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataa" idInput="dataA_id" textKey="label.calendar"/>
						<spring-form:errors path="filter.dataA" cssClass="error" delimiter=" :"/>  
					</td>
				</tr>
				<tr>	
					<td><fmt:message key="label.anno" /></td>
					<td><spring-form:input id="anno_id" path="filter.anno" size="6"/></td>
					<td><fmt:message key="label.mese" /></td>
					<td>
						<spring-form:select path="filter.mese">
							<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:options items="${listaMesi}" itemLabel="descrizione" itemValue="codice"/>
						</spring-form:select>
						<%-- <spring-form:input id="mese_id" path="filter.mese" size="10"/> --%>
					</td>
				</tr>
				<tr>
					<script type="text/javascript">
						function setHiddenViewDocument(inputField,listItem){
							var a = listItem.id;
							
							//document.location.href = '../documenticontabilita/view.htm?codice='+a;
							historySet('${_urlback}','../documenticontabilita/view.htm?codice='+a,'');
					
						}
					</script>
				
					<td><fmt:message key="label.documento" /></td>
					
					<td colspan="3">
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="documenticontabilita" />					
							<jsp:param name="propertyPath" value="filter.nomedocumento" />	
							<jsp:param name="pathPropertyDescription" value="filter.nomedocumento" />
							<jsp:param name="pathPropertyCode" value="filter.id.codice" />
							<jsp:param name="autocompleterAjax" value="findDocumentiContabilita.htm" />
							<jsp:param name="afterUpdateElement" value="setHiddenViewDocument" />
							<jsp:param name="titleKey" value="label.ricerca_documenti_contabilita" />
						</jsp:include>
						
					</td>			
				</tr>
			</table>
			</fieldset>
			<div id="functions">
				<ul>
					<li><a href="javascript:javascript:doSubmit('../documenticontabilita/list.htm','',document.inviodati);"><fmt:message key="button.search" /></a></li>
					<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>						
				</ul>
			</div>
			<br />
			
			
			
			
			
			<fieldset style="margin-top:  50px">
    			<legend>
    			     <% 
    						String displaySchedeAttivita = "display: none;";
							String styleSchedeAttivita = "sezioneDatiPiu";
    			 
    				    	if(StringUtils.isNotBlank((String)request.getAttribute("_displaySchedeAttivita")) 
    					   		 && StringUtils.isNotBlank((String)request.getAttribute("_sezioneDatiPiu")))
    				    	{
	    						displaySchedeAttivita=(String)request.getAttribute("_displaySchedeAttivita");
	    						styleSchedeAttivita=(String)request.getAttribute("_sezioneDatiPiu");
    				    	}
					 %>
						<td width="100%">
						    <a
							class="<%=styleSchedeAttivita%>"
							id="id_pannello_inserimento"
							href="javascript:showHidePanelBase('id_pannello_inserimento_table','id_pannello_inserimento','','${pageContext.request.contextPath}/images/','table',false);"
							title="<fmt:message key="label.mostra_nasconde_sezione" /><fmt:message key="label.form_inserimento" />">
							<label for="id_pannello_inserimento"><fmt:message key="label.form_inserimento" /></label> 
							</a>
    			
    		</legend>
	    	<table style="<%=displaySchedeAttivita%>" id="id_pannello_inserimento_table"  width="50%">	
	    		<tr>
					<td><fmt:message key="label.documento" /></td>
					<td>
						<spring-form:input id="nome_doc_id" path="entity.nomedocumento" size="60"/>
						<spring-form:errors path="entity.nomedocumento" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td>
						<spring-form:textarea path="entity.documento" rows="5" cols="62"></spring-form:textarea>
						<spring-form:errors path="entity.documento" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.da" /></td>
					<td>	
						<spring-form:input id="data_id" path="entity.data" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.data" cssClass="error"/> 
				    </td>
				 </tr> 
				 <tr>
				 	<td><fmt:message key="label.documento" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice" />
						<jsp:param name="codiceOggetto" value="${entity.documenticontabilita.oggetti.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
						<jsp:param name="nomefileId" value="oggetto_nomefile" />					
						</jsp:include> 
						<spring-form:hidden path="entity.oggetti.id.codice" id="oggetto_id_codice" />
						<spring-form:hidden path="entity.oggetti.nomefile" id="oggetto_nomefile" />
						<spring-form:errors path="entity.oggetti" cssClass="error"/>
					</td>
				 </tr> 
				 <tr>
				 	<td>
				 	<div id="functions">
					<ul>
						<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>					
					</ul>
					</div>
				 	</td>
				 </tr>
	    	</table>
	    	<table>
	    		<c:if test="${codiceDocInserito!=null }">
					 <tr>
					    <td><b><fmt:message key="label.dettaglio_documento_inserito" /></b></td>
					 	<td>
					 	    
						 	<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../documenticontabilita/view.htm?codice=${codiceDocInserito}','');"  title="<fmt:message key="label.edit.record" /> ${conti_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>
					 	</td>
					 </tr> 
				 </c:if>
	    	</table>
	    	
	    	</fieldset>
	    </spring-form:form>
	</div>
	
	<script type="text/javascript">
	
	
	
	</script>
</body>
</html>