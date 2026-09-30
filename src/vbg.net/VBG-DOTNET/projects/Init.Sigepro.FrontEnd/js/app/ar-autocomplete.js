
function ArAutocomplete(selector, searchFunction) {
    var container = $(selector),
        hiddenField = container.find('input[type=hidden]'),
        textField = container.find('input[type=text]');

    function svuotaValoreSelezionato() {
        hiddenField.val('');
    }

    textField.autocomplete({
        source: function (request, response) {

            searchFunction(request.term, function (data) {

                response($.map(data.d, function (item) {
                    return {
                        label: item.Descrizione,
                        id: item.Codice
                    };
                }));
            });
        },
        search: svuotaValoreSelezionato,
        select: function (event, ui) {
            if (ui.item && ui.item.id) {
                hiddenField.val(ui.item.id);
            }
        }
    });

    textField.on('change', function () {
        if (textField.val() === '') {
            hiddenField.val('');
        }
    });

    this.svuotaCampi = function () {
        textField.val('');
        hiddenField.val('');
    };
}
