<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.service.CartProxyService"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="stp.label.pannellocontrollo.title" />
	</title>
</head>
<body>
		<jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../stp/pannellocontrollo" />
    	</jsp:include>	

<span class="titoloPagina">
	<fmt:message key="stp.label.pannellocontrollo.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<%
String controlloStyle = "";
String richiesteStyle = "display: none";
String controlloSchedaClass = "SchedaAttiva";
String richiesteSchedaClass = "Scheda";
String spinner="display: none";
String link="";
%>
<div id="subcontent">
<c:if test="${status_msg eq '03'}">
            <div id="status_msg" class="error_header" >
            	<fmt:message key="${status_msg}"/>
            </div>
             <script type="text/javascript" >
		    	$('status_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>
            </c:if>
            <c:if test="${status_msg eq '02'}">
            <div id="status_msg" class="success_header" >
            	<fmt:message key="${status_msg}"/>
            </div>
             <script type="text/javascript" >
		    	$('status_msg').pulsate({ pulses: 2, duration: 1.0 });
		    </script>
    		</c:if>
 	<fieldset>
	
	<legend><fmt:message key="stp.label.pannellocontrollo.title" /></legend>
    <ul class="listaSchede">
    	<c:if test="${view == 'false' || tipo eq 'CONTROLLO'}">
		<li><a id="controlloScheda" class="<%=controlloSchedaClass %>" href="javascript:changeTab('controlloScheda')"><fmt:message key="stp.label.controllomessaggiregione" /></a></li>	
		</c:if>
		<c:if test="${view == 'true' }">
			<c:if test="${tipo ne 'CONTROLLO' }">
			<% 
			   controlloStyle = "display: none";
			   richiesteStyle = "";
			   controlloSchedaClass = "Scheda";
			   richiesteSchedaClass = "SchedaAttiva";
			%>
			</c:if>
		<li><a id="richiesteScheda" class="<%=richiesteSchedaClass %>" href="javascript:changeTab('richiesteScheda')"><fmt:message key="stp.label.richiestesuap" /></a></li>
	    </c:if>
	</ul>
	<div id="controllo_div" style="<%= controlloStyle %>">
	<%
	String display="display:none;";
	String elabora="display:none;";
	String cancella="display:none;"; 
	String aggiorna="display:none;";
	%>
	<br />
	<div id="nomessaggi_id" class="error_header" style="<%=display %>"><fmt:message key="stp.error.nomessaggi" /></div>
	<div id="ko_msg" class="error_header" style="<%=display %>"><fmt:message key="03"/></div>
	<div id="ok_msg" class="success_header" style="<%=display %>"><fmt:message key="stp.label.elaborazioneavvenuta" /></div>
	<div id="ko_msg_del" class="error_header" style="<%=display %>"><fmt:message key="03"/></div>
	<div id="ok_msg_del" class="success_header" style="<%=display %>"><fmt:message key="stp.label.cancellazioneavvenuta" /></div>
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
	<tbody class="tbody">
		<tr class="even">
			<td width="25%"><fmt:message key="stp.label.disponibilitadizionario" /></td>
			<td><select id="select_1"></select> 
			<div id="functions" >
			<ul>
				<li id="spin1" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
			    <li id="link1" style="<%=link %>">
			   		<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaDizionario %>','select_1','1');"><fmt:message key="stp.label.controllomessaggi" /></a>
				</li>
				<li id="elabora1" style="<%=elabora %>">
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaDizionario %>','select_1','1');"><fmt:message key="stp.label.elabora" /></a>
				</li>
				<li id="cancella1" style="<%=cancella %>">
					<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaDizionario %>','select_1','1');"><fmt:message key="stp.label.cancella" /></a>
				</li>
			</ul>
			</div>
			</td>
		</tr>
		<tr  class="odd">
			<td width="25%"><fmt:message key="stp.label.inviodizionario" /></td>
			<td><select id="select_2"></select>
			<div id="functions" >
			<ul>
				<li id="spin2" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
			    <li id="link2" style="<%=link %>"> 
			   		<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioDizionario %>','select_2','2');"><fmt:message key="stp.label.controllomessaggi" /></a>
				</li>
				<li id="elabora2" style="<%=elabora %>">
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.InvioDizionario %>','select_2','2');"><fmt:message key="stp.label.elabora" /></a>
				</li>
				<li id="cancella2" style="<%=cancella %>">
					<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.InvioDizionario %>','select_2','2');"><fmt:message key="stp.label.cancella" /></a>
				</li>
				<li id="aggiorna2" style="<%=aggiorna %>">
					<a href="#" onclick="aggiornaDizionario('<%=CartProxyService.MessaggiAzioni.InvioDizionario %>','select_2','2');"><fmt:message key="stp.label.aggiorna_dizionario" /></a>				
				</li>
			</ul>
			</div>
			</td>
		</tr>
		<tr  class="even">
			<td width="25%"><fmt:message key="stp.label.disponibilitascheda1" /></td>
			<td><select id="select_3"></select>
			<div id="functions" >
			<ul>
				<li id="spin3" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
			    <li id="link3" style="<%=link %>">
					<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC %>','select_3','3');"><fmt:message key="stp.label.controllomessaggi" /></a>
				</li>
				<li id="elabora3" style="<%=elabora %>">
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC %>','select_3','3');"><fmt:message key="stp.label.elabora" /></a>
				</li>
				<li id="elaboratutti3" style="<%=elabora %>">
					<a href="#" onclick="elaboraTuttiMessaggiTipo('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC %>','2');"><fmt:message key="stp.label.elaboratutti" /></a>
				</li>
				<li id="cancella3" style="<%=cancella %>">
					<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC %>','select_3','3');"><fmt:message key="stp.label.cancella" /></a>
				</li>
			</ul>
			</div>
			</td>
		</tr>
		<tr   class="odd">
			<td width="25%"><fmt:message key="stp.label.invioscheda1" /></td>
			<td><select id="select_4"></select>
			<div id="functions" >
			<ul>
				<li id="spin4" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
			    <li id="link4" style="<%=link %>">
					<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioSchedaEC %>','select_4','4');"><fmt:message key="stp.label.controllomessaggi" /></a>
				</li>
				<li id="elabora4" style="<%=elabora %>">
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.InvioSchedaEC %>','select_4','4');"><fmt:message key="stp.label.elabora" /></a>
				</li>
				<li id="elaboratutti4" style="<%=elabora %>" >
				<a href="#" onclick="elaboraTuttiMessaggiTipo('<%=CartProxyService.MessaggiAzioni.InvioSchedaEC %>','4');"><fmt:message key="stp.label.elaboratutti" /></a>
				</li>
				<li id="cancella4" style="<%=cancella %>">
					<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.InvioSchedaEC %>','select_4','4');"><fmt:message key="stp.label.cancella" /></a>
				</li>
			</ul>
			</div>
			</td>
		</tr>		
		<tr  class="even">
			<td width="25%"><fmt:message key="stp.label.disponibilitascheda2" /></td>
			<td><select id="select_5"></select>
			<div id="functions" >
			<ul>
				<li id="spin5" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
			    <li id="link5" style="<%=link %>">
					<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP %>','select_5','5');"><fmt:message key="stp.label.controllomessaggi" /></a>
				</li>
				<li id="elabora5" style="<%=elabora %>" >
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP %>','select_5','5');"><fmt:message key="stp.label.elabora" /></a>
				</li>
				<li id="elaboratutti5" style="<%=elabora %>" >
					<a href="#" onclick="elaboraTuttiMessaggiTipo('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP %>','5');"><fmt:message key="stp.label.elaboratutti" /></a>
				</li>
				<li id="cancella5" style="<%=cancella %>">
					<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP %>','select_5','5');"><fmt:message key="stp.label.cancella" /></a>
				</li>
			</ul>
			</div>
			</td>
		</tr>
		<tr class="odd">
			<td width="25%"><fmt:message key="stp.label.invioscheda2" /></td>
			<td><select id="select_6"></select>
			<div id="functions">
				<ul>
				  <li id="spin6" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
				  <li id="link6" style="<%=link %>">
					<a href="#" onclick="controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioSchedaEP %>','select_6','6');"><fmt:message key="stp.label.controllomessaggi" /></a>
				  </li>
				  <li id="elabora6" style="<%=elabora %>" >	
					<a href="#" onclick="elaboraMessaggio('<%=CartProxyService.MessaggiAzioni.InvioSchedaEP %>','select_6','6');"><fmt:message key="stp.label.elabora" /></a>
				  </li>
				  <li id="elaboratutti6" style="<%=elabora %>" > 	
				 	<a href="#" onclick="elaboraTuttiMessaggiTipo('<%=CartProxyService.MessaggiAzioni.InvioSchedaEP %>','6');"><fmt:message key="stp.label.elaboratutti" /></a>					
				 </li>
				 <li id="cancella6" style="<%=cancella %>" >
				 		<a href="#" onclick="cancellaMessaggio('<%=CartProxyService.MessaggiAzioni.InvioSchedaEP %>','select_6','6');"><fmt:message key="stp.label.cancella" /></a>
				  </li>
				</ul>
			</div>
			</td>
		</tr>
		</tbody>
	</table>
	<div id="functions">
				<ul>
				  <li id="spin7" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
				  <li id="link7" style="<%=link %>">
					<a href="#" onclick="controllaTuttiMessaggi('link7','spin7');"><fmt:message key="stp.label.controllotuttimessaggi" /></a>
				  	<a href="#" onclick="cancellaTuttiMessaggi('link7','spin7');"><fmt:message key="stp.label.cancellatutti" /></a>
				  	<a href="#" onclick="elaboraTuttiMessaggi('link7','spin7');"><fmt:message key="stp.label.elaboratutti" /></a>
				  	<a href="#" onclick="verificaSchedeEndo('link7','spin7');"><fmt:message key="stp.button.verifica_schede_endo" /></a>
				  </li>
				</ul>
			</div>
	</div>
	<script type="text/javascript">
		function removeOptionSelected(opt)
		{
		  var elSel = document.getElementById(opt);
		  var i;
		  for (i = elSel.length - 1; i>=0; i--) {
		    if (elSel.options[i]) {
		       elSel.remove(i);
		    }
		  }
		}
		
		function controllaTuttiMessaggi(linkid,spinid){
			$(linkid).style.display="none";
			$(spinid).style.display="";
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaDizionario %>','select_1','1');
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioDizionario %>','select_2','2');
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEC %>','select_3','3');
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioSchedaEC %>','select_4','4');
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.DisponibilitaSchedaEP %>','select_5','5');
			controllaMessaggi('<%=CartProxyService.MessaggiAzioni.InvioSchedaEP %>','select_6','6');
			$(linkid).style.display="";
			$(spinid).style.display="none";
			}
		
		function cancellaTuttiMessaggi(linkid,spinid){
			$(linkid).style.display="none";
			$(spinid).style.display="";
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxCancellaTuttiMessaggi.htm', {
				  method: 'post',
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("KO")==0) {
						  $('ko_msg').appear();
					  }else{
						  $('ko_msg').fade();
			  		  }
					  $(linkid).style.display="";
					  $(spinid).style.display="none";
					  controllaTuttiMessaggi(linkid,spinid);
				},
				  onFailure: function(){
					$(linkid).style.display="";
					$(spinid).style.display="none";  }
				  });
			}


		function elaboraTuttiMessaggi(linkid,spinid){
			$(linkid).style.display="none";
			$(spinid).style.display="";
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxElaboraTuttiMessaggi.htm', {
				  method: 'post',
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("KO")==0) {
						  $('ko_msg').appear();
					  }else{
						  $('ko_msg').fade();
			  		  }
					  $(linkid).style.display="";
					  $(spinid).style.display="none";
					  controllaTuttiMessaggi(linkid,spinid);
				},
				  onFailure: function(){
					$(linkid).style.display="";
					$(spinid).style.display="none";  }
				  });
			}


		function elaboraTuttiMessaggiTipo(tipoMessaggio,n){

		var linkid='link'+n;
		var spinid='spin'+n;			
		
		$(linkid).style.display="none";
		$(spinid).style.display="";
		new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxElaboraTuttiMessaggiTipo.htm', {
			  method: 'post',
			  parameters: {tipoMessaggio: tipoMessaggio, limit: 12},
			  onSuccess: function(transport){
				  var response = transport.responseText;
				  if (response.indexOf("KO")==0) {
					  $('ko_msg').appear();
				  }else{
					  $('ko_msg').fade();
		  		  }
				  $(linkid).style.display="";
				  $(spinid).style.display="none";
				  controllaTuttiMessaggi(linkid,spinid);
			},
			  onFailure: function(){
				$(linkid).style.display="";
				$(spinid).style.display="none";  }
			  });
		}
		

		function verificaSchedeEndo(){
			historySet('${_urlback}','../stp/verificaSchedeEndo.htm','');
		}
		
		function controllaMessaggi(azione,id,n,isDelete){
			var linkid='link'+n;
			var spinid='spin'+n;
			var elaboraid='elabora'+n;
			var cancellaid='cancella'+n;
			var aggiornaid='aggiorna'+n;
			var elaboratuttiid='elaboratutti'+n;
			$(linkid).style.display="none";
			$(spinid).style.display="";
			if(!isDelete){
				$('ko_msg_del').fade();
				$('ok_msg_del').fade();
				}
			//$('nomessaggi_id').fade();
			removeOptionSelected(id);
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxControlloMessaggi.htm', {
				  method: 'post',
				  parameters: {textToSearch: azione, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("ERRORE")==0) {
						$('ko_msg').appear();
						$(elaboraid).fade();
						if($(elaboratuttiid)){
							$(elaboratuttiid).fade();
						}
						$(cancellaid).fade();
						if($(aggiornaid)){
							$(aggiornaid).fade();
						}
					  }
					  if (response.indexOf("KO")==0) {
						$('nomessaggi_id').appear();
						var descrArray=response.split("-");
						var descr=descrArray[1];
						$('nomessaggi_id').innerHTML=""+descr;
						$(elaboraid).fade();
						if($(elaboratuttiid)){
							$(elaboratuttiid).fade();
						}
						$(cancellaid).fade();
						if($(aggiornaid)){
							$(aggiornaid).fade();
						}						
					  }
						if(response.indexOf("KO")!=0 && response.indexOf("ERRORE")!=0){
						  $('nomessaggi_id').fade();
						  var opts=response.split("#");
						  for(var i=0;i<((opts.length)); i++ ){
							  try{
							  	$(id).add(new Option( opts[i], opts[i]),  $(id).options[i]);
							  }
							  catch(e){ //in IE, try the below version instead of add()
								  $(id).add(new Option( opts[i], opts[i]));
							  }
						  }
						  $(elaboraid).appear();
						  if($(elaboratuttiid)){
						  	$(elaboratuttiid).appear();
						  }
						  $(cancellaid).appear();
							if($(aggiornaid)){
								$(aggiornaid).appear();
							}
					  }
					  $(linkid).style.display="";
					  $(spinid).style.display="none";
				},
				  onFailure: function(){
					$(linkid).style.display="";
					  $(spinid).style.display="none";  }
				  });
			}
		
		function elaboraMessaggio(azione,id,n){
			var linkid='link'+n;
			var spinid='spin'+n;
			$(linkid).style.display="none";
			$(spinid).style.display="";
			$('ko_msg').fade();
			$('ok_msg').fade();
			$('ko_msg_del').fade();
			$('ok_msg_del').fade();
			var x = $(id).selectedIndex;
			if(x>0){
			var idmessaggio=$(id).options[x].value;
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxElaboraMessaggio.htm', {
				  method: 'post',
				  parameters: {code: idmessaggio, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("KO")==0) {
						  $('nomessaggi_id').appear();
						  var descrArray=response.split("-");
						  var descr=descrArray[1];
						  $('nomessaggi_id').innerHTML=""+descr;
						  $('ko_msg').appear();
						  $('ok_msg').fade();
					  }
					  if (response.indexOf("OK")==0) {
						  $('ok_msg').appear();
						  $('ko_msg').fade();
					  }
					  controllaMessaggi(azione,id,n);
					
				    },
				  onFailure: function(){
				    	$(linkid).style.display="";
						  $(spinid).style.display="none";  
						  }
				  });
			}else{
				$('nomessaggi_id').appear();
				$(linkid).style.display="";
				  $(spinid).style.display="none"; 
			}
		}
		function cancellaMessaggio(azione,id,n){
			var linkid='link'+n;
			var spinid='spin'+n;
			$(linkid).style.display="none";
			$(spinid).style.display="";
			$('ko_msg').fade();
			$('ok_msg').fade();
			
			var x = $(id).selectedIndex;
			if(x>0){
				var idmessaggio=$(id).options[x].value;
				new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxCancellaMessaggio.htm', {
					  method: 'post',
					  parameters: {code: idmessaggio, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  if (response.indexOf("KO")==0) {
							  $('ko_msg_del').appear();
							  $('ok_msg_del').fade();
						  }
						  if (response.indexOf("OK")==0) {
							  $('ok_msg_del').appear();
							  $('ko_msg_del').fade();
						  }
							controllaMessaggi(azione,id,n,true);
						   },
					  onFailure: function(){ 
					    	$(linkid).style.display="";
							  $(spinid).style.display="none"; }
					  });
			}else{
				$('nomessaggi_id').appear();
				$(linkid).style.display="";
				  $(spinid).style.display="none"; 
			}
		}


		function aggiornaDizionario(azione,id,n){
			var linkid='link'+n;
			var spinid='spin'+n;
			$(linkid).style.display="none";
			$(spinid).style.display="";
			$('ko_msg').fade();
			$('ok_msg').fade();
			
			var x = $(id).selectedIndex;
			if(x>0){
				if(confirm('<fmt:message key="stp.message.confirm.aggiorna_dizionario" />')){
				var idmessaggio=$(id).options[x].value;
				new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxAggiornaDizionario.htm', {
					  method: 'post',
					  parameters: {code: idmessaggio, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  if (response.indexOf("KO")==0) {
							  $('ko_msg').appear();
							  $('ok_msg').fade();
						  }
						  if (response.indexOf("OK")==0) {
							  $('ok_msg').appear();
							  $('ko_msg').fade();
						  }
							controllaMessaggi(azione,id,n,true);
						   },
					  onFailure: function(){ 
					    	$(linkid).style.display="";
							  $(spinid).style.display="none"; }
					  });
				}else{
					$('nomessaggi_id').appear();
					$(linkid).style.display="";
					  $(spinid).style.display="none"; 
				}				  
			}else{
				$('nomessaggi_id').appear();
				$(linkid).style.display="";
				  $(spinid).style.display="none"; 
			}
		}

		
	</script>
	</div>
    <div id="richieste_div" style="<%= richiesteStyle %>">
    <br />
    <div id="ko_msg_richieste" class="error_header" style="<%=display %>"><fmt:message key="stp.label.noinvioavvenuto"/></div>
	<div id="ok_msg_richieste" class="success_header" style="<%=display %>"><fmt:message key="stp.label.invioavvenuto"/></div>
    <div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<tbody class="tbody">
				<c:if test="${tipo eq 'CONTROLLO'}">
				<tr>
				<td width="20%"><fmt:message key="stp.label.richieste_inviodizionario"/></td>
				<td></td>
				<td width="150px">
					<div id="functions">
					<ul>
					  <li id="spininvio" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
					  <li id="linkinvio" style="<%=link %>">
						<a href="#" onclick="invioDizionario();"><fmt:message key="stp.label.richieste_inviodizionario"/></a>
					  </li>
					</ul>
					</div>
				</td>
				<td valign="middle"><init:help idHelp="helpdizionario" textKey="stp.help.inviodizionario"/></td>
				</tr>
				</c:if>
				<c:if test="${tipo ne 'CONTROLLO'}">
				<tr>
				<td width="10%"><fmt:message key="stp.label.richieste_invioscheda"/></td>
				<td style="width: 150px;">
				<select id="id_richiesta">
					<option value="<%=CartProxyService.TipoRichiesta.DISPONIBILITA%>"><fmt:message key="stp.label.disponibilita_scheda"/></option>
					<option value="<%=CartProxyService.TipoRichiesta.INVIO%>"><fmt:message key="stp.label.invio_scheda"/></option>
				</select>
				</td>
				<td width="150px">
					<div id="functions" >
					<ul>
					  <li id="spin" style="<%=spinner %>"><img src='../images/spinner.gif'/></li>
					  <li id="link" style="<%=link %>">
						<a href="#" onclick="invioScheda();"><fmt:message key="stp.label.richieste_invioscheda"/></a>
					  </li>
					</ul>
					</div>
				</td>
				<td  valign="middle"><span><init:help idHelp="helpscheda" textKey="stp.help.invioscheda"/></span></td>
				</tr>
				</c:if>
			</tbody>
		</table>
	<script type="text/javascript">
	function invioDizionario(){
		$('linkinvio').style.display="none";
		$('spininvio').style.display="";
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxInvioDizionario.htm', {
				  method: 'post',
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("KO")==0) {
						  $('ko_msg_richieste').appear();
						  $('ok_msg_richieste').fade();
					  }
					  if (response.indexOf("OK")==0) {
						  $('ok_msg_richieste').appear();
						  $('ko_msg_richieste').fade();
					  }
					  $('linkinvio').style.display="";
					  $('spininvio').style.display="none";
				    },
				  onFailure: function(){
				    	$('linkinvio').style.display="";
				    	$('spininvio').style.display="none";
				  }
				  });
	}
	function invioScheda(){
			$('link').style.display="none";
			$('spin').style.display="";
			var x = $('id_richiesta').selectedIndex;
			var value=$('id_richiesta').options[x].value;
			new Ajax.Request('${pageContext.request.contextPath}/stp/ajaxInvioScheda.htm?tipo=${tipo}&codice=${codice}', {
				  method: 'post',
				  parameters: {tiporichiesta: value, limit: 12},
				  onSuccess: function(transport){
					  var response = transport.responseText;
					  if (response.indexOf("KO")==0) {
						  $('ko_msg_richieste').appear();
						  $('ok_msg_richieste').fade();
					  }
					  if (response.indexOf("OK")==0) {
						  $('ok_msg_richieste').appear();
						  $('ko_msg_richieste').fade();
					  }
					  $('link').style.display="";
					  $('spin').style.display="none";
				    },
				  onFailure: function(){ 
					$('link').style.display="";
					$('spin').style.display="none"; }
				  });
			
	}
	</script>
	</div>
    </div>
	<script type="text/javascript">
		function changeTab(tabId){
			if(tabId == 'parametriScheda'){
				$('controlloScheda').className = "Scheda";
				$('controllo_div').style.display = "none";
				
				if ($('richieste_div') && $('richiesteScheda')) {
				$('richiesteScheda').className = "Scheda";
				$('richieste_div').style.display = "none";	 
				}		
			}
			if(tabId == 'controlloScheda'){
				
				$('controlloScheda').className = "SchedaAttiva";

				$('controllo_div').style.display = "block";
				
				if ($('richieste_div') && $('richiesteScheda')) {
					$('richieste_div').style.display = "none";
					$('richiesteScheda').className = "Scheda";
				}
				
							
			}
			if(tabId == 'richiesteScheda'){

				$('controlloScheda').className = "Scheda";

				$('controllo_div').style.display = "none";
				
				if ($('richieste_div') && $('richiesteScheda')) {
				$('richiesteScheda').className = "SchedaAttiva";
				$('richieste_div').style.display = "block";	
				}
						
			}
		}
	</script>	
	</fieldset>
</div>
<div id="functions">
<ul>
   <li><a tabindex="17" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>