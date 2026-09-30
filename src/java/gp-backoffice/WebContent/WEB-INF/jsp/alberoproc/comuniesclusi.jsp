<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.AuthLevel"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="java.net.URLEncoder" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><fmt:message key="alberoproc.label.comuni_esclusi" /></title>
    <script type="text/javascript">
         function eliminaRiga(codiceInterventoProc,codiceComune){

            if (!confirm("Cancellare l'esclusione?")) {
                  return;
            }
            
            const params = new URLSearchParams({
                codiceinterventoproc: codiceInterventoProc,
            	codicecomune: codiceComune
           	});
            
            fetch(
           		'${pageContext.request.contextPath}/alberoproc/ajaxCancellaEsclusioneComune.htm?' + params,
           		{
            		method: 'GET',
            		cache: 'no-cache'
           		}
           	)
            .then(response => {
           		if (!response.ok) {
           		    throw new Error(`HTTP ${response.status}`);
           		}
           		return response.text();
           	})
        	.then(dataResult => {
           		location.reload();
          	})
       		.catch(error => {
           		console.error('Errore:', error);
         	});
        }
        
        function aggiungiEnti(codiceInterventoProc){
            const comuniSelezionati = Array
                   .from(
                      document
                       .getElementById("comuni")
                       .selectedOptions
                    )
                    .map(opt => opt.value);

            if (comuniSelezionati.length === 0) {
                alert("Selezionare almeno un ente");
                return;
            }
            
            const formData = new URLSearchParams();
            formData.append('codiceinterventoproc', codiceInterventoProc);
            comuniSelezionati.forEach(comune => {
                formData.append('comuni', comune);
            });
            
            fetch('${pageContext.request.contextPath}/alberoproc/ajaxAggiungiComuniEsclusi.htm', {
                method: 'POST',
                headers: {
                	'Content-Type': 'application/x-www-form-urlencoded'
               	},
                body: formData
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                location.reload();
            })
            .catch(error => {
                console.error('Errore:', error);
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
                <div><c:out value="${alberoproc.descrizioneCompleta}" /></div>
            </div>
        </div>
        <div class="clear"><br /></div>
        <form name="alberoprocComuniEsclusiForm" action="comuniesclusi.htm">
	        <div class="vbg-form">
	            <fieldset>
	                <legend><fmt:message key="alberoproc.label.comuni_esclusi_ereditati" /></legend>
	                <div class="jmesa">
	                    <table border="0"  cellpadding="2" cellspacing="0" class="table">
	                        <thead>
	                            <tr class="header">
	                                <td width="10%"><fmt:message key="label.codice" /> </td>
	                                <td width="20%"><fmt:message key="label.ente" /></td>
	                                <td width="70%"><fmt:message key="label.intervento" /></td>
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
                                                <a href="javascript:historySet('${_urlback}','../alberoproc/comuniesclusi.htm?codiceprocedimento=${comuneEscluso_var.codiceInterventoProc}','')">
                                                    ${comuneEscluso_var.intervento}
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
	                <legend><fmt:message key="alberoproc.label.comuni_esclusi" /></legend>
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
	                                            <a class="eliminaRiga" href="javascript: void 0;" onClick="eliminaRiga(${alberoproc.id.codice},'${comuneEscluso_var.codiceComune}');" title="<fmt:message key="label.elimina" />">
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
	                <legend><fmt:message key="alberoproc.label.nuovo_comune_escluso" /></legend>
	                <div>
	                    <table width="100%">
	                        <tr>
	                            <td width="20%">
	                                <fmt:message key="label.comune" />
	                            </td>
	                            <td>
	                                <select id="comuni" multiple="multiple">                        
	                                    <c:forEach var="comune" items="${elencoComuni}" varStatus="counter">                                     
	                                        <option value="${comune.codiceComune}">${comune.comune}</option>
	                                    </c:forEach>
	                                </select>
	                            </td>
	                      </tr>
	                  </table>
	                </div>
	            </fieldset>
            </div>
        </form>
    </div>
    <div id="functions">
        <ul>
            <li><a href="javascript:void 0;" onClick="aggiungiEnti(${alberoproc.id.codice});"><fmt:message key="button.aggiungi" /></a></li>
            <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
        </ul>
    </div>
</body>
</html>