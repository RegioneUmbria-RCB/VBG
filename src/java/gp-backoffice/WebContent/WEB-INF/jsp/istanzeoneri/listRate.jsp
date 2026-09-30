<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="istanzeoneri.label.lista_istanzeoneri.derateizzazione" /></title>
		<style>
			div.istanzeoneri_id {
			  width: 50%;
			  text-align: left;
			  border-collapse: collapse;
		      margin: 20px 0 20px 0;
			}
			.divTable.istanzeoneri_id .divTableCell, .divTable.istanzeoneri_id .divTableHead {
			  padding: 3px 5px;
			  border: 2px solid #fcfcfc
			}
			.divTable.istanzeoneri_id .divTableBody .divTableCell {
			  font-size: 13px;
			}
			.divTable.istanzeoneri_id .divTableRow:nth-child(even) {
			  background: #F5F5F0;
			}
			.divTable.istanzeoneri_id .divTableHeading {
			  }
			.divTable.istanzeoneri_id .divTableHeading .divTableHead {
			  font-weight: bold;
			}
			
			.divTable{ display: table; }
			.divTableRow { display: table-row; }
			.divTableHeading { 
				display: table-header-group;
			    background: #DEDDCC;
				    }
			.divTableCell, .divTableHead { display: table-cell;}
			.divTableHeading { display: table-header-group;}
			.divTableFoot { display: table-footer-group;}
			.divTableBody { display: table-row-group;}
			.divTableHead.col1 {
			    width: 5%;
			}
			.divTableHead.col2 {
			    width: 20%;
			}
			.divTableHead.col3 {
			    width: 12%;
			}
			.divTableHead.col4 {
			    width: 12%;
			}
			.divTableHead.col5 {
			    width: 10%;
			}
			.divTableHead.col6 {
			    width: 10%;
			}
			.divTableHead.col7 {
			    width: 20%;
			}
			.divTableCell.prezzo {
	  				text-align: right;
			}
		</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="istanzeoneri.label.lista_istanzeoneri.derateizzazione" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
	<div id="subcontent">
		<form name="istanzeoneriForm" >
		<input type="hidden" name="codiceIstanza" value="${istanza.id.codice}"/>
		<input type="hidden" name="codiceCausaleOneri" value="${codiceCausaleOneri }"/>
		<input type="hidden" name="codiceRaggruppamento" value="${codiceRaggruppamento }"/>
			 <div class="divTable istanzeoneri_id">
				<div class="divTableHeading">
					<div class="divTableRow">
						<div class="divTableHead col1"><fmt:message key="label.codice"/></div>
						<div class="divTableHead col2"><fmt:message key="istanzeoneri.label.descrizione_causale"/></div>
						<div class="divTableHead col3"><fmt:message key="istanzeoneri.label.importo"/></div>
						<div class="divTableHead col4"><fmt:message key="istanzeoneri.label.importo_istruttoria"/></div>
						<div class="divTableHead col5"><fmt:message key="istanzeoneri.label.data_scadenza"/></div>
						<div class="divTableHead col6"><fmt:message key="istanzeoneri.label.data"/></div>
						<div class="divTableHead col7"><fmt:message key="istanzeoneri.label.nro_documento"/></div>
					</div>
				</div>
				<div class="divTableBody">
					<c:forEach items="${istanzeoneriList}" var="io">
						<div class="divTableRow">
							<div class="divTableCell">${io.tipicausalioneri.id.codice}</div>						
							<div class="divTableCell">${io.tipicausalioneri.coDescrizione }</div>
							<div class="divTableCell prezzo"><fmt:formatNumber value="${io.prezzo}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></div>
							<div class="divTableCell prezzo"><fmt:formatNumber value="${io.prezzoistruttoria }" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></div>
							<div class="divTableCell data"><fmt:formatDate value="${io.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></div>
							<div class="divTableCell data"><fmt:formatDate value="${io.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></div>
							<div class="divTableCell">${io.nrDocumento }</div>
						</div>
					</c:forEach>				
				</div>
			</div>
		</form>
		
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="istanzeoneri.label.lista_istanzeoneri.title" />';
		</script>
	</div>
	<div id="functions">
		<ul>			
			<li><a href="javascript:doSubmit('../oneritipirateizzazione/derateizzaOnere.htm','',document.istanzeoneriForm)"><fmt:message key="button.derateizza" /></a></li>
			<li><a href="javascript:doHref('list.htm?codiceIstanza=${istanza.id.codice}&&software=${istanza.software.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>