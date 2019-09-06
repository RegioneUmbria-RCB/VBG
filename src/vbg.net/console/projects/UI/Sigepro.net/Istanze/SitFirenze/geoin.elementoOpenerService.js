function ElementoOpenerService(idElemento) {
    return {
        impostaIdPunto: function (key) {
            var el = window.opener.impostaIdPunto(key);
/*
            if (!el) {
                alert('Elemento con id ' + idElemento + ' non trovato nella finestra chiamante');
                return;
            }

            el.value = key;
*/
        }

    };
}