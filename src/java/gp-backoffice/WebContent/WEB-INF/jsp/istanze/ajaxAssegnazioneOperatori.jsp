<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<div class="vbg-form">
	<fieldset>
	<c:choose>
		<c:when test="${param.tipo eq 'responsabile'}">
			<legend><span class="titoloPagina">Assegnazione responsabile procedimento</span></legend>
		</c:when>
		<c:otherwise>
			<legend><span class="titoloPagina">Assegnazione responsabile istruttori</span></legend>
		</c:otherwise>
	</c:choose>
    	<div class="clear" ></div>
    	<input type="hidden" name="codiceIstanza" value="${param.codiceIstanza}" />
		<input type="hidden" name="respSorteggiato" value="${respSorteggiato}" />
		<input type="hidden" name="tipo" value="${param.tipo}" />			    	
    	<table style="width: 90%" class="vbg-table">
    		<thead>
				<tr class="header">			    		
		    		<th>Operatore</th>
		    		<th>N° max istanze</th>
		    		<c:if test="${assegnaGruppo eq 'S'}">
		    			<th>% carico</th>
		    		</c:if>
		    		
		    		<c:if test="${canModifyAssegnazione eq true }">
		    			<th>Azioni</th>
		    		</c:if>
		    	</tr>
    		</thead>
	    	<tbody>
		    	<c:forEach items="${ listaResponsabiliOrdinata}" var="rhb" varStatus="status">		    	
			    	<tr>			    		
			    		<td>${rhb.nominativo}</td>
			    		<td>${rhb.capienza}</td>
			    		<c:if test="${assegnaGruppo eq 'S' }">
			    			<td>${rhb.percentualeAssegnata}</td>
			    		</c:if>	
			    		<c:if test="${canModifyAssegnazione eq true }">
			    			<td>					    	
					    		<c:choose>
					    			<c:when test="${param.tipo eq 'responsabile'}">
					    				<c:if test="${ not (rhb.codiceResponsabile eq rhb.istanza.responsabileProcedimento.id.codice)}">
							    			<a href="javascript: void 0" onclick="assegnaOperatore('${param.tipo}', '${rhb.codiceResponsabile}',true)">Assegna</a>				    		
							    		</c:if>
					    			</c:when>
					    			<c:otherwise>
					    				<c:if test="${not ( rhb.codiceResponsabile eq rhb.istanza.istruttore.id.codice)}">
							    			<a href="javascript: void 0" onclick="assegnaOperatore('${param.tipo}', '${rhb.codiceResponsabile}',true)">Assegna</a>				    		
							    		</c:if>
					    			</c:otherwise>			    		
					    		</c:choose>
					    		<c:if test="${assegnaGruppo eq 'S'}">
					    			<a href="javascript: void 0" onclick="dettaglio('${rhb.codiceResponsabile}','${rhb.idTestata }','${param.tipo}')">Dettaglio</a>
					    		</c:if>
					    		
							</td>
			    		</c:if>
				    </tr>	
		    	</c:forEach>
	    	</tbody>
    	</table>
   	</fieldset>
   	<div class="form-button">
   		<c:if test="${mostraBottoneAssegna }">
   			<a class="btn btn-primary" href="javascript: void 0" onclick="chiusuraGruppo('${ listaResponsabiliOrdinata[0].idTestata}')">Chiusura gruppo</a>
		</c:if>								
		<!-- <a class="btn btn-primary" href="javascript: void 0" onclick="assegnaOperatore('${param.tipo}', '${respSorteggiato}', false)">Assegna</a> -->					
		<a class="btn btn-secondary" href="javascript:chiudiAssegnazione()"><fmt:message key="button.close" /></a>		
	</div>	
</div>
<vbg-modal id="modal-elenco-istanze">
	<div slot='body' id='popup_elenco_istanza'>
		<h1><fmt:message key="label.elenco_istanze_assegnate"/></h1>
		<table id="elenco_istanze" class="vbg-table">
			<thead>
				<tr><th>Numero istanza</th></tr>
			</thead>
			<tbody>					
			</tbody>
		</table>			
	</div>
	<div slot='footer'>		
		<div class="btn btn-secondary" id="closeButtonModalRicarica"><a href="javascript:chiudiModal(this)"><fmt:message key="button.close"/></a></div>            
	</div>		
	</vbg-modal>	
</vbg-modal>


