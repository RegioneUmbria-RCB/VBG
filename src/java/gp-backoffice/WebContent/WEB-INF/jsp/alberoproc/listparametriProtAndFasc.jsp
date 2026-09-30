<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_parametri_protocollo" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.lista_parametri_protocollo" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../alberoproc/listparametriProtAndFasc" />
	</jsp:include>
	<div id="subcontent">
	 	<div class="parametriDiv">
	    	<div class="etichetta">
				<div><fmt:message key="label.procedimento" />:</div>
			</div>
			<div class="parametro">
				<div><c:out value="${alberoproc.vwAlberoproc.scDescrizione}" /></div>
			</div>
	    </div>
	    <div class="clear"></div>	
	
	<c:forEach items="${alberoprocProtAndFascHelpers}" var="alberoprocProtAndFascHelper">
	
	<c:if test="${not empty alberoprocProtAndFascHelper.alberoprocProtocollos}">
	<fieldset><legend>
	<a
			class="sezioneDatiMeno"
			id="id_link_parametri_prot${alberoprocProtAndFascHelper.comune}"
			href="javascript:showHidePanelBase('id_parametri_prot_table${alberoprocProtAndFascHelper.comune}', 'id_link_parametri_prot${amministrProtocolloHelper.comune}', ' ', '${pageContext.request.contextPath}/images/','div',false);"
			title="<fmt:message key="label.mostra_nasconde_sezione" /> ${alberoprocProtAndFascHelper.comune}">
			<label for="id_link_parametri_prot${alberoprocProtAndFascHelper.comune}">${alberoprocProtAndFascHelper.comune}</label> 
	</a>
	</legend>
    <div class="jmesa" id="id_parametri_prot_table${alberoprocProtAndFascHelper.comune}">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td>&nbsp;</td>
				<td colspan="4" style="text-align: center;"><fmt:message key="alberoproc.label.parametri_protocollazione.title"/></td>
				<td colspan="4" style="text-align: center;"><fmt:message key="alberoproc.label.parametri_fascicolazione.title" /></td>
			</tr>
			<tr class="header">
			    <td width="20%"><fmt:message key="label.amministrazione" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scFascclassifica" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scProttipodocumento" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scProtcodtesto" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scProtautomatica" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scFascclassifica" /></td>
				<td width="20%"><fmt:message key="alberoproc.label.scFasccodtesto" /></td>
				<td width="40%"><fmt:message key="alberoproc.label.scFascautomatica" /></td>
				<td width="10%"><fmt:message key="label.azioni" /></td>
            </tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
		
		<c:forEach items="${alberoprocProtAndFascHelper.alberoprocProtocollos}" var="alberoprocProtocollo">
			
			<tr class="<%=(j%2)==0?"odd":"even"%>">
			    <td>${alberoprocProtocollo.amministrazioni.amministrazione}</td>
			    <td>${alberoprocProtocollo.scProtclassifica}</td>
				<td>${alberoprocProtocollo.scProttipodocumento}</td>
				<td>${alberoprocProtocollo.testoProtocollo.descrizione}</td>
				<td>
				<% 
				AlberoprocProtocollo p = (AlberoprocProtocollo)pageContext.getAttribute("alberoprocProtocollo");
				String dec = AlberoprocController.decodificaTipo(p.getScProtautomatica(), true);
				out.print(dec);
				%>	
				
				</td>
				<td>${alberoprocProtocollo.scFascclassifica}</td>
				<td>${alberoprocProtocollo.testoFascicolo.descrizione}</td>
				<td>
					<% 
				
				dec = AlberoprocController.decodificaTipo(p.getScFascautomatica(), false);
				out.print(dec);
				%>	
				</td>
				<td>					
					<a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../alberoproc/viewParametriProtAndFasc.htm?codice=${alberoprocProtocollo.id.codice}','')" title="<fmt:message key="label.azioni" /> ">
							<label><fmt:message key="label.azioni" /></label>
					</a> 					
				</td>
			</tr>
			
			<%j++; %>
		</c:forEach>
			
		</tbody>
	</table>
</div>
</fieldset>
</c:if>	
<br />
</c:forEach>

	
	</div>
	<c:if test="${isComuniAssociati eq true}">
	
		<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm" title="<fmt:message key="label.messaggio_conferma"/>">
		 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height: 100px">
			Per proseguire è necessario scegliere un comune 
			<select name="codiceComune" id="codiceComune_id">
				<option value=""><fmt:message key="label.tutti" /></option>
				<c:forEach items="${responsabilicomunis}" var="rc">
				<option value="${rc.comune.codicecomune}">${rc.comune.comune}</option>
				</c:forEach>
			</select>
			
			<div id="functions">
			<ul>
				<li><a href="javascript:nuovoParametro();"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:hideNuovoParametro();"><fmt:message key="button.annulla" /></a></li>						
			</ul>
			</div>
		
		</div>
		</div>
	
	<script type="text/javascript">
	
		// 
		function showNuovoParametro(){
			dijit.byId('message_dialog_confirm').show();
			
		}
		function hideNuovoParametro(){
			dijit.byId('message_dialog_confirm').hide();
			
		}
		function nuovoParametro(){
			var codiceComune = jQuery('#codiceComune_id').val();	
			historySet('${_urlback}','../alberoproc/createparametriProtAndFasc.htm?codice=${alberoproc.id.codice}&codiceComune='+codiceComune,'');
		}
	</script>
	
	</c:if>
	<c:if test="${isComuniAssociati eq false}">
		<script type="text/javascript"> 
		function showNuovoParametro(){
			historySet('${_urlback}','../alberoproc/createparametriProtAndFasc.htm?codice=${alberoproc.id.codice}&codiceComune=','');			
		}		
		</script>
	</c:if>
	<script type="text/javascript"> 
		function showRiepilogo(){
			
			var jhqrPr = jQuery.ajax({
				  url: 'ajaxRiepilogoConfigurazioniProt.htm',
				  context: document.body,
				  cache: false,	
				  data: "codicealberoproc="+${alberoproc.id.codice},
				  dataType: "html",
				  success: function(data, textStatus, jqXHR){
					  if(data){
						  	jQuery('#riepilogoContentDivId').html(data);
						  	dijit.byId('riepilogo').show();
					  }
				  }
			});
			
		}		
	</script>
	<div id="functions">
		<ul>
			<li><a href="javascript:showNuovoParametro();"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:showRiepilogo();"><fmt:message key="label.riepilogo" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<div style="display: none;" align="center" dojoType="dijit.Dialog" id="riepilogo" title="<fmt:message key="label.riepilogo"/>">
		 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog">
		 		<div id="riepilogoContentDivId" style="width: 80%"></div>							
			</div>
		</div>
	
</body>
</html>