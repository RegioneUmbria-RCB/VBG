<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="alberoproc.label.comuni_esclusi" /></title>
    <script type="text/javascript">
	    function eliminaRiga(codiceInterventoProc,codiceComune){

	    	if (!confirm("Cancellare l'esclusione?")) {
	    		  return;
	    	}
	    	
	        jQuery.ajax({
	            url: '${pageContext.request.contextPath}/alberoproc/ajaxCancellaEsclusioneComune.htm',
	            type: 'GET',
	            data: {
	                codiceinterventoproc: codiceInterventoProc,
	            	codicecomune: codiceComune
	           	},
	            cache: false,             
	            success: function(dataResult) { 
	            	location.reload();
	              },
	            error: function(dataError){                         
	            	console.error('Errore:', error);
	            } 
	       }); 
	    }
	    function aggiungiEnti(inputField,listItem){

	    	let comuneSelezionato = listItem.id;
	    	let codiceInterventoProc = ${alberoproc.id.codice};
	    	
	        if (comuneSelezionato === '') {
	            alert("Selezionare almeno un ente");
	        	return;
        	}
	        
	        disableFunctions();
	        
	        jQuery.ajax({
                url: '${pageContext.request.contextPath}/alberoproc/ajaxAggiungiComuniEsclusi.htm',
                type: 'POST',
                traditional: true,
                data: {
                    codiceinterventoproc: codiceInterventoProc,
                    comuni:  comuneSelezionato  
                    
                },
                cache: false,             
                success: function(dataResult) { 
                    location.reload();
                    enableFunctions();
                  },
                error: function(dataError){                         
                    console.error('Errore:', error);
                    enableFunctions();
                } 
           });
	        
	       
	    }
    </script>
</head>
<body>
    <span class="titoloPagina"><fmt:message key="alberoproc.label.comuni_esclusi" /></span>
    <jsp:include page="../includes/innerNavigation.jsp">
        <jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
        <jsp:param name="path" value="../alberoproc/comuniesclusi" />
    </jsp:include>
    <div id="subcontent">
        <div class="parametriDiv">
            <div class="etichetta">
                <div><fmt:message key="label.procedimento" />:</div>
            </div>
            <div class="parametro">
                <div><c:out value="${alberoproc.scDescrizione}" /></div>
            </div>
        </div>
        <div class="clear"></div>
        <form name="alberoprocComuniEsclusiForm" action="comuniesclusi.htm">
            <fieldset>
                <legend><b><fmt:message key="alberoproc.label.comuni_esclusi_ereditati" /></b></legend>
                <div class="jmesa">
                    <table border="0"  cellpadding="2" cellspacing="0" class="table">
                        <thead>
                            <tr class="header">
                                <td width="10%"><fmt:message key="label.codice" /> </td>
                                <td width="85%"><fmt:message key="label.ente" /></td>
                                <td width="85%"><fmt:message key="label.azioni" /></td>
                            </tr>
                        </thead>
                        <tbody class="tbody">
                            <%int e=1;%>
                            <c:forEach items="${comuniEsclusi}" var="comuneEscluso_var">
                                <c:if test="${alberoproc.id.codice!=comuneEscluso_var.codiceInterventoProc}">
                                    <tr class="<%=(e%2)==0?"odd":"even"%>">
                                        <td>${comuneEscluso_var.codiceComune}</td>
                                        <td>${comuneEscluso_var.comune}</td>
                                        <td>
                                            <a class="dettaglioColumn" href="javascript:historySet('${_urlback}', '../alberoproc/comuniesclusi.htm?codiceprocedimento=${comuneEscluso_var.codiceInterventoProc}', '')" title="<fmt:message key="label.dettaglio" />">
                                                <label><fmt:message key="label.edit.record.image" /></label>
                                            </a>
                                        </td>
                                    </tr>
                                </c:if>
                                <%e++; %>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </fieldset>
            <fieldset>
	            <legend><b><fmt:message key="alberoproc.label.comuni_esclusi" /></b></legend>
	            <div class="jmesa">
	                <table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
						    <tr class="header">
						        <td width="10%"><fmt:message key="label.codice" /> </td>
						        <td width="85%"><fmt:message key="label.ente" /></td>
						        <td width="5%" align="center" ><fmt:message key="label.azioni" /></td>
						    </tr>
						</thead>
						<tbody class="tbody">
                            <%int z=1;%>
                            <c:forEach items="${comuniEsclusi}" var="comuneEscluso_var">
                                <c:if test="${alberoproc.id.codice==comuneEscluso_var.codiceInterventoProc}">
                                    <tr class="<%=(z%2)==0?"odd":"even"%>">
                                        <td>${comuneEscluso_var.codiceComune}</td>
                                        <td>${comuneEscluso_var.comune}</td>
                                        <td>
                                            <a class="eliminaRiga" href="javascritp: void 0;" onClick="eliminaRiga(${alberoproc.id.codice},'${comuneEscluso_var.codiceComune}');" title="<fmt:message key="label.elimina" />">
                                                <label><fmt:message key="label.edit.record.image" /></label>
                                            </a>
                                        </td>
                                    </tr>
                                </c:if>
                                <%z++; %>
                            </c:forEach>
						</tbody>
	                </table>
	            </div>
                <input type="hidden" value="${alberoproc.id.codice}" name="codiceprocedimento"/>
            </fieldset>
            <fieldset>
                <legend><b><fmt:message key="alberoproc.label.nuovo_comune_escluso" /></b></legend>
                <div>
                    <table width="100%">
                        <tr>
                            <td width="20%">
                                <fmt:message key="label.comune" />
                            </td>
							<td>
		                        <jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
		                            <jsp:param name="idElemento" value="comune_id" />      
		                            <jsp:param name="pathPropertyDescription" value="comune_descrizione" />
		                            <jsp:param name="pathPropertyCode" value="comune_codice" />
		                            <jsp:param name="autocompleterAjax" value="findComuniItaliani.htm" />
		                            <jsp:param name="titleKey" value="label.ricerca_ente" />     
		                            <jsp:param name="afterUpdateElement" value="aggiungiEnti" />                       
		                        </jsp:include>
                            </td>
		              </tr>
		          </table>
                </div>
            </fieldset>
        </form>
    </div>
    <div id="functions">
        <ul>
            <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
        </ul>
    </div>
</body>