/*global define*/
function FixLayout_($) {
    'use strict';
    
    this.correggiAllineamentoImmagine = function () {
    
        var image = $('header > .header-logo img'),
            self = this,
            altezzaLogo = 0,
            altezzaIntestazione = 0,
            nuovoTop = 0;

        if (!image[0] || !image[0].complete) {
            setTimeout(function () { self.correggiAllineamentoImmagine(); }, 100);
        } else {
            altezzaLogo = $('header >.header-logo').height();
            altezzaIntestazione = $('header > .header-nome > div').height();

            nuovoTop = (altezzaLogo - altezzaIntestazione) / 2;
            $('header > .header-nome > div').css('padding-top', nuovoTop + 'px');
        }
    };

    this.fixFormTables = function () {
        $('.inputForm table').each(function (idx) {
            $(this).find('tr>th')
                    .parent()
                    .addClass('HeaderStyle');
            $(this).find('tr:even')
                   .addClass('AlternatingItemStyle');
            $(this).find('tr:odd')
                    .addClass('ItemStyle');
        });
    };

    this.fixDDTables = function () {
        $('.DatiDinamici table').each(function (idx) {
            $(this).find('tr>th')
                    .parent()
                    .removeClass('HeaderStyle');
            $(this).find('tr:even')
                    .removeClass('AlternatingItemStyle');
            $(this).find('tr:odd')
                    .removeClass('ItemStyle');
        });
    };

    this.fixBlocchi = function () {
        $('.blocco').each(function () {

            if ($(this).hasClass('fisso')) {
                return;
            }

            var elLegend = $(this).find('legend'),
                testo = elLegend.html();

            $(this).data('testo', testo);

            if ($(this).hasClass('aperto')) {
                elLegend.html("[-] " + testo).css('cursor', 'pointer');
            } else {
                elLegend.html("[+] " + testo).css('cursor', 'pointer');

                $(this).find(':not(legend)')
                        .css('display', 'none');
            }

            elLegend.click(function () {
                //alert('in');

                var prefix = '[-]',
                    display = '';

                //alert($(this).text().slice(0,3));

                if ($(this).text().slice(0, 3) === '[-]') {
                    prefix = '[+]';
                    display = 'none';

                }

                $(this).html(prefix + " " + testo);
                $(this).parent().find(':not(legend)').css('display', display);

            });
        });
    };


    this.apply = function () {
        this.correggiAllineamentoImmagine();
        this.fixFormTables();
        this.fixDDTables();
        this.fixBlocchi();
    };
}

define(['jquery'], function ($) {
    'use strict';
    return new FixLayout_($);
});