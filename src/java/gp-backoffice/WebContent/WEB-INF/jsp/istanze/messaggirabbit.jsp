<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.messaggi_rabbit_istanza" /></title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.messaggi_rabbit_istanza" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../istanze/messaggirabbit" />
    	<jsp:param name="qs" value="codiceIstanza%3D${param.codiceIstanza}" />
	</jsp:include>

	<div id="subcontent">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${param.codiceIstanza}</c:param>
		</c:import>
 		<br class="clear" />
 	
	 	
	 	<fieldset>
			<legend><fmt:message key="label.azioni" /></legend>
	 		<div  id="functions">
				<ul>
					<li class="invia_messaggio" data-azione="nuova_pratica">Nuova pratica</li>
					<li class="invia_messaggio" data-azione="soggetti_aggiornati">Soggetti aggiornati</li>
					<li class="invia_messaggio" data-azione="cambio_stato">Cambio stato</li>
				</ul>	
			</div>
	 	</fieldset>
	 	<br class="clear"/>
 			  
	</div>
	
	<script type="text/javascript">
	
	vbg.ready(() => {
		let azioni = document.querySelectorAll('.invia_messaggio');
    	for (var i = 0, len = azioni.length; i < len; i++) {
    		
    		console.log(azioni[i]);
    		azioni[i].addEventListener('click', (e) => {
    			  console.log("Mostra aggiorna Intervento");
			      inviaMessaggio(e.target);
    		  });
    	}

    	async function inviaMessaggio(obj){
    		
    		if(confirm("Si desidera inviare il messagggio di "+obj.dataset.azione+" per l'istanza ${istanza.numeroistanza}")){
	    		const formData = new FormData();
			    formData.append('codiceIstanza', ${istanza.id.codice});
			    formData.append('azione', obj.dataset.azione);
			    
			    window.vbg.mostraModalCaricamento();
			    
			    const response = await fetch('ajaxInviaMessaggiorabbit.htm', {
		                method: 'POST',
		                body: formData
		            });
			    
			    if( await response.status == 200){
			    	alert("messaggio scritto");		    			    	
			    }else{
			    	_gestioneErrori(await response.json());
			    }
			    
			    window.vbg.nascondiModalCaricamento();
    		}
    	}
    	
    	function _gestioneErrori(errJson){
     		console.log(errJson.error);
     		alert( errJson.error);
		}
    	
    	
	});				    	
</script>
	
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	</body>
</html>