<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>NODO NLA-PDD Registro Imprese - Configurazione</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome/5.15.4-web/css/all.min.css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome/5.15.4-web/css/v4-shims.min.css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/standard.css" crossorigin="anonymous">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/vbg-modern/style.css" crossorigin="anonymous">
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/vbg/vbg-ready.js?<%=vJS %>"></script>
</head>
<body>
	<h1>NODO NLA-PDD Registro Imprese</h1>
	<label>La funzionalità permette di gestire la configurazione dei certificati che verranno utilizzati in fase di integrazione</label>
	<br />
	<label>In caso di nuova registrazione, se il certificato già esiste NON verrà sovrascritto, così come in fase di cancellazione NON verrà eliminato fisicamente</label>
	<br />
	<label>E' possibile indicare più codici catastali contemporaneamente separandoli con una virgola</label>
	<br />
	<br />
	<div class="vbg-form">
		<spring-form:form commandName="nuovoCertificatoModel" name="inviodati" enctype="multipart/form-data" action="add.htm" method="post">
			<fieldset>
				<legend>Nuovo certificato</legend>
				<div class="form-group">
					<label>Codice Catastale Ente</label>
					<spring-form:input type="text" id="codiceCatastale" path="codiceCatastale" />
				</div>
				<div class="form-group">
					<label>Certificato</label>
					<input type="file" id="certificato" name="certificato"></input>
				</div>
				<div class="form-group">
					<label>Alias</label>
					<input type="alias" id="alias" name="alias" ></input>
				</div>
				<div class="form-group">
					<label>Password</label>
					<input type="password" id="password" name="password" autocomplete="new-password"></input>
				</div>
				<div class="form-button">
					<a class="btn btn-primary azione cmd-nuovo">Aggiungi</a>
				</div>
			</fieldset>
		</spring-form:form	>
		<fieldset>
			<legend>Elenco enti</legend>
			<table class="vbg-table">
				<thead>
					<tr>								
						<th>Codice Catastale Ente</th>
						<th>Certificato</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${config.enti}" var="ente">
				    	<tr>
							<td>${ente.codiceCatastale}</td>
							<td>${ente.nomeFile}</td>
							<td>
								<a class="btn azione cmd-elimina" data-id="${ente.codiceCatastale}">
									<i class="fa fa-trash-o"></i> Elimina
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<fieldset>
			<legend>Elenco certificati</legend>
			<table class="vbg-table">
				<thead>
					<tr>
						<th>Certificato</th>
						<th>Azioni</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${config.certificati}" var="certificato">
				    	<tr>
							<td>${certificato.nomeFile}</td>
							<td>
								<a class="btn azione cmd-elimina-cert" data-id="${certificato.nomeFile}">
									<i class="fa fa-trash-o"></i> Elimina
								</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</fieldset>
	</div>
</body>
<script type="text/javascript">
vbg.ready(() => {
	
	//NUOVA CONFIGURAZIONE
	let nuovo = document.getElementsByClassName("azione cmd-nuovo")[0];
	nuovo.addEventListener("click", async (el) => {
		el.preventDefault();

		const formData = new FormData();
		formData.append('codiceCatastale', document.getElementById('codiceCatastale').value);
		formData.append('certificato',document.getElementById('certificato').files[0]);
		formData.append('password', document.getElementById('password').value);
		formData.append('alias', document.getElementById('alias').value);
		
	   	let url = "${pageContext.request.contextPath}/config/add.htm";

	 	const response = await fetch(url, {
			method: "POST",
            cache: "no-cache",
            body: formData //document.nuovocertificato       
		});
	 	
	 	if( response.status != 200 ){
	 		alert("Si sono verificati errori durante il salvataggio!");
	 	}
	 	
	 	location.reload();
	});	
	
	// ELIMINA
	let elimina = document.getElementsByClassName("azione cmd-elimina");
	Array.prototype.forEach.call(elimina, function(item){
		item.addEventListener("click", async (el) => {
			el.preventDefault();
			
			if (confirm("Proseguire con la cancellazione della configurazione per " + el.target.dataset.id) == true) {
				const formData = new FormData();
				formData.append('codiceCatastale', el.target.dataset.id);
			
				let url = "${pageContext.request.contextPath}/config/elimina.htm";
				
				const response = await fetch(url, {
					method: "POST",
		            cache: "no-cache",
		            body: formData        
				});
			 	
			 	if( response.status != 200 ){
			 		alert("Si sono verificati errori durante la cancellazione di una configurazione!");
			 	}
			 	
			 	location.reload();
			}
		});			
	});

});
</script>
</html>