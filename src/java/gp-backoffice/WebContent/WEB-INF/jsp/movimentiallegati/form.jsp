<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.movimenti_allegati" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.movimenti_allegati" />	
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../movimentiallegati/view" />	    	
		</jsp:include>	
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimentiallegati.movimento.istanza.id.codice}</c:param>
		</c:import>
		<div class="parametriDiv">
			<div class="etichetta">
				<div><fmt:message key="label.movimento" />:</div>
			</div>		
			<div class="parametro">       		 	
				<div>
					${movimentiallegati.movimento.movimento} - [${movimentiallegati.movimento.tipomovimento.id.tipomovimento}]							
				</div>
			</div>
		</div>
		<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="movimentiallegati" name="inviodati" enctype="multipart/form-data">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="movimentiallegati" />
		    </jsp:include>
			<table>					
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="documento_id" path="descrizione" size="67" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="note" cols="67" rows="5"/>
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>				
				<tr>
					<td>
						<fmt:message key="label.oggetto" />
					</td>
					<td>
					
					<c:if test="${display=='0' && !isCaricamnetoMultiplo}">
						<jsp:include page="../includes/oggetti.jsp" >
			       				<jsp:param name="idElemento" value="oggettoIdCodice" />
			   					<jsp:param name="codiceOggetto" value="${movimentiallegati.oggetto.id.codice}" />
			   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
			   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
			   					<jsp:param name="codiceIstanza" value="${movimentiallegati.movimento.istanza.id.codice}" />
			   					<jsp:param name="param.parametroLogOperazione" value="1" />		
			   			</jsp:include>
	    				<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
	    				<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
	    				<spring-form:errors path="oggetto" cssClass="error"/>
	    			
	    				
					</c:if>
					
                    <c:if test="${display=='0' && isCaricamnetoMultiplo}">
						<jsp:include page="../includes/oggettiMultipli.jsp" />
					</c:if>

					<c:if test="${display=='1'}">
						<c:choose>
							<c:when test="${movimentiallegati.oggetto.id.codice==null && movimentiallegati.stcIdallegato!=null}">
								<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
			      						<jsp:param name="codiceistanza" value="${movimentiallegati.movimento.istanza.id.codice}" />
			  							<jsp:param name="codicemovimento" value="${movimentiallegati.movimento.id.codice}" />
			  							<jsp:param name="stcIddocumento" value="${movimentiallegati.stcIddocumento}" />
										<jsp:param name="stcIdallegato" value="${movimentiallegati.stcIdallegato}" />
										<jsp:param name="codiceRiferimento" value="${movimentiallegati.id.codice}" />
										<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_MOVIMENTO%>" />		   							
								</jsp:include>    				
							</c:when>
							<c:otherwise>
								<jsp:include page="../includes/oggetti.jsp" >
				       				<jsp:param name="idElemento" value="oggettoIdCodice" />
				   					<jsp:param name="codiceOggetto" value="${movimentiallegati.oggetto.id.codice}" />
				   					<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
				   					<jsp:param name="nomefileId" value="oggetto_nomefile" />
				   					<jsp:param name="codiceIstanza" value="${movimentiallegati.movimento.istanza.id.codice}" />
				   					<jsp:param name="stcIdallegato" value="${movimentiallegati.stcIdallegato}" />
			  						<jsp:param name="stcIddocumento" value="${movimentiallegati.stcIddocumento}" />
			  						<jsp:param name="parametroLogOperazione" value="1" />									
				   				</jsp:include>			    				
		    				</c:otherwise>
		    			</c:choose>
		    			<spring-form:hidden path="oggetto.id.codice" id="oggetto_id_codice"/>
	    				<spring-form:hidden path="oggetto.nomefile" id="oggetto_nomefile"/>
	    				<spring-form:errors path="oggetto" cssClass="error"/>
    				</c:if>    				
					</td>
				 </tr>
				 <tr>
					<td><label for="pubblica_id"><fmt:message  key="label.pubblica" /></label></td>
					<td colspan="3">
						<spring-form:checkbox id="pubblica_id" path="flagPubblica" />
						<spring-form:errors path="flagPubblica" cssClass="error"/>
					</td>
				 </tr>
				 
				 <tr>
					<td>
						<fmt:message key="label.valido" />
					</td>
					<td>
						<spring-form:select id="select_valido" path="controllook">
							<spring-form:option value="" >Da verificare</spring-form:option>									
							<spring-form:option value="1">Valido</spring-form:option>
							<spring-form:option value="0">Non valido</spring-form:option>
						</spring-form:select>	
						<spring-form:errors path="controllook" cssClass="error" />
					</td>
				</tr>
				 				
			</table>

		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${empty movimentiallegati.id.codice}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				<c:if test="${!isCaricamnetoMultiplo or  isCaricamnetoMultiplo==null}">
					<li><a title="<fmt:message key="button.documenti_multipli_insert.help" />" href="javascript:doHref('create.htm?codiceMovimento=${movimentiallegati.movimento.id.codice}&isCaricamnetoMultiplo=true','');"><fmt:message key="button.documenti_multipli_insert" /></a></li>
				</c:if>
				<c:if test="${isCaricamnetoMultiplo}">
					<li><a title="<fmt:message key="button.documenti_multipli_insert.help" />" href="javascript:doHref('create.htm?codiceMovimento=${movimentiallegati.movimento.id.codice}}&isCaricamnetoMultiplo=false','');"><fmt:message key="button.documenti_singolo_insert" /></a></li>
				</c:if>
				
			</c:if>
			<c:if test="${not empty movimentiallegati.id.codice}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<c:if test="${inite:endsWith(movimentiallegati.oggetto.nomefile, '.pdf') && isAttivaLayerProt}">
					<%-- 
					<li><a title="Applica informazioni protocollo come layer" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.applica_layer" /></a></li>
				   	--%>
				   	<li> <a href="javascript:void(0);" class="applica_layer_id" data-codice-movimento="${movimentiallegati.movimento.id.codice}"
									 data-codice-oggetto="${movimentiallegati.oggetto.id.codice}" title="Applica informazioni protocollo come layer"> <fmt:message key="button.applica_layer" /></a></li>
				</c:if>
				
				<li><a href="javascript:void(0);" class="delete_id" data-codice-allegato-movimento="${movimentiallegati.id.codice}"><fmt:message key="button.delete" /></a></li>
				
				
			</c:if>			
			<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<div id="dialog-6" style=""display:none">
         	<fmt:message key="label.messaggio_cancellazione_documento_per_operatore">
					<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
			</fmt:message>
         <div>
            <label for="terms"><fmt:message key="label.accettazione_condizioni_eliminazione"/></label>
            <input type="checkbox" id="terms">
         </div>
      </div>
	
	 <div style="display: none;" id="dialog-7">
         	<fmt:message key="label.messaggio_applica_layer_protocollo_documento">
				<fmt:param>${movimentiallegati.movimento.numeroprotocollo}</fmt:param>
				<fmt:param><fmt:formatDate pattern= "<%=WebConstants.DATE_FORMAT_PATTERN %>" value ="${movimentiallegati.movimento.dataprotocollo}" /></p>  </fmt:param>
			</fmt:message>
         <div>
            <label for="terms"><fmt:message key="label.accettazione"/></label>
            <input type="checkbox" id="terms">
         </div>
      </div>
	
	<script type='text/javascript'>
		$('documento_id').focus();		
		
		jQuery(function () {
	      	
	  		jQuery(".delete_id").eliminaConConferma({
	      		dialogSelector: '#dialog-6',
	      		dataId: 'codiceAllegatoMovimento',
	      		callback: function (id) {
	      			elimina(id);
	      		}
	      	});
	  		
	  	});
		
		jQuery(function () {
	      	
	  		jQuery(".applica_layer_id").applicaLayerConConferma({
	      		dialogSelector: '#dialog-7',
	      		dataMovId: 'codiceMovimento',
	      		dataOggId: 'codiceOggetto',
	      		callback: function (dataMovId,dataOggId) {
	      			//alert(dataOggId);
	      			//alert(dataMovId);
	      			applicaLayerProtocolloInPDF(dataMovId,dataOggId)
	      		}
	      	});
	  		
	  	});

		function applicaLayerProtocolloInPDF(codiceMovimento,codiceOggetto){
			
			doHref('applicaLayerProtocolloPdf.htm?codiceMovimento='+codiceMovimento+'&codiceOggetto='+codiceOggetto+'&codiceAllegato=${movimentiallegati.id.codice}','');
		}
		
		function elimina(codiceAllegatoMovimento){
			doHref('deleteMovAllegati.htm?codice='+codiceAllegatoMovimento+'&codiceMovimento=${movimentiallegati.movimento.id.codice}','',document.inviodati);
		}
		
	</script>	
</body>
</html>