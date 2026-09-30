<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>
<%@ page import="it.gruppoinit.sigepro.cart.service.CartRfcBaseService" %>
<?xml version="1.0" encoding="UTF-8" ?>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="stp.label.richieste_invioscheda"/>: ${descrizioneEndo}</title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="stp.label.richieste_invioscheda"/>: ${descrizioneEndo}</span>
<div id="subcontent">
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<br class="clear" />
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="cart" />
	</jsp:include>
<div class="jmesa">
<table border="0" cellpadding="2" cellspacing="0" class="table" width="100%">
	<tbody class="tbody">
		<tr class="titoloSezione">
			<td colspan="2"><fmt:message key="stp.label.richiestesuap" /></td>		
		</tr>
		<tr>
			<td width="15%">
				<input type="hidden" id="codice_id" name="codice" value="${param.codice}"/>
				<select id="id_richiesta">
					<option value="<%= CartRfcBaseService.TipoRichiesta.Disponibilità%>"><fmt:message key="stp.label.disponibilita_scheda"/></option>
					<option value="<%= CartRfcBaseService.TipoRichiesta.Invio%>"><fmt:message key="stp.label.invio_scheda"/></option>
				</select></td>		
			<td>
				<span id="functions">
					<ul>				
						<li><a href="javascript:invioScheda();" id="richiestaSchedaId"><fmt:message key="stp.label.richieste_invioscheda"/></a></li>
					</ul>
				</span>
			<init:help idHelp="helpscheda"	textKey="stp.help.invioscheda" /></td>
		</tr>						
	</tbody>
</table>
</div>
<jsp:include page="../cart/funzioni_cart.jsp" >
	<jsp:param name="funzioneRichiesta" value="javascriptBlock" />
</jsp:include>
<script type="text/javascript">

var dialogWorking = null; 
jQuery(document).ready(function(){
	dialogWorking = new dijit.Dialog({
       title: "Operazione in corso..." ,
       style: "overflow:auto; width: 250px;",
       content: "<img src='../images/spinner.gif'/>"
    });				
}); 
var invioScheda = function(){
	var qs = "tiporichiesta="+encodeURI($('id_richiesta').value)+"&codice="+$('codice_id').value;
	dialogWorking.show();
	var jqxhr = jQuery.ajax({
		  url: "ajaxRichiestaSchedaEndo${param.tipo}.htm",
		  context: document.body,
		  cache: false,				
		  dataType: "html",
		  data: qs,
		  success: function(data) {
			  dialogWorking.hide();
			  dijit.showTooltip(data, dojo.byId('richiestaSchedaId'));
			  setTimeout(function(){dijit.hideTooltip(dojo.byId('richiestaSchedaId'))},1500);
			} 
		})
	};

</script>
<div id="functions">
	<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</div>
</body>
</html>