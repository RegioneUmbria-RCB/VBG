define(['jquery', 'json', 'jquery.ui'], function ($, JSON) {
    function RicercaProvincia(idComune, provinciaAutoComplete, hiddenCodiceProvincia, urlRicerca) {
        provinciaAutoComplete.autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
                    type: "POST",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    data: JSON.stringify({ 'aliasComune': idComune, 'matchProvincia': provinciaAutoComplete.val() }),
                    success: function (data) {
                        response($.map(data.d, function (item) {
                            return { label: item.Descrizione,
                                id: item.SiglaProvincia,
                                value: item.Provincia
                            };
                        }));
                    }
                });
            },
            search: function (event, ui) { hiddenCodiceProvincia.val(''); },
            select: function (event, ui) {
                if (ui.item && ui.item.id.length > 0) {
                    hiddenCodiceProvincia.val(ui.item.id);
                    provinciaAutoComplete.val(ui.item.value);
                }
                else {
                    hiddenCodiceProvincia.val('');
                }

                //return false;
            }
        });

        provinciaAutoComplete.blur(function (e) {
            if (hiddenCodiceProvincia.val() === '')
                provinciaAutoComplete.val('');
        });
    }

    return RicercaProvincia;
});