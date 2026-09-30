<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.MercatipresenzeT"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="form.gestionepresenze.title" /></title>
</head>
<body>
	<script type="text/javascript">
		function segnaPresenzaConcessionario(codiceMercato,usoMercato,giornoMercato,idPosteggio,id,codiceAnagrafe,idAut,anchor){	
			var url = "segnaPresenzaConcessionario.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codice="+id+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+'#'+anchor;
			doHref(url);
		}
		function segnaPresenzaSpuntista(codiceMercato,usoMercato,giornoMercato,idPosteggio,codiceAnagrafe,idAut,catMerc,anchor){
			if(idAut){
				var url = "segnaPresenzaSpuntista.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+"&catMerc="+catMerc+'#'+anchor;
				doHref(url);
			}else{
				return ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,idPosteggio,'',codiceAnagrafe,'spuntista');
			}
		}
		function segnaPresenzaSpuntistaNoPosteggio(codiceMercato,usoMercato,giornoMercato,codiceAnagrafe,idAut,catMerc,anchor){
			if(idAut){
				var url = "segnaPresenzaSpuntistaNoPosteggio.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codiceAnagrafe="+codiceAnagrafe+"&idAut="+idAut+"&catMerc="+catMerc+'#'+anchor;
				doHref(url);
			}else{
				return ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,'','',codiceAnagrafe,'spuntnop');
			}
		}
		function ricercaAutorizzazione(codiceMercato,usoMercato,giornoMercato,idPosteggio,id,codiceAnagrafe,occupante){
			var url = "${pageContext.request.contextPath}/autorizzazioni/listDaGestionePresenze.htm?codiceAnagrafe="+codiceAnagrafe+"&codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&idPosteggio="+idPosteggio+"&codice="+id+"&occupante="+occupante;
			doHref(url);
		}
	</script>
	<span class="titoloPagina"><fmt:message key="form.gestionepresenze.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    <jsp:param name="path" value="../gestionepresenze/list" />
	</jsp:include>
	<div id="subcontent">
	<%
		String flagAssenza="display:none;";
		String flagAssenza1="display:inline;";
	%>
	<%-- 
		CODICE CHE PERMETTE DI VISUALIZZARE I MESSAGGI DI ERRORE O AVVENUTA CHIUSURA DEL GIORNO DI MERCATO 
     	IL CODICE é STATO PRESO DALLA JSP DISPAYGLOBALMESSAGE 
     --%>
    <c:if test="${param.chiusura=='ko'}">
    <div id="error_msg" class="error_header" >
        <fmt:message key="label.storicizzazionegiorno.noneseguita"/>
    </div>
    <script type="text/javascript">
        $('error_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>  
    </c:if>
    <c:if test="${param.chiusura=='ok'}">
    <div id="status_msg" class="success_header" >
    	<fmt:message key="label.storicizzazionegiorno.eseguita"/>
    </div>
    <script type="text/javascript" >
        $('status_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>
    </c:if>
    <c:if test="${param.apertura=='ko'}">
    <div id="error_msg" class="error_header" >
        <fmt:message key="label.gestionepresenze.apertura.noneseguita"/>
    </div>
    <script type="text/javascript">
        $('error_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>  
    </c:if>
    <c:if test="${param.apertura=='ok'}">
    <div id="status_msg" class="success_header" >
        <fmt:message key="label.gestionepresenze.apertura.eseguita"/>
    </div>
    <script type="text/javascript" >
        $('status_msg').pulsate({ pulses: 2, duration: 1.0 });
    </script>
    </c:if>
	<%-- END CODICE --%>	
	<c:if test="${giornoMercato.flagPresenze eq true}">
		<span class="parametri"><label><fmt:message key="label.storicizzazionegiorno.giornochiuso"/></label></span><br/>
	</c:if>
    <span class="parametri"><fmt:message key="form.gestionepresenze.mercato" />:<label> ${mercato.descrizione}</label></span>	
	<span class="parametri"><fmt:message key="form.gestionepresenze.mercatiUso" />:<label> ${uso.descrizione}</label></span>
	<span class="parametri"><fmt:message key="form.gestionepresenze.data" />:<label> ${data}</label></span><br />
	<c:if test="${not empty listaPosteggi}">
	<form name="posteggiForm" action="list.htm">
    <div class="jmesa">
	<table class="table">
	<thead>
		<tr class="header">
			<td>
				<fmt:message key="form.gestionepresenze.posteggio" />
				<span>
					<c:if test="${not empty visPosteggiLiberi && visPosteggiLiberi eq true}">
					<input type="checkbox" checked="checked" onclick="javascript:doHref('list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&visPosteggiLiberi=false','')" title="<fmt:message key="form.gestionepresenze.vissololiberi" />"/>
					</c:if>
					<c:if test="${not empty visPosteggiLiberi && visPosteggiLiberi ne true}">
					<input type="checkbox" onclick="javascript:doHref('list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&visPosteggiLiberi=true','')" title="<fmt:message key="form.gestionepresenze.vissololiberi" />"/>			
					</c:if>
				</span>
			</td>
			<td><fmt:message key="form.gestionepresenze.concessionario" /></td>
            <td><fmt:message key="form.gestionepresenze.assenzagiustificata" /></td>
			<td><fmt:message key="form.gestionepresenze.spuntista" /></td>
			<td><fmt:message key="form.gestionepresenze.proprietario" /></td>
		</tr>
	</thead>
	<tbody>
	<%int i=0;%>
	<fmt:message key="label.ricerca_spuntista" var="ricerca_spuntista_title" />				
	<c:forEach items="${listaPosteggi}" var="var_presenza" varStatus="varIndex">
		<c:if test="${not empty var_presenza.posteggio.id.codice}">
		<c:if test="${visPosteggiLiberi ne true or (empty var_presenza.occupante.id.codice)}">
		<tr id="anchor${varIndex.index}" class="<%=(i%2)==0?"odd":"even"%>">
			<%--colonna numero posteggio e dettaglio --%>
			<td>
			<c:if test="${empty var_presenza.occupante.id.codice}">
				<label style="font-size: large" for="codiceposteggio${varIndex.index}" onclick="$('posteggio_dettaglio${varIndex.index}').appear()" onmouseout="$('posteggio_dettaglio${varIndex.index}').style.display='none'" title="<fmt:message key="form.gestionepresenze.posteggio.libero" />">${var_presenza.posteggio.codiceposteggio}</label>
			</c:if>
			<c:if test="${not empty var_presenza.occupante.id.codice}">
				<label style="font-size: large; color: red;" for="codiceposteggio${varIndex.index}" onclick="$('posteggio_dettaglio${varIndex.index}').appear()" onmouseout="$('posteggio_dettaglio${varIndex.index}').style.display='none'" title="<fmt:message key="form.gestionepresenze.posteggio.occupato" />">${var_presenza.posteggio.codiceposteggio}</label>
			</c:if>
			<span id="posteggio_dettaglio${varIndex.index}" class="posteggi_dettaglio" style="display: none; text-align: left;">
			    <div class="posteggi_dettaglio_header"><label>&nbsp;<fmt:message key="form.gestionepresenze.posteggio" />&nbsp;${var_presenza.posteggio.codiceposteggio}</label></div><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.tipo" />: ${var_presenza.posteggio.tipoSpazio}</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.larghezza" />: ${var_presenza.posteggio.larghezza} m</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.lunghezza" />: ${var_presenza.posteggio.lunghezza} m</label><br />
				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.superficie" />: ${var_presenza.posteggio.superficie} m<small><sup>2</sup></small></label><br />
  				<label><fmt:message key="form.gestionepresenze.posteggio.dettaglio.note" />: ${var_presenza.posteggio.note}</label>		
			</span>
			</td>
			<%--colonna conmcessionario --%>
			<td>
			<%-- colonna concessionario caso1: posteggio libero e concessionario esistente --%>
			<c:if test="${empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice)}">
			<label id="label_occupante${varIndex.index}" class="occupante">
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						${var_presenza.concessionario.descrizioneRichiedente}
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						${var_presenza.concessionario.descrizioneRichiedente}
					</c:when>
					<c:otherwise>
						<a href="javascript: segnaPresenzaConcessionario('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}','${var_presenza.id.codice}','${var_presenza.concessionario.id.codice }','${var_presenza.transientAutDaSchedaDyn.id.codice}','anchor${varIndex.index}')" title="Segna presenza concessionario">${var_presenza.concessionario.descrizioneRichiedente}</a>
					</c:otherwise>
				</c:choose>
			</label>
			<c:if test="${var_presenza.flagAssenzaGiust eq true}">
			<label><div>[${var_presenza.autorizzazioneConcessionarioAssente.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
			</c:if>
			</c:if>
			<%-- colonna concessionario caso2: posteggio occupato dal concessionario--%>
			<c:if test="${not empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice) and (var_presenza.spuntista ne true)}">
			<label id="label_occupante${varIndex.index}" class="occupante"><img src="<%=request.getContextPath() %>/images/bob.gif" alt="<fmt:message key="form.gestionepresenze.presente" />" title="<fmt:message key="form.gestionepresenze.presente" />" />
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						${var_presenza.concessionario.descrizioneRichiedente}
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						${var_presenza.concessionario.descrizioneRichiedente}
					</c:when>
					<c:otherwise>
						<a href="eliminaPresenza.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codPresenza=${var_presenza.id.codice}#anchor${varIndex.index}" title="Elimina presenza concessionario">${var_presenza.concessionario.descrizioneRichiedente}</a>
					</c:otherwise>
				</c:choose>
			</label>
			<label><div>[${var_presenza.autorizzazioni.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
			</c:if>
			<%-- colonna concessionario caso3: concessionario esistente posteggio occupato dallo spuntista--%>
			<c:if test="${not empty var_presenza.occupante.id.codice and (not empty var_presenza.concessionario.id.codice) and (var_presenza.spuntista eq true)}">
			<label id="label_occupante${varIndex.index}" class="occupante">${var_presenza.concessionario.descrizioneRichiedente}</label>
			<c:if test="${var_presenza.flagAssenzaGiust eq true}">
			<label><div>[${var_presenza.autorizzazioneConcessionarioAssente.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></label>
			</c:if>
			</c:if>
			</td>
            <%--colonna assenza giustificata --%>
            <td>
                <%-- test se mercato chiuso o no, se è aperto visualizza questa parte di codice in modalità editabile  --%>
                <c:if test="${giornoMercato.flagPresenze ne true}">          
	                <c:if test="${(var_presenza.occupante.id.codice==null or var_presenza.spuntista eq true)&& var_presenza.concessionario.id.codice!=null}">
	 				<c:if test="${var_presenza.flagAssenzaGiust eq true}">	          
	                	<input type="checkbox" id="flagAssenza${varIndex.index}" checked="checked" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', true);"/> 
				    	<textarea  rows="4" cols="20" id="txtaAssenza${varIndex.index}" >${var_presenza.motivazione}</textarea>
	                    <a href="#" onclick="assegnaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','txtaAssenza${varIndex.index}','anchor${varIndex.index}');">
							<img src="../images/save.gif" alt="<fmt:message key="label.salva" />" title="<fmt:message key="label.salva" />" />
				  		</a>
	                    <c:if test="${not (var_presenza.motivazione == '' or var_presenza.motivazione == null)}">
	                    <a href="#" onclick="cancellaMotivazioneGiustificazione('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.id.codice}','anchor${varIndex.index}')">
							<img src="../images/cross.gif" alt="<fmt:message key="label.elimina" />" title="<fmt:message key="label.elimina" />" />
				  		</a>
	                    </c:if>
					</c:if>
					<c:if test="${var_presenza.flagAssenzaGiust ne true}">
		            	<input type="checkbox" id="flagAssenza${varIndex.index}" onclick="displayGiustificazione('flagAssenza${varIndex.index}','${var_presenza.id.codice}','anchor${varIndex.index}', false);" />
	                </c:if> 
	                </c:if>
                </c:if>
                <%-- test se mercato chiuso o no, se è chiuso visualizza questa parte di codice in modalità readonly --%>
                <c:if test="${ giornoMercato.flagPresenze eq true and var_presenza.flagAssenzaGiust eq true}">
                     <fmt:message key="form.gestionepresenze.assenzagiustificata" />: <br />${var_presenza.motivazione}
			    </c:if>
            </td>
            <%--colonna spuntista --%>
            <td>
			<c:if test="${empty var_presenza.occupante.id.codice or (not empty var_presenza.occupante.id.codice and (var_presenza.spuntista eq true))}">
			<c:choose>
			<c:when test="${not empty var_presenza.occupante.id.codice and (var_presenza.spuntista eq true)}">
				<label id="label_spuntista${varIndex.index}" class="spuntista"><img src="<%=request.getContextPath() %>/images/bob.gif" alt="<fmt:message key="form.gestionepresenze.presente" />" title="<fmt:message key="form.gestionepresenze.presente" />" />&nbsp;
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						${var_presenza.occupante.descrizioneRichiedente}
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						${var_presenza.occupante.descrizioneRichiedente}
					</c:when>
					<c:otherwise>
						<a href="eliminaPresenza.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codPresenza=${var_presenza.id.codice}" title="Elimina presenza spuntista">
							${var_presenza.occupante.descrizioneRichiedente}
						</a>
					</c:otherwise>
				</c:choose>	
				</label>
				<div>
					<label>
						<c:if test="${not empty var_presenza.autorizzazioni.id.codice}"><div>[${var_presenza.autorizzazioni.transientEstremiAut}]</div><c:if test="${not empty var_presenza.catMerc }"><div>[Cat. ${var_presenza.catMerc }]</div></c:if></c:if>
					</label>
				</div>																																		  
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${giornoMercato.flagRegfatte eq true}">
						<%--visualizzo il vuoto--%>
					</c:when>
					<c:when test="${giornoMercato.flagPresenze eq true}">
						<%--visualizzo il vuoto--%>
					</c:when>
					<c:otherwise>
						<span id="div_spuntista_search${varIndex.index}">
						<input id="anagrafe_id${varIndex.index}" type="text" class="searchbox" onkeydown="javascript:return searchAll(this,event,3)" size="40" title="${ricerca_spuntista_title}" onchange="verifica${varIndex.index}()"/>
						</span>
						<script type='text/javascript'>
						
							function verifica${varIndex.index}(){
								var valore = jQuery('#anagrafe_id${varIndex.index}_hidden').val();
								if(valore!=''){
									if(nuovaAnagrafe${varIndex.index}Win){
										nuovaAnagrafe${varIndex.index}Win.close();
									}
									var idAut='';
									var catMerc='';
									segnaPresenzaSpuntista('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}',valore,idAut,catMerc);
								}
							}
							function setHiddenField${varIndex.index}(inputField,listItem){
								// GIANPAOLO
								var codAnagrafe = listItem.id;	
								var idAut = listItem.lang;
								var catMerc = listItem.title;
								segnaPresenzaSpuntista('${mercato.id.codice}','${uso.id.codice}','${data}','${var_presenza.posteggio.id.codice}',codAnagrafe,idAut,catMerc);
							}
							var nuovaAnagrafe${varIndex.index}Win;
							function nuovaAnagrafe${varIndex.index}(objId){
								jQuery('#anagrafe_id${varIndex.index}_hidden').val('');
								jQuery('#anagrafe_id${varIndex.index}').val('');
								var caller = 'anagrafe_id${varIndex.index}';	
									if('${param.tiposoggetto}'!= '')
									{
										
										var tipo='${param.tiposoggetto}';
										nuovaAnagrafe${varIndex.index}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?visualizzaTipoAnagrafe="+tipo+"&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");								
									}else
									{
										nuovaAnagrafe${varIndex.index}Win = window.open("<%=request.getContextPath()%>/anagrafe/popupcreate.htm?tiposoggetto=F&popupCaller="+caller,69,"status=1,menubar=0,scrollbars=1,width=800, height=600");			
									}
							}

						</script>
						<init:autocompleter minChars="3" afterUpdateElement="setHiddenField${varIndex.index}" methodAjax="findAnagrafeSpuntisti.htm?mercati.id.codice=${mercato.id.codice}&mercatiUso.id.codice=${uso.id.codice}" idHidden="anagrafe_id${varIndex.index}_hidden" idInput="anagrafe_id${varIndex.index}" inputTitleKey="label.ricerca_richiedente"/>
						<input id="anagrafe_id${varIndex.index}_hidden" type="hidden"  />	
					    <a href="javascript:nuovaAnagrafe${varIndex.index}('${varIndex.index}_hidden');" <fmt:message key="label.inserisci_anagrafe" />><img id="imgAggiungi${varIndex.index}"
							src="<%=request.getContextPath()%>/images/add.gif"				
							title="<fmt:message key="label.inserisci_anagrafe" />" /></a>	
					</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>
			</c:if>
			</td>
			<%-- colonna flag proprietario --%>
			<td>
				<c:if test="${not empty var_presenza.occupante.id.codice}" >
					<c:if test="${var_presenza.proprietario ne 1 && giornoMercato.flagPresenze ne true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" onclick="aggiornaProprietario('flagProprietario${varIndex.index}','${var_presenza.id.codice}');"/>
					</c:if>
					<c:if test="${var_presenza.proprietario eq 1 && giornoMercato.flagPresenze ne true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" checked="checked" onclick="aggiornaProprietario('flagProprietario${varIndex.index}','${var_presenza.id.codice}');"/>
					</c:if>
					<c:if test="${var_presenza.proprietario ne 1 && giornoMercato.flagPresenze eq true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" disabled="disabled" />
					</c:if>
					<c:if test="${var_presenza.proprietario eq 1 && giornoMercato.flagPresenze eq true}" >
						<input type="checkbox" id="flagProprietario${varIndex.index}" checked="checked" disabled="disabled"/>
					</c:if>
				</c:if>
				<a name="anchor${varIndex.index}">&nbsp;</a>
			</td>
		</tr>
		</c:if>
		</c:if>
	<%i++; %>
	</c:forEach>
	</tbody>
	</table>
	</div>
    </form>
	
	<br class="clear" />
	<div class="titoloSezione"><fmt:message key="form.gestionepresenze.spuntistinoposteggio.list" /></div>
		<c:if test="${giornoMercato.flagPresenze ne true}">
		<label><fmt:message key="form.gestionepresenze.spuntistinoposteggio.search" /></label>
		<span><input id="anagrafe_spuntista_id" type="text" class="searchbox" onkeydown="javascript:return searchAll(this,event,3)" size="50"/></span>
		<script type='text/javascript'>
			function setHiddenFieldSpuntista(inputField,listItem){
				var codiceAnagrafe = listItem.id;
				var idAut = listItem.lang;
				var catMerc = listItem.title;		
				segnaPresenzaSpuntistaNoPosteggio('${mercato.id.codice}','${uso.id.codice}','${data}',codiceAnagrafe,idAut,catMerc);
			}				
		</script>
		<init:autocompleter minChars="3" afterUpdateElement="setHiddenFieldSpuntista" methodAjax="findAnagrafeSpuntisti.htm?mercati.id.codice=${mercato.id.codice}&mercatiUso.id.codice=${uso.id.codice}" idHidden="anagrafe_spuntista_id_hidden" idInput="anagrafe_spuntista_id" inputTitleKey="label.ricerca_richiedente"/>
		</c:if>
		<br />
		<form name="spuntistiForm" action="list.htm">
			<jmesa:springTableFacade
				id="spuntisti_id" 
				items="${spuntistiNoPosteggio}" 
				var="spuntisti_var"
				exportTypes="pdfp,excel,csv" 
				stateAttr="restore" >
				<jmesa:htmlTable>
					<jmesa:htmlRow>							
						<jmesa:htmlColumn property="occupante.descrizioneRichiedente" titleKey="form.anagrafe.nome"/>
						<jmesa:htmlColumn property="autorizzazioni.transientEstremiAut" titleKey="label.autorizzazione">
							${spuntisti_var.autorizzazioni.transientEstremiAut}
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="catMerc" titleKey="label.categoria_merceologica"/>
						<jmesa:htmlColumn property="occupante.id.codice" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">			
							<c:choose>
								<c:when test="${giornoMercato.flagPresenze eq true}">
								</c:when>
								<c:otherwise>
									<a href="eliminaPresenza.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codPresenza=${spuntisti_var.id.codice}" title="<fmt:message key="label.elimina" /> ${spuntisti_var.id.codice}">
										<img src="../images/cross.gif" alt="<fmt:message key="label.elimina" />" />
									</a>
								</c:otherwise>
							</c:choose>
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>
			</jmesa:springTableFacade>
			<input type="hidden" value="${mercato.id.codice}" name="codiceMercato"/>
			<input type="hidden" value="${uso.id.codice}" name="usoMercato"/>
			<input type="hidden" value="${data}" name="giornoMercato"/>
		</form>			
		<script type="text/javascript">
			var _jmesaUrl='list.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&';
			var _captionTab='<fmt:message key="form.gestionepresenze.spuntistinoposteggio.list" />';

			//Sovrascrivo le due funzioni javascript presenti in gruppoinit.js
			//è neccessario sovrascriverle perchè nella pagina sono presenti due tabelle jmesa

			// funzione per determinare le proprietà del table facade (JMesa)
			function getProperties(id) {
				var properties = new Array();
				properties[0]='occupante.descrizioneRichiedente';
				return properties;
			}

			// determina a partire dall'id del table facade della tabella di JMesa
			// il nome delle colonne
			function getColumnArray(id) {
				key = new Array();
				key[0] = '<fmt:message key="form.anagrafe.nome" />';
				return key;
			}
		</script>
		</c:if>
</div>
<div id="functions">
	<script type="text/javascript">
	var goToUrl = "../registrazionimercato/registrazionipresenze.htm?mercati.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&giornoMercato=${data}";
	goToUrl = escape(goToUrl);
	function getParametriAssenza(idTextAreaAssenza){
		var testo=$(idTextAreaAssenza).value;
		var parametri='&testo='+escape(testo);
		return parametri;
	}
	function assegnaMotivazioneGiustificazione(codiceMercato,usoMercato,giornoMercato,codice,idTextAreaAssenza){
		var parametriAssenza = getParametriAssenza(idTextAreaAssenza);
		var url="assegnaMotivazioneGiustificazione.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codice="+codice+parametriAssenza;
		doHref(url);
	}
	function cancellaMotivazioneGiustificazione(codiceMercato,usoMercato,giornoMercato,codice){
		var url = "cancellaMotivazioneGiustificazione.htm?codiceMercato="+codiceMercato+"&usoMercato="+usoMercato+"&giornoMercato="+giornoMercato+"&codice="+codice;
		doHref(url);
	}
	function displayGiustificazione(idflagAssenza,codice, nomeAncora,isChecked){
		
		var flag='';
	   	if($(idflagAssenza).checked){
			flag='true';
		} else{
		    flag='false';
	   	}
	   	if(isChecked){
	   		doHref('aggiornaFlagGiustificazione.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+flag+"&dataFine=#"+nomeAncora,'');
	   	}else{
	   		$('nomeAncora_id').value=nomeAncora;
		   	$('codice_id').value=codice;
			$('flag_id').value=flag;
			dijit.byId('pannelloSceltaSoftwareDiv').show();
	   	}
	}
	function aggiornaProprietario(idFlagProprietario,codice){
		var check=''; 	  
	   	if($(idFlagProprietario).checked){
			check='true';
		}else{
		    check='false';
	   	}
	   	doHref('aggiornaFlagProprietario.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+check,'');
	}
	
	function setValuesAndGo(){
		var codice = $('codice_id').value;
		var flag = $('flag_id').value;
		var dataFine = $('dataFine_id').value;
		var nomeAncora =$('nomeAncora_id').value;
		doHref('aggiornaFlagGiustificazione.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}&codice='+codice+'&flag='+flag+'&dataFine='+dataFine+'#'+nomeAncora,'');		
	}
	
	</script>
	<%
		MercatipresenzeT giornoMM = (MercatipresenzeT)request.getAttribute("giornoMercato");
		String urlStampeDoc = BackofficeNETConstants.getUrlTo(request,BackofficeNETConstants.getURL_STAMPA_LETTERE_TIPO()+"?idgiorno="+giornoMM.getId().getCodice()+"&fkcodicemercato="+giornoMM.getMercato().getId().getCodice()+"&fkidmercatiuso="+giornoMM.getMercatoUso().getId().getCodice(),"",(String)session.getAttribute(WebConstants.SOFTWARE),true);
		pageContext.setAttribute("dyn_url_stampe",urlStampeDoc);
	%>
		
	<div dojoType="dijit.Dialog" id="pannelloSceltaSoftwareDiv" title="<fmt:message key="label.gestione_presenze.gestisci_assenza" />"  style="display: none; height: auto;">
			<div style="width:300px; height: 200px;">


		<div>
			<fmt:message key="label.gestione_presenze.gestisci_assenza.help" />			
		</div>	
		<input type="hidden" name="codice" id="codice_id" />	
		<input type="hidden" name="flag" id="flag_id" />
		<input type="hidden" name="nomeAncora" id="nomeAncora_id" />
		<input type="hidden" name="proprietario" id="propiretario_id" />
		<fmt:message key="label.data_fine" />
		<input type="text" size="8" name="dataFine" id="dataFine_id" onblur="isValidDate(this,true);" value="${data}"/>
		<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" />	
		
				
			<div id="functions">
				<ul>
					<li id="OkId"><a href="javascript:setValuesAndGo()"><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void 0" onclick="dijit.byId('pannelloSceltaSoftwareDiv').hide();"><fmt:message key="button.annulla" /></a></li>
				</ul>
			</div>
			<br class="clear" />	
			</div>
		</div>
	
	
	<ul>
		<c:if test="${giornoMercato.flagPresenze ne true}">
			<c:if test="${giornoMercato.flagRegfatte  ne true}">
				<li><a href="javascript:doHref('segnaPresentiTuttiConcessionari.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}','')"><fmt:message key="button.insert.tuttiConcessionari" /></a></li>
				<%-- <li><a href="javascript:doHref('listSpuntistiGiornoPrima.htm?codiceMercato=${mercato.id.codice}&usoMercato=${uso.id.codice}&giornoMercato=${data}','')"><fmt:message key="button.view.spuntistigiornoprima" /></a></li> --%>				
			</c:if>
			<!-- §§§BEGIN§§§ -->
			<c:if test="${inite:isEnterprise()}">
				<c:if test="${flagMercatoContabilita == true}">
					<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo='+goToUrl,'')"><fmt:message key="button.registrazione" /></a></li>
			    </c:if>
		    </c:if>
		    <!-- §§§END§§§ -->
			<li><a href="javascript:doHref('chiudiGiornoMercato.htm?mercato.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&data=${data}','<fmt:message key="button.storicizzazionegiorno.alert" />')"><fmt:message key="button.storicizzazionegiorno.closemarket" /></a></li>
		</c:if>
		<c:if test="${giornoMercato.flagPresenze eq true and giornoMercato.flagPresenzeArchivio eq false}">
			<li><a href="javascript:doHref('apriGiornoMercato.htm?mercato.id.codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&data=${data}','<fmt:message key="button.aperturagiorno.alert" />')"><fmt:message key="button.aperturagiorno" /></a></li>
		</c:if>
		<!-- §§§BEGIN§§§ -->
		<c:if test="${inite:isEnterprise()}">
			<li><a href="javascript: void 0;" onclick="window.open('${dyn_url_stampe}',66,'width=600,height=250,menubar=yes,scrollbars=yes,status=yes,resizable=yes');"><fmt:message	key="button.print" /></a></li>
		</c:if>
		<!-- §§§END§§§ -->
		<li><a href="javascript:doHref('../calendariomercato/view.htm?codice=${mercato.id.codice}&mercatouso.id.codice=${uso.id.codice}&anno=<%=((String)request.getAttribute("data")).substring(6,10) %>&step=2','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>	
</body>
</html>