<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="responsabili.label.permessi.title" />
	</title>
	<%
		pageContext.setAttribute("varTT",WebConstants.SOFTWARE_TT);
	%>	
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="responsabili.label.permessi.title" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>

<div id="subcontent">	
	<span class="parametri"><fmt:message key="label.operatore" />: <label><c:out value="${responsabile.entity.responsabile}"/></label>
	</span>
	<br />	
    <div id="navigation">		   
		<!-- Mostro prima i permessi per il software TT -->
		<ul class="listaSchede">	
		<c:forEach var="softwareAbilitati" items="${softwareList}" varStatus="counter">
				<li><a id="scheda_id${softwareAbilitati.codice}" href="javascript:dettaglioTabRespPermessi('${responsabile.entity.id.codice}','${softwareAbilitati.codice}','')" ><c:out value="${softwareAbilitati.descrizione}"></c:out></a></li>			   
	    </c:forEach>	        
		</ul>
	</div>				
	<div id="dettaglioRespPerm_" style="display: none;"></div>			
</div>		
<script type="text/javascript">					


jQuery(document).ready(function () {
	dettaglioTabRespPermessi(${responsabile.entity.id.codice}, '${permessisoftware}', '${status_msg}');
});	


	function dettaglioTabRespPermessi(codice, permessisoftware, status_msg){			
		disableFunctions();
		new Ajax.Request('<%=request.getContextPath()%>/ajax/dettaglioResponsabiliPermessi.htm', {
			method: 'post',
			parameters: {codice: codice, permessisoftware: permessisoftware, status_msg: status_msg},
		  	onSuccess: function(transport){
		  		enableFunctions();
				var response = transport.responseText;		
			  	$("dettaglioRespPerm_").innerHTML = response;
			  	$("dettaglioRespPerm_").style.display='';							  
		  	},
			onFailure: function(transport){ 
				enableFunctions();
			  	var response = transport.responseText;
			    alert(response); 
			}						    		 
		});
		jQuery("a[id^='scheda_id']").removeClass("SchedaAttiva");						
		$('scheda_id'+permessisoftware).className = "SchedaAttiva";
		$('dettaglioRespPerm_').style.display = "none";	
	}	
	function checkedGenitori(menulink, permessisoftware){	
		
		if($('menu_id'+menulink).checked){
			for(i=0;i<(menulink.length-1);i++){
				if(permessisoftware=='TT'){
					$('menu_id'+menulink.substr(0,menulink.length-1-i)).checked = true;
				}else{
					// No checked il menu di primo livello se non siamo sul software TT
					if(i<(menulink.length-1)-1){
						$('menu_id'+menulink.substr(0,menulink.length-1-i)).checked = true;
					}
				}
			}
		}
	}				
	
	function selezionaAndDeselezionaTutti(){	
		if($('a_check_permessi').innerHTML=='<fmt:message key="label.deseleziona_tutto" />'){			
			jQuery("input[id^='menu_id']").attr('checked', false);
			alert('<fmt:message key="responsabili.confirm.salvataggio_permessi" />');
			$('a_check_permessi').innerHTML = '<fmt:message key="label.seleziona_tutto" />';			
		}else{			
			jQuery("input[id^='menu_id']").attr('checked', true);	
			alert('<fmt:message key="responsabili.confirm.salvataggio_permessi" />');
			$('a_check_permessi').innerHTML = '<fmt:message key="label.deseleziona_tutto" />';			
		}		
	}	
</script>	
</body>
</html>