<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.backoffice.web.AlberoprocController"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocProtocollo"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>


<%-- 
<spring-form:form commandName="mercatid"name="inviodati_">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="mercatid" />	
	</jsp:include>
--%>	
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatid/listconfigurazione" />
		
	</jsp:include>
	
<div id="subcontent">
	<table width="100%" cellpadding="0">
			<%-- PRIMA RIGA START --%>
			<c:if test="${mercatid.disabilitato eq true}">
			<<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio_disable"></c:set>
			</c:if>
			<c:if test="${mercatid.disabilitato eq false || mercatid.disabilitato == null}">
				<c:set scope="page" var="style_etichetta_posteggio" value="etichetta_posteggio"></c:set>
			</c:if>
			<tr>
			<td class="codice_posteggio" style="padding: 0px;" valign="top"  ><input id="posteggi_checkbox_id${indice}" type="checkbox"
				    value="${mercatid.id.codice}" name="codiceposteggi" onclick="isposteggiselezionati(${sizeListaPosteggi});"/> 
				    <a href="javascript:void 0" onclick="doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid%2Fview.htm%3fcodice%3d${mercatid.id.codice}','')" title="<fmt:message key="label.dettaglio_posteggio" />">
				    <label class="${style_etichetta_posteggio}">${mercatid.codiceposteggio}</label> 
				    </a> 
               
			<div  style="display: none;" class="salvataggio"></div>    
            </td>
            <tr>
             	<td>
             		Lunghezza
             		<input id="lunghezza_id_${mercatid.id.codice}" type="text" size="3" value="${mercatid.lunghezza}" onchange="changeValue(this,'${mercatid.id.codice}','lunghezza')" ></input>
             		Larghezza
             		<input id="larghezza_id_${mercatid.id.codice}" type="text" size="3" value="${mercatid.larghezza}" onchange="changeValue(this,'${mercatid.id.codice}','larghezza')"></input>
             		Superficie
             		<input id="superficie_id_${mercatid.id.codice}" type="text" size="3" value="${mercatid.superficie}" onchange="changeValue(this,'${mercatid.id.codice}','superficie')"></input>
             	</td>
             </tr>
             <tr> 
                <td>
				<div class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">	
								<td width="30%"><fmt:message key="label.descrizione" /></td>
								<td width="20%"><fmt:message key="label.giorno" /></td>
								<td><fmt:message key="label.usa_mq_posteggio_abbr" /></td>
								<td><fmt:message key="label.fattore_moltiplicativo" /></td>
								<td><fmt:message key="label.tariffa" /></td>
								<td><fmt:message key="label.data_inizio" /></td>
								<td><fmt:message key="label.data_fine" /></td>
								<td><fmt:message key="label.azioni" /></td>
							</tr>
						</thead>
						<tbody>
						<c:if test="${not empty listServizi}">
						<c:forEach items="${listServizi}" var="entry" varStatus="idx">
						<tr class="${(idx.index % 2 == 0)? 'odd':'even' }">
						        <td>${entry.descrizione}</td>
						        <td>${entry.mercatiUso.descrizione}</td>
						        
						        <td align="center">
    		                   	<c:if test="${entry.usaMqPosteggio eq false}"><fmt:message key="label.no" /></c:if>
								<c:if test="${entry.usaMqPosteggio eq true}"><fmt:message key="label.si" /></c:if>
			              	 	</td>
    		                  	<td><fmt:formatNumber value="${entry.fattoreMoltiplicativo}" minFractionDigits="2"></fmt:formatNumber></td>
    		                  	<td><fmt:formatNumber value="${entry.tariffa}" minFractionDigits="2"></fmt:formatNumber></td>
						        <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${entry.dataInizio}"/></td>
						        <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${entry.dataFine}"/></td>
						        <td>
	    		                  	<a class="dettaglioColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatidlivelloservizio/view.htm?codice=${entry.codice}','')" title="<fmt:message key="label.edit.record" />${formula_var.id.codice}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
    		                  	</td>
						</tr>    	 
						</c:forEach>
						</c:if>				
						<c:if test="${empty listServizi}">
							<tr class="even">
								<td colspan="7"><fmt:message key="html.statusbar.noResultsFound" /></td>
							</tr>
						</c:if>
						<tr>
							<td colspan="8">
								<a class="addColumn" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatidlivelloservizio/create.htm?codiceposteggio=${mercatid.id.codice}','')" title="<fmt:message key="label.edit.record" />${formula_var.id.codice}">
										<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</td>
						</tr>
					
						</tbody>
					</table>
						
				</div>
				</td>
             </tr>
  
	  </table>
	  

	  
 </div>
<%-- 
</spring-form:form>	
--%>
