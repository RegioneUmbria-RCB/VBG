<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"  %>

<%@page import="java.math.BigDecimal"%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioni.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioni.title.list" /></span>
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
		<div class="parametriDiv">
			<div class="etichetta">
	        <c:if test="${registrazioniFilter.progressivo!='' && registrazioniFilter.progressivo!=null}">
	      		 <div>
	             <fmt:message key="form.registrazioniFilter.progressivo" />:
	        	</div>
	        </c:if>  
	         <c:if test="${(registrazioniFilter.descrizione!='') && (registrazioniFilter.descrizione!=null)}">
	      		 <div>
	             <fmt:message key="form.registrazioniFilter.descrizione" />:
	        	</div>
	        </c:if>      
			<c:if test="${registrazioniFilter.registrazioniCausali.id.codice!=null }">
				<div>
	              <fmt:message key="form.registrazioniFilter.registrazioniCausali" />:
	        	</div>
	        </c:if>
	        <c:if test="${registrazioniFilter.conti.id.codice!=null }">
	       		 <div>
	             <fmt:message key="form.registrazioniFilter.conti" />:
	    		 </div>
	        </c:if>
	        <c:if  test="${registrazioniFilter.dataInizio != null || registrazioniFilter.dataFine != null}" >
	       	<div>
	       		<fmt:message key="form.registrazioniFilter.data" />:
	       	</div>
	        </c:if>
			<c:if test="${registrazioniFilter.anagrafe.id.codice!=null}">
	        	<div>
	            <fmt:message key="form.registrazioniFilter.anagrafe" />:
	     		</div>
	        </c:if>
	        <c:if test="${registrazioniFilter.mercati.id.codice!=null}">
	     		 <div>
	             <fmt:message key="form.registrazioniFilter.mercati" />:
	             </div>
	        </c:if>
	        <c:if test="${registrazioniFilter.mercatiUso.id.codice!=null}">
	     		 <div>
	             <fmt:message key="form.registrazioniFilter.mercatouso" />:
	             </div>
	        </c:if>
	        <c:if test="${registrazioniFilter.alberoproc.id.codice!=null}">
	       		 <div>
	             <fmt:message key="form.registrazioniFilter.alberoproc" />:
	        	</div>
	        </c:if>
	        <c:if test="${registrazioniFilter.amministrazioni.id.codice!=null}">
	      		 <div>
	             <fmt:message key="form.registrazioniFilter.amministrazioni" />:
	        	</div>
	        </c:if>
	        <c:if test="${registrazioniFilter.importo!=null}">
	      		 <div>
	             <fmt:message key="form.registrazioniFilter.importo" />:
	        	</div>
	        </c:if>  
	        <c:if test="${registrazioniFilter.saldo!=null}">
	      		 <div>
	             <fmt:message key="form.registrazioniFilter.daincassare.maggiore" />:
	        	</div>
	        </c:if>  
        </div>
        <div class="parametro">
       		 	<c:if test="${registrazioniFilter.progressivo!='' && registrazioniFilter.progressivo!=null}">
		      		 <div>
		             	<c:out value="${registrazioniFilter.progressivo}"/>
		        	</div>
		        </c:if>  
		         <c:if test="${(registrazioniFilter.descrizione!='') && (registrazioniFilter.descrizione!=null)}">
		      		 <div>
		             	<c:out value="${registrazioniFilter.descrizione}"/>
		        	</div>
		        </c:if>      
				<c:if test="${registrazioniFilter.registrazioniCausali.id.codice!=null }">
					<div>
		              <c:out value="${registrazioniFilter.registrazioniCausali.descrizione}"/>
		        	</div>
		        </c:if>
		        <c:if test="${registrazioniFilter.conti.id.codice!=null }">
		       		 <div>
		             	<c:out value="${registrazioniFilter.conti.descrizione}"/>
		    		 </div>
		        </c:if>
		        <c:if  test="${registrazioniFilter.dataInizio != null || registrazioniFilter.dataFine != null}" >
		       	<div>		       		
					<c:if  test="${registrazioniFilter.dataInizio != null }" >
						<fmt:message key="form.registrazioniFilter.data.inizio" />&nbsp;<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniFilter.dataInizio}"/>
					</c:if>
					<c:if  test="${registrazioniFilter.dataFine != null }" >
						<fmt:message key="form.registrazioniFilter.data.fine"/>&nbsp;<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniFilter.dataFine}"/>
					</c:if>
		       	</div>
		        </c:if>
				<c:if test="${registrazioniFilter.anagrafe.id.codice!=null}">
		        	<div>
		            	<c:out value="${registrazioniFilter.anagrafe.descrizioneRichiedente}"/>
		     		</div>
		        </c:if>
		        <c:if test="${registrazioniFilter.mercati.id.codice!=null}">
		     		 <div>
		             	<c:out value="${registrazioniFilter.mercati.descrizione}"/>
		             </div>
		        </c:if>
		        <c:if test="${registrazioniFilter.mercatiUso.id.codice!=null}">
		     		 <div>
		             	<c:out value="${registrazioniFilter.mercatiUso.descrizione}"/>
		             </div>
		        </c:if>
		        <c:if test="${registrazioniFilter.alberoproc.id.codice!=null}">
		       		 <div>
		             	<c:out value="${registrazioniFilter.alberoproc.scDescrizione}"/>
		        	</div>
		        </c:if>
		        <c:if test="${registrazioniFilter.amministrazioni.id.codice!=null}">
		      		 <div>
		             	<c:out value="${registrazioniFilter.amministrazioni.amministrazione}"/>
		        	</div>
		        </c:if>
		        <c:if test="${registrazioniFilter.importo!=null}">
		      		 <div>
		             	<fmt:formatNumber minFractionDigits="2">${registrazioniFilter.importo}</fmt:formatNumber>
		        	</div>
		        </c:if>  
		        <c:if test="${registrazioniFilter.saldo!=null}">
		      		 <div>
		             	<fmt:formatNumber minFractionDigits="2">${registrazioniFilter.saldo}</fmt:formatNumber>
		        	</div>
		        </c:if>     
        </div>
	</div>       
        <br class="clear"/>
        <form name="registrazioniForm" action="search.htm">
        <c:set var="adeguamentoBoolean" value="false" />
     	<c:set var="percentuale" value="0"/>   
   <%
   
		String adeguamenti=(String)request.getParameter("adeguamenti");
		String adeguamentoPercentuale = (String)request.getParameter("adeguamentoPercentuale");
		
		
		if((adeguamentoPercentuale == null || adeguamentoPercentuale.equals(""))){
			adeguamentoPercentuale = "0.00";
	    }else{
			adeguamentoPercentuale = adeguamentoPercentuale.replace(',','.');
	    }
		if(adeguamentoPercentuale.indexOf('.')==-1){
		    adeguamentoPercentuale+=".00"; 
		}
		
		if(!(adeguamenti == null || adeguamenti.equals(""))){
	%>
		<c:set var="adeguamentoBoolean" value="true" />
		<c:set var="percentuale" value="<%= new BigDecimal(adeguamentoPercentuale) %>"/>
		<div id="functions">
			<ul>
                <li>
                	<input type="text" size="10" id="adeguamentoPercentuale" onchange="checkCurrencyValue(this);" name="adeguamentoPercentuale" value="<%= adeguamentoPercentuale%>" style="text-align: right;" />%
                	<input type="hidden" name="adeguamenti" value="<%=adeguamenti %>" /> 
                	&nbsp;&nbsp;<a href="javascript:aggiorna();"><fmt:message key="button.adeguamento.test" /></a>
                	&nbsp;<a href="javascript:salvaAdeguamento();"><fmt:message key="button.adeguamento.salva" /></a>
                	<init:help idHelp="help1" textKey="button.adeguamento.help"/>
                </li>				 
			</ul>
		</div>		
	<%} %>
        
        <br />
        	${htmltable}
			<%-- 
				<jmesa:springTableFacade
					id="registrazioni_id" 
					items="${registrazioniList}" 
					var="registrazioni_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore" filterMatcherMap="org.jmesa.custom.DateRegistrazioniFilterMatcherMap">
					<jmesa:htmlTable>
						<jmesa:htmlRow>		
							<jmesa:htmlColumn property="id.codice" titleKey="label.codice" width="2%">
	                           	<a href="javascript:dettaglioRegistrazione('${registrazioni_var.id.codice}');">${registrazioni_var.id.codice}</a>
	                        </jmesa:htmlColumn>						
							<jmesa:htmlColumn property="progressivo" titleKey="form.registrazioni.progressivo" />
							<jmesa:htmlColumn property="anno" titleKey="form.registrazioni.anno" />
							<jmesa:htmlColumn property="dataRegistrazione" titleKey="form.registrazioni.dataRegistrazione" pattern="<% =WebConstants.DATE_FORMAT_PATTERN %>" cellEditor="org.jmesa.view.editor.DateCellEditor" filterEditor="org.jmesa.custom.DataRegRegistrazioniCustomFilter"/>
							<jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="form.registrazioni.anagrafe" />
							<jmesa:htmlColumn property="registrazioniCausali.descrizione" titleKey="form.registrazioni.registrazioniCausali" />
							<jmesa:htmlColumn property="mercatiD.mercati.descrizione" titleKey="form.registrazioni.mercati" />
							<jmesa:htmlColumn property="mercatiUso.descrizione" titleKey="form.registrazioni.mercatiUso" />
							<jmesa:htmlColumn property="mercatiD.codiceposteggio" titleKey="form.registrazioni.mercati.posteggio" />
							<jmesa:htmlColumn property="importo" titleKey="form.registrazioniFilter.importo" style="text-align:right;" headerStyle="text-align:right;">
								<c:if test="${adeguamentoBoolean eq 'true'}">								
										<c:set var="importoRegistrazione" value="${registrazioni_var.importo + (( registrazioni_var.importo * percentuale )/ 100) }"/>								
										<b style="color: red">								
										<fmt:formatNumber minFractionDigits="2" maxFractionDigits="2">${importoRegistrazione}</fmt:formatNumber></b>
										&nbsp;(<fmt:formatNumber minFractionDigits="2">${registrazioni_var.importo}</fmt:formatNumber>)
								</c:if>
								<c:if test="${adeguamentoBoolean eq 'false'}">
									<fmt:formatNumber minFractionDigits="2">${registrazioni_var.importo}</fmt:formatNumber>
								</c:if>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="vwRegistrazionisaldo.saldo" titleKey="form.registrazioniimporti.debito" style="text-align:right;" headerStyle="text-align:right;">
								<fmt:formatNumber minFractionDigits="2">${registrazioni_var.vwRegistrazionisaldo.saldo}</fmt:formatNumber>								
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="8%">
								<a class="dettaglioColumn" href="javascript:dettaglioRegistrazione('${registrazioni_var.id.codice}');"  title="<fmt:message key="label.edit.record" /> ${registrazioni_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<c:if test="${registrazioni_var.vwRegistrazionisaldo.saldo gt 0}">									
								<a class="assegnaColumn" href="javascript:vaiAScadenze('${registrazioni_var.progressivo}','${registrazioni_var.anagrafe.id.codice}');" title="<fmt:message key="label.assegna" />" >
									<label><fmt:message key="label.assegna.image" /></label>
								</a>
								</c:if>
								
							</jmesa:htmlColumn>
						</jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>				
				--%>
				
				
			</form>
			
			<script type="text/javascript">
			//<![CDATA[ 					
                var _jmesaUrl='search.htm?';
			    var _captionTab='<fmt:message key="form.registrazioni.title.list" />';

				function dettaglioRegistrazione(codice){
					var goToUrl = "../registrazioni/view.htm?codice="+codice;
					goToUrl = escape(goToUrl);
					doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'');
				}

				function aggiorna(){
					if(checkNumberValue($('adeguamentoPercentuale'))){
							if(checkCurrencyValue($('adeguamentoPercentuale'))){
							var valorePercentuale = $('adeguamentoPercentuale').value;
							var goToUrl = URLDecode('${_urlback}');	
							goToUrl = URLDecode(goToUrl);
							var valoriAggiuntivi = '&adeguamentoPercentuale='+$('adeguamentoPercentuale').value; 
							if(goToUrl.indexOf('adeguamentoPercentuale=')>0){
								valoriAggiuntivi = '';
								var pos = goToUrl.indexOf('adeguamentoPercentuale');
								var stringBegin = goToUrl.substring(0,pos);
								var stringEnd = goToUrl.substring(pos+22);
								goToUrl = stringBegin + 'zzz'+ stringEnd;
								goToUrl += '&adeguamentoPercentuale='+valorePercentuale;										
							}							
							doHref(goToUrl + valoriAggiuntivi,'');
						}
					}
				}

				function salvaAdeguamento(){					
					if(checkCurrencyValue($('adeguamentoPercentuale'))){
						doHref('adeguaImporti.htm?adeguamentoPercentuale='+$('adeguamentoPercentuale').value,'<fmt:message key="javascript.confirm.update" />');
					}
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
                <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
                <li>
                <a href="../registrazioni/ajaxEsportaTracciato.htm?_tm=<%=System.currentTimeMillis() %>"  title="esporta tracciato">
									            <label>esporta tracciato</label>
								            </a>
				</li>				            				 
			</ul>
		</div>
	</body>
</html>