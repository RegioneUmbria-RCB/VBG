define(['jquery', 'json', 'jquery.ui'], function ($, JSON) {

    function RicercaComuni(idComune, comuneAutoComplete, hiddenCodiceComune, urlRicerca) {
        comuneAutoComplete.autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({ 'aliasComune': idComune, 'matchComune': comuneAutoComplete.val() }),
                    success: function (data) {
                        response($.map(data.d, function (item) {
                            return { label: item.Descrizione,
                                id: item.CodiceComune,
                                value: item.Comune + " (" + item.SiglaProvincia + ")"
                            };
                        }));
                    }
                });
            },
            search: function (event, ui) { hiddenCodiceComune.val(''); },
            select: function (event, ui) {
                if (ui.item && ui.item.id.length > 0) {
                    hiddenCodiceComune.val(ui.item.id);
                    comuneAutoComplete.val(ui.item.value);
                }
                else {
                    hiddenCodiceComune.val('');
                }

                //return false;
            }
        });

        comuneAutoComplete.blur(function (e) {
            if (hiddenCodiceComune.val() === '')
                comuneAutoComplete.val('');

            if (comuneAutoComplete.val() === '')
                hiddenCodiceComune.val('');
        });
    }

    return RicercaComuni;
});