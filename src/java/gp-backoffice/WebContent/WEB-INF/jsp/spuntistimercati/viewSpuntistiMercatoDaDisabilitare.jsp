<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="manifestazioni.label.lista_spuntisti.title" />
	</title>
</head>
<body>

	<span class="titoloPagina">
		<fmt:message key="manifestazioni.label.lista_spuntisti_da_disabilitare.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../spuntistimercati/viewSpuntistiMercato" />	    	
		</jsp:include>	
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.mercato" />:</span>
			<span class="header_dato_valore">
			<div>${mercati.descrizione}</div>
			</span>
		</div>
		<div class="header_dato">
			<span class="header_dato_etichetta"><fmt:message key="label.giorno" />:</span>
			<span class="header_dato_valore">
			<div>${mercatiUso.descrizione}</div>
			</span>
		</div>
		
		<br class="clear" />
	
		<div class="jmesa">
		<table border="1" cellpadding="2" cellspacing="0" class="table">
     	    <%int j=1;%>
			<tr class="header" >
				    <td width="20%"><fmt:message key="label.spuntista"/></td>
				    <td width="8%"><fmt:message  key="label.autorizzazione"/></td>
				    <td width="10%"><fmt:message key="label.data_registrazione" /></td>
					<td width="10%"><fmt:message key="label.data_ultima_registrazione_presenza" /></td>
			</tr>	
				<c:if test="${ not empty  listSpuntistiDadisabilitare}">
					<c:forEach items="${listSpuntistiDadisabilitare}" var="var" varStatus="b1">	
					    <tr style="${styleDisattivati}" class= "<%=(j%2)==0?"odd":"even"%>">
					    <td>${var.descrizioneRichiedente}</td>
						<td>${var.autoriznumero}</td>
						<td><fmt:formatDate value="${var.dataregistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td><fmt:formatDate value="${var.dataregistrazionepresenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					</tr>
					<%j++;%>								
					</c:forEach>
				</c:if>
				<c:if test="${empty  listSpuntistiDadisabilitare}">
					<tr>
				    	<td colspan="4" align="center""><fmt:message key="label.record_non_presenti"/></td>
				    </tr>
				</c:if>
		</table>
		
		<div id="dialog-6">
         	<fmt:message key="label.messaggio_disabilita_spunt_fiere_per_operatore">
					<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
			</fmt:message>
	         <div>
	            <label for="terms"><fmt:message key="label.accettazione_condizioni_disabilita_spuntisti_fiere"/></label>
	            <input type="checkbox" id="terms">
	         </div>
     	 </div>
		
		
		<script>
		  	
		jQuery(function () {
	      	
	  		jQuery(".disabilita_id").conferma({
	      		dialogSelector: '#dialog-6',
	      		mercatoId: 'codiceMercato',
	      		text_button: 'Disabilita' ,
	      		text_title:	'Conferma operazione',
	      		callback: function (mercatoId) {
	      			//alert(id);
	      			disabilita(mercatoId.codiceMercato,mercatoId.codiceUso)
	      		}
	      	});
	  		
	  	});
	  	
	  	function disabilita(mercatoId,usoId){
			doHref('updateSpuntistiMercatoDaDisabilitare.htm?codiceMercato='+mercatoId+'&codiceuso='+usoId,'');
		}
		  	
		
  		</script>
		
	<div id="functions">
		<ul>
		    <li><a href="javascript:void(0);" class="disabilita_id" data-codice-mercato="${mercati.id.codice}" data-codice-uso="${mercatiUso.id.codice}"><fmt:message key="button.disabilita" /></a></li>
		   	<%-- <li><a href="javascript:doHref('updateSpuntistiMercatoDaDisabilitare.htm?codiceMercato=${mercati.id.codice}&codiceuso=${mercatiUso.id.codice}','')" "><fmt:message key="button.disabilita" /></a></li> --%>
		   	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		   
		</ul>
	</div>
</body>
</html>