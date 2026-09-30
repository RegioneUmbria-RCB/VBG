<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html
	PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
		<html xmlns="http://www.w3.org/1999/xhtml" lang="it">

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
			<title>
				<c:if test="${conti.id.codice==null}">
					<fmt:message key="form.conti.title.create" />
				</c:if>
				<c:if test="${conti.id.codice!=null}">
					<fmt:message key="form.conti.title.view" />
				</c:if>
			</title>
			<script type="module"
				src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>"
				defer></script>
			<style>
				.errore {
					color: var(--error-color);
					padding-bottom: var(--default-padding);
					font-style: italic;
					padding-left: var(--half-padding);
				}

				.descrizione {
					cursor: help;
				}

				.td-mappatura {
					cursor: copy;
				}
			</style>
		</head>

		<body>
			<span class="titoloPagina">
				<c:if test="${conti.id.codice==null}">
					<fmt:message key="form.conti.title.create" />
				</c:if>
				<c:if test="${conti.id.codice!=null}">
					<fmt:message key="form.conti.title.view" />
				</c:if>
			</span>
			<jsp:include page="../includes/innerNavigation.jsp">
				<jsp:param name="navmode" value="form" />
			</jsp:include>
			<div id="subcontent">
				<div class="vbg-form">
					<spring-form:form commandName="conti" name="inviodati">
						<jsp:include page="../includes/displayGlobalMessages.jsp">
							<jsp:param name="commandName" value="conti" />
						</jsp:include>
						<div class="errore"></div>
						<div class="form-group">
							<label>*
								<fmt:message key="form.conti.descrizione" />
							</label>
							<spring-form:input id="descrizione_id" path="descrizione" size="70" />
							<spring-form:errors path="descrizione" cssClass="error" />
						</div>
						<div class="form-group">
							<label>
								<fmt:message key="form.conti.note" />
							</label>
							<spring-form:textarea id="note_id" path="note" cols="60" rows="4" />
							<spring-form:errors path="note" cssClass="error" />
						</div>
						<div class="form-group">
							<label>*
								<fmt:message key="form.conti.iva" />
							</label>
							<spring-form:input cssStyle="text-align: right;" id="iva_id" path="iva" size="5"
								maxlength="5" />
							<init:help idHelp="help1" textKey="form.conti.iva.help" />
							<spring-form:errors path="iva" cssClass="error" />
						</div>
						<c:if test="${verticalizzazioni_nodoPagamenti_attiva eq true}">
							<div class="form-group">
								<label for="mappaturanodopag">
									<fmt:message key="tipicausalioneri.label.mappaturanodopag" />
								</label>
								<spring-form:input id="mappaturanodopag_id" path="mappaturanodopag" maxlength="50"
									size="30" readonly="true" />
								<spring-form:errors path="mappaturanodopag" cssClass="error" />
								<c:if test="${conti.mappaturanodopag != null }">
									<a id="cancella_mappatura"><i class="fa fa-trash"></i></a>
								</c:if>
								<a id="bottone_mappatura" class="btn btn-primary" href="">
									<c:if test="${conti.mappaturanodopag == null}">
										<fmt:message key="button.search" />
									</c:if>
									<c:if test="${conti.mappaturanodopag != null }">
										<fmt:message key="button.dettaglio" />
									</c:if>
								</a>
							</div>
						</c:if>
						<!-- Popup gestione codice mappatura -->
						<div class="form-froup" id="form-mappatura"></div>
						<!-- Fine popup-->

						<div class="form-group">
							<label for="dataScadenza">
								<fmt:message key="form.conti.datascadenza" />
							</label>
							<spring-form:input id="dataScadenza_id" path="dataScadenza" size="10"
								onblur="isValidDate(this,div true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="calDataScadenza"
								idInput="dataScadenza_id" textKey="label.calendar" />
							<spring-form:errors path="dataScadenza" cssClass="error" />
							<init:help idHelp="helpDataScadenza" textKey="help.data_scadenza_conti" />
						</div>

					</spring-form:form>
				</div>
			</div>
			<vbg-modal id="visualizza_dettagli" class="vbg-form">
				<div slot='body' id="mod-body">

				</div>

			</vbg-modal>

			<script type="text/javascript">

				let bottone = document.querySelector('#bottone_mappatura');
				let bloccoMappatura = document.querySelector('#form-mappatura');
				let fieldSet = document.createElement('fieldset');
				fieldSet.className = "fieldset-cerca";
				let divCorpo = document.createElement('div');
				let buttonCancella = document.querySelector('#cancella_mappatura');
				let modal = document.querySelector('#visualizza_dettagli');
				let modBoby = document.querySelector('#mod-body');

				divCorpo.className = "corpo";

				let json = '';

				bottone.addEventListener('click', async (e) => {
					if (document.querySelector('.testata')) {
						document.querySelector('.fieldset-cerca legend').remove();
						document.querySelector('.testata').remove();
						document.querySelector('.corpo').remove();
					}

					e.preventDefault();
					window.vbg.mostraModalCaricamento();

					let codiceMappatura = document.querySelector('#mappaturanodopag_id').value;
					let response = await fetch('../conti/ajaxGestisciMappatturaNodo.htm');
					json = await response.json();
					if (response.status !== 200) {
						window.vbg.nascondiModalCaricamento();
						document.querySelector('.errore').innerText = json.errore;
					}


					if (json.messaggio) {

						modBoby.innerHTML = json.messaggio;
						modal.open();
					} else
						if (codiceMappatura == null || codiceMappatura == '') {

							// titolo
							let legend = document.createElement('legend');
							legend.appendChild(document.createTextNode('Filtri'));
							fieldSet.appendChild(legend);

							// Testata
							let divTestata = document.createElement('div');
							divTestata.className = "testata";

							// Filtri ricerca
							let select = document.createElement('select');
							select.className = "filtri_connettore";
							select.setAttribute('onchange', `setFiltro()`);
							let opt_0 = document.createElement('option');
							opt_0.setAttribute('value', '0');
							opt_0.innerText = "-- Selezionare un elemento --";
							select.appendChild(opt_0);
							json.forEach(connettori => {
								let option = document.createElement('option');
								let cf = connettori.connettore.cf_codice_profilo;
								option.setAttribute('value', cf);
								option.innerText = connettori.connettore.descrizione_connettore;
								select.appendChild(option);
							});
							divTestata.appendChild(select);
							// end filtri ricerca

							fieldSet.appendChild(divTestata);
							fieldSet.appendChild(divCorpo);
							bloccoMappatura.appendChild(fieldSet);
						} else {
							visualizzaRigaDettaglio(codiceMappatura, null);
						}
					window.vbg.nascondiModalCaricamento();
				});



				function setFiltro() {
					//rimuove la tabella se visualizzata dopo una prima ricerca
					if (document.querySelector('.vbg-table')) {
						document.querySelector('.vbg-table').remove();
					}

					let table = document.createElement('table'),
						thead = document.createElement('thead'),
						tbody = document.createElement('tbody'),
						tr = document.createElement('tr'),
						rows = [],
						max = 0,
						conn = 0;

					table.className = "vbg-table";
					thead.appendChild(tr);
					table.appendChild(thead);

					//creo la testata della tabella

					let th_con = document.createElement('th');
					th_con.appendChild(document.createTextNode('Connettore'));
					let th_desc = document.createElement('th');
					th_desc.appendChild(document.createTextNode('Descrizione'));
					let th_mapp = document.createElement('th');
					th_mapp.appendChild(document.createTextNode('Mappatura'));
					tr.appendChild(th_con);
					tr.appendChild(th_desc);
					tr.appendChild(th_mapp);

					let filtro = document.querySelector('.filtri_connettore').value;
					let filtrati = json.filter(connettori => connettori.connettore.cf_codice_profilo == filtro);

					filtrati.forEach(connettori => {

						max = Math.max(max, connettori.connettore.causali.length);
						for (let i = 0; i < max; i++) {
							let id = connettori.connettore.causali[i].id;
							let mappatura_client = connettori.connettore.causali[i].mappatura_client;
							let cod_profilo = connettori.connettore.cf_codice_profilo;
							tr = document.createElement('tr');
							tbody.appendChild(tr);
							let visualizzaRigaDettaglio = "visualizzaRigaDettaglio('" + mappatura_client + "','" + cod_profilo + "')";
							let td_conn = document.createElement('td');
							tr.appendChild(td_conn);
							td_conn.appendChild(document.createTextNode(connettori.connettore.descrizione_connettore));
							let td_desc = document.createElement('td');
							td_desc.setAttribute('onclick', visualizzaRigaDettaglio);
							td_desc.className = "descrizione";
							tr.appendChild(td_desc);
							td_desc.appendChild(document.createTextNode(connettori.connettore.causali[i].descrizione));
							let td_mapp = document.createElement('td');
							td_mapp.className = "td-mappatura";
							let salva = "salvaMappatura('" + mappatura_client + "')";
							td_mapp.setAttribute('onclick', salva);
							tr.appendChild(td_mapp);
							td_mapp.appendChild(document.createTextNode(connettori.connettore.causali[i].mappatura_client));
						}

					});
					table.appendChild(tbody);
					divCorpo.appendChild(table);

				}

				function salvaMappatura(mappatura_client) {
					window.vbg.mostraModalCaricamento();
					document.querySelector('#mappaturanodopag_id').value = mappatura_client;
					doSubmit('update.htm', '', document.inviodati);
				}

				/* Metodo che permette di visualizzare il dettaglio sia di una singola riga che di una mappatura e apre il popup*/
				function visualizzaRigaDettaglio(mappatura_client, cf_ente) {

					let newArr = filtraJson(mappatura_client, cf_ente);
					let dettagli = `<h2>Dettagli</h2>`;
					for (let t = 0; t < newArr.length; t++) {
						let filtrato = cf_ente == null ? json : json.filter(c => c.connettore.cf_codice_profilo == cf_ente);
						let nome_connettore = filtrato[t].connettore.descrizione_connettore;
						dettagli += `<fieldset><legend>` + nome_connettore + `</legend>`;
						dettagli += `<div><b>` + newArr[t].descrizione + `</b></div>`;

						if (newArr[t].parametri != null) {
							dettagli += `<table class="vbg-table">
								<thead><tr><th>Descrizione</th><th>Valore</th></tr></thead>`;
							for (let i = 0; i < newArr[t].parametri.length; i++) {
								dettagli += `<tr">`;
								let desc_i = newArr[t].parametri[i].descrizione;
								let value_i = newArr[t].parametri[i].valore;
								dettagli += `<td>` + desc_i + `</td><td>` + value_i + `</td></tr>`;
							}
							dettagli += `</table>`
						}
						dettagli += `</fieldset>`;
					}
					modBoby.innerHTML = dettagli;
					modal.open();

				}

				function filtraJson(mappatura_client, cf_ente) {
					let newArr = [];
					let filtrati = cf_ente == null ? json : json.filter(connettori => connettori.connettore.cf_codice_profilo == cf_ente);
					filtrati.forEach(c => {
						let conn = c.connettore;
						conn.causali.forEach(cau => {
							if (cau.mappatura_client == mappatura_client) {
								newArr.push(cau);
							}
						});
					});
					return newArr;
				}

				if (buttonCancella) {
					buttonCancella.addEventListener('click', (e) => {

						e.preventDefault();
						if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
							window.vbg.mostraModalCaricamento();
							document.querySelector('#mappaturanodopag_id').value = '';
							doSubmit('update.htm', '', document.inviodati);
						}
					});
				}

			</script>


			<div class="form-button">
				<c:if test="${conti.id.codice==null}">
					<a class="btn btn-primary" href="javascript:doSubmit('insert.htm','',document.inviodati)">
						<fmt:message key="button.insert" />
					</a>
				</c:if>
				<c:if test="${conti.id.codice!=null}">
					<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)">
						<fmt:message key="button.update" />
					</a>
					<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)">
					<fmt:message key="button.delete" /></a>
				</c:if>
				<a class="btn btn-secondary" href="javascript:doHref('list.htm','')">
					<fmt:message key="button.back" />
				</a>
			</div>
		</body>

		</html>