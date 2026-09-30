<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.service.helper.FoDomRichiesteHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomRichieste"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Oggetti"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.pal.gp.areariservata.web.filter.ServiziResolverFilter.ServiziEnum"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomandeOggetti"%>
<%@page import="java.util.Set"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.FoDomande"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>

<c:set scope="page" var="tipo_comunicazione" value="${ comunicazione.tipoRichiesta }"></c:set>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %></title>
</head>
<body>

<%
	FoDomRichieste ris = (FoDomRichieste)request.getAttribute("comunicazione");
	String linkRisposta = Utilities.getLinkForFile("id=" + ris.getId().getCodice() + "&fkIdDomanda="+ ris.getFoDomande().getId().getCodice() +"&ts_" + System.currentTimeMillis());								
 %>

<style>

	.panelNotifiche{
	
		width: 80%;
		border: dotted;
		border-color: red;
		padding: 10px;
		margin: 5px;
		display: none;
	}
</style>

	<jsp:include page="../includes/messaggio_aggiornamento.jsp">
			<jsp:param name="settimeout" value="false"></jsp:param>
	</jsp:include>
	<div class="titolo"><%= FoDomRichiesteHelper.decodeTipoRichiesta((String)pageContext.getAttribute("tipo_comunicazione")) %></div>
	<div class="descrizione"></div>
	
	
	<fieldset style="padding: 2px;">
	
	<form name="invio" id="frmInvio" action="inviaComunicazione.htm" enctype="multipart/form-data" method="post">
	<input type="hidden" name="id" value="${comunicazione.id.codice}">
	<input type="hidden" name="msgHidden" id="msgHidden" value="${comunicazione.messaggio}">
	<br />
		
		<table>
			
			<tr>
				<td valign="top"><label>Indicazioni da trasmettere</label></td>
				<td>
					<textarea name="messaggio" id="tamessaggioid" cols="70" rows="10" >${comunicazione.messaggio}</textarea>
				</td>
			</tr>
			


	<c:if test="${not empty allegatis }">
			
				<tr>
					<td class="sezione_table_label" style="vertical-align: top"><label>Lista degli allegati richiesti per i diversi moduli</label></td>
				<td>				
					<table style="width: 100%">
						<tr>
							<td>Modulo</td>
							<td>Allegato</td>
							<td>Richiesto</td>
						</tr>
					<c:forEach items="${ allegatis }" var="allegato">						
						<tr>
							<td>${allegato.modulo}</td>	
							<td>
							
								<c:set var="display_file" value="inline" scope="page"/>
								<c:if test="${not empty allegato.oggetti }">
								
									<%
									FoDomrichAllegati all = (FoDomrichAllegati)pageContext.getAttribute("allegato");
									
									
									String linkDelete = Utilities.getLinkForFile("idAllegato=" + all.getId().getCodice() + "&idrichiesta="+all.getFoDomRichieste().getId().getCodice()+"&ts_="+System.currentTimeMillis());
									
									%>
									<c:if test="${not empty allegato.oggetti.id.codice }">
									<c:set var="display_file" value="none"/>
									<%
									Oggetti o = all.getOggetti();
									String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());
									
									%>
									<a href="${pageContext.request.contextPath}/ajax/download.htm?<%=link %>" title="Scarica il file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/></a>&nbsp;${allegato.oggetti.nomefile}  (  ${allegato.oggetti.dimensioneFileLeggibile } )</a>								
									</c:if>
									<div style="display: inline; width: 60%; float: right;">
										<input type="button" name="btn_show_${allegato.id.codice}" onclick="mostraModifica('file_div_${allegato.id.codice}')" value="Modifica"/>
										<c:if test="${empty allegato.allegatoRichiesto}">
											<input type="button" name="btn_del_${allegato.id.codice}" onclick="deleteFileLibero('<%= linkDelete%>')" value="Elimina"/>
										</c:if>
									</div>
								</c:if>
							
								<div style="padding-top: 4px; display: ${pageScope.display_file}; " id="file_div_${allegato.id.codice}">
								<c:if test="${allegato.firmato ne true}">
									<input class="allegati_modulo" type="file" name="allegato_modulo_${allegato.id.codice}" />
								</c:if>
								
								<c:if test="${allegato.firmato eq true}">
									<input class="allegati_modulo_firmato" type="file" name="allegato_modulo_${allegato.id.codice}" />
								</c:if>
								<c:if test="${allegato.firmato eq true}">
									<img src="${pageContext.request.contextPath}/images/certificato.gif" title="Il file deve essere firmato" alt="Il file deve essere firmato"/>
								</c:if>
								</div>
								
							</td>						
							<td>
								<c:if test="${not empty allegato.allegatoRichiesto }">
									<c:if test="${not empty allegato.allegatoRichiesto.oggetti.id.codice }">
									<%
									FoDomrichAllegati all = (FoDomrichAllegati)pageContext.getAttribute("allegato");
									
									Oggetti o = all.getAllegatoRichiesto().getOggetti();
									String link = Utilities.getLinkForFile("id=" + o.getId().getCodice() + "&idComuneOggetto=" + o.getId().getIdcomune() + "&ts_" + System.currentTimeMillis());								
									%>
									<a href="${pageContext.request.contextPath}/ajax/download.htm?<%=link %>" title="Scarica il file"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"/></a>&nbsp;${allegato.allegatoRichiesto.oggetti.nomefile}  (  ${allegato.allegatoRichiesto.oggetti.dimensioneFileLeggibile } )</a>								
									</c:if>
									<c:if test="${not empty allegato.link }">
										<a href="${allegato.allegatoRichiesto.link}" target="_blank">${allegato.allegatoRichiesto.link}</a>
									</c:if>
								</c:if>
							</td>	

						</tr>					
					</c:forEach>
					</table>
				</td>
				</tr>			

	</c:if>

	</table>			
			
	</form>	
		
	<div style="padding: 10px;">
	<input type="button" id="allegato_libero_btn" onclick="aggiungiAllegato()" value="Aggiungi allegato"/>	
		<div id="panelAllegatoLibero" style="display: none;">
		<form name="invio" id="frmInviolibero" action="inviaComunicazione.htm" enctype="multipart/form-data" method="post">
			<input type="hidden" name="id" value="${comunicazione.id.codice}">
			<table>
				<tr>
						<td>
							<select name="allegato_modulo_destinatario">
								<c:forEach items="${modulis}" var="modu">
									<option value="${modu.codice}###${modu.descrizione}">${modu.codice}</option>
								</c:forEach>
							</select>	
						</td>	
						<td>
							<input class="allegati_modulo_firmato" type="file" name="allegato_modulo_libero" />
							<img src="${pageContext.request.contextPath}/images/certificato.gif" title="Il file deve essere firmato" alt="Il file deve essere firmato"/>
						</td>
				</tr>			
			</table>				
			
			<br />
			<input type="button" id="salva_allegato_libero_btn" class="bottone-cart" onclick="allegatoLibero()" value="Salva"/>
		</form>	
		</div>
	
	</div>							

	<br />
	
	
	
	<script type="text/javascript">
	
	
	function aggiungiAllegato(){
		
		jQuery('#panelAllegatoLibero').dialog({modal:true,width: 800,height: 250}).dialog('open');
		
	}	
	
	function deleteFileLibero(linkAllegato){
		if(confirm('Procedere con l\'eliminazione dell\'allegato?')){
			$('#frmInvio').attr('action', '${pageContext.request.contextPath}/istanze/deleteAllegatoLibero.htm?'+linkAllegato).submit();
		}
	}
	
	
	
	$.validator.addMethod("filefirmato", function(value, element) {
		var result = $(element).val().toLowerCase().indexOf(".p7m", this.length - 4) !== -1;
        return result;        
	}, "Il file deve essere firmato (con estensione .p7m)");
	
	$.validator.addMethod("estensioniammesse", function(value, element) {
		var fileName = $(element).val().toLowerCase();
		
		// pdf, pdf.p7m, xml, dwf, dwf.p7m, svg, svg.p7m, jpg,  jpg.p7m
		
        return (fileName.indexOf(".pdf.p7m", this.length - 8) !== -1 || 
        		fileName.indexOf(".pdf", this.length - 4) !== -1 ||
        		fileName.indexOf(".xml", this.length - 4) !== -1 ||
        		fileName.indexOf(".svg.p7m", this.length - 8) !== -1 ||
        		fileName.indexOf(".svg", this.length - 4) !== -1 ||        		
        		fileName.indexOf(".dwf.p7m", this.length - 8) !== -1 ||
        		fileName.indexOf(".dwf", this.length - 4) !== -1 ||   
        		fileName.indexOf(".jpg.p7m", this.length - 8) !== -1 ||
        		fileName.indexOf(".jpg", this.length - 4) !== -1 
        		);
        
	}, "Possono essere inviati solamente file con le seguenti estensioni ( pdf, pdf.p7m, xml, dwf, dwf.p7m, svg, svg.p7m, jpg,  jpg.p7m )");
	
	
	 $.validator.addClassRules({
		 
		 	allegati_modulo:{
	        required: true,
	        estensioniammesse: true
	    }
	 });
	 
	 $.validator.addClassRules({
		 
		 	allegati_modulo_firmato:{
	        required: true,
	        filefirmato: true,
	        estensioniammesse: true
	    }
	 });
	
	
	 $('#frmInviolibero').validate({
		 	lang: 'it',
	        rules: {
	        	messaggio: {
	                required: true,
	                minlength: 5
	            }
	        }
	    });
	 
	 $('#frmInvio').validate({
		 	lang: 'it',
	        rules: {
	        	messaggio: {
	                required: true,
	                minlength: 5
	            }
	        }
	    });
	 
	
		function salva(){
			if(confirm('Salvare le informazioni?')){
				$('#frmInvio').attr('action', "${pageContext.request.contextPath}/istanze/salvaComunicazione.htm").submit();
			}			
		}
		
		function invia(){
			if(confirm('Procedere con l\'invio?')){
				if($('#frmInvio').valid()){
					document.location.href= "${pageContext.request.contextPath}/istanze/inviaComunicazione.htm?<%=linkRisposta%>";
				}
			}			
		}
		
		function allegatoLibero(){
			if(confirm('Procedere con l\'invio?')){		
				if($('#frmInviolibero').valid()){
					$('#frmInviolibero').attr('action', "${pageContext.request.contextPath}/istanze/salvaAllegatoLibero.htm?msgHidden="+$('#tamessaggioid').val()).submit();
				}
			}			
		}
		
		function mostraModifica(divId){
			
			$('#'+divId).toggle();
			<c:if test="${valid eq true }">
			$('#invia_btn').toggle();
			</c:if>
			$('#allegato_libero_btn').toggle();
		}
		
		
	</script>
	
	
	<c:if test="${comunicazione.flagCompletata ne true }">
		<input type="button" id="salva" class="bottone-cart" onclick="salva()" value="Salva"/>
		
		<c:if test="${valid eq true }">
			<input type="button" id="invia_btn" class="bottone-cart" onclick="invia()" value="Invia"/>
		</c:if>
		
		
		
	</c:if>	
	<input type="button" id="chiudi" class="bottone-cart" onclick="history.back()" value="Chiudi"/>
	
	
	
	
	
</body>
</html>