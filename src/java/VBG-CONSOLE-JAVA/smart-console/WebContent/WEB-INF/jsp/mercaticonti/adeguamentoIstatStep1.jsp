<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.mercatiConti.adeguamento_istat" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.mercatiConti.adeguamento_istat" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../mercaticonti/adeguamentoIstatStep1" />
	    </jsp:include>
		<div id="subcontent">
		<span class="parametri"><fmt:message key="form.mercatiConti.mercato" />:<label>${mercati.descrizione}</label></span><br/>

<form name="inviodati" method="post" action="#">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiConti" />
    </jsp:include>
	<fieldset>
		<legend>
			  <fmt:message key="form.mercatiConti.dataprincipali"></fmt:message></legend>
			  
			  <table cellpadding="4" cellspacing="0" border="1">
			  
			  	  <caption><fmt:message key="form.mercatiConti.adeguamento_istat.lista_dei_conti"/></caption>
				  <tr>
				    <th><fmt:message key="form.mercatiConti.adeguamento_istat.lista_dei_conti.anno_precedente"/></th>
				    <th><fmt:message key="form.mercatiConti.adeguamento_istat.lista_dei_conti.anno_da_adeguare"/></th>
				  </tr>
				  <c:forEach items="${listaConti}" var="conto" varStatus="idx">
						  <tr>
						    <td>
						    	${conto.descrizioneConto}
						    </td>
						    <td>
						    	<input id="conti_id_${idx.index}" name="conti_descrizioneConto${idx.index}" class="searchbox" onchange="checkValue(this,'conti_hidden')" onkeydown="javascript:return searchAll(this,event)" size="50" />
								<init:autocompleter methodAjax="findConti.htm" idHidden="conti_hidden_${idx.index}_${conto.id.codice}" idInput="conti_id_${idx.index}" inputTitleKey="label.ricerca_conto"/>								
								<input type="hidden" id="conti_hidden_${idx.index}_${conto.id.codice}" name="conti_hidden_${idx.index}_${conto.id.codice}" />
						    </td>
						  </tr>
				  </c:forEach>
			</table>
			
			<table>
			  <tr>
			    <td><fmt:message key="label.anno"/>:</td>
			    <td>
			    	<input type="hidden" id="anno_precedente_id" name="anno_precedente" value="${anno}" />
	          		<input type="text" id="anno_id" name="anno_da_adeguare" size="4" value="${(anno+1)}" readonly="readonly"/>
	          	</td>
			  </tr>
			  <tr>
			    <td> <fmt:message key="label.coefficiente_edeguamento_istat"/>: </td>
			    <td>
					<input style="text-align: right;" type="text" id="coefficiente_adeguamento_id" name="coefficiente_adeguamento" onchange="checkCurrencyValue(this)" size="4" /> %
	          	</td>
			  </tr>
			</table>
			<input type="hidden" id="codiceMercato_id" name="codiceMercato" value="${mercati.id.codice}" />
	</fieldset>

			
</form>



<script type="text/javascript">
	
	
	function salvaAdeguamento(){
		var action = '${pageContext.request.contextPath}/mercaticonti/updateAdeguamentoIstat.htm';
		if($('coefficiente_adeguamento_id').value==null || $('coefficiente_adeguamento_id').value==''){
			alert('Coefficiente adeguamento ISTAT non valido');
			$('coefficiente_adeguamento_id').focus();
			return;
		}else{
			if(!checkCurrencyValue($('coefficiente_adeguamento_id'))){
				return;
			}		
		}
		doSubmit(action,'Attenzione!!! Effettuare l\'operazione di adeguamento percentuale?');
	}
	
</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:salvaAdeguamento();"><fmt:message key="button.save" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>