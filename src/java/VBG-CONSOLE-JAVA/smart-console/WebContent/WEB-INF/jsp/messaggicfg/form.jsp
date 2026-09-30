<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Messaggicfg"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${dispatch == 'create'}">
			<fmt:message key="messaggicfg.label.nuovo_messaggicfg.title" />
		</c:if> 
		<c:if test="${dispatch == 'view'}">
			<fmt:message key="messaggicfg.label.dettaglio_messaggicfg.title" />
		</c:if>
	</title>
</head>
<body>

<%

String displayMessHelpIstanDaBackoffice="";
String displayMessHelpAltroMessCfgBase=""; 

if(StringUtils.isNotBlank((String)request.getAttribute("tipo_contesto")) &&request.getAttribute("tipo_contesto").equals(WebConstants.INVIO_ISTANZA_BACKOFFICE))
{
     displayMessHelpIstanDaBackoffice="";
	 displayMessHelpAltroMessCfgBase="display:none"; 
}else
{
    displayMessHelpIstanDaBackoffice="display:none";
	displayMessHelpAltroMessCfgBase=""; 
}
	


%>

<span class="titoloPagina">
<c:if test="${dispatch=='create'}">
	<fmt:message key="messaggicfg.label.nuovo_messaggicfg.title" />
</c:if> 
<c:if test="${dispatch=='view'}">
	<fmt:message key="messaggicfg.label.dettaglio_messaggicfg.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<c:if test="${messaggicfg.software.codice == 'TT'}">	
		<div class="parametriDiv">
			<div class="etichetta">
				<div>Modulo: </div>
			</div>
			<div class="parametro">
				<div>${messaggicfg.software.descrizione} i FrontOffice</div>
			</div>
		</div>			
	</c:if>
	<c:if test="${messaggicfg.software.codice != 'TT'}">	
		<div class="parametriDiv">
			<div class="etichetta">
				<div>Modulo: </div>
			</div>
			<div class="parametro">
				<div>${messaggicfg.software.descrizione} </div>
			</div>
		</div>			
	</c:if>
	<br/>		
	<spring-form:form commandName="messaggicfg" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="messaggicfg" />
    </jsp:include>
	<table>		
		<c:if test="${dispatch=='create'}">
			<tr>
				<td><fmt:message key="messaggicfg.label.selezionare_contesto" /></td>
				<td><spring-form:select id="contestobase_id" path="messaggicfgbase.contesto" onchange="viewHelpCorpo('contestobase_id');javascript:findMesCfgBase();" >
					<spring-form:options items="${messaggicfgbaseList}" itemLabel="descrizione" itemValue="contesto"  />
				</spring-form:select>
				<init:help idHelp="helpcontesto" textKey="messaggicfg.label.selezionare_contesto.help"/>
				<spring-form:errors path="messaggicfgbase.contesto" cssClass="error"/></td>
			</tr>		
		</c:if>
		<c:if test="${dispatch=='view'}">
			<tr>
				<td><fmt:message key="label.contesto" /></td>
				<td><spring-form:input id="contestobase_id" path="messaggicfgbase.descrizione" size="70" disabled="true" />
				<spring-form:errors path="messaggicfgbase.contesto" cssClass="error"/></td>
			</tr>
		</c:if>
		<tr>
			<td><fmt:message key="messaggicfg.label.oggetto" /></td>
			<td><spring-form:input id="oggetto_id" path="oggetto" size="70" />
			<init:help idHelp="help1" textKey="messaggicfg.label.oggetto.help"/>
			<spring-form:errors path="oggetto" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="messaggicfg.label.corpo" /></td>
			<td><spring-form:textarea id="corpo_id" path="corpo" cols="100" rows="8"/>
			<span id="istanza_da_backoffice_id" style="<%=displayMessHelpIstanDaBackoffice%>"><init:help idHelp="help_istanza_da_backoffice" textKey="messaggicfg.label.corpo_invio_istanza_backoffice.help"/></span>
			<span id="altro_mess_cfg_base_id" style="<%=displayMessHelpAltroMessCfgBase%>"><init:help idHelp="help_altro_mess_cfg_base" textKey="messaggicfg.label.corpo.help"/></span>
			<spring-form:errors path="corpo" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="messaggicfg.label.flgInvio" /></td>
			<td>
			<spring-form:select id="flgInvio_id"  size="6" path="flgInvioList" multiple="true">
			
			<%
				Messaggicfg messaggicfg = (Messaggicfg)request.getAttribute("messaggicfg");
				
				Map<Integer, String> presenteM = new HashMap<Integer, String>();
				presenteM.put(1, "");
				presenteM.put(2, "");
				presenteM.put(4, "");
				presenteM.put(8, "");
				presenteM.put(16, "");
				presenteM.put(32, "");
				
				
				int val = messaggicfg.getFlgInvio();
				List<Integer> numeri = new ArrayList<Integer>();
				numeri.add(1);
				numeri.add(2);
				numeri.add(4);
				numeri.add(8);
				numeri.add(16);
				numeri.add(32);
				for (Integer n : numeri) {				    
				    boolean presente = (val & n.intValue()) == n.intValue()?true:false;
					if(presente){
					    presenteM.put(n," selected=\"selected\" ");
					}
				}
					
			%>
			
				<option value="1" <%=presenteM.get(1) %>><fmt:message key="messaggicfg.label.cittadino_richiedente" /></option>
				<option value="2" <%=presenteM.get(2) %>><fmt:message key="messaggicfg.label.altri_soggetti" /></option>
				<option value="4" <%=presenteM.get(4) %>><fmt:message key="messaggicfg.label.mail_responsabile" /></option>
				<option value="8" <%=presenteM.get(8) %>><fmt:message key="label.responsabile_procedimento" /></option>
				<option value="16" <%=presenteM.get(16) %>><fmt:message key="label.responsabile_istruttoria" /></option>
				<option value="32" <%=presenteM.get(32)%>><fmt:message key="label.operatore" /></option>					
				
			</spring-form:select>
			<init:help idHelp="help3" textKey="messaggicfg.label.flgInvio.help"/><fmt:message key="label.select_multiplo" /> 
			<spring-form:errors path="flgInvio" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="messaggicfg.label.flgTipoinvio" /></td>
			<td>
			<spring-form:select id="flgTipoinvio_id" path="flgTipoinvioList" size="2" multiple="true" >
			<%
				Messaggicfg messaggicfg = (Messaggicfg)request.getAttribute("messaggicfg");
				Map<Integer, String> presenteTipoI = new HashMap<Integer, String>();
				presenteTipoI.put(1, "");
				presenteTipoI.put(2, "");
			
				
				int valTipoInvio = messaggicfg.getFlgTipoinvio();
				List<Integer> numeriTipoInvio = new ArrayList<Integer>();
				numeriTipoInvio.add(1);
				numeriTipoInvio.add(2);

				for (Integer n : numeriTipoInvio) {

				    boolean presente = (valTipoInvio & n.intValue()) == n.intValue()?true:false;
					if(presente){
					    presenteTipoI.put(n," selected=\"selected\" ");
					}
				}
					
			%>	
			
			
				<option value="1" <%=presenteTipoI.get(1) %>><fmt:message key="messaggicfg.label.email" /></option>
				<option value="2" <%=presenteTipoI.get(2) %>><fmt:message key="messaggicfg.label.messaggio_frontoffice" /></option>
			
			</spring-form:select>
			<init:help idHelp="help4" textKey="messaggicfg.label.flgTipoinvio.help"/><fmt:message key="label.select_multiplo" />
			<spring-form:errors path="flgTipoinvio" cssClass="error"/></td>
		</tr>
	</table>	
	<c:if test="${dispatch=='create'}">
	<script type='text/javascript'>
	
	
	window.onload=function() {
		findMesCfgBase();
	};

	function viewHelpCorpo(id){ 
		if(document.getElementById(id).value=='<%=WebConstants.INVIO_ISTANZA_BACKOFFICE%>'){
			$('istanza_da_backoffice_id').style.display = '';
 	 		$('altro_mess_cfg_base_id').style.display = 'none';
 	 	}else{
 	 		$('istanza_da_backoffice_id').style.display = 'none';
 	 		$('altro_mess_cfg_base_id').style.display = '';
 	 		
 	 		
  		}
	}
	
	function findMesCfgBase(){ 
			  			  
			  var mesBase = document.getElementById("contestobase_id");
			  if (mesBase.options[1]) {
				  new Ajax.Request('<%=request.getContextPath()%>/ajax/findMessaggiBase.htm', {
					  method: 'get',
					  parameters: {code: mesBase[mesBase.selectedIndex].value, limit: 12},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  responseString=new Array();
						  responseString=response.split(',');
						  document.getElementById("contestobase_id").value=responseString[0];
						  document.getElementById("oggetto_id").value=responseString[1];
						  document.getElementById("corpo_id").value=responseString[2];						  
						  var numFlagInvio = responseString[3];
						  var numFlagTipoInvio=responseString[4];
						  //FLAG INVIO
						  selezionaValoriSelect('flgInvio_id', numFlagInvio);

						 //FLAG TIPO INVIO
						  selezionaValoriSelect('flgTipoinvio_id', numFlagTipoInvio);
						 
					    },
					  onFailure: function(){ 
						 						 
					   }
				  });
			  }
			}
	
	function selezionaValoriSelect(selectId, valoreDef){
		  var select = $(selectId);
		  var opts = select.options;
		  var opt=0;
		  for(opt=0; opt<opts.length; opt++) {
			opts[opt].selected = '';
		  }
		  var opt=0;
		  for(opt=0; opt<opts.length; opt++) {
		  	var valoreOption = opts[opt].value;
			if(( valoreDef & valoreOption  ) == valoreOption){
				opts[opt].selected = 'selected';
			}							  
		  }	
	}
	
	</script>
	</c:if>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${dispatch=='create'}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${dispatch=='view'}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>			
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>		
	</c:if>
	<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
