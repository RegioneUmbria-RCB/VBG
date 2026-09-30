<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="amministrazioni.label.lista_ruoli.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="amministrazioni.label.lista_ruoli.title" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
		<div id="subcontent">
       
        <div class="parametriDiv">
			<div class="etichetta">
	            <div>
	             <fmt:message key="label.amministrazione" />:
	        	</div>
           </div>
         <div class="parametro">
       	  <div>
    	     <c:out value="${amministrazioni.amministrazione}"/>  
          </div>
		 </div>
        </div>       
	<br/>  
    <script type='text/javascript'>
			function setHiddenFieldRuolo(inputField,listItem){
				var a = listItem.id;
				location.href='insertRuolo.htm?codiceRuolo='+a; 
			}				
	</script>
	<label><fmt:message key="amministrazioni.label.inserisci_ruolo" /></label>
	<div>
		<input id="ruolo_id" type="text" class="searchbox" onchange="checkValue(this,'ruolo_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
		<init:autocompleter afterUpdateElement="setHiddenFieldRuolo" methodAjax="findRuoli.htm" idHidden="ruolo_hidden" idInput="ruolo_id" inputTitleKey="label.ricerca_ruolo"/>
    </div>
    <form name="ruoliForm" action="listruoli.htm">
		<jmesa:springTableFacade
			id="ruoli_id" 
			items="${listaruoli}" 
			var="ruoli_var"
			exportTypes="pdfp,excel,csv" 
			stateAttr="restore" >
			<jmesa:htmlTable>
				<jmesa:htmlRow>
                          <jmesa:htmlColumn property="ruoli.id.codice" titleKey="label.codice" width="2%" />						
							<jmesa:htmlColumn property="ruoli.ruolo" titleKey="label.ruoli" />
                          <jmesa:htmlColumn property="responsabili.id.codice" titleKey="label.azioni" sortable="false" filterable="false" width="10%">
							<a href="deleteRuolo.htm?codiceAmministrazione=${ruoli_var.id.codiceamministrazione}&codiceRuolo=${ruoli_var.id.idruolo}" title="<fmt:message key="label.azioni" />&nbsp;${ruoli_var.ruoli.ruolo}">
								<img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.azioni" />&nbsp;${ruoli_var.ruoli.ruolo}"/>
							</a>
						</jmesa:htmlColumn>
				</jmesa:htmlRow>
			</jmesa:htmlTable>
		</jmesa:springTableFacade>
        <input type="hidden" value="${codice}" name="codice"/>
	</form>	
</div>
<div id="functions">
		<ul>
        	<li><a href="javascript:doHref('view.htm?codice=${codice}','');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>