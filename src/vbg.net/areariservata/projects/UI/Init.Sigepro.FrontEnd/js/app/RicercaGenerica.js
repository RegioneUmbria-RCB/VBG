function RicercaGenerica(idComune, autocomplete, hiddenControl, urlRicerca) {

    function onSearchSuccess(response, data) {
        response($.map(data.d, function (item) {
            return {
                label: item.descrizione,
                id: item.codice,
                value: item.descrizione
            };
        }));
    }

    function onSearch(event, ui) {
        hiddenControl.val('');
    }


    function onItenSelected(event, ui) {
        if (ui.item && ui.item.id.length > 0) {
            hiddenControl.val(ui.item.id);
            autocomplete.val(ui.item.value);
        }
        else {
            hiddenControl.val('');
        }
    }

    function autocompleteConfig(request, response) {
        $.ajax({
            url: urlRicerca, //'<%=ResolveClientUrl("~/Public/WebServices/AutocompleteComuni.asmx") %>/RicercaComune',
            type: "POST",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify({
                'idComune': idComune,
                'partial': autocomplete.val()
            }),
            success: function (data) {
                onSearchSuccess(response, data);
            }
        });
    }

    autocomplete.autocomplete({
        source: autocompleteConfig,
        search: onSearch,
        select: onItenSelected
    });

    autocomplete.blur(function (e) {
        if (hiddenControl.val() === '')
            autocomplete.val('');
    });
}
