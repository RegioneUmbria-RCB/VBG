<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${protocollotipidocumento.id.codice eq null}">
			<fmt:message key="label.nuovo_tipo_documento_protocollo.title" />
			
		</c:if> 
		<c:if test="${protocollotipidocumento.id.codice ne null}">
			<fmt:message key="label.dettaglio_tipo_documento_protocollo.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${protocollotipidocumento.id.codice eq null}">
	<fmt:message key="label.nuovo_tipo_documento_protocollo.title" />
</c:if> 
<c:if test="${protocollotipidocumento.id.codice ne null}">
	<fmt:message key="label.dettaglio_tipo_documento_protocollo.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>

<div id="subcontent">
	<spring-form:form commandName="protocollotipidocumento" name="inviodati">
	<spring-form:hidden path="id.codice" />
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="protocollotipidocumento" />
    </jsp:include>
	<table>
		<c:if test="${protocollotipidocumento.id.codice eq null}">
        <tr>
			<td><fmt:message key="label.comune" /></td>
			<td>
			    <spring-form:select  path="comune.codicecomune">
			     	<option value=""><fmt:message key="label.tutti" /></option>
			        <c:forEach items="${responsabilicomunis}" var="respcomuni">
			       		<option value="${respcomuni.comune.codicecomune}">${respcomuni.comune.comune}</option>
			        </c:forEach>
			  </spring-form:select>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.software" /></td>
			<td>
			    <spring-form:select  path="software.codice">
			     	<c:forEach items="${responsabilisoftwares}" var="respsoftware">
			       		<option value="${respsoftware.software.codice}">${respsoftware.software.descrizione}</option>
			        </c:forEach>
			  </spring-form:select>
			</td>
		</tr>	
		<tr>
			<td><fmt:message key="label.codice" /></td>
			<td><spring-form:input id="codice_id" path="codice" size="15" />
			<spring-form:errors path="codice" cssClass="error"/></td> 
		</tr>
		</c:if>
		<c:if test="${protocollotipidocumento.id.codice ne null}">
		<tr>
			<td><fmt:message key="label.comune" /></td>
			<td><b>
				<c:choose>
					<c:when test="${not empty protocollotipidocumento.comune.codicecomune }">
					${protocollotipidocumento.comune.comune}
					</c:when>
					<c:otherwise><fmt:message key="label.tutti" /></c:otherwise>
				</c:choose>
				</b>				
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.software" /></td>
			<td>	<b>			
						${protocollotipidocumento.software.descrizione}
					</b>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="label.codice" /></td>
			<td><spring-form:input id="codice_id" path="codice" size="15" readonly="true"/>
			<spring-form:errors path="codice" cssClass="error"/></td> 
		</tr>
		</c:if>
		
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="70" />
			<spring-form:errors path="descrizione" cssClass="error"/></td> 
		</tr>
		
	</table>
	<br class="clear"/>
	
		<fieldset>
		<legend><fmt:message key="label.configurazione_metadati" /></legend>
			<div id="metadatitable_id">
			
			</div> 
		</fieldset>
</spring-form:form>


<script type="text/javascript">


function ajaxAggiungiMetadato(selectId){
	
	var md_id=jQuery('#'+selectId).val();
	
	if(md_id!=''){		
	var jhqrPr = jQuery.ajax({
		  url: '../protocollotipidocumento/ajaxAggiungiMetadato.htm',
		  context: document.body,
		  cache: false,
		  data: "codice=${protocollotipidocumento.id.codice}&metadatoId="+md_id,
		  dataType: "html",
		  success: function(data) {
			  if(data=='OK'){
				  popolaTabellaMetadati();  
			  }else{
				  alert(data);
			  }
			  
		  },
		  error: function(jqXHR, textStatus, errorThrown){
				console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
		}
	});
	}
}

function ajaxEliminaMetadato(md_id){
	if(confirm('<fmt:message key="javascript.confirm.delete" />')){
	var jhqrPr = jQuery.ajax({
		  url: '../protocollotipidocumento/ajaxEliminaMetadato.htm',
		  context: document.body,
		  cache: false,
		  data: "codice=${protocollotipidocumento.id.codice}&metadatoId="+md_id,
		  dataType: "html",
		  success: function(data) {
			  if(data=='OK'){
				  popolaTabellaMetadati();  
			  }else{
				  alert(data);
			  }
			  
		  },
		  error: function(jqXHR, textStatus, errorThrown){
				console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
		}
	});
	}
}

function popolaTabellaMetadati(){
	var jhqrPr = jQuery.ajax({
		  url: '../protocollotipidocumento/ajaxPopolaTabellaMetadati.htm',
		  context: document.body,
		  cache: false,
		  data: "codice=${protocollotipidocumento.id.codice}",
		  dataType: "html",
		  success: function(data) {
			  if(data){
				  jQuery('#metadatitable_id').html(data);	
			  }
		  },
		  error: function(jqXHR, textStatus, errorThrown){
				console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
		}
	});
}

<c:if test="${protocollotipidocumento.id.codice ne null}">
jQuery(document).ready(function(){	
	popolaTabellaMetadati();				
	
});
</c:if>
</script>

</div>
<div id="functions">
<ul>
	<c:if test="${protocollotipidocumento.id.codice eq null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${protocollotipidocumento.id.codice ne null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
