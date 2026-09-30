<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<fmt:message key="label.peopleprocsportelli.nuovo_peopleprocsportelli.title" />
		</title>
	</head>
	<body>
		<span class="titoloPagina">
			<fmt:message key="label.peopleprocsportelli.nuovo_peopleprocsportelli.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form" />
		</jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="peopleprocsportelliHelper" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp">
					<jsp:param name="commandName" value="peopleprocsportelliHelper" />
				</jsp:include>
				<table>
					<tr>
						<td>
							* <fmt:message key="label.peopleprocsportelli.peopleproc" />
						</td>
						<td>
							<spring-form:input id="peopleProc_id" path="peopleProc" size="30" />
							<spring-form:errors path="peopleProc" cssClass="error" />
							<div id="errori_peopleProc_id" class="error validation-feedback">
								Il campo è obbligatorio</div>
						</td>
					</tr>
					<tr>
						<td>
							* <fmt:message key="label.comune" />
						</td>
						<td>
							<spring-form:select path="codicecomune" id="comuni_id"
								items="${comuniassociatiListInRequest}" itemValue="codicecomune"
								itemLabel="comune" />
							<init:help idHelp="help_codicecomune" textKey="peopleprocsportelli.help.codicecomune" />
							<spring-form:errors path="codicecomune" cssClass="error" />
							<div id="errori_comuni_id" class="error validation-feedback">
								Selezionare almeno un comune</div>
					</tr>
					<tr>
						<td>
							<fmt:message key="label.software" />
						</td>
						<td>
							<spring-form:select path="software.codice" id="software_id">
								<spring-form:options items="${listSoftware}" itemLabel="descrizione"
									itemValue="codice" />
							</spring-form:select>
						</td>
					</tr>
				</table>

			</spring-form:form>
		</div>
		<div id="functions">
			<ul>
				<li><a id="bInsert" href="javascript:doSubmit('insert.htm','',document.inviodati)">
						<fmt:message key="button.insert" />
					</a></li>

				<li><a href="javascript:doHref('list.htm','')">
						<fmt:message key="button.back" />
					</a></li>
			</ul>
		</div>
		<script type='text/javascript'>

			(function ($) {

				const bottoneInserisci = document.getElementById('bInsert');
				/*disattivo il bottone di inserimento*/
				bottoneInserisci.setAttribute('disabled', 'disabled');

				const people = document.getElementById('peopleProc_id');
				const comune = document.getElementById('comuni_id');
				const software = document.getElementById('software_id');

				const campoErrorePeople = document.getElementById("errori_peopleProc_id");
				campoErrorePeople.style.display = 'none';
				const campoErroreComuni = document.getElementById("errori_comuni_id");
				campoErroreComuni.style.display = 'none';

				console.log('Valori comune: ' + $('#comuni_id').val());

				people.addEventListener('blur', (e) => {

					const value = e.target.value;
					if (value == '') {
						console.log('Errore People');
						campoErrorePeople.style.display = 'inline';
						people.style = 'border-color: var(--error-color)';							
					} else {
						console.log('Ok');
						campoErrorePeople.style.display = 'none';
						people.style = 'border-color', 'var(--text-color)';
						bottoneInserisci.toggleAttribute('disabled', false);
					}
					comune.focus();
					bottoneInserisci.setAttribute('disabled', 'disabled');
				});

				comune.addEventListener('blur', (e) => {

					const value1 = e.target.value;
					console.log('valore comune: ' + value1);
					if (value1 == '') {
						console.log('Errore comuni');
						campoErroreComuni.style.display = 'inline';
						comune.style = 'border-color: var(--error-color)';							
					} else {
						console.log('Ok');
						campoErroreComuni.style.display = 'none';
						comune.style = 'border-color', 'var(--text-color)';
						bottoneInserisci.toggleAttribute('disabled', false);
					}
					 e.preventDefault();
				});

				
			})(jQuery);

			$('peopleProc_id').focus();
		</script>
	</body>

</html>