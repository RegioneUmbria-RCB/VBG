<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
	<style type="text/css" class="init">
		#cestino{
			background-color: inherit;
			border: none;
		}
		#peopleprocsportelliTable_wrapper {
		    padding: 16px 0;
		}
	</style>
	<script type="text/javascript" language="javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
	<title><fmt:message key="label.peopleprocsportelli.lista_peopleprocsportelli.title" /></title>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="label.peopleprocsportelli.lista_peopleprocsportelli.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <div class="clear"></div>
	<div id="subcontent">
		<form name="peopleprocsportelliForm" action="list.htm">
			<table id="peopleprocsportelliTable" class="vbg-table">
				<thead>
					<tr>
						<th><fmt:message key="label.codice" />  </th>
						<th><fmt:message key="label.peopleprocsportelli.peopleproc" /></th>
						<th><fmt:message key="label.comune" /></th>
						<th><fmt:message key="label.software" /></th>
						<th><fmt:message key="label.azioni" /></th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${peopleprocsportelliList}" var="peopleprocsportelli" varStatus="step">
						<tr>
							<td>${peopleprocsportelli.id.codice }</td>
							<td>${peopleprocsportelli.peopleProc }</td>
							<td>${peopleprocsportelli.codicecomune.comune }</td>
							<td>${peopleprocsportelli.software.descrizione }</td>
							<td>
								<button id="cestino">
									<i  class="fa fa-trash-o fa-lg" data-id="${peopleprocsportelli.id.codice }"></i>
								</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


		</form>
		<script type="text/javascript">
		
		(function($) {						

			$('button').on('click', function(e) {
				
			  console.log('inizio cancellazione');
			
			  console.log(e.target.dataset.id);
			  
			  eliminaRiga(e.target.dataset.id);
			  
			  e.preventDefault();
			});
			
			
			$(document).ready(function() {
			    var table = $('#peopleprocsportelliTable').DataTable();
			 
			    $("#peopleprocsportelliTable tfoot th").each( function ( i ) {
			        var select = $('<select><option value=""></option></select>')
			            .appendTo( $(this).empty() )
			            .on( 'change', function () {
			                table.column( i )
			                    .search( $(this).val() )
			                    .draw();
			            } );
			 
			        table.column( i ).data().unique().sort().each( function ( d, j ) {
			            select.append( '<option value="'+d+'">'+d+'</option>' )
			        } );
			    } );
			} ); 
			
			
			async function eliminaRiga(idRiga){		
				if(confirm('<fmt:message key="javascript.confirm.delete" />')){
					
					vbg.mostraModalCaricamento();
					
					await $.ajax({
						  url: '${pageContext.request.contextPath}/peopleprocsportelli/ajaxEliminaRiga.htm?idRiga='+idRiga,
						  context: document.body,
						  cache: false,					  
						  dataType: "html",
						  success: function(data){
							 
							 						  	 
						  },
						  error: gestisciErrore						  
						});
					 document.location.reload();
				}
			}
			function gestisciErrore(jqXHR, textStatus, errorThrown) {
				console.error([ jqXHR, textStatus, errorThrown ]);				
				document.getElementById("error").innerHTML = "Si è verificato un errore durante l'esecuzione";
				

			}		
		
		})(jQuery);
			
		
		</script>
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>