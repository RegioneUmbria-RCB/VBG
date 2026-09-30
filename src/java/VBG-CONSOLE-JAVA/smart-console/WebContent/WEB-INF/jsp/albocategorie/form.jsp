<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
		<c:if test="${albocategorie.id.codice==null}">
			<fmt:message key="albocategorie.label.nuovo_albocategoria" />
		</c:if> 
		<c:if test="${albocategorie.id.codice!=null}">
			<fmt:message key="albocategorie.label.dettaglio_albocategoria" />
		</c:if>
	</title>
</head>
<body>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../albocategorie/view" />
</jsp:include>
<span class="titoloPagina">
<c:if test="${albocategorie.id.codice==null}">
	<fmt:message key="albocategorie.label.nuovo_albocategoria" />
</c:if> 
<c:if test="${albocategorie.id.codice!=null}">
	<fmt:message key="albocategorie.label.dettaglio_albocategoria" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="albocategorie" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="albocategorie" />
    </jsp:include>
	<table>
		<tr>
			<td><fmt:message key="albocategorie.label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td> 
		</tr>
		<tr>
			<td><fmt:message key="albocategorie.label.ordine" /></td>
			<td><spring-form:input id="ordine_id" path="ordine" size="4" onchange="checkNumberInt(this)" maxlength="4" cssStyle="text-align:right;" />
			<spring-form:errors path="ordine" cssClass="error"/></td>
		</tr>
	</table>	
	<script type='text/javascript'>
		$('descrizione_id').focus();
	</script>	
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${albocategorie.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${albocategorie.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
<c:if test="${albocategorie.id.codice!=null}">
<br/><br/><br/>
<span class="titoloTabella"><fmt:message key="albocategorie.label.lista_albopubblicazioni" /></span>
		<form name="albopubblicazioniForm" action="view.htm">
				<jmesa:springTableFacade
					id="alboPubblicazioni_id" 
					items="${albocategorie.alboPubblicazionis}"
					var="albopubblicazioni_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DataAlbopubblicazioniFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Falbopubblicazioni%2Fview.htm%3Fcodice%3D${albopubblicazioni_var.id.codice}','');">${albopubblicazioni_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="descrizione" titleKey="albopubblicazioni.label.descrizione" width="500px"/>
							<jmesa:htmlColumn property="dataCreazione" width="150" titleKey="albopubblicazioni.label.datacreazione" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataCreazioneAlboCategorieCustomFilter" />
							<jmesa:htmlColumn property="validaAl" width="150" titleKey="albopubblicazioni.label.validaal" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.ValidaAlAlboCategorieCustomFilter" />
							<jmesa:htmlColumn property="" titleKey="label.azioni" sortable="false" filterable="false" width="8%">
								<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Falbopubblicazioni%2Fview.htm%3Fcodice%3D${albopubblicazioni_var.id.codice}','');" title="<fmt:message key="label.edit.record" /> ${albopubblicazioni_var.id.codice}" > 
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>					
								<a class="eliminaRiga" href="javascript:doSubmit('deletePubblicazioneFromCodice.htm?codice=${albopubblicazioni_var.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)" title="<fmt:message key="label.elimina" /> ${albopubblicazioni_var.id.codice}">
									<label><fmt:message key="label.elimina" /></label>
								</a>							
							</jmesa:htmlColumn>							
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
				<input type="hidden" value="${albocategorie.id.codice}" name="codice"/>
			</form>
<div id="functions">
<ul>			
	<li>
		<a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Falbopubblicazioni%2FcreateFromCategoria.htm%3Fcodicecategoria%3D${albocategorie.id.codice}','');" title="<fmt:message key="albocategorie.button.nuova_pubblicazione" />" >
			<fmt:message key="albocategorie.button.aggiungi_pubblicazione" />
		</a>
			
	</li>		
</ul>
</div>				
			<script type="text/javascript">
				var _jmesaUrl='../albopubblicazioni/listFromCategoria.htm?codicecategoria='+${albocategorie.id.codice}+'&';
				var _captionTab='<fmt:message key="albocategorie.label.lista_albopubblicazioni" />';
			</script>		
</c:if>
</body>
</html>