<%@ include file="../includes/taglibs.jsp" %>

<div id="popup-dettaglio-riga" class='vbg-modal' data-auto-open='false'>
	<div class='vbg-modal-body'>
		<h1 id='riga-titolo'></h1>
		<div class="vbg-form">
			<div class="form-group">
				<label><fmt:message key="label.comunicazione.commissione.riga.destinatario" /></label>
				<span id="riga-destinatario" class='readonly-form-control'></span>
			</div>
		</div>
		<div class="vbg-form">
			<div class="form-group">
				<label><fmt:message key="label.comunicazione.commissione.riga.protocollo" /></label>
				<span id="riga-protocollo" class='readonly-form-control'></span>
			</div>
		</div>
		<div class="vbg-form">
			<div class="form-group">
				<label><fmt:message key="label.comunicazione.commissione.riga.allegati" /></label>
				<span id="riga-allegati" class='readonly-form-control'></span>
			</div>
		</div>
		<div class="vbg-form">
			<div class="form-group">
				<label><fmt:message key="label.comunicazione.commissione.firmatari" /></label>
				<span id="riga-firmatari" class='readonly-form-control'></span>
			</div>
		</div>
		<div class="vbg-form">
			<div class="form-group">
				<label><fmt:message key="label.comunicazione.commissione.riga.errore" /></label>
				<span id="riga-errore" class='readonly-form-control'></span>
			</div>
		</div>		
		<div class="vbg-modal-footer">
			<div class='btn btn-primary' id='riga-cmd-elabora-riga' data-id="">
				<fmt:message key="button.elabora" />
			</div>
			<a href="#" data-role='toggle-popup' class="btn btn-secondary">
				<fmt:message key="button.back" />
			</a>
		</div>
	</div>
</div>