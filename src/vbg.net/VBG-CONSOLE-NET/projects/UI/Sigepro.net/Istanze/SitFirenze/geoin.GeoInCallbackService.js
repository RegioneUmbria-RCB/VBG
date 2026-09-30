function GeoInCallbackService(callbacks) {

    return {
        puntoAggiunto: function (key) {
            callbacks.puntoAggiunto ? callbacks.puntoAggiunto(key) : function () { };
        },

        puntoEliminato: function (key) {
            callbacks.puntoEliminato ? callbacks.puntoEliminato(key) : function () { };
        },

        layerCorrenteInizializzato: function (plugin) {
            callbacks.layerCorrenteInizializzato ? callbacks.layerCorrenteInizializzato(plugin) : function () { }
        },

        puntoCliccato: function (key) {
            callbacks.puntoCliccato ? callbacks.puntoCliccato(key) : function () { }
        }

    };
}