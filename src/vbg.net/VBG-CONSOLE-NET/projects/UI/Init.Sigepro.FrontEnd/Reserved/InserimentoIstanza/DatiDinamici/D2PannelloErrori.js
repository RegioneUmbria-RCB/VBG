/*global $, jQuery*/
function D2PannelloErrori(idPannelloErrori) {
    'use strict';
    this.element = $('#' + idPannelloErrori);

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

        this.element.css('display', 'inline');
        
        for (i = 0; i < listaErrori.length; i += 1) {
            $("<li />").html(listaErrori[i]).appendTo(ul);
        }

        ul.appendTo(this.element);
    };
}