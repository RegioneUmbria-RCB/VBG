<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.comunicazione.bollettazione.list.title" /></title>
	 <script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>

<style>

.col_div{
	width: 850px;
}
.titolo, .flex-wrap {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
}

.col_2, .avanzamento {
	padding-left: 200px;
}

.avanzamento {
/*
	display: flex;
	flex-direction: row;
	justify-content: space-between;
*/
}
.avanzamento > div{
	/*padding: 0 8px;*/
    display: inline-block;
    padding: 0 var(--half-padding);
}

.titolo>.col_1 {
    padding-right: 70px;
}

.titolo>.col_2 {
	padding-right: 56px;
}

.titolo>.col_3 {
	padding-right: 46px;
}

.flex-wrap > div{
	flex-grow: 0;
    flex-basis: auto;
    width: 33%;
}

.col_3 {
	/*
    padding-left: 200px;
	padding-right: 20px;
    */
    text-align: right;
}
.col_3 > button {

}

button {
	width: 110px;
	height: 25px;
    border-radius: 0px;
    border: 0;
}

.cmd-completo {
	background-color: var(--color-success);
	color: var(--inverse-text-color);
	pointer-events: none;
}

.cmd-aggiorna {
    background-color: var(--color-default);
    cursor: pointer;
}

#percCompletati {
	width: 60px;
	height: 16px;
}

.avanzamento-stati {
    margin: 0;
    padding: 0;
    list-style-type: none;
    
}
</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.comunicazione.bollettazione.list.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../bollgestione/list" />
    </jsp:include>
    <div id="subcontent">
		<div class="parametriDiv">
			<div class="etichetta">
		      	<div><fmt:message key="label.descrizione"/>:</div>      	
		    </div>        
		    <div class="parametro">
		      	<div>${testata.descrizione}</div>	        
	        </div>
		</div>	
		<div class="clear" />		
		<div id="subcontent" class="vbg-form">
			<spring-form:form commandName="comunicazioniBollettazioneCommand" name="inviodati">
					<jsp:include page="../includes/displayGlobalMessages.jsp" >
				        <jsp:param name="commandName" value="comunicazioniBollettazioneCommand" />
				    </jsp:include>
		    </spring-form:form>
		    <div id="error" class="error"></div>
		    <table id="listaComunicazioniTable" class="vbg-table">
					<thead>
					<tr class="header">
						<th><fmt:message key="label.codice" /></th>
						<th><fmt:message key="label.data" /></th>
						<th><fmt:message key="label.descrizione" /></th>
                        <th><fmt:message key="label.ultimo_stato" /></th>
                        <th><fmt:message key="label.stato_avanzamento" /></th>
                        <th><fmt:message key="label.azioni" /></th>			
					</tr>
				</thead>
					<tbody>
						<c:forEach items="${comunicazioniResoconti}" var="listComResoconti" varStatus="a">
						  <tr class="riga-comresoconti">
							<td><a id="idTestata_${a.index}"
								href="view.htm?codice=${listComResoconti.id}">${listComResoconti.id}</a></td>
							<td><fmt:formatDate value="${listComResoconti.data}"
									pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" /></td>
							<td>${listComResoconti.descrizione}</td>
                            <td>
                                <ul class="avanzamento-stati">
                                    <c:forEach items="${listComResoconti.resocontoOperazioniMassive }" var="resoconto"> 
                                        <c:set value="${resoconto.titoloResoconto}" var="stato" scope="page"/>
                                    
                                        <li>
                                            ${resoconto.titoloResoconto }: ${resoconto.totaleOperazioniEseguite}
                                        </li>
                                    
                                    </c:forEach>
                                </ul>
                            </td>
                            
                            <td>
                                <progress id="percCompletati" max="${listComResoconti.totaleOperazioniRichieste}"
                                                    value="${listComResoconti.totaleOperazioniCompletate}"> </progress>
                                ${listComResoconti.totaleOperazioniCompletate} su ${listComResoconti.totaleOperazioniRichieste}
                            </td>
                            
                            <td>
                                <c:choose>
                                    <c:when test="${listComResoconti.completa eq true}">
                                        <div class="col_3">
                                            <button class="cmd-completo">
                                                <i class="fa fa-check"></i>
                                                <fmt:message key="label.completato" />
                                            </button>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="col_3">
                                            <button id="aggiorna${a.index}" class="cmd-aggiorna"
                                                data-id="${listComResoconti.id}">
                                                <i class="fa fa-refresh"></i>
                                                <fmt:message key="label.aggiorna" />
                                            </button>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
					      </tr>
					   </c:forEach>
					</tbody>
				</table>
		</div>
	</div>


	<script type="text/javascript">
		

		(function($) {
			
			const buttons = document.querySelectorAll(".cmd-aggiorna");
			this.onError = void (0);
			buttons.forEach((button) => {
				 button.addEventListener('click', (e) => {				
					e.preventDefault();				
					aggiornaRiga(e.target.dataset.id);						
				});
			});
						
			async function aggiornaRiga(idTestata) {
				
			    try{
			    	vbg.mostraModalCaricamento();
			    	
	                console.log(idTestata);
	                
	                await $.ajax({
	                    url : "ajaxElabora.htm",
	                    data : { idMassiveTestata : idTestata},
	                    method : 'GET',
	                    dataType : "html",
	                    success : function(data) {
	                        console.log('Riga aggiornata ', idTestata);
	                    },
	                    error:  gestisciErrore                    
	                }); 
	                
	                // TODO: andrebbe aggiornata solo la riga
	                document.location.reload();
	                
			    } catch (ex) {
			    	console.error(ex);
			    	vbg.nascondiModalCaricamento(); 
			    } finally {
			    			    	
			    }	
				
			}
			
			 function gestisciErrore(jqXHR, textStatus, errorThrown) {
				console.error([ jqXHR, textStatus, errorThrown ]);
				// TODO Mostrare a video
				//alert("Si è verificato un errore durante l'escuzione.");
				document.getElementById("error").innerHTML = "Si è verificato un errore durante l'esecuzione";
				

			}		
			
		})(jQuery);
		
		function returnToBollettazione(){		
			var url  = URLDecode('${_urlback}');			
			ajaxHistorySet(url);			
			setTimeout("doHref('../bollgestione/view.htm?codice=${testata.id.codice}','')",10);
		}

		function ajaxHistorySet(url){
			
			var jhqr = jQuery.ajax({
				  url: '../history/ajaxSet.htm?ReturnTo='+url,
				  context: document.body,
				  cache: false,				
				  dataType: "html",
				  success: function(data) { 				   
					} 
				});	
		}	
		
	</script>

	<div id="functions">
		<ul>
			<li><a
				href="javascript:doHref('create.htm?idBollettazione=${testata.id.codice}','');"><fmt:message
						key="button.new_comunicazione" /></a></li>
			<%-- <li><a href="javascript:doHref('','');"><fmt:message key="button.processa_comunicazioni" /></a></li> --%>
			<li><a
				href="javascript:returnToBollettazione()"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>