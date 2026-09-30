<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>

<%@page import="java.math.BigDecimal"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioni.transazioni.title.step.1" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioni.transazioni.title.step.1" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../registrazioni/search" />
		</jsp:include>
		<div id="subcontent">
  
        <div class="intestazione">
			<fmt:message key="label.filtri"></fmt:message>
		</div>
		<br />
        <c:if test="${registrazioniFilter.progressivo!='' && registrazioniFilter.progressivo!=null}">
      		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.progressivo" />:<label> <c:out value="${registrazioniFilter.progressivo}"/></label>
        	</span>
        </c:if>  
         <c:if test="${(registrazioniFilter.descrizione!='') && (registrazioniFilter.descrizione!=null)}">
      		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.descrizione" />:<label> <c:out value="${registrazioniFilter.descrizione}"/></label>
        	</span>
        </c:if>      
		<c:if test="${registrazioniFilter.registrazioniCausali.id.codice!=null }">
			<span class="parametri">
              <fmt:message key="form.registrazioniFilter.registrazioniCausali" /> : <label><c:out value="${registrazioniFilter.registrazioniCausali.descrizione}"/></label>
        	</span>
        </c:if>
        <c:if test="${registrazioniFilter.conti.id.codice!=null }">
       		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.conti" />:<label> <c:out value="${registrazioniFilter.conti.descrizione}"/></label>
    		 </span>
        </c:if>
        <c:if  test="${datainizio != null || datafine != null}" >
       	<span class="parametri">
       		<fmt:message key="form.registrazioniFilter.data" />:
			<c:if  test="${datainizio != null }" >
				<fmt:message key="form.registrazioniFilter.data.inizio" /><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datainizio}"/></label>
			</c:if>
			<c:if  test="${datafine != null }" >
				<fmt:message key="form.registrazioniFilter.data.fine"/><label><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datafine}"/></label>
			</c:if>
       	</span>
        </c:if>
		<c:if test="${registrazioniFilter.anagrafe.id.codice!=null}">
        	<span class="parametri">
            <fmt:message key="form.registrazioniFilter.anagrafe" />: <label><c:out value="${registrazioniFilter.anagrafe.descrizioneRichiedente}"/></label>
     		</span>
        </c:if>
        <c:if test="${registrazioniFilter.mercati.id.codice!=null}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.mercati" />: <label><c:out value="${registrazioniFilter.mercati.descrizione}"/></label>
             </span>
        </c:if>
        <c:if test="${registrazioniFilter.mercatiUso.id.codice!=null}">
     		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.mercatouso" />: <label><c:out value="${registrazioniFilter.mercatiUso.descrizione}"/></label>
             </span>
        </c:if>
        <c:if test="${registrazioniFilter.alberoproc.id.codice!=null}">
       		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.alberoproc" />:<label> <c:out value="${registrazioniFilter.alberoproc.scDescrizione}"/></label>
        	</span>
        </c:if>
        <c:if test="${registrazioniFilter.amministrazioni.id.codice!=null}">
      		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.amministrazioni" />:<label> <c:out value="${registrazioniFilter.amministrazioni.amministrazione}"/></label>
        	</span>
        </c:if>
        <c:if test="${registrazioniFilter.importo!=null}">
      		 <span class="parametri">
             <fmt:message key="form.registrazioniFilter.importo" />:<label><fmt:formatNumber minFractionDigits="2">${registrazioniFilter.importo}</fmt:formatNumber></label>
        	</span>
        </c:if>    
        <br />
<spring-form:form  commandName="registrazioni" name="inviodati">
        <br />
        <div class="jmesa">
				<table border="0" width="50%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="form.registrazioni.progressivo"/></td>
							<td><fmt:message key="form.registrazioni.anno"/></td>
							<td><fmt:message key="form.registrazioni.dataregistrazione"/></td>
							<td><fmt:message key="form.registrazioni.anagrafe"/></td>
							<td><fmt:message key="form.registrazioni.registrazioniCausali"/></td>
							<td><fmt:message key="form.registrazioni.mercati"/></td>
							<td><fmt:message key="form.registrazioni.mercatiUso"/></td>
							<td><fmt:message key="form.registrazioni.mercati.posteggio"/></td>
							<td style="text-align: right;"><fmt:message key="form.registrazioni.importo"/></td>
							<td style="text-align: right;"><fmt:message key="form.registrazioniimporti.debito"/></td>
							<td><a href="javascript:selezionaDeselezionaTuttiCheckbox(document.inviodati);">
								<fmt:message key="form.registrazioniimporti.seleziona"/></a></td>
						</tr>
					</thead>
					<tbody class="tbody" >
						<%int j=1;%>
							<c:forEach var="registrazione_var" items="${registrazioniList}" varStatus="rataStatus">
								<tr>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.progressivo}</td>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.anno}</td>
									<td class="<%=(j%2)==0?"odd":"even"%>"><fmt:formatDate pattern="<%= WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazione_var.dataRegistrazione}" /></td>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.anagrafe.descrizioneRichiedente}</td>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.registrazioniCausali.descrizione}</td>									
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.mercatiD.mercati.descrizione}</td>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.mercatiUso.descrizione}</td>
									<td class="<%=(j%2)==0?"odd":"even"%>">${registrazione_var.mercatiD.codiceposteggio}</td>
									<td style="text-align: right;" class="<%=(j%2)==0?"odd":"even"%>"><fmt:formatNumber minFractionDigits="2">${registrazione_var.importo}</fmt:formatNumber></td>
									<td style="text-align: right;" class="<%=(j%2)==0?"odd":"even"%>"><fmt:formatNumber minFractionDigits="2">${registrazione_var.vwRegistrazionisaldo.saldo}</fmt:formatNumber></td>
									<td class="<%=(j%2)==0?"odd":"even"%>"><spring-form:checkbox path="transazioniHelper.registrazioniChkList" value="${registrazione_var.id.codice}"/></td>									
								</tr>
							</c:forEach>
					</tbody>	
							
				</table>
		</div>				
				
</spring-form:form>
			
			<script type="text/javascript">
			//<![CDATA[ 					

			           
			           
			    var _jmesaUrl='search.htm?';
			    

			    var _captionTab='<fmt:message key="form.registrazioni.title.list" />';

				function dettaglioRegistrazione(codice){
					var goToUrl = "../registrazioni/view.htm?codice="+codice;
					goToUrl = escape(goToUrl);
					doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
				}

				function vaiAScadenze(progressivo, codiceAnagrafe){
					var goToUrl = "../registrazioniinout/listScadenze.htm?filter.progressivo="+progressivo+
								"&filter.anagrafe.id.codice="+codiceAnagrafe;
					goToUrl = escape(goToUrl);
					doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
				}
				
				//]]> 
			</script>
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doSubmit('listTransazioniImporti.htm', '', document.inviodati);"><fmt:message key="button.forward" /></a></li>
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
			</ul>
		</div>
	</body>
</html>