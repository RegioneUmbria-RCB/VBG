define(['jquery'], function ($) {

    return function () {
        $('.notePadre').each(function () {
            var innerContent = this.innerHTML;
            this.innerHTML = "<fieldset class='notePadreWrapper'><legend>[+]</legend><div class='note'>" + innerContent + "</note></fieldset>";

            var elNomeNodo = $(this).find('.note > .nomeNodo');
            var testoNomeNodo = "<i>&nbsp;" + elNomeNodo.html() + "</i>";
            elNomeNodo.css('display', 'none');

            var elLegend = $(this).find('legend');
            elLegend.data('nomeNodo', testoNomeNodo);
            elLegend.html('[+]' + testoNomeNodo);
            elLegend.css('cursor', 'pointer');


            $(this).find('.note').css('display', 'none');
            $(this).css('padding', '5px');

            elLegend.click(function () {

                var nomeNodo = $(this).data('nomeNodo');

                var note = $(this).parent().find('.note');

                if (note.is(':visible')) {
                    note.css('display', 'none');
                    $(this).html('[+]' + nomeNodo);
                }
                else {
                    note.css('display', 'block');
                    $(this).html('[-]' + nomeNodo);
                }
            });
        });

        $('.notePadre:last').find('legend').click();
    };
});