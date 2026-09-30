<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
	
	<br class="break" />
	<fieldset>
		<legend><label for="id_link_istanzeprocedimenti_configurati"><fmt:message key="label.elenco_normative_configurate" /></label></legend>
	<div>
		<div class="jmesa" >
        		<table border="1"  cellpadding="0"  cellspacing="0"  class="table">
					<thead>
					<tr class="header">
						<td><fmt:message key="label.descrizione"/></td>
						<td><fmt:message key="label.riferimento"/></td>
						<td><fmt:message key="label.normativa"/></td>
						<td><fmt:message key="label.categoria"/></td>
						<td colspan="2" width="5%"><fmt:message key="label.azioni"/></td>
					</tr>
					</thead>
               		 <%
			    	int i=0;
			    	%>
					<tbody class="tbody">
					    <c:if test="${fn:length(inventarioprocLeggis)>0}">
						<c:forEach items="${inventarioprocLeggis}" var="inventarioproc_leggi" varStatus="indice_inventarioproc_leggi">
						<tr class="<%=(i%2)==0?"odd":"even"%>">
							<td>${inventarioproc_leggi.leggi.leDescrizione}</td>
	                       	<td>
	                       		${inventarioproc_leggi.riferimenti}
	                       	</td>
	                       	<td>${inventarioproc_leggi.leggi.normative.normativa}</td>
							<td>${inventarioproc_leggi.leggi.leggitipi.ltDescrizione}</td>
							<td>
								<a class="dettaglioColumn" href="javascript:tabRiferimenti('riferimenti${inventarioproc_leggi.leggi.id.codice}',${inventarioproc_leggi.id.codice})"  title="<fmt:message key="label.edit.record" />&nbsp;${inventarioproc_leggi.leggi.leDescrizione}">
									<label><fmt:message key="label.edit.record.image" /></label>
                            	</a>
                            </td>
                            <td >	
							  <a class="eliminaRiga" href="javascript:deleteNormativa(${inventarioproc_leggi.id.codice},'<fmt:message key="javascript.confirm.delete" />')"  title="<fmt:message key="label.elimina" />&nbsp;${inventarioproc_leggi.leggi.leDescrizione}">
								<label><fmt:message key="label.azioni" /></label>
                              </a>
	                        </td>
	                    </tr>
	                    <%i++;%>
	                    </c:forEach>
	                    </c:if>
						<c:if test="${fn:length(inventarioprocLeggis)==0}">
						<tr  class="<%=(i%2)==0?"odd":"even"%>">
							<td align="center" colspan="6">
								<fmt:message key="label.record_non_presenti"/>
	                        </td>
	                    </tr>
	                    </c:if>
	                </tbody>
				</table>
 		</div>
			
	</div>	
	</fieldset>
