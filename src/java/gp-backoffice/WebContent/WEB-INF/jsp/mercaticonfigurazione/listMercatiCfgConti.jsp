<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><fmt:message key="label.mercati_cfg_conti" /></title>
	</head>
	<body>
	
		<span class="titoloPagina"><fmt:message key="label.mercati_cfg_conti" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list"/>
        </jsp:include>
        <c:set var="software_attivo" scope="page"><%= ORMHelper.getSoftware() %></c:set>
        <jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../mercaticonfigurazione/listMercatiCfgConti" />
			<jsp:param name="qs"
				value="software%3D${ software_attivo }" />
		</jsp:include>
        
		<div id="subcontent">
		
		<table id="posteggiTableId" class="display compact cell-border" style="width:100%; border: 1px">
			<thead>
				<tr>
					<th><fmt:message key="label.mercaticategorie" /></th>
					<th><fmt:message key="label.posteggio_settore" /></th>
					<th><fmt:message key="form.mercatiConti.descrizione" /></th>			
		            <th><fmt:message key="label.concessione_uso" /></th>
		            <td><fmt:message key="form.attivita.istat" /></td>
					<th><fmt:message key="label.data_inizio" /></th>
					<th><fmt:message key="label.data_fine" /></th>
					<th><fmt:message key="label.importo" /></th>
					<th><fmt:message key="label.calcola_con_mq" /></th>
					<th><fmt:message key="label.azioni" /></th>												
				</tr>
			</thead>
			<tbody>
		
			<c:forEach items="${list}" var="bean" varStatus="varIndex">
			<tr>
				<td>${bean.mercatiCategorie.descrizione}</td>
				<td>${bean.posteggiSettori.settore}</td>
				<td>${bean.conti.descrizione}</td>
				<td>${bean.concessioniuso.descrizione}</td>
				<td>${bean.attivita.istat}</td>
				<td><fmt:formatDate value="${bean.datainizioval}" pattern="dd/MM/yyyy" /></td>
				<td><fmt:formatDate value="${bean.datafineval}" pattern="dd/MM/yyyy" /></td>
				<td style="text-align: right;" >${bean.importo}</td>
				<td><c:choose>
					<c:when test="${bean.flagMoltiplicaMq eq true }"><fmt:message key="label.si" />
					</c:when>
					<c:otherwise><fmt:message key="label.no" /></c:otherwise>
					</c:choose>
				</td>
				<td>
				<a class="dettaglioColumn" href="../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticonfigurazione%2FviewMercatiCfgConti.htm?codice=${bean.id.codice}" 
								title="<fmt:message key="label.edit.record" /> ${bean.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label></a>
				
				</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>

		</div>
		<div id="functions">
			<ul>
				<li><a href="javascript:doHref('createMercatiCfgConti.htm','');"><fmt:message key="button.new" /></a></li>
				<li><a href="exportMercatiCfgConti.htm"><fmt:message key="button.esporta" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		<style>
		thead input {
	        width: 100%;
	    }
		</style>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.18/r-2.2.2/datatables.min.css"/>
 
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.18/r-2.2.2/datatables.min.js"></script>
<script type="text/javascript">


jQuery(document).ready(function() {
    // Setup - add a text input to each footer cell
    jQuery('#posteggiTableId thead tr').clone(true).appendTo( '#posteggiTableId thead' );
    jQuery('#posteggiTableId thead tr:eq(1) th').each( function (i) {
        var title = jQuery(this).text();
        jQuery(this).html( '<input type="text" placeholder="'+title+'" />' );
 
        jQuery( 'input', this ).on( 'keyup change', function () {
        
            if ( table.column(i).search() !== this.value ) {
                table
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    
   var table = jQuery('#posteggiTableId').DataTable( {
    	orderCellsTop: true,
        fixedHeader: true,
        responsive: true,

        "aoColumnDefs": [{ "searchable": true, "aTargets": [9] }],
    	"paging": false,
    	"columns": [
    	            { "orderable": true, "width": "10%" },
    	            { "orderable": true, "width": "10%" },
    	            { "orderable": true, "width": "10%" },
    	            { "orderable": true, "width": "10%" },
    	            { "orderable": true, "width": "10%" },
    	            { "orderable": false, "width": "10%" },
    	            { "orderable": false, "width": "10%" },
    	            { "orderable": false, "width": "5%" },
    	            { "orderable": false, "width": "2%" },
    	            { "orderable": false, "width": "2%" }
    	          ],
    	          
    	          "language":{
    	        	    "decimal":        "",
    	        	    "emptyTable":     "Nessun dato",
    	        	    "info":           "Visualizzati da _START_ a _END_ di _TOTAL_ record",
    	        	    "infoEmpty":      "Visualizzati da 0 a 0 di 0 record",
    	        	    "infoFiltered":   "(filtered from _MAX_ total entries)",
    	        	    "infoPostFix":    "",
    	        	    "thousands":      ",",
    	        	    "lengthMenu":     "Visualizza _MENU_ record",
    	        	    "loadingRecords": "Caricamento dati...",
    	        	    "processing":     "In elaborazione...",
    	        	    "search":         "Ricerca:",
    	        	    "zeroRecords":    "Nessun record trovato",
    	        	    "paginate": {
    	        	        "first":      "Primo",
    	        	        "last":       "Ultimo",
    	        	        "next":       "Prossimo",
    	        	        "previous":   "Precedente"
    	        	    },
    	        	    "aria": {
    	        	        "sortAscending":  ": Attivare per ordinare in ordine crescente le colonne",
    	        	        "sortDescending": ": Attivare per ordinare in ordine decrescente le colonne"
    	        	    }
    	        	},
        initComplete: function () {
            this.api().columns('.select-filter').every( function () {
                var column = this;
                
                var select = jQuery('<select><option value=""></option></select>')
                    .appendTo( jQuery(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = jQuery.fn.dataTable.util.escapeRegex(
                        		jQuery(this).val()
                        );
                       
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' );
                } );
            } );
        }
    } );


} );
</script>

	</body>
</html>