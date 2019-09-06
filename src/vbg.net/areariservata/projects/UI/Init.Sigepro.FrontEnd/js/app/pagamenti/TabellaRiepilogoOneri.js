/*global define*/
/*jslint unparam: true,*/

function TabellaRiepilogoOneri(selector, labelTotale) {
    var element = $(selector),
        tabella = $('<table />', { 'class': 'tabellaRiepilogoOneri table' }),
        initialized = false;

    element.empty();

    tabella.appendTo(element);

    function formatString(importo) {
        var importos = importo.toString().replace('.', ',');

        if (importos.indexOf(',') === -1) {
            importos += ",00";
        }

        return importos;
    }

    function scriviIntestazioneTabella() {
        var th = $('<tr />'),
            causale = $('<th />'),
            importo = $('<th />');

        causale.text('Causale');
        importo.text('Importo');

        causale.appendTo(th);
        importo.appendTo(th);

        th.appendTo(tabella);
    }

    function aggiungiRiga(causale, importo) {
        var tr = $('<tr />', { 'class': 'rigaCausale valoreOnere' }),
            causaleCell = $('<td />'),
            importoCell = $('<td />', { 'class': 'importoOnere' });

        tr.data('importo', importo);

        causaleCell.text(causale);
        importoCell.text(formatString(importo));

        causaleCell.appendTo(tr);
        importoCell.appendTo(tr);

        tr.appendTo(tabella);
    }

    function calcolaTotale() {
        var tr = $('<tr />', { 'class': 'rigaCausale totale' }),
            causale = $('<td />'),
            importo = $('<td />'),
            totale = 0.0;

        tabella.find('.rigaCausale.valoreOnere').each(function (idx, el) {
            totale += $(el).data('importo');
        });

        causale.text(labelTotale);
        importo.text(formatString(totale));

        causale.appendTo(tr);
        importo.appendTo(tr);

        tr.appendTo(tabella);
    }

    function inizioAggiornamento() {

        if (!initialized) {
            scriviIntestazioneTabella();
            initialized = true;
        }

        element.find('.rigaCausale').remove();
    }

    function fineAggiornamento() {
        calcolaTotale();
    }

    function render(listaValori) {
        var i,
            item;

        inizioAggiornamento();

        for (i = 0; i < listaValori.length; i += 1) {
            item = listaValori[i];

            aggiungiRiga(item.causale, item.importo);
        }

        fineAggiornamento();

        element.closest('.panel').toggle(listaValori.length > 0);
    }
    
    return {
        render: render
    };
}
