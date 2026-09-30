<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	<div>
		<div class="jmesa" >
        		<table border="0"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr class="header">
						<td width="5%"><fmt:message key="label.numeroistanza" /></td>
						<td><fmt:message key="label.richiedente"/></td>
						<td width="10%"><fmt:message key="label.azioni"/></td>
					</tr>
					</thead>
					<tbody class="tbody">
					 <c:if test="${not empty listaIstanzecollegate}">
						 <c:forEach items="${listaIstanzecollegate}" var="istanza_col" varStatus="indice">
							<c:if test="${istanza_col.istanzaDacollegare!=null}">
								<c:set var="trStyle" value="odd"/>
								<c:if test="${(indice.index mod 2) eq 0}">
									<c:set var="trStyle">even</c:set>
								</c:if>		
								<tr class="${trStyle}">
								<td>${istanza_col.istanzaDacollegare.numeroistanza}</td>
								<td>${istanza_col.istanzaDacollegare.transientRichiedenteQualitaAzienda}</td>
								<td><a class="addColumn" href="../istanzerichiedenti/copiaSoggettiCollegati.htm?codiceIstanzaDestinataria=${istanzaDestinatario.id.codice}&codiceIstanzaSorgente=${istanza_col.istanzaDacollegare.id.codice}" title="<fmt:message key="label.copia_soggetti_collegati"/>">
								    	 <label><fmt:message key="label.copia_soggetti_collegati" /></label>
								     </a>
								</td>
									
								</tr>
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${empty listaIstanzecollegate}">
					        <tr class="even">
								<td colspan="3" style="text-align: center;"><fmt:message key="label.non_istanze_collegatate"/></td>
							</tr>
					</c:if>
			        </tbody>
				</table>
 		</div>
	</div>