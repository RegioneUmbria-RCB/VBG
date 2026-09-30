<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Ateco"%>
	<br class="break" />
	<div >
	<%
		String displayPreferenze = "display:none;";
		String stylePreferenze = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO)).equals("1")) {
		    displayPreferenze = "";
   			stylePreferenze="sezioneDatiMeno";
		} else {
		    displayPreferenze = "display:none;";
    		stylePreferenze="sezioneDatiPiu";
		}
		
		String styleDescrizione = "display:none;";
		//gestisce la visualizzazione della colonna descrizione
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE)).equals("1")) {
		  	styleDescrizione="";
		} else {		    
    		styleDescrizione="display:none;";
		}		
	%>
	<a class="<%=stylePreferenze %>" 
		id="id_link_preferenze" 
		href="javascript:showHidePanel('preferenzeColonne_id', 'id_link_preferenze', '<%= WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO %>', '${pageContext.request.contextPath}/images/','div');"	
		title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.modifica_colonne_da_visualizzare"/>">
		<label for="id_link_preferenze"><fmt:message key="label.modifica_colonne_da_visualizzare"/></label>
	</a>
	<div id="preferenzeColonne_id" style="<%= displayPreferenze%>">
		<fieldset>
			<div><fmt:message key="label.spiegazione_salvataggio_preferenze" />
				<div>				
					<input type="checkbox" id="CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_id" 
						onclick="salvaPreferenza('<%=WebConstants.CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE%>',this)" ${CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_CHECKED}/>
						<label for="CONF_UTENTE_ALBEROPROC_LIST_ATECO_DESCRIZIONE_id"><fmt:message key="label.descrizione" /></label>
				</div>
				<div id="functions">
					<ul>
						<li><a href="javascript:document.location.reload();"><fmt:message key="button.save" /></a></li>
					</ul>
				</div>
				&nbsp;
			</div>		
		</fieldset>
	</div>						
		<fieldset><legend><b><fmt:message key="alberoproc.label.lista_ateco_associati" /></b></legend>
			<div class="jmesa">
				<table border="0"  cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="5%"><fmt:message key="label.codice" /> </td>
							<td width="20%" ><fmt:message key="label.titolo" /></td>
							<td width="65%" align="center" style="<%= styleDescrizione%>"><fmt:message key="label.descrizione" /></td>
			                <td width="2%" align="center"><fmt:message key="label.elimina" /></td>
			            </tr>
					</thead>
					<tbody class="tbody">
						<%int i=1;%>
						<c:forEach items="${alberoproc.alberoprocAtecos}" var="ateco_var">
							<tr class="<%=(i%2)==0?"odd":"even"%>" valign="top">
						      <td>						
								${ateco_var.ateco.codice}
							  </td>
							  <td>
						  		${ateco_var.ateco.titolo}
		    	              </td>		    	              
				           	  <td style="<%= styleDescrizione%>">
				           	  <%
				           	  	AlberoprocAteco apAteco = (AlberoprocAteco)pageContext.getAttribute("ateco_var");
				           	  	Ateco descrizioneAteco= apAteco.getAteco();
				           	    if(descrizioneAteco.getDescrizione() != null){
				           	  		out.print(descrizioneAteco.getDescrizione().replaceAll("\\r|\\n","<br />"));
				           	    }				           	  
				           	  %>
		    	              </td>		    	              
				              <td>					                  	 
				               	<a class="eliminaRiga" style="float: none;" href="javascript:eliminaAteco(${ateco_var.ateco.id},${alberoproc.id.codice})" title="<fmt:message key="label.elimina" /> ${ateco_var.ateco.id}">
				              	 <label><fmt:message key="label.elimina.image" /></label>
					            </a>							            
				              </td>
							</tr>
							<%i++; %>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</fieldset>		
	</div>	
	
	
	