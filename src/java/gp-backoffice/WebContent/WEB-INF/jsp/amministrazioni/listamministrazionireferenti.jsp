<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><fmt:message key="amministrazioni.label.lista_amministrazionireferenti.title" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="amministrazioni.label.lista_amministrazionireferenti.title" /></span>
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
			<form name="amministrazionireferentiForm" action="listamministrazionireferenti.htm">
				<jmesa:springTableFacade
					id="amministrazionireferenti_id" 
					items="${amministrazionireferentiList}" 
					var="amministrazionireferenti_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
                                  <a href="viewAmministrazionireferenti.htm?codice=${amministrazionireferenti_var.id.codice}">${amministrazionireferenti_var.id.codice}</a>
                            </jmesa:htmlColumn>							
							<jmesa:htmlColumn property="ufficio" titleKey="amministrazioni.label.amministrazioni_referenti_ufficio" />
                            <jmesa:htmlColumn property="referente" titleKey="amministrazioni.label.amministrazioni_referenti_referente" />
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="10%">
								<a class="dettaglioColumn" href="viewAmministrazionireferenti.htm?codice=${amministrazionireferenti_var.id.codice}" title="<fmt:message key="label.edit.record" />&nbsp;${amministrazionireferenti_var.ufficio}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
                <input type="hidden" value="${codice}" name="codice"/>
			</form>
			<script type="text/javascript">
				var _jmesaUrl='listamministrazionireferenti.htm?codice=${codice}&';
				var _captionTab='<fmt:message key="amministrazioni.label.lista_amministrazionireferenti.title" />';
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createAmministrazionireferenti.htm?codiceamministrazione=${codice}','');"><fmt:message key="button.new" /></a></li>
                <li><a href="javascript:doHref('view.htm?codice=${codice}','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>