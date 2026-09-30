<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="amministrazioni.label.lista_responsabili.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="amministrazioni.label.lista_responsabili.title" /></span>
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
	    <script type='text/javascript'>
			function setHiddenFieldResponsabile(inputField,listItem){
				var a = listItem.id;
				location.href='insertResponsabile.htm?codiceResponsabile='+a; 
			}				
		</script>  
		<br />
		
		<div style="width: 650px;min-height: 50px; border: thin dotted; padding: 5px;">
			<fmt:message key="label.help_dismissione_amministrazionireferenti" />
		</div>
		   
		<label><fmt:message key="amministrazioni.label.inserisci_responsabile" /></label>
		<%--
			INSERIMENTO DISMESSO VEDI REDMINE #133
		<div><input id="responsabile_id" type="text" class="searchbox" onchange="checkValue(this,'responsabile_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
			<init:autocompleter afterUpdateElement="setHiddenFieldResponsabile" methodAjax="findResponsabili.htm" idHidden="responsabile_hidden" idInput="responsabile_id" inputTitleKey="label.ricerca_responsabile"/>
        </div>
         --%>
		<form name="responsabiliForm" action="listresponsabili.htm">
			<jmesa:springTableFacade
				id="responsabili_id" 
				items="${amministrazioniresponsabiliList}" 
				var="responsabili_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>
                           <jmesa:htmlColumn property="responsabili.id.codice" titleKey="label.codice" width="2%" />						
						<jmesa:htmlColumn property="responsabili.responsabile" titleKey="label.responsabile" />
						<%--
							INSERIMENTO DISMESSO VEDI REDMINE #133
                           <jmesa:htmlColumn property=".id.codice" titleKey="label.azioni" sortable="false" filterable="false" width="10%">
							<a href="deleteResponsabile.htm?codice=${responsabili_var.id.codice}" title="<fmt:message key="label.azioni" />&nbsp;${responsabili_var.responsabili.responsabile}">
								<img src="${pageContext.request.contextPath}/images/cross.gif" alt="<fmt:message key="label.azioni" />&nbsp;${responsabili_var.responsabili.responsabile}"/>
							</a>
						</jmesa:htmlColumn>
						 --%>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
            <input type="hidden" value="${codice}" name="codice"/>
		</form>
		<script type="text/javascript">
			var _jmesaUrl='listruoli.htm?codice=${codice}&';
			var _captionTab='<fmt:message key="amministrazioni.label.lista_responsabili.title" />';
		</script>
		</div>
		<div id="functions">
			<ul>
                <li><a href="javascript:doHref('view.htm?codice=${codice}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>