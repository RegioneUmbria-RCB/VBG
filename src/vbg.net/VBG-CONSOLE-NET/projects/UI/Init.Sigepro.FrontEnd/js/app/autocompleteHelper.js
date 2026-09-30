define(['jquery.ui'], function () {

    function AutocompleteHelper(textControl, valueControl, valuesService, minLength) {
        textControl.autocomplete({
            source: function (request, response) {

                valuesService.getValues(textControl.val(), function (data) {
                    response($.map(data, function (item) {
                        return {
                            label: item.Descrizione,
                            id: item.Codice,
                            value: item.Descrizione
                        };
                    }));
                });
            },
            minLength: minLength ? minLength : 2,
            search: function (event, ui) { valueControl.val(''); },
            select: function (event, ui) {
                if (ui.item && ui.item.id.length > 0) {
                    valueControl.val(ui.item.id);
                    textControl.val(ui.item.value);
                }
                else {
                    valueControl.val('');
                }

                //return false;
            }
        });

        textControl.blur(function (e) {
            if (valueControl.val() === '')
                textControl.val('');

            if (textControl.val() === '')
                valueControl.val('');
        });
    }

    return AutocompleteHelper;
});