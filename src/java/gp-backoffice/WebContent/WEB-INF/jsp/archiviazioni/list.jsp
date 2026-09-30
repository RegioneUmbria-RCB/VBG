<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.lista_archiviazioni" /></title>
	</head>
	<body>
		<style>
		 .to-accept{
		 	width: 100%;
		 	height: 16px;
		 	cursor: pointer;
		 	background-repeat: no-repeat;
		 	background-position: center;
		 	background-image: url("../images/accept-gray.gif");
		 }
		 .to-accept:hover{
		 	background-image: url("../images/accept.png");	 	
		 }
		 .accepted{
		 	background-image: url("../images/accept.png");
		 	background-repeat: no-repeat;
		 	background-position: center;
		 	width: 100%;
		 	height: 16px;
		 }
		</style>
		<span class="titoloPagina"><fmt:message key="label.lista_archiviazioni" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
		</jsp:include>
		<div id="subcontent">
			<div id="error_msg" class="error_header">
			<c:if test="${not empty param.status_msg }">    
	    		<fmt:message key="${param.status_msg }"/><br />
	    		<c:out value="${param.error_msg }" />   	
	    	</c:if>
			</div>
			<form name="inviodati" action="list.htm">
				${htmlTable}
				<input type="hidden"  value="${isSoloConErrori}" name="isSoloConErrori"/>
				
				<br /><br />
			<table width="100%">
				<tr class=titoloSezione>
					<td colspan="2"><fmt:message key="label.filtri" /></td>
				</tr>
				<tr>
				<td>
				    <fmt:message key="label.da" />
					<input id="data_id" name="dataDa" size="10" maxlength="10" value="${da}" onblur="isValidDate(this,true);"/>
					<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
				
				    <fmt:message key="label.a" />
					<input id="dataA_id" name="dataA" size="10" maxlength="10"  onblur="isValidDate(this,true);"/>
					<init:calendar imagePath="/images/cal.gif" idImage="caldataA" idInput="dataA_id" textKey="label.calendar"/>
					<fmt:message key="help.label.date_archiviazione" />
				</td>
				</tr>
			</table>
				
			</form>
		</div>
		
		
		<div id="functions">
			<ul>
			<c:choose>
			    <%-- 
				<c:when test="${tipoAlgoritmo eq 'ARCHIVIAZIONE_MULTI_ISTANZE'}">
					<li><a href="javascript:archivia()"><fmt:message key="button.esegui_archiviazione" /></a></li>
				</c:when>
				--%>
				<c:when test="${tipoAlgoritmo eq 'ARCHIVIAZIONE_PER_OGGETTO'}">
					<li><a href="javascript:archiviaPerOggetto()"><fmt:message key="button.esegui_archiviazione" /></a></li>
				</c:when>
				<%-- 
				<c:when test="${tipoAlgoritmo eq }">
					<li><a href="javascript:archiviaIstanza()"><fmt:message key="button.esegui_archiviazione_per_istanza" /></a></li>
				</c:when>
				--%>
			</c:choose>
			<c:if test="${!isSoloConErrori}">
				<li><a href="javascript:listSoloErrori('si');"><fmt:message key="button.mostra_archiviazione_solo_con_errori" /></a></li>
			</c:if>
			<c:if test="${isSoloConErrori}">
				<li><a href="javascript:listSoloErrori('no')"><fmt:message key="button.mostra_archiviazione_tutte" /></a></li>
			</c:if>
			
			</ul>
		</div>
		<script type="text/javascript">
		
		function archivia(){
			doHref('../archiviazioni/archivia.htm','<fmt:message key="alert.conferma_archiviazione"/>');
			
		}
		function archiviaPerOggetto(){
			//doHref('../archiviazioni/archiviaPerOggetto.htm','<fmt:message key="alert.conferma_archiviazione"/>');
			doSubmit('../archiviazioni/archiviaPerOggetto.htm','<fmt:message key="alert.conferma_archiviazione"/>',document.inviodati)
			
		}
		
		function listSoloErrori(val){
			
			if(val == 'si')
			{
				doHref('../archiviazioni/list.htm?isSoloConErrori=true','');
			}else
			{
				doHref('../archiviazioni/list.htm?isSoloConErrori=false','');
			}
			
			
			
		}
		
		/**
		function archiviaIstanza(){
			doHref('../archiviazioni/archivia.htm','<fmt:message key="alert.conferma_archiviazione"/>');
			
		}
		**/
		$('error_msg').pulsate({ pulses: 2, duration: 1.0 });
		jQuery(function(){
			jQuery(".to-accept").on('click',function (e) {
				var id = jQuery(this).data("id");
				doHref('../archiviazioni/ajaxUpdateCorretto.htm?archId='+id,'Confermi la validazione dell\'archiviazione numero '+id+' ?');
			});

			jQuery(".toggle").each(function () {
				jQuery(this).html('[+]').css("cursor", "pointer");
				jQuery(this).click(function() {
				  if (jQuery(this).text().slice(0,3) === '[-]') {
					  jQuery(this).next().hide();
					  jQuery(this).html('[+]');
	              }else{
	            	  jQuery(this).html('[-]');
	            	  jQuery(this).next().show();
	              }
				});		
			});
		});
		
		</script>
	</body>
</html>