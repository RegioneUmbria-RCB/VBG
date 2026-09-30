<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.IASnapshotCampoHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.IASnapshotViewerHelper"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.helper.IASnapshotValoriHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.lista_storico_attivita.title" /></title>
</head>
<body>

<style>
	.indiceSelezionato{
		text-decoration: underline;
		font-weight: bolder;
		font-size: 1.3em;
	}
	.linkIndice{
		font-style: italic;
	}
	.contenutoCampo{
		font-weight: bolder;
		text-align: center;		
	}
	.etichetteCampi{
		text-align: left; 
		vertical-align: top;
		font-style: italic;
	}
	.intestazioneScheda{
		padding: 10px;
		text-align: left; 
		vertical-align: top;
	}
	
	.etichettaAnagrafica{
		text-align: left; 
		vertical-align: top;
		font-style: italic;
		text-transform: uppercase;
	}
	
</style>
	<span class="titoloPagina"><fmt:message key="label.lista_storico_attivita.title" /></span>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../iattivita/viewStoricoSnapshot" />
	</jsp:include>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<div class="parametriDiv">
		<div class="etichetta">
				<div><fmt:message key="label.iattivita" />:</div>
				<div><fmt:message key="label.ultima_istanza" />:</div>		
			</div>		
			<div class="parametro">       		 	
				<div>${iAttivita.denominazione}</div>
				<div>${iAttivita.istanza.numeroistanza}</div>
			</div>
		</div>
		<br class="clear"/>
		<form name="iattivitaSnapshotForm" action="#">

		<table border="0" cellspacing="2" cellpadding="4" style="border: 1px dotted">
		<colgroup>
				<col span="2" style="background-color: #f9f9f9;">
		    	<col span="${fn:length(helper.listaSnapshot)}" style="background-color: #f0f0f0;">
		  	</colgroup>
		<colgroup>
		<thead>		
		<tr class="titoloSezione">
			<td rowspan="10" class="intestazioneScheda">
				<a
				id="linkAnagrafe_id" 
				href="javascript:void 0"
				onclick="showHidePanelBase('tbody', 'linkAnagrafe_id', '', '/backend/images/', 'tr', false);"
				class="sezioneDatiMeno">
					<label for="linkAnagrafe_id"><fmt:message key="label.scheda_anagrafica" /></label>
				</a>
			</td>
		</tr>
		<%
			IASnapshotViewerHelper helperV=(IASnapshotViewerHelper)request.getAttribute("helper");
		%>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.id" /></td>
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("id"));
			%>
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.data" /></td>
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("data"));
			%>			
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.denominazione" /></td>			
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("denominazione"));
			%>
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.istanza" /></td>			
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("istanza"));
			%>
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.intervento" /></td>			
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("descrizioneIntervento"));
			%>
		</tr>
		
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.tipologia_attivita" /></td>
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("tipologiaAttivita"));
			%>				
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.attiva" /></td>		
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("attiva"));
			%>	
		</tr>
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.operante" /></td>
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("operante"));
			%>				
		</tr>
		
		<tr id="tbody">
			<td class="etichettaAnagrafica"><fmt:message key="label.codice_osservatorio" /></td>
			<%
				out.print(helperV.scriviValoriSnapshotSchedaAnagrafe("codiceOsservatorio"));
			%>				
		</tr>
		</thead>
		
		<c:forEach items="${helper.listaSchede}" var="s" varStatus="idxs">
			<c:set var="indiceScheda">${idxs.index}</c:set>	
			<%
				int indiceSchedaVar =  Integer.valueOf((String)pageContext.getAttribute("indiceScheda"));
			%>
			<tr class="titoloSezione"><td colspan="${fn:length(helper.listaSnapshot)+2}" style="font-size: 2px;"></td></tr>
			<tbody align="center">
			
			<tr class="titoloSezione">
				<c:set var="rspan" scope="page">${fn:length(s.listaCampi)}</c:set>
				<%int rspanVal = Integer.valueOf((String)pageContext.getAttribute("rspan"));
				rspanVal+=1;
				%>
				<c:if test="${s.maxIndice > 0}">
				<% 
					rspanVal+=1;
				%>
				</c:if>
				<c:if test="${s.maxIndiceMolteplicita > 0}">
				<% 
					rspanVal+=1;
				%>
				</c:if>
				
				<td rowspan="<%=rspanVal %>" class="intestazioneScheda">
					<a id="linkScheda_id_${idxs.index}" 
						href="javascript: void 0" 
						onclick="showHidePanelBase('tbody${idxs.index}', 'linkScheda_id_${idxs.index}', '', '/backend/images/', 'tr', false);" 
						title="${s.toolTip }"
						class="sezioneDatiMeno">
							<label for="linkScheda_id_${idxs.index}">${s.descrizione}<br />(${s.toolTip})</label></a>
				</td>					
			</tr>
			<c:if test="${s.maxIndice > 0}">
				<tr id="tbody${idxs.index}">
					<td colspan="${fn:length(helper.listaSnapshot)+1}"><fmt:message key="label.indice_delle_schede" /> 
					<c:set var="indice" scope="page">${s.maxIndice}</c:set>
								<%
									int indiceVar =  Integer.valueOf((String)pageContext.getAttribute("indice"));
									out.print("&lt;");
									for(int i=0;i<=indiceVar;i++){
									    String classStyle="linkIndice";
									    if(i==0){
											classStyle="indiceSelezionato";										
									    }
										%>
										    <a 
										    href="javascript:void 0" onclick="popolaCampiIndiceMolteplicita(this, 'id_idx_<%=indiceSchedaVar %>_<%= i %>','<%= i %>','0','${idxs.index}')" 
										    id="id_idx_<%=  indiceSchedaVar %>_<%=  i %>" 
										    class="<%= classStyle%> linkIndice"><%= i%></a>
										   	<% 
									    if(i<indiceVar){
											out.print(", ");
									    }
									}
									out.print("&gt;");
								%>
							
					</a></td>
				</tr>
			</c:if>
			<c:forEach items="${s.listaCampi}" var="c" varStatus="idxc">
			<tr id="tbody${idxs.index}">
				<td title="${c.toolTip}" class="etichetteCampi">
				${c.nomeCampo}
				<c:if test="${not empty c.descrizione}">
					<init:help idHelp="help_c${c.codice}_${idxs.index}" text="${c.descrizione}"/>
				</c:if>
				</td><%
					IASnapshotCampoHelper campoH = (IASnapshotCampoHelper)pageContext.getAttribute("c");
					out.print(helperV.scriviValoriSnapshotScheda(campoH.getCodice()));
				%>
			</tr>
			</c:forEach>
				<c:if test="${s.maxIndiceMolteplicita>0}">
					<tr id="tbody${idxs.index}">
						<td colspan="${fn:length(helper.listaSnapshot)+1}"><fmt:message key="label.indice_righe_raggruppate" /> 
								<c:set var="indiceMolteplicita" scope="page">${s.maxIndiceMolteplicita}</c:set>
								<%
									int indiceMolteplicitaVar =  Integer.valueOf((String)pageContext.getAttribute("indiceMolteplicita"));
									out.print("&lt;");
									for(int i=0;i<=indiceMolteplicitaVar;i++){
									    String classStyle="linkIndice";
									    if(i==0){
											classStyle="indiceSelezionato";										
									    }
									   	%><a 
									    href="javascript:void 0" onclick="popolaCampiIndiceMolteplicita(this, 'id_idxm_<%=indiceSchedaVar %>_<%= i %>','0','<%= i %>','${idxs.index}')" 
									    id="id_idxm_<%=  indiceSchedaVar %>_<%=  i %>" 
									    class="<%= classStyle%> linkIndice"><%= i%></a><% 
									    if(i<indiceMolteplicitaVar){
											out.print(", ");
									    }
									}
									out.print("&gt;");
								%>
							
						</a></td>
					</tr>
				</c:if>
			</tbody>
			
			
			
		</c:forEach>
		</table>
		</form>
		<%
		
		List<IASnapshotValoriHelper> listSnaps = helperV.getListaSnapshot();
		for(IASnapshotValoriHelper snap: listSnaps){
		    Map<String,String> mappaValori=snap.getMapValori();
		    for (Map.Entry<String, String> mv : mappaValori.entrySet())
		    {
			%><div id="val_ias_id_<%= mv.getKey() %>" style="display: none;" title="val_ias_id_<%= mv.getKey() %>"><%= mv.getValue() %></div><%
		    }
		}
		%>

			
		<script type="text/javascript">

		
		
		function popolaCampiIndiceMolteplicita(hrefLinkObj, idLink, indice, indiceMolteplicita, scheda){
			
			disableFunctions();
			
			jQuery(hrefLinkObj).parent().find(".linkIndice").removeClass("indiceSelezionato");
			jQuery(hrefLinkObj).addClass("indiceSelezionato");
			
			var elsToChange = jQuery("tr#tbody"+scheda+" > td.contenutoCampo");
		      for( i = 0; i< elsToChange.length; i++ ){
				var idEl = elsToChange[i].id;
				var elemento = jQuery(elsToChange[i]);
				var decodificaCampoSnapshot = idEl.replace('val_campo_id_','').split("_");
				var campoId 	= 	decodificaCampoSnapshot[0];
				var snapShotId  = 	decodificaCampoSnapshot[1];
				var newVal = jQuery("#val_ias_id_"+snapShotId+"_"+campoId+"_"+ indice +"_"+indiceMolteplicita).text();				
				elemento.html(newVal); 				
		      }
		      setTimeout('enableFunctions()',200);
		}
		
		
		
		function dettaglioIstanza(codiceIstanza, software){
			
			historySet('${_urlback}', '../istanze/view.htm?codice='+codiceIstanza+'&software='+software, '');
		}

		// mod_codice_osservatorio
		jQuery(function() {
		    jQuery('.mod_codice_osservatorio').change(function() {
		    	var idsnapshot = jQuery( this ).data('idsnapshot');
		    	var valore = jQuery( this ).val();
		    	if(/^([0-9]*)$/.test(valore)|| valore===''){
		    		aggiornaSnapshot(jQuery( this ), idsnapshot, valore);
		    	}else{
		    		alert("Attenzione il valore specificato [" + valore + "] non è ammesso.");
		    	}
		    });
		});
		
		
		function aggiornaSnapshot(jqObj, idsnapshot, valore){
			
			jQuery.ajax({
				  type: "POST",
				  url: '../iattivita/ajaxUpdateSnapshot.htm',
				  context: document.body,
				  cache: false,
				  data: "idsnapshot="+idsnapshot+"&valore="+valore,				  
				  dataType: "text",
				  success: function(data) {
					alert("Dato aggiornato!");
				  },
				  error: function(jqXHR, textStatus, errorThrown){
						console.error("Errore nella chiamata al controllo su sessione condivisa:" + jqXHR.responseText);
						alert("Si è verificato un errore  nell' aggiornamento del dato!");
						document.location.reload();
				}
			});
			
		}
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>
