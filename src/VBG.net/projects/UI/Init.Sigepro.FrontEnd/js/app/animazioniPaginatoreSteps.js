define(['jquery'], function ($) {

    function AnimazioniPaginatoreSteps(idContenitorePrincipale) {

        idContenitorePrincipale = '#' + idContenitorePrincipale;

        var settings = {
            idBottoneNavigazione: idContenitorePrincipale + ' .bottoneNavigazione',
            idTabellaPaginatoreSteps: idContenitorePrincipale + ' table',
            idDivInvioInCorso: idContenitorePrincipale + ' #invioInCorso',
            testoDivInvio: '<div style=\'display:none\' id=\'invioInCorso\'></div>'
        };

        var controls = {
            bottoneNavigazione: $(settings.idBottoneNavigazione),
            tabellaPaginatoreSteps: $(settings.idTabellaPaginatoreSteps),
            divInvioInCorso: creaDivInvio()
        };

        $(function () {
            controls.bottoneNavigazione
				.click(onBottoneNavigazioneClick);
        });

        function onBottoneNavigazioneClick() {
            controls.tabellaPaginatoreSteps
				.fadeOut('fast', nascondiTabellaSteps);
        }

        function mostraMessaggioInvioInCorso() {
            if (typeof (window.ValidatorOnSubmit) == "function" && window.Page_IsValid === false) {
                controls
                    .divInvioInCorso
					.fadeOut('slow', function () {
					    controls
                            .tabellaPaginatoreSteps
                            .fadeIn('slow');
					});
            }
        }

        function creaDivInvio() {

            var divInvio = $('<div />', {
                'style': 'display:none',
                'id': 'invioInCorso'
            });

            divInvio
                .text('Invio dei dati in corso...')
				.appendTo($(idContenitorePrincipale));

            return divInvio;
        }

        function nascondiTabellaSteps() {
            controls
                .divInvioInCorso
				.fadeIn('fast', mostraMessaggioInvioInCorso);
        }
    }

    return AnimazioniPaginatoreSteps;
});