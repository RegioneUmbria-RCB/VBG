<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.pagamentoutenzeposteggio.title.assegnaimporti" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.pagamentoutenzeposteggio.title.assegnaimporti" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="pagamentoUtenze" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="pagamentoUtenze" />
    </jsp:include>

<%-- Blocco scelta parametri di registrazione --%>

<fieldset><legend><fmt:message key="form.pagamentoutenzeposteggio.registrazione"></fmt:message></legend>
       
       <%-- Caso in cui scegliamo i parametri da settare --%>
       <c:if test="${contiList==null}">
       <table>
		<tr>
            <td><fmt:message key="form.pagamentoutenzeposteggio.data"/></td>
            <td >
				<spring-form:input tabindex="1" id="dataRegistrazione_id" path="dataregistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calregistrazione" idInput="dataRegistrazione_id" textKey="label.calendar"/>
				<spring-form:errors path="dataregistrazione" cssClass="error" delimiter=" :"/>  
			</td>
        </tr>
        <tr>	 	
             <td><fmt:message key="form.pagamentoutenzeposteggio.causale" /></td>
             <td>
                <spring-form:select tabindex="3"  path="registrazioniCausali.id.codice">
                <spring-form:options items="${registrazionicausaliList}" itemValue="id.codice" itemLabel="descrizione"/>
                </spring-form:select>
                <spring-form:errors path="registrazioniCausali.id.codice" cssClass="error" />
			 </td>
		</tr>		
		<tr>
             <td><fmt:message key="form.pagamentoutenzeposteggio.mercato" /></td>
             <td><span class="parametri" style="display: inline;">${pagamentoUtenze.mercati.descrizione}</span></td>
        </tr>
        <tr>
			<td><fmt:message key="form.pagamentoutenzeposteggio.giorno" /></td>
            <td><span class="parametri" style="display: inline;">${pagamentoUtenze.mercatiUso.descrizione}</span></td>
        </tr>
		</table>

<%-- script per il calendario --%>
<script type="text/javascript">
$('dataRegistrazione_id').focus();
</script>
   
   </c:if>
   
    <%-- Caso in cui mostriamo i parametri settati --%>
        <c:if test="${contiList!=null}">
        <table>
		<tr>
			<td><fmt:message key="form.pagamentoutenzeposteggio.data" /></td>
            <td> <spring-form:input path="dataregistrazione" readonly="true"/></td>
		</tr>
        <tr>	 	
             <td><fmt:message key="form.pagamentoutenzeposteggio.causale" /></td>
             <td><spring-form:input  path="registrazioniCausali.descrizione" readonly="true" size="30"/></td>
		</tr>		
		<tr>
             <td><fmt:message key="form.pagamentoutenzeposteggio.mercato" /></td>
             <td><span class="parametri" style="display: inline;">${pagamentoUtenze.mercati.descrizione}</span></td>
        </tr>
        <tr>
			<td><fmt:message key="form.pagamentoutenzeposteggio.giorno" /></td>
            <td><span class="parametri">${pagamentoUtenze.mercatiUso.descrizione}</span></td>
        </tr>
		</table>
   
  </c:if>


</fieldset><br></br>

<%-- Fine Blocco scelta parametri di registrazione --%>

<%-- Blocco scelta pagamenti utenza --%>

<fieldset><legend><fmt:message key="form.pagamentoutenzeposteggio.importidaassegnare"></fmt:message></legend>
     <c:if test="${contiList!=null}">
     
     <%-- Tabella che mostra i pagamenti utenza settati  --%>
     <div class="jmesa">
	 <table border="0" width="100%"  cellpadding="2" cellspacing="0" class="table">
	     <thead>
           <tr class="header">
             <td><fmt:message key="form.pagamentoutenzeposteggio.conti" /></td>
             <td><fmt:message key="form.pagamentoutenzeposteggio.data" /></td>
      		 <td style="text-align: right;"><fmt:message key="form.pagamentoutenzeposteggio.importo" /></td>
      		 <td><fmt:message key="label.elimina" /></td>
    	  </tr>
    	</thead>
    <tbody class="tbody">
	<%
      int j = 1;
	%>
		 <c:forEach var="registrazioneImportiList_var" items="${pagamentoUtenze.registrazioneImportiList}">
		 <tr class="<%=(j % 2) == 0 ? "odd" : "even"%>">
			<td><label>${registrazioneImportiList_var.conti.descrizioneConto}</label></td>
    		<td><label> <fmt:formatDate value="${registrazioneImportiList_var.scadenza}" pattern="dd/MM/yyyy"/></label></td>
    		<td align="right"><label>${registrazioneImportiList_var.importo}</label></td>
    		<td>
    			<a class="vbg-btn btn-elimina" href="javascript:doSubmit('deleteImporto.htm?codice=<%=j-1%>','',document.inviodati)"></a>
    		</td>
	<%
	j++;
	%>
    	</tr>
		</c:forEach>
	
    </tbody>
	
    </table><br></br>
    
    <%-- Fine Tabella che mostra i pagamenti utenza settati  --%>
    
    <%-- campi scelta parametri pagamento utenza  --%>
    <c:if test="${fn:length(pagamentoUtenze.posteggiList) == 0}" >
    <table class="jmesa">
    	<tr class="header">
    		<td><fmt:message key="form.pagamentoutenzeposteggio.conti" /></td>
    		<td><fmt:message key="form.pagamentoutenzeposteggio.data" /></td>
    		<td><fmt:message key="form.pagamentoutenzeposteggio.importo" /></td>
    	</tr>
       <tr>
    		<td>
                <spring-form:select  path="conti.id.codice"   >
                <spring-form:options items="${contiList}" itemValue="id.codice" itemLabel="descrizioneConto"/>
                </spring-form:select>
     			<spring-form:errors path="conti.id.codice" cssClass="error" />
     		</td>
     		<td class="inline-ui-cell">
				<spring-form:input id="dataScadenza_id" path="datascadenza" size="10" maxlength="10" onblur="isValidDate(this,true);" />
				<a id="caldatascadenza" href="" title="<fmt:message key="label.calendar"/>"> 
				<img src="${pageContext.request.contextPath}/images/cal.gif" alt="<fmt:message key="label.calendar"/>"/></a> 
				<spring-form:errors path="datascadenza" cssClass="error" delimiter=" :"/>  
			</td>
			<td>
          		<spring-form:input path="importi" size="10" cssStyle="text-align:right;" onchange="checkNumberValue(this,true);"/>
          		<spring-form:errors path="importi" cssClass="error" />
     		</td>
     </tr>
   </table>
    
    <%-- fine campi scelta parametri pagamento utenza  --%>
   
    <%-- Script per la data  --%>
    <script type="text/javascript">
   //<![CDATA[ 					
    Calendar.setup({
	inputField     :    "dataScadenza_id",     // id of the input field
	button         :    "caldatascadenza"  // trigger for the calendar (button ID)
	
    });

    //]]> 
  </script>
   
  </c:if>
  </div>

  <%-- link per aggiungere pagamenti utenze ,scopare un avolta che ho associato i pagamenti settatti ai parcheggi  --%>
  </c:if>
  <c:if test="${fn:length(pagamentoUtenze.posteggiList) == 0}" >
  <a class="vbg-btn btn-aggiungi" href="javascript:doSubmit('pagamentoUtenze.htm','',document.inviodati)">
  </a>
  </c:if>
  </fieldset><br></br>

<%-- Fine Blocco scelta pagamenti utenza --%>


<%--  Blocco posteggi attivi con pagamenti associati --%>

<%--  cntrolla visualizzazione solo se sono stati già inseriti pagamenti utenze --%>
 <c:if test="${not empty pagamentoUtenze.registrazioneImportiList}" >
  
    <%--  Script utilizzato per nascondere i posteggi a cui nion vogliamo inserire i pagamenti configuarti --%>
    <script type="text/javascript">

    function displayCriteri(idcriterio,idtable){
       
        if($(idcriterio).checked){
    	   $(idtable).style.display='none';
    	   
        } else{
        	$(idtable).style.display='';
        }
        
        }
  </script>                              

<%
String displayCriteri="display:block;";
%> 

<fieldset><legend><fmt:message key="form.pagamentoutenzeposteggio.importiposteggi"></fmt:message></legend>

<%--START Tabella principale --%> 
    <div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%"><fmt:message key="form.pagamentoutenzeposteggio.posteggio" /></td>
				<td width="30%"><fmt:message key="form.pagamentoutenzeposteggio.anagrafe" /></td>
                <td width="65%"><fmt:message key="form.pagamentoutenzeposteggio.importi" /></td>
                <td><fmt:message key="form.pagamentoutenzeposteggio.visualizza" /></td>
			</tr>
		</thead>
		<tbody class="tbody">
			<%int i = 1; %>
			<c:forEach var="posteggiList_var" 
				items="${pagamentoUtenze.posteggiList}"
				varStatus="idx">
				<tr class="<%=(i % 2) == 0 ? "odd" : "even"%>">
					<td valign="top">${posteggiList_var.posteggio.codiceposteggio}</td>
                    <td valign="top">${posteggiList_var.anagrafe.nominativo}</td>
                    <td valign="top">
						<%-- START TABELLA ANNIDATA NELLA SECONDA COLONNA --%>
	                    <%-- primo div utilizzato per nascondere il poasteggio --%>
	                    <div id="table${idx.index}">
	                    <div class="jmesa">
						<table border="0" width="100%"  cellpadding="2" cellspacing="0" class="table">
							<thead>
								<tr class="header">
									<td><fmt:message key="form.pagamentoutenzeposteggio.conto" /></td>
									<td><fmt:message key="form.pagamentoutenzeposteggio.scadenza" /></td>
	                                <td style="text-align: right;"><fmt:message key="form.pagamentoutenzeposteggio.importo" /></td>
	                               
								</tr>
							</thead>
							<tbody class="tbody">
								<%int k = 1; %>
								<c:forEach var="registrazioniimportiposteggioList_var"
									items="${posteggiList_var.registrazioniimportiposteggioList}"
									varStatus="idx1">
									<tr class="<%=(k % 2) == 0 ? "odd" : "even"%>">
										<td id="conto${idx.index}_${idx1.index}">${registrazioniimportiposteggioList_var.conti.descrizioneConto}</td>
										<td id="scadenza${idx.index}_${idx1.index}"><fmt:formatDate value="${registrazioniimportiposteggioList_var.scadenza}" pattern="dd/MM/yyyy"/></td>
	                                    <td align="right">
	                                    <spring-form:input readonly="false" id="importo${idx.index}_${idx1.index}" path="posteggiList[${idx.index}].registrazioniimportiposteggioList[${idx1.index}].importo" maxlength="8" size="8" cssStyle="text-align:right;"  onchange="checkNumberValue(this,true);"/>
	                                    </td>
	                                </tr>
									<%k++; %>
								</c:forEach>
							</tbody>
						</table>
	                    </div>
						<%-- END FINE TABELLA ANNIDATA --%>
	                	</div>
                	</td>
                	<td>
                  		 <spring-form:checkbox id="criteri${idx.index}_${idx1.index}" path="posteggiList[${idx.index}].visualizza"  onclick="displayCriteri('criteri${idx.index}_${idx1.index}','table${idx.index}');"/>
                	</td> 
				</tr>
				<%i++; %>
			</c:forEach>
		</tbody>
	</table>
	</div>
<%-- END FINE TABELLA PRINCIPALE --%> 
<c:if test="${fn:length(pagamentoUtenze.posteggiList) == 0}" >
	<a class="vbg-btn btn-aggiungi" href="javascript:doSubmit('assegnaPagamentiAposteggi.htm','',document.inviodati)">
	</a><br></br><br></br>
</c:if>

<%-- Blocco scelta e descrizione rateizzazione--%>

<c:if test="${fn:length(pagamentoUtenze.posteggiList) > 0}" >
  <span class="parametri">
      <fmt:message key="form.pagamentoutenzeposteggio.rate"></fmt:message>
      <spring-form:checkbox path="rate" /><br></br><br></br>

  <c:forEach items="${rangerateizzazioniList}" var="rangerateizzazioniList_var">
  <table>
  <tr>
	<td><fmt:message key="label.from"/> ${rangerateizzazioniList_var.rangeBasso} <fmt:message key="label.valuta"/></td>
	<td>
	<fmt:message key="label.to"/>
 	<c:if test="${rangerateizzazioniList_var.rangeAlto ne null}">
 	${rangerateizzazioniList_var.rangeAlto} <fmt:message key="label.valuta"/>
	</c:if>
	<c:if test="${rangerateizzazioniList_var.rangeAlto eq null}">
	<fmt:message key="label.oltre"/>
	</c:if>
	</td>
	<td>
 	- ${rangerateizzazioniList_var.tiporateizzazione.descrizione}
	</td>
	</tr>
	</table>
	</c:forEach>
  </span>
</c:if>
</fieldset>
<%-- Fine Blocco posteggi attivi con pagamenti associati --%>
</c:if>
</spring-form:form>
</div>
<div id="functions">
	<ul>
	    <c:if test="${fn:length(pagamentoUtenze.posteggiList) > 0}" >
		<li><a href="javascript:doSubmit('insertPagamentiUtenze.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		</c:if>
	    <li><a href="javascript:doHref('pagamentoUtenzeCreate.htm?mercati.id.codice=${pagamentoUtenze.mercati.id.codice}&mercatoUso=${pagamentoUtenze.mercatiUso.id.codice}','')"><fmt:message key="button.reset" /></a></li>
	    <li><a href="javascript:doHref('../contabilitamercati/createSearch.htm','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>