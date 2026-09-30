<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_senza_pagamento" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_senza_pagamento" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="search"/>
</jsp:include>
<div id="subcontent">

	<h1 class="error_header">
	<fmt:message key="form.gestionepresenze.sistema_posizioni_debitorie_senza_pagamento.help" />
	</h1>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatipresenzeT" />
    </jsp:include>
    <table>
		<tr>
			<td><fmt:message key="label.dalla_data" />*</td>
			<td class="inline-ui-cell" valign="middle" style="vertical-align: middle;">
				<input type="text" id="dalladata_id" name="dalladata" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dalladata_id" textKey="label.calendar"/>	
			</td>
			<td><fmt:message key="label.alla_data" />*</td>
			<td class="inline-ui-cell" valign="middle" style="vertical-align: middle;">
				<input type="text" id="alladata_id" name="alladata" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
				<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="alladata_id" textKey="label.calendar"/>	
			</td>	
		</tr>
		<tr>
			<td>Tipologia*</td>
			<td class="inline-ui-cell" valign="middle" style="vertical-align: middle;">
				<select name="tipologia" id="tipologia_id">
					<%--
						<option value="tutti">tutti</option>
					 --%>
					<option value="battitori">battitori</option>
					<%--
						<option value="concessionari">concessionari</option>
						<option value="spuntisti">spuntisti</option>
					 --%>
				</select>
			</td>
			
		</tr>		
	</table>

	<h2 id="avanzamento_id" class="success_header"></h2>
	<div id="output_id"></div>
	
	<br />
	<pre id="report_id"></pre>
	
</div>
<script type="text/javascript">
	
	var glbContinuaAvanzamento = true;

	function ok(){
		
		var dalladata = document.getElementById('dalladata_id').value;
		var alladata = document.getElementById('alladata_id').value;
		var tipologia = document.getElementById('tipologia_id').value;
		
		if(dalladata != '' && alladata != '' && tipologia != ''){
			if(confirm('Attenzione! La procedura creerà le posizioni debitorie le presenze che non hanno attualmente registrata una posizione debitoria. \nContinuare?')){
			
				checkAvanzamento();
				
				var jhqrPr = jQuery.ajax({
					  url: '../gestionepresenze/ajaxVerificaESistemaPagamenti.htm',
					  data: "dalladata="+dalladata+"&alladata="+alladata+"&tipo="+tipologia,
					  context: document.body,
					  cache: false,					  
					  dataType: "html",
					  success: function(result){
						  jQuery('#output_id').html(result);
						  stopAvanzamento();
					  },
				      error: function (xhr, ajaxOptions, thrownError) {
				    	stopAvanzamento();
				        alert(xhr.status);
				        alert(thrownError);
				      }
				});
				
			}
	
		}else{
			alert("Inserire i parametri obbligatori");
		}
	}
	
	
	function stopAvanzamento(){
		glbContinuaAvanzamento = false;
	}
	
	async function checkAvanzamento(){
		
		const response = await fetch("../gestionepresenze/ajaxSistemaPosizioniDebitorieAvanzamento.htm" , {
            method: "GET",
            cache: "no-cache",
            headers: {
                'Content-Type': 'application/json'
            }
        });
        let ris = await response.json();		
        document.getElementById('avanzamento_id').innerHTML=`<ul>
        													<li>totale record: \${ris.totale} </li>
        													<li>elaborati: \${ris.elaborati} </li>
        													</ul>`;		
       if(glbContinuaAvanzamento){
	       setTimeout(() => {
	    	   checkAvanzamento();
			}, 4000); 				
       }
	}
	
	async function checkReportAvanzamento(){
		
		const response = await fetch("../gestionepresenze/ajaxReportSistemaPosizioniDebitorieAvanzamento.htm" , {
            method: "GET",
            cache: "no-cache",
            headers: {
                'Content-Type': 'application/json'
            }
        });
		let ris = await response.text();		
		document.getElementById('report_id').innerText = ris		
	}
	
	async function fermaProcedura(){
		
		const response = await fetch("../gestionepresenze/ajaxFermaSistemaPosizioniDebitorieAvanzamento.htm" , {
            method: "GET",
            cache: "no-cache",
            headers: {
                'Content-Type': 'application/json'
            }
        });
		
		let ris = await response.json();		
        let risposta =`fermaProceduraPosizioniDebitorieAvanzamento: \${ris.fermaProceduraPosizioniDebitorieAvanzamento} `;		
        alert(risposta);
	}
	

</script>
<div id="functions">
<ul>

	<li><a href="javascript:ok()"><fmt:message key="button.ok" /></a></li>
	
	<li><a href="javascript:fermaProcedura()">ferma procedura</a></li>
	<li><a href="javascript:checkReportAvanzamento()">report</a></li>
	
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
