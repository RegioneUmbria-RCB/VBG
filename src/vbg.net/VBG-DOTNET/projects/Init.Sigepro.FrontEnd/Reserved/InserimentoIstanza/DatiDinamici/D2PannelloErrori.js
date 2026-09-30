/*global $, jQuery*/
function D2PannelloErrori(divErroriOrId) {
    'use strict';
    this.element = typeof (divErroriOrId) === "string" ? $('#' + divErroriOrId) : divErroriOrId;

    this.mostraErrori = function (listaErrori) {
        if (!this.element) {
            return;
        }

        this.element.empty();
        
        if (listaErrori === null || listaErrori.length === 0) {
            this.nascondi();
        } else {
            this.visualizza(listaErrori);
        }
    };
    
    this.nascondi = function () {
        this.element.css('display', 'none');
    };
    
    this.visualizza = function (listaErrori) {
        var ul = $('<ul />'),
            i = 0;

        this.element.css('display', 'block');
        
        for (i = 0; i < listaErrori.length; i += 1) {
            $("<li />").html(listaErrori[i]).appendTo(ul);
        }

        ul.appendTo(this.element);
    };
}