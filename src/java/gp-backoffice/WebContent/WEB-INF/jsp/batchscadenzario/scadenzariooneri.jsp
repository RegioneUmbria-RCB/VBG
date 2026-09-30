<%@ include file="../includes/taglibs.jsp"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>

<form name="batchScadenzarioFilter" action="${formAction}">
	<div class="vbg-form" style="display: inline-grid;">
	<!-- <div id="f_ricerca" class="fRicerca"> -->
		<fieldset>
		<legend><fmt:message key="label.form_ricerca" /> </legend>
			<div class="form-group">
				<div class="input-icons">
					<i class="fa fa-search icon"></i>
					<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
				</div>
				<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella.scadenzario" /></div>		
			</div>
		</fieldset>	
		<!-- </div> -->
	
		<fieldset>
			<legend><fmt:message key="label.scadenzario_oneri"/> </legend>
			<div class="csv">
				<a href="#" onclick="tableToCSV()">
					<i class="fa fa-file-csv"></i>
            		<fmt:message key="button.export_csv"/>
            	</a>
            </div>
            		
             <div id="button">
		        
		        <a href="#" id="goToFirst" title="<fmt:message key="html.toolbar.tooltip.firstPage"/>">
					<i class="fas fa-angle-double-left"></i>     		
            	</a>
		        
		        <a href="#" id="prevButton" title="<fmt:message key="html.toolbar.tooltip.prevPage"/>">
					<i class="fa fa-angle-left"></i>   		
            	</a>
		        
		        <a href="#" id="nextButton" title="<fmt:message key="html.toolbar.tooltip.nextPage"/>">
					<i class="fa fa-angle-right"></i>      		
            	</a>
		        
		        <a href="#" id="goToLast" title="<fmt:message key="html.toolbar.tooltip.lastPage"/>">
					<i class="fa fa-angle-double-right"></i>        		
            	</a>
		        
		        
		        <select id="sel_size" onchange="selezionaNItem(value)">
		            <option value="10">10</option>
		            <option value="50">50</option>
		            <option value="100" selected>100</option>
		            <option value="${numeroOneri}" >${numeroOneri}</option>
		        </select>
		    </div>
		    <div style="display: none;">
		    <table id="tabella_scad_hidd" class="vbg-table">
				<thead>
					<tr>
						<th><fmt:message key="label.data_scadenza"/></th>
						<th><fmt:message key="label.numeroistanza"/></th>
						<th><fmt:message key="label.richiedente"/></th>
						<th><fmt:message key="label.intervento"/></th>
						<th><fmt:message key="label.tot_imp_causale"/></th>
						<th><fmt:message key="label.tot_inc_ric"/></th>
						<th>Codice STC</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tabScadenzarioOneri }" var="_scadonere" >
					<tr>
						<td><fmt:formatDate value="${_scadonere.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td>${_scadonere.numeroistanza}</td>
						<td>${_scadonere.richiedente}</td>
						<td>${_scadonere.descrizione}</td>
						<td><fmt:formatNumber value="${_scadonere.totale}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></td>
						<td><fmt:formatNumber value="${_scadonere.totalepagato}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></td>
						<td>${_scadonere.codicestc}</td>
					</tr>
				</c:forEach>
				</tbody>	
				</table>
				</div>
		    
			<table id="tabella_scad" class="vbg-table" data-pagecount=${numeroOneri}>
				<thead>
					<tr>
						<th><fmt:message key="label.data_scadenza"/></th>
						<th><fmt:message key="label.numeroistanza"/></th>
						<th><fmt:message key="label.richiedente"/></th>
						<th><fmt:message key="label.intervento"/></th>
						<th><fmt:message key="label.tot_imp_causale"/></th>
						<th><fmt:message key="label.tot_inc_ric"/></th>
						<th>codice</th>
						<th>Codice STC</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tabScadenzarioOneri }" var="scadonere" >
					<tr>
						<td><fmt:formatDate value="${scadonere.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
						<td class="campo-ricerca"><a href="../istanze/view.htm?codice=${scadonere.codiceistanza}">${scadonere.numeroistanza}</a></td>
						<td class="campo-ricerca">${scadonere.richiedente}</td>
						<td class="campo-ricerca">${scadonere.descrizione}</td>
						<td><fmt:formatNumber value="${scadonere.totale}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></td>
						<td><fmt:formatNumber value="${scadonere.totalepagato}" pattern="<%=WebConstants.NUMBER_FORMAT_PATTERN%>" /></td>
						<td>${scadonere.codiceistanza}</td>
						<td>${scadonere.codicestc}</td>
					</tr>
				</c:forEach>
				</tbody>			
			</table>			
		</fieldset>
	</div>
</form>


<script type="text/javascript">
		
	function filtraTestoCelle() {
				
		let input, filter, table, tr, td, i, txtValue;
		input = document.getElementById("input_ricerca");
		filter = input.value.toUpperCase();
		table = document.getElementById("tabella_scad");
		tr = table.getElementsByTagName("tr");
		let trovato = false;
		for (i = 1; i < tr.length; i++) {
		  td = tr[i].getElementsByClassName("campo-ricerca");
		  for (let cell of td) {
		    if (cell) {
		      txtValue = cell.textContent || cell.innerText;			       
		      if (txtValue.toUpperCase().indexOf(filter) > -1) {			        
		      	trovato = true;
		      }			        
		    }
		  }
		  if(trovato){
		  	tr[i].style.display = "";
		  	trovato = false;
		  } else{
		  	tr[i].style.display = "none";			  	
		  }
		}
	}
	
	function tableToCSV() {

	    let csv_data = [];

		let table = document.querySelector('#tabella_scad_hidd');
	    let rows = table.getElementsByTagName('tr');
	    for (var i = 0; i < rows.length; i++) {

	        var cols = rows[i].querySelectorAll('td,th');

	        let csvrow = [];
	        let col = "";
	        for (var j = 0; j < cols.length; j++) {

	            col = cols[j].innerHTML;
	            
	            if(col.startsWith("<a")){
	            	let start = col.indexOf("\">")+2;
	            	let end = col.indexOf("</");
	            	col = col.substring(start,end);
	            }
	            csvrow.push(col);
	        }

	        csv_data.push(csvrow.join(";"));
	    }

	    csv_data = csv_data.join('\n');

	    downloadCSVFile(csv_data);

	}

	function downloadCSVFile(csv_data) {

	    CSVFile = new Blob([csv_data], {
	        type: "text/csv"
	    });

	    let temp_link = document.createElement('a');

	    temp_link.download = "export_"+Date.now()+".csv";
	    let url = window.URL.createObjectURL(CSVFile);
	    temp_link.href = url;

	    temp_link.style.display = "none";
	    document.body.appendChild(temp_link);

	    temp_link.click();
	    document.body.removeChild(temp_link);
	}
	
	document.addEventListener('DOMContentLoaded', init, false);

    let data, table;
    let pageSize = document.querySelector('#sel_size').value;
    let curPage = 1;
    
    const dimTabella = document.querySelector('#tabella_scad').dataset.pagecount;
    
    function getNumeroPagine() {
        return Math.ceil(dimTabella / pageSize);
    }

    function init() {

        table = document.querySelector('#tabella_scad');

        data = creaTable(table);
        renderTable();

        document.querySelector('#goToFirst').addEventListener('click', firstPage, false);
        document.querySelector('#nextButton').addEventListener('click', nextPage, false);
        document.querySelector('#prevButton').addEventListener('click', previousPage, false);
        document.querySelector('#goToLast').addEventListener('click', lastPage, false);
    }


    function creaTable(table) {
        let _tabella = [];

        var row, rows = table.rows;
        for (var i = 1, iLen = rows.length; i < iLen; i++) {
            let obj = new Object();
            row = rows[i];
            obj.data = row.cells[0].textContent;
            obj.istanza = row.cells[1].textContent;
            obj.richiedente = row.cells[2].textContent;
            obj.intervento = row.cells[3].textContent;
            obj.totale = row.cells[4].textContent;
            obj.incassato = row.cells[5].textContent;
            obj.codice = row.cells[6].textContent;
            obj.codicestc = row.cells[7].textContent;
            _tabella.push(obj);
        }
        return _tabella;
    }
    
    function selezionaNItem(e) {
        pageSize = e;
        renderTable();
    }

    function renderTable() {
        // create html
        let result = `<thead>
			<tr>
			<th><fmt:message key="label.data_scadenza"/></th>
			<th><fmt:message key="label.numeroistanza"/></th>
			<th><fmt:message key="label.richiedente"/></th>
			<th><fmt:message key="label.intervento"/></th>
			<th><fmt:message key="label.tot_imp_causale"/></th>
			<th><fmt:message key="label.tot_inc_ric"/></th>
			<th>Codice Stc</th>
		</tr>
	</thead>`;
			data.filter((row, index) => {
		    let start = (curPage - 1) * pageSize;
		    let end = curPage * pageSize;
		    if (index >= start && index < end) return true;
		}).forEach(c => {
			let _data = c.data;
			let _istanza = c.istanza;
			let _richiedente = c.richiedente;
			let _intervento = c.intervento;
			let _totale = c.totale;
			let _incassato = c.incassato;
			let _codicestc = c.codicestc;			
			let _codice ='<a href="../istanze/view.htm?codice='+c.codice+'">';
		    result += `<tr>
		 <td>`+_data+`</td>
		 <td class="campo-ricerca">`+_codice+_istanza+`</a></td>
		 <td class="campo-ricerca">`+_richiedente+`</td>
		 <td class="campo-ricerca">`+_intervento+`</td>
		 <td>`+_totale+`</td>
		 <td>`+_incassato+`</td>
		 <td>`+_codicestc+`</td>
		</tr>`;
		});
        table.innerHTML = result;
    }

    function previousPage() {
        if (curPage > 1) curPage--;
        renderTable();
    }

    function nextPage() {
        if ((curPage * pageSize) < data.length) curPage++;
        renderTable();
    }
    
    function firstPage() {
        curPage = 1;
        renderTable();
    }

    function lastPage() {
        curPage = getNumeroPagine();
        renderTable();
    }	


</script>
<style media="all">
	#f_ricerca {
    	display: inline-block;
    }
    
    .csv {
		  text-align: right;
		  padding-right: var(--default-padding);
		}
</style>