<%@ include file="../includes/taglibs.jsp" %>
<div id="popup-modifica-mail" class='vbg-modal' data-auto-open='false'>
	<div class='vbg-modal-body'>
		<h1>
			<span id="titolare-mail-pec"></span>
		</h1>
		<div class="vbg-form">
			<div id="div-modifica-pec" class="form-group">
				<label><fmt:message key="label.comunicazione.bollettazione.riga.pec" /></label>
				<input type="text" value="" id="modifica-pec" width="250px">
			</div>
		</div>
		<div class="vbg-form">
			<div id="div-modifica-mail" class="form-group">
				<label><fmt:message key="label.comunicazione.bollettazione.riga.mail" /></label>
				<input type="text" value="" id="modifica-mail" width="250px">
			</div>
		</div>	
		<div class="vbg-modal-footer">
			<div class='btn btn-primary' id='cmdSalvaMail' data-id="" data-codiceanagrafe="">
				<fmt:message key="button.save" />
			</div>
			<a href="#" data-role='toggle-popup' class="btn btn-secondary">
				<fmt:message key="button.back" />
			</a>
		</div>
	</div>
</div>