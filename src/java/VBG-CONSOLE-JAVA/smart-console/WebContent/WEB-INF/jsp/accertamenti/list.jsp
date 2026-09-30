<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="form.registrazioniFilter.title.list" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="form.registrazioniFilter.title.list" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../accertamenti/search" />
		</jsp:include>
		<div id="subcontent">
		<div class="intestazione">
			<fmt:message key="label.filtri"></fmt:message>
		</div>
		<br />
		<div class="parametriDiv">
			<div class="etichetta">
			<c:if test="${registrazioniFilter.registrazioniCausali.id.codice!=null}">
				<div >
	              <fmt:message key="form.registrazioniFilter.registrazioniCausali" /> : 
	        	</div>
       		</c:if>
       		<c:if test="${registrazioniFilter.conti.id.codice!=null}">
	       		<div>
	             	<fmt:message key="form.registrazioniFilter.conti" />:
	    		</div>
        	</c:if>
	        <c:if  test="${registrazioniFilter.dataInizio != null || registrazioniFilter.dataFine != null}" >
		       	<div>
		       		<fmt:message key="form.registrazioniFilter.datadistinta" />:
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
		        <div>
		        	<fmt:message key="form.registrazioniFilter.raggruppamento" />:
		        </div>
			</div>
			<div class="parametro">
			<c:if test="${registrazioniFilter.registrazioniCausali.id.codice!=null}">
				<div>
	              <c:out value="${registrazioniFilter.registrazioniCausali.descrizione}"/>
	        	</div>
       		</c:if>
       		<c:if test="${registrazioniFilter.conti.id.codice!=null}">
	       		<div>
	              	<c:out value="${registrazioniFilter.conti.descrizione}"/>
	    		</div>
        	</c:if>
        	<c:if  test="${registrazioniFilter.dataInizio != null || registrazioniFilter.dataFine != null}" >
	        	<div>
					<c:if  test="${registrazioniFilter.dataInizio != null }" >
							<fmt:message key="form.registrazioniFilter.data.inizio"/> <fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniFilter.dataInizio}"/>
					</c:if>
					<c:if  test="${registrazioniFilter.dataFine != null }" >
							<fmt:message key="form.registrazioniFilter.data.fine"/> <fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${registrazioniFilter.dataFine}"/>
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
	        <div>
	        <c:if test="${enum eq 'ANAGRAFE'}">
				<fmt:message key="form.registrazioniFilter.raggruppamento.anagrafe" />
			</c:if>
			<c:if test="${enum eq 'CAUSALE'}">
				<fmt:message key="form.registrazioniFilter.raggruppamento.causale" />
			</c:if>
			<c:if test="${enum eq 'CONTO'}">
				<fmt:message key="form.registrazioniFilter.raggruppamento.conto" />
			</c:if>
			<c:if test="${enum eq 'AMMINISTRAZIONE'}">			
				<fmt:message key="form.registrazioniFilter.raggruppamento.amministrazioni" />			
			</c:if>
			<c:if test="${enum eq 'MERCATO'}">			
				<fmt:message key="form.registrazioniFilter.raggruppamento.mercato" />			
			</c:if>
			</div>
			</div>
		</div>
		<div class="clear"></div>
		<form name="registrazioniFilterForm" action="search.htm">
				<jmesa:springTableFacade
					id="registrazioniFilter_id" 
					items="${registrazioniFilterList}" 
					var="registrazioniFilter_var"
					exportTypes="pdfp,excel,csv" 
					stateAttr="restore"  view="org.jmesa.custom.RegistrazioniFilterColumnTotal" >
					<jmesa:htmlTable>
						<jmesa:htmlRow>
							<c:if test="${enum eq 'ANAGRAFE'}">	
                                <jmesa:htmlColumn property="anagrafe.descrizioneRichiedente" titleKey="form.registrazioniFilter.anagrafe" />
								<jmesa:htmlColumn property="anno" titleKey="form.registrazioniFilter.anno" />
							</c:if>
							<c:if test="${enum eq 'CAUSALE'}">	
                               	<jmesa:htmlColumn property="registrazioniCausali.descrizione" titleKey="form.registrazioniFilter.registrazioniCausali" />
							</c:if>
							<c:if test="${enum eq 'CONTO'}">							
								<jmesa:htmlColumn property="conti.descrizioneConto" titleKey="form.registrazioniFilter.conti" />
							</c:if>
							<c:if test="${enum eq 'AMMINISTRAZIONE'}">							
								<jmesa:htmlColumn property="amministrazioni.amministrazione" titleKey="form.registrazioniFilter.amministrazioni" />
							</c:if>
                            <c:if test="${enum eq 'MERCATO'}">							
								<jmesa:htmlColumn property="mercati.descrizione" titleKey="form.registrazioniFilter.mercati" />
								<c:if test="${not empty registrazioniFilterList}">
	                                <jmesa:htmlColumn property="mercatiUso.descrizione" titleKey="form.registrazioniFilter.mercatouso"  />
	                                <jmesa:htmlColumn property="posteggio.codiceposteggio" titleKey="form.registrazioniFilter.posteggio"  />
                                </c:if>
							</c:if>
							<jmesa:htmlColumn property="emesso" style="text-align:right;" headerStyle="text-align:right;" titleKey="form.registrazioniFilter.emesso" >
								<fmt:formatNumber minFractionDigits="2" value="${registrazioniFilter_var.emesso}"/>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="incassato" style="text-align:right;" headerStyle="text-align:right;"  titleKey="form.registrazioniFilter.incassato" >
								<fmt:formatNumber minFractionDigits="2" value="${registrazioniFilter_var.incassato}"/>
							</jmesa:htmlColumn>
							<jmesa:htmlColumn property="saldo"  style="text-align:right;" headerStyle="text-align:right;"  titleKey="form.registrazioniFilter.saldo" >
							<fmt:formatNumber minFractionDigits="2" value="${registrazioniFilter_var.saldo}"/>
							</jmesa:htmlColumn>
							<%--se cambiano le etichette label.edit.registrazioni e  label.edit.scadenze bisogna modificarle anche in baseController per l'export--%>
                            <jmesa:htmlColumn property="id.codice" titleKey="label.edit.registrazioni" sortable="false" filterable="false">
								<a  href="javascript:historySet('${_urlback}','../registrazioni/search.htm?registrazioniCausali.id.codice=${registrazioniFilter.registrazioniCausali.id.codice }&conti.id.codice=${registrazioniFilter.conti.id.codice}&dataInizio=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datainizio}"/>&dataFine=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datafine}"/>&anagrafe.id.codice=${registrazioniFilter.anagrafe.id.codice}&mercati.id.codice=${registrazioniFilter.mercati.id.codice}&mercatiUso.id.codice=${registrazioniFilter.mercatiUso.id.codice}&alberoproc.id.codice=${registrazioniFilter.alberoproc.id.codice}','')"
                                    title="<fmt:message key="label.search" /> ${spuntisti_var.id.codice}">
                                    <img src="../images/search.gif" alt="<spring:message code="label.search" />" />  
                                </a>
							</jmesa:htmlColumn>	
                             <jmesa:htmlColumn property="id1.codice" titleKey="label.edit.scadenze" sortable="false" filterable="false">
								<a  href="javascript:historySet('${_urlback}','../registrazioniinout/listScadenze.htm?filter.registrazioniCausali.id.codice=${registrazioniFilter.registrazioniCausali.id.codice }&filter.conti.id.codice=${registrazioniFilter.conti.id.codice}&filter.dataInizio=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datainizio}"/>&filter.dataFine=<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${datafine}"/>&filter.anagrafe.id.codice=${registrazioniFilter.anagrafe.id.codice}&filter.mercati.id.codice=${registrazioniFilter.mercati.id.codice}&filter.mercatiUso.id.codice=${registrazioniFilter.mercatiUso.id.codice}&filter.alberoproc.id.codice=${registrazioniFilter.alberoproc.id.codice}','')"
                                    title="<fmt:message key="label.search" /> ">
                                    <img src="../images/search.gif" alt="<spring:message code="label.search" />" />  
								</a>
							</jmesa:htmlColumn>	
                        </jmesa:htmlRow>
					</jmesa:htmlTable>
				</jmesa:springTableFacade>
			</form>
			
			<script type="text/javascript">
				var _jmesaUrl='search.htm?';
				var _captionTab='<fmt:message key="form.registrazioniFilter.title.list" />';
			</script>
			
		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.newsearch" /></a></li>
			</ul>
		</div>
	</body>
</html>